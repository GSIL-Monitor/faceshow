package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserSearchKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserSearchKeyDao {
    int deleteByPrimaryKey(Integer keyId);

    int insert(UserSearchKey record);

    int insertSelective(UserSearchKey record);

    UserSearchKey selectByPrimaryKey(Integer keyId);

    int updateByPrimaryKeySelective(UserSearchKey record);

    int updateByPrimaryKey(UserSearchKey record);

    /**
     * 用户第一次进入发现搜索页面展示数据
     *
     * @param map 查询条件
     * @return
     */
    List<Object> intoSearch(Map<String, Object> map);

    /**
     * 排行榜, 查询用户获赞数
     *
     * @param map 分页条件
     * @return
     */
    List<Object> findRanklist(Map<String, Object> map);

    /**
     * 查询排行榜用户数量
     *
     * @param map
     * @return
     */
    Integer findRankTotalCount(Map<String, Object> map);
    /**
     * @author: YS
     * @Date:2018/4/13 13:47
     * @param: 无
     * @explain：发现页面需要使用 查询返回所有的热门话题
     * @return: List<Map<String, Object>>
     */
    List<Map<String, Object>> findHotTopic();
}