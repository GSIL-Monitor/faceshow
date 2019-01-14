package com.faceshow.modules.live.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.*;
import com.faceshow.modules.Broadcast.BroadcastApiutils;
import com.faceshow.modules.live.service.LiveInfoServicve;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.user.entity.UserInfo;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 直播操作Controller<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.live.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/3 8:34
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/live")
public class LiveInfoController extends AbstractController {
    @Autowired
    private  RedisUtils redisUtils;
    @Autowired
    private LiveInfoServicve liveInfoServicve;

    /**
     * 查询正在直播的直播列表
     *
     * @param currPage 当前页, 默认值为1
     * @param pageSize 每页显示信息数 默认值为10
     * @param type     查询类型, 1全球热门, 2本地热门, 3最新开播
     * @param country  当前国家
     * @return
     */
    @SysLog
    @PostMapping("/findLiveList")
    public Object findLiveList(@RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer type, String country) {
        return liveInfoServicve.findLiveList(currPage, pageSize, type, country);
    }


    /**
     * @Author:YS
     * @Description: 开启直播  传递用户ID 直播间名字 直播间图片 地理位置 返回推流地址（推流地址默认24小时是有效的）
     * @Date:2018/3/10
     * @param:No such property: code for class: Script1
     */
    @SysLog
    @PostMapping("/getLivePush")
    public Object getLivePush(@RequestParam Map<String, Object> map, MultipartFile img) throws Exception {
        UserInfo userInfo = getUserInfo();
        String mobile = userInfo.getMobile();
        if (StringUtils.isBlank(mobile)){
            return R.result(0, "您还没有绑定手机号，请绑定手机号", "fail");
        }
        Map<String, Object> insertSelective = liveInfoServicve.insertSelective(map, img);
        if (insertSelective == null) {
            return R.result(0, "您还没有绑定手机号，请绑定手机号", "fail");
        }
        return R.result(1, "OK", insertSelective);
    }


    /**
     * @author: YS
     * @Date:2018/4/23 14:29
     * @param:
     * @explain： 判断主播是否实名认证
     * @return:
     */
    @SysLog
    @PostMapping("/getAuthentication")
    public Object getAuthentication(@RequestParam Map<String, Object> map) throws Exception {
        Object authentication = liveInfoServicve.getAuthentication(map);
        if (authentication == null) {
            return R.result(0, "您还没有实名认证，不能开启直播", "fail");
        }
        return R.result(1, "您已经实名认证了，可以开始直播", authentication);
    }

    /**
     * @author: YS
     * @Date:2018/4/23 13:11
     * @param:
     * @explain： 极光推送  带的参数就是live_id
     * @return:
     */
    @SysLog
    @PostMapping("/beingBroadcastJGTS")
    public Object beingBroadcastJGTS(Integer live_id) throws Exception {
      try {
          //从redis中取缓存数据
          String json = redisUtils.get("JGTS:"+live_id);
          redisUtils.delete("JGTS:"+live_id);
          return R.result(1, "OK", json==null?"":json);
      }catch (Exception e){
          return R.result(0, "NO", e);
      }
    }




    /**
     * @Author:YS
     * @Description:查看正在直播 带分页
     * @Date:2018/3/12
     * @param:No such property: code for class: Script1
     */
    @SysLog
    @PostMapping("/beingBroadcast")
    public Object beingBroadcast(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Map<String, Object>> infoDetail = liveInfoServicve.beingBroadcast(query);
        int total = liveInfoServicve.beingBroadcastTotal();
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }


    /**
     * @Author:YS
     * @Description: 直播结束以后的页面 包括收入了多少 增加了多少粉丝等
     * @Date:2018/3/13
     * @param:No such property: code for class: Script1
     */
    @SysLog
    @PostMapping("/finalLivePush")
    public Object finalLivePush(@RequestParam Map<String, Object> map) {
        Object insertSelective = liveInfoServicve.finalLivePush(map);
        //  return  R.result(1,"OK",insertSelective);
        return R.ok("OK").put("result", insertSelective);
    }

    /**
     * @Author:YS
     * @Description: 查询禁言
     * @Date:2018/3/17
     * @param:
     */
    @SysLog
    @PostMapping("/selectTimes")
    public Object selectTimes(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> maps = liveInfoServicve.selectTimes(map);
        return R.result(1, "OK", maps);
    }

    /**
     * @Author:YS
     * @Description: 申请语音连麦
     * @Date:2018/3/20
     * @param: #{anchor_id},#{audience_id}
     */
    @SysLog
    @PostMapping("/insertMicrophone")
    public Object insertMicrophone(@RequestParam Map<String, Object> map) {
        map.put("creat_time", new Date());//创建时间k
        Map<String, Object> stringObjectMap = liveInfoServicve.insertMicrophone(map);
        if (stringObjectMap == null) {
            return R.result(0, "您已经申请过连麦了，请等待", "");
        }
        return R.result(1, "OK", stringObjectMap);
    }

    /**
     * @Author:YS
     * @Description: 申请语音连麦展示页面娗
     * @Date:2018/3/20
     * @param: #{anchor_id} 主播ID
     */
    @SysLog
    @PostMapping("/selectMicrophoneList")
    public Object selectMicrophoneList(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> maps = liveInfoServicve.selectMicrophoneList(map);
        return R.result(1, "OK", maps);
    }
    /**
     * @Author:YS
     * @Description: 申请连麦展示页面
     * @Date:2018/3/29
     * @param: #{anchor_id} 主播ID
     */
    @SysLog
    @PostMapping("/selectvideoMicrophone")
    public Object selectvideoMicrophone(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> maps = liveInfoServicve.selectvideoMicrophone(map);
        return R.result(1, "OK", maps);
    }
    /**
     * @Author:YS
     * @Description: 连麦开始
     * @Date:2018/3/20
     * @param: #{stream_id} 腾讯推流的业务ID
     */
    @SysLog
    @PostMapping("/common_access")
    public Object common_access(String output_stream_id, String input_stream_id,String live_id) {
        String app_id = BroadcastApiutils.APPID;
        String URL = "http://fcgi.video.qcloud.com/common_access?t=%s&appid=%s&sign=%s&interface=%s";//业务API
        String format = String.format(URL, BroadcastApiutils.getTime(), app_id, BroadcastApiutils.getSign(), "Mix_StreamV2");
        String json = "{\n" +
                "    \"timestamp\": " + System.currentTimeMillis() / 1000 + ",\n" +
                "    \"eventId\": " + System.currentTimeMillis() / 1000 + ",\n" +
                "    \"interface\": {\n" +
                "        \"interfaceName\": \"Mix_StreamV2\",\n" +
                "        \"para\": {\n" +
                "            \"app_id\": " + app_id + ",\n" +
                "            \"interface\": \"mix_streamv2.start_mix_stream_advanced\",\n" +
                "            \"mix_stream_session_id\": \"" + output_stream_id + "\",\n" +
                "            \"output_stream_id\": \"" + output_stream_id + "\",\n" +
                "            \"output_stream_type\": 0,\n" +
                "            \"input_stream_list\": [\n" +
                "                {\n" +
                "                    \"input_stream_id\": \"" + output_stream_id + "\",\n" +
                "                    \"layout_params\": {\n" +
                "                        \"image_layer\": 1\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"input_stream_id\": \"" + input_stream_id + "\",\n" +
                "                    \"layout_params\": {\n" +
                "                        \"image_layer\": 2,\n" +
                "                        \"image_width\": 200,\n" +
                "                        \"image_height\": 200,\n" +
                "                        \"location_x\": 100,\n" +
                "                        \"location_y\": 100\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
        System.out.println("json--------------" + json);
        System.out.println("format-----------------" + format);
        liveInfoServicve.endMicrophoneList(json, format, input_stream_id ,live_id);
        return R.result(1, "OK", "");
    }

    /**
     * @Author:YS
     * @Description: 连麦结束后删除这个人的申请在连麦申请表
     * @Date:2018/3/21
     * @param:
     */
    @SysLog
    @PostMapping("/finishMicrophoneList")
    public Object finishMicrophoneList(int audience_id) {
        int i = liveInfoServicve.finishMicrophoneList(audience_id);
        if (i == 0) {
            return R.result(0, "NO", i);
        }
        return R.result(1, "OK", i);
    }

    /**
     * @Author:YS
     * @Description: 申请连麦遭到拒绝调取接口
     * @Date:2018/3/21
     * @param: #{stream_id}
     */
    @SysLog
    @PostMapping("/refuseMicrophoneList")
    public Object refuseMicrophoneList(@RequestParam Map<String, Object> map) {
        int i = liveInfoServicve.refuseMicrophoneList(map);
        return R.result(1, "OK", i);
    }

    /**
     * @Author:YS
     * @Description: <!--主播邀请观众连麦-->
     * @Date:2018/3/22
     * @param:
     */
    @SysLog
    @PostMapping("/anchorApplicationMicrophoneList")
    public Object anchorApplicationMicrophoneList(@RequestParam Map<String, Object> map) {
        map.put("creat_time", new Date());//创建时间k
        Map<String, String> stringObjectMap = liveInfoServicve.anchorApplicationMicrophoneList(map);
        if (stringObjectMap == null) {
            return R.result(0, "您已经申请过连麦了，请等待", "");
        }
        return R.result(1, "OK", stringObjectMap);
    }


    /**
     * @Author:YS
     * @Description: <!--1V1表等待匹配表的插入-->
     * @Date:2018/3/22
     * @param:
     */
    @SysLog
    @RequestMapping("/insertSelectiveLiveMatching")
    public Object insertSelectiveLiveMatching(@RequestParam Map<String,Object>map )throws Exception  {
        map.put("create_time",new Date());
        Object o = liveInfoServicve.insertSelectiveLiveMatching(map);
        if (o.toString().equals("")){
            return R.result(0,"对不起，您匹配失败，请稍后重试", o);
        }
        return R.result(1,"OK", o);
    }

    /**
     * @Author:YS
     * @Description: 查询1v1类型表 YSTODO 加缓存
     * @Date:2018/3/22
     * @param:
     */
    @SysLog
    @PostMapping("/selectLiveMatchingType")
    public Object selectLiveMatchingType() {
        List<Map<String, Object>> matchingType = liveInfoServicve.selectLiveMatchingType();
        return R.result(1, "OK", matchingType);
    }

    /**
     *@Author:YS
     *@Description:1V1历史记录
     *@Date:2018/3/23
     *@param:No such property: code for class: Script1
     */
    @SysLog
    @PostMapping("/allFinish")
    public Object allFinish(@RequestParam Map<String, Object> map) {
        Map<String, Object> allFinish = liveInfoServicve.allFinish(map);
        return R.result(1, "OK", allFinish);
    }

    /**
     *@Author:YS
     *@Description://返回1V1还剩下多少时间单位秒
     *@Date:2018/3/28
     *@param: #{finish_id}
     */
    @SysLog
    @PostMapping("/RemainingTime")
    public Object RemainingTime(@RequestParam Map<String, Object> map) {
        Map<String, Object> insertSelective = liveInfoServicve.RemainingTime(map);
        return R.result(1, "OK", insertSelective);
    }

    /**
     *@Author:YS
     *@Description:   //Pk的视频批量插入
     *@Date:2018/3/28
     *@param:No such property: code for class: Script1
     */
    @SysLog
    @PostMapping("/insertSelectiveLive_challenge")
    public Object insertSelectiveLive_challenge(@RequestParam Map<String, Object> map) {
       Object insertSelective = liveInfoServicve.insertSelectiveLive_challenge(map);
        return R.result(1, "OK", insertSelective);
    }




    /**
     *@Author:YS
     *@Description: PK开始  开始混流
     *@Date:2018/3/28
     *@param:
     * input_stream_id 主播的流
     * input_stream_id_two PK观众的流
     * mix_stream_session_id 不需要传递 我自己随机生成流主键 取消的时候需要传递
     * #{live_challenge_id} PK主键
     * live_id 用户直播主键ID 发起直播的时候给你返回去的现在传递过来
     */
    @SysLog
    @PostMapping("/startChallenge")
    public Object startChallenge(@RequestParam Map<String, Object> map) {
         String huabu = InvertCodeGenerator.genCodes(10, 1).get(0);//生成10位的随机数字 到时候取消混流还要使用呢
        //  String mix_stream_session_id = "5000_enson";//生成10位的随机数字 到时候取消混流还要使用呢
        String input_stream_id=map.get("input_stream_id") .toString();//混流使用 需要传递
        String input_stream_id_two=map.get("input_stream_id_two") .toString();//混流使用 需要传递
        String app_id = BroadcastApiutils.APPID;
        String URL = "http://fcgi.video.qcloud.com/common_access?t=%s&appid=%s&sign=%s&interface=%s";//业务API
        String format = String.format(URL, BroadcastApiutils.getTime(), app_id, BroadcastApiutils.getSign(), "Mix_StreamV2");
        System.out.println("format后的URL为--------"+format);
        String json="{\n" +
                "    \"timestamp\": "+System.currentTimeMillis() / 1000+",\n" +
                "    \"eventId\": "+System.currentTimeMillis() / 1000+",\n" +
                "    \"interface\": {\n" +
                "        \"interfaceName\": \"Mix_StreamV2\",\n" +
                "        \"para\": {\n" +
                "            \"app_id\": "+app_id+",\n" +
                "            \"interface\": \"mix_streamv2.start_mix_stream_advanced\",\n" +
                "            \"mix_stream_session_id\": \""+input_stream_id+"\",\n" +
                "            \"output_stream_id\": \""+input_stream_id+"\",\n" +
                "            \"output_stream_type\": 0,\n" +
                "            \"mix_stream_template_id\": 390,\n" +
                "            \"input_stream_list\": [\n" +
                "                {\n" +
                "                    \"input_stream_id\": \""+huabu+"\",\n" +
                "                    \"layout_params\": {\n" +
                "                        \"image_layer\": 1,\n" +
                "                        \"input_type\": 3,\n" +
                "                        \"color\": \"0x000000\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"input_stream_id\": \""+input_stream_id+"\",\n" +
                "                    \"layout_params\": {\n" +
                "                        \"image_layer\": 2\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"input_stream_id\": \""+input_stream_id_two+"\",\n" +
                "                    \"layout_params\": {\n" +
                "                        \"image_layer\": 3\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
        System.out.println("发送的JSON为-----------"+json);
       String date= liveInfoServicve.beginChallenge(map, json, format);
        return R.result(1,"OK",date);
    }
    /**
     *@Author:YS
     *@Description: PK结束后 取消混流
     *@Date:2018/3/28
     *@param:
     * mix_stream_session_id
     * input_stream_id
     */
    @SysLog
    @PostMapping("/cancelChallenge")//String mix_stream_session_id, String input_stream_id
    public Object cancelChallenge(@RequestParam Map<String, Object> map) {
        String input_stream_id=map.get("input_stream_id") .toString();//混流使用 需要传递
        String app_id = BroadcastApiutils.APPID;
        String URL = "http://fcgi.video.qcloud.com/common_access?t=%s&appid=%s&sign=%s&interface=%s";//业务API
        String format = String.format(URL, BroadcastApiutils.getTime(), app_id, BroadcastApiutils.getSign(), "Mix_StreamV2");
        System.out.println("format后的URL为--------"+format);
        String json="{\n" +
                "    \"timestamp\": "+ System.currentTimeMillis() / 1000+",\n" +
                "    \"eventId\": "+ System.currentTimeMillis() / 1000+",\n" +
                "    \"interface\": {\n" +
                "        \"interfaceName\": \"Mix_StreamV2\",\n" +
                "        \"para\": {\n" +
                "            \"app_id\": "+app_id+",\n" +
                "            \"interface\": \"mix_streamv2.start_mix_stream_advanced\",\n" +
                "            \"mix_stream_session_id\": \""+input_stream_id+"\",\n" +
                "            \"output_stream_id\": \""+input_stream_id+"\",\n" +
                "            \"input_stream_list\": [\n" +
                "                {\n" +
                "                    \"input_stream_id\": \""+input_stream_id+"\",\n" +
                "                    \"layout_params\": {\n" +
                "                        \"image_layer\": 1\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
       Object o = liveInfoServicve.stateChallenge(map, json, format);
        return R.result(1,"OK",o);
    }


    /**
     *@Author:YS
     *@Description:   连麦开关 的使用
     *@Date:2018/3/28
     *@param: #{microphone_switch} #{live_id}
     */
    @SysLog
    @PostMapping("/microphone_switch")
    public Object microphone_switch(@RequestParam Map<String, Object> map) {
       if (ObjectUtils.isEmpty(map.get("microphone_switch"))){
           //如果不传递 microphone_switch 说明是查询连麦开关的状态
           Map<String, Object> microphone_switch = liveInfoServicve.microphone_switch(map);
           return R.result(1, "OK", microphone_switch);
       }else {
           liveInfoServicve.stateMicrophone_switch(map);//传递了就是修改开关
       }
        return R.result(1, "OK", liveInfoServicve.microphone_switch(map));
    }


    /**
     *@Author:YS
     *@Description:   全局禁言开关 的使用
     *@Date:2018/3/28
     *@param: #{microphone_switch} #{live_id}
     */
    @SysLog
    @PostMapping("/prohibit_speaking")
    public Object prohibit_speaking(@RequestParam Map<String, Object> map) {
        if (ObjectUtils.isEmpty(map.get("microphone_switch"))){
            //如果不传递 microphone_switch 说明是查询连麦开关的状态
            Map<String, Object> microphone_switch = liveInfoServicve.prohibit_speaking(map);
            return R.result(1, "OK", microphone_switch);
        }else {
            liveInfoServicve.stateMicrophone_switch(map);//传递了就是修改开关
        }
        return R.result(1, "OK", "");
    }




    /**
     * @Author:YS
     * @Description: 申请视频交友连麦
     * @Date:2018/3/20
     * @param: #{anchor_id},#{audience_id}
     */
    @SysLog
    @PostMapping("/videoMicrophone")
    public Object videoMicrophone(@RequestParam Map<String, Object> map) {
        Map<String, Object> stringObjectMap = liveInfoServicve.videoMicrophone(map);
        if (stringObjectMap == null) {
            return R.result(0, "您已经申请过视频连麦了，请等待", "");
        }
        return R.result(1, "OK", stringObjectMap);
    }


    /**
     *@Author:YS
     *@Description:  <!-- 批量删除视频连麦的 -->
     *@Date:2018/3/29
     *@param:No such property: code for class: Script1
     */
    @SysLog
    @PostMapping("/deleteAll")
    public Object deleteAll(@RequestParam Map<String, Object> map) {
        String stream_id = map.get("stream_id").toString();
        if(null != stream_id && !"".equals(stream_id)){
            String ArrayStream_id[] = stream_id.split(",");
            liveInfoServicve.deleteAll(ArrayStream_id);
        }
        return R.result(1, "OK", "");
    }
    /**
     *@Author:YS
     *@Description: <!--查询开始直播的分类 不返回PK 全球 热门 最新-->
     *@Date:2018/4/8
     *@param:
     */
    @SysLog
    @PostMapping("/selectLive_type")
    public Object selectLive_type() {
        List<Map<String, Object>> maps = liveInfoServicve.selectLive_type();
        return R.result(1, "OK", maps);
    }
    /**
     *@Author:YS
     *@Description: <!--查询开始直播的分类-->
     *@Date:2018/4/8
     *@param:
     */
    @SysLog
    @PostMapping("/selectLive_typeAll")
    public Object selectLive_typeAll() {
         Object maps = liveInfoServicve.selectLive_typeAll();
        return R.result(1, "OK", maps);
    }

    /**
     * @author: YS
     * @Date:2018/4/16 19:55
     * @param:
     * @explain：  <!--查看某个群的 连麦开关0关闭1开启' 全局禁言开关0关闭1开启' 以及在这个群的状态 是否禁言-->
     * @return:
     */
    @SysLog
    @PostMapping("/imSwitch")
    public Object imSwitch(@RequestParam Map<String, Object> map) {
       Map<String, Object> maps = liveInfoServicve.imSwitch(map);
        return R.result(1, "OK", maps);
    }


    /**
     * @author: YS
     * @Date:2018/4/20 10:32
     * @param:
     * @explain：   <!--查看直播最后一次的直播间名字以及背景图-->
     * @return:
     */
    @SysLog
    @PostMapping("/selectMyLive")
    public Object selectMyLive(@RequestParam Map<String, Object> map) {
        Object insertSelective = liveInfoServicve.selectMyLive(map);
        if (insertSelective==null){
            Map<String, Object> result = new HashMap<>();
            result.put("live_img","");
            result.put("info_name","");
            return R.result(1, "OK",result );
        }
        return R.result(1, "OK", insertSelective);
    }


}
