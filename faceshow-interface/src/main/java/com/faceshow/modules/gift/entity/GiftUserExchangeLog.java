package com.faceshow.modules.gift.entity;

import java.io.Serializable;
import java.util.Date;

public class GiftUserExchangeLog implements Serializable {
    /**
     * 兑换礼品日志ID主键
     */
    private Integer logId;

    /**
     * 兑换礼品ID主键
     */
    private Integer giftId;

    /**
     * 兑换人
     */
    private Integer userId;

    /**
     * 兑换个数 默认 1个
     */
    private Integer numbers;

    /**
     * 兑换时间
     */
    private Date logTime;

    private static final long serialVersionUID = 1L;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {
        this.numbers = numbers;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", giftId=").append(giftId);
        sb.append(", userId=").append(userId);
        sb.append(", numbers=").append(numbers);
        sb.append(", logTime=").append(logTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}