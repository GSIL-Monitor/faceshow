package com.faceshow.common.utils.alipay;

/**
 * 短信签名模板
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/11 18:20
 * -------------------------------------------------------------- <br>
 */
public class SmsKeys {

    /**
     * 软件名称
     */
    public static final String APP_NAME = "facecast";

    /**
     * 国内版短信签名
     */
    public static final String DOMESTIC_SIGNATURE = "【河南国超】";

    /**
     * 国外版短信签名
     */
    public static final String FOREIGN_SIGNATURES = "【Facecast】";

    /**
     * 国内版短信正文内容
     */
    public static class Domestic {

        /**
         * 信息变更验证码
         *
         * @param code 验证码
         * @return
         */
        public static String accountUpgrades(Integer code) {
            return DOMESTIC_SIGNATURE + "验证码" + code + "，您正在尝试变更" + APP_NAME + "重要信息，请妥善保管账户信息。";
        }

        /**
         * 修改密码验证码
         *
         * @param code 验证码
         * @return
         */
        public static String paswordReset(Integer code) {
            return DOMESTIC_SIGNATURE + "验证码" + code + "，您正在尝试修改" + APP_NAME + "登录密码，请妥善保管账户信息。";
        }

        /**
         * 活动确认验证码
         *
         * @param code       验证码
         * @param activities 活动名称
         * @return
         */
        public static String activityReminders(Integer code, String activities) {
            return DOMESTIC_SIGNATURE + "验证码" + code + "，您正在参加" + APP_NAME + "的" + activities + "活动，请确认系本人申请。";
        }

        /**
         * 用户注册验证码
         *
         * @param code 验证码
         * @return
         */
        public static String registered(Integer code) {
            return DOMESTIC_SIGNATURE + "验证码" + code + "，您正在注册成为" + APP_NAME + "用户，感谢您的支持！";
        }

        /**
         * 登录异常验证码
         *
         * @param code 验证码
         * @return
         */
        public static String loginAlerts(Integer code) {
            return DOMESTIC_SIGNATURE + "验证码" + code + "，您正尝试异地登录" + APP_NAME + "，若非本人操作，请勿泄露";
        }

        /**
         * 登录确认验证码
         *
         * @param code 验证码
         * @return
         */
        public static String login(Integer code) {
            return DOMESTIC_SIGNATURE + "验证码" + code + "，您正在登录" + APP_NAME + "，若非本人操作，请勿泄露。";
        }

        /**
         * 身份验证验证码
         *
         * @param code 验证码
         * @return
         */
        public static String verifyIdentity(Integer code) {
            return DOMESTIC_SIGNATURE + "验证码" + code + "，您正在进行" + APP_NAME + "身份验证，若非本人操作，请勿泄露。";
        }

        /**
         * 注册
         *
         * @param code    验证码
         * @param minutes 分钟数
         * @return
         */
        public static String signUp(Integer code, Integer minutes) {
            return DOMESTIC_SIGNATURE + code + "为Facecast的验证码,仅用于注册,请于" + minutes + "分钟内填写,请勿告知他人,如有疑问联系客服。";
        }
    }

    /**
     * 国外版短信正文内容
     */
    public static class Foreign {

        /**
         * 信息变更验证码
         *
         * @param code 验证码
         * @return
         */
        public static String accountUpgrades(Integer code) {
            return FOREIGN_SIGNATURES + code + " is your " + APP_NAME + " Account Upgrades Verification code, Keep your account information safe.";
        }

        /**
         * 修改密码验证码
         *
         * @param code 验证码
         * @return
         */
        public static String paswordReset(Integer code) {
            return FOREIGN_SIGNATURES + code + " is your " + APP_NAME + " Password Reset Verification code, Keep your account information safe.";
        }

        /**
         * 活动确认验证码
         *
         * @param code       验证码
         * @param activities 活动名称
         * @return
         */
        /*public static String activityReminders(Integer code, String activities) {
            return FOREIGN_SIGNATURES + "验证码" + code + "您正在参加" + APP_NAME + "的" + activities + "活动，请确认系本人申请。";
        }*/

        /**
         * 用户注册验证码
         *
         * @param code 验证码
         * @return
         */
       /* public static String registered(Integer code) {
            return FOREIGN_SIGNATURES + "验证码" + code + "您正在注册成为" + APP_NAME + "用户，感谢您的支持！";
        }*/

        /**
         * 登录异常验证码
         *
         * @param code 验证码
         * @return
         */
        public static String loginAlerts(Integer code) {
            return FOREIGN_SIGNATURES + code + " is your " + APP_NAME + " Login Alerts Verification code.";
        }

        /**
         * 登录确认验证码
         *
         * @param code 验证码
         * @return
         */
        public static String login(Integer code) {
            return FOREIGN_SIGNATURES + code + " is your " + APP_NAME + " Login Verification code.";
        }

        /**
         * 身份验证验证码
         *
         * @param code 验证码
         * @return
         */
        public static String verifyIdentity(Integer code) {
            return FOREIGN_SIGNATURES + code + " is your " + APP_NAME + " Identity Verification code.";
        }

        /**
         * 注册
         *
         * @param code    验证码
         * @param minutes 分钟数
         * @return
         */
        public static String signUp(Integer code, Integer minutes) {
            return FOREIGN_SIGNATURES + code + " is the verification code of Facecast. It is only for registration. Please fill in within " + minutes + " minutes. Please do not inform others. If you have any questions, please contact customer service.";
        }
    }

}
