package com.faceshow.modules.language.dao;

import com.faceshow.modules.language.LanguageSelectRowVo;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户会说的语言信息操作Dao
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 13:51
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface LanguageCanDao extends BaseDao<Object> {

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
    int save(@Param("userId") Integer userId, @Param("languages") String[] languages, @Param("date") Date date);

    /**
     * 查询用户掌握的语言
     *
     * @param userId 用户id
     * @return
     */
    List<LanguageSelectRowVo> findLanguageByUserId(Integer userId);

    /**
     * 查询用户掌握的语言名称, 编辑个人资料展示使用
     *
     * @param userId 用户id
     * @return
     */
    List<String> findLanguageNameByUserId(Integer userId);

    /**
     * 根据用户掌握语言id查询用户id
     *
     * @param languageCanIds 用户掌握语言
     * @return
     */
    List<Integer> findUserIdByLanguageId(String[] languageCanIds);

    /**
     * 测试使用
     * @param userId
     * @param language
     * @return
     */
    int insert(@Param("userId") String userId, @Param("language") String language);
}