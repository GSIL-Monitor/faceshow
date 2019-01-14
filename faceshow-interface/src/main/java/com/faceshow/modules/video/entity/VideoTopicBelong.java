package com.faceshow.modules.video.entity;

import java.io.Serializable;
import java.util.Date;

public class VideoTopicBelong implements Serializable {
    /**
     * 视频话题ID主键
     */
    private Integer belongId;

    /**
     * 话题id
     */
    private Integer topicId;

    /**
     * 视频ID主键
     */
    private Integer videoId;

    /**
     * 添加时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public VideoTopicBelong() {
    }

    public VideoTopicBelong(Integer topicId, Integer videoId, Date createTime) {
        this.topicId = topicId;
        this.videoId = videoId;
        this.createTime = createTime;
    }

    public Integer getBelongId() {
        return belongId;
    }

    public void setBelongId(Integer belongId) {
        this.belongId = belongId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", belongId=").append(belongId);
        sb.append(", topicId=").append(topicId);
        sb.append(", videoId=").append(videoId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}