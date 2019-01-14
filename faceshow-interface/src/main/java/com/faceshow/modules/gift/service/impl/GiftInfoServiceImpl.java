package com.faceshow.modules.gift.service.impl;

import com.faceshow.common.MQ.message.push.*;
import com.faceshow.common.utils.DateUtils;
import com.faceshow.common.utils.MessagePushSummaryUtlis;
import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.push.Jpush.JpushUtils;
import com.faceshow.modules.gift.dao.GiftInfoDao;
import com.faceshow.modules.gift.dao.GiftUserInfoDao;
import com.faceshow.modules.gift.entity.GiftUserInfo;
import com.faceshow.modules.gift.service.GiftInfoService;
import com.faceshow.modules.live.dao.LiveInfoDao;
import com.faceshow.modules.user.dao.UserGiveAwayLogDao;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.user.dao.UserInfoDetailDao;
import com.faceshow.modules.user.service.UserInfoService;
import com.faceshow.modules.user.vo.MessagePushSummary;
import com.faceshow.modules.video.dao.VideoGiftSendDao;
import com.faceshow.modules.video.dao.VideoInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;


/**
 * 礼物操作Service实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/1 17:15
 * -------------------------------------------------------------- <br>
 */
@Service
@Transactional
public class GiftInfoServiceImpl implements GiftInfoService {

    @Autowired
    private GiftInfoDao giftInfoDao;

    @Autowired
    private UserInfoDetailDao userInfoDetailDao;

    @Autowired
    private GiftUserInfoDao giftUserInfoDao;

    @Autowired
    private VideoGiftSendDao videoGiftSendDao;

    @Autowired
    private UserGiveAwayLogDao userGiveAwayLogDao;
    @Autowired
    private LiveInfoDao liveInfoDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;
    @Autowired
    private VideoInfoDao videoInfoDao;

    /**
     * 点击礼物图标, 弹出礼物页面, 查询所有礼物及当前用户拥有礼物
     *
     * @param userId   当前用户id
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @return
     */
    @Override
    public Object findGiftByUserId(Integer userId, Integer currPage, Integer pageSize) {
        // 查询所有礼物及当前用户拥有礼物
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("userId", userId);
        search.put("currPage", (currPage - 1) * pageSize);
        search.put("pageSize", pageSize);
        List<Object> giftUserInfos = giftInfoDao.findGiftByUserId(search);

        // 获取礼物信息数
        Integer giftTotalCount = giftInfoDao.findGiftTotalCountByUserId(userId);

        // 查询用户拥有的钻石数量
        Integer diamond = userInfoDetailDao.findDiamondByUserId(userId);
        if (diamond == null) {
            diamond = 0;
        }

        return R.ok("查询成功").put("result", new PageUtils(giftUserInfos, giftTotalCount, pageSize, currPage)).put("diamondNum", diamond);
    }

    /**
     * 用户向短视频赠送礼物
     * <p>
     * #{userId} 礼物赠送着用户id
     * #{userName} 礼物赠送着用户昵称
     * #{account}  礼物接收者用户id
     * #{accountUserName} 礼物接收者用户昵称
     * #{videoId} 赠送视频id
     * #{giftId} 赠送礼物id
     * #{numbers} 赠送礼物数量
     * #{price} 赠送礼物单价
     *
     * @param map 赠送信息
     * @return
     */
    @Override
    public Object sendGiftToVideo(Map<String, Object> map) {
        // 查询当前用户是否拥有该礼物以及拥有数量
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("userId", map.get("userId"));
        search.put("giftId", map.get("giftId"));

        // 赠送礼物数量
        Integer numbers = Integer.parseInt(map.get("numbers").toString());
        if (numbers == null || numbers < 1) {
            numbers = 1;
        }

        // 获取礼物赠送着拥有礼物数量
        Integer sendUserGiftNum = giftUserInfoDao.findGiftByUserIdAndGiftId(search);
        if (sendUserGiftNum == null) {
            return R.result(0, "您还没有该礼物", "");
        } else if (sendUserGiftNum < numbers) {
            return R.result(0, "赠送数量超出拥有数量, 赠送失败", "");
        }
        // 修改礼物赠送着礼物数量
        if (sendUserGiftNum == numbers) {
            // 赠送所有该礼物
            giftUserInfoDao.deleteGiftByUserIdAndGiftId(search);
        } else {
            //赠送部分礼物
            search.put("numbers", sendUserGiftNum - numbers);
            giftUserInfoDao.updateGiftNumByUserIdAndGiftId(search);
        }

        // 获取礼物接受者拥有礼物数量
        search.put("userId", map.get("account"));
        Integer receiveUserGiftNum = giftUserInfoDao.findGiftByUserIdAndGiftId(search);
        // 修改礼物接受者拥有礼物数量
        if (receiveUserGiftNum == null) {
            // 没有该礼物, 新添加
            GiftUserInfo giftUserInfo = new GiftUserInfo();
            giftUserInfo.setGiftId(Integer.parseInt(map.get("giftId").toString()));
            giftUserInfo.setNumbers(Integer.parseInt(map.get("numbers").toString()));
            giftUserInfo.setUserId(Integer.parseInt(map.get("account").toString()));
            giftUserInfoDao.insertSelective(giftUserInfo);
        } else {
            // 已经拥有该礼物, 修改拥有数量
            search.put("numbers", receiveUserGiftNum + numbers);
            giftUserInfoDao.updateGiftNumByUserIdAndGiftId(search);
        }

        // 向用户视频礼品赠送表中插入数据
        map.put("createTime", new Date());
        videoGiftSendDao.insertSelective(map);
        // 向用户赠送礼品表中添加数据
        map.put("isType", 1);
        map.put("itemId", map.get("videoId"));
        userGiveAwayLogDao.insertSelective(map);
        return R.result(1, "礼物赠送成功", "");
    }

    /**
     * @Author:YS
     * @Description:赠送礼物 下面三个方法都是
     * @Date:2018/2/5
     */

    @Override
    public List<Object> GiftAll(Map<String, Object> map) {
        return giftInfoDao.GiftAll(map);//所有礼物页面
    }

    @Override
    public List<Object> userGiftTO(Map<String, Object> map) {
        ArrayList<Object> objects = new ArrayList<>();
        Object giftTO = giftInfoDao.userGiftTO(map);//赠送给谁
        List<Object> giftAll = GiftAll(map);
        objects.add(giftTO);
        objects.add(giftAll);
        return objects;//赠送给谁
    }

    @Override
    public int GiftAllTotal(Map<String, Object> map) {
        return giftInfoDao.GiftAllTotal(map);//赠送数量
    }

    //开始处理赠送礼物的逻辑 TODO赠送礼物
    @Override
    public Object sendGift(Map<String, Object> map) {

        Map<String, Object> ygiftHaving = giftInfoDao.YgiftHaving(map);//查询用户自己拥有的礼物 这个礼物是任务得到的 是可以赠送的
        if (ygiftHaving != null && !ygiftHaving.get("numbers").toString().equalsIgnoreCase("0")) {
            int dbNum = (int) ygiftHaving.get("numbers");//数据库查询出来的礼物数量
            int gNum = Integer.parseInt(map.get("numbers").toString());//要赠送的礼物数量
            if ((dbNum - gNum) < 0) {
                return giftResult(map);
            }
            map.put("info_id", ygiftHaving.get("info_id"));
            giftInfoDao.DupdateNumber(map);
            giveGift(map);
            return giftResult(map);
        }
        Map<String, Object> balance = giftInfoDao.userBalance(map);
        Map<String, Object> giftPrice = giftInfoDao.giftPrice(map);//查看礼物的钱数
        int diamond = (int) balance.get("diamond");//剩余钱数 查询赠送礼物的钱够不够
        int price = Integer.parseInt(giftPrice.get("price").toString());//礼物价格
        int numbers = Integer.parseInt(map.get("numbers").toString());//礼物数量
        if ((diamond - price * numbers) >= 0) {//钻石够用
            map.put("create_time", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            map.put("editor_time",DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            map.put("price", price);
            map.put("diamond", diamond);
            map.put("numbers", numbers);
            map.put("gname", giftPrice.get("gname"));//礼物名字
            if (map.get("type").toString().equals("1")) {//赠送类型 赠送类型 1 短视频 2 直播 3 一对一 4 聊天 5个人中心送礼物, 6 问答 7PK 8充值 9提现 10 F币转换钻石',
                SendGiftOneProduct.SendGiftProduct(map);
            } else if (map.get("type").toString().equals("2")) {
                SendGiftTwoProduct.SendGiftProduct(map);
            }else if (map.get("type").toString().equals("3")) {
                SendGiftThreeProduct.SendGiftProduct(map);
            }else if (map.get("type").toString().equals("4")) {
                SendGiftFourProduct.SendGiftProduct(map);
            } else if (map.get("type").toString().equals("5")) {
                SendGiftFiveProduct.SendGiftProduct(map);
            } else if (map.get("type").toString().equals("11")) {
                SendGiftElevenProduct.SendGiftProduct(map);
            }
        } else {
            return giftResult(map);//钱不够
        }
        return giftResult(map);
    }

    /**
     * @Author:YS
     * @Description: <!--用户赠送礼品日志表-->
     * @Date:2018/2/10
     */

    @Override
    public int insertGiveAwayLog(Map<String, Object> map) {
        return giftInfoDao.insertGiveAwayLog(map);
    }

    /**
     * @Author:YS
     * @Description:赠送礼物排行榜
     * @Date:2018/2/27
     * @param：userId 收礼物人的ID
     */
    @Override
    public List<Object> giftRankingList(Map<String, Object> map) {
        return giftInfoDao.giftRankingList(map);
    }

    @Override
    public int giftRankingListTotal(Map<String, Object> map) {
        return giftInfoDao.giftRankingListTotal(map);
    }

    /**
     * @Author:YS
     * @Description:我收到的所有礼物 返回礼物个数 图片 价格
     * @Date:2018/2/27
     * @param：userId 收礼物人的ID
     */
    @Override
    public List<Object> findGiftDetail(Map<String, Object> map) {
        return giftInfoDao.findGiftDetail(map);
    }

    @Override
    public int findGiftDetailTotal(Map<String, Object> map) {
        return giftInfoDao.findGiftDetailTotal(map);
    }

    /**
     * @Author:YS
     * @Description:这一场的直播排行榜 传递live_id也就是直播的唯一ID
     * @Date:2018/3/13
     * @param:
     */
    @Override
    public List<Object> liveRankingList(Map<String, Object> map) {
        return giftInfoDao.liveRankingList(map);
    }

    @Override
    public int liveRankingListTotal(Map<String, Object> map) {
        return giftInfoDao.liveRankingListTotal(map);
    }

    /**
     * @Author:YS
     * @Description: 这个人的直播排行榜 一周内的
     * @Date:2018/3/13
     * @param: userId
     */
    @Override
    public List<Object> liveRankingListWeek(Map<String, Object> map) {
        return giftInfoDao.liveRankingListWeek(map);
    }

    @Override
    public int liveRankingListWeekTotal(Map<String, Object> map) {
        return giftInfoDao.liveRankingListWeekTotal(map);
    }

    /**
     * @Author:YS
     * @Description: 查看某个直播 或者PK 收到的礼物
     * @Date:2018/3/30
     * @param: is_type ITEM_ID
     */
    @Override
    public List<Map<String, Object>> haveGift(Map<String, Object> map) {
        return giftInfoDao.haveGift(map);
    }
    /**
     *@Author:YS
     *@Description: <!--查询PK时候的礼物价值和礼物ID-->
     *@Date:2018/3/31
     *@param: live_challenge_id
     */
    @Override
    public Map<String, Object> selectPkPrice(int live_challenge_id) {
        HashMap<String, Object> map = new HashMap<>();
        Map<String, Object> pkRedPrice = giftInfoDao.selectPkRedPrice(live_challenge_id);//红队
        Map<String, Object> yellowPrice = giftInfoDao.selectPkYellowPrice(live_challenge_id);//黄队
        map.put("red",pkRedPrice);
        map.put("yellow",yellowPrice);
        return map;
    }

    //用自己拥有的礼物进行了赠送
    private void giveGift(Map<String, Object> map) {
        Map<String, Object> giftPrice = giftInfoDao.giftPrice(map);//查看礼物的钱数
        int price = Integer.parseInt(giftPrice.get("price").toString());//礼物价格
        int numbers = Integer.parseInt(map.get("numbers").toString());//礼物数量
        map.put("create_time", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        map.put("editor_time",DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        map.put("price", price);
        map.put("numbers", numbers);
        if (map.get("type").toString().equals("1")) {//赠送类型 赠送类型 1 短视频 2 直播 3 一对一 4 聊天 5个人中心送礼物, 6 问答 7PK 8充值 9提现 10 F币转换钻石',
            SendGiftOneProduct.SendGiftProduct(map);
        } else if (map.get("type").toString().equals("2")) {
            SendGiftTwoProduct.SendGiftProduct(map);
        }else if (map.get("type").toString().equals("3")) {
            SendGiftThreeProduct.SendGiftProduct(map);
        }else if (map.get("type").toString().equals("4")) {
            SendGiftFourProduct.SendGiftProduct(map);
        } else if (map.get("type").toString().equals("5")) {
            SendGiftFiveProduct.SendGiftProduct(map);
        }
    }

    /*返回钱和礼物*/
    private Map<String, Object> giftResult(Map<String, Object> map) {
        Map<String, Object> myGift = userInfoService.myGift(map);//拥有的礼物 还要返回还有多少钱
        Map<String, Object> selectBalance = giftInfoDao.selectBalance(map);//查找剩余钱数量
        if (map.get("finish_id") != null) {
            Map<String, Object> endTime = giftInfoDao.endTime(map);
            int endTime1 = Integer.parseInt(endTime.get("end_time").toString());
            if (endTime1 < 0) {//如果是时间小于0 说明已经过期了
                selectBalance.put("end_time", 0);
            } else {
                selectBalance.put("end_time", endTime.get("end_time"));
            }
        } else {//如果不是1V1送礼物 还必须返回这个字段
            selectBalance.put("end_time", 0);
        }
        selectBalance.put("gift", myGift.get("gift"));
        return selectBalance;


    }

}
