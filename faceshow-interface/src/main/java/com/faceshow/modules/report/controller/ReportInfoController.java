package com.faceshow.modules.report.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.FastDFSClient;
import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.R;
import com.faceshow.modules.report.entity.ReportInfo;
import com.faceshow.modules.report.entity.ReportType;
import com.faceshow.modules.report.service.ReportInfoService;
import com.faceshow.modules.report.service.ReportTypeService;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.user.entity.AppFeedback;
import com.faceshow.modules.user.entity.BackList;
import com.faceshow.modules.user.entity.UserSet;
import com.faceshow.modules.user.service.UserAttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 关注相关, 用户注册前的数据校验操作<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.user.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 李燕 on 2018/01/24 16:37
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/report")
public class ReportInfoController extends AbstractController {
    @Autowired
    private ReportTypeService reportTypeService;
    @Autowired
    private ReportInfoService reportInfoService;
    @Autowired
    private UserAttentionService userAttentionService;

    /**
     * 获取不同页面的举报类型
     *
     * @param type 1短视频举报类型, 2评论举报类型, 3直播举报类型
     */
    @SysLog
    @PostMapping("/getallreport")
    public Object getAllReport(Integer type) {
        // List<UserReportType> res = userReportTypeService.selectAll();

        List<ReportType> result = reportTypeService.findReportByType(type);
        if (result == null) {
            return R.result(0, "获取举报类型失败", result);
        } else {
            return R.result(1, "获取举报类型成功", result);
        }
    }

    /**
     * 添加举报
     */
    @SysLog
    @PostMapping("/addreport")
    public Object addReport(ReportInfo reportInfo, MultipartFile imgFile) {
        reportInfo.setUserId(getUserInfoId());

        // 将上传文件保存到服务器
        if (imgFile != null) {
            try {
                String imgUrl = FastDFSClient.uploadFile(imgFile.getBytes(), imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf(".") + 1), null);
                reportInfo.setImg(imgUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        reportInfo.setCreateTime(new Date());
        return reportInfoService.insertSelective(reportInfo);
    }

    /**
     * 我的举报列表
     */
    @SysLog
    @PostMapping("/listreport")
    public Object listReport(Integer userId, @RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Object> res = reportInfoService.selectByUserId(getUserInfoId(), (currPage - 1) * pageSize, pageSize);
        if (res == null) {
            return R.result(0, "获取举报列表失败", "");
        } else {
            Integer friendNum = reportInfoService.selectByUserIdNum(getUserInfoId());
            return R.result(1, "获取举报列表成功", new PageUtils(res, friendNum, pageSize, currPage));
        }

    }

    /**
     * 添加举报黑名单
     */
    @SysLog
    @PostMapping("/addbacklist")
    public Object addBackList(BackList backList) {
        Integer res = reportInfoService.insertBackList(backList);
        backList.setCreateTime(new Date());
        if (res == null || res == 0) {
            return R.result(0, "拉黑失败", "");
        } else {
            return R.result(1, "拉黑成功", "");
        }

    }

    /**
     * 移除黑名单
     */
    @SysLog
    @PostMapping("/deletebacklist")
    public Object deleteBackList(Integer userId, Integer account) {
        Integer res = reportInfoService.deleteByUserid(userId, account);
        if (res > 0) {
            return R.result(1, "移除成功", "");
        } else {
            return R.result(0, "移除失败", "");
        }

    }

    /**
     * 我的黑名单列表
     */
    @SysLog
    @PostMapping("/listbacklist")
    public Object listBackList(Integer userId, @RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Object> res = reportInfoService.selectByBackUserid(userId, (currPage - 1) * pageSize, pageSize);
        if (res == null) {
            return R.result(0, "获取关注列表失败", "");
        } else {
            Integer friendNum = reportInfoService.selectByBackUseridNum(userId);
            return R.result(1, "获取关注列表成功", new PageUtils(res, friendNum, pageSize, currPage));
        }
    }

    /**
     * 问题反馈
     */
    @SysLog
    @PostMapping("/addfeedback")
    public Object addFeedBack(AppFeedback appFeedback) {
        appFeedback.setCreateTime(new Date());
        Integer res = reportInfoService.insertFeedBack(appFeedback);

        if (res == null) {
            return R.result(0, "问题反馈失败", "");
        } else {
            return R.result(1, "问题反馈成功", "");
        }
    }

    /**
     * 更新距离设置
     */
    @SysLog
    @PostMapping("/updatedistance")
    public Object updateDistance(UserSet userSet) {
        userSet.setSetTime(new Date());
        Integer res = reportInfoService.updateDistance(userSet);
        if (res == null) {
            return R.result(0, "设置失败", "");
        } else {
            return R.result(1, "设置成功", "");
        }

    }

    /**
     * 获取距离设置
     */
    @SysLog
    @PostMapping("/getDistance")
    public Object getDistance(Integer userId) {
        UserSet res = reportInfoService.selectByDistance(userId);
        if (res == null) {
            return R.result(0, "获取失败", "");
        } else {
            return R.result(1, "获取成功", res);
        }

    }

}
