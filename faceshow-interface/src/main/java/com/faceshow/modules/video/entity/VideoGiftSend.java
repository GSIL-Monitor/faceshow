package com.faceshow.modules.video.entity;

import java.io.Serializable;
import java.util.Date;

public class VideoGiftSend implements Serializable {
    /**
     * 推荐ID主键
     */
    private Integer sendId;

    /**
     * 会员ID主键
     */
    private Integer userId;

    /**
     * 视频ID主键
     */
    private Integer videoId;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 礼品ID主键 关联礼品表主键ID
     */
    private Integer giftId;

    /**
     * 价格(钻石数) 只是当前礼物价格 如是多个不计算
     */
    private Integer price;

    /**
     * 赠送个数 默认最少1个
     */
    private Integer numbers;

    private static final long serialVersionUID = 1L;

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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
        sb.append(", sendId=").append(sendId);
        sb.append(", userId=").append(userId);
        sb.append(", videoId=").append(videoId);
        sb.append(", createTime=").append(createTime);
        sb.append(", giftId=").append(giftId);
        sb.append(", price=").append(price);
        sb.append(", numbers=").append(numbers);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}