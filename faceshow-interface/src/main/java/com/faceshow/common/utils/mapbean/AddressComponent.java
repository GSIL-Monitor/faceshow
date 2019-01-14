package com.faceshow.common.utils.mapbean;

import java.io.Serializable;


public class AddressComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String country;

    private int country_code;

    private String country_code_iso;

    private String country_code_iso2;

    private String province;

    private String city;

    private int city_level;

    private String district;

    private String town;

    private String adcode;

    private String street;

    private String street_number;

    private String direction;

    private String distance;

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry_code(int country_code) {
        this.country_code = country_code;
    }

    public int getCountry_code() {
        return this.country_code;
    }

    public void setCountry_code_iso(String country_code_iso) {
        this.country_code_iso = country_code_iso;
    }

    public String getCountry_code_iso() {
        return this.country_code_iso;
    }

    public void setCountry_code_iso2(String country_code_iso2) {
        this.country_code_iso2 = country_code_iso2;
    }

    public String getCountry_code_iso2() {
        return this.country_code_iso2;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return this.province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity_level(int city_level) {
        this.city_level = city_level;
    }

    public int getCity_level() {
        return this.city_level;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getTown() {
        return this.town;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getAdcode() {
        return this.adcode;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public String getStreet_number() {
        return this.street_number;
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
}
