package com.faceshow.modules.pay.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.weixinpay.PayCommonUtil;
import com.faceshow.common.utils.weixinpay.PropertyUtil;
import com.faceshow.common.utils.weixinpay.XmlUtils;
import com.faceshow.modules.pay.entity.RechargeNorm;
import com.faceshow.modules.pay.service.RechargeNormService;
import com.faceshow.modules.pay.service.RechargeOrderService;
import com.faceshow.modules.sys.controller.AbstractController;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 微信app支付接口
 */
@RestController
public class WeixinAppController extends AbstractController {
    @Autowired
    RechargeNormService rechargeNormService;
    @Autowired
    RechargeOrderService rechargeOrderService;

    /**
     * 拉取微信预付单
     */
    @SysLog
    @RequestMapping("/tokens/wxPay/getWXPay")
    public Object getWXPay(HttpServletRequest httpRequest, String rechargeNormId, int userId) {
        RechargeNorm rechargeNorm = rechargeNormService.getRechargeNormBySign(rechargeNormId);
        if (rechargeNorm == null) return R.error("支付异常，请稍后再试");
        BigDecimal price = rechargeNorm.getPriceChina();
        if (price.doubleValue() <= 0) // 防止抓包修改订单金额造成损失
        {
            return R.error("支付出现异常，请稍后重试!");
        }
        try {
            SortedMap<Object, Object> parameters = PayCommonUtil.getWXPrePayID(); // 获取预付单，此处已做封装，需要工具类

            parameters.put("body", "pay");

            parameters.put("spbill_create_ip", httpRequest.getRemoteAddr());
            String format = String.format("%06d", userId);
            parameters.put("out_trade_no", "wx" + format + PayCommonUtil.getDateStr()); // 订单号
            parameters.put("total_fee", String.valueOf(price.multiply(new BigDecimal(100)).intValue())); // 金额
            parameters.put("attach", rechargeNormId+ "_" + userId); // 充值规格id

            // 设置签名
            String sign = PayCommonUtil.createSign("UTF-8", parameters);
            parameters.put("sign", sign);
            // 封装请求参数结束
            String requestXML = PayCommonUtil.getRequestXml(parameters); // 获取xml结果
            logger.debug("封装请求参数是：" + requestXML);
            // 调用统一下单接口
            String result = PayCommonUtil.httpsRequest(PropertyUtil.getInstance().getProperty("WxPay.payURL"), "POST",
                    requestXML);
            logger.debug("调用统一下单接口：" + result);
            Map<String, String> rem = XmlUtils.doXMLParse(result);
            String return_code =  rem.get("return_code");
            if (!"SUCCESS".equals(return_code)){
                return R.error("支付出现异常，请稍后重试!");
            }
            SortedMap<Object, Object> parMap = PayCommonUtil.startWXPay(result);
            logger.debug("最终的map是：" + parMap.toString());
            if (parMap != null) {
                return R.ok().put("result", parMap);
            } else {
                return R.error("支付出现异常，请稍后重试!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return R.error("支付出现异常，请稍后重试!");
        }
    }

    /**
     * 支付成功回调
     * @param request
     * @param response
     * @throws IOException
     * @throws JDOMException
     */
    @SysLog
    @RequestMapping("/api/wx/notify")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {
        String result = PayCommonUtil.reciverWx(request); // 接收到异步的参数
        Map<String, String> m = new HashMap<>();// 解析xml成map
        if (m != null && !"".equals(m)) {
            m = XmlUtils.doXMLParse(result);
        }
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 判断签名是否正确
        String resXml = "";
        if (PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
            if ("SUCCESS".equals((String) packageParams.get("return_code"))) {
                // 如果返回成功
                String mch_id = (String) packageParams.get("mch_id"); // 商户号
                String out_trade_no = (String) packageParams.get("out_trade_no"); // 商户订单号
                String total_fee = (String) packageParams.get("total_fee");
                String[] attach = packageParams.get("attach").toString().split("_");

                Integer rechargeNormId = Integer.parseInt(attach[0]);
                Integer userId = Integer.parseInt(attach[1]);

                RechargeNorm rechargeNorm = rechargeNormService.getRechargeNormById(rechargeNormId);

                // 验证商户ID 和 价格 以防止篡改金额
                if (PropertyUtil.getInstance().getProperty("WxPay.mchid").equals(mch_id)
                        ) {
                    //支付成功业务

                    rechargeOrderService.saveRechargeOrder(2, rechargeNormId, userId, rechargeNorm.getPriceChina(), out_trade_no);

                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                } else {
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
                }
            } else // 如果微信返回支付失败，将错误信息返回给微信
            {
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[交易失败]]></return_msg>" + "</xml> ";
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
        }

        // 处理业务完毕，将业务结果通知给微信
        // ------------------------------
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

}
