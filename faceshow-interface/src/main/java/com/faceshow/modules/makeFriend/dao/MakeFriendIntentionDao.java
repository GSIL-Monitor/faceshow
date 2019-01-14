package com.faceshow.modules.makeFriend.dao;

import com.faceshow.modules.makeFriend.vo.MakeFriendIntentionSelectRowVo;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户交友意向操作Service
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 13:56
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface MakeFriendIntentionDao extends BaseDao<Object> {

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
    List<String> findByUserId(Integer userId);

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
    int saveTagByUserId(@Param("userId") Integer userId, @Param("ids") Integer[] ids, @Param("date") Date date);

    /**
     * 根据交友标签id查询用户id
     *
     * @param makeFriendIds 交友状态id
     * @return
     */
    List<Integer> findUserIdByFriendId(Integer[] makeFriendIds);
}