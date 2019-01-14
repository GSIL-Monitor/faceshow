package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserSearchKey;

import java.util.List;
import java.util.Map;

public interface UserSearchKeyService {

    int deleteByPrimaryKey(Integer keyId);

    int insert(UserSearchKey record);

    int insertSelective(UserSearchKey record);

    UserSearchKey selectByPrimaryKey(Integer keyId);

    int updateByPrimaryKeySelective(UserSearchKey record);

    int updateByPrimaryKey(UserSearchKey record);

    /**
     * 进入发现搜索页面, 查询用户信息
     *
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @param userId   当前查询用户id
     * @param search   查询条件
     * @param type     1用户搜索，2话题搜索，3群组搜索，4音乐
     * @return
     */
    Object intoSearch( Integer typeId, Integer type_id, Integer userId, String search, Integer currPage, Integer pageSize, int type);



    /**
     * 排行榜, 查询用户获赞数
     *
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @param country  当前国家
     * @return
     */
    Object intoRanklist(String country, Integer currPage, Integer pageSize);

    /**
     * @author: YS
     * @Date:2018/4/13 15:46
     * @param:
     * @explain：   <!-- 热门后台推荐的话题 这个是发现页面使用-->
     * @return:
     */
    Object findHotTopic();


    /**
     * @author: YS
     *@Date:2018/4/13 16:18
     * @param:
     * @explain：发现的页面 点击更多调取接口带分页
     * @return:
     */
    int selectByTopicTotal(Map<String,Object> map);


    /**
     * @author: YS
     *@Date:2018/4/13 16:18
     * @param:
     * @explain：发现的页面 点击更多调取接口带分页
     * @return:
     */
    List<Map<String, Object>> selectByTopic(Map<String,Object> map);
    /**
     * @author: YS
     * @Date:2018/4/13 17:27
     * @param:
     * @explain： <!--进入发现页面随机推荐用户6个本国4个外国-->
     * @return:
     */
    List<Map<String, Object>> recommendUser(Map<String,Object> map);
     Object intoFind(String country, Integer currPage, Integer pageSize);

    /**
     * @author: YS
     * @Date:2018/4/14 9:24
     * @param: type_id
     * @explain：<!--发现页面的某种类型的视频发布的所有短视频在7天内获得的赞数，按倒叙排列-->
     * @return:
     */
    List<Map<String, Object>> weekSort(Map<String,Object> map);
    int weekSortTotal(Map<String,Object> map);
    /**
     * @author: YS
     * @Date:2018/4/14 10:00
     * @param:
     * @explain： 查询热门音乐
     * @return:
     */
    List<Map<String, Object>> hotMusic(Map<String,Object>map);
    int  hotMusicTotal(Map<String,Object>map);


    /**
     * @author: YS
     * @Date:2018/4/14 11:15
     * @param:
     * @explain： 热门视频 带分页
     * @return:
     */
    List<Map<String, Object>> hotVideo(Map<String,Object>map);
    int hotVideoTotal(Map<String,Object>map);


}
