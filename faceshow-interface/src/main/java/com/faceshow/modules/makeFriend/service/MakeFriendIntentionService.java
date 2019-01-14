package com.faceshow.modules.makeFriend.service;

import com.faceshow.modules.makeFriend.vo.MakeFriendIntentionSelectRowVo;

import java.util.List;

/**
 * 用户交友意向操作Service
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 13:56
 * -------------------------------------------------------------- <br>
 */
public interface MakeFriendIntentionService {

    /**
     * 查询所有标签, 并判定当前用户是否已经拥有该标签
     *
     * @param userId 用户id
     * @return
     */
    List<MakeFriendIntentionSelectRowVo> findAll(Integer userId);

    /**
     * 根据用户id删除用户拥有的标签
     *
     * @param userId 用户id
     * @return
     */
    int deleteByUserId(Integer userId);

    /**
     * 修改用户拥有的标签
     *
     * @param userId 用户id
     * @param ids    标签id数组
     * @return
     */
    int updateTagByUserId(Integer userId, Integer[] ids);
}
