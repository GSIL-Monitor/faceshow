package com.faceshow.modules.user.entity;

import java.io.Serializable;
import java.util.Date;

public class UserNewsletterGroup implements Serializable {
    /**
     * 分组ID主键
     */
    private Integer newsletterId;

    /**
     * 分组类型ID主键
     */
    private Integer typeId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 活跃度
     */
    private Integer activity;

    /**
     * 创建人(那个注册用户创建的分组信息)
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Integer editor;

    /**
     * 修改时间
     */
    private Date editorTime;

    /**
     * 分组群头像
     */
    private String img;

    /**
     * 群公告
     */
    private String cements;

    private static final long serialVersionUID = 1L;

    public Integer getNewsletterId() {
        return newsletterId;
    }

    public void setNewsletterId(Integer newsletterId) {
        this.newsletterId = newsletterId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getCements() {
        return cements;
    }

    public void setCements(String cements) {
        this.cements = cements == null ? null : cements.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", newsletterId=").append(newsletterId);
        sb.append(", typeId=").append(typeId);
        sb.append(", groupName=").append(groupName);
        sb.append(", activity=").append(activity);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", editor=").append(editor);
        sb.append(", editorTime=").append(editorTime);
        sb.append(", img=").append(img);
        sb.append(", cements=").append(cements);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}