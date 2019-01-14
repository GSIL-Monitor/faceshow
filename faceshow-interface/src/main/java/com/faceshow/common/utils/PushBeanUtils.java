package com.faceshow.common.utils;

import java.io.Serializable;

/**
 * 极光推送消息Utils
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 16:39
 * -------------------------------------------------------------- <br>
 */
public class PushBeanUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 推送信息类型主键
     */
    private Integer infoId;

    /**
     * 推送人主键
     */
    private Integer userId;

    /**
     * 接收人
     */
    private Integer account;

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public PushBeanUtils(Integer infoId, Integer userId, Integer account) {
        this.infoId = infoId;
        this.userId = userId;
        this.account = account;
    }

    public PushBeanUtils(Integer userId) {
        this.userId = userId;
    }

    public PushBeanUtils() {
    }

    public PushBeanUtils(Integer infoId, Integer userId) {
        this.infoId = infoId;
        this.userId = userId;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PushBeanUtils{" +
                "infoId=" + infoId +
                ", userId=" + userId +
                '}';
    }
}
