package com.faceshow.modules.live.dao;

import com.faceshow.modules.IM.Bean.ForbidSend;
import com.faceshow.modules.live.entity.LiveInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface LiveInfoDao {
    int deleteByPrimaryKey(Integer liveId);

    int insertSelective(Map<String, Object> map);//批量插入

    LiveInfo selectByPrimaryKey(Integer liveId);

    int updateByPrimaryKeySelective(LiveInfo record);

    /**
     * 查询正在直播的直播列表
     *
     * @param search
     * @return
     */
    List<Object> findLiveList(Map<String, Object> search);

    /**
     * 查询正在直播的数量
     *
     * @param search
     * @return
     */
    Integer findLiveTotalCount(Map<String, Object> search);

    /**
     * @Author:YS
     * @Description: 更新用户表 更新为主播 而且更新主播编号
     * @Date:2018/3/10
     * @param:
     */

    int updateAnchor(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 更新用户直播间信息
     * @Date:2018/3/10
     * @param:
     */
    int insertlive_home_info(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 直播结束后更新数据库 TX的回掉
     * @Date:2018/3/10
     * @param:
     */
    int endBroadcast(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description:看看是否实名认证
     * @Date:2018/3/12
     * @param:No such property: code for class: Script1
     */
    Map<String, Object> authentication(Map<String, Object> map);


    /**
     * @Author:YS
     * @Description:查看主播编号
     * @Date:2018/3/12
     * @param:
     */
    String findAnchor_no(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查看正在直播 带分页
     * @Date:2018/3/12
     * @param:
     */
    List<Map<String, Object>> beingBroadcast(Map<String, Object> map);

    int beingBroadcastTotal();

    /**
     * @Author:YS
     * @Description: 根据用户ID 跟新IM的聊天室
     * @Date:2018/3/13
     * @param:
     */
    int updateIM_ID(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 解散群的时候使用需要查询出来解散哪个群
     * @Date:2018/3/13
     * @param:
     */
    String selectIm_ID(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查询这场直播增加了多少粉丝 多少等 需要得到开播时间和结束开播时间
     * @Date:2018/3/13
     * @param:
     */
    Map<String, Object> selectLive_info(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查询这个人直播了多少次了
     * @Date:2018/3/13
     * @param:
     */
    int selectNumber(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description:解散群聊天室
     * @Date:2018/3/15
     * @param:
     */
    String selectIm(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查看这个群里面的禁言时间和人
     * @Date:2018/3/17
     * @param:No such property: code for class: Script1
     */

    List<Map<String, Object>> selectTimes(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查看主播的信息
     * @Date:2018/3/19
     * @param:
     */
    Map<String, Object> selectAnchor(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description:申请连麦
     * @Date:2018/3/20
     * @param:
     */
    int insertSelectiveMicrophone(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查询申请连麦的的列表页面 传递的是主播的的用户ID
     * @Date:2018/3/20
     * @param: #{anchor_id} 主播ID
     */
    List<Map<String, Object>> selectMicrophoneList(Map<String, Object> map);


    /**
     * @Author:YS
     * @Description: 查询申请连麦的的列表页面 传递的是主播的的用户ID
     * @Date:2018/3/20
     * @param: #{anchor_id} 主播ID
     */
    Map<String, Object> applyMicrophoneList(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 主播同意连麦连麦
     * @Date:2018/3/20
     * @param:
     */
    int endMicrophoneList(String stream_id);

    /**
     * @Author:YS
     * @Description: 连麦结束后删除这个人的申请在连麦申请表
     * @Date:2018/3/21
     * @param:
     */
    int finishMicrophoneList(int audience_id);

    /**
     * @Author:YS
     * @Description: 直播结束后的回掉 删除这个主播所有的连麦申请
     * @Date:2018/3/21
     * @param:
     */

    int finishBroadcast(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 根据stream_id 查询这个表的主键
     * @Date:2018/3/30
     * @param:No such property: code for class: Script1
     */
    int findMicrophone_id(String stream_id);

    /**
     * @Author:YS
     * @Description: 增加业务关联和直播表
     * @Date:2018/3/30
     * @param:
     */
    int updateLive_infoBusiness_id(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 申请连麦遭到拒绝调取接口
     * @Date:2018/3/21
     * @param: #{stream_id}
     */
    int refuseMicrophoneList(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查看是否申请过连麦
     * @Date:2018/3/21
     * @param:No such property: code for class: Script1
     */

    Map<String, Object> connectMicrophoneList(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: <!--1V1表等待匹配表的插入-->
     * @Date:2018/3/22
     * @param:
     */
    int insertSelectiveLiveMatching(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查询1v1类型表
     * @Date:2018/3/22
     * @param:
     */
    List<Map<String, Object>> selectLiveMatchingType();


    /**
     * @Author:YS
     * @Description: <!--去1V1匹配去 传递匹配的时间间隔的时间和匹配类型  interval  type_id 匹配一个-->
     * @Date:2018/3/22
     * @param:
     */
    Map<String, Object> intervalMatchingType(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description:<!--插入1V1匹配中表-->
     * @Date:2018/3/23
     * @param:No such property: code for class: Script1
     */

    int intervalLiveMatchingFinish(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 匹配成功后把live_matching匹配标识symbol符设置为1
     * @Date:2018/3/23
     * @param:
     */
    int finishMatching(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 是否以前已经匹配过了
     * @Date:2018/3/23
     * @param:No such property: code for class: Script1
     */

    Map<String, Object> historyMatching(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: <!--1V1匹配过后就更新匹配次数加一-->
     * @Date:2018/3/23
     * @param:No such property: code for class: Script1
     */
    int updateNum(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: <!--历史1V1记录-->
     * @Date:2018/3/23
     * @param:
     */
    Map<String, Object> allFinish(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: <!--历史1V1赠送和收到的礼物个数-->
     * @Date:2018/3/23
     * @param:
     */
    Map<String, Object> stotalgift(Map<String, Object> map);//收到

    Map<String, Object> ztotalgift(Map<String, Object> map);//送的

    int addTime(Map<String, Object> map);//送的时长

    Map<String, Object> selectaddTime(Map<String, Object> map);//更新时长


    Map<String, Object> OrAttention(Map<String, Object> map);//是否关注


    Map<String, Object> playUrl(Map<String, Object> map);//返回退拉流

    int delMatching(Map<String, Object> map);//删除匹配

    Map<String, Object> RemainingTime(Map<String, Object> map);//返回1V1还剩下多少时间单位秒 #{finish_id}

    int insertSelectiveLive_challenge(Map<String, Object> map);//Pk的视频批量插入

    int broadcastState(Map<String, Object> map);//修改状态 视频直播的状态 '0普通1PK2私密3交友',

    int stateChallenge(Map<String, Object> map);//<!--PK结束后 操作--> #{types} #{live_challenge_id}


    Map<String, Object> microphone_switch(Map<String, Object> map);//<!--查看连麦的开关状态-->

    int stateMicrophone_switch(Map<String, Object> map);//修改连麦开关的状态


    Map<String, Object> prohibit_speaking(Map<String, Object> map);//<!--查看全局禁言的开关状态-->

    int stateProhibit_speaking(Map<String, Object> map);//修改全局禁言开关的状态


    void deleteAll(String[] stream_id);// <!-- 批量删除视频连麦的 -->

    /**
     * @Author:YS
     * @Description: <!--个人查看连麦列表01-->
     * @Date:2018/3/30
     * @param: userId 关注人
     * account被关注人
     */

    Map<String, Object> personalLianmaiListAttention(Map<String, Object> map);


    /**
     * @Author:YS
     * @Description: <!--查询开始直播的分类 不返回PK-->
     * @Date:2018/4/8
     * @param:
     */
    List<Map<String, Object>> selectLive_type();

    /**
     * @Author:YS
     * @Description: <!--直播分类页面的返回-->
     * @Date:2018/4/8
     * @param:
     */
    List<Map<String, Object>> selectLive_typeAll();

    /**
     * @Author:YS
     * @Description: <!--直播查看红队和蓝队的信息 如果是PK的话-->
     * @Date:2018/4/9
     * @param:
     */
    List<Map<String, Object>> selectLive_challenge(Map<String, Object> map);

    /**
     * 查询本国热门视频
     *
     * @param offset    开始查询数量
     * @param limit     每页显示信息数
     * @param countryId 当前国家
     * @param state     状态, 1查询本国热门, 2查询国际热门
     * @return
     */
    List<Object> findDomesticHotLive(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("countryId") Integer countryId, @Param("state") Integer state);

    /**
     * 根据路径查询直播是否正在继续
     *
     * @param liveUrl 直播路径
     * @return
     */
    List<Map<String, Object>> findLiveByLiveUrl(String liveUrl);


    /**
     * @author: YS
     * @Date:2018/4/14 17:10
     * @param: userId
     * @explain： <!--为了防止直播结束后腾讯回掉失败，当开启直播的时候，再次关闭以前的直播-->
     * @return:
     */

    int updateEndBroadcast(Map<String, Object> map);

    /**
     * @author: YS
     * @Date:2018/4/16 19:55
     * @param:
     * @explain： <!--查看某个群的 连麦开关0关闭1开启' 全局禁言开关0关闭1开启' 以及在这个群的状态 是否禁言-->
     * @return:
     */
    Map<String, Object> imSwitch(Map<String, Object> map);

    /**
     * @author: YS
     * @Date:2018/4/16 20:03
     * @param:
     * @explain： <!--查詢出來這個人是否在群里被禁言-->
     * @return:
     */
    Map<String, Object> banIm(Map<String, Object> map);

    /**
     * @author: YS
     * @Date:2018/4/17 18:48
     * @param:
     * @explain：<!--根據编码查找国家ID-->
     * @return:
     */
    String selectCountry_id(String coding);


    /**
     * @author: YS
     * @Date:2018/4/20 9:45
     * @param:
     * @explain： 对1V1表的CRUD
     * @return:
     */
    List<Map<String, Object>> selectLive_v_set(Map<String, Object> map);//  <!--1V1设置设置状态表--> 查看 可以传递userId 也可以不传递

    int insertLive_v_set(Integer userId);//插入1V1初始化1V1设置 仅仅传递userId就可以

    int updateLive_v_set(Map<String, Object> map);//插入1V1初始化1V1设置 根据userId 批量更新 传递什么字段就更新什么字段

    /**
     * @author: YS
     * @Date:2018/4/20 10:32
     * @param:
     * @explain： <!--查看直播最后一次的直播间名字以及背景图-->
     * @return:
     */
    Map<String, Object> selectMyLive(Map<String, Object> map);


    int SaveLive_home_info(Map<String, Object> map);
}