package com.faceshow.modules.sys.dao;

import com.faceshow.modules.sys.entity.SysCountry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysCountryDao {

    /**
     * 查询国家列表
     *
     * @return
     */
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
     * 根据国家id查询国家信息
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