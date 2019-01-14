package com.faceshow.modules.friend.service.impl;

import com.faceshow.common.utils.R;
import com.faceshow.modules.friend.dao.FriendSetDao;
import com.faceshow.modules.friend.entity.FriendSet;
import com.faceshow.modules.friend.service.FriendSetService;
import com.faceshow.modules.user.dao.BackListDao;
import com.faceshow.modules.user.dao.UserAttentionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 好友设置操作Service实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/26 18:13
 * -------------------------------------------------------------- <br>
 */
@Service
public class FriendSetServiceImpl implements FriendSetService {

    @Autowired
    private FriendSetDao friendSetDao;

    @Autowired
    private BackListDao backListDao;

    @Autowired
    private UserAttentionDao userAttentionDao;

    /**
     * 进入好友设置页面
     *
     * @param userId    当前主用户id
     * @param accountId 被设置用户id
     * @return
     */
    @Override
    public Object openSet(Map<String, Object> map) {
        map.put("setTime", new Date());
        // 查询当前设置状态
        Map<String, Object> friendSet = friendSetDao.selectByUserIdAndAccountId(map);
        // 查询拉黑状态
        int i = backListDao.selectByUseridAccount(map);
        //查询两人是否是好友关系
        map.put("account", map.get("accountId"));
        Integer j = userAttentionDao.selectFriendByUserIdAndAccount(map);
        if (friendSet == null) {
            // 首次进入好友设置页面
            friendSetDao.save(map);
            friendSet = friendSetDao.selectByUserIdAndAccountId(map);
            friendSet.put("isBlack", i);
            friendSet.put("isFriend", j);
            return R.result(1, "", friendSet);
        }
        friendSet.put("isBlack", i);
        friendSet.put("isFriend", j);
        return R.result(1, "", friendSet);
    }

    /**
     * 修改好友设置
     *
     * @param friendSet 设置内容
     * @return
     */
    @Override
    public Object update(FriendSet friendSet) {
        friendSet.setSetTime(new Date());
        int update = friendSetDao.update(friendSet);
        if (update > 0) {
            // 修改设置成功
            return R.result(1, "修改成功", "");
        }
        return R.result(0, "修改失败", "");
    }
}
