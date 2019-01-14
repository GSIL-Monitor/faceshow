package com.faceshow.modules.video.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.modules.video.service.VideoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 视频类型操作controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.video.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/5 11:47
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/video/type")
public class VideoTypeController {

    @Autowired
    private VideoTypeService videoTypeService;

    /**
     * 查询视频分类20个
     *
     * @return
     */
    @SysLog
    @PostMapping("/list")
    public Object list(@RequestParam Map<String,Object>map) {
        return videoTypeService.list(map);
    }

    /**
     * 进入分类详情页
     *
     * @param typeId 分类id
     * @return
     */
    @SysLog
    @PostMapping("/intoVideoType")
    public Object intoVideoType(Integer typeId) {
        return videoTypeService.intoVideoType(typeId);
    }
}
