package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImMsgContent implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private String Text ;// 	消息内容。当接收方为iphone后台在线时，做iOS离线Push时文本展示。
    @JsonProperty
    private  Integer Index;
    @JsonProperty
    private String Data;
    @JsonIgnore
    public Integer getIndex() {
        return Index;
    }
    @JsonIgnore
    public void setIndex(Integer index) {
        Index = index;
    }
    @JsonIgnore
    public String getData() {
        return Data;
    }
    @JsonIgnore
    public void setData(String data) {
        Data = data;
    }

    @JsonIgnore
    public String getText() {
        return Text;
    }
    @JsonIgnore
    public void setText(String text) {
        Text = text;
    }
}
