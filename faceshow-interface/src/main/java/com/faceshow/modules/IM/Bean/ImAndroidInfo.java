package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImAndroidInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private String Sound;
    @JsonIgnore
    public String getSound() {
        return Sound;
    }
    @JsonIgnore
    public void setSound(String sound) {
        Sound = sound;
    }
}
