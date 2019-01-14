package com.faceshow.modules.user.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.FastDFSClient;
import com.faceshow.common.utils.R;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.user.entity.UserApprove;
import com.faceshow.modules.user.service.UserApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 实名认证<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.user.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 李燕 on 2018/02/3 16:00
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/user/approve")
public class UserApproveController extends AbstractController {

    @Autowired
    private UserApproveService userApproveService;

    /**
     * 申请实名认证
     *
     * @param cardOpeFile 身份证反面
     * @param cardPoeFile 身份证正面
     * @param userApprove
     * @return
     */
    @SysLog
    @PostMapping(value = "/addApprove")
    public Object addApprove(MultipartFile cardOpeFile, MultipartFile cardPoeFile, UserApprove userApprove) {
        userApprove.setUserId(getUserInfoId());
        userApprove.setApproveTime(new Date());
        if (cardOpeFile != null && cardOpeFile.getSize() > 0 && cardPoeFile != null && cardPoeFile.getSize() > 0) {
            try {
                String CardOpUrl = FastDFSClient.uploadFile(cardOpeFile.getBytes(), cardOpeFile.getOriginalFilename().substring(cardOpeFile.getOriginalFilename().lastIndexOf(".") + 1), null);
                userApprove.setCardOp(CardOpUrl);
                userApprove.setApproveTime(new Date());
                String CardPoUrl = FastDFSClient.uploadFile(cardPoeFile.getBytes(), cardPoeFile.getOriginalFilename().substring(cardPoeFile.getOriginalFilename().lastIndexOf(".") + 1), null);
                userApprove.setCardPo(CardPoUrl);
                userApproveService.insertSelective(userApprove);
                logger.info("添加实名认证成功, 认证用户id为: " + userApprove.getUserId());
                return R.result(1, "添加实名认证成功", "");
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("添加实名认证失败, 异常信息为: ", e);
            }
        }
        logger.warn("没有上传身份证照片, 添加实名认证失败");

        return R.result(0, "添加实名认证失败", "");


    }
}
