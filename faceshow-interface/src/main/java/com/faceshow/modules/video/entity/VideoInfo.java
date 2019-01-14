package com.faceshow.modules.video.entity;

import java.io.Serializable;
import java.util.Date;

public class VideoInfo implements Serializable {
    /**
     * 视频ID主键
     */
    private Integer videoId;

    /**
     * 分类ID主键(默认为音乐分类)
     */
    private Integer typeId;

    /**
     * 所属会员ID主键
     */
    private Integer userId;

    /**
     * 音乐名称ID主键 0 表示无
     */
    private Integer musicId;

    /**
     * 所属地区
     */
    private String country;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 视频状态 0 已删除 1 正常 2 冻结
     */
    private Byte statues;

    /**
     * 点赞数
     */
    private Integer collectionNum;

    /**
     * 是否是私密视频 0私有 1公开, 2朋友圈私密视频
     */
    private Byte isPrivate;

    /**
     * 得到第一秒（也是第一帧）图片
     */
    private String img;

    /**
     * 视频简介
     */
    private String introduction;

    /**
     * 视频来源, 1本地上传, 2直接拍摄
     */
    private Byte isSource;

    /**
     * 是否为推荐热门, 0=否, 1=是
     */
    private Byte isHot;

    /**
     * 热度值, 越大越靠前, 默认为0
     */
    private Integer heatNum;

    /**
     * 评论数量, 默认为0
     */
    private Integer commentNum;

    /**
     * 分享数量, 默认为0
     */
    private Integer shareNum;

    private static final long serialVersionUID = 1L;

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Byte getStatues() {
        return statues;
    }

    public void setStatues(Byte statues) {
        this.statues = statues;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    public Byte getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Byte isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Byte getIsSource() {
        return isSource;
    }

    public void setIsSource(Byte isSource) {
        this.isSource = isSource;
    }

    public Byte getIsHot() {
        return isHot;
    }

    public void setIsHot(Byte isHot) {
        this.isHot = isHot;
    }

    public Integer getHeatNum() {
        return heatNum;
    }

    public void setHeatNum(Integer heatNum) {
        this.heatNum = heatNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", videoId=").append(videoId);
        sb.append(", typeId=").append(typeId);
        sb.append(", userId=").append(userId);
        sb.append(", musicId=").append(musicId);
        sb.append(", country=").append(country);
        sb.append(", createTime=").append(createTime);
        sb.append(", url=").append(url);
        sb.append(", statues=").append(statues);
        sb.append(", collectionNum=").append(collectionNum);
        sb.append(", isPrivate=").append(isPrivate);
        sb.append(", img=").append(img);
        sb.append(", introduction=").append(introduction);
        sb.append(", isSource=").append(isSource);
        sb.append(", isHot=").append(isHot);
        sb.append(", heatNum=").append(heatNum);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", shareNum=").append(shareNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}