package com.faceshow.common.utils.mapbean;

import java.io.Serializable;
import java.util.List;


public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    private Location location;

    private String formatted_address;

    private String business;

    private AddressComponent addressComponent;

    private List<Pois> pois;

    private List<Roads> roads;

    private List<PoiRegions> poiRegions;

    private String sematic_description;

    private int cityCode;

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getFormatted_address() {
        return this.formatted_address;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getBusiness() {
        return this.business;
    }

    public void setAddressComponent(AddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

    public AddressComponent getAddressComponent() {
        return this.addressComponent;
    }

    public void setPois(List<Pois> pois) {
        this.pois = pois;
    }

    public List<Pois> getPois() {
        return this.pois;
    }

    public void setRoads(List<Roads> roads) {
        this.roads = roads;
    }

    public List<Roads> getRoads() {
        return this.roads;
    }

    public void setPoiRegions(List<PoiRegions> poiRegions) {
        this.poiRegions = poiRegions;
    }

    public List<PoiRegions> getPoiRegions() {
        return this.poiRegions;
    }

    public void setSematic_description(String sematic_description) {
        this.sematic_description = sematic_description;
    }

    public String getSematic_description() {
        return this.sematic_description;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getCityCode() {
        return this.cityCode;
    }

}
