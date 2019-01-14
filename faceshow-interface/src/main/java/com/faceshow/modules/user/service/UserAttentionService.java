package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserAttention;

import java.util.List;
import java.util.Map;

public interface UserAttentionService {
    int deleteByPrimaryKey(Integer attentionId);

    int insert(UserAttention record);

    int insertSelective(UserAttention record);

    UserAttention selectByPrimaryKey(Integer attentionId);

    int updateByPrimaryKeySelective(UserAttention record);

    int updateByPrimaryKey(UserAttention record);

    int selectByUserid(Integer userId, Integer account);

    int updateTu(Integer userId, Integer account, Boolean isTutual);

    int deleteByUserid(Integer userId, Integer account);

    List<Object> selectAll(Integer userId,Integer begin,Integer pageSize);

    List<Object> selectBeAll(Integer account,Integer begin,Integer pageSize);

    List<Object> selectAllShield(Integer userId,Integer begin,Integer pageSize);

    int deleteBack(Integer userId, Integer account);//拉黑

    int updateByUserIdAccount(Integer userId, Integer account, Byte shieldCircle);//是否屏蔽朋友圈动态

    List<Object> selectFriend(Integer userId,Integer begin,Integer pageSize);//好友列表
    int selectFriendNum(Integer userId);//好友数量


    int selectAllNum(Integer userId);



     int selectBeAllNum(Integer userId);


     int selectAllShieldNum(Integer userId);
}