package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.dao.UserApplicationAnchorDao;
import com.faceshow.modules.user.entity.UserApplicationAnchor;
import com.faceshow.modules.user.service.UserApplicationAnchorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Athor GuoChao
 * @Date Create in 11:56 2018/1/15
 */
@Service
public class UserApplicationAnchorServiceImpl implements UserApplicationAnchorService {

    @Autowired
    private UserApplicationAnchorDao userApplicationAnchorDao;

    @Override
    public int deleteByPrimaryKey(Integer anchorId) {
        return userApplicationAnchorDao.deleteByPrimaryKey(anchorId);
    }

    @Override
    public int insert(UserApplicationAnchor record) {
        return userApplicationAnchorDao.insert(record);
    }

    @Override
    public int insertSelective(UserApplicationAnchor record) {
        return userApplicationAnchorDao.insertSelective(record);
    }

    @Override
    public UserApplicationAnchor selectByPrimaryKey(Integer anchorId) {
        return userApplicationAnchorDao.selectByPrimaryKey(anchorId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserApplicationAnchor record) {
        return userApplicationAnchorDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserApplicationAnchor record) {
        return userApplicationAnchorDao.updateByPrimaryKey(record);
    }
}
