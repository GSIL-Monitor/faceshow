package com.faceshow.modules.qa.entity;

import java.io.Serializable;
import java.util.Date;

public class QaInfo implements Serializable {
    /**
     * 问答信息ID主键
     */
    private Integer infoId;

    /**
     * 问答信息分类表
     */
    private Integer typeId;

    /**
     * 问答标题
     */
    private String title;

    /**
     * 问答内容
     */
    private String content;

    /**
     * 发布人
     */
    private Integer userId;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 问题视频地址URL
     */
    private String video;

    /**
     * 点赞数 默认 0
     */
    private Integer likeNum;

    /**
     * 回答数
     */
    private Integer replyNum;

    /**
     * 吐槽数
     */
    private Integer makeNum;

    /**
     * 问题封面
     */
    private String cover;

    /**
     * 发布地点
     */
    private String address;

    /**
     * 是否置顶 0 否 1 置顶
     */
    private Byte isTop;

    /**
     * 问题经度
     */
    private String longitude;

    /**
     * 问题维度
     */
    private String latitude;

    /**
     * 悬赏金额设置类型 1 无偿回答 2 指定最佳答案 3 评论和点赞前三名
     */
    private Byte amountType;

    /**
     * 所属国家
     */
    private String country;

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

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video == null ? null : video.trim();
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getMakeNum() {
        return makeNum;
    }

    public void setMakeNum(Integer makeNum) {
        this.makeNum = makeNum;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Byte getIsTop() {
        return isTop;
    }

    public void setIsTop(Byte isTop) {
        this.isTop = isTop;
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

    public Byte getAmountType() {
        return amountType;
    }

    public void setAmountType(Byte amountType) {
        this.amountType = amountType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
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
        sb.append(", infoId=").append(infoId);
        sb.append(", typeId=").append(typeId);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", video=").append(video);
        sb.append(", likeNum=").append(likeNum);
        sb.append(", replyNum=").append(replyNum);
        sb.append(", makeNum=").append(makeNum);
        sb.append(", cover=").append(cover);
        sb.append(", address=").append(address);
        sb.append(", isTop=").append(isTop);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", amountType=").append(amountType);
        sb.append(", country=").append(country);
        sb.append(", editor=").append(editor);
        sb.append(", editorTime=").append(editorTime);
        sb.append(", isCheck=").append(isCheck);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}