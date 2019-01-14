package com.faceshow.modules.IM.Service;

import com.faceshow.modules.IM.Bean.Callback;
import com.faceshow.modules.IM.Bean.ForbidSend;
import com.faceshow.modules.IM.Bean.ImBean;
import com.faceshow.modules.IM.Bean.ImDelGropBean;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ImGroupService {
    /**
     * @Author:YS
     * @Description: 新增群组
     * @Date:2018/1/25
     */

    Object insertSelective( String api,ImBean map);
    /**
     * @Author:YS
     * @Description: 修改群资料
     * @Date:2018/1/26
     */
    Object updateSelective(String api, Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 群拉人进群
     * @Date:2018/1/26
     */
    Object insertGroup(String api, ImBean imBean);

    /**
     * @Author:YS
     * @Description: 踢出群
     * @Date:2018/1/26
     */
    Object deleteGroupMember(String api, ImDelGropBean imDelGropBean);

    /**
     * @Author:YS
     * @Description: 修改群成员成为管理员 或者取消管理员
     * @Date:2018/1/26
     */
    Object modifyGroupMemberInfo(String api, Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 解散群
     * @Date:2018/1/26
     */
    Object destroyGroup(String api, Map<String, Object> map);//解散群

    /**
     * 查询所有群类型
     *
     * @return
     */
    Object findGroupTypeAll();
    /**
     *@Author:YS
     *@Description:查找这个人加入的所有群
     *@Date:2018/2/7
     */

    List<Object> selectAllGroup(int user_id);//查找某人加入的所有群
   /**
    *@Author:YS
    *@Description: <!--80后 90后标签分页查询-->
    *@Date:2018/2/8
    */

    List<Object> findType(Map<String,Object> map);//<!--80后 90后标签分页查询-->
    int findTypeTotal(Map<String,Object> map);//<!--80后 90后标签分页查询-->


    /**
     *@Author:YS
     *@Description:// <!--群里面有哪些人--> 有多少个人
     *@Date:2018/2/8
     */
    List<Object> belongGroup(Map<String,Object> map);
    /**
     *@Author:YS
     *@Description: 查询这个群的类型
     *@Date:2018/2/8
     */
    Map GroupType(Map<String,Object> map);//根据IMID查找类型

    /**
     *@Author:YS
     *@Description:查询黑名单列表
     *@Date:2018/2/9
     */
    List<Object> findBlacklist(Map<String,Object> map);//查询黑名单列表
    /**
     *@Author:YS
     *@Description:<!--查看这个人在群里面是什么身份-->
     *@Date:2018/2/28
     *@param:userId IM_ID
     */

    Map<String,Object> isRole(Map<String,Object> map);//  <!--查看这个人在群里面是什么身份-->

    /**
     * @Author:YS
     * @Description: 修改群类型
     * @Date:2018/1/26
     */
    int updateSelectiveType( Map<String, Object> map);

    int delGroup(Map<String,Object> map);//退出群

   void findById(Callback callback, Map map);//根据IMID查找自己数据库的主键

    /**
     * @Author:YS
     * @Description: 用户自己主动加群
     * @Date:2018/1/26
     */
    int insertGroup(Map map);

    Map<String,Object> selectImg(String userId);//查询昵称和图片

    /**
     *@Author:YS
     *@Description: 禁言
     *@Date:2018/3/17
     *@param:
     */
    String insetInfo_ban(Map<String,Object>map, String api,ForbidSend forbidSend);
    List< Map<String,Object>> securityManagement();//安全管理表 這個群不能发什么信息 可以发什么信息
    int setUp(Map<String,Object> map);//设置群安全管理
}
