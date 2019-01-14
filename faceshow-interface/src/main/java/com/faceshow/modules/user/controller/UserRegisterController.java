package com.faceshow.modules.user.controller;

import com.faceshow.common.MQ.message.tencent.EmailSendProduct;
import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.RedisKeys;
import com.faceshow.common.utils.RedisUtils;
import com.faceshow.common.utils.ThumbnailatorUtils;
import com.faceshow.common.utils.alipay.MailKeys;
import com.faceshow.common.validator.Assert;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.user.entity.UserInfo;
import com.faceshow.modules.user.service.UserInfoService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 注册相关, 用户注册前的数据校验操作<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.user.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/01/24 10:37
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/api/user/register")
public class UserRegisterController extends AbstractController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ThumbnailatorUtils thumbnailatorUtils;

    /**
     * 密码盐
     */
    @Value("${passwordSalt}")
    private String passwordSalt;


    /**
     * 用户注册, 注册成功后直接登录
     *
     * @param userInfo
     * @return
     */
    @SysLog
    @PostMapping("/save")
    public Object register(MultipartFile imgFile, UserInfo userInfo) {
        Assert.isBlank(userInfo.getPwd(), "密码不能为空");
        if (imgFile != null && imgFile.getSize() > 0) {
            try {
                Map<String, Object> map = thumbnailatorUtils.uploadFileAndCreateThumbnail1(imgFile);
                userInfo.setImg(map.get("bigImgUrl").toString());
                userInfo.setSmallImg(map.get("smallImgUrl").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 密码加密
        userInfo.setPwd(DigestUtils.sha256Hex(userInfo.getPwd() + passwordSalt));
        return userInfoService.insertSelective(userInfo);
    }

    /**
     * 验证邮箱是否已经存在
     *
     * @param email 邮箱号码
     * @return
     */
    @SysLog
    @PostMapping("/checkEmail")
    public Object checkEmail(String email) {
        String redisEmailValue = userInfoService.checkEmail(email);
        if (StringUtils.isBlank(redisEmailValue)) {
            // 不存在, 邮箱号码可以使用, 向邮箱内发送一条邮件
            // 生成token令牌
            String code = UUID.randomUUID().toString().replace("-", "");
            // 设置令牌时效为15分钟
            redisUtils.set(RedisKeys.VERIFY_EMAIL + email, code, 60 * 15);
            EmailSendProduct.pushFriendAt(email, MailKeys.ActiveEmail.TITLE, MailKeys.ActiveEmail.activeEmail(email, code));

            // 将邮箱保存到redis中, 设置有效期为15分钟, 状态码为0, 表示未进行激活处理
            redisUtils.set(RedisKeys.USER_INFO_EMAIL + email, "0", 60 * 15);

            return R.result(1, "邮箱可以使用", 1);
        }
        // 表示没有进行激活处理, 需要进入邮箱进行激活处理
        if (redisEmailValue.equals("0")) {
            return R.result(1, "请去邮箱激活", 2);
        }
        // 如果已经激活, 直接进行下一步操作
        if (redisEmailValue.equals("1")) {
            return R.result(1, "进行下一步操作", 3);
        }

        return R.result(1, "邮箱已被使用", 0);
    }

    /**
     * 重新发送邮件按钮
     *
     * @param email 邮箱号码
     * @return
     */
    @SysLog
    @PostMapping("/resendEmail")
    public Object resendEmail(String email) {
        if (StringUtils.isNotBlank(redisUtils.get(RedisKeys.VERIFY_EMAIL + email))) {
            return R.result(0, "我们已经给你的邮箱发送了一封邮件", "");
        }

        // 生成token令牌
        String code = UUID.randomUUID().toString().replace("-", "");
        // 设置令牌时效为15分钟
        redisUtils.set(RedisKeys.VERIFY_EMAIL + email, code, 60 * 15);
        EmailSendProduct.pushFriendAt(email, MailKeys.ActiveEmail.TITLE, MailKeys.ActiveEmail.activeEmail(email, code));

        // 将邮箱保存到redis中, 设置有效期为15分钟, 状态码为0, 表示未进行激活处理
        redisUtils.set(RedisKeys.USER_INFO_EMAIL + email, "0", 60 * 15);
        return R.result(1, "邮件已经发送请去邮箱激活", "");
    }

    /**
     * 验证电话号码是否已经存在
     *
     * @param mobile 电话号码
     * @return
     */
    @SysLog
    @PostMapping("/checkMobile")
    public Object checkMobile(String mobile) {
        if (StringUtils.isBlank(userInfoService.checkMobile(mobile))) {
            // 不存在
            return R.result(1, "电话号码可以使用", "");
        }
        return R.result(0, "电话号码已被使用", "");
    }

    /**
     * 验证用户名是否已经存在
     *
     * @param userName 用户名
     * @return
     */
    @SysLog
    @PostMapping("/checkUserName")
    public Object checkUserName(String userName) {
        if (StringUtils.isBlank(userInfoService.checkUserName(userName))) {
            // 不存在
            return R.result(1, "用户名可以使用", "1");
        }
        return R.result(1, "用户名已被使用", "0");
    }


    /**
     * 接收手机号码, 发送验证码
     *
     * @param mobile   手机号码
     * @param type     0注册, 1找回密码
     * @param areaCode 国家区号
     * @return 验证码发送结果
     */
    @SysLog
    @PostMapping("/sendCodeByMobile")
    public Object sendCodeByMobile(String mobile, Integer type, String areaCode) {
        Assert.isBlank(mobile, "手机号不能为空");

        Integer i = userInfoService.sendCodeByMobile(mobile, type, areaCode);

        if (i > 0) {
            // 短信发送成功
            return R.result(1, "短信发送成功", i);
        } else if (i == -1) {
            return R.result(0, "手机号码未注册", "");
        } else if (i == -2) {
            return R.result(0, "手机号码已存在", "");
        }

        return R.result(0, "信息发送失败", "");

    }

    /**
     * 接收邮箱号码, 发送验证码
     *
     * @param email 邮箱号码
     * @return 验证码发送结果
     */
    @SysLog
    @PostMapping(value = "/sendCodeByEmail")
    public Object sendCodeByEmail(String email) {

        Assert.isBlank(email, "邮箱号码不能为空");

        Integer i = userInfoService.sendCodeByEmail(email);

        if (i > 0) {
            // 短信发送成功
            return R.result(1, "邮件发送成功", "");
        } else if (i == -1) {
            return R.result(0, "邮箱号码不存在", "");
        }

        return R.result(0, "邮件发送失败", "");
    }

    /**
     * 校验输入的验证码是否正确
     *
     * @param mobile 手机号码
     * @param code   验证码
     * @return
     */
    @SysLog
    @PostMapping("/checkCodeByMobile")
    public Object checkCodeByMobile(String mobile, String code) {
        Integer i = userInfoService.checkCode(mobile, code);
        if (i > 0) {
            // 验证码正确
            return R.result(1, "验证码正确", "");
        }
        return R.result(0, "验证码错误", "");
    }

    /**
     * 校验输入的验证码是否正确
     *
     * @param email 邮箱号码
     * @param code  验证码
     * @return
     */
    @SysLog
    @PostMapping("/checkCodeByEmail")
    public Object checkCodeByEmail(String email, String code) {
        Integer i = userInfoService.checkCode(email, code);
        if (i > 0) {
            // 验证码正确
            return R.result(1, "验证码正确", "");
        }
        return R.result(0, "验证码错误", "");
    }

    /**
     * 忘记密码，找回密码
     *
     * @param type  类型, 1 手机找回密码, 2邮箱找回密码
     * @param pwd   新密码
     * @param param 手机号或邮箱或userId
     * @return
     */
    @SysLog
    @PostMapping("/retrievePwd")
    public Object retrievePwd(Integer type, String param, String pwd, String token) {
        if (type == 2) {
            String code = redisUtils.get(RedisKeys.VERIFY_EMAIL + param);
            if (token == null || !token.equals(code)) {
                return R.result(2, "链接已过期, 请重新发送邮件", param);
            }
            redisUtils.delete(RedisKeys.VERIFY_EMAIL + param);
            pwd = pwd.substring(6, pwd.length() - 6);
        }

        return userInfoService.retrievePwd(type, param, pwd + passwordSalt);
    }
}
