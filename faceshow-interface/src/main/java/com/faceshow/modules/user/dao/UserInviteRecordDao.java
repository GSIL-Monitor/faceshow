package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserInviteRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInviteRecordDao {
    int deleteByPrimaryKey(Integer recordId);

    int insert(UserInviteRecord record);

    int insertSelective(UserInviteRecord record);

    UserInviteRecord selectByPrimaryKey(Integer recordId);

    int updateByPrimaryKeySelective(UserInviteRecord record);

    int updateByPrimaryKey(UserInviteRecord record);
}