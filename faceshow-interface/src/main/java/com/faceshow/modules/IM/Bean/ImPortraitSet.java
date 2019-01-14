package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ImPortraitSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private String From_Account;//需要设置该Identifier的资料。
    @JsonProperty
    private List<ImProfileItemPortraitSet>ProfileItem;//待设置的用户的资料对象数组，数组中每一个对象都包含了Tag和Value。
    @JsonIgnore
    public String getFrom_Account() {
        return From_Account;
    }
    @JsonIgnore
    public void setFrom_Account(String from_Account) {
        From_Account = from_Account;
    }
    @JsonIgnore
    public List<ImProfileItemPortraitSet> getProfileItem() {
        return ProfileItem;
    }
    @JsonIgnore
    public void setProfileItem(List<ImProfileItemPortraitSet> profileItem) {
        ProfileItem = profileItem;
    }
}
