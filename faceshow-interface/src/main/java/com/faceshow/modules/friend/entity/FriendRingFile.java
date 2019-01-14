package com.faceshow.modules.friend.entity;

import java.io.Serializable;
import java.util.Date;

public class FriendRingFile implements Serializable {
    /**
     * 朋友圈图片视频ID主键
     */
    private Integer fileId;

    /**
     * 朋友圈ID主键
     */
    private Integer friendId;

    /**
     * 图片视频名称URL
     */
    private String fileUrl;

    /**
     * 0图片1视频
     */
    private Byte type;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 缩略图
     */
    private String fileSmallUrl;

    private static final long serialVersionUID = 1L;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFileSmallUrl() {
        return fileSmallUrl;
    }

    public void setFileSmallUrl(String fileSmallUrl) {
        this.fileSmallUrl = fileSmallUrl == null ? null : fileSmallUrl.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fileId=").append(fileId);
        sb.append(", friendId=").append(friendId);
        sb.append(", fileUrl=").append(fileUrl);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", fileSmallUrl=").append(fileSmallUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}