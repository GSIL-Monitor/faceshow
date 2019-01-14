package com.faceshow.modules.live.service.impl;

import com.faceshow.common.MQ.message.likenum.LiveMatchingProduct;
import com.faceshow.common.MQ.message.push.LivePushProduct;
import com.faceshow.common.exception.RRException;
import com.faceshow.common.utils.*;
import com.faceshow.common.utils.mapbean.MapResultBean;
import com.faceshow.common.utils.push.Jpush.JpushUtils;
import com.faceshow.modules.Broadcast.Broadcastutils;
import com.faceshow.modules.IM.Bean.Broadcast;
import com.faceshow.modules.IM.Imutils;
import com.faceshow.modules.IM.dao.ImFriendDao;
import com.faceshow.modules.live.dao.LiveInfoDao;
import com.faceshow.modules.live.service.LiveInfoServicve;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.user.vo.MessagePushSummary;
import com.faceshow.modules.video.dao.VideoInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 直播操作Service实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/3 8:36
 * -------------------------------------------------------------- <br>
 */
@Service
@Transactional
public class LiveInfoServicveImpl implements LiveInfoServicve {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private LiveInfoDao liveInfoDao;
    @Autowired
    private VideoInfoDao videoInfoDao;
    @Autowired
    private ImFriendDao imFriendDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;

    /**
     * 查询正在直播的直播列表
     *
     * @param currPage 当前页, 默认值为1
     * @param pageSize 每页显示信息数 默认值为10
     * @param type     查询类型, 1全球热门, 2本地热门, 3最新开播
     * @param country  当前国家
     * @return
     */

    @Override
    public Object findLiveList(Integer currPage, Integer pageSize, Integer type, String country) {

        // 分页查询直播列表
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("currPage", (currPage - 1) * pageSize);
        search.put("pageSize", pageSize);
        search.put("country", country);
        search.put("type", type);
        List<Object> lives = liveInfoDao.findLiveList(search);
        // 查询直播数量
        Integer liveTotalCount = liveInfoDao.findLiveTotalCount(search);
        return R.result(1, "查询成功", new PageUtils(lives, liveTotalCount, pageSize, currPage));
    }

    //开启直播
    @Override
    public Map<String, Object> insertSelective(Map<String, Object> map, MultipartFile img) throws Exception {
        Map<String, Object> back = liveInfoDao.selectAnchor(map);//要返回的MAP
        Map<String, Object> authentication = liveInfoDao.authentication(map);//查看实名认证 返回info_id和IM_ID
        if (ObjectUtils.isEmpty(authentication)) {//是空的话说明 没有实名认证
            map.put("home_no",UUID.randomUUID().toString());
             liveInfoDao.SaveLive_home_info(map);
            authentication = liveInfoDao.authentication(map);
        }
        ////////根据经纬度换算出来
        String latitude = map.get("latitude").toString();//纬度
        String longitude = map.get("longitude").toString();//经度
        MapResultBean bean = BaiduMapUtils.getMsg(latitude + "," + longitude);
        String country_code_iso2 = bean.getResult().getAddressComponent().getCountry_code_iso2();
        String country_id = liveInfoDao.selectCountry_id(country_code_iso2);
        map.put("country_id", country_id);
        ////////根据经纬度换算出来
        liveInfoDao.updateEndBroadcast(map);//  <!--为了防止直播结束后腾讯回掉失败，当开启直播的时候，再次关闭以前的直播-->
        String imgUrl = FastDFSClient.uploadFile(img.getBytes(), "jpg", null);//图片服务器有问题
        map.put("live_img", imgUrl);//
        String anchor_no = liveInfoDao.findAnchor_no(map);//根據用戶ID查看主播编号
        map.put("anchor_no", anchor_no);//主播编号
        map.put("info_id", authentication.get("info_id"));//用户直播间信息ID主键/////////////////////////////////////////////////
        map.put("user_id", map.get("userId"));//创建人
        map.put("start_time", new Date());//直播开始时间
        //String livepPushUrl = Broadcastutils.getLivepPushUrl();//推流地址
        Map<String, String> livePlayUrl = Broadcastutils.getLivePlayUrl();//播放地址
        map.put("rtmp_url", livePlayUrl.get("rtmp_url"));
        map.put("flv_url", livePlayUrl.get("flv_url"));
        map.put("hls_url", livePlayUrl.get("hls_url"));
        if (map.get("pwd") != null && !map.get("pwd").toString().equals("")) {
            map.put("types", "3");
            map.put("type_id", 4);//4	私密
        }
        liveInfoDao.insertSelective(map);//插入表

        ////////查看这个人目前有没有聊天室 有的话就说明不是第一次开播了
        Object im_id = authentication.get("IM_ID");
        if (im_id == null) {
            /////////////IM创建聊天室开始
            Broadcast broadcast = new Broadcast();
           /* broadcast.setOwner_Account("robot");//機器人是群组*/
            broadcast.setName(map.get("info_name").toString());
            System.out.println(JsonUtils.objectToJson(broadcast));
            String BUSINESS = "v4/group_open_http_svc/create_group";//业务API
            String api = Imutils.BASISAPI + BUSINESS + Imutils.PUBLIC;//拼接URL
            String date = HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(broadcast));//发送请求
            Map<String, Object> result = JsonUtils.jsonToPojo(date, map.getClass());
            if (result.get("ActionStatus").toString().equalsIgnoreCase("FAIL")) {
                throw new RRException(date);
            }
            map.put("IM_ID", result.get("GroupId"));
            back.put("IM_ID", result.get("GroupId"));//返回的MAP
            /////////////IM创建聊天室结束
        } else {
            map.put("IM_ID", authentication.get("IM_ID"));
            back.put("IM_ID", im_id);//返回的MAP
        }
        ////////////////////////////////////////////////////
        back.put("stream_id", livePlayUrl.get("stream_id"));//腾讯的直播房间号
        map.put("stream_id", livePlayUrl.get("stream_id"));//腾讯的直播码(也就是直播间编号
        liveInfoDao.updateIM_ID(map);//更新互动聊天室
        /////返回的MAP
        back.put("flv_url", livePlayUrl.get("flv_url"));//拉
        back.put("rtmp_url", livePlayUrl.get("rtmp_url"));//拉
        back.put("hls_url", livePlayUrl.get("hls_url"));//拉
        back.put("livepPushUrl", livePlayUrl.get("finalUrl"));//退
        back.put("live_id", map.get("live_id"));//用户直播间信息ID主键
        back.put("info_id", authentication.get("info_id"));//用户直播间信息ID主键

        LivePushProduct.livePushProduct(Integer.parseInt(map.get("userId")+""));

        return back;
    }


    @Override
    public int endBroadcast(Map<String, Object> map) {
        liveInfoDao.finishBroadcast(map);
        return liveInfoDao.endBroadcast(map);
    }


    /**
     * @Author:YS
     * @Description: 查看正在直播 带分页    1全球2本国热门3最新4私密5生活6才艺7PK
     * @Date:2018/3/12
     * @param:
     */
    @Transactional
    @Override
    public List<Map<String, Object>> beingBroadcast(Map<String, Object> map) {
        List<Map<String, Object>> maps = liveInfoDao.beingBroadcast(map);
        for (Map<String, Object> stringObjectMap : maps) {
            map.put("account", stringObjectMap.get("user_id"));//主播的ID
            Map daoByAllId = imFriendDao.findByAllId(map);//查询关注主播没有
            if (ObjectUtils.isEmpty(daoByAllId)) {
                stringObjectMap.put("is_tutual", 0);//查询不出来 没有关注
            } else {
                stringObjectMap.put("is_tutual", 1);//查询出来就是关注了
            }

            String business_id = stringObjectMap.get("business_id").toString();
            if (!"".equals(business_id)) {
                map.put("live_challenge_id", business_id);//关联的PK外键
                List<Map<String, Object>> challenge = liveInfoDao.selectLive_challenge(map);//根据外键查询数据库 查询出来红队和蓝队的信息
                stringObjectMap.put("redUserId", challenge.get(0).get("userId"));//红队
                stringObjectMap.put("redNickName", challenge.get(0).get("nick_name"));//红队
                stringObjectMap.put("redSignature", challenge.get(0).get("signature"));//红队
                stringObjectMap.put("redImg", challenge.get(0).get("img"));//红队

                stringObjectMap.put("yellowUserId", challenge.get(1).get("userId"));//黄队
                stringObjectMap.put("yellowNickName", challenge.get(1).get("nick_name"));//黄队
                stringObjectMap.put("yellowSignature", challenge.get(1).get("signature"));//黄队
                stringObjectMap.put("yellowImg", challenge.get(1).get("img"));//黄队
            }
        }
        return maps;
    }


    @Override
    public int beingBroadcastTotal() {
        return liveInfoDao.beingBroadcastTotal();
    }

    /**
     * @Author:YS
     * @Description: 解散群的时候使用需要查询出来解散哪个群
     * @Date:2018/3/13
     * @param:
     */
    @Transactional
    @Override
    public String selectIm_ID(Map<String, Object> map) {
        return liveInfoDao.selectIm_ID(map);
    }

    /**
     * @Author:YS
     * @Description: 查询这场直播增加了多少粉丝 多少等 需要得到开播时间和结束开播时间 TODOYS
     * @Date:2018/3/13
     * @param:
     */

    @Override
    public Object finalLivePush(Map<String, Object> map) {
        Map<String, Object> selectLiveInfo = liveInfoDao.selectLive_info(map);
        map.put("stime", selectLiveInfo.get("start_time"));
        map.put("etime", selectLiveInfo.get("end_time"));
        int fansVideo = videoInfoDao.AddFansVideo(map);//增加的粉丝数量
        Integer addPrice = videoInfoDao.AddPrice(map);//这个直播的收入
        int selectNumber = liveInfoDao.selectNumber(map);//这是第几次直播？
        selectLiveInfo.put("fansVideo", fansVideo);
        selectLiveInfo.put("addPrice", addPrice == null ? 0 : addPrice);
        selectLiveInfo.put("selectNumber", selectNumber);
        return selectLiveInfo;
    }

    /**
     * @Author:YS
     * @Description: 查看这个群里面的禁言时间和人
     * @Date:2018/3/17
     * @param:
     */
    @Transactional
    @Override
    public List<Map<String, Object>> selectTimes(Map<String, Object> map) {
        return liveInfoDao.selectTimes(map);
    }

    /**
     * @Author:YS
     * @Description: 申请语音连麦
     * @Date:2018/3/20
     * @param:
     */
    @Transactional
    @Override
    public Map<String, Object> insertMicrophone(Map<String, Object> map) {
        map.put("types", "0");//0语音连麦1视频连麦交友',
        Map<String, Object> connectMicrophoneList = liveInfoDao.connectMicrophoneList(map);
        if (!ObjectUtils.isEmpty(connectMicrophoneList)) {
            return null;
        }
        Map<String, String> livePlayUrl = Broadcastutils.getLivePlayUrl();//播放地址
        map.put("rtmp_url", livePlayUrl.get("rtmp_url"));//拉一
        map.put("flv_url", livePlayUrl.get("flv_url"));//拉二
        map.put("hls_url", livePlayUrl.get("hls_url"));//拉三
        map.put("final_url", livePlayUrl.get("finalUrl"));//推流
        map.put("stream_id", livePlayUrl.get("stream_id"));//streamid
        liveInfoDao.insertSelectiveMicrophone(map);
        return liveInfoDao.applyMicrophoneList(map);
    }


    /**
     * @Author:YS
     * @Description: 申请视频连麦
     * @Date:2018/3/20
     * @param:
     */
    @Transactional
    @Override
    public Map<String, Object> videoMicrophone(Map<String, Object> map) {
        map.put("types", "1");//0语音连麦1视频连麦交友',
        Map<String, Object> connectMicrophoneList = liveInfoDao.connectMicrophoneList(map);
        if (!ObjectUtils.isEmpty(connectMicrophoneList)) {
            return null;
        }
        Map<String, String> livePlayUrl = Broadcastutils.getLivePlayUrl();//播放地址
        map.put("rtmp_url", livePlayUrl.get("rtmp_url"));//拉一
        map.put("flv_url", livePlayUrl.get("flv_url"));//拉二
        map.put("hls_url", livePlayUrl.get("hls_url"));//拉三
        map.put("final_url", livePlayUrl.get("finalUrl"));//推流
        map.put("stream_id", livePlayUrl.get("stream_id"));//streamid
        map.put("creat_time", new Date());
        liveInfoDao.insertSelectiveMicrophone(map);
        return liveInfoDao.applyMicrophoneList(map);
    }

    /**
     * @Author:YS
     * @Description: 申请连麦展示页面
     * @Date:2018/3/20
     * @param: #{anchor_id} 主播ID
     */
    @Transactional
    @Override
    public List<Map<String, Object>> selectMicrophoneList(Map<String, Object> map) {
        map.put("types", 0);//'0语音连麦1视频连麦交友',
        List<Map<String, Object>> list = liveInfoDao.selectMicrophoneList(map);//申请连麦展示页面
        return list;
    }

    @Transactional
    @Override
    public List<Map<String, Object>> selectvideoMicrophone(Map<String, Object> map) {
        map.put("types", 1);//'0语音连麦1视频连麦交友',
        List<Map<String, Object>> list = liveInfoDao.selectMicrophoneList(map);//申请连麦展示页面
        for (Map<String, Object> stringObjectMap : list) {
            map.put("account", stringObjectMap.get("user_id"));
            Map<String, Object> personalLianmaiListAttention = liveInfoDao.personalLianmaiListAttention(map);//增加是否关注
            if (personalLianmaiListAttention == null) {
                stringObjectMap.put("is_tutual", "0");
            } else {
                stringObjectMap.put("is_tutual", personalLianmaiListAttention.get("is_tutual"));
            }
        }
        return list;
    }

    /**
     * @Author:YS
     * @Description: 主播同意连麦
     * @Date:2018/3/20
     * @param:
     */
    @Transactional
    @Override
    public String endMicrophoneList(String json, String format, String stream_id, String live_id) {
        //1.修改状态为连麦中
        liveInfoDao.endMicrophoneList(stream_id);
        //2.插入直播表这个语音连麦表的主键 加以关联
        int microphone_id = liveInfoDao.findMicrophone_id(stream_id);
        //3.更新直播表的类型为语音连麦类型
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("live_id", live_id);
        parameter.put("types", "2");//0普通 1.PK   2语音连麦
        parameter.put("business_id", microphone_id);//业务ID 可能是PK的主键 视频语音主键根据types决定',
        liveInfoDao.updateLive_infoBusiness_id(parameter);
        String date = HttpClientUtil.doPostJson(format, json);
        System.out.println("date腾讯返回的数据为-----------------" + date);
        Map map = JsonUtils.jsonToPojo(date, parameter.getClass());
        if (!map.get("code").toString().equalsIgnoreCase("0")) {
            throw new RRException(date);//发送请求失败就扔出异常 事务不会提交
        }
        return date;
    }

    /**
     * @Author:YS
     * @Description: 连麦结束后删除这个人的申请在连麦申请表
     * @Date:2018/3/21
     * @param:
     */
    @Transactional
    @Override
    public int finishMicrophoneList(int audience_id) {
        return liveInfoDao.finishMicrophoneList(audience_id);
    }

    /**
     * @Author:YS
     * @Description: 申请连麦遭到拒绝调取接口
     * @Date:2018/3/21
     * @param: #{stream_id}
     */
    @Transactional
    @Override
    public int refuseMicrophoneList(Map<String, Object> map) {
        return liveInfoDao.refuseMicrophoneList(map);
    }

    /**
     * @Author:YS
     * @Description: <!--主播邀请观众连麦-->
     * @Date:2018/3/22
     * @param:
     */
    @Transactional
    @Override
    public Map<String, String> anchorApplicationMicrophoneList(Map<String, Object> map) {
        Map<String, Object> connectMicrophoneList = liveInfoDao.connectMicrophoneList(map);//如果申请过了就返回NULL
        if (!ObjectUtils.isEmpty(connectMicrophoneList)) {
            return null;
        }
        Map<String, String> livePlayUrl = Broadcastutils.getLivePlayUrl();//播放地址
        map.put("rtmp_url", livePlayUrl.get("rtmp_url"));//拉一
        map.put("flv_url", livePlayUrl.get("flv_url"));//拉二
        map.put("hls_url", livePlayUrl.get("hls_url"));//拉三
        map.put("final_url", livePlayUrl.get("finalUrl"));//推流
        map.put("stream_id", livePlayUrl.get("stream_id"));//streamid
        liveInfoDao.insertSelectiveMicrophone(map);
        return livePlayUrl;
    }

    /**
     * @Author:YS
     * @Description: <!--1V1表等待匹配表的插入--> 不要事务
     * @Date:2018/3/22
     * @param:
     */
  /*  @Override
    public Object insertSelectiveLiveMatching(Map<String, Object> map)throws Exception {
        redisUtils.listSet("live_matching",JsonUtils.objectToJson(map),300);//存到缓存里面 1分
        Map<String, Object> objectHashMap = new HashMap<>();
        objectHashMap=map;
        map.put("create_time",new Date());
        liveInfoDao.delMatching(map);
        Map<String, String> alivePlayUrl = Broadcastutils.getLivePlayUrl();
        map.put("rtmp_url",alivePlayUrl.get("rtmp_url"));//拉一
        map.put("flv_url",alivePlayUrl.get("flv_url"));//拉二
        map.put("hls_url",alivePlayUrl.get("hls_url"));//拉三
        map.put("final_url",alivePlayUrl.get("finalUrl"));//推流
        map.put("stream_id",alivePlayUrl.get("stream_id"));//streamid
        liveInfoDao.insertSelectiveLiveMatching(map);//维护数据到数据库 live_matching
        ///准备开始匹配了 开始标识
        Map<String, Object> yuser_id = new HashMap<String, Object>();
        for ( int i=0;i <50 ; i++) {
            List list = redisUtils.lrange("live_matching", 0, 100);
            if (list!=null && !list.isEmpty()){//list不能为空
                for(Object str : list) {

                    yuser_id = JsonUtils.jsonToPojo(str.toString(), map.getClass());
                    //不能匹配到自己 然后再匹配别人
                    if (!yuser_id.get("user_id").toString().equalsIgnoreCase(map.get("user_id").toString())&&yuser_id.get("type_id").toString().equalsIgnoreCase(map.get("type_id").toString())){
                        redisUtils.remove("live_matching",1L,str.toString());
                        redisUtils.remove("live_matching",1L,JsonUtils.objectToJson(objectHashMap));
                        map.put("juser_id",map.get("user_id"));
                        map.put("yuser_id",yuser_id.get("user_id"));
                        String finish_id = intervalLiveMatchingFinish(map);//匹配結束 插入表 live_matching_finish
                        Map<String, Object> playUrl = liveInfoDao.playUrl(map);//查找出来匹配到那个人的推拉流
                        Map<String, Object> orAttention = liveInfoDao.OrAttention(map);//是否關注
                        if (orAttention==null){
                            playUrl.put("is_tutual","0");
                        }else {
                            playUrl.put("is_tutual",orAttention.get("is_tutual"));
                        }
                        //匹配到的人的為B 自己的為A
                        playUrl.put("finish_id",finish_id);
                        playUrl.put("artmp_url",map.get("rtmp_url"));
                        playUrl.put("aflv_url",map.get("flv_url"));
                        playUrl.put("ahls_url",map.get("hls_url"));
                        playUrl.put("afinal_url",map.get("final_url"));
                        playUrl.put("astream_id",map.get("stream_id"));
                      redisUtils.remove("live_matching",1L,str.toString());
                        return playUrl;

                    }


                }
            }
            Thread.sleep(2000);// 睡眠2000毫秒
        }
        return "";
    }*/
    @Override
    public Object insertSelectiveLiveMatching(Map<String, Object> map) throws Exception {
        Map<String, String> alivePlayUrl = Broadcastutils.getLivePlayUrl();
        alivePlayUrl.put("nick_name", map.get("nick_name").toString());
        alivePlayUrl.put("img", map.get("img").toString());
        alivePlayUrl.put("user_id", map.get("user_id").toString());
        redisUtils.set("1V1_" + map.get("user_id"), JsonUtils.objectToJson(alivePlayUrl), 300L);//保存自己的推拉流
        redisUtils.listSet("1V1_live_matching", JsonUtils.objectToJson(map), 300L);
        ///准备开始匹配了 开始标识
        Map<String, Object> yuser_id = new HashMap<String, Object>();
        for (int i = 0; i < 5; i++) {
            List list = redisUtils.lrange("1V1_live_matching", 0, 100);
            if (list != null && !list.isEmpty()) {//list不能为空
                for (Object str : list) {
                    yuser_id = JsonUtils.jsonToPojo(str.toString(), map.getClass());
                    //不能匹配到自己 然后再匹配别人
                    if (!yuser_id.get("user_id").toString().equalsIgnoreCase(map.get("user_id").toString()) && yuser_id.get("type_id").toString().equalsIgnoreCase(map.get("type_id").toString())) {
                        redisUtils.remove("1V1_live_matching", 1L, JsonUtils.objectToJson(yuser_id));//从缓存里面删除匹配到的人
                        redisUtils.remove("1V1_live_matching", 1L, JsonUtils.objectToJson(map));//从缓存里面删除自己  防止别人再次匹配到自己
                        map.put("juser_id", map.get("user_id"));
                        map.put("yuser_id", yuser_id.get("user_id"));
                        LiveMatchingProduct.intervalLiveMatchingFinish(map);//MQ 异步处理
                        //   String finish_id = intervalLiveMatchingFinish(map);//匹配結束 插入表 live_matching_finish
                        String userId = redisUtils.get("1V1_" + map.get("user_id"));//自己推拉流
                        String account = redisUtils.get("1V1_" + yuser_id.get("user_id"));//匹配到人的推拉流
                        Map<String, Object> jpojo = JsonUtils.jsonToPojo(userId, map.getClass());//自己的推拉流
                        Map<String, Object> ypojo = JsonUtils.jsonToPojo(account, map.getClass());//匹配到人的推拉流
                        jpojo.put("brtmp_url", ypojo.get("rtmp_url"));
                        jpojo.put("bflv_url", ypojo.get("flv_url"));
                        jpojo.put("bhls_url", ypojo.get("hls_url"));
                        jpojo.put("bfinal_url", ypojo.get("finalUrl"));
                        jpojo.put("bstream_id", ypojo.get("stream_id"));
                        jpojo.put("nick_name", ypojo.get("nick_name"));
                        jpojo.put("img", ypojo.get("img"));
                        jpojo.put("user_id", ypojo.get("user_id"));


                        jpojo.put("artmp_url", jpojo.get("rtmp_url"));
                        jpojo.put("aflv_url", jpojo.get("flv_url"));
                        jpojo.put("ahls_url", jpojo.get("hls_url"));
                        jpojo.put("afinal_url", jpojo.get("finalUrl"));
                        jpojo.put("astream_id", jpojo.get("stream_id"));

                        Map<String, Object> orAttention = liveInfoDao.OrAttention(map);//是否關注
                        if (orAttention == null) {
                            jpojo.put("is_tutual", "0");
                        } else {
                            jpojo.put("is_tutual", orAttention.get("is_tutual"));
                        }
                        return jpojo;

                    }
                    Thread.sleep(1000);// 睡眠2000毫秒
                }
            }

        }
        redisUtils.remove("1V1_live_matching", 1L, JsonUtils.objectToJson(map));//从缓存里面删除自己  防止别人再次匹配到自己
        return "";
    }

    /**
     * @Author:YS
     * @Description: 查询1v1类型表
     * @Date:2018/3/22
     * @param:
     */
    @Transactional
    @Override
    public List<Map<String, Object>> selectLiveMatchingType() {
        return liveInfoDao.selectLiveMatchingType();
    }

    /**
     * @Author:YS
     * @Description:<!--插入1V1匹配中表-->
     * @Date:2018/3/23
     * @param:No such property: code for class: Script1
     */
    @Transactional
    @Override
    public String intervalLiveMatchingFinish(Map<String, Object> map) {
        map.put("update_time", new Date());
        Map<String, Object> historyMatching = liveInfoDao.historyMatching(map);
        if (!ObjectUtils.isEmpty(historyMatching)) {//如果匹配过了 就在匹配次数上面加一 并且更新匹配时间
            historyMatching.put("update_time", new Date());
            liveInfoDao.updateNum(historyMatching);
            liveInfoDao.finishMatching(map);//<!--匹配成功后结束匹配-->
            return historyMatching.get("finish_id").toString();
        } else {
            liveInfoDao.intervalLiveMatchingFinish(map);// <!--插入1V1匹配中表-->
            liveInfoDao.finishMatching(map);//<!--匹配成功后结束匹配-->
            return map.get("finish_id").toString();

        }
    }

    /**
     * @Author:YS
     * @Description: 历史1V1记录 TODOYS
     * @Date:2018/3/23 传递userId
     * @param:
     */
    @Transactional
    @Override
    public Map<String, Object> allFinish(Map<String, Object> map) {
        Map<String, Object> stringObjectMap = liveInfoDao.allFinish(map);//查询1V1结束表
        map.put("stime", stringObjectMap.get("update_time"));//查询礼物用
        map.put("etime", stringObjectMap.get("end_time"));//查询礼物用
        map.put("finish_id", stringObjectMap.get("finish_id"));//查询礼物用
        String juser_id = stringObjectMap.get("juser_id").toString();//等待对比甲方还是乙方
        String yuser_id = stringObjectMap.get("yuser_id").toString();//等待对比甲方还是乙方
        if (map.get("userId").toString().equalsIgnoreCase(juser_id)) {
            map.put("userId", yuser_id);//如果相同 就查对方的头像ID和昵称
            Map<String, Object> stringObjectMap1 = liveInfoDao.selectAnchor(map);
            Map<String, Object> ztotalgift = liveInfoDao.ztotalgift(map);//赠送的礼物
            Map<String, Object> stotalgift = liveInfoDao.stotalgift(map);//收到的礼物
            //stringObjectMap1.put("create_time",stringObjectMap.get("create_time"));
            stringObjectMap1.put("end_time", stringObjectMap.get("end_time"));
            stringObjectMap1.put("update_time", stringObjectMap.get("update_time"));
            stringObjectMap1.put("ztotalgift", ztotalgift == null ? 0 : ztotalgift);
            stringObjectMap1.put("stotalgift", stotalgift == null ? 0 : stotalgift);
            return stringObjectMap1;
        } else if (map.get("userId").toString().equalsIgnoreCase(yuser_id)) {
            map.put("userId", juser_id);//如果相同 就查对方的头像ID和昵称
            Map<String, Object> stringObjectMap2 = liveInfoDao.selectAnchor(map);
            Map<String, Object> ztotalgift = liveInfoDao.ztotalgift(map);//赠送的礼物
            Map<String, Object> stotalgift = liveInfoDao.stotalgift(map);//收到的礼物
            //stringObjectMap2.put("create_time",stringObjectMap.get("create_time"));
            stringObjectMap2.put("end_time", stringObjectMap.get("end_time"));
            stringObjectMap2.put("update_time", stringObjectMap.get("update_time"));
            stringObjectMap2.put("ztotalgift", ztotalgift == null ? 0 : ztotalgift);
            stringObjectMap2.put("stotalgift", stotalgift == null ? 0 : stotalgift);
            return stringObjectMap2;
        }
        return null;
    }

    /**
     * @Author:YS
     * @Description://返回1V1还剩下多少时间单位秒
     * @Date:2018/3/28
     * @param: #{finish_id}
     */
    @Transactional
    @Override
    public Map<String, Object> RemainingTime(Map<String, Object> map) {
        return liveInfoDao.RemainingTime(map);
    }

    /**
     * @Author:YS
     * @Description: //Pk的视频批量插入
     * @Date:2018/3/28
     * @param:No such property: code for class: Script1
     */
    @Transactional
    @Override
    public Object insertSelectiveLive_challenge(Map<String, Object> map) {
        //1.把直播的状态修改为PK
        map.put("types", 1);//0普通1PK2私密3交友',
        map.put("creat_time", new Date());//0普通1PK2私密3交友',
        liveInfoDao.broadcastState(map);
        //2.返回推拉流地址以及腾讯编号  等待混流 以及取消混流
        liveInfoDao.insertSelectiveLive_challenge(map);//插入PK表
        Map<String, String> livePlayUrl = Broadcastutils.getLivePlayUrl();
        livePlayUrl.put("live_challenge_id", map.get("live_challenge_id").toString());
        return livePlayUrl;
    }

    /**
     * @Author:YS
     * @Description: PK挑战结束
     * @Date:2018/3/28
     * @param:No such property: code for class: Script1
     */
    @Transactional
    @Override
    public Object stateChallenge(Map<String, Object> map, String json, String format) {
        //1.修改PK的状态为PK结束了
        map.put("types", "2");//'0等待PK中 1PK进行时2PK结束',
        liveInfoDao.stateChallenge(map);//TODOYS
        String date = HttpClientUtil.doPostJson(format, json);
        System.out.println("date腾讯返回的数据为-----------------" + date);
        Map result = JsonUtils.jsonToPojo(date, map.getClass());
        if (!result.get("code").toString().equalsIgnoreCase("0")) {
            throw new RRException(date);//发送请求失败就扔出异常 事务不会提交
        }
        return date;
    }

    /**
     * @Author:YS
     * @Description: PK开始  开始混流
     * @Date:2018/3/28
     * @param: input_stream_id 主播的流
     * input_stream_id_two PK观众的流
     * mix_stream_session_id 不需要传递 我自己随机生成流主键 取消的时候需要传递
     * #{live_challenge_id} PK主键
     * live_id 用户直播主键ID 发起直播的时候给你返回去的现在传递过来
     */
    @Transactional
    @Override
    public String beginChallenge(Map<String, Object> map, String json, String format) {
        //1.修改状态为PK状态
        map.put("types", "1");//0等待PK中 1PK进行时2PK结束',
        liveInfoDao.stateChallenge(map);
        //2.关联直播表并且把直播表的类型修改为PK
        map.put("business_id", map.get("live_challenge_id"));
        map.put("types", 1);//'0普通 1.PK  2 交友 3语音连麦 4视频连麦',
        liveInfoDao.updateLive_infoBusiness_id(map);
        // 3.混流进行  并且把取消混流的ID返回回去
        String date = HttpClientUtil.doPostJson(format, json);
        System.out.println("date腾讯返回的数据为-----------------" + date);
        Map result = JsonUtils.jsonToPojo(date, map.getClass());
        if (!result.get("code").toString().equalsIgnoreCase("0")) {
            throw new RRException(date);//发送请求失败就扔出异常 事务不会提交
        }
        return date;
    }

    /**
     * @Author:YS
     * @Description:<!--查看连麦的开关状态-->
     * @Date:2018/3/29
     * @param: #{live_id}
     */
    @Transactional
    @Override
    public Map<String, Object> microphone_switch(Map<String, Object> map) {
        return liveInfoDao.microphone_switch(map);
    }

    /**
     * @Author:YS
     * @Description:修改连麦开关的状态
     * @Date:2018/3/29
     * @param: #{microphone_switch} #{live_id}
     */
    @Transactional
    @Override
    public int stateMicrophone_switch(Map<String, Object> map) {
        return liveInfoDao.stateMicrophone_switch(map);
    }


    /**
     * @Author:YS
     * @Description: <!--查看全局禁言的开关状态-->
     * @Date:2018/3/30
     * @param: live_id
     */
    @Transactional
    @Override
    public Map<String, Object> prohibit_speaking(Map<String, Object> map) {
        return liveInfoDao.prohibit_speaking(map);
    }

    /**
     * @Author:YS
     * @Description: 修改全局禁言的状态
     * @Date:2018/3/30
     * @param: live_id   prohibit_speaking
     */
    @Transactional
    @Override
    public int stateProhibit_speaking(Map<String, Object> map) {
        return liveInfoDao.stateProhibit_speaking(map);
    }


    /**
     * @Author:YS
     * @Description: <!-- 批量删除视频连麦的 -->
     * @Date:2018/3/29
     * @param:No such property: code for class: Script1
     */
    @Override
    public void deleteAll(String[] stream_id) {
        liveInfoDao.deleteAll(stream_id);
    }


    /**
     * @Author:YS
     * @Description: 查询直播的分类 TODO缓存
     * @Date:2018/4/8
     * @param:
     */
    @Override
    public List<Map<String, Object>> selectLive_type() {
        return liveInfoDao.selectLive_type();
    }

    /**
     * @Author:YS
     * @Description: <!--直播分类页面的返回-->
     * @Date:2018/4/8
     * @param:
     */
    @Override
    public Object selectLive_typeAll() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            //从redis中取缓存数据
            String json = redisUtils.get(RedisKeys.LIVE_TYPE);
            if (!org.apache.commons.lang.StringUtils.isBlank(json)) {
                //把json转换成List
                List toList = JsonUtils.jsonToList(json, map.getClass());
                return toList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //执行查询
        List<Map<String, Object>> typeAll = liveInfoDao.selectLive_typeAll();
        //返回结果之前，向缓存中添加数据
        try {
            //为了规范key可以使用hash
            //定义一个保存内容的key，hash中每个项就是cid
            //value是list，需要把list转换成json数据。
            redisUtils.set(RedisKeys.LIVE_TYPE, JsonUtils.objectToJson(typeAll));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeAll;
    }

    /**
     * 查询本国热门视频
     *
     * @param offset    开始查询数量
     * @param limit     每页显示信息数
     * @param countryId 当前国家
     * @param state     状态, 1查询本国热门, 2查询国际热门
     * @return
     */
    @Override
    public List<Object> findDomesticHotLive(Integer offset, Integer limit, Integer countryId, Integer state) {
        return liveInfoDao.findDomesticHotLive(offset, limit, countryId, state);
    }

    /**
     * 根据路径查询直播是否正在继续
     *
     * @param liveUrl 直播路径
     * @return
     */
    @Override
    public List<Map<String, Object>> findLiveByLiveUrl(String liveUrl) {
        return liveInfoDao.findLiveByLiveUrl(liveUrl);
    }

    /**
     * @author: YS
     * @Date:2018/4/16 19:55
     * @param:
     * @explain： <!--查看某个群的 连麦开关0关闭1开启' 全局禁言开关0关闭1开启' 以及在这个群的状态 是否禁言-->
     * @return:
     */
    @Override
    public Map<String, Object> imSwitch(Map<String, Object> map) {
        Map<String, Object> imSwitch = liveInfoDao.imSwitch(map);
        map.put("IM_ID", imSwitch.get("IM_ID"));
        Map<String, Object> banIm = liveInfoDao.banIm(map);
        if (banIm == null) {
            imSwitch.put("info_ban", "");
        } else {
            imSwitch.put("info_ban", banIm.get("times"));
        }
        return imSwitch;
    }

    /**
     * @author: YS
     * @Date:2018/4/20 9:45
     * @param:
     * @explain： 对1V1表的CRUD
     * @return:
     */
    @Override
    public List<Map<String, Object>> selectLive_v_set(Map<String, Object> map) {
        return liveInfoDao.selectLive_v_set(map);
    }

    @Override
    public int insertLive_v_set(Integer userId) {
        return liveInfoDao.insertLive_v_set(userId);
    }

    @Override
    public int updateLive_v_set(Map<String, Object> map) {
        return liveInfoDao.updateLive_v_set(map);
    }

    /**
     * @author: YS
     * @Date:2018/4/20 10:32
     * @param:
     * @explain： <!--查看直播最后一次的直播间名字以及背景图-->
     * @return:
     */
    @Override
    public Map<String, Object> selectMyLive(Map<String, Object> map) {
        Map<String, Object> live = liveInfoDao.selectMyLive(map);
        if (live != null && live.get("info_name") == null) {
            live.put("info_name", live.get("img"));
        }
        return liveInfoDao.selectMyLive(map);
    }

    /**
     * @author: YS
     * @Date:2018/4/23 14:31
     * @param:
     * @explain： 查看主播是否實名認證
     * @return:
     */
    @Override
    public Object getAuthentication(Map<String, Object> map) {
        Map<String, Object> authentication = liveInfoDao.authentication(map);//查看实名认证 返回info_id和IM_ID
        if (ObjectUtils.isEmpty(authentication)) {//是空的话说明 没有实名认证
            return null;
        }
        return authentication;
    }


}
