package com.faceshow.modules.language.dao;

import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户学习的语言操作Dao
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 13:51
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface LanguageStudyDao extends BaseDao<Object> {


    /**
     * 删除用户会说的语言
     *
     * @param userId 用户id
     * @return
     */
    int deleteByUserId(Integer userId);

    /**
     * 保存用户会说的语言
     *
     * @param userId    用户id
     * @param languages 语言集合
     * @return
     */
    int save(@Param("userId") Integer userId, @Param("languages") String[] languages, @Param("date") Date date);
}