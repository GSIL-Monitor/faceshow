package com.faceshow.modules.job.service.impl;

import com.faceshow.modules.job.dao.JobInfoDao;
import com.faceshow.modules.job.service.JobInfoService;
import com.faceshow.modules.job.vo.JobInfoSelectRowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职业信息操作Service
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 17:21
 * -------------------------------------------------------------- <br>
 */
@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoDao jobInfoDao;

    /**
     * 查询行业菜单
     *
     * @param parentId 父菜单id
     * @return
     */
    @Override
    public List<JobInfoSelectRowVo> findJobInfoList(Integer parentId) {
        return jobInfoDao.findJobInfoList(parentId);
    }
}
