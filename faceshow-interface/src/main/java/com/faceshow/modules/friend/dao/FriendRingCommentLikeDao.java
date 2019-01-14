package com.faceshow.modules.friend.dao;

import com.faceshow.modules.friend.entity.FriendRingCommentLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FriendRingCommentLikeDao {
    int deleteByPrimaryKey(Integer likeId);

    int insertSelective(Map<String, Object> map);

    FriendRingCommentLike selectByPrimaryKey(Integer likeId);

    int updateByPrimaryKeySelective(FriendRingCommentLike record);

    /**
     * 删除点赞信息
     *
     * @param search
     * @return
     */
    int deleteByUserIdAndCommentId(@Param("commentId") Integer commentId, @Param("userId") Integer userId);

    /**
     * 根据commentid 查询所有评论点赞信息
     *
     * @param commentId
     * @return
     */
    List<FriendRingCommentLike> findByCommentId(Integer commentId);

    /**
     * 根据评论id删除点赞
     *
     * @param commentId
     * @return
     */
    int deleteByCommentId(Integer commentId);

    /**
     * 根据userId和commentId判断是否已经点赞
     *
     * @param commentId 评论id
     * @param userId 用户id
     * @return
     */
    Integer findLikeByUserIdAndCommentId(@Param("commentId") Integer commentId, @Param("userId") Integer userId);

}