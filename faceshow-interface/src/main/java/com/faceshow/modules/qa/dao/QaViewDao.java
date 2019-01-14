package com.faceshow.modules.qa.dao;

import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 问答查看信息操作Dao
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:37
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface QaViewDao extends BaseDao<Object> {

    /**
     * 标记指定问答是否有新回复
     *
     * @param infoId 问答id
     * @param state  0 没有新回复, 1 有新回复
     * @return
     */
    int updateNewAnswerByInfoId(@Param("infoId") Integer infoId, @Param("state") Integer state);
}