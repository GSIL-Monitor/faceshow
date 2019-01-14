package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class GroupInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value = "ErrorCode")
    private int errorCode;
    @JsonProperty(value = "GroupId")
    private String groupId;
    @JsonProperty(value = "MemberList")
    private List<MemberList> memberList ;
    @JsonProperty(value = "MemberNum")
    private int memberNum;
    @JsonProperty(value = "Name")
    private String name;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<MemberList> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberList> memberList) {
        this.memberList = memberList;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
