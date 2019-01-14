package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImNumber implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value = "Member_Account")
    private  String member_Account;
    @JsonProperty(value = "Role")
    private  String role;

    public String getMember_Account() {
        return member_Account;
    }

    public void setMember_Account(String member_Account) {
        this.member_Account = member_Account;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
