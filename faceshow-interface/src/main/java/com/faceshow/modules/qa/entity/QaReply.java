package com.faceshow.modules.qa.entity;

import java.io.Serializable;
import java.util.Date;

public class QaReply implements Serializable {
    /**
     * 问答回复ID主键
     */
    private Integer replyId;

    /**
     * 问答信息ID主键
     */
    private Integer infoId;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 回复人ID主键(用户表)
     */
    private Integer userId;

    /**
     * 回复时间
     */
    private Date createTime;

    /**
     * 回复点赞数量
     */
    private Integer likeNums;

    /**
     * 吐槽数
     */
    private Integer makeNum;

    /**
     * 问题回复视频URL
     */
    private String video;

    /**
     * 回答封面
     */
    private String cover;

    /**
     * 是否最佳答案 0 否 1 是
     */
    private Byte isAptimal;

    /**
     * 是否神回复 0 否 1 是
     */
    private Byte isShen;

    /**
     * 是否已查看 0 否 1 是
     */
    private Byte isLook;

    /**
     * 回答地点(经纬度)
     */
    private String longitude;

    /**
     * 回答地点(维度)
     */
    private String latitude;

    /**
     * 发布地址
     */
    private String address;

    /**
     * 审核人
     */
    private Integer editor;

    /**
     * 审核时间
     */
    private Date editorTime;

    /**
     * 审核状态, 0否, 1是
     */
    private Byte isCheck;

    /**
     * 评论总数
     */
    private Integer commentNum;

    private static final long serialVersionUID = 1L;

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public Integer getLikeNums() {
        return likeNums;
    }

    public void setLikeNums(Integer likeNums) {
        this.likeNums = likeNums;
    }

    public Integer getMakeNum() {
        return makeNum;
    }

    public void setMakeNum(Integer makeNum) {
        this.makeNum = makeNum;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video == null ? null : video.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public Byte getIsAptimal() {
        return isAptimal;
    }

    public void setIsAptimal(Byte isAptimal) {
        this.isAptimal = isAptimal;
    }

    public Byte getIsShen() {
        return isShen;
    }

    public void setIsShen(Byte isShen) {
        this.isShen = isShen;
    }

    public Byte getIsLook() {
        return isLook;
    }

    public void setIsLook(Byte isLook) {
        this.isLook = isLook;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getEditor() {
        return editor;
    }

    public void setEditor(Integer editor) {
        this.editor = editor;
    }

    public Date getEditorTime() {
        return editorTime;
    }

    public void setEditorTime(Date editorTime) {
        this.editorTime = editorTime;
    }

    public Byte getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Byte isCheck) {
        this.isCheck = isCheck;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", replyId=").append(replyId);
        sb.append(", infoId=").append(infoId);
        sb.append(", content=").append(content);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", likeNums=").append(likeNums);
        sb.append(", makeNum=").append(makeNum);
        sb.append(", video=").append(video);
        sb.append(", cover=").append(cover);
        sb.append(", isAptimal=").append(isAptimal);
        sb.append(", isShen=").append(isShen);
        sb.append(", isLook=").append(isLook);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", address=").append(address);
        sb.append(", editor=").append(editor);
        sb.append(", editorTime=").append(editorTime);
        sb.append(", isCheck=").append(isCheck);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}