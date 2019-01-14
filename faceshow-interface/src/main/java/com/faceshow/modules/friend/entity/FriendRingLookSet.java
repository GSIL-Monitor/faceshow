package com.faceshow.modules.friend.entity;

import java.io.Serializable;

public class FriendRingLookSet implements Serializable {
    /**
     * 主键ID
     */
    private Integer setId;

    /**
     * 朋友圈主键
     */
    private Integer friendId;

    /**
     * 屏蔽或允许观看朋友圈用户(关联friend_ring中的 shield 0动态公开,  1屏蔽好友,  2指定某些人看, 3仅好友可看)
     */
    private Integer userId;

    /**
     * @用户昵称
     */
    private String nickName;

    private static final long serialVersionUID = 1L;

    public FriendRingLookSet() {
    }

    public FriendRingLookSet(Integer friendId, Integer userId) {
        this.friendId = friendId;
        this.userId = userId;
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", setId=").append(setId);
        sb.append(", friendId=").append(friendId);
        sb.append(", userId=").append(userId);
        sb.append(", nickName=").append(nickName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}