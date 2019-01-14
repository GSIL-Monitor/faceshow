package com.faceshow.modules.sys.controller;

import com.faceshow.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.sys.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/1/31 10:27
 * -------------------------------------------------------------- <br>
 */
@Controller
@RequestMapping("/api")
public class TestController {

    @Autowired
    private  RedisUtils redisUtils;

    @RequestMapping("/redisSetExpire")
    public Object redisSetExpire(String key, String value, Long expire) {
        redisUtils.set(key, value, expire);
        return null;
    }

    @RequestMapping("/redisSet")
    public Object redisSet(String key, String value) {
        redisUtils.set(key, value);
        return null;
    }

    @RequestMapping("/redisGet")
    public Object redisSet(String key) {
        String s = redisUtils.get(key);
        return s;
    }

    @GetMapping("/emailPage")
    public String emailPage(Model model){
        model.addAttribute("email","123456");
        return "pass_reset";
    }
}
