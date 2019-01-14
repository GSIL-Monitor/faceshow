package com.faceshow.modules.friend.service;

import com.faceshow.modules.friend.entity.FriendSet;

import java.util.Map; /**
 * 好友设置操作Service
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/26 18:13
 * -------------------------------------------------------------- <br>
 */
public interface FriendSetService {

    /**
     * 进入好友设置页面
     *
     * @param userId    当前主用户id
     * @param accountId 被设置用户id
     * @return
     */
    Object openSet(Map<String, Object> map);

    /**
     * 修改好友设置
     *
     * @param friendSet 设置内容
     * @return
     */
    Object update(FriendSet friendSet);
}
