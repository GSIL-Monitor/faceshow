package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserAttention;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserAttentionDao {
    int deleteByPrimaryKey(Integer attentionId);

    int insert(UserAttention record);

    int insertSelective(UserAttention record);

    UserAttention selectByPrimaryKey(Integer attentionId);

    int updateByPrimaryKeySelective(UserAttention record);

    int updateByPrimaryKey(UserAttention record);

    Integer selectByUserid(Map<String, Object> map);

    Integer updateTu(Map<String, Object> map);

    Integer deleteByUserid(Map<String, Object> map);

    List<Object> selectAll(Map<String, Object> map);

    List<Object> selectBeAll(Map<String, Object> map);//被关注

    List<Object> selectAllShield(Map<String, Object> map);//屏蔽动态的列表

    Integer deleteBack(Map<String, Object> map);//拉黑

    int updateByUserIdAccount(Map<String, Object> record);//是否屏蔽朋友圈动态

    List<Object> selectFriend(Map<String, Object> map);

    int selectFriendNum(Integer userId);

    int selectAllNum(Integer userId);

    int selectBeAllNum(Integer userId);//被关注个数

    int selectAllShieldNum(Integer userId);//屏蔽动态的列表个数


    /**
     * 判断两人是否是好友关系
     *
     * @param map
     * @return
     */
    Integer selectFriendByUserIdAndAccount(Map<String, Object> map);

    /**
     * 判断当前用户是否已经关注其他用户
     *
     * @param map
     * @return
     */
    Integer isFollowByUserIdAndAccount(Map<String, Object> map);
}