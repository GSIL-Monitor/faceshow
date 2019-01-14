package com.faceshow.modules.user.vo;

import java.io.Serializable;

public class MessagePushSummary implements Serializable {
    private static final long serialVersionUID = 1L;
    private String beforeContent;//前段内容
    private String afterContent;//后端内容
    private Integer typeId;//消息推送通知类型ID主键

    public String getBeforeContent() {
        return beforeContent;
    }

    public void setBeforeContent(String beforeContent) {
        this.beforeContent = beforeContent;
    }

    public String getAfterContent() {
        return afterContent;
    }

    public void setAfterContent(String afterContent) {
        this.afterContent = afterContent;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "MessagePushSummary{" +
                "beforeContent='" + beforeContent + '\'' +
                ", afterContent='" + afterContent + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}
