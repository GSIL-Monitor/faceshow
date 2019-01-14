package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ExitMemberList implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value = "Member_Account")
    private String member_Account;

    public String getMember_Account() {
        return member_Account;
    }

    public void setMember_Account(String member_Account) {
        this.member_Account = member_Account;
    }
}
