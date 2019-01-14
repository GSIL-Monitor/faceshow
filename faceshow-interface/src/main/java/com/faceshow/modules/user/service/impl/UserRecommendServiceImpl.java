package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.service.UserRecommendService;
import com.faceshow.modules.user.dao.UserRecommendDao;
import com.faceshow.modules.user.entity.UserRecommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Athor GuoChao
 * @Date Create in 13:34 2018/1/15
 */
@Service
public class UserRecommendServiceImpl implements UserRecommendService {

    @Autowired
    private UserRecommendDao userRecommendDao;

    @Override
    public int deleteByPrimaryKey(Integer recommendId) {
        return userRecommendDao.deleteByPrimaryKey(recommendId);
    }

    @Override
    public int insert(UserRecommend record) {
        return userRecommendDao.insert(record);
    }

    @Override
    public int insertSelective(UserRecommend record) {
        return userRecommendDao.insertSelective(record);
    }

    @Override
    public UserRecommend selectByPrimaryKey(Integer recommendId) {
        return userRecommendDao.selectByPrimaryKey(recommendId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserRecommend record) {
        return userRecommendDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserRecommend record) {
        return userRecommendDao.updateByPrimaryKey(record);
    }
}
