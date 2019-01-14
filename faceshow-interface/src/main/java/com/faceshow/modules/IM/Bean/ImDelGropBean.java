package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImDelGropBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private String GroupId;
    @JsonProperty
    private String[]MemberToDel_Account;
    @JsonIgnore
    public String getGroupId() {
        return GroupId;
    }
    @JsonIgnore
    public void setGroupId(String groupId) {
        GroupId = groupId;
    }
    @JsonIgnore
    public String[] getMemberToDel_Account() {
        return MemberToDel_Account;
    }
    @JsonIgnore
    public void setMemberToDel_Account(String[] memberToDel_Account) {
        MemberToDel_Account = memberToDel_Account;
    }
}
