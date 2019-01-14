package com.faceshow.modules.IM.Controller;

import com.faceshow.common.utils.HttpClientUtil;
import com.faceshow.common.utils.JsonUtils;
import com.faceshow.modules.IM.Imutils;
import com.tls.sigcheck.tls_sigcheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 这个类的方法主要是注册时候 和封号的时候使用
 * IM采用的是独立模式 也就说用户的保存等都是我们自己处理 TX不负责处理 我们这个类主要是账号的导入
 * 独立模式账号导入接口
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.IM.Controller<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/1/29 16:52
 * -------------------------------------------------------------- <br>
 */
@Component
public class ImImportController {

    private static final Logger logger = LoggerFactory.getLogger(ImImportController.class);

    /**
     * 加载签名文件
     */
    private tls_sigcheck tls = new tls_sigcheck();
    /**
     * 加载秘钥文件
     */
    private File priKeyFile = null;

    public ImImportController() {
        try {
            if ("\\".equals(File.separator)) {
                tls.loadJniLib(new ClassPathResource("jnisigcheck.dll").getFile().getAbsolutePath());
            } else if ("/".equals(File.separator)) {
                tls.loadJniLib(new ClassPathResource("jnisigcheck.so").getFile().getAbsolutePath());
            }
            priKeyFile = new ClassPathResource("private_key").getFile();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("jnisigcheck.so loding error: ", e);
        }
    }

    /**
     * 独立模式账号导入接口  注册的时候请调用这个接口 把
     * 该接口的功能是在腾讯内部创建一个内部ID，使没有登录腾讯云的应用自有帐号能够使用腾讯云服务。
     * 举个例子：APP开发者通过腾讯云服务给APP用户A发送一条消息，
     * 用户A如果没有登录过云通信服务，由于腾讯内部没有用户A对应的内部ID，那么给用户A发送消息将会失败。此时需要把用户A的帐号同步给腾讯，
     * 在腾讯内部会为用户A创建一个内部ID，这样就可以给用户A发送消息了。
     *
     * @Author:YS
     * @Description: 传递参数
     * Identifier 	String 	必填 	用户名，长度不超过 32 字节
     * Nick 	String 	选填 	用户昵称
     * FaceUrl 	String 	选填 	用户头像URL。
     * Type 	Integer 	选填 	帐号类型，开发者默认无需填写，值0表示普通帐号，1表示机器人帐号。
     * @Date:2018/1/29
     */

    public Object accountImport(Map<String, Object> map) {
        //业务API独立模式账号导入接口
        String BUSINESS = "v4/im_open_login_svc/account_import";
        //拼接URL
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;
        String s = JsonUtils.objectToJson(map);
        logger.info("注册腾讯云请求参数: " + s);
        //发送请求
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));
        logger.info("注册腾讯云返回结果: " + date);
        return date;
    }

    /**
     * 本接口适用于将应用帐号的登录态（比如UserSig）失效。
     * 比如：开发者判断一个用户为恶意帐号后，可以调用本接口将该用户当前的登录态失效。
     * 封号接口
     *
     * @Author:YS
     * @Description:传递参数 Identifier    String 	必填 	用户名
     * @Date:2018/1/29
     */

    public Object kick(@RequestParam Map<String, Object> map) {
        //业务API 帐号登录态失效接口
        String BUSINESS = "v4/im_open_login_svc/kick";
        //拼接URL
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;
        //发送请求
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));
        return date;
    }

    /**
     * 生成腾讯签名
     *
     * @param appid      应用id
     * @param Identifier 用户名(用户id)
     * @return 签名
     * @throws Exception
     */
    public String createTls(String appid, String Identifier) throws Exception {
        StringBuilder strBuilder = new StringBuilder();
        String s = "";

        // 读取秘钥信息
        BufferedReader br = new BufferedReader(new FileReader(priKeyFile));
        while ((s = br.readLine()) != null) {
            strBuilder.append(s + '\n');
        }
        br.close();
        String priKey = strBuilder.toString();
        // 生成签名信息
        int ret = tls.tls_gen_signature_ex2(appid, Identifier, priKey);

        if (0 != ret) {
            System.out.println("ret " + ret + " " + tls.getErrMsg());
            return null;
        } else {
            System.out.println("sig:\n" + tls.getSig());
            return tls.getSig();
        }
    }

    public static void main(String[] args) throws Exception {
        // 向腾讯云注册账号
        /*Map<String, Object> accountImport = new HashMap<String, Object>();
        accountImport.put("Identifier", "42");
        accountImport.put("Nick", "");
        accountImport.put("Type", 0);

        Object o = ImImportController.accountImport(accountImport);
        System.out.println(o);
        Map<String, Object> parse = (Map<String, Object>) JSON.parse(o.toString());

        System.out.println(parse.get("ErrorCode"));

        String s = createTls("1400063998", "42");
        System.out.println(s);*/

        System.out.println(File.pathSeparator);
        System.out.println(File.pathSeparatorChar);
        System.out.println("\\".equals(File.separator));
        System.out.println(File.separatorChar);

    }
}
