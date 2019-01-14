package com.faceshow.modules.video.service.impl;

import com.faceshow.common.utils.Query;
import com.faceshow.common.utils.R;
import com.faceshow.modules.video.dao.VideoInfoDao;
import com.faceshow.modules.video.dao.VideoTopicDao;
import com.faceshow.modules.video.entity.VideoTopic;
import com.faceshow.modules.video.service.VideoTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoTopicServiceImpl implements VideoTopicService {

    @Autowired
    private VideoTopicDao videoTopicDao;

    @Autowired
    private VideoInfoDao videoInfoDao;

    /**
     * 进入话题详情页
     *
     * @param topicId 话题id
     * @return
     */
    @Override
    public Object intoVideoTopic(Integer topicId) {

        // 查询话题发起人信息及话题简介信息
        Map<String, Object> topicInfo = videoTopicDao.findUserAndTopicByTopicId(topicId);

        // 查询该话题下得分最高的前六名
        List<Object> firstSix = videoInfoDao.findHightScoreUser(1, topicId);

        topicInfo.put("firstSix", firstSix);

        return R.ok("OK").put("result", topicInfo);
    }

    /**
     * @Author:YS
     * @Description:相关话题 全球话题 本国话题 带分页
     * @Date:2018/2/24
     */
    @Override
    public List<Object> findTopicRelevant(Map<String, Object> map) {
        return videoTopicDao.findTopicRelevant(map);
    }

    @Override
    public int findTopicRelevantTotal(Map<String, Object> map) {
        return videoTopicDao.findTopicRelevantTotal(map);
    }

    /**
     * @Author:YS
     * @Description: 查找话题表的所有
     * @Date:2018/2/26
     * @param:No such property: code for class: Script1
     */
    @Override
    public List<Object> findAll(Map<String, Object> map) {
        return videoTopicDao.findAll(new Query(map));
    }

    /**
     * 查询话题数量
     *
     * @param map
     * @return
     */
    @Override
    public Integer findAllTotal() {
        return videoTopicDao.findAllTotal();
    }

    /**
     * @Author:YS
     * @Description: <!--查看这个话题参与者按照魅力值分页-->
     * @Date:2018/2/26
     * @param: topic_id 话题Id
     */
    @Override
    public List<Object> findRankingList(Map<String, Object> map) {
        return videoTopicDao.findRankingList(map);
    }

    @Override
    public int findRankingListTotal(Map<String, Object> map) {
        return videoTopicDao.findRankingListTotal(map);
    }

    /**
     * 根据话题名字查询话题
     *
     * @param topic 话题名字
     * @return
     */
    @Override
    public VideoTopic findByTopicName(String topic) {
        return videoTopicDao.findByTopicName(topic);
    }

    /**
     * 添加新话题
     *
     * @param videoTopic
     * @return
     */
    @Override
    public int save(VideoTopic videoTopic) {
        return videoTopicDao.insertSelective(videoTopic);
    }
}
