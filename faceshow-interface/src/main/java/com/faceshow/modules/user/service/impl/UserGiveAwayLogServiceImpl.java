package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.dao.UserGiveAwayLogDao;
import com.faceshow.modules.user.entity.UserGiveAwayLog;
import com.faceshow.modules.user.service.UserGiveAwayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Athor GuoChao
 * @Date Create in 13:09 2018/1/15
 */
@Service
public class UserGiveAwayLogServiceImpl implements UserGiveAwayLogService {

    @Autowired
    private UserGiveAwayLogDao userGiveAwayLogDao;

    @Override
    public int deleteByPrimaryKey(Integer giveId) {
        return userGiveAwayLogDao.deleteByPrimaryKey(giveId);
    }

    @Override
    public int insertSelective(Map<String, Object> map) {
        return userGiveAwayLogDao.insertSelective(map);
    }

    @Override
    public UserGiveAwayLog selectByPrimaryKey(Integer giveId) {
        return userGiveAwayLogDao.selectByPrimaryKey(giveId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserGiveAwayLog record) {
        return userGiveAwayLogDao.updateByPrimaryKeySelective(record);
    }
}
