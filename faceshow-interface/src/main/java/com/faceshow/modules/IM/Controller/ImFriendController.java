package com.faceshow.modules.IM.Controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.R;
import com.faceshow.modules.IM.Bean.*;
import com.faceshow.modules.IM.Imutils;
import com.faceshow.modules.IM.Service.ImFriendService;
import com.faceshow.modules.report.service.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation:  主要是关系链的管理 比如加好友 删除好友 拉黑等等
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.IM.Controller<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/1/26 16:52
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/im/Friend")
public class ImFriendController {
    @Autowired
    private ImFriendService imFriendService;
    @Autowired
    private ReportInfoService reportInfoService;

    @PostMapping("/friend_add")
    //添加好友
    /**
     *@Author:YS
     *@Description: 这个接口重复添加 也是显示OK 但是会有错误编码
     * To_Account: "lisi"
       ResultCode: 30520
         ResultInfo: "SNS_FRD_ADD_FRD_EXIST"  表示你已经有该好友了 但是并不影响我们当作关注使用
     *@Date:2018/1/29
     */

    /*public  Object friend_add (ImFriendAdd imFriendAdd){
        String BUSINESS="v4/sns/friend_add";//业务API
        String api = Imutils.BASISAPI+BUSINESS+Imutils.PUBLIC;//拼接URL
       Object date=imFriendService.insertAttention(api,imFriendAdd);
      // String date= HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imFriendAdd));//发送请求
        return R.result(1,"ok",date);
    }*/
    @SysLog
    public  Object friend_add (String Json){
        ImFriendAdd imFriendAdd= JsonUtils.jsonToPojo(Json,ImFriendAdd.class);
       // System.out.println(JsonUtils.objectToJson(imFriendAdd));
        String BUSINESS="v4/sns/friend_add";//业务API
        String api = Imutils.BASISAPI+BUSINESS+Imutils.PUBLIC;//拼接URL
        Object date=imFriendService.insertAttention(api,imFriendAdd);
        // String date= HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imFriendAdd));//发送请求
        return R.result(1,"ok",date);
    }
    @PostMapping("/friend_delete")
    //取消关注好友
    /*
    * From_Account 	String 	必填 	需要为该Identifier删除好友。
      To_Account 	String数组 	必填 	待删除的好友的Identifier。 但是接口不支持批量取消关注
      成功 {"code":1,"result":"{\"ResultItem\":[{\"To_Account\":\"7\",\"ResultCode\":0,\"ResultInfo\":\"OK\"}],\"ActionStatus\":\"OK\",\"ErrorCode\":0,\"ErrorInfo\":\"\",\"ErrorDisplay\":\"\"}","msg":"ok"}
      伪成功 也算成功 也返回OK 只是腾讯告诉你 你删除的不是你的好友 但是不会报错
    * */
    @SysLog
    public  Object friend_delete (ImFriendDelete imFriendDelete){
        String BUSINESS="v4/sns/friend_delete";//业务API
        String api = Imutils.BASISAPI+BUSINESS+Imutils.PUBLIC;//拼接URL
        Object date=imFriendService.deleteSingle(api,imFriendDelete);
       //  String date= HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imFriendDelete));//发送请求
        return R.result(1,"ok",date);
    }


    @PostMapping("/black_list_add")
    //加入黑名单
    //From_Account 	String 	必填 	需要为该Identifier添加黑名单。 To_Account 	String 	必填 	黑名单的Identifier。
    //{"code":1,"result":"{\"ResultItem\":[{\"To_Account\":\"lisi\",\"ResultCode\":31307,\"ResultInfo\":\"Err_SNS_BlackListAdd_Already_Exist\"}],\"Fail_Account\":[\"lisi\"],\"ActionStatus\":\"OK\",\"ErrorCode\":0,\"ErrorInfo\":\"\",\"ErrorDisplay\":\"\"}","msg":"ok"}
    @SysLog
    public  Object black_list_add (ImBlackListAdd imBlackListAdd){
        String BUSINESS="v4/sns/black_list_add";//业务API
        String api = Imutils.BASISAPI+BUSINESS+Imutils.PUBLIC;//拼接URL
       Object date=imFriendService.black_list_add(api,imBlackListAdd);
   //   String date= HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imBlackListAdd));//发送请求
        return R.result(1,"ok",date);
    }
    @PostMapping("/black_list_delete")
    //取消拉黑
    /*From_Account 	String 	必填 	需要为该Identifier删除黑名单。
      To_Account 	Array 	必填 	将要删除的黑名单的Identifier。
     */
    //伪移除 也就是说你已经移除但是再移除一边 腾讯不会给你报错 只是返回信息变了 告诉你 已经移除过了  删除不存在的黑名单，请确认待删除的帐号是否是黑名单，也不会报错的
    @SysLog
    public  Object black_list_delete (ImBlackListAdd imBlackListAdd){
        String BUSINESS="v4/sns/black_list_delete";//业务API
        String api = Imutils.BASISAPI+BUSINESS+Imutils.PUBLIC;//拼接URL
        Object date=imFriendService.black_list_delete(api,imBlackListAdd);
        //String date= HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imBlackListAdd));//发送请求
        return R.result(1,"ok",date);
    }
    @PostMapping("/portrait_set")
    //设置资料
    @SysLog
    public  String portrait_set (ImPortraitSet imPortraitSet){
        String BUSINESS="v4/profile/portrait_set";//业务API
        String api = Imutils.BASISAPI+BUSINESS+Imutils.PUBLIC;//拼接URL
       String date=imFriendService.editUser_info(api,imPortraitSet);
        return date;
    }

    @SysLog
    @PostMapping("/myFriends")
    //查找我的好友接口
    public  Object myFriends (@RequestParam Map<String,Object>map){
        List<Map<String,Object>> myFriends = imFriendService.myFriends(map);
        return R.result(1,"ok",myFriends);
    }

}
