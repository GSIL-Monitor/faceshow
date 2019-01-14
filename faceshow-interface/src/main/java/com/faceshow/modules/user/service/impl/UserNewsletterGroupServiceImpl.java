package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.entity.UserNewsletterGroup;
import com.faceshow.modules.user.dao.UserNewsletterGroupDao;
import com.faceshow.modules.user.service.UserNewsletterGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Athor GuoChao
 * @Date Create in 13:27 2018/1/15
 */
@Service
public class UserNewsletterGroupServiceImpl implements UserNewsletterGroupService {

    @Autowired
    private UserNewsletterGroupDao userNewsletterGroupDao;

    @Override
    public int deleteByPrimaryKey(Integer newsletterId) {
        return userNewsletterGroupDao.deleteByPrimaryKey(newsletterId);
    }

    @Override
    public int insert(UserNewsletterGroup record) {
        return userNewsletterGroupDao.insert(record);
    }

    @Override
    public int insertSelective(UserNewsletterGroup record) {
        return userNewsletterGroupDao.insertSelective(record);
    }

    @Override
    public UserNewsletterGroup selectByPrimaryKey(Integer newsletterId) {
        return userNewsletterGroupDao.selectByPrimaryKey(newsletterId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserNewsletterGroup record) {
        return userNewsletterGroupDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserNewsletterGroup record) {
        return userNewsletterGroupDao.updateByPrimaryKey(record);
    }
}
