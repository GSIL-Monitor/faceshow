package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserInfoDetail;

import java.util.Map;

public interface UserInfoDetailService {
    int deleteByPrimaryKey(Integer levelId);

    int insertSelective(UserInfoDetail record);

    UserInfoDetail selectByPrimaryKey(Integer levelId);

    int updateByPrimaryKeySelective(UserInfoDetail record);

    /**
     * 根据用户id查询用户详情
     *
     * @param userId 用户id
     * @return
     */
    UserInfoDetail selectByUserId(Integer userId);

    /**
     * 查询用户拥有的钻石数量
     *
     * @param userId 用户id
     * @return 钻室数量
     */
    Integer findDiamondByUserId(Integer userId);
    /**
     *@Author:YS
     *@Description: 查询我的钱包
     *@Date:2018/4/9
     *@param:
     */
    Map<String,Object> selectWallet(Integer userId);
}