package com.faceshow.modules.IM.Bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImProfileItemPortraitSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private String Tag;
    @JsonProperty
    private String Value;
    @JsonIgnore
    public String getTag() {
        return Tag;
    }
    @JsonIgnore
    public void setTag(String tag) {
        Tag = tag;
    }
    @JsonIgnore
    public String getValue() {
        return Value;
    }
    @JsonIgnore
    public void setValue(String value) {
        Value = value;
    }

}
