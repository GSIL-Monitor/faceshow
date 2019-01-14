package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserRechargeLog;

public interface UserRechargeLogService {
    int deleteByPrimaryKey(Integer rechargeId);

    int insert(UserRechargeLog record);

    int insertSelective(UserRechargeLog record);

    UserRechargeLog selectByPrimaryKey(Integer rechargeId);

    int updateByPrimaryKeySelective(UserRechargeLog record);

    int updateByPrimaryKey(UserRechargeLog record);
}