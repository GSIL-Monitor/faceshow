package com.faceshow.modules.IM.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation:  这个主要是关系链管理的 主要是添加好友 删除好友 等等
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.IM.dao<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/1/26 16:47
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface ImFriendDao {
    int insertAttention (Map<String,Object> map);//用户关注
    Map findById (Map<String,Object> map);//查找一下是否相互关注了
    int editAttention (Map<String,Object> map);//批量更新
    Map findByAllId (Map<String,Object> map);//查看是否是相互关注
    int deleteSingle (Map<String,Object> map);//单方面取消关注
    int editUser_info (Map<String,Object> map);//批量更新用户表

    int AddAttention (Map<String,Object> map);//添加关注后 个人表的关注数量加一
    int AddFensi (Map<String,Object> map);//有粉丝了 则个人表的粉丝加一
    int reduceAttention (Map<String,Object> map);//添加关注后 个人表的关注数量加一
    int reduceFensi (Map<String,Object> map);//有粉丝了 则个人表的粉丝加一

    List<Map<String,Object>>myFriends(Map<String,Object> map);//<!--查看我的好友-->
}
