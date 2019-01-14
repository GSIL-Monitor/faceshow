package com.faceshow.modules.language.service.impl;

import com.faceshow.modules.language.dao.LanguageCanDao;
import com.faceshow.modules.language.service.LanguageCanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户会说的语言信息操作Service
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 20:34
 * -------------------------------------------------------------- <br>
 */
@Service
public class LanguageCanServiceImpl implements LanguageCanService {

    @Autowired
    private LanguageCanDao languageCanDao;

    /**
     * 删除用户会说的语言
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public int deleteByUserId(Integer userId) {
        return languageCanDao.deleteByUserId(userId);
    }

    /**
     * 保存用户会说的语言
     *
     * @param userId    用户id
     * @param languages 语言集合
     * @return
     */
    @Override
    @Transactional
    public int save(Integer userId, String[] languages) {
        // 删除原来的语言
        languageCanDao.deleteByUserId(userId);
        // 添加新的语言
        return languageCanDao.save(userId, languages, new Date());
    }
}