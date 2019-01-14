package com.faceshow.modules.language.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.R;
import com.faceshow.modules.language.service.LanguageStudyService;
import com.faceshow.modules.sys.controller.AbstractController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 用户学习中的语言操作controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.language.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 21:13
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/language/study")
public class LanguageStudyController extends AbstractController {

    @Autowired
    private LanguageStudyService languageStudyService;

    /**
     * 保存用户学习中的语言
     *
     * @param languages 语言名称, 多个使用逗号拼接
     * @return
     */
    @SysLog
    @PostMapping("/save")
    public Object save(String languages) {
        Integer userId = getUserInfoId();
        // 如果没有学习中的语言, 则清空学习中的语言列表
        if (StringUtils.isBlank(languages)) {
            int i = languageStudyService.deleteByUserId(userId);
            return R.result(1, "OK", "");
        }

        // 保存会说的语言
        String[] split = languages.split(",");
        int i = languageStudyService.save(userId, split);
        return R.result(1, "OK", "");
    }
}
