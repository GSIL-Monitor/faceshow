package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ImMsgBody implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private String MsgType;//TIM消息对象类型，目前支持的消息对象包括： TIMTextElem(文本消息),TIMFaceElem(表情消息)， TIMLocationElem(位置消息)，TIMCustomElem(自定义消息)。
    @JsonProperty
    private ImMsgContent MsgContent;//对于每种MsgType用不同的MsgContent格式，
    @JsonIgnore
    public ImMsgContent getMsgContent() {
        return MsgContent;
    }
    @JsonIgnore
    public void setMsgContent(ImMsgContent msgContent) {
        MsgContent = msgContent;
    }

    @JsonIgnore
    public String getMsgType() {
        return MsgType;
    }
    @JsonIgnore
    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

}
