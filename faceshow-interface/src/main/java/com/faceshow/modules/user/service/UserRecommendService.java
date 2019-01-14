package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserRecommend;

public interface UserRecommendService {
    int deleteByPrimaryKey(Integer recommendId);

    int insert(UserRecommend record);

    int insertSelective(UserRecommend record);

    UserRecommend selectByPrimaryKey(Integer recommendId);

    int updateByPrimaryKeySelective(UserRecommend record);

    int updateByPrimaryKey(UserRecommend record);
}