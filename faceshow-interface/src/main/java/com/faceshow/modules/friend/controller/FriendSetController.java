package com.faceshow.modules.friend.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.modules.friend.entity.FriendSet;
import com.faceshow.modules.friend.service.FriendSetService;
import com.faceshow.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 好友设置操作Controller<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.friend.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/26 18:16
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/friendset")
public class FriendSetController extends AbstractController {

    @Autowired
    private FriendSetService friendSetService;

    /**
     * 进入好友设置页面
     *
     * @param :userId    当前主用户id
     * @param :accountId 被设置用户id
     * @return
     */
    @SysLog
    @PostMapping("/open")
    public Object openSet(@RequestParam Map<String, Object> map) {
        return friendSetService.openSet(map);
    }

    /**
     * 修改好友设置
     *
     * @param friendSet 设置内容
     * @return
     */
    @SysLog
    @PostMapping("/update")
    public Object update(FriendSet friendSet) {
        return friendSetService.update(friendSet);
    }
}
