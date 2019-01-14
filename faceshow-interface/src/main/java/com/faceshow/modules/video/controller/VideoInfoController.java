package com.faceshow.modules.video.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.*;
import com.faceshow.modules.music.entity.MusicInfo;
import com.faceshow.modules.music.service.MusicInfoService;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.user.service.UserInfoService;
import com.faceshow.modules.video.entity.RecommendVideo;
import com.faceshow.modules.video.entity.VideoInfo;
import com.faceshow.modules.video.service.VideoInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 我的视频页面
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.user.controller<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/1/24 10:19
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/userVideo")
public class VideoInfoController extends AbstractController {

    @Autowired
    private VideoInfoService videoInfoService;

    @Autowired
    private MusicInfoService musicInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private VideoUtils videoUtils;

    /**
     * 视频点赞
     *
     * @param video_id 视频id
     * @param userId   用户id
     * @return
     */
    @SysLog
    @PostMapping("/InsertCollection")
    public Object InsertCollection(Integer video_id, Integer userId) {
        return videoInfoService.InsertCollection(video_id, getUserInfoId(), true);
    }

    /**
     * 视频取消点赞
     *
     * @param video_id 视频id
     * @param userId   用户id
     * @return
     */
    @SysLog
    @PostMapping("/updateCancel")
    public Object updateCancel(Integer video_id, Integer userId) {
        return videoInfoService.updateCancel(video_id, getUserInfoId(), true);
    }

    /**
     * @Author:YS
     * @Description: 公开视频详情页面 - 传递参数userId
     * @Date:2018/1/24
     */
    @SysLog
    @PostMapping("/lookVideoDetail")
    public Object lookVideoDetail(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        Object data = videoInfoService.lookVideoDetail(map);
        return R.ok("OK").put("result", data);
    }

    /**
     * @Author:YS
     * @Description: 我参与的话题- 传递参数userId
     * @Date:2018/1/24
     */
    @SysLog
    @PostMapping("/participateTopic")
    public Object participateTopic(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Map<String, Object>> infoDetail = videoInfoService.participateTopic(query);
        int total = videoInfoService.participateTopicTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * @Author:YS
     * @Description: 我发出的话题- 传递参数userId
     * @Date:2018/1/24
     */
    @SysLog
    @PostMapping("/deliverTopic")
    public Object deliverTopic(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = videoInfoService.deliverTopic(query);
        int total = videoInfoService.deliverTopicTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
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
    @SysLog
    @PostMapping("/recommendVideo")
    public Object recommendVideo(@RequestParam(defaultValue = "1") Integer type, @RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize, String country) {
        return videoInfoService.recommendVideo(getUserInfoId(), currPage, pageSize, country, type);
    }

    /**
     * @Author:YS
     * @Description:6.2.1.7.1我的点赞直播页面
     * @Date:2018/2/2
     */
    @PostMapping("/videoFabulous")
    public Object videoFabulous(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = videoInfoService.videoFabulous(query);
        int total = videoInfoService.videoFabulousTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * @Author:YS
     * @Description: 6.2.1.8.1进入话题页面
     * @Date:2018/2/2
     */
    @SysLog
    @PostMapping("/topicMaster")
    public Object topicMaster(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = videoInfoService.topicMaster(query);
        int total = videoInfoService.topicMasterTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * @Author:YS
     * @Description: 我的点赞页面
     * @Date:2018/2/24
     */
    @SysLog
    @PostMapping("/findZanVideo")
    public Object findZanVideo(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = videoInfoService.findZanVideo(query);
        int total = videoInfoService.findZanVideoTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * @Author:YS
     * @Description: 我的直播历史
     * @Date:2018/2/25
     */
    @SysLog
    @PostMapping("/liveHistory")
    public Object liveHistory(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Map<String, Object>> infoDetail = videoInfoService.liveHistory(query);
        int total = videoInfoService.liveHistoryTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }


    /**
     * @Author:YS
     * @Description: 公开视频详情页面 - 传递参数userId
     * @Date:2018/1/24
     */
    @SysLog
    @PostMapping("/lookTopicVideo")
    public Object lookTopicVideo(@RequestParam Map<String, Object> map) {
        List<Map<String, Integer>> video = videoInfoService.TopicVideo(map);
        return R.result(1, "OK", video == null ? Collections.emptyList() : video);
    }

    /**
     * 发布短视频接口 -- 废弃暂时不用
     *
     * @param videoFile 视频文件
     * @param imgFile   图片文件
     * @param videoInfo 视频信息
     * @param topicName 话题名称
     * @param friend    @好友信息
     * @return
     */
    @SysLog
    @PostMapping("/publish")
    public Object publishVideo(MultipartFile videoFile, MultipartFile imgFile, VideoInfo videoInfo, String topicName, String friend) {
        videoInfo.setUserId(getUserInfoId());
        if (videoFile == null || videoFile.getSize() < 1) {
            // 没有上传文件
            return R.result(0, "没有找到需要上传的文件", "");
        }

        // 上传视频文件
        Map<String, String> upload = null;

        try {
            // 如果有上传视频封面, 接收并保存
            if (imgFile != null && imgFile.getSize() > 0) {
                String imgName = imgFile.getOriginalFilename();
                String imgUrl = FastDFSClient.uploadFile(imgFile.getBytes(), imgName.substring(imgName.lastIndexOf(".") + 1), null);
                videoInfo.setImg(imgUrl);
            }

            if (StringUtils.isBlank(videoInfo.getImg())) {
                // 如果没有有上传视频封面, 从视频中获取视频封面
                upload = videoUtils.videoUpload(videoFile, true, videoInfo.getMusicId() == null || videoInfo.getMusicId() == 0);
                videoInfo.setImg(upload.get("imgUrl"));
            } else {
                upload = videoUtils.videoUpload(videoFile, false, videoInfo.getMusicId() == null || videoInfo.getMusicId() == 0);
            }
            videoInfo.setUrl(upload.get("videoUrl"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果没有上传音乐id, 上传音乐到服务器
        if (videoInfo.getMusicId() == null || videoInfo.getMusicId() == 0) {
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setUrl(upload.get("musicUrl"));
            musicInfo.setCreateTime(new Date());
            musicInfo.setUserId(videoInfo.getUserId());
            // 设置音乐名为: 某某某的原声音乐
            musicInfo.setMname(userInfoService.findNickNameByUserId(videoInfo.getUserId()) + ("cn".equalsIgnoreCase(videoInfo.getCountry()) ? "的原创声音" : " voice"));
            musicInfo.setCountry(videoInfo.getCountry());
            musicInfoService.insertSelective(musicInfo);
            videoInfo.setMusicId(musicInfo.getMusicId());
        }

        return videoInfoService.publishVideo(videoInfo, topicName, friend);

    }

    /**
     * 进入话题详情, 音乐详情, 视频分类详情需要展示的视频
     *
     * @param map 查询条件
     *            topicId 话题id
     *            typeId 类型id
     *            musicId 音乐id
     *            country 国家名称
     *            type 查询类型 0相关1全球2本国
     *            limit 每页显示信息数
     *            page 当前页
     *            longitude 经度
     *            latitude 维度
     * @return
     */
    @SysLog
    @PostMapping("/findVideoRelevant")
    public Object findVideoRelevant(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        Query query = new Query(map);
        List<Object> list = videoInfoService.findVideoRelevantList(query);
        Integer total = videoInfoService.findVideoRelevantTotal(query);
        return R.ok("OK").put("page", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * 查询某个短视频收到的礼物排行榜
     *
     * @param map 查询参数
     *            videoId 类型id
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @SysLog
    @PostMapping("/haveGiftRank")
    public Object haveGiftRank(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> list = videoInfoService.haveGiftRankList(query);
        Integer total = videoInfoService.haveGiftRankTotal(query);
        return R.ok("OK").put("page", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * 删除视频
     *
     * @param videoId 视频id
     * @return
     */
    @SysLog
    @PostMapping("/delete")
    public Object deleteVideo(Integer videoId) {
        int i = videoInfoService.deleteVideo(videoId);
        return R.result(1, "OK", "");
    }

    /**
     * 修改视频状态, 私密视频改为公开视频
     *
     * @param videoId 视频id
     * @return
     */
    @SysLog
    @PostMapping("/updateState")
    public Object updateState(Integer videoId) {
        int i = videoInfoService.updateState(videoId);
        return R.result(1, "OK", "");
    }

    /**
     * 查询关注的人的视频
     *
     * @param map 查询条件
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @SysLog
    @PostMapping("/attention")
    public Object attention(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        Query query = new Query(map);

        Integer total = videoInfoService.findAttentionVideoTotal(query);

        if (total == 0) {
            return R.ok("OK").put("page", new PageUtils(Collections.emptyList(), total, query.getLimit(), query.getPage()));
        }

        List<RecommendVideo> list = videoInfoService.findAttentionVideoList(query);

        return R.ok("OK").put("page", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * 发布视频接口
     *
     * @param videoFile  视频文件
     * @param imgFile    视频封面文件
     * @param videoInfo  视频信息
     * @param prospectus 视频描述信息
     * @return
     */
    @SysLog
    @PostMapping("/pushVideo")
    public Object pushVideo(MultipartFile videoFile, MultipartFile imgFile, VideoInfo videoInfo, String prospectus) {
        videoInfo.setUserId(getUserInfoId());
        videoInfo.setCreateTime(new Date());
        if (videoFile == null || videoFile.getSize() < 1) {
            // 没有上传文件
            return R.result(0, "没有找到需要上传的文件", "");
        }

        // 上传视频文件
        Map<String, String> upload = null;

        try {
            // 如果有上传视频封面, 接收并保存
            if (imgFile != null && imgFile.getSize() > 0) {
                String imgName = imgFile.getOriginalFilename();
                String imgUrl = FastDFSClient.uploadFile(imgFile.getBytes(), imgName.substring(imgName.lastIndexOf(".") + 1), null);
                videoInfo.setImg(imgUrl);
            }

            if (StringUtils.isBlank(videoInfo.getImg())) {
                // 如果没有有上传视频封面, 从视频中获取视频封面
                upload = videoUtils.videoUpload(videoFile, true, videoInfo.getMusicId() == null || videoInfo.getMusicId() == 0);
                videoInfo.setImg(upload.get("imgUrl"));
            } else {
                upload = videoUtils.videoUpload(videoFile, false, videoInfo.getMusicId() == null || videoInfo.getMusicId() == 0);
            }
            videoInfo.setUrl(upload.get("videoUrl"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果没有上传音乐id, 上传音乐到服务器
        if (videoInfo.getMusicId() == null || videoInfo.getMusicId() == 0) {
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setUrl(upload.get("musicUrl"));
            musicInfo.setCreateTime(new Date());
            musicInfo.setUserId(videoInfo.getUserId());
            // 设置音乐名为: 某某某的原声音乐
            musicInfo.setMname(userInfoService.findNickNameByUserId(videoInfo.getUserId()) + ("cn".equalsIgnoreCase(videoInfo.getCountry()) ? "的原创声音" : " voice"));
            musicInfo.setCountry(videoInfo.getCountry());
            musicInfoService.insertSelective(musicInfo);
            videoInfo.setMusicId(musicInfo.getMusicId());
        }

        return videoInfoService.pushVideo(videoInfo, prospectus);
    }
}

