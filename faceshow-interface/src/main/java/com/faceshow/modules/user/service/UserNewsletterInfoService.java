package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserNewsletterInfo;
import com.faceshow.modules.user.entity.UserNewsletterInfoKey;

public interface UserNewsletterInfoService {
    int deleteByPrimaryKey(UserNewsletterInfoKey key);

    int insert(UserNewsletterInfo record);

    int insertSelective(UserNewsletterInfo record);

    UserNewsletterInfo selectByPrimaryKey(UserNewsletterInfoKey key);

    int updateByPrimaryKeySelective(UserNewsletterInfo record);

    int updateByPrimaryKey(UserNewsletterInfo record);
}