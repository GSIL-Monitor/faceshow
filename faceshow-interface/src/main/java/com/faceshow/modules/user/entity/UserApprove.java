package com.faceshow.modules.user.entity;

import java.io.Serializable;
import java.util.Date;

public class UserApprove implements Serializable {
    /**
     * 认证ID主键
     */
    private Integer approveId;

    /**
     * 所属会员
     */
    private Integer userId;

    /**
     * 真实名称
     */
    private String realName;

    /**
     * 申请认证时间
     */
    private Date approveTime;

    /**
     * 身份证反面
     */
    private String cardOp;

    /**
     * 身份证正面
     */
    private String cardPo;

    /**
     * 身份证号码
     */
    private String cardId;

    /**
     * 认证人
     */
    private Integer approveMan;

    /**
     * 确定认证时间
     */
    private Date okTime;

    /**
     * 是否认证通过 0 否 1 是
     */
    private Boolean isPast;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getApproveId() {
        return approveId;
    }

    public void setApproveId(Integer approveId) {
        this.approveId = approveId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getCardOp() {
        return cardOp;
    }

    public void setCardOp(String cardOp) {
        this.cardOp = cardOp == null ? null : cardOp.trim();
    }

    public String getCardPo() {
        return cardPo;
    }

    public void setCardPo(String cardPo) {
        this.cardPo = cardPo == null ? null : cardPo.trim();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public Integer getApproveMan() {
        return approveMan;
    }

    public void setApproveMan(Integer approveMan) {
        this.approveMan = approveMan;
    }

    public Date getOkTime() {
        return okTime;
    }

    public void setOkTime(Date okTime) {
        this.okTime = okTime;
    }

    public Boolean getIsPast() {
        return isPast;
    }

    public void setIsPast(Boolean isPast) {
        this.isPast = isPast;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", approveId=").append(approveId);
        sb.append(", userId=").append(userId);
        sb.append(", realName=").append(realName);
        sb.append(", approveTime=").append(approveTime);
        sb.append(", cardOp=").append(cardOp);
        sb.append(", cardPo=").append(cardPo);
        sb.append(", cardId=").append(cardId);
        sb.append(", approveMan=").append(approveMan);
        sb.append(", okTime=").append(okTime);
        sb.append(", isPast=").append(isPast);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}