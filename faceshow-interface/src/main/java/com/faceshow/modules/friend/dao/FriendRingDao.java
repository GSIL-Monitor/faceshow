package com.faceshow.modules.friend.dao;

import com.faceshow.common.utils.NumUtils;
import com.faceshow.common.utils.Query;
import com.faceshow.modules.friend.entity.FriendRing;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 朋友圈操作Dao接口
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/1/26 14:53
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface FriendRingDao {
    int deleteByPrimaryKey(Integer friendId);

    /**
     * 保存发布的消息
     *
     * @param record
     * @return
     */
    int insertSelective(FriendRing record);

    FriendRing selectByPrimaryKey(Integer friendId);

    int updateByPrimaryKeySelective(FriendRing record);

    /**
     * 分页查询朋友圈内容
     *
     * @param search 查询条件
     * @return
     */
    List<Map<String, Object>> findFriendRing(Map<String, Object> search);

    /**
     * 查询朋友圈内容详情
     *
     * @param friendId 内容id
     * @return
     */
    Map<String, Object> findFriendRingByFriengId(Integer friendId);

    /**
     * 修改点赞数量
     *
     * @param numUtils 点赞数量
     * @return
     */
    Integer updateLikeNum(NumUtils numUtils);

    /**
     * 获取朋友圈总消息数量
     *
     * @param userId
     * @return
     */
    Integer findFriendRingTotalCount(Integer userId);

    /**
     * 根据朋友圈id查询视频id
     *
     * @param friendId 朋友圈id
     * @return
     */
    Integer findVideoIdByFriendId(Integer friendId);

    /**
     * 根据视频id查询朋友圈id
     *
     * @param videoId 视频id
     * @return
     */
    Integer findFriendIdByVideoId(Integer videoId);

    /**
     * 根据当前用户查看指定用户的动态列表
     *
     * @param query 查询条件
     * @return
     */
    List<Object> findFriendRingByUserIdAndAccountId(Query query);

    /**
     * 根据当前用户查询指定用户的动态列表数量
     *
     * @param query 查询条件
     * @return
     */
    Integer findFriendRingTotalByUserIdAndAccountId(Query query);

    /**
     * 查询自己发布的朋友圈动态列表
     *
     * @param query
     * @return
     */
    List<Object> findOneselfFriendRing(Query query);

    /**
     * 查询自己发布的朋友圈动态数量
     *
     * @param query
     * @return
     */
    Integer findOneselfFriendRingTotalCount(Query query);

    /**
     * 查询附近发布的动态列表, 三天内, 100公里内
     *
     * @param query 查询条件
     * @return
     */
    List<Object> findNearbyFriendRingList(Query query);

    /**
     * 查询附近发布的动态数量, 三天内, 100公里内
     *
     * @param query
     * @return
     */
    Integer findNearbyFriendRingTotal(Query query);

    /**
     * 根据用户id查询四张用户朋友圈图片, 附近的人做展示使用
     *
     * @param userId 用户id
     * @return
     */
    List<String> findImgByUserId(Integer userId);

    /**
     * @author: YS
     * @Date:2018/4/25 15:27
     * @param:
     * @explain： <!--根据主键查询userId-->
     * @return:
     */
    int selectUserIdByFriendId(Integer friend_id );
}