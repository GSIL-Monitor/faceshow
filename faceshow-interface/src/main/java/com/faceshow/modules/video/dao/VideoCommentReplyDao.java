package com.faceshow.modules.video.dao;

import com.faceshow.modules.video.entity.VideoCommentReply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoCommentReplyDao {
    int deleteByPrimaryKey(Integer replyId);

    int insertSelective(VideoCommentReply record);

    VideoCommentReply selectByPrimaryKey(Integer replyId);

    int updateByPrimaryKeySelective(VideoCommentReply record);

    /**
     * 根据视频评论id查询该评论的回复信息
     *
     * @param videoId
     * @return
     */
    List<Object> findVideoCommentReplyByVideoId(Integer videoId);
}