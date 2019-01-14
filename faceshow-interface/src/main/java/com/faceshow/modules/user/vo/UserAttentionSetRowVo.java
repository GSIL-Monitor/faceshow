package com.faceshow.modules.user.vo;

import java.io.Serializable;

/**
 * 查询用户粉丝及推送设置信息
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/25 13:20
 * -------------------------------------------------------------- <br>
 */
public class UserAttentionSetRowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 推送者id
     */
    private String userId;
    /**
     * 接受者id
     */
    private String accountId;
    /**
     * 设置状态
     */
    private Integer videoSet;

    public UserAttentionSetRowVo() {
    }

    public UserAttentionSetRowVo(String userId, String accountId, Integer videoSet) {
        this.userId = userId;
        this.accountId = accountId;
        this.videoSet = videoSet;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getVideoSet() {
        return videoSet;
    }

    public void setVideoSet(Integer videoSet) {
        this.videoSet = videoSet;
    }

    @Override
    public String toString() {
        return "UserAttentionSetRowVo{" +
                "userId=" + userId +
                ", accountId=" + accountId +
                ", videoSet=" + videoSet +
                '}';
    }
}
