package com.faceshow.modules.language.service;

/**
 * 用户学习的语言操作Service
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 13:51
 * -------------------------------------------------------------- <br>
 */
public interface LanguageStudyService {

    /**
     * 删除用户会说的语言
     *
     * @param userId 用户id
     * @return
     */
    int deleteByUserId(Integer userId);

    /**
     * 保存用户会说的语言
     *
     * @param userId    用户id
     * @param languages 语言集合
     * @return
     */
    int save(Integer userId, String[] languages);
}
