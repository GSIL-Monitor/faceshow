package com.faceshow.modules.sys.service.impl;

import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.RedisUtils;
import com.faceshow.modules.sys.dao.SysCountryDao;
import com.faceshow.modules.sys.entity.SysCountry;
import com.faceshow.modules.sys.service.SysCountryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
@Service
public class SysCountryServiceImpl implements SysCountryService {
    @Autowired
    private SysCountryDao sysCountryDao;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * @Author:YS
     * @Description: 查看国家列表
     * @Date:2018/4/8
     * @param:No such property: code for class: Script1
     */
    @Override
    public List selectCountry() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String sys_country = redisUtils.get("sys_country");
            if (!StringUtils.isBlank(sys_country)) {
                List list = JsonUtils.jsonToList(sys_country, map.getClass());
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //执行查询
        List<Map<String, Object>> maps = sysCountryDao.selectCountry();

        //返回结果之前，向缓存中添加数据
        try {
            redisUtils.set("sys_country", JsonUtils.objectToJson(maps));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maps;
    }

    /**
     * 根据国家编号查询国家
     *
     * @param coding 国家编号
     * @return
     */
    @Override
    public SysCountry findCountryByCoding(String coding) {
        return sysCountryDao.findCountryByCoding(coding);
    }

    /**
     * 根据国家编号查询国家id
     *
     * @param coding 国家编号
     * @return
     */
    @Override
    public Integer findCountryIdByCoding(String coding) {
        return sysCountryDao.findCountryIdByCoding(coding);
    }

    /**
     * 根据国家id\查询国家信息
     *
     * @param id 国家id
     * @return
     */
    @Override
    public SysCountry findCountryById(Integer id) {
        return sysCountryDao.findCountryById(id);
    }

    /**
     * 查询所有语言
     *
     * @return
     */
    @Override
    public List<String> findLanguageAll(){
        return sysCountryDao.findLanguageAll();
    }

}
