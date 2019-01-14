package com.faceshow.modules.language.service.impl;

import com.faceshow.modules.language.dao.LanguageStudyDao;
import com.faceshow.modules.language.service.LanguageStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户学习的语言操作Service
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 20:47
 * -------------------------------------------------------------- <br>
 */
@Service
public class LanguageStudyServiceImpl implements LanguageStudyService {

    @Autowired
    private LanguageStudyDao languageStudyDao;

    /**
     * 删除用户会说的语言
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public int deleteByUserId(Integer userId) {
        return languageStudyDao.deleteByUserId(userId);
    }

    /**
     * 保存用户会说的语言
     *
     * @param userId    用户id
     * @param languages 语言集合
     * @return
     */
    @Override
    public int save(Integer userId, String[] languages) {
        return languageStudyDao.save(userId, languages, new Date());
    }
}
