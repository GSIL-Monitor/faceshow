package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserRecommend;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRecommendDao {
    int deleteByPrimaryKey(Integer recommendId);

    int insert(UserRecommend record);

    int insertSelective(UserRecommend record);

    UserRecommend selectByPrimaryKey(Integer recommendId);

    int updateByPrimaryKeySelective(UserRecommend record);

    int updateByPrimaryKey(UserRecommend record);
}