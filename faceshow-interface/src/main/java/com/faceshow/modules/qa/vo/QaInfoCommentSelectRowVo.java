package com.faceshow.modules.qa.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 问答评论查询信息
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/4 13:12
 * -------------------------------------------------------------- <br>
 */
public class QaInfoCommentSelectRowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer commentId;
    private Integer parentCommentId;
    private String comment;
    private Integer userId;
    private Integer likeNum;
    private Long createTime;
    private String nickName;
    private String userImg;
    private Integer isLike;
    private String isCommentator;
    private String byCommentator;
    private Integer byCommentatorId;
    private Integer infoId;
    private Integer replyId;

    public QaInfoCommentSelectRowVo() {
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getCommentId() {
        return commentId == null ? 0 : commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getParentCommentId() {
        return parentCommentId == null ? 0 : parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getComment() {
        return comment == null ? "" : comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getUserId() {
        return userId == null ? 0 : userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLikeNum() {
        return likeNum == null ? 0 : likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getNickName() {
        return nickName == null ? "" : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserImg() {
        return userImg == null ? "" : userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public String getIsCommentator() {
        return isCommentator == null ? "" : isCommentator;
    }

    public void setIsCommentator(String isCommentator) {
        this.isCommentator = isCommentator;
    }

    public String getByCommentator() {
        return byCommentator == null ? "" : byCommentator;
    }

    public void setByCommentator(String byCommentator) {
        this.byCommentator = byCommentator;
    }

    public Integer getByCommentatorId() {
        return byCommentatorId == null ? 0 : byCommentatorId;
    }

    public void setByCommentatorId(Integer byCommentatorId) {
        this.byCommentatorId = byCommentatorId;
    }

    @Override
    public String toString() {
        return "QaInfoCommentSelectRowVo{" +
                "commentId=" + commentId +
                ", parentCommentId=" + parentCommentId +
                ", comment='" + comment + '\'' +
                ", userId=" + userId +
                ", likeNum=" + likeNum +
                ", createTime=" + createTime +
                ", nickName='" + nickName + '\'' +
                ", userImg='" + userImg + '\'' +
                ", isLike=" + isLike +
                ", isCommentator='" + isCommentator + '\'' +
                ", byCommentator='" + byCommentator + '\'' +
                ", byCommentatorId=" + byCommentatorId +
                '}';
    }
}
