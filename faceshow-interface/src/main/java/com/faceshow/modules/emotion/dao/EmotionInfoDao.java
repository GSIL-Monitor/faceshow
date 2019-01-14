package com.faceshow.modules.emotion.dao;


import com.faceshow.modules.makeFriend.vo.MakeFriendIntentionSelectRowVo;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


/**
 * 用户情感状态操作Dao
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 11:55
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface EmotionInfoDao extends BaseDao<Object> {

    /**
     * 查询所有标签, 并判定当前用户是否已经拥有该标签
     *
     * @param userId 用户id
     * @return
     */
    List<MakeFriendIntentionSelectRowVo> findAll(Integer userId);

    /**
     * 查询用户拥有的标签
     *
     * @param userId 用户id
     * @return
     */
    String findByUserId(Integer userId);

    /**
     * 根据用户id删除用户拥有的标签
     *
     * @param userId 用户id
     * @return
     */
    int deleteByUserId(Integer userId);

    /**
     * 添加用户拥有的标签
     *
     * @param userId 用户id
     * @param tagId  标签id数组
     * @return
     */
    int saveTagByUserId(@Param("userId") Integer userId, @Param("tagId") Integer tagId, @Param("date") Date date);
}