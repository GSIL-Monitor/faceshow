package com.faceshow.modules.qa.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.modules.qa.service.QaRewardMoneyService;
import com.faceshow.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 悬赏金额操作controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.qa.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/20 11:14
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/qamoney")
public class QaRewardMoneyController extends AbstractController {

    @Autowired
    private QaRewardMoneyService qaRewardMoneyService;

    /**
     * 查询悬赏金额列表
     *
     * @return
     */
    @SysLog
    @PostMapping("/list")
    public Object list() {
        return qaRewardMoneyService.list();
    }

}
