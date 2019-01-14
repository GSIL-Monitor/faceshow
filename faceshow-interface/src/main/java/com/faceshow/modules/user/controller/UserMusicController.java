package com.faceshow.modules.user.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.Query;
import com.faceshow.common.utils.R;
import com.faceshow.modules.user.service.UserMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 用户音乐表 操作音乐类型
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.user.service<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/2/3 16:30
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/music")
public class UserMusicController {
    @Autowired
    private UserMusicService userMusicService;
  /**
   *@Author:YS
   *@Description:得到所有音乐类别
   *@Date:2018/2/3
   */
  @SysLog
    @PostMapping("/getAllType")
    public Object getAllType(@RequestParam Map<String,Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = userMusicService.typeMusic(query);
        int total = userMusicService.typeMusicTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }
    /**
     *@Author:YS
     *@Description:模糊搜索分页
     *@Date:2018/2/5
     */
    @SysLog
    @PostMapping("/getAllMusic")
    public Object getAllMusic(@RequestParam Map<String,Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = userMusicService.getMusic(query);
        int total = userMusicService.getMusicTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

}
