package com.faceshow.modules.language;

import java.io.Serializable;

/**
 * 用户掌握的语言列表
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/19 15:36
 * -------------------------------------------------------------- <br>
 */
public class LanguageSelectRowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 语言id
     */
    private Integer languageId;

    /**
     * 语言名字
     */
    private String languageName;

    public LanguageSelectRowVo() {
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    @Override
    public String toString() {
        return "LanguageSelectRowVo{" +
                "languageId=" + languageId +
                ", languageName='" + languageName + '\'' +
                '}';
    }
}
