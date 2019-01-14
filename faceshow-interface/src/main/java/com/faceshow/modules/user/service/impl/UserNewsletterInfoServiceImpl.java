package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.dao.UserNewsletterInfoDao;
import com.faceshow.modules.user.entity.UserNewsletterInfo;
import com.faceshow.modules.user.entity.UserNewsletterInfoKey;
import com.faceshow.modules.user.service.UserNewsletterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Athor GuoChao
 * @Date Create in 13:30 2018/1/15
 */
@Service
public class UserNewsletterInfoServiceImpl implements UserNewsletterInfoService {

    @Autowired
    private UserNewsletterInfoDao userNewsletterInfoDao;

    @Override
    public int deleteByPrimaryKey(UserNewsletterInfoKey key) {
        return userNewsletterInfoDao.deleteByPrimaryKey(key);
    }

    @Override
    public int insert(UserNewsletterInfo record) {
        return userNewsletterInfoDao.insert(record);
    }

    @Override
    public int insertSelective(UserNewsletterInfo record) {
        return userNewsletterInfoDao.insertSelective(record);
    }

    @Override
    public UserNewsletterInfo selectByPrimaryKey(UserNewsletterInfoKey key) {
        return userNewsletterInfoDao.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(UserNewsletterInfo record) {
        return userNewsletterInfoDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserNewsletterInfo record) {
        return userNewsletterInfoDao.updateByPrimaryKey(record);
    }
}
