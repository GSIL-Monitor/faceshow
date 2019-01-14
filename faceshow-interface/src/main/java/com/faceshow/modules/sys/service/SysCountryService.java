package com.faceshow.modules.sys.service;

import com.faceshow.modules.sys.entity.SysCountry;

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
public interface SysCountryService {
    List<Map<String, Object>> selectCountry();

    /**
     * 根据国家编号查询国家
     *
     * @param coding 国家编号
     * @return
     */
    SysCountry findCountryByCoding(String coding);

    /**
     * 根据国家编号查询国家id
     *
     * @param coding 国家编号
     * @return
     */
    Integer findCountryIdByCoding(String coding);

    /**
     * 根据国家id\查询国家信息
     *
     * @param id 国家id
     * @return
     */
    SysCountry findCountryById(Integer id);

    /**
     * 查询所有语言
     *
     * @return
     */
    List<String> findLanguageAll();
}
