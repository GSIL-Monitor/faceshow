package com.faceshow.modules.IM.Bean;

import com.faceshow.common.utils.InvertCodeGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ImSendMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private Integer SyncOtherMachine;//选填 	1：把消息同步到From_Account在线终端和漫游上；2：消息不同步至From_Account；若不填写默认情况下会将消息存From_Account漫游
    @JsonProperty
    private String From_Account;//选填 	消息发送方账号。（用于指定发送消息方账号）
    @JsonProperty
    private String To_Account;//必填 	消息接收方账号。
    @JsonProperty
    private Integer MsgLifeTime;//选填 	消息离线保存时长（秒数），最长为7天（604800s）。若消息只发在线用户，不想保存离线，则该字段填0。若不填，则默认保存7天
    @JsonProperty
    private Integer MsgRandom= InvertCodeGenerator.getNumber();//消息随机数,由随机函数产生。（用作消息去重）
    @JsonProperty
    private  Integer MsgTimeStamp;//选填 	消息时间戳，unix时间戳。
    @JsonProperty
    private List<ImMsgBody> MsgBody;//消息内容，具体格式请参考消息格式描述。（注意，一条消息可包括多种消息元素，MsgBody为Array类型）
    @JsonProperty
    private  ImOfflinePushInfo OfflinePushInfo;// 	离线推送信息配置，具体可参考消息格式描述。
    @JsonIgnore
    public Integer getSyncOtherMachine() {
        return SyncOtherMachine;
    }
    @JsonIgnore
    public void setSyncOtherMachine(Integer syncOtherMachine) {
        SyncOtherMachine = syncOtherMachine;
    }
    @JsonIgnore
    public String getFrom_Account() {
        return From_Account;
    }
    @JsonIgnore
    public void setFrom_Account(String from_Account) {
        From_Account = from_Account;
    }
    @JsonIgnore
    public String getTo_Account() {
        return To_Account;
    }
    @JsonIgnore
    public void setTo_Account(String to_Account) {
        To_Account = to_Account;
    }
    @JsonIgnore
    public Integer getMsgLifeTime() {
        return MsgLifeTime;
    }
    @JsonIgnore
    public void setMsgLifeTime(Integer msgLifeTime) {
        MsgLifeTime = msgLifeTime;
    }
    @JsonIgnore
    public Integer getMsgRandom() {
        return MsgRandom;
    }
    @JsonIgnore
    public void setMsgRandom(Integer msgRandom) {
        MsgRandom = msgRandom;
    }
    @JsonIgnore
    public Integer getMsgTimeStamp() {
        return MsgTimeStamp;
    }
    @JsonIgnore
    public void setMsgTimeStamp(Integer msgTimeStamp) {
        MsgTimeStamp = msgTimeStamp;
    }
    @JsonIgnore
    public List<ImMsgBody> getMsgBody() {
        return MsgBody;
    }
    @JsonIgnore
    public void setMsgBody(List<ImMsgBody> msgBody) {
        MsgBody = msgBody;
    }

    @JsonIgnore
    public ImOfflinePushInfo getOfflinePushInfo() {
        return OfflinePushInfo;
    }
    @JsonIgnore
    public void setOfflinePushInfo(ImOfflinePushInfo offlinePushInfo) {
        OfflinePushInfo = offlinePushInfo;
    }
}
