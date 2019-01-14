package com.faceshow.modules.job.dao;

import com.faceshow.modules.job.vo.JobUserSelectRowVo;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户职业信息操作Dao
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 13:22
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface JobUserDao extends BaseDao<Object> {

    /**
     * 根据用户id查找用户职业
     *
     * @param userId 用户id
     * @return
     */
    String findCareerByUserId(Integer userId);

    /**
     * 根据用户id查询用户职业信息
     *
     * @param userId 用户id
     * @return
     */
    JobUserSelectRowVo findByUserId(Integer userId);
}