package com.faceshow.modules.sys.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.ShiroUtils;
import com.faceshow.modules.sys.service.SysCountryService;
import com.faceshow.modules.sys.service.SysUserTokenService;
import com.faceshow.modules.user.entity.UserInfo;
import com.faceshow.modules.user.service.UserInfoService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 登录相关
 *
 * @author Gaosx
 * @email Gaoshanxi@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
public class SysLoginController {
    @Autowired
    private Producer producer;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private SysCountryService sysCountryService;

    /**
     * 密码盐
     */
    @Value("${passwordSalt}")
    private String passwordSalt;

    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @SysLog
    @RequestMapping(value = "/api/sys/login", method = RequestMethod.POST)
    public Map<String, Object> login(UserInfo userInfo) throws IOException {
        //用户信息
        UserInfo user = userInfoService.login(userInfo);

        //账号不存在、密码错误
        if (userInfo.getType() >= 1 && userInfo.getType() <= 3) {
            if (user == null || !user.getPwd().equals(DigestUtils.sha256Hex(userInfo.getPwd() + passwordSalt))) {
                return R.error("账号或密码不正确");
            }
        } else {
            if (user == null || !userInfo.getUid().equals(user.getUid())) {
                return R.error("登录失败");
            }
        }

        //账号锁定
        if (user.getStatues() == 0) {
            return R.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        R r = sysUserTokenService.createToken(user.getUserId());
        user.setPwd("");
        user.setUid("");

        r.put("result", user).put("msg", "登录成功").put("code", 1);
        return r;
    }

}
