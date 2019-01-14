package com.faceshow.modules.user.entity;

import java.io.Serializable;
import java.util.Date;

public class UserGiveAwayLog implements Serializable {
    /**
     * 赠送ID主键
     */
    private Integer giveId;

    /**
     * 礼品ID主键
     */
    private Integer giftId;

    /**
     * 赠送人
     */
    private Integer userId;

    /**
     * 赠送人名称
     */
    private String userName;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 接收人
     */
    private Integer account;

    /**
     * 接收人名称
     */
    private String accountUserName;

    /**
     * 赠送类型 1 短视频 2 直播 3 一对一 4 聊天 5
     */
    private Byte isType;

    /**
     * 赠送数量 默认 1
     */
    private Integer numbers;

    /**
     * 价格(礼品单价)
     */
    private Integer price;

    /**
     * 赠送了类型主键
     */
    private Integer itemId;

    /**
     * 备注字段
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getGiveId() {
        return giveId;
    }

    public void setGiveId(Integer giveId) {
        this.giveId = giveId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getAccountUserName() {
        return accountUserName;
    }

    public void setAccountUserName(String accountUserName) {
        this.accountUserName = accountUserName == null ? null : accountUserName.trim();
    }

    public Byte getIsType() {
        return isType;
    }

    public void setIsType(Byte isType) {
        this.isType = isType;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {
        this.numbers = numbers;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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
        sb.append(", giveId=").append(giveId);
        sb.append(", giftId=").append(giftId);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", createTime=").append(createTime);
        sb.append(", account=").append(account);
        sb.append(", accountUserName=").append(accountUserName);
        sb.append(", isType=").append(isType);
        sb.append(", numbers=").append(numbers);
        sb.append(", price=").append(price);
        sb.append(", itemId=").append(itemId);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}