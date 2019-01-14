package com.faceshow.modules.gift.entity;

import java.io.Serializable;

public class GiftUserInfo implements Serializable {
    /**
     * 用户拥有礼品ID主键
     */
    private Integer infoId;

    /**
     * 礼品ID主键
     */
    private Integer giftId;

    /**
     * 拥有人
     */
    private Integer userId;

    /**
     * 拥有个数 默认 1个
     */
    private Integer numbers;

    private static final long serialVersionUID = 1L;

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", infoId=").append(infoId);
        sb.append(", giftId=").append(giftId);
        sb.append(", userId=").append(userId);
        sb.append(", numbers=").append(numbers);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}