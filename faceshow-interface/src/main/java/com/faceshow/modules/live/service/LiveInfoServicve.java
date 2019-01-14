package com.faceshow.modules.live.service;

import com.faceshow.modules.IM.Bean.ForbidSend;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 直播操作Service接口
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/3 8:35
 * -------------------------------------------------------------- <br>
 */
public interface LiveInfoServicve {

    /**
     * 查询正在直播的直播列表
     *
     * @param currPage 当前页, 默认值为1
     * @param pageSize 每页显示信息数 默认值为10
     * @param type     查询类型, 1全球热门, 2本地热门, 3最新开播
     * @param country  当前国家
     * @return
     */
    Object findLiveList(Integer currPage, Integer pageSize, Integer type, String country);

    /**
     * @Author:YS
     * @Description: 批量插入数据库
     * @Date:2018/3/10
     * @param:
     */
    Map<String, Object> insertSelective(Map<String, Object> map, MultipartFile img) throws Exception;//批量插入

    /**
     * @Author:YS
     * @Description: 直播结束后更新数据库 TX的回掉
     * @Date:2018/3/10
     * @param:
     */
    int endBroadcast(Map<String, Object> map);

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
    Object finalLivePush(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查看这个群里面的禁言时间和人
     * @Date:2018/3/17
     * @param:No such property: code for class: Script1
     */

    List<Map<String, Object>> selectTimes(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description:申请连麦
     * @Date:2018/3/20
     * @param:
     */
    Map<String, Object> insertMicrophone(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description:申请视频连麦
     * @Date:2018/3/29
     * @param:No such property: code for class: Script1
     */

    Map<String, Object> videoMicrophone(Map<String, Object> map);

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
     * @Date:2018/3/29
     * @param: #{anchor_id} 主播ID
     */
    List<Map<String, Object>> selectvideoMicrophone(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 主播同意连麦连麦
     * @Date:2018/3/20
     * @param:
     */
    String endMicrophoneList(String json, String format, String stream_id, String live_id);

    /**
     * @Author:YS
     * @Description: 连麦结束后删除这个人的申请在连麦申请表
     * @Date:2018/3/21
     * @param:
     */
    int finishMicrophoneList(int audience_id);

    /**
     * @Author:YS
     * @Description: 申请连麦遭到拒绝调取接口
     * @Date:2018/3/21
     * @param: #{stream_id}
     */
    int refuseMicrophoneList(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 主播邀请观众进行连麦
     * @Date:2018/3/22
     * @param:No such property: code for class: Script1
     */
    Map<String, String> anchorApplicationMicrophoneList(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: <!--1V1表等待匹配表的插入-->
     * @Date:2018/3/22
     * @param:
     */
    Object insertSelectiveLiveMatching(Map<String, Object> map) throws Exception;

    /**
     * @Author:YS
     * @Description: 查询1v1类型表
     * @Date:2018/3/22
     * @param:
     */
    List<Map<String, Object>> selectLiveMatchingType();


    /**
     * @Author:YS
     * @Description:<!--插入1V1匹配中表-->
     * @Date:2018/3/23
     * @param:No such property: code for class: Script1
     */

    String intervalLiveMatchingFinish(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: <!--历史1V1记录-->
     * @Date:2018/3/23
     * @param:
     */
    Map<String, Object> allFinish(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description://返回1V1还剩下多少时间单位秒
     * @Date:2018/3/28
     * @param: #{finish_id}
     */

    Map<String, Object> RemainingTime(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: //Pk的视频批量插入
     * @Date:2018/3/28
     * @param:No such property: code for class: Script1
     */
    Object insertSelectiveLive_challenge(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: PK挑战结束
     * @Date:2018/3/28
     * @param:No such property: code for class: Script1
     */
    Object stateChallenge(Map<String, Object> map, String json, String format);

    /**
     * @Author:YS
     * @Description: 开始PK啦 #{types}  0等待PK中 1PK进行时2PK结束',
     * #{live_challenge_id}
     * @Date:2018/3/29
     * @param:No such property: code for class: Script1
     */

    String beginChallenge(Map<String, Object> map, String json, String format);

    /**
     * @Author:YS
     * @Description:<!--查看连麦的开关状态-->
     * @Date:2018/3/29
     * @param: #{live_id}
     */
    Map<String, Object> microphone_switch(Map<String, Object> map);//<!--查看连麦的开关状态-->

    /**
     * @Author:YS
     * @Description:修改连麦开关的状态
     * @Date:2018/3/29
     * @param: #{microphone_switch} #{live_id}
     */
    int stateMicrophone_switch(Map<String, Object> map);//修改连麦开关的状态

    /**
     * @Author:YS
     * @Description: <!--查看全局禁言的开关状态-->
     * @Date:2018/3/30
     * @param: live_id
     */
    Map<String, Object> prohibit_speaking(Map<String, Object> map);//<!--查看全局禁言的开关状态-->

    /**
     * @Author:YS
     * @Description: 修改全局禁言的状态
     * @Date:2018/3/30
     * @param: live_id   prohibit_speaking
     */
    int stateProhibit_speaking(Map<String, Object> map);//修改全局禁言开关的状态

    /**
     * @Author:YS
     * @Description: <!-- 批量删除视频连麦的 -->
     * @Date:2018/3/29
     * @param:No such property: code for class: Script1
     */

    void deleteAll(String[] stream_id);// <!-- 批量删除视频连麦的 -->

    /**
     * @Author:YS
     * @Description: 查询直播的分类
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
   Object selectLive_typeAll();

    /**
     * 查询本国热门视频
     *
     * @param offset    开始查询数量
     * @param limit     每页显示信息数
     * @param countryId 当前国家
     * @param state     状态, 1查询本国热门, 2查询国际热门
     * @return
     */
    List<Object> findDomesticHotLive(Integer offset, Integer limit, Integer countryId, Integer state);

    /**
     * 根据路径查询直播是否正在继续
     *
     * @param liveUrl 直播路径
     * @return
     */
    List<Map<String, Object>> findLiveByLiveUrl(String liveUrl);

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

    /**
     * @author: YS
     * @Date:2018/4/23 14:31
     * @param:
     * @explain： 查看主播是否實名認證
     * @return:
     */
    Object getAuthentication(Map<String, Object> map);

}
