package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.PersonalHome;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 6.2.1.10个人主页页面
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.user.dao<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/2/3 10:49 
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface PersonalHomeDao {
    PersonalHome findPersonalHome (Map<String,Object>map);//<!-- 6.2.1.10个人主页页面 -->
    List<Object> findContributionImg (Map<String,Object>map);//获取前三名的贡献榜
    List<Object> findVideoImg (Map<String,Object>map);//<!--是个主页的视频 还需要分页-->
    int findVideoImgTotal (Map<String,Object>map);//<!--是个主页的视频 还需要分页-->分页
    List<Object> findfriendImg (Map<String,Object>map);//<!--是个主页的朋友圈 还需要分页-->
    int findfriendImgTotal (Map<String,Object>map);//<!--是个主页的朋友圈 还需要分页-->

    List<Map<String,Object>> findVideoImgF (Map<String,Object>map);//<!--是个主页的视频 还需要分页 默认四条-->
    List<Map<String,Object>> findfriendImgF (Map<String,Object>map);//<!--是个主页的朋友圈 还需要分页默认四条-->
    List<Map<String,Object>> findfriendImgFScreen (Map<String,Object>map);//屏蔽了谁 就不能看朋友圈了
    List<Map<String,Object>> findfriendImgFAppoint (Map<String,Object>map);//指定谁看我的朋友圈
    List<Object> selectGroup (Map<String,Object>map);//<!--加入的群-->
    List<Object> userTags (Map<String,Object>map);//用户标签
    Map<String,Object> OrAttention (Map<String,Object>map);//是否关注 A和B
    Map<String,Object> BOrAttention (Map<String,Object>map);//是否关注 B和A
}
