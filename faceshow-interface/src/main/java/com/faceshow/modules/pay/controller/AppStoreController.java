package com.faceshow.modules.pay.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.HttpsUtils;
import com.faceshow.common.utils.R;
import com.faceshow.modules.pay.entity.RechargeNorm;
import com.faceshow.modules.pay.service.RechargeNormService;
import com.faceshow.modules.pay.service.RechargeOrderService;
import com.faceshow.modules.sys.controller.AbstractController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import java.math.BigDecimal;

/**
 * appStore苹果内支付验证接口
 */
@RestController
public class AppStoreController extends AbstractController {
    //购买凭证验证地址
    private final String certificateUrl = "https://buy.itunes.apple.com/verifyReceipt";

    //测试的购买凭证验证地址
    private final String certificateUrlTest = "https://sandbox.itunes.apple.com/verifyReceipt";

    @Autowired
    private RechargeOrderService rechargeOrderService;
    @Autowired
    RechargeNormService rechargeNormService;
    /**
     * 接收iOS端发过来的购买凭证
     * @param userId
     * @param receipt
     */
    @SysLog
    @RequestMapping("/tokens/pay/appPayCertificate")
    public Object appPayCertificate(Integer userId, String receipt, int environment){
        String url = environment==0 ? certificateUrlTest: certificateUrl;
        logger.info("AppStore appPayCertificate：environment:{},userId:{},receipt:{}", environment, userId, receipt);

        final String certificateCode = receipt;
        if(StringUtils.isNotEmpty(certificateCode)){

            JSONObject obj = new JSONObject();
            obj.put("receipt-data", certificateCode);
            String reStr = new HttpsUtils().sendPOSTHttpsCoon(url, obj);
            JSONObject reJson = JSONObject.parseObject(reStr);

            if (reJson == null || reJson.getInteger("status") != 0){
                return R.error("支付验证失败"+ reJson.getInteger("status"));
            } else {
                JSONObject receiptJson = reJson.getJSONObject("receipt");
                JSONObject in_app = receiptJson.getJSONArray("in_app").getJSONObject(0);

                String transaction_id= in_app.getString("transaction_id");
                String product_id = in_app.getString("product_id");


                int typeId = 6;
                RechargeNorm rechargeNormById = rechargeNormService.getRechargeNormBySign(product_id);

                if (rechargeNormById == null ) return R.error("支付验证失败");
                BigDecimal price = rechargeNormById.getPriceAmerica();
                return rechargeOrderService.saveRechargeOrder(typeId, rechargeNormById.getRechargeId(), userId, price, "aps" + transaction_id );
            }

        }else{
            return R.error("支付验证失败");
        }
    }



}
