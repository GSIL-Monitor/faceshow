package com.faceshow.modules.qa.vo;

import java.io.Serializable;

/**
 * 问答回复详情
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/8 20:53
 * -------------------------------------------------------------- <br>
 */
public class QaReplySelectRowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer replyId;
    private Integer infoId;
    private String title;
    private Integer amountType;
    private Integer publisherId;
    private String content;
    private Integer isPay;
    private Integer price;
    private Integer twoPrice;
    private Integer threeThree;
    private Integer userId;
    private String nickName;
    private String img;
    private Long createTime;
    private Integer likeNum;
    private Integer makeNum;
    private String video;
    private String cover;
    private String address;
    private String length;
    private Integer isTutual;
    private Integer isLike;
    private Integer isMake;
    private Integer commentNum;

    public QaReplySelectRowVo() {
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getIsMake() {
        return isMake;
    }

    public void setIsMake(Integer isMake) {
        this.isMake = isMake;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getIsTutual() {
        return isTutual;
    }

    public void setIsTutual(Integer isTutual) {
        this.isTutual = isTutual;
    }

    public Integer getReplyId() {
        return replyId == null ? 0 : replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getInfoId() {
        return infoId == null ? 0 : infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAmountType() {
        return amountType;
    }

    public void setAmountType(Integer amountType) {
        this.amountType = amountType;
    }

    public Integer getPublisherId() {
        return publisherId == null ? 0 : publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsPay() {
        return isPay == null ? 0 : isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Integer getPrice() {
        return price == null ? 0 : price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTwoPrice() {
        return twoPrice == null ? 0 : twoPrice;
    }

    public void setTwoPrice(Integer twoPrice) {
        this.twoPrice = twoPrice;
    }

    public Integer getThreeThree() {
        return threeThree == null ? 0 : threeThree;
    }

    public void setThreeThree(Integer threeThree) {
        this.threeThree = threeThree;
    }

    public Integer getUserId() {
        return userId == null ? 0 : userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName == null ? "" : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImg() {
        return img == null ? "" : img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getLikeNum() {
        return likeNum == null ? 0 : likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getMakeNum() {
        return makeNum == null ? 0 : makeNum;
    }

    public void setMakeNum(Integer makeNum) {
        this.makeNum = makeNum;
    }

    public String getVideo() {
        return video == null ? "" : video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCover() {
        return cover == null ? "" : cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLength() {
        return length == null ? "0" : length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "QaReplySelectRowVo{" +
                "replyId=" + replyId +
                ", infoId=" + infoId +
                ", title='" + title + '\'' +
                ", amountType=" + amountType +
                ", publisherId=" + publisherId +
                ", content='" + content + '\'' +
                ", isPay=" + isPay +
                ", price=" + price +
                ", twoPrice=" + twoPrice +
                ", threeThree=" + threeThree +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", img='" + img + '\'' +
                ", createTime=" + createTime +
                ", likeNum=" + likeNum +
                ", makeNum=" + makeNum +
                ", video='" + video + '\'' +
                ", cover='" + cover + '\'' +
                ", address='" + address + '\'' +
                ", length='" + length + '\'' +
                '}';
    }
}
