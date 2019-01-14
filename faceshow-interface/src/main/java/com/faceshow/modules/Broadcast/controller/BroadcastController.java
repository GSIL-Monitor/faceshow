package com.faceshow.modules.Broadcast.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.FastDFSClient;
import com.faceshow.common.utils.HttpClientUtil;
import com.faceshow.modules.Broadcast.BroadcastApiutils;
import com.faceshow.modules.Broadcast.CallbackBean;
import com.faceshow.modules.IM.Imutils;
import com.faceshow.modules.friend.entity.FriendRing;
import com.faceshow.modules.live.service.LiveInfoServicve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tokens/broadcast")
public class BroadcastController {
    @Autowired
    private LiveInfoServicve liveInfoServicve;

    @SysLog
    @PostMapping("/Live_Channel_GetChannelList")
    //查询频道列表
    public Object Live_Channel_GetChannelList(@RequestParam Map<String, String> map) {
        String BUSINESS = "http://fcgi.video.qcloud.com/common_access";//业务API
        map.put("appid", "1252173102");
        map.put("interface", "Live_Channel_GetChannelList");//接口名
        map.put("t", BroadcastApiutils.getTime());//有效截止时间戳
        map.put("sign", BroadcastApiutils.getSign());// 	签名  	md5(key+ 有效截止时间戳)
        String date = HttpClientUtil.doGet(BUSINESS, map);
        return date;
    }

    /**
     * @Author:YS
     * @Description:对一条直播流执行禁用、断流和允许推流操作。 禁用 表示不能再继续使用该流 ID 推流；如果正在推流，则推流会被中断，中断后不可再次推流。断流 表示中断正在推的流，
     * 断流后可以再次推流。允许推流 表示启用该流 ID，允许用该流 ID 推流。
     * 用途
     * 主要用于鉴黄时的禁播场景，比如您如果在后台发现某个主播有涉黄或者反动内容，可以随时断流或者禁用这条流。
     * 说明
     * 一条直播流一旦被设置为【禁用】状态，推流链路将被腾讯云主动断开，并且后续的推流请求也会被拒绝，一条流最长禁用 3 个月，超过 3 个月，禁用失效。
     * @Date:2018/3/9
     * @param:No such property: code for class: Script1
     */
    @SysLog
    @PostMapping("/Live_Channel_SetStatus")
    //开启关闭推流  https://cloud.tencent.com/document/product/267/5959
    public Object Live_Channel_SetStatus(@RequestParam Map<String, String> map) {
        String BUSINESS = "http://fcgi.video.qcloud.com/common_access";//业务API
        map.put("appid", "1252173102");//移动端传递
        map.put("interface", "Live_Channel_SetStatus");//接口名
        map.put("Param.s.channel_id", "0025");//移动端传递 	直播码
        map.put("Param.n.status", "2");// 移动端传递   开关状态 0 表示禁用，1 表示允许推流，2 表示断流
        map.put("t", BroadcastApiutils.getTime());//有效截止时间戳
        map.put("sign", BroadcastApiutils.getSign());// 	签名  	md5(key+ 有效截止时间戳)
        String date = HttpClientUtil.doGet(BUSINESS, map);
        return date;
    }
    /**
     *@Author:YS
     *@Description: 云端混流接口 https://cloud.tencent.com/document/product/267/8832
     *@Date:2018/3/9
     *@param:No such property: code for class: Script1
     */


    /**
     * @Author:YS
     * @Description:暂停并延迟恢复 用途
     * 当某些原因不允许主播推流时，可调用此接口。
     * 说明
     * 调用该接口可以暂停推某路流（即禁止推流），如果要恢复主播推流，可再次调用该接口或者设置一个恢复时间，
     * 到达指定时间后允许推流，最长禁止推流 3 个月，即禁止推流截止时间最长为当前时间往后 3 个月，如果超过 3 个月，则以 3 个月为准。
     * @Date:2018/3/9
     * @param:No
     */
    @SysLog
    @PostMapping("/channel_manager")
    //暂停并延迟恢复  https://cloud.tencent.com/document/product/267/9500
    public Object channel_manager(@RequestParam Map<String, String> map) {
        String BUSINESS = "http://fcgi.video.qcloud.com/common_access";//业务API
        map.put("appid", "1252173102");//移动端传递
        map.put("interface", "channel_manager");//接口名
        map.put("Param.s.channel_id", "0025");//移动端传递 	直播码
        map.put("Param.n.abstime_end", "2");// 移动端传递   禁播截止的绝对时间，请填写UNIX 时间戳（十进制），系统最多禁播三个月。
        map.put("Param.s.action", "forbid");// 移动端传递   	断流：forbid；恢复推流：resume
        map.put("t", BroadcastApiutils.getTime());//有效截止时间戳
        map.put("sign", BroadcastApiutils.getSign());// 	签名  	md5(key+ 有效截止时间戳)
        String date = HttpClientUtil.doGet(BUSINESS, map);
        return date;
    }

    /**
     *@Author:YS
     *@Description: 创建录制任务 https://cloud.tencent.com/document/product/267/9567
     *@Date:2018/3/9
     *@param:No such property: code for class: Script1
     */


    /**
     * @Author:YS
     * @Description:结束录制任务 https://cloud.tencent.com/document/product/267/9568
     * 说明   结束录制任务。
     * @Date:2018/3/9
     * @param:No such property: code for class: Script1
     */
    @SysLog
    @PostMapping("/Live_Tape_Stop")
    public Object Live_Tape_Stop(@RequestParam Map<String, String> map) {
        String BUSINESS = "http://fcgi.video.qcloud.com/common_access";//业务API
        map.put("appid", "1252173102");//移动端传递
        map.put("interface", "channel_manager");//接口名
        map.put("t", BroadcastApiutils.getTime());//有效截止时间戳
        map.put("sign", BroadcastApiutils.getSign());// 	签名  	md5(key+ 有效截止时间戳)
        map.put("Param.s.channel_id", "0025");//移动端传递 	直播码
        map.put("Param.s.task_id", "2");// 任务 ID
        map.put("Param.n.task_sub_type", "0");// 默认 0，1 表示开启小视频录制
        String date = HttpClientUtil.doGet(BUSINESS, map);
        return date;
    }
    ///////////////////////////华丽的分割线  以下是查询类接口  ///////////////////////////

    /**
     * @Author:YS
     * @Description: 查询直播状态
     * 用于查询某条流是否处于正在直播的状态。https://cloud.tencent.com/document/product/267/5958
     * 用途
     * 用于查询某条流是否处于正在直播的状态，其内部原理是基于腾讯云对音视频流的中断检测而实现的，因此实时性上可能不如 App 的主动上
     * 报这么快速和准确，但在进行直播流定时清理检查的时候可以作为一种不错的补充手段。
     * @Date:2018/3/9
     * @param:
     */
    @SysLog
    @PostMapping("/Live_Channel_GetStatus")
    public Object Live_Channel_GetStatus(@RequestParam Map<String, String> map) {
        String BUSINESS = "http://fcgi.video.qcloud.com/common_access";//业务API
        map.put("appid", "1252173102");//移动端传递
        map.put("interface", "channel_manager");//接口名
        map.put("t", BroadcastApiutils.getTime());//有效截止时间戳
        map.put("sign", BroadcastApiutils.getSign());// 	签名  	md5(key+ 有效截止时间戳)
        map.put("Param.s.channel_id", "0025");//移动端传递 	直播码
        String date = HttpClientUtil.doGet(BUSINESS, map);
        return date;
    }
    /**
     *@Author:YS
     *@Description: 查询统计信息 https://cloud.tencent.com/document/product/267/6110
     * Get_LiveStat：查询指定直播流的推流和播放信息；
     * Get_LivePushStat ：仅返回推流统计信息以提高查询效率；
     * Get_LivePlayStat ：仅返回播放统计信息以提高查询效率。
     * 用途
     * 用于查询某条直播流的统计信息（如观看人数、带宽、码率、帧率等）；
     * 查询当前正在直播状态中的若干条直播流的统计信息（建议采用分页查询避免单次回包数据过大）。
     * 说明。
     * 统计数据均为查询时间点的瞬时统计数据，而并非历史累计数据；
     * 如果目标流不在直播中，则返回结果中的 output 字段为空；
     * 推流信息的统计数据每 5 秒钟更新一次，也就是快于 5 秒的查询频率是浪费的；
     * 播放信息的统计数据每 1 分钟更新一次，也就是快于 60 秒的查询频率是浪费的。
     * BETA
     *统计接口目前尚处于 Beta 阶段，并未全员放开，未开通即调用此接口会收到【cmd is invalid】提示，如您急需请联系我们。
     *@Date:2018/3/9
     *@param:
     */


    /**
     *@Author:YS
     *@Description: 查询录制文件 https://cloud.tencent.com/document/product/267/5960
     * Get_LiveStat：查询指定直播流的推流和播放信息；
     * Get_LivePushStat ：仅返回推流统计信息以提高查询效率；
     * Get_LivePlayStat ：仅返回播放统计信息以提高查询效率。
     * 说明。
     * 由于文件的落地时间对您不可知，所以这种主动查询接口在调用时机上并不是特别好掌握，更推荐使用被动事件通知（event_type = 100）机制。
     *@Date:2018/3/9
     *@param:
     */


    /**
     * @Author:YS
     * @Description: 查询直播中频道列表   https://cloud.tencent.com/document/product/267/8862
     * 用途。
     * 在直播码模式下，用于查询直播中频道列表。。
     * @Date:2018/3/9
     * @param:
     */
    @SysLog
    @PostMapping("/Live_Channel_GetLiveChannelList")
    public Object Live_Channel_GetLiveChannelList(@RequestParam Map<String, String> map) {
        String BUSINESS = "http://fcgi.video.qcloud.com/common_access";//业务API
        map.put("appid", "1252173102");//移动端传递
        map.put("interface", "channel_manager");//接口名
        map.put("t", BroadcastApiutils.getTime());//有效截止时间戳
        map.put("sign", BroadcastApiutils.getSign());// 	签名  	md5(key+ 有效截止时间戳)
        String date = HttpClientUtil.doGet(BUSINESS, map);
        return date;
    }
    /////////////////////////////华丽的分割线  以下就是统计的接口了//////////////////////////////////////////////

    /**
     * @Author:YS
     * @Description: 获取推流历史信息 https://cloud.tencent.com/document/product/267/9579
     * 说明
     * 可获取指定时间段内推流信息
     * 推流信息的统计数据每 5 秒钟更新一次。
     * 使用该接口需要后台配置，如需调用该接口，请联系腾讯商务人员或者 提交工单，联系电话：4009-100-100。
     * @Date:2018/3/9
     * @param:
     */

    @SysLog
    @PostMapping("/Get_LivePushStatHistory")
    public Object Get_LivePushStatHistory(@RequestParam Map<String, String> map) {
        String BUSINESS = "http://fcgi.video.qcloud.com/common_access";//业务API
        map.put("cmd", "1252173102");//移动端传递
        map.put("interface", "channel_manager");//接口名
        map.put("t", BroadcastApiutils.getTime());//有效截止时间戳
        map.put("sign", BroadcastApiutils.getSign());// 	签名  	md5(key+ 有效截止时间戳)
        map.put("Param.n.start_time", BroadcastApiutils.getSign());//  	查询起始时间	 	3 天内的数据时间戳
        map.put("Param.n.end_time", BroadcastApiutils.getSign());//查询终止时间 	建议查询跨度不大于 2 小时时间戳
        map.put("Param.s.stream_id", BroadcastApiutils.getSign());//  	流 ID
        String date = HttpClientUtil.doGet(BUSINESS, map);
        return date;
    }

    /**
     * @Author:YS
     * @Description: 获取播放统计历史信息 https://cloud.tencent.com/document/product/267/9580
     * 说明
     * 可获取指定时间段内推流信息
     * 推流信息的统计数据每 5 秒钟更新一次。
     * 使用该接口需要后台配置，如需调用该接口，请联系腾讯商务人员或者 提交工单，联系电话：4009-100-100。
     * @Date:2018/3/9
     * @param:
     */

    @SysLog
    @PostMapping("/Get_LivePlayStatHistory")
    public Object Get_LivePlayStatHistory(@RequestParam Map<String, String> map) {
        String BUSINESS = "http://fcgi.video.qcloud.com/common_access";//业务API
        map.put("cmd", "1252173102");//移动端传递
        map.put("interface", "channel_manager");//接口名
        map.put("t", BroadcastApiutils.getTime());//有效截止时间戳
        map.put("sign", BroadcastApiutils.getSign());// 	签名  	md5(key+ 有效截止时间戳)

        map.put("Param.n.start_time", BroadcastApiutils.getSign());//  	查询起始时间	 	3 天内的数据时间戳
        map.put("Param.n.end_time", BroadcastApiutils.getSign());//查询终止时间 	建议查询跨度不大于 2 小时时间戳
        map.put("Param.s.stream_id", BroadcastApiutils.getSign());//  	流 ID
        map.put("Param.s.domain", BroadcastApiutils.getSign());//  	若不填，取这个 APPID 下的总数据，需要填写 cname 前的原始播放域名
        String date = HttpClientUtil.doGet(BUSINESS, map);
        return date;
    }


    /**
     * @Author:YS
     * @Description: 直播结束后更新数据库 TX的回掉 TODOYS
     * @Date:2018/3/10
     * @param:
     */
    @SysLog
    @PostMapping("/callback")
    public String callback(@RequestBody CallbackBean callbackbean) {
        System.out.println(System.currentTimeMillis() / 1000);
        System.out.println(callbackbean);
        //判断是否是腾讯发来的回掉
        String sign = BroadcastApiutils.md5(BroadcastApiutils.KEY + callbackbean.getT());
        System.out.println("验证Sign是否正确" + sign);
        if (callbackbean.getEvent_type() == 0 && callbackbean.getSign().equals(sign)) {
            System.out.println("直播流回掉进来了 0 代表断流，1 代表推流" + callbackbean.getEvent_type());
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("end_time", new Date());
            map.put("is_live", 1);
            map.put("stream_id", callbackbean.getChannel_id());
            liveInfoServicve.endBroadcast(map);//更新流 把这次直播关闭时间更新
            System.out.println("stream_id为--" + callbackbean.getChannel_id());
            return "{\"code\":0}";
        }
        System.out.println("出来了" + callbackbean.getEvent_type());
        return "";
    }


    ////////////////////////上传图片
    @SysLog
    @PostMapping("/upload")
    public Object upload(MultipartFile img) throws Exception {
        // 保存发布消息
        String imgUrl = FastDFSClient.uploadFile(img.getBytes(), "png", null);//图片服务器有问题
        System.out.println(imgUrl);
        return imgUrl;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        String BUSINESS = "http://fcgi.video.qcloud.com/common_access";//业务API
        map.put("appid", "1252173102");//有效截止时间戳
        map.put("interface", "Mix_StreamV2");//有效截止时间戳
        map.put("t", BroadcastApiutils.getTime());//有效截止时间戳
        map.put("sign", BroadcastApiutils.getSign());// 	签名  	md5(key+ 有效截止时间戳)
        String date = HttpClientUtil.doGet(BUSINESS, map);
    }
}
