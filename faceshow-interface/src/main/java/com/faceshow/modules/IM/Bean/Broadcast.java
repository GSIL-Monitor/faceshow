package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Broadcast implements Serializable {
    private static final long serialVersionUID = 1L;
   /* @JsonProperty(value = "Owner_Account")
    private String owner_Account;//群主ID*/
    @JsonProperty(value = "Type")
    private String type="AVChatRoom";//类型 包括Public（公开群），Private（私密群），ChatRoom（聊天室），AVChatRoom（互动直播聊天室），BChatRoom（在线成员广播大群）。
    @JsonProperty(value = "Name")
    private String name;

  /*  public String getOwner_Account() {
        return owner_Account;
    }

    public void setOwner_Account(String owner_Account) {
        this.owner_Account = owner_Account;
    }*/

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
}
