package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.AppFeedback;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppFeedbackDao {
    int deleteByPrimaryKey(Integer feedblackId);

    int insert(AppFeedback record);

    int insertSelective(AppFeedback record);

    AppFeedback selectByPrimaryKey(Integer feedblackId);

    int updateByPrimaryKeySelective(AppFeedback record);

    int updateByPrimaryKey(AppFeedback record);
}