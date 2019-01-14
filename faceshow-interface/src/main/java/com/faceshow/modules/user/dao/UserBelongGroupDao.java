package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserBelongGroupKey;
import com.faceshow.modules.user.entity.UserBelongGroup;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserBelongGroupDao {
    int deleteByPrimaryKey(UserBelongGroupKey key);

    int insert(UserBelongGroup record);

    int insertSelective(UserBelongGroup record);

    UserBelongGroup selectByPrimaryKey(UserBelongGroupKey key);

    int updateByPrimaryKeySelective(UserBelongGroup record);

    int updateByPrimaryKey(UserBelongGroup record);
}