package com.faceshow.modules.user.vo;

import java.io.Serializable;

/**
 * 返回附近的人
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/10 13:10
 * -------------------------------------------------------------- <br>
 */
public class UserInfoAroundRowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户头像
     */
    private String userImg;
    /**
     * 用户签名
     */
    private String signature;
    /**
     * 是否已经关注该用户
     */
    private Integer isTutual;

    /**
     * 距离
     */
    private Integer length;

    /**
     * 登录时间
     */
    private String loginTime;

    public UserInfoAroundRowVo() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName == null ? "" : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserImg() {
        return userImg == null ? "" : userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getSignature() {
        return signature == null ? "" : signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getIsTutual() {
        return isTutual == null ? 0 : isTutual;
    }

    public void setIsTutual(Integer isTutual) {
        this.isTutual = isTutual;
    }

    public Integer getLength() {
        return length == null ? 0 : length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getLoginTime() {
        return loginTime == null ? "" : loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "UserInfoAroundRowVo{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", userImg='" + userImg + '\'' +
                ", signature='" + signature + '\'' +
                ", isTutual=" + isTutual +
                ", length='" + length + '\'' +
                ", loginTime='" + loginTime + '\'' +
                '}';
    }
}
