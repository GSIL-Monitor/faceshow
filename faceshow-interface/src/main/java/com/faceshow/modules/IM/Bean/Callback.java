package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Callback implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value = "From_Account")
    private String from_Account;// 发单聊消息之前回调 发送者
    @JsonProperty(value = "To_Account")
    private String to_Account;// 发单聊消息之前回调 接收者

    @JsonProperty(value = "CallbackCommand")
    private String callbackCommand;// 回调命令
    @JsonProperty(value = "GroupId")
    private String groupId;// 群组ID
    @JsonProperty(value = "Type")
    private String type; // 群组类型
    @JsonProperty(value = "ExitType")
    private String exitType; // 成员离开方式：Kicked-被踢；Quit-主动退群。
    @JsonProperty(value = "Operator_Account")
    private String operator_Account; // 操作者 // 操作者成员
    @JsonProperty(value = "ExitMemberList")
    private List<ExitMemberList> exitMemberList ;// 离开群的成员列表



    @JsonProperty(value = "NewMemberList")
    private List<ExitMemberList> newMemberList ;//  新入群成员列表
    @JsonProperty(value = "JoinType")
    private String joinType; // 入群方式：Apply（申请入群）；Invited（邀请入群）。

    public String getFrom_Account() {
        return from_Account;
    }

    public void setFrom_Account(String from_Account) {
        this.from_Account = from_Account;
    }

    public String getTo_Account() {
        return to_Account;
    }

    public void setTo_Account(String to_Account) {
        this.to_Account = to_Account;
    }

    public String getCallbackCommand() {
        return callbackCommand;
    }

    public void setCallbackCommand(String callbackCommand) {
        this.callbackCommand = callbackCommand;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExitType() {
        return exitType;
    }

    public void setExitType(String exitType) {
        this.exitType = exitType;
    }

    public String getOperator_Account() {
        return operator_Account;
    }

    public void setOperator_Account(String operator_Account) {
        this.operator_Account = operator_Account;
    }

    public List<ExitMemberList> getExitMemberList() {
        return exitMemberList;
    }

    public void setExitMemberList(List<ExitMemberList> exitMemberList) {
        this.exitMemberList = exitMemberList;
    }

    public List<ExitMemberList> getNewMemberList() {
        return newMemberList;
    }

    public void setNewMemberList(List<ExitMemberList> newMemberList) {
        this.newMemberList = newMemberList;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    @Override
    public String toString() {
        return "Callback{" +
                "from_Account='" + from_Account + '\'' +
                ", to_Account='" + to_Account + '\'' +
                ", callbackCommand='" + callbackCommand + '\'' +
                ", groupId='" + groupId + '\'' +
                ", type='" + type + '\'' +
                ", exitType='" + exitType + '\'' +
                ", operator_Account='" + operator_Account + '\'' +
                ", exitMemberList=" + exitMemberList +
                ", newMemberList=" + newMemberList +
                ", joinType='" + joinType + '\'' +
                '}';
    }
}
