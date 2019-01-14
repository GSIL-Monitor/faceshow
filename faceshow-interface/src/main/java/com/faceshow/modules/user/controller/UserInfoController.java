package com.faceshow.modules.user.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.*;
import com.faceshow.common.validator.Assert;
import com.faceshow.modules.IM.Bean.ImPortraitSet;
import com.faceshow.modules.IM.Bean.ImProfileItemPortraitSet;
import com.faceshow.modules.IM.Controller.ImFriendController;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.user.entity.UserInfo;
import com.faceshow.modules.user.service.UserInfoDetailService;
import com.faceshow.modules.user.service.UserInfoService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 用户个人信息操作相关<br>
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
@RequestMapping("/tokens/user/info")
public class UserInfoController extends AbstractController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ImFriendController imFriendController;

    @Autowired
    private UserInfoDetailService userInfoDetailService;

    @Autowired
    private ThumbnailatorUtils thumbnailatorUtils;

    /**
     * 密码盐
     */
    @Value("${passwordSalt}")
    private String passwordSalt;

    /**
     * 修改用户信息
     *
     * @param userInfo 接收的参数
     * @return
     */
    @SysLog
    @PostMapping("/updateUserInfo")
    public Object updateInfo(MultipartFile imgFile, UserInfo userInfo, HttpServletRequest request) {
        userInfo.setUserId(getUserInfoId());

        logger.info("更新用户信息, 更新用户id为: " + userInfo.getUserId());

        if (imgFile != null && imgFile.getSize() > 0) {
            try {
                Map<String, Object> map = thumbnailatorUtils.uploadFileAndCreateThumbnail1(imgFile);
                userInfo.setImg(map.get("bigImgUrl").toString());
                userInfo.setSmallImg(map.get("smallImgUrl").toString());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("用户头像更新上传到fastdfs失败, 异常信息为: " + e);
            }
        }

        // 更新腾讯云信息
        ImPortraitSet imPortraitSet1 = new ImPortraitSet();
        imPortraitSet1.setFrom_Account(userInfo.getUserId().toString());

        ArrayList<ImProfileItemPortraitSet> imProfileItemPortraitSets = new ArrayList<>();

        if (StringUtils.isNotBlank(userInfo.getNickName())) {
            ImProfileItemPortraitSet imProfileItemPortraitSet = new ImProfileItemPortraitSet();
            imProfileItemPortraitSet.setTag("Tag_Profile_IM_Nick");
            imProfileItemPortraitSet.setValue(userInfo.getNickName());

            imProfileItemPortraitSets.add(imProfileItemPortraitSet);
        }

        if (StringUtils.isNotBlank(userInfo.getImg())) {
            ImProfileItemPortraitSet imProfileItemPortraitSet1 = new ImProfileItemPortraitSet();
            imProfileItemPortraitSet1.setTag("Tag_Profile_IM_Image");
            imProfileItemPortraitSet1.setValue(userInfo.getImg());

            imProfileItemPortraitSets.add(imProfileItemPortraitSet1);
        }

        if (imProfileItemPortraitSets.size() > 0) {
            imPortraitSet1.setProfileItem(imProfileItemPortraitSets);
            imFriendController.portrait_set(imPortraitSet1);
            logger.info("更新用户信息的同时, 更新改用户在腾讯云的注册信息, 操作用户id为: " + userInfo.getUserId());
        }


        UserInfo result = userInfoService.updateByPrimaryKeySelective(userInfo);
        if (result == null) {
            logger.warn("用户信息更新失败, 操作用户id为: " + userInfo.getUserId());
            return R.result(0, "信息修改失败", result);
        }
        result.setPwd(null);
        logger.info("用户信息更新成功, 操作用户id为: " + userInfo.getUserId());

        return R.ok().put("result", result).put("code", 1).put("msg", "信息修改成功");
    }

    /**
     * 登录状态下重置密码
     *
     * @param userId 用户id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return
     */
    @SysLog
    @PostMapping("/resetPwd")
    public Object resetPwd(Integer userId, String oldPwd, String newPwd) {
        return userInfoService.resetPwd(getUserInfoId(), oldPwd + passwordSalt, newPwd + passwordSalt);
    }


    /**
     * 点击个人中心
     * 所需要返回的数据
     *
     * @param userId 用户id
     */
    @SysLog
    @PostMapping("/getInfoDetail")
    public Object getInfoDetail(int userId) {
        Object infoDetail = userInfoService.getInfoDetail(getUserInfoId());
        return R.result(1, "OK", infoDetail);
    }

    /**
     * 点击我的粉丝页面
     * 所需要返回的数据
     *
     * @param :map 用户id
     */
    @SysLog
    @PostMapping("/getFansDetail")
    public Object getFansDetail(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = userInfoService.getFansDetail(query);
        int total = userInfoService.TotalFans(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * @Author:YS
     * @Description: APP端传递用户ID 返回这个用户得到的所有的礼物 返回的字段有名字 图片 和价格
     * @Date:2018/1/23
     */
    @SysLog
    @PostMapping("/getGiftDetail")
    public Object getGiftDetail(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail;
        int total;
        if (map.get("type").toString().equals("1")) {//1我得到的所有礼物
            infoDetail = userInfoService.getGiftDetail(map);
            total = userInfoService.getGiftDetailTotal(query);
        } else if (map.get("type").toString().equals("2")) {//2我送出的所有礼物
            infoDetail = userInfoService.ZgetGiftDetail(query);
            total = userInfoService.ZgetGiftDetailTotal(query);
        } else {
            Object myGift = userInfoService.myGift(map);
            return R.result(1, "OK", myGift);
        }
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
       /* List<Object> infoDetail = userInfoService.getGiftDetail(map);
        return R.result(1, "OK", infoDetail);
*/
    }

    /**
     * @Author:YS #{is_private} '是否是私密视频 0私有 1公开',
     * #{userId}  5
     * @Description: APP端传递用户ID 返回这个用户得到的所有的礼物 返回的字段有名字 图片 和价格
     * @Date:2018/1/24
     */
    @SysLog
    @PostMapping("/getMyVideo")
    public Object getMyVideo(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = userInfoService.getMyVideo(query);
        int total = userInfoService.getMyVideoTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }


    /**
     * @Author:YS
     * @Description:6.2.1.6.1.2公开视频礼物页面
     * @Date:2018/2/2
     */
    @SysLog
    @PostMapping("/getGift")
    public Object getGift(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = userInfoService.selectGift(query);
        int total = userInfoService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * @Author:YS
     * @Description:6.2.1.6.1.2公开视频礼物页面 排行榜
     * @Date:2018/2/2
     */
    @SysLog
    @PostMapping("/GiftRanking")
    public Object GiftRanking(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = userInfoService.GiftRanking(query);
        int total = userInfoService.GiftRankingTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * liyan
     * 显示绑定的邮箱
     */
    @SysLog
    @PostMapping("/getInfoEmail")
    public Object getInfoEmail(Integer userId) {
        String oldEmail = userInfoService.getInfoEmail(getUserInfoId());
        if (oldEmail != null) {
            return R.result(1, "已经绑定过邮件", oldEmail);

        } else {
            return R.result(0, "未绑定邮箱", "");
        }
    }

    /**
     * liyan
     * 显示绑定的手机号
     */
    @SysLog
    @PostMapping("/getInfoMobile")
    public Object getInfoMobile(Integer userId) {
        String oldMobile = userInfoService.getInfoMobile(getUserInfoId());
        if (oldMobile != null) {

            return R.result(1, "已经绑定过手机号", oldMobile);
        } else {
            return R.result(0, "未绑定手机号", "");
        }
    }

    /**
     * liyan
     * 修改绑定的邮箱接收邮箱号码, 发送验证码
     *
     * @param email  邮箱号码
     * @param userId 用户id
     * @return 验证码发送结果
     */
    @SysLog
    @PostMapping(value = "/sendCodeByOldEmail")
    public Object sendCodeByOldEmail(String email, Integer userId) {
        Assert.isBlank(email, "邮箱号码不能为空");
        String oldEmail = userInfoService.getInfoEmail(getUserInfoId());
        if (email.equalsIgnoreCase(oldEmail)) {
            return R.result(0, "新旧邮箱相同", "");
        }
        Integer i = userInfoService.sendRestCodeByEmail(email, getUserInfoId());
        if (i > 0) {
            // 发送成功
            return R.result(1, "邮件发送成功", "");
        } else if (i == -1) {
            return R.result(0, "邮箱号码已经存在，请换邮箱", "");
        }

        return R.result(0, "邮件发送失败", "");
    }

    /**
     * 返回邮箱修改绑定邮箱的页面
     *
     * @param email 接收邮箱
     * @return
     * @author:liyan
     */
    @SysLog
    @GetMapping("/changeEmail")
    public ModelAndView changeEmail(String email, String token) {
        ModelAndView modelAndView = new ModelAndView();
        Object userId = redisUtils.get(token);
        if (userId == null) {
            modelAndView.setViewName("email_fail");
            return modelAndView;
        } else {
            Integer result = userInfoService.updateEmailByUserId(Integer.parseInt(userId.toString()), email);
            if (result > 0) {
                modelAndView.setViewName("email_success");
                return modelAndView;
            }
            modelAndView.setViewName("email_fail");
            return modelAndView;
        }

    }

    /**
     * @param mobile   手机号码
     * @param userId   用户id
     * @param areaCode 国家区号
     * @return 验证码发送结果
     * @author:liyan 接收手机号码, 发送验证码
     * 修改绑定的手机号
     */
    @SysLog
    @PostMapping(value = "/sendCodeByOldMobile")
    public Object sendCodeByMobile(String mobile, Integer userId, String areaCode) {
        String oldMobile = userInfoService.getInfoMobile(getUserInfoId());
        if (StringUtils.isNotBlank(oldMobile) && oldMobile.equals(mobile)) {
            return R.result(0, "新旧手机号相同", "");
        } else {
            Assert.isBlank(mobile, "手机号不能为空");

            Integer i = userInfoService.sendCodeByOldMobile(mobile, getUserInfoId(), areaCode);

            if (i > 0) {
                // 短信发送成功
                return R.result(1, "短信发送成功", i);
            } else if (i == -2) {
                return R.result(0, "手机号码已存在", "");
            } else {
                return R.result(0, "信息发送失败", "");
            }
        }
    }

    /**
     * 校验输入的验证码是否正确
     *
     * @param mobile 邮箱号码
     * @param code   验证码
     * @return
     * @author:liyan
     */
    @SysLog
    @PostMapping("/checkCodeByMobile")
    public Object checkCodeByMobile(String mobile, String code) {
        Object userId = redisUtils.get(mobile + code);
        if (userId == null) {
            return R.result(0, "验证码超时", "");
        }
        // 验证码正确
        Integer res = userInfoService.updateMobileByUserId(Integer.parseInt(userId.toString()), mobile);
        if (res == null || res == 0) {
            return R.result(0, "手机号绑定失败", "");
        } else {
            return R.result(1, "手机号绑定成功", "");
        }
    }

    /**
     * @author liyan
     * 修改支付密码
     */
    @SysLog
    @PostMapping("/updatePaypassword")
    public Object updatePayByUserId(Integer userId, String pasword) {
        Integer res = userInfoService.updatePayByUserId(getUserInfoId(), pasword + passwordSalt);
        if (res == null) {
            return R.result(0, "修改失败", "");
        } else {
            return R.result(1, "修改成功", "");
        }
    }

    /**
     * @author liyan
     * 查询原支付密码正确
     */
    @SysLog
    @PostMapping("/showOldPassword")
    public Object showOldPassword(Integer userId, String pasword) {
        Object res = userInfoService.showOldPassword(getUserInfoId(), pasword + passwordSalt);
        return res;
    }

    /**
     * @Author:YS
     * @Description:
     * @Date:2018/2/10
     */
    @SysLog
    @PostMapping("/findDetail")
    public Object findDetail(int userId) {
        Object detail = userInfoService.findDetail(userId);
        return R.result(1, "OK", detail);
    }

    /**
     * 根据用户id查询用户钻石数量
     *
     * @param userId 用户id
     * @return
     */
    @SysLog
    @PostMapping("/findDiamond")
    public Object findDiamond(Integer userId) {
        return R.result(1, "OK", userInfoDetailService.findDiamondByUserId(getUserInfoId()));
    }

    /**
     * @Author:YS
     * @Description: 根据用户ID查询用户的F 和钻石等
     * @Date:2018/4/9
     * @param:
     */
    @SysLog
    @PostMapping("/selectWallet")
    public Object selectWallet(Integer userId) {
        return R.result(1, "OK", userInfoDetailService.selectWallet(userId));
    }

    /**
     * @Author:YS
     * @Description: 查看是否设置了支付密码
     * @Date:2018/4/9
     * @param:
     */
    @SysLog
    @PostMapping("/selectPayPassword")
    public Object selectPayPassword(Integer userId) {
        Object res = userInfoService.selectPayPassword(userId);
        if (res == null) {
            return R.result(0, "您还没有设置支付密码", "");
        } else {
            return R.result(1, "已经设置了支付密码", "");
        }

    }

    /**
     * @Author:YS
     * @Description: 充值页面
     * @Date:2018/4/9
     * @param:
     */
    @SysLog
    @PostMapping("/selectRecharge_norm")
    public Object selectRecharge_norm() {
        Object res = userInfoService.selectRecharge_norm();
        return R.result(1, "OK", res);
    }

    /**
     * @Author:YS
     * @Description: 提现
     * @Date:2018/4/10
     * @param:
     */
    @SysLog
    @PostMapping("/selectWithdraw_norm")
    public Object selectWithdraw_norm() {
        Object res = userInfoService.selectWithdraw_norm();
        return R.result(1, "OK", res);
    }

    /**
     * 查询附近的人
     *
     * @param map 查询参数
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     *            longitude 经度
     *            latitude 维度
     *            nickName 用户昵称
     *            sex 性别
     *            statAge 开始年龄
     *            endAge 结束年龄
     *            makeFriendIds 交友意向
     *            nowAddress 现居住地
     *            languages 掌握的语言
     *            type 类型, 1附近的人, 2搜索的人
     * @return
     */
    @SysLog
    @PostMapping("/findAroundMan")
    public Object findAroundMan(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());

        return userInfoService.findAroundMan(map);
    }

    /**
     * @Author:YS
     * @Description: 查询所有的银行
     * @Date:2018/4/10
     * @param:No such property: code for class: Script1
     */
    @SysLog
    @PostMapping("/selectBank")
    public Object selectBank() {
        return R.result(1, "OK", userInfoService.selectBank());
    }

    /**
     * @Author:YS
     * @Description: 插入申请表
     * @Date:2018/4/10
     * @param:
     */
    @SysLog
    @PostMapping("/insertWithdrawApplication")
    public Object insertWithdrawApplication(@RequestParam Map<String, Object> map) {
        map.put("create_time", new Date());
        Object o = userInfoService.insertWithdrawApplication(map);
        return R.result(1, "OK", o);
    }

    /**
     * @Author:YS
     * @Description: 订单记录表
     * @Date:2018/4/10
     * @param:
     */
    @SysLog
    @PostMapping("/selectUser_give_away_log")
    public Object selectUser_give_away_log(@RequestParam Map<String, Object> map) throws Exception {
        Object maps = userInfoService.selectUser_give_away_log(map);
        return R.result(1, "OK", maps);
    }

    /**
     * 绑定用户名和密码
     *
     * @param map 用户信息
     *            userId 用户id
     *            param 参数
     *            pwd 密码
     *            type 类型, 1手机号码, 2邮箱号码, 3用户名
     * @return
     */
    @SysLog
    @PostMapping("/boundUserName")
    public Object boundUserName(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        map.put("pwd", DigestUtils.sha256Hex(map.get("pwd").toString() + passwordSalt));
        return userInfoService.boundUserName(map);
    }

    /**
     * @author: YS
     * @Date:2018/4/15 13:16
     * @param:
     * @explain： <!--查找本国前三名的头像-->
     * @return:
     */
    @SysLog
    @PostMapping("/selectThreeImg")
    public Object selectThreeImg(@RequestParam Map<String, Object> map) {
        Object maps = userInfoService.selectThreeImg(map);
        return R.result(1, "OK", maps);
    }

    /**
     * 切换国家和地区
     *
     * @param map 条件
     *            userId    用户id
     *            countryId 国家id
     *            coding    国家唯一编码
     * @return
     */
    @SysLog
    @PostMapping("/toggleArea")
    public Object toggleArea(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        userInfoService.toggleArea(map);
        return R.result(1, "OK", "");
    }

    /**
     * 修改用户昵称
     *
     * @param userId   用户id
     * @param nickName 用户昵称
     * @return
     */
    @SysLog
    @PostMapping("/updateNickName")
    public Object updateNickName(Integer userId, String nickName) {
        int i = userInfoService.updateNickNameByUserId(getUserInfoId(), nickName);
        return R.result(1, "OK", "");
    }

    /**
     * 修改用户签名
     *
     * @param userId    用户id
     * @param signature 用户签名(自我介绍)
     * @return
     */
    @SysLog
    @PostMapping("/updateSignature")
    public Object updateSignature(Integer userId, String signature) {
        int i = userInfoService.updateSignatureByUserId(getUserInfoId(), signature);
        return R.result(1, "OK", "");
    }

    /**
     * 修改用户头像
     *
     * @param userId 用户id
     * @param img    用户头像
     * @return
     */
    @SysLog
    @PostMapping("/updateImg")
    public Object updateImg(Integer userId, MultipartFile img) {
        if (img != null && img.getSize() > 0) {
            try {
                Map<String, Object> map = thumbnailatorUtils.uploadFileAndCreateThumbnail1(img);
                map.put("userId", getUserInfoId());
                userInfoService.updateImgByUserId(map);
                return R.result(1, "OK", map.get("bigImgUrl"));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("用户头像更新上传到fastdfs失败, 异常信息为: " + e);
            }
        }
        return R.result(0, "ERROR", "");
    }

    /**
     * @author: YS
     * @Date:2018/4/16 20:30
     * @param:
     * @explain： 查询是否是好友 并且返回对方的 头像 等
     * @return:
     */
    @SysLog
    @PostMapping("/findDetailAttention")
    public Object findDetail(@RequestParam Map<String, Object> map) {
        Map<String, Object> detail = userInfoService.findDetail(map);
        return R.result(1, "OK", detail);
    }

    /**
     * @author: YS
     * @Date:2018/4/17 14:53
     * @param:
     * @explain： 更新极光推送的ID
     * @return:
     */
    @SysLog
    @PostMapping("/updateRegistration_id")
    public Object updateRegistration_id(@RequestParam Map<String, Object> map) {
        return R.result(1, "OK", userInfoService.updateRegistration_id(map));
    }

    /**
     * 修改用户生日
     *
     * @param userId   用户id
     * @param birthday 用户生日, yyyy-MM-dd
     * @return
     */
    @SysLog
    @PostMapping("/updateBirthday")
    public Object updateBirthday(Integer userId, String birthday) {
        int i = userInfoService.updateBirthdayByUserId(getUserInfoId(), birthday);
        return R.result(1, "OK", "");
    }

    /**
     * 修改用户的现居住地址
     *
     * @param userId  用户id
     * @param country 居住国家
     * @return
     */
    @SysLog
    @PostMapping("/updateNowAdress")
    public Object updateNowAdress(Integer userId, String country) {
        int i = userInfoService.updateNowAdress(getUserInfoId(), country);
        return R.result(1, "OK", "");
    }
    /**
     * @author: YS
     * @Date:2018/4/18 20:24
     * @param:
     * @explain： //查询是否 聊过天 参数   #{user_id} #{account}
     * @return:
     */
    @SysLog
    @PostMapping("/selectUser_Chat")
    public Object selectUser_Chat(@RequestParam Map<String, Object> map) {
        Map<String, Object> userChat = userInfoService.selectUser_Chat(map);
        if (userChat==null){
            return R.result(1, "No", "");
        }
        return R.result(1, "OK", userChat);
    }

    /**
     * @author: YS
     * @Date:2018/4/20 19:19
     * @param:
     * @explain：
     * @return:
     */
    @SysLog
    @PostMapping("/SelectMessage_push_user_set")
    public Object SelectMessage_push_user_set(Integer userId) {
        Map<String, Object> userChat = userInfoService.SelectMessage_push_user_set(userId);
        return R.result(1, "OK", userChat);
    }

    /**
     * @author: YS
     * @Date:2018/4/20 19:19
     * @param:
     * @explain：
     * @return:
     */
    @SysLog
    @PostMapping("/insertMessage_push_user_set")
    public Object insertMessage_push_user_set(Integer userId) {
        int i = userInfoService.insertMessage_push_user_set(userId);
        return R.result(1, "OK", i);
    }

    /**
     * @author: YS
     * @Date:2018/4/20 19:19
     * @param:
     * @explain：
     * @return:
     */
    @SysLog
    @PostMapping("/updateMessage_push_user_set")
    public Object updateMessage_push_user_set(@RequestParam Map<String, Object> map) {
        int i = userInfoService.updateMessage_push_user_set(map);
        return R.result(1, "OK", i);
    }

    /**
     * 进入个人资料编辑页面查询需要编辑的内容
     *
     * @param userId 用户id
     * @return
     */
    @SysLog
    @PostMapping("/findEditorData")
    public Object findEditorData(Integer userId) {
        return userInfoService.findEditorData(getUserInfoId());
    }

    /**
     * 修改用户身高
     *
     * @param userId  用户id
     * @param stature 用户身高
     * @return
     */
    @SysLog
    @PostMapping("/updateStature")
    public Object updateStature(Integer userId, String stature) {
        Assert.isBlank(stature, "stature is null");
        int i = userInfoService.updateStature(getUserInfoId(), stature);
        return R.result(1, "OK", "");
    }

    /**
     * 修改用户体重
     *
     * @param userId 用户id
     * @param weight 用户体重
     * @return
     */
    @SysLog
    @PostMapping("/updateWeight")
    public Object updateWeight(Integer userId, String weight) {
        Assert.isBlank(weight, "weight is null");
        int i = userInfoService.updateWeight(getUserInfoId(), weight);
        return R.result(1, "OK", "");
    }

    /**
     * 修改用户性别
     *
     * @param userId 用户id
     * @param sex    性别, 0女, 1男
     * @return
     */
    @SysLog
    @PostMapping("/updateSex")
    public Object updateSex(Integer userId, Byte sex) {
        int i = userInfoService.updateSex(getUserInfoId(), sex);
        if (i < 1) {
            return R.result(0, "No modification allowed", "");
        }
        return R.result(1, "OK", "");
    }

    /**
     * @author: YS
     * @Date:2018/4/26 13:59
     * @param:
     * @explain： F转换钻石表
     * @return:
     */
    @SysLog
    @PostMapping("/selectTemplate")
    public Object selectTemplate() {
        Object infoDetail = userInfoService.selectTemplate();
        return R.result(1, "OK", infoDetail);
    }

    /**
     * @author: YS
     * @Date:2018/4/26 13:59
     * @param:
     * @explain： F转换钻石表
     * @return:
     */
    @SysLog
    @PostMapping("/f_transformation_diamond")
    public Object f_transformation_diamond(@RequestParam Map<String,Object> map) {
        Object infoDetail = userInfoService.f_transformation_diamond(map);
        return R.result(1, "OK", infoDetail);
    }

}
