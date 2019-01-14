package com.faceshow.modules.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.alipay.AlipayConfig;
import com.faceshow.common.utils.weixinpay.PayCommonUtil;
import com.faceshow.modules.pay.entity.RechargeNorm;
import com.faceshow.modules.pay.service.RechargeNormService;
import com.faceshow.modules.pay.service.RechargeOrderService;
import com.faceshow.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝app支付接口
 */
@RestController
public class AliAppPayController extends AbstractController {

    @Autowired
    RechargeNormService rechargeNormService;
    @Autowired
    RechargeOrderService rechargeOrderService;


    @SysLog
    @RequestMapping(value = "/tokens/pay/alipay/signprams")
    public Object signprams(HttpServletRequest request, int userId, String rechargeNormId) {
        try {

            RechargeNorm rechargeNorm = rechargeNormService.getRechargeNormBySign(rechargeNormId);
            if (rechargeNorm == null) return R.error("支付异常，请稍后再试");

            String subject = "pay";
            String body = "pay";

            String format = String.format("%06d", userId);
            String out_trade_no = "al" + format + PayCommonUtil.getDateStr();

            //实例化客户端
            AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                    AlipayConfig.APP_ID, AlipayConfig.PRIVATE_KEY, "json", AlipayConfig.INPUT_CHARSET,
                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest alirequest = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setPassbackParams(URLEncoder.encode(body.toString()));
             //描述信息  添加附加数据
            model.setSubject(subject); //商品标题
            model.setOutTradeNo(out_trade_no); //商家订单编号
            model.setTimeoutExpress("30m"); //超时关闭该订单时间
            model.setTotalAmount(rechargeNorm.getPriceChina().toString());  //订单总金额
            model.setBusinessParams(rechargeNormId+"_" + userId);
            model.setProductCode("QUICK_MSECURITY_PAY"); //销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
            alirequest.setBizModel(model);
            alirequest.setNotifyUrl(AlipayConfig.notifyurl);  //回调地址
            String orderStr = "";
            try {
                //这里和普通的接口调用不同，使用的是sdkExecute
                AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alirequest);
                orderStr = response.getBody();
                return R.ok().put("result", orderStr);
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return R.error();
    }

    /**
     * 支付回调地址
     * @param request
     * @return
     */
    @SysLog
    @RequestMapping(value="/api/pay/alipay/notify",produces="application/json;charset=utf-8")
    public String notify(HttpServletRequest request){
        Map requestParams = request.getParameterMap();
        logger.info("支付宝支付结果通知"+requestParams.toString());
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<>();

        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com

        try {
            //验证签名
            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.INPUT_CHARSET, "RSA2");
            if(flag){
                if("TRADE_SUCCESS".equals(params.get("trade_status"))){
                    //付款金额
                    String amount = params.get("buyer_pay_amount");
                    //附加数据
                    String[] business_params = URLDecoder.decode(params.get("business_params")).split("_");
                    Integer rechargeNormId = Integer.parseInt(business_params[0]);
                    Integer userId = Integer.parseInt(business_params[1]);

                    RechargeNorm rechargeNorm = rechargeNormService.getRechargeNormById(rechargeNormId);

                    //商户订单号
                    String out_trade_no = params.get("out_trade_no");

                    rechargeOrderService.saveRechargeOrder(1, rechargeNormId, userId, rechargeNorm.getPriceChina(), out_trade_no);
                }
            }
        } catch (AlipayApiException e) {
            logger.error("error",e);
            e.printStackTrace();
        }
        return "success";
    }
}
