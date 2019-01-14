package com.faceshow.modules.video.service;

import com.faceshow.modules.video.entity.VideoTopic;

import java.util.List;
import java.util.Map;

public interface VideoTopicService {

    /**
     * 进入话题详情页
     *
     * @param topicId  话题id
     * @return
     */
    Object intoVideoTopic(Integer topicId);

    /**
     * @Author:YS
     * @Description:相关话题 全球话题 本国话题 带分页
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
     * 查询话题数量
     *
     * @param map
     * @return
     */
    Integer findAllTotal();

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
     * @param topic 话题名字
     * @return
     */
    VideoTopic findByTopicName(String topic);

    /**
     * 添加新话题
     *
     * @param videoTopic
     * @return
     */
    int save(VideoTopic videoTopic);


}
