package com.faceshow.modules.video.dao;

import com.faceshow.modules.video.entity.VideoTopic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoTopicDao {
    int deleteByPrimaryKey(Integer topicId);

    int insertSelective(VideoTopic record);

    VideoTopic selectByPrimaryKey(Integer topicId);

    int updateByPrimaryKeySelective(VideoTopic record);

    /**
     * 首次进入发现搜索页面展示数据
     *
     * @param map 分页参数
     * @return
     */
    List<Map<String, Object>> findTopicByCriteria(Map<String, Object> map);

    /**
     * @author: YS
     * @Date:2018/4/13 13:47
     * @param: 无
     * @explain：发现页面需要使用 查询返回所有的热门话题
     * @return: List<Map<String, Object>>
     */
    List<Map<String, Object>> findHotTopic();

    /**
     * 查询话题发起人信息及话题详情
     *
     * @param topicId 话题id
     * @return
     */
    Map<String, Object> findUserAndTopicByTopicId(Integer topicId);

    /**
     * 查询得分最高的视频
     *
     * @param map 查询条件
     * @return
     */
    List<Object> findHightScoreVideo(Map<String, Object> map);

    /**
     * 查询话题总数
     *
     * @param pageQuery
     * @return
     */
    Integer findTopicTotalCount(Map<String, Object> pageQuery);

    /**
     * @Author:YS
     * @Description:相关话题 全球话题 本国话题
     * @Date:2018/2/24
     */
    List<Object> findTopicRelevant(Map<String, Object> map);

    int findTopicRelevantTotal(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查找话题表的所有
     * @Date:2018/2/26
     * @param:No such property: code for class: Script1
     */
    List<Object> findAll(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: <!--查看这个话题参与者按照魅力值分页-->
     * @Date:2018/2/26
     * @param: topic_id 话题Id
     */

    List<Object> findRankingList(Map<String, Object> map);

    int findRankingListTotal(Map<String, Object> map);

    /**
     * 根据话题名字查询话题
     *
     * @param topic
     * @return
     */
    VideoTopic findByTopicName(String topic);

    /**
     * 查询话题数量
     *
     * @return
     */
    Integer findAllTotal();

    /**
     * 根据话题名字查询话题id
     *
     * @param topic 话题名字
     * @return
     */
    Integer findTopicIdByTopicName(String topic);

}