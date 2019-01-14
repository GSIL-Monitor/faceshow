package com.faceshow.modules.job.service.impl;

import com.faceshow.modules.job.dao.JobUserDao;
import com.faceshow.modules.job.entity.JobUser;
import com.faceshow.modules.job.service.JobUserService;
import com.faceshow.modules.job.vo.JobUserSelectRowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户职业信息操作Service实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 19:50
 * -------------------------------------------------------------- <br>
 */
@Service
public class JobUserServiceImpl implements JobUserService {

    @Autowired
    private JobUserDao jobUserDao;

    /**
     * 保存用户职业信息
     *
     * @param jobUser 用户职业信息
     * @return
     */
    @Override
    public int save(JobUser jobUser) {
        // 修改
        int i = jobUserDao.update(jobUser);
        if (i < 1) {
            // 新增
            return jobUserDao.save(jobUser);
        }
        return i;
    }

    /**
     * 根据id删除用户的职业信息
     *
     * @param jobUserId 用户职业id
     * @return
     */
    @Override
    public int delete(Integer jobUserId) {
        return jobUserDao.delete(jobUserId);
    }

    /**
     * 修改用户职业信息
     *
     * @param jobUser 用户职业信息
     * @return
     */
    @Override
    public int update(JobUser jobUser) {
        return jobUserDao.update(jobUser);
    }

    /**
     * 根据用户id查询用户职业信息
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public JobUserSelectRowVo findByUserId(Integer userId) {
        return jobUserDao.findByUserId(userId);
    }

}
