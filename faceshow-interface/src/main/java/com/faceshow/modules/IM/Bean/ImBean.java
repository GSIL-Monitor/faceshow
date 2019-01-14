package com.faceshow.modules.IM.Bean;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;


public class ImBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value = "Owner_Account")
    private String owner_Account;
    @JsonProperty(value = "Type")
    private String type;
    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "Introduction")
    private String introduction;
    @JsonProperty(value = "Notification")
    private String notification;
    @JsonProperty(value = "FaceUrl")
    private String faceUrl;
    @JsonProperty(value = "GroupId")
    private String groupId;
    @JsonProperty(value = "MemberList")
    private List<ImNumber> memberList;

    public String getOwner_Account() {
        return owner_Account;
    }

    public void setOwner_Account(String owner_Account) {
        this.owner_Account = owner_Account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<ImNumber> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<ImNumber> memberList) {
        this.memberList = memberList;
    }
}
