package com.faceshow.modules.user.entity;

import java.io.Serializable;
import java.util.Date;

public class BackList implements Serializable {
    /**
     * 黑名单ID主键
     */
    private Integer blackId;

    /**
     * 举办人
     */
    private Integer userId;

    /**
     * 被举报人
     */
    private Integer account;

    /**
     * 举报时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getBlackId() {
        return blackId;
    }

    public void setBlackId(Integer blackId) {
        this.blackId = blackId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", blackId=").append(blackId);
        sb.append(", userId=").append(userId);
        sb.append(", account=").append(account);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}