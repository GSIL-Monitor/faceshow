package com.faceshow.modules.pay.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RechargeOrder implements Serializable {
    /**
     * 订单ID主键
     */
    private Integer orderId;

    /**
     * 支付类型
     */
    private Integer typeId;

    /**
     * 规格ID主键
     */
    private Integer rechargeId;

    /**
     * 会员ID主键
     */
    private Integer userId;

    /**
     * 订单金额
     */
    private BigDecimal price;

    /**
     * 订单状态 0 失败 1 支付成功
     */
    private Integer statues;

    /**
     * 支付状态
     */
    private Integer payType;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Integer rechargeId) {
        this.rechargeId = rechargeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatues() {
        return statues;
    }

    public void setStatues(Integer statues) {
        this.statues = statues;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
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
        sb.append(", orderId=").append(orderId);
        sb.append(", typeId=").append(typeId);
        sb.append(", rechargeId=").append(rechargeId);
        sb.append(", userId=").append(userId);
        sb.append(", price=").append(price);
        sb.append(", statues=").append(statues);
        sb.append(", payType=").append(payType);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}