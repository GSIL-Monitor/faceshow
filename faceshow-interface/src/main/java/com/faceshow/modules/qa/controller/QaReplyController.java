package com.faceshow.modules.qa.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.BaiduMapUtils;
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.VideoUtils;
import com.faceshow.common.utils.mapbean.MapResultBean;
import com.faceshow.modules.qa.entity.QaReply;
import com.faceshow.modules.qa.service.QaReplyService;
import com.faceshow.modules.sys.controller.AbstractController;
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
 * Explanation: 问答回复操作controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.qa.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:48
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/qareplay")
public class QaReplyController extends AbstractController {

    @Autowired
    private QaReplyService qaReplyService;

    @Autowired
    private VideoUtils videoUtils;

    /**
     * 给问答添加新回复
     *
     * @param qaReply 问答回复详情
     * @param file    视频文件
     * @return
     */
    @SysLog
    @PostMapping("/addReply")
    public Object addReply(QaReply qaReply, MultipartFile file) {
        qaReply.setUserId(getUserInfoId());
        //根据经纬度获取国家地质信息
        MapResultBean msg = BaiduMapUtils.getMsg(qaReply.getLatitude() + "," + qaReply.getLongitude());
        qaReply.setAddress(msg.getResult().getFormatted_address());

        qaReply.setCreateTime(new Date());
        try {
            Map<String, String> fileUrl = videoUtils.videoUpload(file, true, false);
            qaReply.setVideo(fileUrl.get("videoUrl"));
            qaReply.setCover(fileUrl.get("imgUrl"));
            return qaReplyService.addReply(qaReply);
        } catch (Exception e) {
            e.printStackTrace();
            return R.result(0, "问答回复失败", "");
        }
    }

    /**
     * 查询神回复列表
     *
     * @param map 查询条件
     *            limit 每页显示信息数
     *            page 当前页
     *            userId 当前用户
     * @return
     */
    @SysLog
    @PostMapping("/shenList")
    public Object shenList(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return qaReplyService.shenList(map);
    }

    /**
     * 问答回复点赞
     *
     * @param userId  当前用户id
     * @param replyId 问答回复id
     * @param isMake  是否已经吐槽 0 未吐槽, 1 已吐槽
     * @return
     */
    @SysLog
    @PostMapping("/addLike")
    public Object addLike(Integer userId, Integer replyId, Integer isMake) {
        return qaReplyService.addLike(getUserInfoId(), replyId, isMake);
    }

    /**
     * 问答回复取消点赞
     *
     * @param userId  当前用户id
     * @param replyId 问答回复id
     * @return
     */
    @SysLog
    @PostMapping("/deleteLike")
    public Object deleteLike(Integer userId, Integer replyId) {
        return qaReplyService.deleteLike(getUserInfoId(), replyId);
    }

    /**
     * 问答回复吐槽
     *
     * @param userId  当前用户id
     * @param replyId 问答回复id
     * @param isLike  是否已经点赞 0 未吐槽, 1 已吐槽
     * @return
     */
    @SysLog
    @PostMapping("/addMake")
    public Object addMake(Integer userId, Integer replyId, Integer isLike) {
        return qaReplyService.addMake(getUserInfoId(), replyId, isLike);
    }

    /**
     * 问答回复取消吐槽
     *
     * @param userId  当前用户id
     * @param replyId 问答回复id
     * @return
     */
    @SysLog
    @PostMapping("/deleteMake")
    public Object deleteMake(Integer userId, Integer replyId) {
        return qaReplyService.deleteMake(getUserInfoId(), replyId);
    }

    /**
     * 根据问答回复id查询问答回复详情
     *
     * @param map 条件
     *            replyId 问答回复id
     *            userId 当前用户id
     *            longitude 用户当前位置(经度)
     *            latitude 用户当前位置(维度)
     * @return
     */
    @SysLog
    @PostMapping("/detail")
    public Object detail(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return qaReplyService.getQaReplyById(map);
    }


    /**
     * 查询当前用户的回答列表
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @SysLog
    @PostMapping("/findReplyListByUserId")
    public Object findReplyListByUserId(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return qaReplyService.findReplyListByUserId(map);
    }

    /**
     * 根据问答id查询问答回复信息
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            infoId 当前问答id
     *            qaUserId 当前问答发起人id
     *            latitude 维度
     *            longitude 经度
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @SysLog
    @PostMapping("/findReplyListByInfoId")
    public Object findReplyListByInfoId(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return qaReplyService.findReplyListByInfoId(map);
    }
}
