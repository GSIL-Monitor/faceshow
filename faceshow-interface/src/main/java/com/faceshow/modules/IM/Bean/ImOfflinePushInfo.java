package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImOfflinePushInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private  Integer PushFlag;
    @JsonProperty
    private  String Desc;//离线推送内容
    @JsonProperty
    private  String Ext;//这是透传的内容
    @JsonProperty
    private ImAndroidInfo AndroidInfo;
    @JsonProperty
    private ImApnsInfo ApnsInfo;
    @JsonIgnore
    public Integer getPushFlag() {
        return PushFlag;
    }
    @JsonIgnore
    public void setPushFlag(Integer pushFlag) {
        PushFlag = pushFlag;
    }

    @JsonIgnore
    public String getDesc() {
        return Desc;
    }
    @JsonIgnore
    public void setDesc(String desc) {
        Desc = desc;
    }
    @JsonIgnore
    public String getExt() {
        return Ext;
    }
    @JsonIgnore
    public void setExt(String ext) {
        Ext = ext;
    }
    @JsonIgnore
    public ImAndroidInfo getAndroidInfo() {
        return AndroidInfo;
    }
    @JsonIgnore
    public void setAndroidInfo(ImAndroidInfo androidInfo) {
        AndroidInfo = androidInfo;
    }
    @JsonIgnore
    public ImApnsInfo getApnsInfo() {
        return ApnsInfo;
    }
    @JsonIgnore
    public void setApnsInfo(ImApnsInfo apnsInfo) {
        ApnsInfo = apnsInfo;
    }
}
