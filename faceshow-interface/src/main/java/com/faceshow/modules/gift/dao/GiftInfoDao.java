package com.faceshow.modules.gift.dao;


import com.faceshow.modules.gift.entity.GiftInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GiftInfoDao {
    int deleteByPrimaryKey(Integer giftId);

    int insertSelective(GiftInfo record);

    GiftInfo selectByPrimaryKey(Integer giftId);

    int updateByPrimaryKeySelective(GiftInfo record);

    /**
     * 根据当前用户查询所有礼物及当前用户拥有礼物数量
     *
     * @param search 查询条件
     * @return
     */
    List<Object> findGiftByUserId(Map<String, Object> search);

    /**
     * 获取总信息数
     *
     * @param userId
     * @return
     */
    Integer findGiftTotalCountByUserId(Integer userId);
    /**
     *@Author:YS
     *@Description: 赠送礼物 6.2.1.10.4送礼物页面
     *@Date:2018/2/5
     */

    List<Object> GiftAll(Map<String, Object> search);//所有的礼物页面
    Object userGiftTO(Map<String, Object> search);//赠送用户礼物
    int GiftAllTotal(Map<String, Object> search);//所有的礼物页面带分页

    /**
     *@Author:YS
     *@Description:赠送礼物点击赠送查询数据库 钻石充足插入数据库表记录
     *@Date:2018/2/5
     */
    Map<String,Object> userBalance(Map<String, Object> search);//  <!--看看用户的钱是否够用--> 返回送礼物的人姓名和赠送和ID
    int save_gift_log(Map<String, Object> search);//插入一条记录到数据库

    int updateMoney(Map<String, Object> map);//买礼物后更新价格

    int insertGiveAwayLog(Map<String, Object> map);// <!--用户赠送礼品日志表--> 总表
    /**
    *@Author:YS
    *@Description:赠送礼物排行榜
    *@Date:2018/2/27
    *@param：userId 收礼物人的ID
    */
    List<Object> giftRankingList(Map<String, Object> map);//赠送礼物排行榜
    int giftRankingListTotal(Map<String, Object> map);//分页
    /**
     *@Author:YS
     *@Description:我收到的所有礼物 返回礼物个数 图片 价格
     *@Date:2018/2/27
     *@param：userId 收礼物人的ID
     */
    List<Object> findGiftDetail(Map<String, Object> map);//赠送礼物排行榜
    int findGiftDetailTotal(Map<String, Object> map);//分页

    int insertvideo_gift_send(Map<String, Object> map);// <!--用户赠送礼品日志表--> 视频赠送礼物表
    int inserlive_gift_send(Map<String, Object> map);// <!--用户赠送礼品日志表-->  <!-- 用户直播礼品赠送表-->

   /**
    *@Author:YS
    *@Description:这一场的直播排行榜 传递live_id也就是直播的唯一ID
    *@Date:2018/3/13
    *@param:
    */
    List<Object> liveRankingList(Map<String, Object> search);//
    int liveRankingListTotal(Map<String, Object> map);//分页
    /**
     *@Author:YS
     *@Description: 这个人的直播排行榜 一周内的
     *@Date:2018/3/13
     *@param: userId
     */
    List<Object> liveRankingListWeek(Map<String, Object> search);//这个人的直播排行榜 一周内的
    int liveRankingListWeekTotal(Map<String, Object> map);//分页

    /**
     *@Author:YS
     *@Description: 用户拥有礼品表 插入
     *@Date:2018/3/15
     *@param:
     */
    int insertGiftUserInfo(Map<String, Object> map);
    /**
     *@Author:YS
     *@Description: 用户拥有这个礼品吗？不可以赠送的
     *@Date:2018/3/15
     *@param:
     */
    Map<String, Object> giftHaving(Map<String, Object> map);
    /**
     *@Author:YS
     *@Description: 用户拥有这个礼品吗？可以赠送的
     *@Date:2018/3/15
     *@param:
     */
    Map<String, Object> YgiftHaving(Map<String, Object> map);

    /**
     *@Author:YS
     *@Description: 更新这个礼物的数量 增加
     *@Date:2018/3/15
     *@param:
     */
   int updateNumber(Map<String, Object> map);
    /**
     *@Author:YS
     *@Description: 更新这个礼物的数量 减少
     *@Date:2018/3/15
     *@param:
     */
    int DupdateNumber(Map<String, Object> map);

    Map<String, Object> selectBalance(Map<String, Object> map);//查看剩余的钱
    Map<String, Object> endTime(Map<String, Object> map);//查看剩余1V1的时间endTime

    /**
     *@Author:YS
     *@Description: 赠送礼物 PK的时候 红队
     *@Date:2018/3/29
     *@param: price    red_gift_id user_id
     */
   int addRed_gift_price(Map<String, Object> map);

    /**
     *@Author:YS
     *@Description: 赠送礼物 PK的时候 红队
     *@Date:2018/3/29
     *@param: price    yellow_gift_id user_id
     */
    int addYellow_gift_price(Map<String, Object> map);

    /**
     *@Author:YS
     *@Description: 查看礼物的价格
     *@Date:2018/3/29
     *@param:No such property: code for class: Script1
     */
    Map<String, Object> giftPrice(Map<String, Object> map);
    /**
     *@Author:YS
     *@Description: 查看某个直播 或者PK 收到的礼物
     *@Date:2018/3/30
     *@param: is_type ITEM_ID
     */

    List<Map<String,Object>> haveGift(Map<String, Object> delete);
    /**
     *@Author:YS
     *@Description: <!--查询PK时候的礼物价值和礼物ID-->
     *@Date:2018/3/31
     *@param: live_challenge_id
     */
    Map<String,Object> selectPkRedPrice(int live_challenge_id);//红队
    Map<String,Object> selectPkYellowPrice(int live_challenge_id);//黄队
}