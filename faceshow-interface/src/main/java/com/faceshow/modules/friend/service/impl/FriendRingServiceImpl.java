package com.faceshow.modules.friend.service.impl;


import com.faceshow.common.MQ.message.likenum.FriendCommentLikeNumProduct;
import com.faceshow.common.MQ.message.likenum.FriendLikeNumProduct;
import com.faceshow.common.MQ.message.push.FriendAtPushProduct;
import com.faceshow.common.MQ.message.push.FriendCommentProduct;
import com.faceshow.common.MQ.message.push.FriendLikePushProduct;
import com.faceshow.common.utils.*;
import com.faceshow.common.utils.mapbean.MapResultBean;
import com.faceshow.modules.friend.dao.*;
import com.faceshow.modules.friend.entity.FriendRing;
import com.faceshow.modules.friend.entity.FriendRingComment;
import com.faceshow.modules.friend.entity.FriendRingFile;
import com.faceshow.modules.friend.entity.FriendRingLookSet;
import com.faceshow.modules.friend.service.FriendRingService;
import com.faceshow.modules.friend.vo.FriendRingSelectRowVo;
import com.faceshow.modules.video.dao.VideoAitFriendDao;
import com.faceshow.modules.video.dao.VideoTopicDao;
import com.faceshow.modules.video.entity.VideoAitFriend;
import com.faceshow.modules.video.entity.VideoComment;
import com.faceshow.modules.video.entity.VideoInfo;
import com.faceshow.modules.video.entity.VideoTopic;
import com.faceshow.modules.video.service.VideoCommentService;
import com.faceshow.modules.video.service.VideoInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 朋友圈操作Service接口实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/1/26 14:53
 * -------------------------------------------------------------- <br>
 */
@Service
@Transactional
public class FriendRingServiceImpl implements FriendRingService {

    @Autowired
    private FriendRingDao friendRingDao;

    @Autowired
    private FriendRingLikeDao friendRingLikeDao;

    @Autowired
    private FriendRingCommentDao friendRingCommentDao;


    @Autowired
    private FriendRingFileDao friendRingFileDao;

    @Autowired
    private FriendRingCommentLikeDao friendRingCommentLikeDao;

    @Autowired
    private FriendRingLookSetDao friendRingSetDao;

    @Autowired
    private VideoUtils videoUtils;

    @Autowired
    private ThumbnailatorUtils thumbnailatorUtils;

    @Autowired
    private VideoAitFriendDao videoAitFriendDao;

    @Autowired
    private VideoCommentService videoCommentService;

    @Autowired
    private VideoInfoService videoInfoService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 发布朋友圈动态, 添加消息
     *
     * @param friendRing 朋友圈发布内容
     * @param files      所有上传文件
     * @param users      @用户
     * @param videoType  视频所属类型
     * @param musicId    音乐id
     * @param isSource   视频来源
     * @return
     */
    @Override
    public Object insertSelective(FriendRing friendRing, String users, MultipartFile[] files, Integer videoType, Integer musicId, Byte isSource) {

        // 根据经纬度获取国家信息
        MapResultBean msg = BaiduMapUtils.getMsg(friendRing.getLatitude() + "," + friendRing.getLongitude());

        friendRing.setAddress(msg.getResult().getFormatted_address());

        String videoUrl = "";

        Integer friendId = null;

        // 发布的是视频
        if (friendRing.getType() == 2) {
            Map<String, String> videoUpload = null;
            try {
                // 上传视频
                videoUpload = videoUtils.videoUpload(files[0], true, musicId == null || musicId == 0);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("视频上传失败");
            }

            // 视频上传路径
            videoUrl = videoUpload.get("videoUrl");
            // 视频封面路径
            String videoImgUrl = videoUpload.get("imgUrl");

            // 设置朋友圈文件存储
            FriendRingFile friendRingFile = new FriendRingFile();
            friendRingFile.setFileSmallUrl(videoImgUrl);
            friendRingFile.setFileUrl(videoUrl);
            friendRingFile.setCreateTime(new Date());
            friendRingFile.setType(friendRing.getType());

            // 保存视频信息
            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setCreateTime(new Date());
            videoInfo.setTypeId(videoType);
            videoInfo.setImg(videoImgUrl);
            videoInfo.setUrl(videoUrl);
            videoInfo.setCountry(msg.getResult().getAddressComponent().getCountry_code_iso2());
            videoInfo.setUserId(friendRing.getUserId());
            videoInfo.setMusicId(musicId);
            videoInfo.setIntroduction(friendRing.getContent());
            videoInfo.setIsPrivate(friendRing.getShield() == 0 || friendRing.getShield() == 4 ? (byte) 1 : 2);
            videoInfo.setIsSource(isSource);
            videoInfoService.insertSelective(videoInfo);

            // 设置朋友圈关联视频操作
            friendRing.setVideoId(videoInfo.getVideoId());

            // 向朋友圈表中插入数据
            friendRing.setCreateTime(new Date());
            friendRingDao.insertSelective(friendRing);

            // 获取朋友圈主键id
            friendId = friendRing.getFriendId();

            // 向朋友圈文件表中插入数据
            friendRingFile.setFriendId(friendId);
            friendRingFileDao.insertSelective(friendRingFile);

            // 判断该动态是否屏蔽某些人或指定某些人查看
            if (friendRing.getShield() != 0 && StringUtils.isNotBlank(users)) {
                // 创建保存朋友圈@好友信息list
                List<Object> list = new ArrayList<>(0);

                // 创建保存视频@好友信息list
                List<VideoAitFriend> videoAitList = new ArrayList<>(0);

                // 用户id数组
                List<Integer> userIds = Stream.of(users.split(",")).map(Integer::valueOf).collect(Collectors.toList());

                // 提醒别人查看, 发送推送通知
                if (friendRing.getShield() == 4) {
                    FriendAtPushProduct.pushFriendAtMany(friendId, friendRing.getUserId(), userIds);
                }

                for (Integer useId : userIds) {
                    list.add(new FriendRingLookSet(friendId, useId));

                    videoAitList.add(new VideoAitFriend(videoInfo.getVideoId(), useId, new Date()));
                }
                friendRingSetDao.saveBatch(list);
                videoAitFriendDao.saveBatch(videoAitList);
            }

        } else {
            // 向朋友圈表中插入数据
            friendRing.setCreateTime(new Date());
            friendRingDao.insertSelective(friendRing);

            friendId = friendRing.getFriendId();

            // 判断该动态是否屏蔽某些人或指定某些人查看
            if (friendRing.getShield() != 0 && StringUtils.isNotBlank(users)) {

                // 用户id数组
                List<Integer> userIds = Stream.of(users.split(",")).map(Integer::valueOf).collect(Collectors.toList());

                // 创建保存朋友圈@好友信息list
                List<Object> list = new ArrayList<>(0);

                // 提醒别人查看, 发送推送通知
                if (friendRing.getShield() == 4) {
                    FriendAtPushProduct.pushFriendAtMany(friendId, friendRing.getUserId(), userIds);
                }

                for (Integer id : userIds) {
                    list.add(new FriendRingLookSet(friendId, id));
                }
                friendRingSetDao.saveBatch(list);
            }
        }

        //发布的是图片
        if (friendRing.getType() == 1) {
            List<FriendRingFile> fileList = saveFiles(files, friendRing.getFriendId(), friendRing.getType());
            // 批量保存文件
            friendRingFileDao.saveFiles(fileList);
        }

        Map<String, Object> friendRingMap = friendRingDao.findFriendRingByFriengId(friendId);
        List<Object> firendRingFileList = friendRingFileDao.findFileByFriendId(friendId);

        if (firendRingFileList == null) {

            // 将朋友圈详情信息添加到redis
            friendRingMap.put("fileList", new ArrayList<>(0));
            redisUtils.set(RedisKeys.FRIEND_RING + friendId, friendRingMap, RedisUtils.NOT_EXPIRE);
            // 没有文件上传
            return R.result(1, "消息发布成功", videoUrl);
        }

        // 将朋友圈详情信息添加到redis
        friendRingMap.put("fileList", firendRingFileList);
        redisUtils.set(RedisKeys.FRIEND_RING + friendId, friendRingMap, RedisUtils.NOT_EXPIRE);

        return R.result(1, "消息发布成功", videoUrl);

    }

    /**
     * 接收朋友圈批量上传图片
     *
     * @param files    批量图片
     * @param friendId 朋友圈id
     * @return 朋友圈文件集合对象
     */
    private List<FriendRingFile> saveFiles(MultipartFile[] files, Integer friendId, Byte type) {
        List<FriendRingFile> friendRingFiles = new ArrayList<>(0);

        if (files == null || files.length < 1) {
            // 没有要上传的图片
            return friendRingFiles;
        }
        try {
            for (MultipartFile file : files) {

                Map<String, Object> map = thumbnailatorUtils.uploadFileAndCreateThumbnail1(file);
                FriendRingFile friendRingFile = new FriendRingFile();
                friendRingFile.setFriendId(friendId);
                friendRingFile.setFileUrl(map.get("bigImgUrl").toString());
                friendRingFile.setFileSmallUrl(map.get("smallImgUrl").toString());
                friendRingFile.setCreateTime(new Date());
                friendRingFile.setType(type);

                friendRingFiles.add(friendRingFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friendRingFiles;
    }

    /**
     * 查询朋友圈动态
     *
     * @param userId   当前用户id
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @return
     */
    @Override
    public Object findFriendRing(Integer userId, Integer currPage, Integer pageSize) {
        System.out.println(new Date().toLocaleString());

        // 分页查询当前用户朋友圈内容
        Map<String, Object> search = new HashMap<String, Object>(0);
        search.put("userId", userId);
        search.put("currPage", (currPage - 1) * pageSize);
        search.put("pageSize", pageSize);
        List<Map<String, Object>> result = friendRingDao.findFriendRing(search);
        // 查询总记录数
        Integer totalCount = friendRingDao.findFriendRingTotalCount(userId);

        for (Map<String, Object> friendRing : result) {

            // 朋友圈id
            Integer friendId = Integer.parseInt(friendRing.get("friendId").toString());

            // 获取当前动态评论总数量
            Integer commendCount = friendRingCommentDao.findCountByFriendId(friendId);
            friendRing.put("commendCount", commendCount);

            if (!friendRing.get("type").equals(3)) {
                // 如果该动态不是纯文字, 则查询文件表
                List<Object> files = friendRingFileDao.findFileByFriendId(friendId);
                friendRing.put("fileList", files);
            } else {
                friendRing.put("fileList", new ArrayList<>(0));
            }

            // 拼接@好友字符串
            if (friendRing.get("shield").equals(4)) {
                List<String> list = friendRingSetDao.findByFriendId(friendId);
                String resultString = list != null && list.size() > 0 ? list.toString() : "";
                friendRing.put("users", resultString);
            } else {
                friendRing.put("users", "");
            }

            // 判断该动态, 当前用户是否点赞
            friendRing.put("isLike", friendRingLike(friendId, userId));
        }
        System.out.println(new Date().toLocaleString());
        return R.ok("查询成功").put("result", new PageUtils(result, totalCount, pageSize, currPage));
    }

    /**
     * 查看朋友圈内容详情(朋友圈内容由移动端处理, 实际查询当前朋友圈评论列表)
     *
     * @param friendId 朋友圈id
     * @param currPage 评论当前页
     * @param pageSize 每页显示评论数量
     * @param userId   当前用户id
     * @return
     */
    @Override
    public Object findFriendRingOne(Integer userId, Integer friendId, Integer currPage, Integer pageSize) {
        Map<String, Object> search = new HashMap<String, Object>(0);
        search.put("friendId", friendId);
        search.put("currPage", (currPage - 1) * pageSize);
        search.put("pageSize", pageSize);
        search.put("userId", userId);

        //  查询当前朋友圈的评论(一级评论)
        List<FriendRingSelectRowVo> friendCommentList = friendRingCommentDao.findCommentByFriendId(search);

        if (friendCommentList == null || friendCommentList.size() < 1) {
            return R.ok().put("page", new PageUtils(new ArrayList<>(0), 0, pageSize, currPage));
        }

        // 查询子评论
        List<FriendRingSelectRowVo> list = new ArrayList<>(0);
        findCommentByParent(friendCommentList, list);

        // 查询一级评论总数
        Integer commentTotalCount = friendRingCommentDao.findCommentTotalCountByFriendId(friendId);

        for (FriendRingSelectRowVo comment : list) {
            // 判断此评论当前用户是否已经点赞
            comment.setIsLike(friendRingCommentLike(comment.getCommentId(), userId));
        }
        return R.ok().put("page", new PageUtils(list, commentTotalCount, pageSize, currPage));
    }

    /**
     * 递归查询评论
     *
     * @param friendCommentList
     * @param list
     */
    private void findCommentByParent(List<FriendRingSelectRowVo> friendCommentList, List<FriendRingSelectRowVo> list) {
        if (friendCommentList == null || friendCommentList.size() < 1) {
            return;
        }

        for (int i = 0; i < friendCommentList.size(); i++) {
            FriendRingSelectRowVo comment = friendCommentList.get(i);

            list.add(comment);

            // 获取子评论信息
            List<FriendRingSelectRowVo> childerComment = friendRingCommentDao.findCommentByParent(comment.getCommentId());
            findCommentByParent(childerComment, list);

        }
    }

    /**
     * 添加朋友圈内容评论
     *
     * @param friendRingComment
     * @return
     */
    @Override
    public Object addFriendComment(FriendRingComment friendRingComment) {

        // 根据朋友圈id获取视频id, 判断是否同步到视频
        Integer videoId = friendRingDao.findVideoIdByFriendId(friendRingComment.getFriendId());
        if (videoId != null) {
            // 已经同步到我的视频, 向视频评论表中插入数据
            VideoComment videoComment = new VideoComment();
            videoComment.setVideoId(videoId);
            videoComment.setUserId(friendRingComment.getUserId());
            videoComment.setParentCommentId(friendRingCommentDao.findVideoCommentIdByFriendCommentId(friendRingComment.getParentCommentId()));
            videoComment.setComment(friendRingComment.getContent());
            videoComment.setCreateTime(new Date());
            videoCommentService.save(videoComment);
            friendRingComment.setVideoCommentId(videoComment.getCommentId());
        }

        // 添加朋友圈评论
        friendRingComment.setCreateTime(new Date());
        int i = friendRingCommentDao.insertSelective(friendRingComment);
        if (i > 0) {
            if (friendRingComment.getParentCommentId() == null || friendRingComment.getParentCommentId() == 0) {
                // 评论推送通知
                FriendCommentProduct.pushFriendComment(friendRingComment.getCommentId());
            } else {
                //评论回复通知
                FriendCommentProduct.pushFriendCommentReply(friendRingComment.getCommentId());
            }
        }
        return R.result(1, "评论成功", "");
    }

    /**
     * 根据评论id删除朋友圈评论内容
     *
     * @param commentId 评论id
     * @return
     */
    @Override
    public Object deleteFriendComment(Integer commentId) {
        // 根据朋友圈评论id获取视频评论id
        Integer videoCommentId = friendRingCommentDao.findVideoCommentIdByFriendCommentId(commentId);
        if (videoCommentId != null) {
            // 删除视频评论id
            videoCommentService.deleteCommentById(videoCommentId, 0);
        }

        // 删除当前评论, 如果该评论有子评论, 删除子评论
        deleteCommentByCommentId(commentId);
        return R.result(1, "删除成功", "");
    }

    /**
     * 朋友圈消息,用户点赞, 取消点赞
     *
     * @param friendId 朋友圈内容id
     * @param userId   点赞人id
     * @return
     */
    @Override
    public Object addOrDelFriendLike(Integer friendId, Integer userId) {

        // 判断是否同步到我的视频点赞
        Integer videoId = friendRingDao.findVideoIdByFriendId(friendId);

        // 判断是否点赞
        if (friendRingLike(friendId, userId) == 0) {
            // 没有点赞, 进行点赞
            // 保存点赞信息
            int i = friendRingLikeDao.insertSelective(friendId, userId, new Date(), videoId);

            if (videoId != null) {
                // 添加视频点赞
                videoInfoService.InsertCollection(videoId, userId, false);
            }

            if (i > 0) {
                // 将点赞信息添加到redis
                redisUtils.set(RedisKeys.FRIEND_RING_LIKE + friendId + "_" + userId, "1", RedisUtils.NOT_EXPIRE);
                // 修改点赞数量
                FriendLikeNumProduct.updateFriendLikeNum(friendId, 1);
                // 推送通知
                FriendLikePushProduct.pushFriendLike(friendId, userId);
            }
            return R.result(1, "点赞成功", "");
        } else {
            // 取消点赞
            int i = friendRingLikeDao.deleteByUserIdAndFriendId(friendId, userId);

            // 将点赞信息从redis中删除
            redisUtils.delete(RedisKeys.FRIEND_RING_LIKE + friendId + "_" + userId);

            if (videoId != null) {
                videoInfoService.updateCancel(videoId, userId, false);
            }

            // 修改点赞数量
            if (i > 0) {
                FriendLikeNumProduct.updateFriendLikeNum(friendId, -1);
            }
            return R.result(1, "取消点赞成功", "");
        }
    }

    /**
     * 朋友圈消息点赞+1
     *
     * @param friendId 朋友圈id
     * @param userId   用户id
     * @return
     */
    @Override
    public int addFriendLike(Integer friendId, Integer userId, Integer videoId) {

        // 添加点赞信息
        int i = friendRingLikeDao.insertSelective(friendId, userId, new Date(), videoId);

        // 将点赞信息添加到redis
        redisUtils.set(RedisKeys.FRIEND_RING_LIKE + friendId + "_" + userId, "1", RedisUtils.NOT_EXPIRE);

        if (i > 0) {
            // 修改点赞数量 +1
            FriendLikeNumProduct.updateFriendLikeNum(friendId, 1);
            // 推送通知
            FriendLikePushProduct.pushFriendLike(friendId, userId);
        }
        return 0;
    }

    /**
     * 朋友圈消息点赞-1
     *
     * @param friendId 朋友圈id
     * @param userId   用户id
     * @return
     */
    @Override
    public int delFriendLike(Integer friendId, Integer userId) {
        // 删除点赞
        int i = friendRingLikeDao.deleteByUserIdAndFriendId(friendId, userId);

        // 将点赞信息从redis中删除
        redisUtils.delete(RedisKeys.FRIEND_RING_LIKE + friendId + "_" + userId);

        // 修改点赞数量 -1
        if (i > 0) {
            FriendLikeNumProduct.updateFriendLikeNum(friendId, -1);
        }
        return 0;
    }

    /**
     * 朋友圈评论点赞+1
     *
     * @param commentId 朋友圈评论id
     * @param userId    用户id
     * @return
     */
    @Override
    public int addCommentLike(Integer commentId, Integer userId, Integer videoCommentId) {
        // 添加朋友圈评论点赞
        Map<String, Object> map = new HashMap<>(0);
        map.put("commentId", commentId);
        map.put("userId", userId);
        map.put("createTime", new Date());
        map.put("videoCommentId", videoCommentId);
        int i = friendRingCommentLikeDao.insertSelective(map);

        if (i > 0) {
            // 添加到redis中
            redisUtils.set(RedisKeys.FRIEND_RING_COMMENT_LIKE + commentId + "_" + userId, 1, RedisUtils.NOT_EXPIRE);
            // 修改点赞数量
            FriendCommentLikeNumProduct.updateFriendCommentLikeNum(commentId, 1);
        }
        return 0;
    }

    /**
     * 朋友圈评论点赞-1
     *
     * @param commentId 朋友圈评论id
     * @param userId    用户id
     * @return
     */
    @Override
    public int delCommentLike(Integer commentId, Integer userId) {
        int i = friendRingCommentLikeDao.deleteByUserIdAndCommentId(commentId, userId);
        // 从redis中删除
        redisUtils.delete(RedisKeys.FRIEND_RING_COMMENT_LIKE + commentId + "_" + userId);

        if (i > 0) {
            FriendCommentLikeNumProduct.updateFriendCommentLikeNum(commentId, -1);
        }
        return 0;
    }

    /**
     * 朋友圈消息内容评论点赞, 取消点赞
     *
     * @param search 点赞信息
     * @return
     */
    @Override
    public Object addOrDelCommentLike(Map<String, Object> search) {
        Integer commentId = Integer.parseInt(search.get("commentId").toString());
        Integer userId = Integer.parseInt(search.get("userId").toString());

        // 获取视频评论信息
        Integer videoCommentId = friendRingCommentDao.findVideoCommentIdByFriendCommentId(commentId);

        // 从redis中获取点赞信息
        if (friendRingCommentLike(commentId, userId) == 0) {
            // 没有点赞, 进行点赞
            search.put("createTime", new Date());
            search.put("videoCommentId", videoCommentId);
            int i = friendRingCommentLikeDao.insertSelective(search);

            // 给视频评论点赞
            if (videoCommentId != null) {
                videoCommentService.addCommentLike(commentId, userId);
            }

            // 修改点赞数量
            if (i > 0) {
                redisUtils.set(RedisKeys.FRIEND_RING_COMMENT_LIKE + commentId + "_" + userId, 1, RedisUtils.NOT_EXPIRE);
                FriendCommentLikeNumProduct.updateFriendCommentLikeNum(commentId, 1);
            }
            return R.result(1, "点赞成功", "");
        } else {
            // 取消点赞
            int i = friendRingCommentLikeDao.deleteByUserIdAndCommentId(commentId, userId);
            redisUtils.delete(RedisKeys.FRIEND_RING_COMMENT_LIKE + commentId + "_" + userId);

            // 视频评论点赞删除
            if (videoCommentId != null) {
                videoCommentService.delCommentLike(commentId, userId);
            }

            // 修改点赞数量
            if (i > 0) {
                FriendCommentLikeNumProduct.updateFriendCommentLikeNum(commentId, -1);
            }
            return R.result(1, "取消点赞成功", "");
        }
    }

    /**
     * 根据视频id查询朋友圈id
     *
     * @param videoId 视频id
     * @return
     */
    @Override
    public Integer findFriendIdByVideoId(Integer videoId) {
        return friendRingDao.findFriendIdByVideoId(videoId);
    }

    /**
     * 查看某个用户的朋友圈动态
     *
     * @param map 查询条件
     *            userId 当前用户
     *            account 被查看用户
     *            limit 每页显示信息数 默认10
     *            page 当前页 默认1
     * @return
     */
    @Override
    public Object findFriendRingByUserId(Map<String, Object> map) {
        if (map.get("page") == null) {
            map.put("page", 1);
            map.put("limit", 10);
        }

        Integer userId = Integer.parseInt(map.get("userId").toString());

        Query query = new Query(map);
        // 查询朋友圈动态列表
        List<Object> list = friendRingDao.findFriendRingByUserIdAndAccountId(query);
        for (Object obj : list) {
            Map<String, Object> friendRing = (Map<String, Object>) obj;
            Integer friendId = Integer.parseInt(friendRing.get("friendId").toString());

            // 获取当前动态评论总数量
            Integer commendCount = friendRingCommentDao.findCountByFriendId(friendId);
            friendRing.put("commendCount", commendCount);

            if (!friendRing.get("type").equals(3)) {
                // 如果该动态不是纯文字, 则查询文件表
                List<Object> files = friendRingFileDao.findFileByFriendId(friendId);
                friendRing.put("fileList", files);
            } else {
                friendRing.put("fileList", new ArrayList<>(0));
            }

            // 拼接@好友字符串
            if (friendRing.get("shield").equals(4)) {
                List<String> listStr = friendRingSetDao.findByFriendId(friendId);
                String resultString = list != null && listStr.size() > 0 ? listStr.toString() : "";
                friendRing.put("users", resultString);
            } else {
                friendRing.put("users", "");
            }

            // 判断该动态, 当前用户是否点赞
            friendRing.put("isLike", friendRingLike(friendId, userId));
        }
        // 查询朋友圈动态数量
        Integer total = friendRingDao.findFriendRingTotalByUserIdAndAccountId(query);
        return R.ok().put("查询成功", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * 查看自己发布的动态
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数 默认10
     *            page 当前页 默认1
     * @return
     */
    @Override
    public Object findOneselfFriendRing(Map<String, Object> map) {
        if (map.get("page") == null) {
            map.put("page", 1);
            map.put("limit", 10);
        }
        Query query = new Query(map);
        List<Object> list = friendRingDao.findOneselfFriendRing(query);
        for (Object obj : list) {
            Map<String, Object> friendRing = (Map<String, Object>) obj;

            Integer friendId = Integer.parseInt(friendRing.get("friendId").toString());

            // 获取当前动态评论总数量
            Integer commendCount = friendRingCommentDao.findCountByFriendId(friendId);
            friendRing.put("commendCount", commendCount);

            if (!friendRing.get("type").equals(3)) {
                // 如果该动态不是纯文字, 则查询文件表
                List<Object> files = friendRingFileDao.findFileByFriendId(friendId);
                friendRing.put("fileList", files);
            } else {
                friendRing.put("fileList", new ArrayList<>(0));
            }

            // 拼接@好友字符串
            if (friendRing.get("shield").equals(4)) {
                List<String> listStr = friendRingSetDao.findByFriendId(friendId);
                String resultString = list != null && listStr.size() > 0 ? listStr.toString() : "";
                friendRing.put("users", resultString);
            } else {
                friendRing.put("users", "");
            }

        }
        Integer total = friendRingDao.findOneselfFriendRingTotalCount(query);
        return R.ok().put("查询成功", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * 查看附近朋友圈动态, 查询三天内附近的动态
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     *            latitude 维度(当前用户所在位置)
     *            longitude 经度(当前用户所在位置)
     * @return
     */
    @Override
    public Object findNearbyFriendRing(Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> list = friendRingDao.findNearbyFriendRingList(query);

        Integer userId = Integer.parseInt(map.get("userId").toString());

        for (Object obj : list) {
            Map<String, Object> friendRing = (Map<String, Object>) obj;

            Integer friendId = Integer.parseInt(friendRing.get("friendId").toString());

            // 获取当前动态评论总数量
            Integer commendCount = friendRingCommentDao.findCountByFriendId(friendId);
            friendRing.put("commendCount", commendCount);

            if (!friendRing.get("type").equals(3)) {
                // 如果该动态不是纯文字, 则查询文件表
                List<Object> files = friendRingFileDao.findFileByFriendId(friendId);
                friendRing.put("fileList", files);
            } else {
                friendRing.put("fileList", new ArrayList<>(0));
            }

            // 拼接@好友字符串
            if (friendRing.get("shield").equals(4)) {
                List<String> listStr = friendRingSetDao.findByFriendId(friendId);
                String resultString = list != null && listStr.size() > 0 ? listStr.toString() : "";
                friendRing.put("users", resultString);
            } else {
                friendRing.put("users", "");
            }

            // 判断该动态, 当前用户是否点赞
            friendRing.put("isLike", friendRingLike(friendId, userId));

        }

        Integer total = friendRingDao.findNearbyFriendRingTotal(query);

        return R.ok("查询成功").put("result", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * 根据id删除当前发布的朋友圈消息
     *
     * @param friendId 朋友圈id
     * @return
     */
    @Override
    public Object deleteFriendRingById(Integer friendId) {
        // 删除评论
        deleteCommentByFriendId(friendId);
        // 删除提醒人
        friendRingSetDao.deleteByFriendId(friendId);
        // 删除朋友圈信息
        friendRingDao.deleteByPrimaryKey(friendId);
        // 删除视频
        Integer videoId = friendRingDao.findVideoIdByFriendId(friendId);
        if (videoId != null && videoId > 0) {
            videoInfoService.deleteByPrimaryKey(videoId);
        }
        return R.result(1, "删除成功", "");
    }

    /**
     * 刪除当前朋友圈信息下面所有的评论
     *
     * @param friendId 朋友圈id
     */
    private void deleteCommentByFriendId(Integer friendId) {
        // 查询所有的评论并删除
        List<Integer> commentIds = friendRingCommentDao.findAllCommentByFriendId(friendId);
        for (Integer commentId : commentIds) {
            deleteCommentByCommentId(commentId);
        }

        // 删除所有点赞
        friendRingLikeDao.deleteLikeByFriendId(friendId);
        // 删除redis信息
        redisUtils.delete(redisUtils.keys(RedisKeys.FRIEND_RING_LIKE + friendId + "_*"));
        // 删除所有图片
        friendRingFileDao.deleteFileByFriendId(friendId);
    }

    /**
     * 递归删除评论
     *
     * @param commentId
     */
    private void deleteCommentByCommentId(Integer commentId) {
        // 查询是否拥有子评论
        List<Integer> childComment = friendRingCommentDao.findByParentId(commentId);
        if (childComment != null && childComment.size() > 0) {
            for (Integer id : childComment) {
                deleteCommentByCommentId(id);
            }
        }

        // 删除评论点赞
        friendRingCommentLikeDao.deleteByCommentId(commentId);
        // 删除redis信息
        redisUtils.delete(redisUtils.keys(RedisKeys.FRIEND_RING_COMMENT_LIKE + commentId + "_*"));
        // 删除评论
        friendRingCommentDao.deleteByPrimaryKey(commentId);
    }

    /**
     * 判断当前用户是否对朋友圈点赞
     *
     * @param friendRingId 朋友圈id
     * @param userId       用户id
     * @return
     */
    private Integer friendRingLike(Integer friendRingId, Integer userId) {
        String like = redisUtils.get(RedisKeys.FRIEND_RING_LIKE + friendRingId + "_" + userId);
        if (StringUtils.isBlank(like)) {
            return friendRingLikeDao.findLikeByUserIdAndFriendId(friendRingId, userId);
        }
        return 1;
    }

    /**
     * 判断当前用户是否对朋友圈评论点赞
     *
     * @param commentId 朋友圈评论id
     * @param userId    用户id
     * @return
     */
    private Integer friendRingCommentLike(Integer commentId, Integer userId) {
        String like = redisUtils.get(RedisKeys.FRIEND_RING_COMMENT_LIKE + commentId + "_" + userId);
        if (StringUtils.isBlank(like)) {
            return friendRingCommentLikeDao.findLikeByUserIdAndCommentId(commentId, userId);
        }
        return 1;
    }
}
