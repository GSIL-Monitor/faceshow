package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserNewsletterGroup;

public interface UserNewsletterGroupService {
    int deleteByPrimaryKey(Integer newsletterId);

    int insert(UserNewsletterGroup record);

    int insertSelective(UserNewsletterGroup record);

    UserNewsletterGroup selectByPrimaryKey(Integer newsletterId);

    int updateByPrimaryKeySelective(UserNewsletterGroup record);

    int updateByPrimaryKey(UserNewsletterGroup record);
}