package com.faceshow.modules.qa.vo;

import java.io.Serializable;

/**
 * 前三名
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/3 10:22
 * -------------------------------------------------------------- <br>
 */
public class QaAmountSetSelectRowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer infoId;
    private Integer userId;
    private String nickName;
    private Long createTime;
    private Integer days;
    private Integer price;
    private Integer twoPrice;
    private Integer threePrice;

    public QaAmountSetSelectRowVo() {
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTwoPrice() {
        return twoPrice;
    }

    public void setTwoPrice(Integer twoPrice) {
        this.twoPrice = twoPrice;
    }

    public Integer getThreePrice() {
        return threePrice;
    }

    public void setThreePrice(Integer threePrice) {
        this.threePrice = threePrice;
    }

    @Override
    public String toString() {
        return "QaAmountSetSelectRowVo{" +
                "infoId=" + infoId +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", createTime=" + createTime +
                ", days=" + days +
                ", price=" + price +
                ", twoPrice=" + twoPrice +
                ", threePrice=" + threePrice +
                '}';
    }
}
