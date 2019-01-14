package com.faceshow.modules.job.service;

import com.faceshow.modules.job.entity.JobUser;
import com.faceshow.modules.job.vo.JobUserSelectRowVo;

/**
 * 用户职业信息操作Service
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 13:22
 * -------------------------------------------------------------- <br>
 */
public interface JobUserService {

    /**
     * 保存用户职业信息
     *
     * @param jobUser 用户职业信息
     * @return
     */
    int save(JobUser jobUser);

    /**
     * 根据id删除用户的职业信息
     *
     * @param jobUserId 用户职业id
     * @return
     */
    int delete(Integer jobUserId);

    /**
     * 修改用户职业信息
     *
     * @param jobUser 用户职业信息
     * @return
     */
    int update(JobUser jobUser);

    /**
     * 根据用户id查询用户职业信息
     *
     * @param userId 用户id
     * @return
     */
    JobUserSelectRowVo findByUserId(Integer userId);
}
