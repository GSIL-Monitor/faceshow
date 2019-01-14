package com.faceshow.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class SysCountry implements Serializable {
    /**
     * 国家信息ID主键
     */
    private Integer countryId;

    /**
     * 国家名称
     */
    private String cname;

    /**
     * 国家唯一标识
     */
    private String coding;

    /**
     * 国际电话区号, 例如中国是: 86
     */
    private Integer countryNum;

    /**
     * 国家标志
     */
    private String logo;

    /**
     * 国家说明
     */
    private String memo;

    /**
     * 所属语言
     */
    private String language;

    private static final long serialVersionUID = 1L;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding == null ? null : coding.trim();
    }

    public Integer getCountryNum() {
        return countryNum;
    }

    public void setCountryNum(Integer countryNum) {
        this.countryNum = countryNum;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", countryId=").append(countryId);
        sb.append(", cname=").append(cname);
        sb.append(", coding=").append(coding);
        sb.append(", countryNum=").append(countryNum);
        sb.append(", logo=").append(logo);
        sb.append(", memo=").append(memo);
        sb.append(", language=").append(language);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}