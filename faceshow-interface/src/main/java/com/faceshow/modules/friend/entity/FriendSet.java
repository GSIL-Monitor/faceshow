package com.faceshow.modules.friend.entity;

import java.io.Serializable;
import java.util.Date;

public class FriendSet implements Serializable {
    /**
     * 用户好友基本设计ID主键
     */
    private Integer setId;

    /**
     * 主用户ID主键
     */
    private Integer userId;

    /**
     * 被设置用户ID主键
     */
    private Integer accountId;

    /**
     * 是否查看他的动态 0 否 1 是
     */
    private Byte isShow;

    /**
     * 不接受直播通知 0 不接受 1 接受
     */
    private Byte isNotice;

    /**
     * 对他隐身 0 隐身 1 不隐身
     */
    private Byte stealth;

    /**
     * 1V1我想遇见他 0 否 1 是
     */
    private Byte lvl;

    /**
     * 设置时间
     */
    private Date setTime;

    private static final long serialVersionUID = 1L;

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Byte getIsShow() {
        return isShow;
    }

    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
    }

    public Byte getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(Byte isNotice) {
        this.isNotice = isNotice;
    }

    public Byte getStealth() {
        return stealth;
    }

    public void setStealth(Byte stealth) {
        this.stealth = stealth;
    }

    public Byte getLvl() {
        return lvl;
    }

    public void setLvl(Byte lvl) {
        this.lvl = lvl;
    }

    public Date getSetTime() {
        return setTime;
    }

    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", setId=").append(setId);
        sb.append(", userId=").append(userId);
        sb.append(", accountId=").append(accountId);
        sb.append(", isShow=").append(isShow);
        sb.append(", isNotice=").append(isNotice);
        sb.append(", stealth=").append(stealth);
        sb.append(", lvl=").append(lvl);
        sb.append(", setTime=").append(setTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}