package com.faceshow.modules.qa.entity;

import java.io.Serializable;
import java.util.Date;

public class QaSetDays implements Serializable {
    /**
     * 问答天数设置ID主键
     */
    private Integer setId;

    /**
     * 天数
     */
    private Byte days;

    /**
     * 添加人
     */
    private Integer creator;

    /**
     * 添加时间
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

    private static final long serialVersionUID = 1L;

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public Byte getDays() {
        return days;
    }

    public void setDays(Byte days) {
        this.days = days;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", setId=").append(setId);
        sb.append(", days=").append(days);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", editor=").append(editor);
        sb.append(", editorTime=").append(editorTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}