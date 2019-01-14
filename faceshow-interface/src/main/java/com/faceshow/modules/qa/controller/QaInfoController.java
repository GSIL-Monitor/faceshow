package com.faceshow.modules.qa.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.BaiduMapUtils;
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.VideoUtils;
import com.faceshow.common.utils.mapbean.MapResultBean;
import com.faceshow.modules.qa.entity.QaAmountSet;
import com.faceshow.modules.qa.entity.QaInfo;
import com.faceshow.modules.qa.service.QaInfoService;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.user.service.UserInfoDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 问答详情操作controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.qa.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:28
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/qainfo")
public class QaInfoController extends AbstractController {

    @Autowired
    private QaInfoService qaInfoService;

    @Autowired
    private UserInfoDetailService userInfoDetailService;

    @Autowired
    private VideoUtils videoUtils;

    /**
     * 保存发布的问题
     *
     * @param qaInfo      问题内容信息
     * @param nickName    发布人昵称
     * @param qaAmountSet 金额设置表
     * @param file        问题视频
     * @return
     */
    @SysLog
    @PostMapping("/save")
    public Object save(QaInfo qaInfo, String nickName, QaAmountSet qaAmountSet, MultipartFile file) {
        qaInfo.setUserId(getUserInfoId());

        qaInfo.setCreateTime(new Date());

        // 用户余额
        Integer diamond = 0;
        int price = 0;

        if (qaInfo.getAmountType() == 2) {
            // 获取用户余额
            diamond = userInfoDetailService.findDiamondByUserId(qaInfo.getUserId());
            price = qaAmountSet.getPrice();

            // 2 指定最佳答案
            if (diamond < price) {
                return R.result(0, "余额不足", "");
            }
        } else if (qaInfo.getAmountType() == 3) {
            // 3 前三名
            // 获取用户余额
            diamond = userInfoDetailService.findDiamondByUserId(qaInfo.getUserId());

            // 发布问答价格
            price = qaAmountSet.getPrice() + qaAmountSet.getTwoPrice() + qaAmountSet.getThreePrice();

            if (diamond < price) {
                return R.result(0, "余额不足", "");
            }
        }

        // 根据经纬度获取国家信息
        MapResultBean msg = BaiduMapUtils.getMsg(qaInfo.getLatitude() + "," + qaInfo.getLongitude());
        qaInfo.setCountry(msg.getResult().getAddressComponent().getCountry_code_iso2());
        qaInfo.setAddress(msg.getResult().getFormatted_address());

        try {
            Map<String, String> fileUrl = videoUtils.videoUpload(file, true, false);
            qaInfo.setVideo(fileUrl.get("videoUrl"));
            qaInfo.setCover(fileUrl.get("imgUrl"));
            return qaInfoService.save(qaInfo, nickName, qaAmountSet, price);
        } catch (Exception e) {
            e.printStackTrace();
            return R.result(0, "发布视频失败", "");
        }
    }

    /**
     * 根据用户id查询用户的提问列表
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @SysLog
    @PostMapping("/findQaInfoListByUserId")
    public Object findQaInfoListByUserId(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return qaInfoService.findQaInfoListByUserId(map);
    }

    /**
     * 问答点赞
     *
     * @param userId 当前用户id
     * @param infoId 问答回复id
     * @return
     */
    @SysLog
    @PostMapping("/addLike")
    public Object addLike(Integer userId, Integer infoId) {
        return qaInfoService.addLike(getUserInfoId(), infoId);
    }

    /**
     * 问答取消点赞
     *
     * @param userId 当前用户id
     * @param infoId 问答回复id
     * @return
     */
    @SysLog
    @PostMapping("/deleteLike")
    public Object deleteLike(Integer userId, Integer infoId) {
        return qaInfoService.deleteLike(getUserInfoId(), infoId);
    }

    /**
     * 查询有奖回答列表
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     *            country 国家
     * @return
     */
    @SysLog
    @PostMapping("/findWaitQaInfo")
    public Object findWaitQaInfo(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return qaInfoService.findWaitQaInfo(map);
    }

    /**
     * 查询免费广场信息
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     *            country 国家
     * @return
     */
    @SysLog
    @PostMapping("/findFreeQaInfo")
    public Object findFreeQaInfo(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return qaInfoService.findFreeQaInfo(map);
    }

    /**
     * 查看问答详情
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            infoId 当前问答id
     * @return
     */
    @SysLog
    @PostMapping("/findQaInfo")
    public Object findQaInfoById(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return qaInfoService.findQaInfoById(map);
    }

    /**
     * 标记问答回复为最佳答案
     *
     * @param map 条件
     *            userId 当前用户id
     *            account 回复视频用户id
     *            accountName 回复视频用户昵称
     *            infoId 问答id
     *            amountType 问答类型
     *            replyId 回复视频id
     * @return
     */
    @SysLog
    @PostMapping("/bestAnswer")
    public Object bestAnswer(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return qaInfoService.bestAnswer(map);
    }

}
