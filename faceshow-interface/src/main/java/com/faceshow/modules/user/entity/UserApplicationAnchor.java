package com.faceshow.modules.user.entity;

import java.io.Serializable;
import java.util.Date;

public class UserApplicationAnchor implements Serializable {
    /**
     * 申请主播ID主键
     */
    private Integer anchorId;

    /**
     * 会员ID主键
     */
    private Integer userId;

    /**
     * 身份证号码
     */
    private String card;

    /**
     * 身份证正面手持
     */
    private String cardPositive;

    /**
     * 身份证反面手持
     */
    private String cardOpposite;

    /**
     * 真是姓名
     */
    private String realName;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 审核人
     */
    private String checkName;

    /**
     * 审核时间
     */
    private Date checkTime;

    /**
     * 审核状态 1 审核中 2 拒绝 3 审核通过
     */
    private Byte checkType;

    private static final long serialVersionUID = 1L;

    public Integer getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(Integer anchorId) {
        this.anchorId = anchorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card == null ? null : card.trim();
    }

    public String getCardPositive() {
        return cardPositive;
    }

    public void setCardPositive(String cardPositive) {
        this.cardPositive = cardPositive == null ? null : cardPositive.trim();
    }

    public String getCardOpposite() {
        return cardOpposite;
    }

    public void setCardOpposite(String cardOpposite) {
        this.cardOpposite = cardOpposite == null ? null : cardOpposite.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName == null ? null : checkName.trim();
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Byte getCheckType() {
        return checkType;
    }

    public void setCheckType(Byte checkType) {
        this.checkType = checkType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", anchorId=").append(anchorId);
        sb.append(", userId=").append(userId);
        sb.append(", card=").append(card);
        sb.append(", cardPositive=").append(cardPositive);
        sb.append(", cardOpposite=").append(cardOpposite);
        sb.append(", realName=").append(realName);
        sb.append(", createTime=").append(createTime);
        sb.append(", checkName=").append(checkName);
        sb.append(", checkTime=").append(checkTime);
        sb.append(", checkType=").append(checkType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}