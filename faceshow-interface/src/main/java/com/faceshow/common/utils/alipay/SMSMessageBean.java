package com.faceshow.common.utils.alipay;

import java.io.Serializable;

/**
 * 发送短信信息存储
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/28 19:58
 * -------------------------------------------------------------- <br>
 */
public class SMSMessageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 短信类型
     */
    private Integer type;
    /**
     * 国际区号
     */
    private String areaCode;
    /**
     * 电话号码
     */
    private String mobile;
    /**
     * 短信内容
     */
    private String content;

    public SMSMessageBean() {
    }

    public SMSMessageBean(Integer type, String areaCode, String mobile, String content) {
        this.type = type;
        this.areaCode = areaCode;
        this.mobile = mobile;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SMSMessageBean{" +
                "type=" + type +
                ", areaCode='" + areaCode + '\'' +
                ", mobile='" + mobile + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
