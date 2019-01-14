package com.faceshow.modules.friend.entity;

import java.io.Serializable;
import java.util.Date;

public class FriendRing implements Serializable {
    /**
     * 朋友圈ID主键
     */
    private Integer friendId;

    /**
     * 发布人(那个注册用户创建的分组信息)
     */
    private Integer userId;

    /**
     * 发布内容
     */
    private String content;

    /**
     * 当前地址
     */
    private String address;

    /**
     * 地址是否公开, 0 不公开, 1公开
     */
    private Byte isPublic;

    /**
     * 图片
     */
    private String img;

    /**
     * 点赞数
     */
    private Integer likeNum;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 是否审核 0 未审核 1 审核通过
     */
    private Byte isCheck;

    /**
     * 审核时间
     */
    private Date checkTime;

    /**
     * 审核人
     */
    private String checkMan;

    /**
     * 朋友圈发布类型: 1图片 2视频 3纯文字
     */
    private Byte type;

    /**
     * 是否同步到我的视频: 0同步, 1不同步
     */
    private Byte sync;

    /**
     * type为2并且sync为0时, 同步到视频列表, 此为视频id
     */
    private Integer videoId;

    /**
     * 0动态公开,  1屏蔽好友,  2指定某些人看, 3仅好友可看, 4提醒好友查看
     */
    private Byte shield;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 维度
     */
    private String latitude;

    private static final long serialVersionUID = 1L;

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Byte getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Byte isPublic) {
        this.isPublic = isPublic;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Byte isCheck) {
        this.isCheck = isCheck;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckMan() {
        return checkMan;
    }

    public void setCheckMan(String checkMan) {
        this.checkMan = checkMan == null ? null : checkMan.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getSync() {
        return sync;
    }

    public void setSync(Byte sync) {
        this.sync = sync;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Byte getShield() {
        return shield;
    }

    public void setShield(Byte shield) {
        this.shield = shield;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", friendId=").append(friendId);
        sb.append(", userId=").append(userId);
        sb.append(", content=").append(content);
        sb.append(", address=").append(address);
        sb.append(", isPublic=").append(isPublic);
        sb.append(", img=").append(img);
        sb.append(", likeNum=").append(likeNum);
        sb.append(", createTime=").append(createTime);
        sb.append(", isCheck=").append(isCheck);
        sb.append(", checkTime=").append(checkTime);
        sb.append(", checkMan=").append(checkMan);
        sb.append(", type=").append(type);
        sb.append(", sync=").append(sync);
        sb.append(", videoId=").append(videoId);
        sb.append(", shield=").append(shield);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}