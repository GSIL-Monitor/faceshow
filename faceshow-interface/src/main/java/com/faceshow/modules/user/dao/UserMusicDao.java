package com.faceshow.modules.user.dao;

import org.apache.ibatis.annotations.Mapper;
import sun.security.krb5.internal.tools.Klist;

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
@Mapper
public interface UserMusicDao {
    List<Object> typeMusic  (Map<String,Object> map);//所有的音乐类别
    int typeMusicTotal (Map<String,Object> map);//所有的音乐类别分页！
    List<Object>getMusic (Map<String,Object> map);// <!--模糊搜索音乐表带分页-->
    int getMusicTotal (Map<String,Object> map);// <!--模糊搜索音乐表带分页-->
}
