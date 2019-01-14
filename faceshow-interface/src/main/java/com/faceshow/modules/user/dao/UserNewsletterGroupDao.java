package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserNewsletterGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserNewsletterGroupDao {
    int deleteByPrimaryKey(Integer newsletterId);

    int insert(UserNewsletterGroup record);

    int insertSelective(UserNewsletterGroup record);

    UserNewsletterGroup selectByPrimaryKey(Integer newsletterId);

    int updateByPrimaryKeySelective(UserNewsletterGroup record);

    int updateByPrimaryKey(UserNewsletterGroup record);

    /**
     * 首次进入发现搜索页面展示数据
     *
     * @param map 分页参数
     * @return
     */
    List<Map<String, Object>> findNewsletterGroupByCriteria(Map<String, Object> map);

    /**
     * 查询分组总数
     *
     * @param search
     * @return
     */
    Integer findNewsletterGroupTotalCount(Map<String, Object> map);
}