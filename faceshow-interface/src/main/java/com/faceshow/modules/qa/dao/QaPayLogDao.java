package com.faceshow.modules.qa.dao;


import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 问答支付日志操作Dao
 * <p>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:28
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface QaPayLogDao extends BaseDao<Object>{

    /**
     * 保存问答支付日志, 前三名支付日志
     * @param map
     * @return
     */
    int saveTopThreePayLog(Map<String, Object> map);

    /**
     * 保存问答支付日志, 前两名支付日志
     * @param map
     * @return
     */
    int saveTopTwoPayLog(Map<String, Object> map);
}