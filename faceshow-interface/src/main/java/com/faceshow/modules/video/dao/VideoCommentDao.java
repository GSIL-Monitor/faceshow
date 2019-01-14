package com.faceshow.modules.video.dao;

import com.faceshow.common.utils.NumUtils;
import com.faceshow.modules.video.entity.VideoComment;
import com.faceshow.modules.video.vo.VideoCommentSelectRowVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoCommentDao {
    int deleteByPrimaryKey(Integer commentId);

    int insertSelective(VideoComment record);

    VideoComment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(VideoComment record);

    /**
     * 分页查询当前短视频的评论信息
     *
     * @param search 查询条件
     * @return
     */
    List<VideoCommentSelectRowVo> findVideoCommentByVideoId(Map<String, Object> search);

    /**
     * 查询该视频评论总数
     *
     * @param videoId
     * @return
     */
    Integer findCommentTotalCount(Integer videoId);

    /**
     * 根据父评论查询子评论内容
     *
     * @param parentCommentId 父评论id
     * @return
     */
    List<VideoCommentSelectRowVo> findCommentByParent(Integer parentCommentId);

    /**
     * 根据父评论id查询子评论id
     *
     * @param parentCommentId 父评论id
     * @return 子评论id集合
     */
    List<Integer> findcommentIdByParentId(Integer parentCommentId);

    /**
     * 根据评论id查询视频id
     *
     * @param videoCommentId
     * @return
     */
    Integer findVideoIdByCommentId(Integer videoCommentId);

    /**
     * 修改评论点赞数量, 数量+1或者-1
     *
     * @param num id num
     * @return
     */
    int updateCommentLikeNum(NumUtils num);

    /**
     * @author: YS
     * @Date:2018/4/25 15:20
     * @param:
     * @explain：     <!--根据评论查看userId-->
     * @return:
     */
    int selectUserIdByCommentId(Integer comment_id);
}