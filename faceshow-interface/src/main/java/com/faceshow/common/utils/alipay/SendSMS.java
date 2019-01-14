package com.faceshow.common.utils.alipay;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.BizResult;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * 短信发送工具类
 *
 * @author wsw
 */
public class SendSMS {

    private static final String CorpID = "ZZJS002814";
    private static final String Pwd = "zm0513@";
    private static final String URL = "https://sdk2.028lk.com/sdk2/BatchSend2.aspx";

    private static final String APPKEY = "23306358";
    private static final String SECRET = "f1deb7174baaddd568944b4b3767aa01";
    private static final String SEND_URL = "http://gw.api.taobao.com/router/rest";

    /**
     * 阿里大鱼发送短信验证码
     *
     * @param mobile       手机号码
     * @param code         验证码
     * @param signName     签名名称
     * @param templateCode 短信模板
     * @return
     */
    public static BizResult sendMsg(String mobile, String code, String signName, String templateCode) {
        TaobaoClient client = new DefaultTaobaoClient(SEND_URL, APPKEY, SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("");
        req.setSmsType("normal");
        req.setSmsFreeSignName(signName);
        req.setSmsParamString("{\"code\":\"" + code + "\",\"product\":\"faceshow\"}");
        req.setRecNum(mobile);
        req.setSmsTemplateCode(templateCode);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp);
        return rsp.getResult();
    }

    /**
     * 腾讯发送短信配置
     */
    private static final Integer TENCENT_APPID = 1400082991;
    private static final String TENCENT_APPKEY = "9b1146b284e5b8eb2c448a3763fe59b2";

    /**
     * 使用腾讯云发送短信
     *
     * @param type        短信类型，0 为普通短信，1 营销短信
     * @param nationCode  国家码，如 86 为中国
     * @param phoneNumber 不带国家码的手机号
     * @param msg         信息内容，必须与申请的模板格式一致，否则将返回错误
     * @return
     */
    public static SmsSingleSenderResult sendMsg(Integer type, String nationCode, String phoneNumber, String msg) {
        try {
            // 初始化发送短信对象
            SmsSingleSender sender = new SmsSingleSender(TENCENT_APPID, TENCENT_APPKEY);
            SmsSingleSenderResult result = sender.send(type, nationCode, phoneNumber, msg, "", "");
            System.out.println(result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("send error");
        }
    }

    /**
     * @param phone    电话，字符串格式，多个用  “,” 隔开
     * @param content  短信内容 eg:验证码为：7901，您正在注册伴知旅行，请在5分钟内完成验证
     * @param sendTime 固定14位长度字符串，比如：20060912152435代表2006年9月12日15时24分35秒，为空表示立即发送
     * @return int   大于0的数字提交成功,–1账号未注册,–2其他错误,–3帐号或密码错误,–5余额不足，请充值,-9发送号码为空
     * @throws UnsupportedEncodingException
     * @Description:短信发送工具类方法
     * @author wsw
     * @date 2017-7-19 下午2:14:02
     */
    public static int sendMessage(String phone, String content, String sendTime) throws UnsupportedEncodingException {
        String inputLine = "";
        int value = -2;
        String send_content = URLEncoder.encode(content.replaceAll("<br/>", " "), "GBK");// 发送内容
        String param = "CorpID=" + CorpID + "&Pwd=" + Pwd + "&Mobile=" + phone + "&Content=" + send_content + "&Cell=&SendTime=" + sendTime;
        try {
            inputLine = sendPost(URL, param);
            //System.out.println("开始发送短信手机号码为 ：" + phone);
            value = new Integer(inputLine).intValue();
        } catch (Exception e) {
            //System.out.println("网络异常,发送短信失败！");
            value = -2;
        }
        //System.out.println(String.format("返回值：%d", value));
        return value;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    private static String sendPost(String url, String param) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
