package com.faceshow.modules.sys.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.R;
import com.faceshow.modules.sys.service.SysCountryService;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation:  国家的查询
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.sys.service<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/4/8 14:13
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/api/Country")
public class SysCountryController {
    @Autowired
    private SysCountryService sysCountryService;


    @SysLog
    @PostMapping("/selectCountry")
    public Object selectCountry() {
        List<Map<String, Object>> maps = sysCountryService.selectCountry();
        return R.result(1, "OK", maps);
    }

    /**
     * 查询所有的语言
     *
     * @return
     */
    @SysLog
    @PostMapping("/findLanguage")
    public Object findLanguage() {
        return R.result(1, "OK", sysCountryService.findLanguageAll());
    }
}
