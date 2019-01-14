package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.entity.UserInfoDetail;
import com.faceshow.modules.user.service.UserInfoDetailService;
import com.faceshow.modules.user.dao.UserInfoDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Athor GuoChao
 * @Date Create in 13:11 2018/1/15
 */
@Service
public class UserInfoDetailServiceImpl implements UserInfoDetailService {

    @Autowired
    private UserInfoDetailDao userInfoDetailDao;

    @Override
    public int deleteByPrimaryKey(Integer levelId) {
        return userInfoDetailDao.deleteByPrimaryKey(levelId);
    }


    @Override
    public int insertSelective(UserInfoDetail record) {
        return userInfoDetailDao.insertSelective(record);
    }

    @Override
    public UserInfoDetail selectByPrimaryKey(Integer levelId) {
        return userInfoDetailDao.selectByPrimaryKey(levelId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserInfoDetail record) {
        return userInfoDetailDao.updateByPrimaryKeySelective(record);
    }


    /**
     * 根据用户id查询用户详情
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public UserInfoDetail selectByUserId(Integer userId) {
        return userInfoDetailDao.selectByUserId(userId);
    }

    /**
     * 查询用户拥有的钻石数量
     *
     * @param userId 用户id
     * @return 钻室数量
     */
    @Override
    public Integer findDiamondByUserId(Integer userId) {
        return userInfoDetailDao.findDiamondByUserId(userId);
    }
    /**
     *@Author:YS
     *@Description: 查询我的钱包
     *@Date:2018/4/9
     *@param:
     */
    @Override
    public Map<String, Object> selectWallet(Integer userId) {
        return userInfoDetailDao.selectWallet(userId);
    }
}
