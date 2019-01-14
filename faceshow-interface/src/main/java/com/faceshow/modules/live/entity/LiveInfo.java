package com.faceshow.modules.live.entity;

import java.io.Serializable;
import java.util.Date;

public class LiveInfo implements Serializable {
    /**
     * 用户直播主键ID
     */
    private Integer liveId;

    /**
     * 直播用户主键ID
     */
    private Integer userId;

    /**
     * 主播编号
     */
    private String anchorNo;

    /**
     * 直播开始时间
     */
    private Date startTime;

    /**
     * 直播结束时间
     */
    private Date endTime;

    /**
     * 在线人数
     */
    private Integer onlineNum;

    /**
     * 点赞数 点赞就是收藏直播室
     */
    private Integer likeNum;

    /**
     * 礼品数量
     */
    private Integer giftNum;

    /**
     * 封面
     */
    private String liveImg;

    /**
     * 直播地址 也就是直播间的地址
     */
    private String fileUrl;

    /**
     * 0正在直播 1直播结束了
     */
    private Byte isLive;

    private static final long serialVersionUID = 1L;

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAnchorNo() {
        return anchorNo;
    }

    public void setAnchorNo(String anchorNo) {
        this.anchorNo = anchorNo == null ? null : anchorNo.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(Integer onlineNum) {
        this.onlineNum = onlineNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }

    public String getLiveImg() {
        return liveImg;
    }

    public void setLiveImg(String liveImg) {
        this.liveImg = liveImg == null ? null : liveImg.trim();
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    public Byte getIsLive() {
        return isLive;
    }

    public void setIsLive(Byte isLive) {
        this.isLive = isLive;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", liveId=").append(liveId);
        sb.append(", userId=").append(userId);
        sb.append(", anchorNo=").append(anchorNo);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", onlineNum=").append(onlineNum);
        sb.append(", likeNum=").append(likeNum);
        sb.append(", giftNum=").append(giftNum);
        sb.append(", liveImg=").append(liveImg);
        sb.append(", fileUrl=").append(fileUrl);
        sb.append(", isLive=").append(isLive);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}