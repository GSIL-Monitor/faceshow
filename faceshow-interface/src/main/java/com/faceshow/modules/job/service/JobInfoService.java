package com.faceshow.modules.job.service;

import com.faceshow.modules.job.vo.JobInfoSelectRowVo;

import java.util.List;

/**
 * 职业信息操作Service
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 13:21
 * -------------------------------------------------------------- <br>
 */
public interface JobInfoService {

    /**
     * 查询行业菜单
     *
     * @param parentId 父菜单id
     * @return
     */
    List<JobInfoSelectRowVo> findJobInfoList(Integer parentId);
}
