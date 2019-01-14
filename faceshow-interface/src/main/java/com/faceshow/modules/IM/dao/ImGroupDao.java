package com.faceshow.modules.IM.dao;

import com.faceshow.modules.IM.Bean.ForbidSend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation:  TX的IM接口   对应的XML文档 里面标注很清楚 因为有的方法不针对业务 请查看XML具体业务
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.IM.dao<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/1/26 14:02
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface ImGroupDao {
    int insertSelective(Map map);//批量插入

    Map findByName(Map map);//根据名字查找

    int insertGroup(Map map);//插入群

    int updateSelective(Map map);//批量更新

    Map<String, Object> findById(Map map);//根据IMID查找自己数据库的主键

    int deleteGroupMember(Map map);//删除群成员

    int modifyGroupMemberInfo(Map map);//修改群组的信息

    int destroyGroup(Map map);//解散群
    int selectMax();//查询最群的ID的最大值
    List<Object> findGroupTypeAll();// 查询所有群类型名称

    List<Object> selectAllGroup(int user_id);//查找某人加入的所有群
    List<Object> selectAllGroupAdd(int user_id);//<!--用户创建了那个群-->

    List<Object> findType(Map<String,Object> map);//<!--80后 90后标签分页查询-->
    int findTypeTotal(Map<String,Object> map);//<!--80后 90后标签分页查询-->

    List<Object> belongGroup(Map<String,Object> map);// <!--群里面有哪些人-->

    Map GroupType(Map<String,Object> map);//根据IMID查找类型

    List<Object> findBlacklist(Map<String,Object> map);//查询黑名单列表

    Map WhetherBelong(Map<String,Object> map);// <!--用户是否属于这个组-->
    Map<String,Object> isRole(Map<String,Object> map);//  <!--查看这个人在群里面是什么身份-->
    int delGroup(Map<String,Object> map);//退出群

    Map<String,Object> selectImg(String userId);//查询昵称和图片

    /**
     *@Author:YS
     *@Description: 禁言
     *@Date:2018/3/17
     *@param:
     */
    int insetInfo_ban(Map<String,Object> map);
    List< Map<String,Object>> securityManagement();//安全管理表 這個群不能发什么信息 可以发什么信息
    int setUp(Map<String,Object> map);//设置群安全管理
    List<Map<String,Object>> selectGroupOrderByActivity(Map<String,Object> map);//   <!--發現推荐，按照活跃度排行-->
    int delBan (Map<String,Object> map);//取消禁言

}
