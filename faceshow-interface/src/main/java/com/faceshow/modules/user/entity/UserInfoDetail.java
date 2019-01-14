package com.faceshow.modules.user.entity;

import java.io.Serializable;
import java.util.Date;

public class UserInfoDetail implements Serializable {
    /**
     * 会员详细ID主键
     */
    private Integer detailId;

    /**
     * 会员ID主键
     */
    private Integer userId;

    /**
     * 会员名称
     */
    private String userName;

    /**
     * 会员钻石数
     */
    private Integer diamond;

    /**
     * F币
     */
    private Integer f;

    /**
     * 直播收入
     */
    private Integer liveIncome;

    /**
     * 短视频收入
     */
    private Integer videoIncome;

    /**
     * 1V1收入
     */
    private Integer matchingIncome;

    /**
     * 修改时间
     */
    private Date editorTime;

    /**
     * 修改人
     */
    private Integer editor;

    /**
     * 说明
     */
    private String memo;

    /**
     * 是否热门主播 主播标签(如：本月热门，本周热门，年度热门等) 0  否 1 是
     */
    private Byte isTags;

    /**
     * 每天需要直播任务时长
     */
    private Integer anchorTime;

    /**
     * 会员所属母语
     */
    private String language;

    /**
     * 国籍
     */
    private String citizenship;

    /**
     * 现居住地(暂时只选国家，在国家信息表中的信息)
     */
    private String nowAddress;

    public UserInfoDetail() {
    }

    public UserInfoDetail(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getNowAddress() {
        return nowAddress;
    }

    public void setNowAddress(String nowAddress) {
        this.nowAddress = nowAddress;
    }

    private static final long serialVersionUID = 1L;

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getDiamond() {
        return diamond;
    }

    public void setDiamond(Integer diamond) {
        this.diamond = diamond;
    }

    public Integer getF() {
        return f;
    }

    public void setF(Integer f) {
        this.f = f;
    }

    public Integer getLiveIncome() {
        return liveIncome;
    }

    public void setLiveIncome(Integer liveIncome) {
        this.liveIncome = liveIncome;
    }

    public Integer getVideoIncome() {
        return videoIncome;
    }

    public void setVideoIncome(Integer videoIncome) {
        this.videoIncome = videoIncome;
    }

    public Integer getMatchingIncome() {
        return matchingIncome;
    }

    public void setMatchingIncome(Integer matchingIncome) {
        this.matchingIncome = matchingIncome;
    }

    public Date getEditorTime() {
        return editorTime;
    }

    public void setEditorTime(Date editorTime) {
        this.editorTime = editorTime;
    }

    public Integer getEditor() {
        return editor;
    }

    public void setEditor(Integer editor) {
        this.editor = editor;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Byte getIsTags() {
        return isTags;
    }

    public void setIsTags(Byte isTags) {
        this.isTags = isTags;
    }

    public Integer getAnchorTime() {
        return anchorTime;
    }

    public void setAnchorTime(Integer anchorTime) {
        this.anchorTime = anchorTime;
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
        sb.append(", detailId=").append(detailId);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", diamond=").append(diamond);
        sb.append(", f=").append(f);
        sb.append(", liveIncome=").append(liveIncome);
        sb.append(", videoIncome=").append(videoIncome);
        sb.append(", matchingIncome=").append(matchingIncome);
        sb.append(", editorTime=").append(editorTime);
        sb.append(", editor=").append(editor);
        sb.append(", memo=").append(memo);
        sb.append(", isTags=").append(isTags);
        sb.append(", anchorTime=").append(anchorTime);
        sb.append(", language=").append(language);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}