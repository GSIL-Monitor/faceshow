package com.faceshow.modules.video.controller;

import com.faceshow.common.MQ.message.likenum.VideoPlayLogProduct;
import com.faceshow.common.annotation.SysLog;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.video.entity.VideoPlayLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 视频播controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.video.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/28 14:09
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/api/video/play")
public class VideoPlayLogController extends AbstractController {

    /**
     * 用户播放日志
     *
     * @param videoId 视频id
     * @param userId  用户id
     */
    @SysLog
    @RequestMapping("/log")
    public Object playLog(Integer videoId, Integer userId) {
        VideoPlayLogProduct.sendVideoPlayMessage(new VideoPlayLog(videoId, userId, new Date()));
        return null;
    }
}
