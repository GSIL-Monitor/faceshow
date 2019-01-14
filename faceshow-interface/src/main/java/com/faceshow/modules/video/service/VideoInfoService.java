package com.faceshow.modules.video.service;

import com.faceshow.common.utils.Query;
import com.faceshow.modules.video.entity.RecommendVideo;
import com.faceshow.modules.video.entity.VideoInfo;
import com.faceshow.modules.video.vo.VideoInfoSelectRowVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 视频信息操作service接口
 */
public interface VideoInfoService {
    int deleteByPrimaryKey(Integer videoId);

    int insertSelective(VideoInfo videoInfo);

    VideoInfo selectByPrimaryKey(Integer videoId);

    int updateByPrimaryKeySelective(VideoInfo record);

    /**
     * 视频点赞
     *
     * @param video_id 视频id
     * @param userId   用户id
     * @return
     */
    Object InsertCollection(Integer video_id, Integer userId, boolean flag);

    /**
     * 视频取消点赞
     *
     * @param video_id 视频id
     * @param userId   用户id
     * @return
     */
    Object updateCancel(Integer video_id, Integer userId, boolean flag);

    /**
     * @Author:YS
     * @Description: 公开视频详情页面 -
     * @Date:2018/1/24
     */
    VideoInfoSelectRowVo lookVideoDetail(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 我的话题页面 我参与的话题
     * @Date:2018/1/24
     */
    List<Map<String, Object>> participateTopic(Map<String, Object> map);

    int participateTopicTotal(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 我的话题页面 我发出的话题
     * @Date:2018/1/24
     */
    List<Object> deliverTopic(Map<String, Object> map);

    int deliverTopicTotal(Map<String, Object> map);

    /**
     * 进入推荐短视频页面, 开始推荐短视频
     *
     * @param currPage 当前页, 默认为1
     * @param pageSize 每页显示信息数, 默认为10
     * @param country  查询国家
     * @param type     查询类型, 1推荐视频, 2关注人的视频, 3最新视频, 默认是推荐视频
     * @return
     */
    Object recommendVideo(Integer userId, Integer currPage, Integer pageSize, String country, Integer type);

    /**
     * @Author:YS
     * @Description:6.2.1.7.1我的点赞直播页面 带分页
     * @Date:2018/2/2
     */
    List<Object> videoFabulous(Map<String, Object> map);

    int videoFabulousTotal(Map<String, Object> map);//分页

    /**
     * @Author:YS
     * @Description:6.2.1.8.1进入话题页面
     * @Date:2018/2/2
     */
    List<Object> topicMaster(Map<String, Object> map);

    int topicMasterTotal(Map<String, Object> map);//分页

    /**
     * @Author:YS
     * @Description:我的公开视频点赞页面 我的点赞 分页
     * @Date:2018/2/24
     */

    List<Object> findZanVideo(Map<String, Object> map);

    int findZanVideoTotal(Map<String, Object> map);//分页

    /**
     * @Author:YS
     * @Description: 我的直播历史
     * @Date:2018/2/25
     */
    List<Map<String, Object>> liveHistory(Map<String, Object> map);

    int liveHistoryTotal(Map<String, Object> map);//分页

    /**
     * @Author:YS
     * @Description: 获取前六个最高的点赞数量
     * @Date:2018/1/26
     */
    List<Map<String, Integer>> TopicVideo(Map<String, Object> map);

    /**
     * 发布短视频接口 -- 废弃暂时不用
     *
     * @param videoInfo 视频信息
     * @param topicName 话题名称
     * @param friend    @好友信息
     * @return
     */
    Object publishVideo(VideoInfo videoInfo, String topicName, String friend);


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
    List<Object> findVideoRelevantList(Query query);

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
    Integer findVideoRelevantTotal(Query query);

    /**
     * 查询收到的礼物排行列表
     *
     * @param query 查询条件
     * @return
     */
    List<Object> haveGiftRankList(Query query);

    /**
     * 查询收到的礼物排行数量
     *
     * @param query 查询条件
     * @return
     */
    Integer haveGiftRankTotal(Query query);

    /**
     * 根据视频url判断视频是否存在
     *
     * @param videoUrl 视频路径
     * @return
     */
    List<Object> findVideoImgByVideoUrl(String videoUrl);

    /**
     * 查询本国热门视频
     *
     * @param offset  开始查询数量
     * @param limit   每页显示信息数
     * @param country 当前国家
     * @param state   状态, 1查询本国热门, 2查询国际热门
     * @return
     */
    List<Object> findDomesticHotVideo(Integer offset, Integer limit, String country, Integer state);

    /**
     * 删除视频
     *
     * @param videoId 视频id
     * @return
     */
    int deleteVideo(Integer videoId);

    /**
     * 修改视频状态, 私密视频改为公开视频
     *
     * @param videoId 视频id
     * @return
     */
    int updateState(Integer videoId);

    /**
     * 查询关注的人的视频列表
     *
     * @param map 查询条件
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    List<RecommendVideo> findAttentionVideoList(Map<String, Object> map);

    /**
     * 查询关注的人的视频数量
     *
     * @param map 查询条件
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    Integer findAttentionVideoTotal(Map<String, Object> map);

    /**
     * 发布视频接口
     *
     * @param videoInfo  视频信息
     * @param prospectus 视频描述信息
     * @return
     */
    Object pushVideo(VideoInfo videoInfo, String prospectus);

    /**
     * 视频分享数量+1
     *
     * @param videoId 视频id
     * @return
     */
    int addShareNumByVideoId(Integer videoId);
}
