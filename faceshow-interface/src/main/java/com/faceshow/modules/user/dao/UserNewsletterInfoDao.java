package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserNewsletterInfo;
import com.faceshow.modules.user.entity.UserNewsletterInfoKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserNewsletterInfoDao {
    int deleteByPrimaryKey(UserNewsletterInfoKey key);

    int insert(UserNewsletterInfo record);

    int insertSelective(UserNewsletterInfo record);

    UserNewsletterInfo selectByPrimaryKey(UserNewsletterInfoKey key);

    int updateByPrimaryKeySelective(UserNewsletterInfo record);

    int updateByPrimaryKey(UserNewsletterInfo record);
}