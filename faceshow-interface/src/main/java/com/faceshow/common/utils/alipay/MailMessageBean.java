package com.faceshow.common.utils.alipay;

import java.io.Serializable;

/**
 * 发送邮件信息存储
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/28 19:58
 * -------------------------------------------------------------- <br>
 */
public class MailMessageBean  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 邮件地址
     */
    private String email;
    /**
     * 邮件标题
     */
    private String tital;
    /**
     * 邮件内容
     */
    private String content;

    public MailMessageBean() {
    }

    public MailMessageBean(String email, String tital, String content) {
        this.email = email;
        this.tital = tital;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTital() {
        return tital;
    }

    public void setTital(String tital) {
        this.tital = tital;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MailMessageBean{" +
                "email='" + email + '\'' +
                ", tital='" + tital + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
