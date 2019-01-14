package com.faceshow.modules.qa.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.modules.qa.service.QaInfoRecommendService;
import com.faceshow.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 推荐问题操作controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.qa.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/20 10:07
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/qarecommend")
public class QaInfoRecommendController extends AbstractController {

    @Autowired
    private QaInfoRecommendService qaInfoRecommendService;

    /**
     * 查询推荐问题列表
     *
     * @param map 分页条件
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @SysLog
    @PostMapping("/list")
    public Object list(@RequestParam Map<String, Object> map) {
        return qaInfoRecommendService.list(map);
    }
}
