package com.faceshow.modules.user.dao;


/**
 * @author: YS
 * @Date:2018/4/13 15:44
 * @param:
 * @explain： 发现页面
 * @return:
 */
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserFoundDao {
    /**
     * @author: YS
     * @Date:2018/4/13 13:47
     * @param: 无
     * @explain：发现页面需要使用 查询返回所有的热门话题
     * @return: List<Map<String, Object>>
     */
    List<Map<String, Object>> findHotTopic();

    /**
     * @author: YS
     * @Date:2018/4/13 14:10
     * @param: topic
     * @explain： 首页展示的话题页面   根据话题ID查
     * @return:
     */
    List<Map<String, Object>> selectByTopic(Map<String,Object> map);

    int selectByTopicTotal(Map<String,Object> map);//带分页的 和上面的语句

    /**
     * @author: YS
     * @Date:2018/4/13 17:27
     * @param:
     * @explain： <!--进入发现页面随机推荐用户6个本国4个外国-->
     * @return:
     */
    List<Map<String, Object>> recommendUser(Map<String,Object> map);

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