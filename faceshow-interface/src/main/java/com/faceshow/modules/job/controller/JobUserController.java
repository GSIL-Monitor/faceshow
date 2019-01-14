package com.faceshow.modules.job.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.R;
import com.faceshow.modules.job.entity.JobUser;
import com.faceshow.modules.job.service.JobUserService;
import com.faceshow.modules.job.vo.JobUserSelectRowVo;
import com.faceshow.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 用户职业操作controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.job.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 19:29
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/job/user")
public class JobUserController extends AbstractController {

    @Autowired
    private JobUserService jobUserService;

    /**
     * 保存用户职业信息
     *
     * @param jobUser 用户职业信息
     * @return
     */
    @SysLog
    @PostMapping("/save")
    public Object save(JobUser jobUser) {
        jobUser.setCreateTime(new Date());
        jobUser.setUserId(getUserInfoId());
        int i = jobUserService.save(jobUser);
        return R.result(1, "OK", "");
    }

    /**
     * 删除用户职业信息
     *
     * @param jobUserId 用户职业id
     * @return
     */
    @SysLog
    @PostMapping("/delete")
    public Object delete(Integer jobUserId) {
        int i = jobUserService.delete(jobUserId);
        return R.result(1, "OK", "");
    }

    /**
     * 修改用户职业信息
     *
     * @param jobUser 用户职业信息
     * @return
     */
    @SysLog
    @PostMapping("/update")
    public Object update(JobUser jobUser) {
        jobUser.setUserId(getUserInfoId());
        int i = jobUserService.update(jobUser);
        return R.result(1, "OK", "");
    }

    /**
     * 根据用户id查询用户职业信息
     *
     * @param userId 用户id
     * @return
     */
    @SysLog
    @PostMapping("/findByUserId")
    public Object findByUserId(Integer userId) {
        JobUserSelectRowVo jobUserSelectRowVo = jobUserService.findByUserId(getUserInfoId());
        return R.ok("OK").put("result", jobUserSelectRowVo == null ? new JobUserSelectRowVo() : jobUserSelectRowVo);
    }
}
