package com.faceshow.modules.qa.entity;

import java.io.Serializable;

public class QaAmountSet implements Serializable {
    /**
     * 悬赏金额设置ID主键
     */
    private Integer setId;

    /**
     * 问答信息ID主键
     */
    private Integer infoId;

    /**
     * 问答悬赏金额(解：指定最佳答案使用，前三名最佳答案使用下面是二和三名次的金额)
     */
    private Integer price;

    /**
     * 最佳答案第二名悬赏金额
     */
    private Integer twoPrice;

    /**
     * 最佳答案第三名悬赏金额
     */
    private Integer threePrice;

    /**
     * 悬赏金额是否已经缴纳 0 未支付 1 已支付
     */
    private Byte isPay;

    /**
     * 悬赏金额设置类型  为‘评论和点赞前三名’使用
     */
    private Integer days;

    private static final long serialVersionUID = 1L;

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
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

    public Byte getIsPay() {
        return isPay;
    }

    public void setIsPay(Byte isPay) {
        this.isPay = isPay;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", setId=").append(setId);
        sb.append(", infoId=").append(infoId);
        sb.append(", price=").append(price);
        sb.append(", twoPrice=").append(twoPrice);
        sb.append(", threePrice=").append(threePrice);
        sb.append(", isPay=").append(isPay);
        sb.append(", days=").append(days);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}