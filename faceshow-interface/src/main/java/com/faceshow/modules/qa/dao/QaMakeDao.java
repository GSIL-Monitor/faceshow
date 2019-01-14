package com.faceshow.modules.qa.dao;


import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 问答吐槽操作Dao
 * <p>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:28
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface QaMakeDao extends BaseDao<Object> {

    /**
     * 根据用户id和问答id删除问答吐槽
     *
     * @param userId 用户id
     * @param infoId 问答id
     * @return
     */
    int deleteByUserIdAndInfoId(@Param("userId") Integer userId, @Param("infoId") Integer infoId);

    /**
     * 根据用户id和问答id查询问答吐槽
     *
     * @param userId 用户id
     * @param infoId 问答id
     * @return
     */
    Integer findByInfoIdAndUserId(@Param("userId") Integer userId, @Param("infoId") Integer infoId);
}