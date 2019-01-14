package com.faceshow.modules.IM.Service;

import com.faceshow.modules.IM.Bean.ImBlackListAdd;
import com.faceshow.modules.IM.Bean.ImFriendAdd;
import com.faceshow.modules.IM.Bean.ImFriendDelete;
import com.faceshow.modules.IM.Bean.ImPortraitSet;

import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation:  用户关注关系的维护模块
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.IM.Service<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/1/29 11:38
 * -------------------------------------------------------------- <br>
 */

public interface ImFriendService {
    Object insertAttention (String api, ImFriendAdd imFriendAdd);//用户关注
    Object deleteSingle (String api,ImFriendDelete imFriendDelete);//单方面取消关注
    Object black_list_add(String api,ImBlackListAdd imBlackListAdd);//拉黑
    Object black_list_delete(String api,ImBlackListAdd imBlackListAdd);//取消拉黑
    String editUser_info (String api,ImPortraitSet imPortraitSet);//批量更新用户表
    List<Map<String,Object>> myFriends(Map<String,Object> map);//<!--查看我的好友-->

}
