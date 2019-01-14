package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserBelongGroup;
import com.faceshow.modules.user.entity.UserBelongGroupKey;

public interface UserBelongGroupService {
    int deleteByPrimaryKey(UserBelongGroupKey key);

    int insert(UserBelongGroup record);

    int insertSelective(UserBelongGroup record);

    UserBelongGroup selectByPrimaryKey(UserBelongGroupKey key);

    int updateByPrimaryKeySelective(UserBelongGroup record);

    int updateByPrimaryKey(UserBelongGroup record);
}