package com.faceshow.common.utils.alipay;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

/**
 * qq企业邮箱发送邮件工具类
 *
 * @Athor GuoChao
 * @Date Create in 11:27 2018/1/16
 */
public class MailUtils {
    private static String account = "song@gchao.com";    //登录用户名
    private static String pass = "VHXydjBAL7hxemGy";     //登录密码

    static class MyAuthenricator extends Authenticator {
        String u = null;
        String p = null;

        public MyAuthenricator(String u, String p) {
            this.u = u;
            this.p = p;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(u, p);
        }
    }

    /**
     * 发送腾讯企业邮箱
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public static void sendMail(String to, String subject, String content) {
        Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", "smtp");
        //服务器
        prop.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        //端口
        prop.setProperty("mail.smtp.port", "465");
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getDefaultInstance(prop, new MyAuthenricator(account, pass));
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            //发件人
            mimeMessage.setFrom(new InternetAddress(account,"facecast"));    //如果不需要就省略
            //收件人
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //主题
            mimeMessage.setSubject(subject);
            //时间
            mimeMessage.setSentDate(new Date());
            //容器类，可以包含多个MimeBodyPart对象
            Multipart mp = new MimeMultipart();

            //MimeBodyPart可以包装文本，图片，附件
            MimeBodyPart body = new MimeBodyPart();
            //HTML正文
            body.setContent(content, "text/html; charset=UTF-8");
            mp.addBodyPart(body);

            //设置邮件内容
            mimeMessage.setContent(mp);
            //仅仅发送文本
            //mimeMessage.setText(content);
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        sb.append("<p>***这是一个自动生成的电子邮件，请不要回复。</p>");
        sb.append("要更改密码，请单击此处：<br/>");
        sb.append("<a href='http://192.168.1.26:8083/faceshow/api/user/register/emailPage.html?email=1530696613@qq.com'><button>更改密码</button></a><br/>");
        sb.append("如果你有麻烦，点击“更改密码”按钮，请复制并粘贴<a href='http://192.168.1.26:8083/faceshow/api/user/register/emailPage.html?email=1530696613@qq.com'>http://192.168.1.26:8083/faceshow/api/user/register/emailPage.html?email=1530696613@qq.com</a>到您的网页浏览器。");
        sb.append("<br/>如果您没有请求此更改，请忽略此消息。");
       //  sendMail("1530696613@qq.com", "faceshow重置密码", sb.toString());
    }
}
