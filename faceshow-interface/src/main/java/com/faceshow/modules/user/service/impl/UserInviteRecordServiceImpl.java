package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.dao.UserInviteRecordDao;
import com.faceshow.modules.user.entity.UserInviteRecord;
import com.faceshow.modules.user.service.UserInviteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Athor GuoChao
 * @Date Create in 13:15 2018/1/15
 */
@Service
public class UserInviteRecordServiceImpl implements UserInviteRecordService {

    @Autowired
    private UserInviteRecordDao userInviteRecordDao;

    @Override
    public int deleteByPrimaryKey(Integer recordId) {
        return userInviteRecordDao.deleteByPrimaryKey(recordId);
    }

    @Override
    public int insert(UserInviteRecord record) {
        return userInviteRecordDao.insert(record);
    }

    @Override
    public int insertSelective(UserInviteRecord record) {
        return userInviteRecordDao.insertSelective(record);
    }

    @Override
    public UserInviteRecord selectByPrimaryKey(Integer recordId) {
        return userInviteRecordDao.selectByPrimaryKey(recordId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserInviteRecord record) {
        return userInviteRecordDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserInviteRecord record) {
        return userInviteRecordDao.updateByPrimaryKey(record);
    }
}
