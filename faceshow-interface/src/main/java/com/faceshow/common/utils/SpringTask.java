package com.faceshow.common.utils;

import com.faceshow.modules.qa.service.QaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * spring定时任务
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/14 15:06
 * -------------------------------------------------------------- <br>
 */
@Component
public class SpringTask {

    @Autowired
    private QaInfoService qaInfoService;

    /**
     * 每天0点执行一次
     */
    @Async
    @Scheduled(cron = "0 0 0 * * ?")
    public void show() {
        try {
            qaInfoService.bestAnswerToThree();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
