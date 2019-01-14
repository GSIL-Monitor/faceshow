package com.faceshow.modules.video.entity;

import java.io.Serializable;
import java.util.Date;

public class VideoTopic implements Serializable {
    /**
     * 视频话题ID主键
     */
    private Integer topicId;

    /**
     * 话题名称
     */
    private String tname;

    /**
     * 添加人
     */
    private Integer userId;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 话题图标
     */
    private String img;

    /**
     * 话题简介
     */
    private String introduction;

    private static final long serialVersionUID = 1L;

    public VideoTopic() {
    }

    public VideoTopic(String tname, Integer userId, Date createTime) {
        this.tname = tname;
        this.userId = userId;
        this.createTime = createTime;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    public Integer getUserId() {
        return userId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", topicId=").append(topicId);
        sb.append(", tname=").append(tname);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", img=").append(img);
        sb.append(", introduction=").append(introduction);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}