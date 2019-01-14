package com.faceshow.modules.qa.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 问答列表详情
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/3 9:26
 * -------------------------------------------------------------- <br>
 */
public class QaInfoSelectListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer infoId;
    private String title;
    private String content;
    private Integer userId;
    private Date createTime;
    private Integer likeNum;
    private Integer replyNum;
    private Integer makeNum;
    private Integer price;
    private Integer twoPrice;
    private Integer threePrice;
    private String cover;
    private Integer amountType;
    private Integer isNewAnswer;
    private String video;
    private Integer isCheck;
    private Integer isNewReply;
    private Integer isLike;
    private Integer isMake;
    private Integer joinNum;

    public Integer getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(Integer joinNum) {
        this.joinNum = joinNum;
    }

    public QaInfoSelectListVo() {
    }


    public Integer getTwoPrice() {
        return twoPrice ==  null ? 0 : twoPrice;
    }

    public void setTwoPrice(Integer twoPrice) {
        this.twoPrice = twoPrice;
    }

    public Integer getThreePrice() {
        return threePrice == null ? 0 : threePrice;
    }

    public void setThreePrice(Integer threePrice) {
        this.threePrice = threePrice;
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

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId == null ? 0 : userId;
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

    public Integer getLikeNum() {
        return likeNum == null ? 0 : likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getReplyNum() {
        return replyNum == null ? 0 : replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getMakeNum() {
        return makeNum == null ? 0 : makeNum;
    }

    public void setMakeNum(Integer makeNum) {
        this.makeNum = makeNum;
    }

    public Integer getPrice() {
        return price == null ? 0 : price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCover() {
        return cover == null ? "" : cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getAmountType() {
        return amountType;
    }

    public void setAmountType(Integer amountType) {
        this.amountType = amountType;
    }

    public Integer getIsNewAnswer() {
        return isNewAnswer == null ? 0 : isNewAnswer;
    }

    public void setIsNewAnswer(Integer isNewAnswer) {
        this.isNewAnswer = isNewAnswer;
    }

    public String getVideo() {
        return video == null ? "" : video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getIsCheck() {
        return isCheck == null ? 1 : isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public Integer getIsNewReply() {
        return isNewReply == null ? 0 : isNewReply;
    }

    public void setIsNewReply(Integer isNewReply) {
        this.isNewReply = isNewReply;
    }

    public Integer getIsLike() {
        return isLike == null ? 0 : isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getIsMake() {
        return isMake == null ? 0 : isMake;
    }

    public void setIsMake(Integer isMake) {
        this.isMake = isMake;
    }

    @Override
    public String toString() {
        return "QaInfoSelectListVo{" +
                "infoId=" + infoId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", likeNum=" + likeNum +
                ", replyNum=" + replyNum +
                ", makeNum=" + makeNum +
                ", price=" + price +
                ", cover='" + cover + '\'' +
                ", amountType=" + amountType +
                ", isNewAnswer=" + isNewAnswer +
                ", video='" + video + '\'' +
                ", isCheck=" + isCheck +
                ", isNewReply=" + isNewReply +
                ", isLike=" + isLike +
                ", isMake=" + isMake +
                '}';
    }
}
