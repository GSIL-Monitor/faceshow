package com.faceshow.modules.makeFriend.vo;

import java.io.Serializable;

/**
 * 用户交友状态标签返回值
 */
public class MakeFriendIntentionSelectRowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交友意向标签ID主键
     */
    private Integer tagId;

    /**
     * 标签名称
     */
    private String tname;

    /**
     * 当前用户是否拥有该礼物, 0没有拥有礼物, 1拥有该礼物
     */
    private Integer have;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTname() {
        return tname == null ? "" : tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Integer getHave() {
        return have == null ? 0 : 1;
    }

    public void setHave(Integer have) {
        this.have = have;
    }

    @Override
    public String toString() {
        return "MakeFriendIntentionSelectRowVo{" +
                "tagId=" + tagId +
                ", tname=" + tname +
                ", have=" + have +
                '}';
    }
}
