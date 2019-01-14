package com.faceshow.modules.gift.service;

import com.faceshow.modules.video.entity.VideoGiftSend;

import java.util.List;
import java.util.Map;

/**
 * 礼物操作Service接口
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/1 17:15
 * -------------------------------------------------------------- <br>
 */
public interface GiftInfoService {

    /**
     * 点击礼物图标, 弹出礼物页面, 查询所有礼物及当前用户拥有礼物
     *
     * @param userId   当前用户id
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @return
     */
    Object findGiftByUserId(Integer userId, Integer currPage, Integer pageSize);

    /**
     * 用户向短视频赠送礼物
     *
     * #{userId} 礼物赠送着用户id
     * #{userName} 礼物赠送着用户昵称
     * #{account}  礼物接收者用户id
     * #{accountUserName} 礼物接收者用户昵称
     * #{videoId} 赠送视频id
     * #{giftId} 赠送礼物id
     * #{numbers} 赠送礼物数量
     * #{price} 赠送礼物单价
     * @param map 赠送信息
     * @return
     */
    Object sendGiftToVideo(Map<String, Object> map);
    /**
     *@Author:YS
     *@Description: 赠送礼物 6.2.1.10.4送礼物页面
     *@Date:2018/2/5
     */

    List<Object> GiftAll(Map<String, Object> search);//所有的礼物页面
    List<Object> userGiftTO(Map<String, Object> search);//赠送用户礼物
    int GiftAllTotal(Map<String, Object> search);//所有的礼物页面带分页

    Object sendGift (Map<String, Object> search);//赠送礼物逻辑开始处理

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

    Map<String,Object> selectPkPrice(int live_challenge_id);
}
