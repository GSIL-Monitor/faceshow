package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserRechargeLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRechargeLogDao {
    int deleteByPrimaryKey(Integer rechargeId);

    int insert(UserRechargeLog record);

    int insertSelective(UserRechargeLog record);

    UserRechargeLog selectByPrimaryKey(Integer rechargeId);

    int updateByPrimaryKeySelective(UserRechargeLog record);

    int updateByPrimaryKey(UserRechargeLog record);
}