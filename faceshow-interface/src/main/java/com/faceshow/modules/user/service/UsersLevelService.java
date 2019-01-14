package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UsersLevel;

public interface UsersLevelService {
    int deleteByPrimaryKey(Integer levelId);

    int insert(UsersLevel record);

    int insertSelective(UsersLevel record);

    UsersLevel selectByPrimaryKey(Integer levelId);

    int updateByPrimaryKeySelective(UsersLevel record);

    int updateByPrimaryKey(UsersLevel record);
}