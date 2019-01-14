package com.faceshow.common.utils.mapbean;


import java.io.Serializable;

public class Pois implements Serializable {

    private static final long serialVersionUID = 1L;

    private String addr;

    private String cp;

    private String direction;

    private String distance;

    private String name;

    private String poiType;

    private Point point;

    private String tag;

    private String tel;

    private String uid;

    private String zip;

    private Parent_poi parent_poi;

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCp() {
        return this.cp;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPoiType(String poiType) {
        this.poiType = poiType;
    }

    public String getPoiType() {
        return this.poiType;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return this.point;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return this.tel;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return this.uid;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return this.zip;
    }

    public void setParent_poi(Parent_poi parent_poi) {
        this.parent_poi = parent_poi;
    }

    public Parent_poi getParent_poi() {
        return this.parent_poi;
    }
}
