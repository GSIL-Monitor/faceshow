package com.faceshow.modules.IM.Bean;

import com.faceshow.common.utils.InvertCodeGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ImSendGroupMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private String GroupId;//群ID 也就是IMID
    @JsonProperty
    private String From_Account;//指定消息发送者（选填） 不指定就是管理员发送
    @JsonProperty
    private Integer Random= InvertCodeGenerator.getNumber();//生成随机数 随机数字，五分钟数字相同认为是重复消息
    @JsonProperty
    private String MsgPriority;//可以指定消息的优先级，默认优先级Normal； 可以指定4种优先级，从高到低依次为High，Normal，Low，Lowest，区分大小写。
    @JsonProperty
    private List<ImMsgBody> MsgBody;//消息内容，具体格式请参考消息格式描述。（注意，一条消息可包括多种消息元素，MsgBody为Array类型）
    @JsonProperty
    private  ImOfflinePushInfo OfflinePushInfo;// 	离线推送信息配置，具体可参考消息格式描述。
    @JsonIgnore
    public String getGroupId() {
        return GroupId;
    }
    @JsonIgnore
    public void setGroupId(String groupId) {
        GroupId = groupId;
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
    public Integer getRandom() {
        return Random;
    }
    @JsonIgnore
    public void setRandom(Integer random) {
        Random = random;
    }
    @JsonIgnore
    public String getMsgPriority() {
        return MsgPriority;
    }
    @JsonIgnore
    public void setMsgPriority(String msgPriority) {
        MsgPriority = msgPriority;
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
