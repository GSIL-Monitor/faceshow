package com.faceshow.modules.gift.controller;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey;
import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.Query;
import com.faceshow.common.utils.R;
import com.faceshow.modules.gift.service.GiftInfoService;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.video.entity.VideoGiftSend;
import com.faceshow.modules.video.service.VideoGiftSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 礼物操作Controller<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.gift.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/1 17:15
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/gift")
public class GiftInfoController extends AbstractController {

    @Autowired
    private GiftInfoService giftInfoService;

    /**
     * 点击礼物图标, 弹出礼物页面, 查询所有礼物及当前用户拥有礼物
     *
     * @param userId   当前用户id
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @return
     */
    @SysLog
    @PostMapping("/findGift")
    public Object findGift(Integer userId, @RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        return giftInfoService.findGiftByUserId(getUserInfoId(), currPage, pageSize);
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
    @SysLog
    @PostMapping("sendGiftToVideo")
    public Object sendGiftToVideo(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return giftInfoService.sendGiftToVideo(map);
    }

    /**
     * @Author:YS
     * @Description:赠送礼物接口 6.2.1.10.4送礼物页面
     * @Date:2018/2/5
     */
    @SysLog
    @PostMapping("userGiftTo")
    public Object userGiftTO(@RequestParam Map<String, Object> map) {
        List<Object> infoDetail = giftInfoService.GiftAll(map);
        return R.result(1, "ok", infoDetail);
    }

    /**
     * @Author:YS
     * @Description:赠送礼物逻辑开始处理
     * @Date:2018/2/5
     */
    @SysLog
    @PostMapping("sendGift")
    public Object sendGift(@RequestParam Map<String, Object> map) {
        Object o = giftInfoService.sendGift(map);
        //System.out.println(JsonUtils.objectToJson(o));
        return R.result(1, "OK", o);
       /* if (i==0){
            return  R.result(0,"钻石不足","");
        }else if (i==3){
            return  R.result(0,"您拥有的礼物数量不足，请选择钻石购买赠送","");
        }else if (i==4){
            return  R.result(1,"您用了自己拥有的礼物进行了赠送","");
        }
        else{
            return  R.result(1,"使用钻石赠送成功","");
        }*/
    }

    /**
     * @Author:YS
     * @Description: <!--送我礼物所有人的排行榜 带分页-->
     * @Date:2018/2/27
     * @param:
     */

    @SysLog
    @PostMapping("/giftRankingList")
    public Object giftRankingList(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = giftInfoService.giftRankingList(query);
        int total = giftInfoService.giftRankingListTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * @Author:YS
     * @Description: <!--我收到的所有礼物-->
     * @Date:2018/2/27
     * @param:
     */

    @SysLog
    @PostMapping("/findGiftDetail")
    public Object findGiftDetail(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = giftInfoService.findGiftDetail(query);
        int total = giftInfoService.findGiftDetailTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * @Author:YS
     * @Description:这一场的直播排行榜 传递live_id也就是直播的唯一ID
     * @Date:2018/3/13
     * @param:
     */

    @SysLog
    @PostMapping("/liveRankingList")
    public Object liveRankingList(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail;//申明
        int total;//申明
        if (map.get("type").toString().equals("1")) {
            //一周的礼物排行榜
            infoDetail = giftInfoService.liveRankingListWeek(query);
            total = giftInfoService.liveRankingListWeekTotal(query);
        } else {
            //本场的排行榜
            infoDetail = giftInfoService.liveRankingList(query);
            total = giftInfoService.liveRankingListTotal(query);
        }
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * @Author:YS
     * @Description: 得到所有礼物 YSTODO等待加缓存
     * @Date:2018/3/28
     * @param:No such property: code for class: Script1
     */

    @SysLog
    @PostMapping("GiftAll")
    public Object GiftAll(@RequestParam Map<String, Object> map) {
        Object o = giftInfoService.GiftAll(map);
        return R.result(1, "OK", o);
    }

    /**
     * @Author:YS
     * @Description: 查看某个直播 或者PK 收到的礼物
     * @Date:2018/3/30
     * @param: is_type ITEM_ID
     */

    @SysLog
    @PostMapping("haveGift")
    public Object haveGift(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> maps = giftInfoService.haveGift(map);
        return R.result(1, "OK", maps == null ? "" : maps);
    }

    /**
     * @Author:YS
     * @Description: <!--查询PK时候的礼物价值和礼物ID-->
     * @Date:2018/3/31
     * @param: live_challenge_id
     */

    @SysLog
    @PostMapping("selectPkPrice")
    public Object selectPkPrice(int live_challenge_id) {
        Map<String, Object> maps = giftInfoService.selectPkPrice(live_challenge_id);
        return R.result(1, "OK", maps == null ? "" : maps);
    }
}
