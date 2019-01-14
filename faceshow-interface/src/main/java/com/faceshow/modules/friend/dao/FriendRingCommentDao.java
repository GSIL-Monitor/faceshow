package com.faceshow.modules.friend.dao;

import com.faceshow.common.utils.NumUtils;
import com.faceshow.modules.friend.entity.FriendRingComment;
import com.faceshow.modules.friend.vo.FriendRingSelectRowVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FriendRingCommentDao {
    int deleteByPrimaryKey(Integer commentId);

    int insertSelective(FriendRingComment record);

    FriendRingComment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(FriendRingComment record);

    /**
     * 根据朋友圈内容id分页查询当前内容评论
     *
     * @param search 查询条件
     * @return
     */
    List<FriendRingSelectRowVo> findCommentByFriendId(Map<String, Object> search);

    /**
     * 查询当前父评论下所有的子评论
     *
     * @param parentCommentId 父评论id
     * @return
     */
    List<Integer> findByParentId(Integer parentCommentId);

    /**
     * 查询当前朋友圈下所有的评论id
     *
     * @param friendId 朋友圈id
     * @return
     */
    List<Integer> findAllCommentByFriendId(Integer friendId);

    /**
     * 获取评论总数
     *
     * @param friendId
     * @return
     */
    Integer findCommentTotalCountByFriendId(Integer friendId);

    /**
     * 根据父评论查询子评论id
     *
     * @param parentCommentId 父评论id
     * @return
     */
    List<FriendRingSelectRowVo> findCommentByParent(Integer parentCommentId);

    /**
     * 查询当前动态的评论总数量(一级评论及子评论)
     *
     * @param friendId 动态id
     * @return
     */
    Integer findCountByFriendId(Integer friendId);

    /**
     * 根据朋友圈评论id查询视频评论id
     *
     * @param friendCommentId 朋友圈评论id
     * @return
     */
    Integer findVideoCommentIdByFriendCommentId(Integer friendCommentId);

    /**
     * 根据视频评论id查询朋友圈评论id
     *
     * @param videoCommentId 视频评论id
     * @return
     */
    Integer findFriendCommentIdByVideoCommentId(Integer videoCommentId);

    /**
     * 朋友圈评论点赞数量修改
     *
     * @param numUtils
     * @return
     */
    int updateCommentLikeNum(NumUtils numUtils);

    int  selectUserIdByCommentId(Integer comment_id);

}