package com.faceshow.modules.emotion.entity;

import java.io.Serializable;

public class EmotionInfoTags implements Serializable {
    /**
     * 情感状态标签ID主键
     */
    private Integer tagsId;

    /**
     * 标签名称
     */
    private String tname;

    private static final long serialVersionUID = 1L;

    public Integer getTagsId() {
        return tagsId;
    }

    public void setTagsId(Integer tagsId) {
        this.tagsId = tagsId;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tagsId=").append(tagsId);
        sb.append(", tname=").append(tname);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}