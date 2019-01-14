package com.faceshow.common.utils.alipay;

/**
 * 定义发送邮件内容
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/11 15:30
 * -------------------------------------------------------------- <br>
 */
public class MailKeys {

    /**
     * 注册发送激活邮件
     */
    public static class ActiveEmail {

        public static final String TITLE = "Facecast Email Verification";

        /**
         * 激活邮箱
         *
         * @param email 邮箱
         * @param code  token令牌
         * @return
         */
        public static String activeEmail(String email, String code) {
            StringBuilder sb = new StringBuilder();
            // 英文信息
            sb.append("<p>Congratulations, you have successfully registered an Facecast account!</p>");
            sb.append("<p>Before we can activate your account, we need to verify your email address.</p>");
            sb.append("<p>Please click on the following link.</p><br/>");
            sb.append("<a href='http://192.168.1.26:8083/faceshow/api/page/activateEmail.html?email=" + email + "&token=" + code + "'><button>Activate Now</button></a><br/>");
            sb.append("If your mail client does not allow you to click the link, then copy and paste it into your web browser.");
            sb.append("<a href='http://192.168.1.26:8083/faceshow/api/page/activateEmail.html?email=" + email + "&token=" + code + "'>http://192.168.1.26:8083/faceshow/api/page/activateEmail.html?email=" + email + "&token=" + code + "</a>");
            sb.append("<br/>If you did not request to register, please ignore this e-mail.");
            sb.append("<br/>*This is system message and please do not reply");

            // 换行
            sb.append("<br/><br/><br/>");

            // 中文信息
            sb.append("<p>***这是一个自动生成的电子邮件，请不要回复。</p>");
            sb.append("要激活邮箱，请单击此处：<br/>");
            sb.append("<a href='http://192.168.1.26:8083/faceshow/api/page/activateEmail.html?email=" + email + "&token=" + code + "'><button>Activate Now</button></a><br/>");
            sb.append("如果你有麻烦，请复制并粘贴<a href='http://192.168.1.26:8083/faceshow/api/page/activateEmail.html?email=" + email + "&token=" + code + "'>http://192.168.1.26:8083/faceshow/api/page/activateEmail.html?email=" + email + "&token=" + code + "</a>到您的网页浏览器。");
            sb.append("<br/>如果您没有请求此更改，请忽略此消息。");
            return sb.toString();
        }

    }

    /**
     * 重置密码
     */
    public static class ChangePassword {

        public static final String TITLE = "Facecast Password Reset Request";

        /**
         * 发送链接内容
         *
         * @param email 邮箱
         * @param code  token令牌
         * @return
         */
        public static String changePwd(String email, String code) {
            StringBuilder sb = new StringBuilder();
            // 英文信息
            sb.append("<p>Someone requested that your Facecast password be reset.</p>");
            sb.append("<p>If this wasn't you, there's nothing to worry about - simply ignore this email and nothing will change.</p>");
            sb.append("<p>If you DID ask to reset the password on your Facecast account, just click here to make it happen:</p><br/>");
            sb.append("<a href='http://192.168.1.26:8083/faceshow/api/page/emailPage.html?email=" + email + "&token=" + code + "'><button>Facecast Password Reset Request</button></a><br/>");
            sb.append("If your mail client does not allow you to click the link, then copy and paste it into your web browser.");
            sb.append("<a href='http://192.168.1.26:8083/faceshow/api/page/emailPage.html?email=" + email + "&token=" + code + "'>http://192.168.1.26:8083/faceshow/api/page/emailPage.html?email=" + email + "&token=" + code + "</a>");
            sb.append("<br/>*This is system message and please do not reply");
            sb.append("<br/>Thanks.");

            // 换行
            sb.append("<br/><br/><br/>");

            // 中文信息
            sb.append("<p>***这是一个自动生成的电子邮件，请不要回复。</p>");
            sb.append("要更改密码，请单击此处：<br/>");
            sb.append("<a href='http://192.168.1.26:8083/faceshow/api/page/emailPage.html?email=" + email + "&token=" + code + "'><button>更改密码</button></a><br/>");
            sb.append("如果你有麻烦，点击“更改密码”按钮，请复制并粘贴<a href='http://192.168.1.26:8083/faceshow/api/page/emailPage.html?email=" + email + "&token=" + code + "'>http://192.168.1.26:8083/faceshow/api/page/emailPage.html?email=" + email + "&token=" + code + "</a>到您的网页浏览器。");
            sb.append("<br/>如果您没有请求此更改，请忽略此消息。");
            return sb.toString();
        }
    }

    /**
     * 修改绑定邮箱
     */
    public static class ChangeEmail {

        public static final String TITLE = "Facecast Email Reset Request";

        /**
         * 发送链接内容
         *
         * @param email 邮箱
         * @param code  token令牌
         * @return
         */
        public static String changeEmail(String email, String code) {
            StringBuilder sb = new StringBuilder();
            // 英文信息
            sb.append("<p>Someone requested that your Facecast email be reset.</p>");
            sb.append("<p>If this wasn't you, there's nothing to worry about - simply ignore this email and nothing will change.</p>");
            sb.append("<p>If you DID ask to reset the email on your Facecast account, just click here to make it happen:</p><br/>");
            sb.append("<a href='http://192.168.1.26:8083/faceshow/api/page/changeEmail.htm?email=" + email + "&token=" + code + "'><button>Facecast Email Reset Request</button></a><br/>");
            sb.append("If your mail client does not allow you to click the link, then copy and paste it into your web browser.");
            sb.append("<a href='http://192.168.1.26:8083/faceshow/api/page/changeEmail.html?email=" + email + "&token=" + code + "'>http://192.168.1.26:8083/faceshow/api/page/changeEmail.html?email=" + email + "&token=" + code + "</a>");
            sb.append("<br/>*This is system message and please do not reply");
            sb.append("<br/>Thanks.");

            // 换行
            sb.append("<br/><br/><br/>");

            // 中文信息
            sb.append("<p>***这是一个自动生成的电子邮件，请不要回复。</p>");
            sb.append("要修改绑定邮箱，请单击此处：<br/>");
            sb.append("<a href='http://192.168.1.26:8083/faceshow/api/page/changeEmail.html?email=" + email + "&token=" + code + "'><button>绑定邮箱</button></a><br/>");
            sb.append("如果你有麻烦，点击“绑定邮箱”按钮，请复制并粘贴<a href='http://192.168.1.26:8083/faceshow/api/page/changeEmail.html?email=" + email + "&token=" + code + "'>http://192.168.1.26:8083/faceshow/api/page/changeEmail.html?email=" + email + "&token=" + code + "</a>到您的网页浏览器。");
            sb.append("<br/>如果您没有请求此更改，请忽略此消息。");
            return sb.toString();
        }
    }
}
