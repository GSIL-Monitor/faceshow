package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserSet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserSetDao {

    int insertSelective(UserSet record);

    UserSet selectByPrimaryKey(Integer setId);

    int updateByUserId(UserSet record);

    UserSet selectByDistance(Integer recode);
}