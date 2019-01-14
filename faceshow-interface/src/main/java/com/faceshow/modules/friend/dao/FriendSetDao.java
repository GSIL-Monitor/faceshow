package com.faceshow.modules.friend.dao;

import com.faceshow.modules.friend.entity.FriendSet;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 好友设置操作Dao
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/26 18:11
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface FriendSetDao extends BaseDao<FriendSet> {

    /**
     * 查询设置状态
     *
     * @param map
     * @return
     */
    Map<String,Object> selectByUserIdAndAccountId(Map<String, Object> map);
}
