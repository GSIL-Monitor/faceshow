package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MemberList implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value = "Member_Account")
    private String member_Account;
    @JsonProperty(value = "Role")
    private String role;
    @JsonProperty(value = "Img")
    private String img;
    @JsonProperty(value = "Nick_name")
    private String nick_name;
    @JsonProperty(value = "Signature")
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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

    @Override
    public String toString() {
        return "MemberList{" +
                "member_Account='" + member_Account + '\'' +
                ", role='" + role + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
