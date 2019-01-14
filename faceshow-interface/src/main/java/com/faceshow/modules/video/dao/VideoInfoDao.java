package com.faceshow.modules.video.dao;

import com.faceshow.common.utils.NumUtils;
import com.faceshow.common.utils.Query;
import com.faceshow.modules.video.entity.RecommendVideo;
import com.faceshow.modules.video.entity.VideoInfo;
import com.faceshow.modules.video.entity.VideoPlayLog;
import com.faceshow.modules.video.vo.VideoInfoSelectRowVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 视频信息操作Dao
 */
@Mapper
public interface VideoInfoDao {
    int deleteByPrimaryKey(Integer videoId);

    int insertSelective(VideoInfo record);

    VideoInfo selectByPrimaryKey(Integer videoId);

    int updateByPrimaryKeySelective(VideoInfo record);

    /**
     * @Author:YS
     * @Description: 接口是我的视频页面 点击关注和取消关注的时候用的接口
     * @Date:2018/1/24
     */
    int deleteCancel(Map<String, Object> map);//取消关注

    int InsertCollection(Map<String, Object> map);//关注

    int updateCancel(Integer video_id);//取消关注

    int updateFollow(Integer video_id);//关注


    /**
     * @Author:YS
     * @Description: 公开视频详情页面 - 点赞数量 还有评论数量
     * @Date:2018/1/24
     */
    VideoInfoSelectRowVo lookVideoDetail(Map<String, Object> map);

    int lookVideoDetailZanNum(Map<String, Object> map);//点赞数量

    int lookVideoDetailCommentNum(Map<String, Object> map);//还有评论数量

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
     * 根据类型查询当前热门视频, 查询数量为15
     *
     * @param typeId 类型id
     * @return
     */
    List<Object> findHotVideoByTypeId(int typeId);


    /**
     * 分页查询视频详情
     *
     * @param search 查询条件, 包含分页信息
     * @return
     */
    List<RecommendVideo> findVideoByRecommend(Map<String, Object> search);

    /**
     * 查询信息总条数
     *
     * @param search
     * @return
     */
    Integer findVideoTotalCountByRecommend(Map<String, Object> search);

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
     * @Description:我的点赞 公开视频点赞
     * @Date:2018/2/24
     */
    List<Object> findZanVideo(Map<String, Object> map);

    int findZanVideoTotal(Map<String, Object> map);//分页

    /**
     * @Author:YS
     * @Description:看看两者的关系是什么
     * @Date:2018/2/24
     */

    Map<String, Object> FansYes(Map<String, Object> map);//看看两者的关系是什么

    /**
     * @Author:YS
     * @Description: 我的直播历史
     * @Date:2018/2/25
     */
    List<Map<String, Object>> liveHistory(Map<String, Object> map);

    int liveHistoryTotal(Map<String, Object> map);//分页


    int AddFansVideo(Map<String, Object> map);//<!--直播的时候增加了多少个粉丝？-->

    Integer AddPrice(Map<String, Object> map);//<!--直播的时候 赚取了多少钻石-->


    /**
     * @Author:YS
     * @Description: 获取前六个最高的点赞数量
     * @Date:2018/1/26
     */
    List<Map<String, Integer>> TopicVideo(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查看这个用户是否点赞过
     * @Date:2018/3/7
     * @param:WHERE video_id     user_id
     */

    Map<String, Object> OrCollection_log(Map<String, Object> map);


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
     * 查询话题, 分类, 音乐 得分最高的六个人
     *
     * @param type  1话题, 2分类, 3音乐
     * @param param 话题,分类, 音乐id
     * @return
     */
    List<Object> findHightScoreUser(@Param("type") Integer type, @Param("param") Integer param);

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
    List<Object> findDomesticHotVideo(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("country") String country, @Param("state") Integer state);

    /**
     * @author: YS
     * @Date:2018/4/13 14:10
     * @param: topic
     * @explain： 首页展示的话题页面   根据话题ID查
     * @return:
     */

    List<Map<String, Object>> selectByTopic(Map<String, Object> map);

    /**
     * @author: YS
     * @Date:2018/4/23 15:39
     * @param:
     * @explain：根据视频ID查询视频的名字
     * @return:
     */
    Map<String, Object> selectvname(Map<String, Object> map);

    /**
     * 修改视频评论数量+1或者-1
     *
     * @param numUtils videoId, commentNum
     * @return
     */
    int updateCommentNumOne(NumUtils numUtils);

    /**
     * 直接修改视频评论数量
     *
     * @param numUtils videoId, commentNum
     * @return
     */
    int updateCommentNumMany(NumUtils numUtils);

    /**
     * 修改点赞数量+1 或者-1
     *
     * @param numUtils videoId, commentNum
     * @return
     */
    int updateLikeNum(NumUtils numUtils);

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
     * 根据视频id查询用户id
     *
     * @param videoId 视频id
     * @return
     */
    Integer findUserIdByVideoId(Integer videoId);

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
     * @author: YS
     * @Date:2018/4/25 14:47
     * @param:
     * @explain： <!--根據 video_id 查詢用户ID-->
     * @return:
     */
    int selectVideoUserId(Integer videoId);

    /**
     * 根据视频id查询视频使用音乐的音乐发起人
     *
     * @param videoId
     * @return
     */
    Integer findMusicUserIdByVideoId(Integer videoId);

    /**
     * 根据视频id添加视频播放次数
     *
     * @param list 视频播放记录
     * @return
     */
    int addplayNumByVideoId(List<VideoPlayLog> list);

    /**
     * 视频分享数量+1
     *
     * @param videoId 视频id
     * @return
     */
    int addShareNumByVideoId(Integer videoId);
}