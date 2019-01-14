package com.faceshow.modules.job.dao;

import com.faceshow.modules.job.vo.JobInfoSelectRowVo;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 职业信息操作Dao
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 13:21
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface JobInfoDao extends BaseDao<Object> {

    /**
     * 查询行业菜单
     *
     * @param parentId 父菜单id
     * @return
     */
    List<JobInfoSelectRowVo> findJobInfoList(Integer parentId);

}