package com.faceshow.modules.qa.dao;

import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 问答点赞信息操作
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/20 15:55
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface QaLikeDao extends BaseDao<Object> {

    /**
     * 根据用户id和问答id删除点赞记录
     *
     * @param userId 用户id
     * @param infoId 问答id
     * @return
     */
    int deleteByUserIdAndInfoId(@Param("userId") Integer userId, @Param("infoId") Integer infoId);

    /**
     * 根据问答id和用户id判断是否点赞
     *
     * @param infoId 问答id
     * @param userId 用户id
     * @return
     */
    Integer findByInfoIdAndUserId(@Param("userId") Integer userId, @Param("infoId") Integer infoId);
}
