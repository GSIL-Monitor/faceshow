package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UsersLevel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersLevelDao {
    int deleteByPrimaryKey(Integer levelId);

    int insert(UsersLevel record);

    int insertSelective(UsersLevel record);

    UsersLevel selectByPrimaryKey(Integer levelId);

    int updateByPrimaryKeySelective(UsersLevel record);

    int updateByPrimaryKey(UsersLevel record);
}