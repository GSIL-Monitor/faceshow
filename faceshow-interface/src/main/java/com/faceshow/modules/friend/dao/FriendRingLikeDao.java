package com.faceshow.modules.friend.dao;

import com.faceshow.modules.friend.entity.FriendRingLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface FriendRingLikeDao {
    int deleteByPrimaryKey(Integer likeId);

    int insertSelective(@Param("friendId") Integer friendId, @Param("userId") Integer userId, @Param("createTime") Date date, @Param("videoId") Integer videoId);

    FriendRingLike selectByPrimaryKey(Integer likeId);

    int updateByPrimaryKeySelective(FriendRingLike record);

    /**
     * 查询当前朋友圈内容所有点赞
     *
     * @param friendId 当前朋友圈id
     * @return
     */
    List<Object> findLikeByFriendId(Integer friendId);

    /**
     * 查看是否已经点赞
     *
     * @param friendId 朋友圈id
     * @param userId   用户id
     * @return
     */
    Integer findLikeByUserIdAndFriendId(@Param("friendId") Integer friendId, @Param("userId") Integer userId);

    /**
     * 取消点赞
     *
     * @param friendId 朋友圈id
     * @param userId   用户id
     * @return
     */
    Integer deleteByUserIdAndFriendId(@Param("friendId") Integer friendId, @Param("userId") Integer userId);

    /**
     * 根据朋友圈信息id删除点赞信息
     *
     * @param friendId 朋友圈id
     * @return
     */
    Integer deleteLikeByFriendId(Integer friendId);
}