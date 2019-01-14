package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.entity.UsersLevel;
import com.faceshow.modules.user.service.UsersLevelService;
import com.faceshow.modules.user.dao.UsersLevelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Athor GuoChao
 * @Date Create in 13:40 2018/1/15
 */
@Service
public class UsersLevelServiceImpl implements UsersLevelService {

    @Autowired
    private UsersLevelDao usersLevelDao;

    @Override
    public int deleteByPrimaryKey(Integer levelId) {
        return usersLevelDao.deleteByPrimaryKey(levelId);
    }

    @Override
    public int insert(UsersLevel record) {
        return usersLevelDao.insert(record);
    }

    @Override
    public int insertSelective(UsersLevel record) {
        return usersLevelDao.insertSelective(record);
    }

    @Override
    public UsersLevel selectByPrimaryKey(Integer levelId) {
        return usersLevelDao.selectByPrimaryKey(levelId);
    }

    @Override
    public int updateByPrimaryKeySelective(UsersLevel record) {
        return usersLevelDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UsersLevel record) {
        return usersLevelDao.updateByPrimaryKey(record);
    }
}
