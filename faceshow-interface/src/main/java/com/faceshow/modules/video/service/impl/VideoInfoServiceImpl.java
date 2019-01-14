package com.faceshow.modules.video.service.impl;

import com.faceshow.common.MQ.message.likenum.VideoLikeNumProduct;
import com.faceshow.common.MQ.message.likenum.VideoPlayLogProduct;
import com.faceshow.common.MQ.message.push.VideoAtPushProduct;
import com.faceshow.common.MQ.message.push.VideoLikePushProduct;
import com.faceshow.common.MQ.message.push.VideoPushManyProduct;
import com.faceshow.common.utils.*;
import com.faceshow.modules.friend.service.FriendRingService;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.video.dao.*;
import com.faceshow.modules.video.entity.*;
import com.faceshow.modules.video.service.VideoInfoService;
import com.faceshow.modules.video.vo.VideoInfoSelectRowVo;
import com.faceshow.modules.video.vo.VideoIntroduction;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 视频信息操作service实现类
 */
@Service
public class VideoInfoServiceImpl implements VideoInfoService {

    @Autowired
    private VideoInfoDao videoInfoDao;

    @Autowired
    private VideoTopicDao videoTopicDao;

    @Autowired
    private VideoAitFriendDao videoAitFriendDao;

    @Autowired
    private VideoGiftSendDao videoGiftSendDao;

    @Autowired
    private VideoCollectionLogDao videoCollectionLogDao;

    @Autowired
    private FriendRingService friendRingService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private VideoTopicBelongDao videoTopicBelongDao;

    @Autowired
    private VideoInfoIntroductionDao videoInfoIntroductionDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public int deleteByPrimaryKey(Integer videoId) {
        return videoInfoDao.deleteByPrimaryKey(videoId);
    }

    @Override
    public int insertSelective(VideoInfo videoInfo) {
        return videoInfoDao.insertSelective(videoInfo);
    }

    @Override
    public VideoInfo selectByPrimaryKey(Integer videoId) {
        return videoInfoDao.selectByPrimaryKey(videoId);
    }

    @Override
    public int updateByPrimaryKeySelective(VideoInfo record) {
        return videoInfoDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 视频点赞
     *
     * @param video_id 视频id
     * @param userId   用户id
     * @return
     */
    @Override
    @Transactional
    public Object InsertCollection(Integer video_id, Integer userId, boolean flag) {
        if (getVideoLike(video_id, userId) > 0) {
            return R.result(1, "OK", "");
        }
        VideoCollectionLog videoCollectionLog = new VideoCollectionLog();
        videoCollectionLog.setVideoId(video_id);
        videoCollectionLog.setUserId(userId);
        videoCollectionLog.setCreateTime(new Date());
        //插入日誌表一條記錄
        int i = videoCollectionLogDao.insertSelective(videoCollectionLog);
        if (i > 0) {
            //更新關注數量加一
            VideoLikeNumProduct.updateVideoLikeNumOne(video_id, 1);
            // 把点赞记录放入redis中
            redisUtils.set(RedisKeys.VIDEO_LIKE + video_id + "_" + userId, 1, RedisUtils.NOT_EXPIRE);
            // 点赞推送通知
            VideoLikePushProduct.pushVideoLike(video_id, userId);
        }

        if (flag) {
            // 查询是否同步到朋友圈
            Integer friendId = friendRingService.findFriendIdByVideoId(video_id);
            if (friendId != null) {
                friendRingService.addFriendLike(friendId, userId, video_id);
            }
        }

        return R.result(1, "OK", videoCollectionLog.getLogId());
    }

    /**
     * 视频取消点赞
     *
     * @param video_id 视频id
     * @param userId   用户id
     * @return
     */
    @Override
    @Transactional
    public Object updateCancel(Integer video_id, Integer userId, boolean flag) {
        if (getVideoLike(video_id, userId) == 0) {
            return R.result(1, "OK", "");
        }
        //刪除日志表相关的记录 根据主键
        int cancel = videoCollectionLogDao.delByVideoIdAndUserId(video_id, userId);

        // 从redis中删除点赞信息
        redisUtils.delete(RedisKeys.VIDEO_LIKE + video_id + "_" + userId);
        if (cancel > 0) {
            //取消關注 關注數量-1
            VideoLikeNumProduct.updateVideoLikeNumOne(video_id, -1);
        }

        if (flag) {
            // 查询是否同步到朋友圈
            Integer friendId = friendRingService.findFriendIdByVideoId(video_id);
            if (friendId != null) {
                friendRingService.delFriendLike(friendId, userId);
            }
        }
        return R.result(1, "OK", "");
    }

    /**
     * @Author:YS
     * @Description: 公开视频详情页面 -
     * @Date:2018/1/24
     */
    @Override
    public VideoInfoSelectRowVo lookVideoDetail(Map<String, Object> map) {
        //公开视频详情页面
        VideoInfoSelectRowVo lookVideoDetail = videoInfoDao.lookVideoDetail(map);
        Integer videoId = Integer.parseInt(map.get("video_id").toString());
        Integer userId = Integer.parseInt(map.get("userId").toString());
        if (!ObjectUtils.isEmpty(lookVideoDetail)) {
            // 从redis中获取是否已经点赞
            lookVideoDetail.setIsZan(getVideoLike(videoId, userId));

            // 根据视频id查询视频简介
            String introduction = videoInfoIntroductionDao.findIntroductionByVideoId(lookVideoDetail.getVideoId());
            if (StringUtils.isNotBlank(introduction)) {
                // 将内容信息转成集合对象
                List<VideoIntroduction> list = JsonUtils.jsonToList(introduction, VideoIntroduction.class);

                for (VideoIntroduction videoIntroduction : list) {
                    Integer videoType = videoIntroduction.getType();
                    if (videoType == 3) {
                        // 根据用户id查询用户名
                        videoIntroduction.setContent("@" + userInfoDao.selectNick_name(Integer.parseInt(videoIntroduction.getUserId())));
                    }
                }

                lookVideoDetail.setVideoIntroductionJson(JsonUtils.objectToJson(list));
            }
        }

        // 向用户观看视频列表中插入数据
        VideoPlayLogProduct.sendVideoPlayMessage(new VideoPlayLog(videoId, userId, new Date()));

        return lookVideoDetail;
    }


    /**
     * @Author:YS
     * @Description: 我的话题页面 我参与的话题 传递用户ID
     * @Date:2018/1/24
     */
    @Override
    public List<Map<String, Object>> participateTopic(Map<String, Object> map) {
        List<Map<String, Object>> faqizheId = videoInfoDao.participateTopic(map);//查看这个话题的信息 包括发起人信息
        for (Map<String, Object> stringObjectMap : faqizheId) {
            map.put("account", stringObjectMap.get("faqizheId"));//发起人ID
            Map<String, Object> fansYes = videoInfoDao.FansYes(map);//查看这个人和发起人的关系 粉丝还是相互关注？
            if (ObjectUtils.isEmpty(fansYes)) {//没有查询到那么就没关注
                stringObjectMap.put("is_tutual", 0);
            } else {
                stringObjectMap.put("is_tutual", 1);
            }
        }
        return faqizheId;
    }

    //分页
    @Override
    public int participateTopicTotal(Map<String, Object> map) {
        return videoInfoDao.participateTopicTotal(map);
    }


    /**
     * @Author:YS
     * @Description: 我的话题页面 我发出的话题 传递用户ID
     * @Date:2018/1/24
     */
    @Override
    public List<Object> deliverTopic(Map<String, Object> map) {
        return videoInfoDao.deliverTopic(map);
    }

    @Override
    public int deliverTopicTotal(Map<String, Object> map) {
        return videoInfoDao.deliverTopicTotal(map);
    }

    /**
     * 进入推荐短视频页面, 开始推荐短视频
     *
     * @param currPage 当前页, 默认为1
     * @param pageSize 每页显示信息数, 默认为10
     * @param country  查询国家
     * @param type     查询类型, 1推荐视频, 2关注人的视频, 3最新视频, 默认是推荐视频
     * @return
     */
    @Override
    public Object recommendVideo(Integer userId, Integer currPage, Integer pageSize, String country, Integer type) {

        // 分页查询短视频信息, 包含视频音乐, 视频发布人信息,视屏所属话题
        Map<String, Object> search = new HashMap<>(0);
        search.put("currPage", (currPage - 1) * pageSize);
        search.put("pageSize", pageSize);
        search.put("userId", userId);
        search.put("country", country);
        search.put("type", type);

        // 视频列表内容集合
        List<RecommendVideo> videoInfos = videoInfoDao.findVideoByRecommend(search);
        boolean flag = (videoInfos == null || videoInfos.size() < 1) && type != 2;
        if (flag) {
            search.put("country", "");
            videoInfos = videoInfoDao.findVideoByRecommend(search);
        }

        for (RecommendVideo videoInfo : videoInfos) {
            // 从redis中获取是否已经点赞
            videoInfo.setIsZan(getVideoLike(videoInfo.getVideoId(), userId).toString());

            // 根据视频id查询视频简介
            String introduction = videoInfoIntroductionDao.findIntroductionByVideoId(videoInfo.getVideoId());
            if (StringUtils.isNotBlank(introduction)) {
                // 将内容信息转成集合对象
                List<VideoIntroduction> list = JsonUtils.jsonToList(introduction, VideoIntroduction.class);

                for (VideoIntroduction videoIntroduction : list) {
                    Integer videoType = videoIntroduction.getType();
                    if (videoType == 3) {
                        // 根据用户id查询用户名
                        videoIntroduction.setContent("@" + userInfoDao.selectNick_name(Integer.parseInt(videoIntroduction.getUserId())));
                    }
                }

                videoInfo.setVideoIntroductionJson(JsonUtils.objectToJson(list));
            }
        }

        // 查询信息总条数
        Integer totalCount = videoInfoDao.findVideoTotalCountByRecommend(search);
        // 返回查询到的信息
        return R.ok("查询成功").put("result", new PageUtils(videoInfos, totalCount, pageSize, currPage));
    }

    /**
     * @Author:YS
     * @Description:6.2.1.7.1我的点赞直播页面 带分页
     * @Date:2018/2/2
     */
    @Override
    public List<Object> videoFabulous(Map<String, Object> map) {
        return videoInfoDao.videoFabulous(map);
    }

    @Override
    public int videoFabulousTotal(Map<String, Object> map) {
        return videoInfoDao.videoFabulousTotal(map);
    }

    /**
     * @Author:YS
     * @Description:6.2.1.8.1进入话题页面
     * @Date:2018/2/2
     */
    @Override
    public List<Object> topicMaster(Map<String, Object> map) {
        return videoInfoDao.topicMaster(map);
    }

    //分页参数
    @Override
    public int topicMasterTotal(Map<String, Object> map) {
        return videoInfoDao.topicMasterTotal(map);
    }

    /**
     * @Author:YS
     * @Description:我的公开视频点赞页面 我的点赞
     * @Date:2018/2/24
     */
    @Override
    public List<Object> findZanVideo(Map<String, Object> map) {
        return videoInfoDao.findZanVideo(map);
    }

    @Override
    public int findZanVideoTotal(Map<String, Object> map) {
        return videoInfoDao.findZanVideoTotal(map);
    }

    /**
     * @Author:YS
     * @Description: 我的直播历史
     * @Date:2018/2/25
     */
    @Override
    public List<Map<String, Object>> liveHistory(Map<String, Object> map) {
        List<Map<String, Object>> liveHistory = videoInfoDao.liveHistory(map);//我的直播目前直播增加粉丝数 还有收益
        for (Map<String, Object> stringObjectMap : liveHistory) {
            stringObjectMap.put("stime", stringObjectMap.get("stime"));
            stringObjectMap.put("etime", stringObjectMap.get("etime"));
            stringObjectMap.put("userId", stringObjectMap.get("user_id"));
            int addFansVideo = videoInfoDao.AddFansVideo(stringObjectMap);//直播增加了多少粉丝
            Integer addPrice = videoInfoDao.AddPrice(stringObjectMap);//直播赚了多少钱
            stringObjectMap.put("addFansVideo", addFansVideo);
            stringObjectMap.put("addPrice", addPrice == null ? 0 : addPrice);
        }
        return liveHistory;
    }

    /*分页*/
    @Override
    public int liveHistoryTotal(Map<String, Object> map) {
        return videoInfoDao.liveHistoryTotal(map);
    }

    /**
     * @Author:YS
     * @Description: 获取前六个最高的点赞数量
     * @Date:2018/3/7
     * @param:
     */
    @Override
    public List<Map<String, Integer>> TopicVideo(Map<String, Object> map) {
        return videoInfoDao.TopicVideo(map);
    }

    /**
     * 发布短视频接口 -- 废弃暂时不用
     *
     * @param videoInfo 视频信息
     * @param topicName 话题名称
     * @param friend    @好友信息
     * @return
     */
    @Override
    @Transactional
    public Object publishVideo(VideoInfo videoInfo, String topicName, String friend) {

        // 视频话题操作
        /*if (videoInfo.getTopicId() == null && StringUtils.isNotBlank(topicName)) {
            VideoTopic videoTopic = videoTopicDao.findByTopicName(topicName);
            if (videoTopic == null) {
                //该话题不存在, 发布新话题
                videoTopic = new VideoTopic();
                videoTopic.setUserId(videoInfo.getUserId());
                videoTopic.setCreateTime(new Date());
                videoTopic.setTname(topicName);
                videoTopicDao.insertSelective(videoTopic);
            }
            videoInfo.setTopicId(videoTopic.getTopicId());
        }*/
        videoInfo.setCreateTime(new Date());
        videoInfoDao.insertSelective(videoInfo);

        // 判断该视频是否有@好友
        if (StringUtils.isNotBlank(friend)) {
            String[] friends = friend.split(",");
            List<VideoAitFriend> list = new ArrayList<>(0);

            for (String id : friends) {
                VideoAitFriend videoAitFriend = new VideoAitFriend();
                videoAitFriend.setAitTime(new Date());
                videoAitFriend.setUserId(Integer.parseInt(id));
                videoAitFriend.setVideoId(videoInfo.getVideoId());
                list.add(videoAitFriend);
            }

            videoAitFriendDao.saveBatch(list);
        }

        // 推送至粉丝
        VideoPushManyProduct.pushVideoLike(videoInfo.getVideoId(), videoInfo.getUserId());
        return R.result(1, "发布成功", videoInfo.getUrl());
    }

    /**
     * 进入话题详情, 音乐详情, 视频分类详情需要展示的视频列表
     *
     * @param query 查询条件
     *              topicId 话题id
     *              typeId 类型id
     *              musicId 音乐id
     *              type 查询类型 0相关1全球2本国
     *              limit 每页显示信息数
     *              page 当前页
     * @return
     */
    @Override
    public List<Object> findVideoRelevantList(Query query) {
        return videoInfoDao.findVideoRelevantList(query);
    }

    /**
     * 进入话题详情, 音乐详情, 视频分类详情需要展示的视频数量
     *
     * @param query 查询条件
     *              topicId 话题id
     *              typeId 类型id
     *              musicId 音乐id
     *              type 查询类型 0相关1全球2本国
     *              limit 每页显示信息数
     *              page 当前页
     * @return
     */
    @Override
    public Integer findVideoRelevantTotal(Query query) {
        return videoInfoDao.findVideoRelevantTotal(query);
    }

    /**
     * 查询收到的礼物排行列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public List<Object> haveGiftRankList(Query query) {
        return videoGiftSendDao.haveGiftRankList(query);
    }

    /**
     * 查询收到的礼物排行数量
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public Integer haveGiftRankTotal(Query query) {
        return videoGiftSendDao.haveGiftRankTotal(query);
    }

    /**
     * 根据视频url判断视频是否存在
     *
     * @param videoUrl 视频路径
     * @return
     */
    @Override
    public List<Object> findVideoImgByVideoUrl(String videoUrl) {
        return videoInfoDao.findVideoImgByVideoUrl(videoUrl);
    }

    /**
     * 查询本国热门视频
     *
     * @param offset  开始查询数量
     * @param limit   每页显示信息数
     * @param country 当前国家
     * @param state   状态, 1查询本国热门, 2查询国际热门
     * @return
     */
    @Override
    public List<Object> findDomesticHotVideo(Integer offset, Integer limit, String country, Integer state) {
        return videoInfoDao.findDomesticHotVideo(offset, limit, country, state);
    }

    /**
     * 删除视频
     *
     * @param videoId 视频id
     * @return
     */
    @Override
    public int deleteVideo(Integer videoId) {
        return videoInfoDao.deleteVideo(videoId);
    }

    /**
     * 修改视频状态, 私密视频改为公开视频
     *
     * @param videoId 视频id
     * @return
     */
    @Override
    public int updateState(Integer videoId) {
        return videoInfoDao.updateState(videoId);
    }

    /**
     * 查询关注的人的视频列表
     *
     * @param map 查询条件
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @Override
    public List<RecommendVideo> findAttentionVideoList(Map<String, Object> map) {
        return videoInfoDao.findAttentionVideoList(map);
    }

    /**
     * 查询关注的人的视频数量
     *
     * @param map 查询条件
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @Override
    public Integer findAttentionVideoTotal(Map<String, Object> map) {
        return videoInfoDao.findAttentionVideoTotal(map);
    }

    /**
     * 发布视频接口
     *
     * @param videoInfo  视频信息
     * @param prospectus 视频描述信息
     * @return
     */
    @Override
    @Transactional
    public Object pushVideo(VideoInfo videoInfo, String prospectus) {
        Date date = new Date();

        // 保存视频数据
        videoInfoDao.insertSelective(videoInfo);

        if (StringUtils.isNotBlank(prospectus)) {
            // 将内容信息转成集合对象
            List<VideoIntroduction> list = JsonUtils.jsonToList(prospectus, VideoIntroduction.class);

            // 视频话题操作集合
            List<VideoTopicBelong> topicIds = new ArrayList<>(0);
            // 视频@好友操作集合
            List<VideoAitFriend> videoAitFriends = new ArrayList<>(0);

            // 获取相信信息
            for (VideoIntroduction topic : list) {
                // 保存的是话题
                if (topic.getType() == 2) {
                    String substring = topic.getContent().substring(1);
                    VideoTopic videoTopic = videoTopicDao.findByTopicName(substring);
                    if (videoTopic == null) {
                        videoTopic = new VideoTopic(substring, videoInfo.getUserId(), date);
                        videoTopicDao.insertSelective(videoTopic);
                    }
                    topicIds.add(new VideoTopicBelong(videoTopic.getTopicId(), videoInfo.getVideoId(), date));
                    topic.setUserId(videoTopic.getTopicId().toString());
                }
                // 保存的是用户
                if (topic.getType() == 3 && StringUtils.isNotBlank(topic.getUserId())) {
                    videoAitFriends.add(new VideoAitFriend(videoInfo.getVideoId(), Integer.parseInt(topic.getUserId()), date));
                }
            }
            if (topicIds.size() > 0) {
                // 批量保存视频话题操作
                videoTopicBelongDao.saveBatch(topicIds);
            }

            if (videoAitFriends.size() > 0) {
                // 批量保存@好友信息
                videoAitFriendDao.saveBatch(videoAitFriends);
                List<Integer> userIds = videoAitFriends.stream().map(VideoAitFriend::getUserId).collect(Collectors.toList());
                // 推送消息
                VideoAtPushProduct.pushVideoAtMany(videoInfo.getVideoId(), videoInfo.getUserId(), userIds);
            }

            // 将内容信息保存到视频简介表中
            videoInfoIntroductionDao.save(videoInfo.getVideoId(), JsonUtils.objectToJson(list));
        }
        // 推送至粉丝
        VideoPushManyProduct.pushVideoLike(videoInfo.getVideoId(), videoInfo.getUserId());
        return R.result(1, "发布成功", videoInfo.getUrl());
    }

    /**
     * 视频分享数量+1
     *
     * @param videoId 视频id
     * @return
     */
    @Override
    public int addShareNumByVideoId(Integer videoId) {
        return videoInfoDao.addShareNumByVideoId(videoId);
    }

    /**
     * 判断是否点赞
     *
     * @param videoId 视频id
     * @param userId  用户id
     * @return
     */
    private Integer getVideoLike(Integer videoId, Integer userId) {
        String isLike = redisUtils.get(RedisKeys.VIDEO_LIKE + videoId + "_" + userId);
        if (StringUtils.isBlank(isLike)) {
            Integer like = videoCollectionLogDao.findLogIdByVideoIdAndUserId(videoId, userId);
            if (like > 0) {
                redisUtils.set(RedisKeys.VIDEO_LIKE + videoId + "_" + userId, 1, RedisUtils.NOT_EXPIRE);
            }
            return like;
        }
        return 1;
    }

}
