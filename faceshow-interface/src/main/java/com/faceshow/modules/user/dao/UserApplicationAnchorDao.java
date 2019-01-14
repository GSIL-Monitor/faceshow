package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserApplicationAnchor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserApplicationAnchorDao {
    int deleteByPrimaryKey(Integer anchorId);

    int insert(UserApplicationAnchor record);

    int insertSelective(UserApplicationAnchor record);

    UserApplicationAnchor selectByPrimaryKey(Integer anchorId);

    int updateByPrimaryKeySelective(UserApplicationAnchor record);

    int updateByPrimaryKey(UserApplicationAnchor record);
}