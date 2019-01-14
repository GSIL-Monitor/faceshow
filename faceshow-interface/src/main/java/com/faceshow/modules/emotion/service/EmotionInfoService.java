package com.faceshow.modules.emotion.service;

import com.faceshow.modules.makeFriend.vo.MakeFriendIntentionSelectRowVo;

import java.util.List;

/**
 * 用户情感状态操作Service
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 11:55
 * -------------------------------------------------------------- <br>
 */
public interface EmotionInfoService {

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
     * @param tagId  标签id数组
     * @return
     */
    int updateTagByUserId(Integer userId, Integer tagId);
}
