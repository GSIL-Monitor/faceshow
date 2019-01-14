package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserInviteRecord;

public interface UserInviteRecordService {
    int deleteByPrimaryKey(Integer recordId);

    int insert(UserInviteRecord record);

    int insertSelective(UserInviteRecord record);

    UserInviteRecord selectByPrimaryKey(Integer recordId);

    int updateByPrimaryKeySelective(UserInviteRecord record);

    int updateByPrimaryKey(UserInviteRecord record);
}