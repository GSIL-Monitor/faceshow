package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.service.UserBelongGroupService;
import com.faceshow.modules.user.dao.UserBelongGroupDao;
import com.faceshow.modules.user.entity.UserBelongGroup;
import com.faceshow.modules.user.entity.UserBelongGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Athor GuoChao
 * @Date Create in 12:42 2018/1/15
 */
@Service
public class UserBelongGroupServiceImpl implements UserBelongGroupService {

    @Autowired
    private UserBelongGroupDao userBelongGroupDao;

    @Override
    public int deleteByPrimaryKey(UserBelongGroupKey key) {
        return userBelongGroupDao.deleteByPrimaryKey(key);
    }

    @Override
    public int insert(UserBelongGroup record) {
        return userBelongGroupDao.insert(record);
    }

    @Override
    public int insertSelective(UserBelongGroup record) {
        return userBelongGroupDao.insertSelective(record);
    }

    @Override
    public UserBelongGroup selectByPrimaryKey(UserBelongGroupKey key) {
        return userBelongGroupDao.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(UserBelongGroup record) {
        return userBelongGroupDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserBelongGroup record) {
        return userBelongGroupDao.updateByPrimaryKey(record);
    }
}
