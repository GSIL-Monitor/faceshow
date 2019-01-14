package com.faceshow.modules.user.entity;

import java.io.Serializable;
import java.util.Date;

public class UserAttention implements Serializable {
    /**
     * 关注ID主键
     */
    private Integer attentionId;

    /**
     * 会员ID主键
     */
    private Integer userId;

    /**
     * 被关注人
     */
    private Integer account;

    /**
     * 关注时间
     */
    private Date createTime;

    /**
     * 拉黑 0 拉黑 1 正常
     */
    private Byte blackList;

    /**
     * 屏蔽朋友圈 0 屏蔽 1 正常
     */
    private Byte shieldCircle;

    /**
     * 是否互相关注 0 否 1 是
     */
    private Byte isTutual;

    /**
     * 距离
     */
    private String distance;

    private static final long serialVersionUID = 1L;

    public Integer getAttentionId() {
        return attentionId;
    }

    public void setAttentionId(Integer attentionId) {
        this.attentionId = attentionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getBlackList() {
        return blackList;
    }

    public void setBlackList(Byte blackList) {
        this.blackList = blackList;
    }

    public Byte getShieldCircle() {
        return shieldCircle;
    }

    public void setShieldCircle(Byte shieldCircle) {
        this.shieldCircle = shieldCircle;
    }

    public Byte getIsTutual() {
        return isTutual;
    }

    public void setIsTutual(Byte isTutual) {
        this.isTutual = isTutual;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance == null ? null : distance.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", attentionId=").append(attentionId);
        sb.append(", userId=").append(userId);
        sb.append(", account=").append(account);
        sb.append(", createTime=").append(createTime);
        sb.append(", blackList=").append(blackList);
        sb.append(", shieldCircle=").append(shieldCircle);
        sb.append(", isTutual=").append(isTutual);
        sb.append(", distance=").append(distance);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}