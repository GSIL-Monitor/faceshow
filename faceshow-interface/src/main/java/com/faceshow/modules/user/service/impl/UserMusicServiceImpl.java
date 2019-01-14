package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.dao.UserMusicDao;
import com.faceshow.modules.user.service.UserMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 用户音乐表 操作音乐类型
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.user.service<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/2/3 16:30
 * -------------------------------------------------------------- <br>
 */
@Service
public class UserMusicServiceImpl implements UserMusicService {
    @Autowired
    private UserMusicDao userMusicDao;
    /**
     *@Author:YS
     *@Description: 音乐类别 查找所有
     *@Date:2018/2/3
     */
    @Override
    public List<Object> typeMusic(Map<String, Object> map) {
        return userMusicDao.typeMusic(map);
    }
    /**
     *@Author:YS
     *@Description: 音乐类别 查找所有个数
     *@Date:2018/2/3
     */
    @Override
    public int typeMusicTotal(Map<String, Object> map) {
        return userMusicDao.typeMusicTotal(map);
    }
    // <!--模糊搜索音乐表带分页-->
    @Override
    public List<Object> getMusic(Map<String, Object> map) {
        return userMusicDao.getMusic(map);
    }
    // <!--模糊搜索音乐表带分页-->
    @Override
    public int getMusicTotal(Map<String, Object> map) {
        return userMusicDao.getMusicTotal(map);
    }
}
