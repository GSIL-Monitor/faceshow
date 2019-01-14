package com.faceshow.modules.music.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.modules.music.service.MusicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 音乐类型查询操作controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.music.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/5 11:10
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/music/type")
public class MusicTypeController {

    @Autowired
    private MusicTypeService musicTypeService;

    /**
     * 列表查询音乐类型
     *
     * search 查询条件
     *
     * @param map
     * @return
     */
    @SysLog
    @PostMapping("/list")
    public Object list(@RequestParam Map<String, Object> map) {
        return musicTypeService.list(map);
    }

}
