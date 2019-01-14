package com.faceshow.common.utils.mapbean;


import java.io.Serializable;

public class PoiRegions implements Serializable {

    private static final long serialVersionUID = 1L;

    private String direction_desc;
    private String name;
    private String tag;
    private String uid;
    public void setDirection_desc(String direction_desc) {
        this.direction_desc = direction_desc;
    }
    public String getDirection_desc() {
        return direction_desc;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getTag() {
        return tag;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getUid() {
        return uid;
    }

}
