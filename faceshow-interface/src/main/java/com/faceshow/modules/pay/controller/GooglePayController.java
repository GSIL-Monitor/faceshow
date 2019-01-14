package com.faceshow.modules.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.HttpsUtils;
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.weixinpay.PropertyUtil;
import com.faceshow.modules.pay.entity.RechargeNorm;
import com.faceshow.modules.pay.service.RechargeNormService;
import com.faceshow.modules.pay.service.RechargeOrderService;
import com.faceshow.modules.sys.controller.AbstractController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * GooglePay  googleapp内支付
 */
@RestController
public class GooglePayController extends AbstractController {

    @Autowired
    private RechargeOrderService rechargeOrderService;
    @Autowired
    RechargeNormService rechargeNormService;

    private final String publicKey = PropertyUtil.getInstance().getProperty("ali.PRIVATE_KEY");

    /**
     * 接收iOS端发过来的购买凭证
     * @param userId
     * @param signtureData
     */
    @SysLog
    @RequestMapping("/tokens/pay/googlePayCertificate")
    public Object appPayCertificate(Integer userId, String signtureData, String signture){
        logger.info("googlePay appPayCertificate：userId:{},signtureData:{},signture:{}", userId, signtureData, signture);
        if(StringUtils.isNotEmpty(signtureData) && StringUtils.isNotEmpty(signture)
                && docheck(signtureData, signture, publicKey)){

            JSONObject reJson = JSONObject.parseObject(signtureData);

            String orderId = reJson.getString("orderId");
            String productId = reJson.getString("productId");

            int typeId = 7;

            RechargeNorm rechargeNormById = rechargeNormService.getRechargeNormBySign(productId);
            if (rechargeNormById == null ) return R.error("支付验证失败");
            BigDecimal price = rechargeNormById.getPriceAmerica();
            return rechargeOrderService.saveRechargeOrder(typeId, rechargeNormById.getRechargeId(), userId, price, "g" + orderId );

        }else{
            return R.error("支付验证失败");
        }
    }

    private boolean docheck(String content, String sign, String publicKey){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.getDecoder().decode(publicKey);
            PublicKey pubKey = keyFactory
                    .generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes("utf-8"));
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (Exception  e){
            e.printStackTrace();
            return false;
        }

    }

}
