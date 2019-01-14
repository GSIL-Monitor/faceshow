package com.faceshow.modules.IM.Service.impl;


import com.faceshow.common.MQ.message.push.FriendAddPushProduct;
import com.faceshow.common.exception.RRException;
import com.faceshow.common.utils.*;
import com.faceshow.common.utils.push.Jpush.JpushUtils;
import com.faceshow.modules.IM.Bean.ImBlackListAdd;
import com.faceshow.modules.IM.Bean.ImFriendAdd;
import com.faceshow.modules.IM.Bean.ImFriendDelete;
import com.faceshow.modules.IM.Bean.ImPortraitSet;
import com.faceshow.modules.IM.Service.ImFriendService;
import com.faceshow.modules.IM.dao.ImFriendDao;
import com.faceshow.modules.report.service.ReportInfoService;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.user.entity.BackList;
import com.faceshow.modules.user.vo.MessagePushSummary;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation:  主要是关系链的管理
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.IM.Service.impl<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/1/26 16:51
 * -------------------------------------------------------------- <br>
 */

@Service
@Transactional
public class ImFriendServiceImpl implements ImFriendService {
    @Autowired
    private ImFriendDao imFriendDao;
    @Autowired
    private ReportInfoService reportInfoService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     *@Author:YS
     *@Description: 插入相互关注的模块
     *@Date:2018/1/29
     */
    /**
     * 传递参数
     * From_Account  	需要为该Identifier添加好友。
     * AddFriendItem[0].To_Account 好友的Identifier。
     * AddFriendItem[0].Remark From_Account对To_Account的好友备注
     * AddFriendItem[0].GroupName From_Account对To_Account的分组信息
     * AddFriendItem[0].AddSource 加好友来源字段  示例：加好友来源的关键字是Android，则加好友来源字段是：AddSource_Type_Android。
     * AddFriendItem[0].AddWording From_Account和To_Account形成好友关系时的附言信息， 实例 我是张三，同意一下
     */
    @Override
    public Object insertAttention(String api, ImFriendAdd imFriendAdd) {
        Map<String, Object> map = new HashMap<>(0);
        //'会员ID主键'
        map.put("user_id", imFriendAdd.getFrom_Account());
        //加入的人不为空
        if (imFriendAdd.getAddFriendItem() != null && !imFriendAdd.getAddFriendItem().isEmpty()) {
            for (int i = 0; i < imFriendAdd.getAddFriendItem().size(); i++) {
                //你要关注谁？ account
                map.put("account", imFriendAdd.getAddFriendItem().get(i).getTo_Account());

                //如果不为NULL 说明你重复添加关注人家直接返回
                if (getUsetAttention(map) > 0) {
                    throw new RRException("已经关注了");
                }
                //我看下 人家是否关注了你
                Map<String, Object> is_tutualMap = imFriendDao.findById(map);
                if (is_tutualMap == null) {
                    //第一次关注
                    map.put("create_time", new Date());
                    imFriendDao.insertAttention(map);
                    imFriendDao.AddFensi(map);//个人表的粉丝数量加一
                    imFriendDao.AddAttention(map);//关注数量加
                    // 保存到redis中
                    redisUtils.set(RedisKeys.USER_ATTENTION + map.get("user_id") + "_" + map.get("account"), 1, RedisUtils.NOT_EXPIRE);
                    //极光推送异步通知  参数一 触发者 参数二 接收者
                    FriendAddPushProduct.pushFriendAdd(Integer.parseInt(imFriendAdd.getFrom_Account()), Integer.parseInt(imFriendAdd.getAddFriendItem().get(i).getTo_Account()));
                    return "OK";
                } else if (is_tutualMap.get("is_tutual").toString().equals("0")) {
                    map.put("attention_id", is_tutualMap.get("attention_id"));//根据主键更新为相互关注
                    map.put("is_tutual", "1");//更新为相互关注
                    imFriendDao.editAttention(map);//更新为相互关注
                    map.put("create_time", new Date());
                    map.put("is_tutual", "1");//更新为相互关注
                    imFriendDao.insertAttention(map);
                    imFriendDao.AddFensi(map);//个人表的粉丝数量加一
                    imFriendDao.AddAttention(map);//关注数量加一
                    String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imFriendAdd));//发送请求
                    Map json = JsonUtils.jsonToPojo(date, map.getClass());
                    if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
                        throw new RRException(date);
                    }
                    redisUtils.set(RedisKeys.USER_ATTENTION + map.get("account") + "_" + map.get("user_id"), 1, RedisUtils.NOT_EXPIRE);
                    //极光推送异步通知  参数一 触发者 参数二 接收者
                    FriendAddPushProduct.pushFriendAdd(Integer.parseInt(imFriendAdd.getFrom_Account()), Integer.parseInt(imFriendAdd.getAddFriendItem().get(i).getTo_Account()));
                    return date;
                } else {
                    //你们已经相互关注了
                    throw new RRException("已经相互关注了");
                }
            }
        }

        throw new RRException("请联系管理员");

    }

    /**
     * @Author:YS
     * @Description:单方面取消关注
     * @Date:2018/1/29
     */
    @Override
    public Object deleteSingle(String api, ImFriendDelete imFriendDelete) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_id", imFriendDelete.getFrom_Account());
        map.put("account", imFriendDelete.getTo_Account()[0]);//我们接口是不支持批量取消关注好友的
        imFriendDao.deleteSingle(map);//删除数据库表记录
        imFriendDao.reduceAttention(map);//更新个人表减少关注
        imFriendDao.reduceFensi(map);//更新个人表减少粉丝
        Map<String, Object> IS_OR = imFriendDao.findById(map);//查看剩余的记录是否是相互关注 是的话is_tutual字段修改为0
        //boolean b = !ObjectUtils.isEmpty(IS_OR);
        if (!ObjectUtils.isEmpty(IS_OR) && IS_OR.get("is_tutual").toString().equals("1")) {
            map.put("is_tutual", "0");//是否互相关注 0 否 1 是',
            imFriendDao.editAttention(map);
        }
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imFriendDelete));//发送请求
        Map json = JsonUtils.jsonToPojo(date, map.getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
            throw new RRException(date);
        }
        return date;
    }

    //添加黑名单
    @Override
    public Object black_list_add(String api, ImBlackListAdd imBlackListAdd) {
        BackList backList = new BackList();
        String account = imBlackListAdd.getFrom_Account();//测试用等待删除
        String account1 = imBlackListAdd.getTo_Account()[0];//测试用等待删除
        backList.setUserId(Integer.parseInt(imBlackListAdd.getFrom_Account()));//调用拉黑方法
        backList.setAccount(Integer.parseInt(imBlackListAdd.getTo_Account()[0]));//调用拉黑方法
        Integer res = reportInfoService.insertBackList(backList);
        if (res == null || res == 0) {
            throw new RRException("拉黑失败");
        }
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imBlackListAdd));//发送请求
        Map json = JsonUtils.jsonToPojo(date, new HashMap<String, Object>().getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
            throw new RRException(date);
        }
        return date;
    }

    //取消拉黑
    @Override
    public Object black_list_delete(String api, ImBlackListAdd imBlackListAdd) {
        Integer res = reportInfoService.deleteByUserid(Integer.parseInt(imBlackListAdd.getFrom_Account()), Integer.parseInt(imBlackListAdd.getTo_Account()[0]));
        if (res < 0) {
            throw new RRException("移除失敗");
        }
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imBlackListAdd));//发送请求
        Map json = JsonUtils.jsonToPojo(date, new HashMap<String, Object>().getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
            throw new RRException(date);
        }
        return date;
    }

    // 更新用户的信息
    @Override
    public String editUser_info(String api, ImPortraitSet imPortraitSet) {
        Map<String, Object> map = new HashMap<String, Object>();
        String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imPortraitSet));//发送请求
        Map json = JsonUtils.jsonToPojo(date, map.getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
            throw new RRException(date);
        }
        return date;
    }

    //<!--查看我的好友-->
    @Override
    public List<Map<String, Object>> myFriends(Map<String, Object> map) {
        return imFriendDao.myFriends(map);
    }


    /**
     * 判断两个用户是否已经关注
     *
     * @param map userId 当前用户, account 被关注人
     * @return
     */
    private Integer getUsetAttention(Map map) {
        String userAttenttion = redisUtils.get(RedisKeys.USER_ATTENTION + map.get("user_id") + "_" + map.get("account"));
        if (StringUtils.isBlank(userAttenttion)) {
            Map byAllId = imFriendDao.findByAllId(map);
            return byAllId == null ? -1 : 1;
        }
        return 1;
    }


}
