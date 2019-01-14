package com.faceshow.modules.sys.dao;

import com.faceshow.modules.sys.entity.SysTopic;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysTopicDao {
    int deleteByPrimaryKey(Integer topicId);

    int insert(SysTopic record);

    int insertSelective(SysTopic record);

    SysTopic selectByPrimaryKey(Integer topicId);

    int updateByPrimaryKeySelective(SysTopic record);

    int updateByPrimaryKey(SysTopic record);

    /**
     * 首次进入发现搜索页面展示数据
     *
     * @param map 分页参数
     * @return
     */
    List<Object> findTopicByCriteria(Map<String, Object> map);
}