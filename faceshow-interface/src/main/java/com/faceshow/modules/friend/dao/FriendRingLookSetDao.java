package com.faceshow.modules.friend.dao;

import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 朋友圈动态设置操作Dao
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/8 20:28
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface FriendRingLookSetDao extends BaseDao<Object> {

    /**
     * 根据朋友圈id查询@好友
     *
     * @param friendId 朋友圈id
     * @return
     */
    List<String> findByFriendId(Integer friendId);

    /**
     * 根据朋友圈id删除提醒人或者屏蔽人
     *
     * @param friendId 朋友圈id
     * @return
     */
    int deleteByFriendId(Integer friendId);
}
