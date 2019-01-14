package com.faceshow.modules.qa.entity;

import java.io.Serializable;
import java.util.Date;

public class QaInfoType implements Serializable {
    /**
     * 问答信息分类表
     */
    private Integer typeId;

    /**
     * 分类名称
     */
    private String tname;

    /**
     * 添加时间
     */
    private Date creatorTime;

    /**
     * 添加人
     */
    private Integer creator;

    /**
     * 修改时间
     */
    private Date editorTime;

    /**
     * 修改人
     */
    private Integer editor;

    private static final long serialVersionUID = 1L;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getEditorTime() {
        return editorTime;
    }

    public void setEditorTime(Date editorTime) {
        this.editorTime = editorTime;
    }

    public Integer getEditor() {
        return editor;
    }

    public void setEditor(Integer editor) {
        this.editor = editor;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", typeId=").append(typeId);
        sb.append(", tname=").append(tname);
        sb.append(", creatorTime=").append(creatorTime);
        sb.append(", creator=").append(creator);
        sb.append(", editorTime=").append(editorTime);
        sb.append(", editor=").append(editor);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}