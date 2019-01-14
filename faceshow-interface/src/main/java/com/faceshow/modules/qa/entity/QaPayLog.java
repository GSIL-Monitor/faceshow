package com.faceshow.modules.qa.entity;

import java.io.Serializable;
import java.util.Date;

public class QaPayLog implements Serializable {
    /**
     * 问答支付日志主键ID
     */
    private Integer logId;

    /**
     * 问答信息ID主键
     */
    private Integer infoId;

    /**
     * 支付金额
     */
    private Integer price;

    /**
     * 接收人
     */
    private Integer accountId;

    /**
     * 支付人
     */
    private Integer userId;

    /**
     * 支付时间
     */
    private Date createTime;

    /**
     * 支付状态 0 未支付 1 已支付
     */
    private Byte payType;

    private static final long serialVersionUID = 1L;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", infoId=").append(infoId);
        sb.append(", price=").append(price);
        sb.append(", accountId=").append(accountId);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", payType=").append(payType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}