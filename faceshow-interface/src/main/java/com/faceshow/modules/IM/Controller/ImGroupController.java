package com.faceshow.modules.IM.Controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.exception.RRException;
import com.faceshow.common.utils.*;
import com.faceshow.modules.IM.Bean.*;
import com.faceshow.modules.IM.Imutils;
import com.faceshow.modules.IM.Service.ImGroupService;
import com.faceshow.modules.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tokens/im")
public class ImGroupController {
    @Autowired
    private ImGroupService imGroupService;
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/get_group_info")
    //获取群组详细资料
    @SysLog
    public Object get_group_info(String group) {
      /*  String BUSINESS="v4/group_open_http_svc/get_group_info";//业务API
        String api = Imutils.BASISAPI+BUSINESS+Imutils.PUBLIC;//拼接URL
        String date= HttpClientUtil.doPostJson(api,JsonUtils.objectToJson(map));//发送请求
        return date;*/
        //String group="0371";
        String json = "{\"GroupIdList\":[\"" + group + "\"],\"ResponseFilter\": {\"GroupBaseInfoFilter\": [\"MemberNum\",\"Name\"],\"MemberInfoFilter\": [\"Account\",\"Role\"]}}";//传递的JSON字符串参数
        String BUSINESS = "v4/group_open_http_svc/get_group_info";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        String date = HttpClientUtil.doPostJson(api, json);//发送请求
        Member member = JsonUtils.jsonToPojo(date, Member.class);
        List<MemberList> memberList = member.getGroupInfo().get(0).getMemberList();
        for (MemberList list : memberList) {
            Map<String, Object> selectImg = imGroupService.selectImg(list.getMember_Account());//ID
            list.setNick_name(selectImg.get("nick_name").toString());//设置名字
            if (selectImg.get("img") == null) {
                list.setImg("");//设置图片
            } else {
                list.setImg(selectImg.get("img").toString());//设置图片
            }
            if (selectImg.get("signature") == null) {
                list.setSignature("");
            } else {
                list.setSignature(selectImg.get("signature").toString());
            }
            //System.out.println(list);
        }
        //System.out.println("群里面的返回值"+ JsonUtils.objectToJson(memberList));
        return R.result(1, "OK", memberList);
    }


    @PostMapping("/send_group_msg")
    //在群组中发送普通消息
    @SysLog
    public Object send_group_msg(ImSendGroupMsg imSendGroupMsg) {
        String BUSINESS = "v4/group_open_http_svc/send_group_msg";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imSendGroupMsg));//发送请求
        Map json = JsonUtils.jsonToPojo(date, new HashMap<>().getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
            throw new RRException(date);
        }
        return R.result(1, "ok", date);
    }

    @PostMapping("/send_group_system_notification")
    //在群组中发通知
    //GroupId 	String 	必填 	向哪个群组发送系统通知。
    //Content 	String 	必填 	系统通知的内容。
    @SysLog
    public Object send_group_system_notification(@RequestParam Map<String, Object> map) {
        String BUSINESS = "v4/group_open_http_svc/send_group_system_notification";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));//发送请求
        Map json = JsonUtils.jsonToPojo(date, map.getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
            throw new RRException(date);
        }
        return R.result(1, "ok", date);
    }

    /**
     * @author: YS
     * @Date:2018/4/28 14:20
     * @param:
     * @explain： 获取用户所加入的群组
     * @return:
     */

    @SysLog
    @PostMapping("/get_joined_group_list")
    //获取APP中的所有群组
    public Object get_joined_group_list(String userId) {
        String json = "{\n" +
                "    \"Member_Account\": \"" + userId + "\",\n" +
                "    \"ResponseFilter\": {\n" +
                "        \"GroupBaseInfoFilter\": [\n" +
                "            \"GroupId\",\n" +
                "            \"Type\",\n" +
                "            \"Name\"\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        String BUSINESS = "v4/group_open_http_svc/get_joined_group_list";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        String date = HttpClientUtil.doPostJson(api, json);//发送请求
        return R.result(1, "ok", date);
    }


    @SysLog
    @PostMapping("/get_appid_group_list")
    //获取APP中的所有群组
    public Object get_appid_group_list(@RequestParam Map<String, Object> map) {
        String BUSINESS = "v4/group_open_http_svc/get_appid_group_list";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));//发送请求
        return date;
    }

    @SysLog
    @PostMapping("/create_group")
    //创建群组
    public Object create_group(String Json) throws Exception {
        ImBean map = JsonUtils.jsonToPojo(Json, ImBean.class);
        if (map.getName().getBytes("utf-8").length > 30) {
            throw new RRException("群名称过长");
        }
        String BUSINESS = "v4/group_open_http_svc/create_group";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        Object date = imGroupService.insertSelective(api, map);
        //String date= HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));//发送请求
        return R.result(1, "ok", date);
    }

    @SysLog
    @PostMapping("/get_group_member_info")
    //获取群组成员详细资料
    public Object get_group_member_info(@RequestParam Map<String, Object> map) {
        String BUSINESS = "v4/group_open_http_svc/get_group_member_info";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));//发送请求
        return date;
    }

    @SysLog
    @PostMapping("/modify_group_base_info")
    //修改群组基础资料
    public Object modify_group_base_info(@RequestParam Map<String, Object> map) {
        String BUSINESS = "v4/group_open_http_svc/modify_group_base_info";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        Object date = imGroupService.updateSelective(api, map);
        //String date= HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));//发送请求
        return R.result(1, "ok", date);
    }

    /*  @PostMapping("/add_group_member")
      //增加群组成员
      public  Object add_group_member( ImBean imBean){
          String BUSINESS="v4/group_open_http_svc/add_group_member";//业务API
          String api =Imutils.BASISAPI+BUSINESS+Imutils.PUBLIC;//拼接URL
          Object date=  imGroupService.insertGroup(api,imBean);
          return R.result(1,"ok",date);
      }*/
    @SysLog
    @PostMapping("/add_group_member")
    //增加群组成员
    public Object add_group_member(String Json) {
        ImBean imBean = JsonUtils.jsonToPojo(Json, ImBean.class);
        String BUSINESS = "v4/group_open_http_svc/add_group_member";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        Object date = imGroupService.insertGroup(api, imBean);
        return R.result(1, "ok", date);
    }

    @SysLog
    @PostMapping("/delete_group_member")
    //删除群组成员
    public Object delete_group_member(ImDelGropBean imDelGropBean) {
        String BUSINESS = "v4/group_open_http_svc/delete_group_member";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        Object date = imGroupService.deleteGroupMember(api, imDelGropBean);
        // String date= HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));//发送请求
        return R.result(1, "ok", date);
    }

    @SysLog
    @PostMapping("/modify_group_member_info")
    // 也就是设置管理员 取消管理员 目前此接口不支持批量设置管理员    设置取消设置管理员(直播的群設置取消管理員不可以調取這個接口)
    public Object modify_group_member_info(@RequestParam Map<String, Object> map) {
        String BUSINESS = "v4/group_open_http_svc/modify_group_member_info";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        Object date = imGroupService.modifyGroupMemberInfo(api, map);
        //  String date= HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));//发送请求
        return R.result(1, "ok", date);
    }


    @SysLog
    @PostMapping("/live_info_modify_group_member_info")
    // 设置取消设置管理员(直播的群設置取消管理員調取这个接口即可)
    public Object live_info_modify_group_member_info(@RequestParam Map<String, Object> map) {
        String BUSINESS = "v4/group_open_http_svc/modify_group_member_info";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(map));//发送请求
        return R.result(1, "ok", date);
    }

    @SysLog
    @PostMapping("/destroy_group")
    //解散群
    public Object destroy_group(@RequestParam Map<String, Object> map) {
        String BUSINESS = "v4/group_open_http_svc/destroy_group";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        Object date = imGroupService.destroyGroup(api, map);
        // String date= HttpClientUtil.doPostJson(api,JsonUtils.objectToJson(map));//发送请求
        return R.result(1, "ok", date);
    }

    /**
     * 查询所有群类型
     *
     * @return
     */
    @SysLog
    @PostMapping("/findGroupType")
    public Object findGroupType() {
        return imGroupService.findGroupTypeAll();
    }

    /**
     * 查询这个人加入的所有群
     *
     * @return
     */
    @SysLog
    @PostMapping("/selectAllGroup")
    public Object selectAllGroup(int user_id) {
        List<Object> objectList = imGroupService.selectAllGroup(user_id);
        return R.result(1, "OK", objectList);

    }

    /**
     * 推荐群 80后 90后标签分页查询
     *
     * @return
     */
    @SysLog
    @PostMapping("/findType")
    public Object findType(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = imGroupService.findType(query);
        int total = imGroupService.findTypeTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * @Author:YS
     * @Description:// <!--群里面有哪些人--> 有多少个人
     * @Date:2018/2/8
     */
    @SysLog
    @PostMapping("/belongGroup")
    public Object belongGroup(@RequestParam Map<String, Object> map) {
        List<Object> objects = imGroupService.belongGroup(map);
        return R.result(1, "OK", objects);

    }

    /**
     * @Author:YS
     * @Description: 查找群的类型名字
     * @Date:2018/2/8
     */
    @SysLog
    @PostMapping("/GroupType")
    public Object GroupType(@RequestParam Map<String, Object> map) {
        Object objects = imGroupService.GroupType(map);
        return R.result(1, "OK", objects);
    }

    /**
     * @Author:YS
     * @Description:查询黑名单列表
     * @Date:2018/2/9
     */
    @SysLog
    @PostMapping("/findBlacklist")
    public Object findBlacklist(@RequestParam Map<String, Object> map) {
        Object objects = imGroupService.findBlacklist(map);
        return R.result(1, "OK", objects);
    }

    /**
     * @Author:YS
     * @Description:IM 回调
     * @Date:2018/2/9
     */

    @SysLog
    @PostMapping("/callback")
    public Object callback(@RequestBody(required = false) Callback callback) {
        //确实双向验证以及HTTP的APPID验证
        HashMap<String, Object> hashMap = new HashMap<>();
        System.out.println("腾讯应答包打印-----" + JsonUtils.objectToJson(callback));
        //群成员离开之后回调并且必须是主动退群我才会处理逻辑 因为踢群的逻辑已经写过了  // 成员离开方式：Kicked-被踢；Quit-主动退群。 互動聊天室的各种加群退出不管
        if (callback.getCallbackCommand().equals("Group.CallbackAfterMemberExit") && callback.getExitType().equalsIgnoreCase("Quit") && !callback.getType().equalsIgnoreCase("AVChatRoom")) {
            hashMap.put("IM_ID", callback.getGroupId());
            hashMap.put("user_id", callback.getExitMemberList().get(0).getMember_Account());
            imGroupService.delGroup(hashMap);
            System.out.println("我进了Quit-主动退群。-------------------------------" + callback.getExitType());
            // 入群方式：Apply（申请入群）；Invited（邀请入群）。//加群之前并且是自己主动申请才处理  互動聊天室的各种加群退出不管
        } else if (callback.getCallbackCommand().equals("Group.CallbackAfterNewMemberJoin") && callback.getJoinType().equalsIgnoreCase("Apply") && !callback.getType().equalsIgnoreCase("AVChatRoom")) {
            hashMap.put("GroupId", callback.getGroupId());
            imGroupService.findById(callback, hashMap);//查询出来自己数据库表的主键
            System.out.println("我进了Apply（申请入群）。-------------------------------" + callback.getJoinType());
        }/*else if (callback.getCallbackCommand().equals("C2C.CallbackBeforeSendMsg")){
            hashMap.put("user_id",callback.getFrom_Account());
            hashMap.put("account",callback.getTo_Account());
            Map<String, Object> userChat = userInfoService.selectUser_Chat(hashMap);//查找主键
            System.out.println("请看发单聊消息之前回调：From_Account"+callback.getFrom_Account()+"To_Account"+callback.getTo_Account());
        }*/
        System.out.println("请看回掉入群方式：Apply（申请入群）；Invited（邀请入群）-------------------------------" + callback.getJoinType());
        System.out.println("请看回掉成员离开方式：Kicked-被踢；Quit-主动退群。-------------------------------" + callback.getExitType());
        System.out.println("腾讯回掉包为" + callback);
        return new ImCallback();
    }

    /**
     * @Author:YS
     * @Description: 修改群类型
     * @Date:2018/3/1
     * @param:type_id 类型ID    IM_ID群ID
     */
    @SysLog
    @PostMapping("/updateSelectiveType")
    public Object updateSelectiveType(@RequestParam Map<String, Object> map) {
        Object objects = imGroupService.updateSelectiveType(map);
        return R.result(1, "OK", objects);
    }

    /**
     * @Author:YS
     * @Description:<!--查看这个人在群里面是什么身份-->
     * @Date:2018/2/28
     * @param:userId IM_ID
     */
    @SysLog
    @PostMapping("/isRole")
    public Object isRole(@RequestParam Map<String, Object> map) {
        return R.result(1, "OK", imGroupService.isRole(map));
    }

    public static void main(String[] args) {
        ImGroupController controller = new ImGroupController();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("GroupId", "@TGS#2HF7KBBFY");
        map.put("Member_Account", "zhaoliu");
        map.put("Role", "Admin");
        Object groupList = controller.modify_group_member_info(map);
        System.out.println(groupList);
    }

    @SysLog
    @PostMapping("/forbid_send_msg")
    //取消和设置禁言
    public Object forbid_send_msg(@RequestParam Map<String, Object> map, ForbidSend forbidSend) {
        String BUSINESS = "v4/group_open_http_svc/forbid_send_msg";//业务API
        String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
        imGroupService.insetInfo_ban(map, api, forbidSend);
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(forbidSend));//发送请求
        return R.result(1, "ok", date);
    }

    /**
     * @Author:YS
     * @Description: 查询群的安全管理表 这个群可以发什么不可以发什么
     * @Date:2018/3/19
     * @param:No such property: code for class: Script1
     */

    @SysLog
    @PostMapping("/securityManagement")
    public Object securityManagement() {
        Object objects = imGroupService.securityManagement();
        return R.result(1, "OK", objects);
    }

    /**
     * @Author:YS
     * @Description: 设置这个群可以发什么 不可以发什么
     * @Date:2018/3/19
     * @param:
     */

    @SysLog
    @PostMapping("/setUp")
    public Object setUp(@RequestParam Map<String, Object> map) {
        Object objects = imGroupService.setUp(map);
        return R.result(1, "OK", objects);
    }
}
