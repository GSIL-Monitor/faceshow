package com.faceshow.modules.qa.vo;

import java.io.Serializable;

/**
 * 问答详情
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/9 11:09
 * -------------------------------------------------------------- <br>
 */
public class QaInfoSelectRowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer infoId;
    private String title;
    private String content;
    private Integer amountType;
    private Integer userId;
    private String nickName;
    private String img;
    private Long createTime;
    private Integer replyNum;
    private Integer joinNum;
    private String video;
    private Integer price;
    private Integer twoPrice;
    private Integer threePrice;
    private String cover;
    private Integer commentNum;
    private String length;
    private Integer isTutual;
    private Integer isLike;

    public QaInfoSelectRowVo() {
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

    public Integer getAmountType() {
        return amountType;
    }

    public void setAmountType(Integer amountType) {
        this.amountType = amountType;
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

    public Integer getReplyNum() {
        return replyNum == null ? 0 : replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getJoinNum() {
        return joinNum == null ? 0 : joinNum;
    }

    public void setJoinNum(Integer joinNum) {
        this.joinNum = joinNum;
    }

    public String getVideo() {
        return video == null ? "" : video;
    }

    public void setVideo(String video) {
        this.video = video;
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

    public Integer getThreePrice() {
        return threePrice == null ? 0 : threePrice;
    }

    public void setThreePrice(Integer threePrice) {
        this.threePrice = threePrice;
    }

    public String getCover() {
        return cover == null ? "" : cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getCommentNum() {
        return commentNum == null ? 0 : commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Integer getIsTutual() {
        return isTutual;
    }

    public void setIsTutual(Integer isTutual) {
        this.isTutual = isTutual;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    @Override
    public String toString() {
        return "QaInfoSelectRowVo{" +
                "infoId=" + infoId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", amountType=" + amountType +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", img='" + img + '\'' +
                ", createTime=" + createTime +
                ", replyNum=" + replyNum +
                ", joinNum=" + joinNum +
                ", video='" + video + '\'' +
                ", price=" + price +
                ", twoPrice=" + twoPrice +
                ", threePrice=" + threePrice +
                ", cover='" + cover + '\'' +
                ", commentNum=" + commentNum +
                ", length='" + length + '\'' +
                ", isTutual=" + isTutual +
                ", isLike=" + isLike +
                '}';
    }
}
