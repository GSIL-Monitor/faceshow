package com.faceshow.modules.user.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserRechargeLog implements Serializable {
    /**
     * 推荐ID主键
     */
    private Integer rechargeId;

    /**
     * 会员ID主键
     */
    private Integer userId;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 充值金额
     */
    private BigDecimal money;

    /**
     * 充值状态
     */
    private Boolean statues;

    private static final long serialVersionUID = 1L;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Boolean getStatues() {
        return statues;
    }

    public void setStatues(Boolean statues) {
        this.statues = statues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rechargeId=").append(rechargeId);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", money=").append(money);
        sb.append(", statues=").append(statues);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}