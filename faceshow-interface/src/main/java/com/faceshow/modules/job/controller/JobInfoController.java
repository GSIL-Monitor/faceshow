package com.faceshow.modules.job.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.R;
import com.faceshow.modules.job.service.JobInfoService;
import com.faceshow.modules.job.vo.JobInfoSelectRowVo;
import com.faceshow.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 用户行业信息操作controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.job.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 17:10
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/job/info")
public class JobInfoController extends AbstractController {

    @Autowired
    private JobInfoService jobInfoService;

    /**
     * 查询行业菜单
     *
     * @param parentId 父菜单id
     * @return
     */
    @SysLog
    @PostMapping("/list")
    public Object list(@RequestParam(defaultValue = "0") Integer parentId) {
        List<JobInfoSelectRowVo> list = jobInfoService.findJobInfoList(parentId);
        return R.result(1, "OK", list == null ? Collections.emptyList() : list);
    }

}
