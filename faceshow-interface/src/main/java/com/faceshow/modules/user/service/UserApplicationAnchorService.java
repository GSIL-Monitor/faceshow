package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserApplicationAnchor;

public interface UserApplicationAnchorService {
    int deleteByPrimaryKey(Integer anchorId);

    int insert(UserApplicationAnchor record);

    int insertSelective(UserApplicationAnchor record);

    UserApplicationAnchor selectByPrimaryKey(Integer anchorId);

    int updateByPrimaryKeySelective(UserApplicationAnchor record);

    int updateByPrimaryKey(UserApplicationAnchor record);
}