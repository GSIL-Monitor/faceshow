package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.service.UserRechargeLogService;
import com.faceshow.modules.user.dao.UserRechargeLogDao;
import com.faceshow.modules.user.entity.UserRechargeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Athor GuoChao
 * @Date Create in 13:31 2018/1/15
 */
@Service
public class UserRechargeLogServiceImpl implements UserRechargeLogService {

    @Autowired
    private UserRechargeLogDao userRechargeLogDao;

    @Override
    public int deleteByPrimaryKey(Integer rechargeId) {
        return userRechargeLogDao.deleteByPrimaryKey(rechargeId);
    }

    @Override
    public int insert(UserRechargeLog record) {
        return userRechargeLogDao.insert(record);
    }

    @Override
    public int insertSelective(UserRechargeLog record) {
        return userRechargeLogDao.insertSelective(record);
    }

    @Override
    public UserRechargeLog selectByPrimaryKey(Integer rechargeId) {
        return userRechargeLogDao.selectByPrimaryKey(rechargeId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserRechargeLog record) {
        return userRechargeLogDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserRechargeLog record) {
        return userRechargeLogDao.updateByPrimaryKey(record);
    }
}
