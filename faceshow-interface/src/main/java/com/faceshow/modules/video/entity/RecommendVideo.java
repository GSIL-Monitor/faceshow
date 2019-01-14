package com.faceshow.modules.video.entity;

import java.io.Serializable;

public class RecommendVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer videoId;
    private Integer topicId;
    private Integer typeId;
    private Integer userId;
    private Integer musicId;
    private String videoCreateTime;
    private String videoUrl;
    private Integer videoCollectionNum;
    private String videoImg;
    private String videoIntroductionContent;
    private String videoIntroductionJson;
    private String userNickName;
    private String userImg;
    private String topicName;
    private String topicImg;
    private String typeName;
    private String topicIntroduction;
    private String musicName;
    private String musicUrl;
    private String musicImg;
    private String isZan = "1";//该用户是否点赞过这个视频 0没有1点赞了
    private Integer videoCommentNum;//评论数量
    private String friend;
    private Integer isAttention;
    private Integer isSource;
    private Integer videoPlayNum;

    public Integer getVideoPlayNum() {
        return videoPlayNum == null ? 0 : videoPlayNum;
    }

    public void setVideoPlayNum(Integer videoPlayNum) {
        this.videoPlayNum = videoPlayNum;
    }

    public String getMusicImg() {
        return musicImg == null ? "" : musicImg;
    }

    public void setMusicImg(String musicImg) {
        this.musicImg = musicImg;
    }

    public Integer getIsSource() {
        return isSource == null ? 1 : isSource;
    }

    public void setIsSource(Integer isSource) {
        this.isSource = isSource;
    }

    public Integer getIsAttention() {
        return isAttention == null ? 0 : isAttention;
    }

    public void setIsAttention(Integer isAttention) {
        this.isAttention = isAttention;
    }

    public String getFriend() {
        return friend == null ? "" : friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getTypeName() {
        return typeName == null ? "" : typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getVideoCommentNum() {
        return videoCommentNum == null ? 0 : videoCommentNum;
    }

    public void setVideoCommentNum(Integer videoCommentNum) {
        this.videoCommentNum = videoCommentNum;
    }

    public String getIsZan() {
        return isZan;
    }

    public void setIsZan(String isZan) {
        this.isZan = isZan;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getTopicId() {
        return topicId == null ? 0 : topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getTypeId() {
        return typeId == null ? 0 : typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUserId() {
        return userId == null ? 0 : userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMusicId() {
        return musicId == null ? 0 : musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }

    public String getVideoCreateTime() {
        return videoCreateTime == null ? "" : videoCreateTime;
    }

    public void setVideoCreateTime(String videoCreateTime) {
        this.videoCreateTime = videoCreateTime;
    }

    public String getVideoUrl() {
        return videoUrl == null ? "" : videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getVideoCollectionNum() {
        return videoCollectionNum == null ? 0 : videoCollectionNum;
    }

    public void setVideoCollectionNum(Integer videoCollectionNum) {
        this.videoCollectionNum = videoCollectionNum;
    }

    public String getVideoImg() {
        return videoImg == null ? "" : videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public String getUserNickName() {
        return userNickName == null ? "" : userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserImg() {
        return userImg == null ? "" : userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getTopicName() {
        return topicName == null ? "" : topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicImg() {
        return topicImg == null ? "" : topicImg;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public String getTopicIntroduction() {
        return topicIntroduction == null ? "" : topicIntroduction;
    }

    public void setTopicIntroduction(String topicIntroduction) {
        this.topicIntroduction = topicIntroduction;
    }

    public String getMusicName() {
        return musicName == null ? "" : musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicUrl() {
        return musicUrl == null ? "" : musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getVideoIntroductionContent() {
        return videoIntroductionContent == null ? "" : videoIntroductionContent;
    }

    public void setVideoIntroductionContent(String videoIntroductionContent) {
        this.videoIntroductionContent = videoIntroductionContent;
    }

    public String getVideoIntroductionJson() {
        return videoIntroductionJson == null ? "" : videoIntroductionJson;
    }

    public void setVideoIntroductionJson(String videoIntroductionJson) {
        this.videoIntroductionJson = videoIntroductionJson;
    }

    @Override
    public String toString() {
        return "RecommendVideo{" +
                "videoId=" + videoId +
                ", topicId=" + topicId +
                ", typeId=" + typeId +
                ", userId=" + userId +
                ", musicId=" + musicId +
                ", videoCreateTime='" + videoCreateTime + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", videoCollectionNum=" + videoCollectionNum +
                ", videoImg='" + videoImg + '\'' +
                ", videoIntroductionContent='" + videoIntroductionContent + '\'' +
                ", videoIntroductionJson='" + videoIntroductionJson + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userImg='" + userImg + '\'' +
                ", topicName='" + topicName + '\'' +
                ", topicImg='" + topicImg + '\'' +
                ", typeName='" + typeName + '\'' +
                ", topicIntroduction='" + topicIntroduction + '\'' +
                ", musicName='" + musicName + '\'' +
                ", musicUrl='" + musicUrl + '\'' +
                ", musicImg='" + musicImg + '\'' +
                ", isZan='" + isZan + '\'' +
                ", videoCommentNum=" + videoCommentNum +
                ", friend='" + friend + '\'' +
                ", isAttention=" + isAttention +
                ", isSource=" + isSource +
                ", videoPlayNum=" + videoPlayNum +
                '}';
    }
}
