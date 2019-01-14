package com.faceshow.modules.IM.Service.impl;
/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation:  这个类主要是群组的管理
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.IM.Service.impl<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/1/26 16:50
 * -------------------------------------------------------------- <br>
 */

import com.faceshow.common.exception.RRException;
import com.faceshow.common.utils.HttpClientUtil;
import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.R;
import com.faceshow.modules.IM.Bean.Callback;
import com.faceshow.modules.IM.Bean.ForbidSend;
import com.faceshow.modules.IM.Bean.ImBean;
import com.faceshow.modules.IM.Bean.ImDelGropBean;
import com.faceshow.modules.IM.Service.ImGroupService;
import com.faceshow.modules.IM.dao.ImGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
@Transactional
public class ImGroupServiceImpl implements ImGroupService {
    @Autowired
    private ImGroupDao imGroupDao;

    /**
     * @Author:YS
     * @Description: 新增群组
     * @Date:2018/1/25
     */
    @Override
    public Object insertSelective( String api,ImBean imBean) {
        Map<String,Object>map=new HashMap<String,Object>();
        int o=imGroupDao.selectMax();//群
        imBean.setGroupId(o+1+"");
        //map.put("type_name",imBean.getType());
       // Map byName = imGroupDao.findByName(map);//查询出来群类型
        map.put("type_id",imBean.getType());//插入查出来的群类型ID
        map.put("IM_ID",o+1);//IM_ID
        map.put("group_name",imBean.getName());//群名字
        map.put("user_id",imBean.getOwner_Account());//群主 也就是创建人
        map.put("img",imBean.getFaceUrl());//群头像
        map.put("introduction",imBean.getIntroduction());//群介绍
        map.put("cements",imBean.getNotification());//群公告
        map.put("create_time",new Date());//创建时间
        imGroupDao.insertSelective(map);//插入用户聊天分组表
        //  int id = (int)map.get("newsletter_id");
        map.put("newsletter_id", map.get("newsletter_id"));//分组ID主键
        map.put("is_role", 2);//加入人的身份
        imGroupDao.insertGroup(map);//管理员也要要加入群分类user_belong_group表 2代表群主 1代表管理员
        map.put("is_role",0);//加入人的身份 恢复成员身份
        if (imBean.getMemberList() != null && !imBean.getMemberList().isEmpty()) {
            for (int i = 0; i < imBean.getMemberList().size(); i++) {
              //  System.out.println(imBean.getMemberList().get(i).getMember_Account());
                map.put("user_id", imBean.getMemberList().get(i).getMember_Account());//加入人
               /* if (imBean.getMemberList().get(i).getRole().equalsIgnoreCase("Admin")) {
                    map.put("is_role", 1);//加入人的身份
                }*/
                imGroupDao.insertGroup(map);
            }
        }
        //map.put("user_id",imBean.getMemberList().get(0).getMember_Account());//加入人
        //imGroupDao.insertGroup(map);
       // System.out.println(JsonUtils.objectToJson(imBean));
        imBean.setType("Public");
        String date= HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imBean));//发送请求
        Map json = JsonUtils.jsonToPojo(date, map.getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
            throw new RRException(date);
        }
        return date;
    }

    /**
     * @Author:YS
     * @Description: 修改群资料
     * @Date:2018/1/26
     */
    /*{
    "GroupId": "@TGS#1NVTZEAE4",  // 要修改哪个群的基础资料（必填）
    "Name": "NewName",  // 群名称（选填）
    "Introduction": "NewIntroduction",  //  群简介（选填）
    "Notification": "NewNotification",    // 群公告（选填）
    "FaceUrl": "http://this.is.new.face.url",  // 群头像（选填）
    "MaxMemberNum": 500,  // 最大群成员数量（选填）
    "ApplyJoinOption": "NeedPermission",  // 申请加群方式（选填）  不需要
    "ShutUpAllMember": "On" //设置全员禁言（选填）:"On"开启，"Off"关闭
}*/
    @Override
    public Object updateSelective(String api, Map<String, Object> map) {
        String date = "";
        map.put("IM_ID", map.get("GroupId")); // 要修改哪个群的基础资料（必填）
        map.put("group_name", map.get("Name")); // 群名称（选填）
        map.put("introduction", map.get("Introduction")); // 群简介（选填）
        map.put("cements", map.get("Notification")); // 群公告（选填）
        map.put("img", map.get("FaceUrl")); // 群头像（选填）
        map.put("editor_time", new Date()); // 更新时间
        imGroupDao.updateSelective(map);//保存到数据库
        date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));//发送请求
        Map json = JsonUtils.jsonToPojo(date, map.getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
            throw new RRException(date);//发送请求失败就扔出异常 事务不会提交
        }
        return date;
    }

    /**
     * @Author:YS
     * @Description: 拉人入群
     * @Date:2018/1/26
     */
    @Override
    public Object insertGroup(String api, ImBean imBean) {
        Map<String, Object> map = new HashMap();
        //公网打开 的时候注释开始标志
        map.put("GroupId", imBean.getGroupId());//Member_Account
        Map newsletter_id = imGroupDao.findById(map);//获取主键
        map.put("newsletter_id", newsletter_id.get("newsletter_id"));//主键
        map.put("create_time", new Date());//create_time
        if (imBean.getMemberList() != null && !imBean.getMemberList().isEmpty()) {
            for (int i = 0; i < imBean.getMemberList().size(); i++) {
                map.put("user_id", imBean.getMemberList().get(i).getMember_Account());//加入人
                Map whetherBelong = imGroupDao.WhetherBelong(map);//我看看你拉的人在群里面不在？
                if (ObjectUtils.isEmpty(whetherBelong)){//为NULL 说明这个人不在群里面
                    imGroupDao.insertGroup(map);
                }
            }
        }
        //公网打开 的时候注释结束标志
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imBean));//发送请求
        Map json = JsonUtils.jsonToPojo(date, map.getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
            throw new RRException(date);//发送请求失败就扔出异常 事务不会提交
        }
        return date;
    }

    /**
     * @Author:YS
     * @Description: 踢出群
     * @Date:2018/1/26
     */
    @Override
    public Object deleteGroupMember(String api, ImDelGropBean imDelGropBean) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("GroupId", imDelGropBean.getGroupId());
        Map newsletter_id = imGroupDao.findById(map);//根据IM的主键查找自己数据库的主键
        map.put("newsletter_id", newsletter_id.get("newsletter_id"));//根据这个外键以及用户ID删除
        imDelGropBean.getMemberToDel_Account();
        //如果传递的删除用户的个数不是空
        if (imDelGropBean.getMemberToDel_Account() != null || (imDelGropBean.getMemberToDel_Account() == null && imDelGropBean.getMemberToDel_Account().length != 0)) {
            for (int i = 0; i < imDelGropBean.getMemberToDel_Account().length; i++) {
                map.put("user_id", imDelGropBean.getMemberToDel_Account()[i]);
                //  System.out.println(imDelGropBean.getMemberToDel_Account()[i]);
                imGroupDao.deleteGroupMember(map);//循环删除组成员
            }
        }
        //  System.out.println(JsonUtils.objectToJson(imDelGropBean));
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imDelGropBean));//发送请求
        Map json = JsonUtils.jsonToPojo(date, map.getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
            throw new RRException(date);//发送请求失败就扔出异常 事务不会提交
        }
        return date;
    }

    /**
     * @Author:YS
     * @Description: 修改群成为管理员 取消管理员
     * @Date:2018/1/26
     */
    @Override
    public Object modifyGroupMemberInfo(String api, Map<String, Object> map) {
        Map newsletter_id = imGroupDao.findById(map);//根据IM_ID查找自己数据库的主键
        map.put("newsletter_id", newsletter_id.get("newsletter_id"));//外键 分组表
        map.put("user_id", map.get("Member_Account"));//根据外键和用户确定唯一记录 更新是否成为管理员
        if (map.get("Role").toString().equalsIgnoreCase("Admin")) {
            map.put("is_role", "1");//admin 设置成为管理员  map.put("is_role","0");//admin 设置成为管理员
        } else if ((map.get("Role").toString().equalsIgnoreCase("Member"))) {
            map.put("is_role", "0");//Member 取消管理员
        }
        imGroupDao.modifyGroupMemberInfo(map);
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));//发送请求
        Map json = JsonUtils.jsonToPojo(date, map.getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
            throw new RRException(date);//发送请求失败就扔出异常 事务不会提交
        }
        return date;

    }

    /**
     * @Author:YS
     * @Description: 解散群
     * @Date:2018/1/26
     */

    @Override
    public Object destroyGroup(String api, Map<String, Object> map) {

        Map newsletter_id = imGroupDao.findById(map);//查出来数据库的主键
        map.put("newsletter_id", newsletter_id.get("newsletter_id"));//
        imGroupDao.deleteGroupMember(map);//先删除加入这个群里面的人 操作user_belong_group
        imGroupDao.destroyGroup(map);//解散这个群 操作newsletter_group
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));//发送请求
        Map json = JsonUtils.jsonToPojo(date, map.getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
            throw new RRException(date);//发送请求失败就扔出异常 事务不会提交
        }
        return date;
    }

    /**
     * 查询所有群类型
     *
     * @return
     */
    @Override
    public Object findGroupTypeAll() {
        List<Object> typeAll = imGroupDao.findGroupTypeAll();
        return R.result(1, "OK", typeAll);
    }
    /**
     *@Author:YS
     *@Description:查找这个人加入的所有群
     *@Date:2018/2/7
     */
    @Override
    public  List<Object> selectAllGroup(int user_id) {

        List<Object> objects = imGroupDao.selectAllGroup(user_id);
      /*  List<Object> list = imGroupDao.selectAllGroup(user_id);
        for (int i = 0; i < list.size(); i++) {
            objects.add(list.get(i));
        }*/
        return objects;
    }
    /**
     *@Author:YS
     *@Description: <!--80后 90后标签分页查询-->
     *@Date:2018/2/8
     */

    @Override
    public List<Object> findType(Map<String, Object> map) {
        return imGroupDao.findType(map);
    }
    /**
     *@Author:YS
     *@Description: <!--80后 90后标签分页查询-->
     *@Date:2018/2/8
     */
    @Override
    public int findTypeTotal(Map<String, Object> map) {
        return imGroupDao.findTypeTotal(map);
    }
    /**
     *@Author:YS
     *@Description:// <!--群里面有哪些人--> 有多少个人
     *@Date:2018/2/8
     */
    @Override
    public List<Object> belongGroup(Map<String, Object> map) {
        List<Object> belongGroup = imGroupDao.belongGroup(map);//<!--群里面有哪些人-->
       // int totalGroup = imGroupDao.TotalGroup(map);//有多少个人
      //  belongGroup.add(totalGroup);
        return belongGroup;
    }
   /**
    *@Author:YS
    *@Description: 查找群的类型名字
    *@Date:2018/2/8
    */
    @Override
    public Map GroupType(Map<String, Object> map) {
        return imGroupDao.GroupType(map);
    }
    /**
     *@Author:YS
     *@Description: 查询黑名单列表
     *@Date:2018/2/9
     */
    @Override
    public List<Object> findBlacklist(Map<String, Object> map) {
        return imGroupDao.findBlacklist(map);
    }
    /**
     *@Author:YS
     *@Description:<!--查看这个人在群里面是什么身份-->
     *@Date:2018/2/28
     *@param:userId IM_ID
     */
    @Override
    public Map<String, Object> isRole(Map<String, Object> map) {
        Map<String, Object> role = imGroupDao.isRole(map);
        if (ObjectUtils.isEmpty(role)){
            Map<String, Object> ISrole = new HashMap<String, Object>();
            ISrole.put("is_role",3);
            return ISrole;
        }
        return role;
    }
    /**
     *@Author:YS
     *@Description: 修改群类型
     *@Date:2018/3/1
     *@param:type_id 类型ID    IM_ID群ID
     */

    @Override
    public int updateSelectiveType(Map<String, Object> map) {
        return imGroupDao.updateSelective(map);
    }

    @Override
    public int delGroup(Map<String, Object> map) {
        return imGroupDao.delGroup(map);
    }

    @Override
    public void findById(Callback callback ,Map map) {
        Map<String, Object> newsletter_id =  imGroupDao.findById(map);
        map.put("newsletter_id",newsletter_id.get("newsletter_id"));//主键相当于IMID
        map.put("create_time",new Date());//时间
        if (callback.getNewMemberList() != null && !callback.getNewMemberList().isEmpty()) {
            for (int i = 0; i < callback.getNewMemberList().size(); i++) {
                map.put("user_id", callback.getNewMemberList().get(i).getMember_Account());//加入人
                Map whetherBelong = imGroupDao.WhetherBelong(map);//我看看你拉的人在群里面不在？
                if (ObjectUtils.isEmpty(whetherBelong)){//为NULL 说明这个人不在群里面
                    imGroupDao.insertGroup(map);
                }
            }
        }
    }

    @Override
    public int insertGroup(Map map) {//用户自己主动加群
        return imGroupDao.insertGroup(map);
    }
    //查询昵称和图片
    @Override
    public Map<String, Object> selectImg(String userId) {
        return imGroupDao.selectImg(userId);
    }
    /**
     *@Author:YS
     *@Description:     禁言
     *@Date:2018/3/17
     *@param:
     */
    @Override
    public String insetInfo_ban( Map<String,Object>map,String api,ForbidSend forbidSend) {
        String[] members_account = forbidSend.getMembers_Account();
        int shutUpTime = forbidSend.getShutUpTime();
        for (int i = 0; i < members_account.length; i++) {
            map.put("user_id",members_account[i]);
            if (shutUpTime!=0){
                imGroupDao.insetInfo_ban(map);
            }else if (shutUpTime==0){
                imGroupDao.delBan(map);
            }
        }
        String date= HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(forbidSend));//发送请求
        return date;
    }
    //安全管理表 這個群不能发什么信息 可以发什么信息
    @Override
    public List<Map<String, Object>> securityManagement() {
        return imGroupDao.securityManagement();
    }
    //设置群安全管理
    @Override
    public int setUp(Map<String, Object> map) {
        return imGroupDao.setUp(map);
    }
}
