package com.faceshow.modules.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.message.tencent.EmailSendProduct;
import com.faceshow.common.MQ.message.tencent.SMSSendProduct;
import com.faceshow.common.utils.*;
import com.faceshow.common.utils.alipay.MailKeys;
import com.faceshow.common.utils.alipay.MailUtils;
import com.faceshow.common.utils.alipay.SendSMS;
import com.faceshow.common.utils.alipay.SmsKeys;
import com.faceshow.common.utils.mapbean.MapResultBean;
import com.faceshow.common.validator.Assert;
import com.faceshow.modules.IM.Controller.ImImportController;
import com.faceshow.modules.IM.Imutils;
import com.faceshow.modules.IM.dao.ImFriendDao;
import com.faceshow.modules.emotion.dao.EmotionInfoDao;
import com.faceshow.modules.friend.dao.FriendRingDao;
import com.faceshow.modules.gift.dao.GiftInfoDao;
import com.faceshow.modules.hobby.entity.HobbyInfo;
import com.faceshow.modules.hobby.service.HobbyInfoService;
import com.faceshow.modules.job.dao.JobUserDao;
import com.faceshow.modules.language.LanguageSelectRowVo;
import com.faceshow.modules.language.dao.LanguageCanDao;
import com.faceshow.modules.live.dao.LiveInfoDao;
import com.faceshow.modules.makeFriend.dao.MakeFriendIntentionDao;
import com.faceshow.modules.makeFriend.vo.MakeFriendIntentionSelectRowVo;
import com.faceshow.modules.report.service.ReportInfoService;
import com.faceshow.modules.sys.dao.SysCountryDao;
import com.faceshow.modules.sys.entity.SysCountry;
import com.faceshow.modules.sys.service.SysUserTokenService;
import com.faceshow.modules.user.dao.UserAttentionDao;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.user.dao.UserInfoDetailDao;
import com.faceshow.modules.user.dao.UserSetDao;
import com.faceshow.modules.user.entity.UserAttention;
import com.faceshow.modules.user.entity.UserInfo;
import com.faceshow.modules.user.entity.UserInfoDetail;
import com.faceshow.modules.user.entity.UserSet;
import com.faceshow.modules.user.service.UserInfoService;
import com.faceshow.modules.user.vo.MessagePushSummary;
import com.faceshow.modules.user.vo.UserInfoAroundRowVo;
import com.faceshow.modules.user.vo.UserInfoEditorDataRowVo;
import com.github.qcloudsms.SmsSingleSenderResult;
import io.netty.util.internal.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Athor GuoChao
 * @Date Create in 13:13 2018/1/15
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private HobbyInfoService hobbyInfoService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

    @Autowired
    private ReportInfoService reportInfoService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserSetDao userSetDao;

    @Autowired
    private UserInfoDetailDao userInfoDetailDao;

    @Autowired
    private SysCountryDao sysCountryDao;

    @Autowired
    private ImFriendDao imFriendDao;

    @Autowired
    private UserAttentionDao userAttentionDao;

    @Autowired
    private LanguageCanDao languageCanDao;

    @Autowired
    private FriendRingDao friendRingDao;

    @Autowired
    private MakeFriendIntentionDao makeFriendIntentionDao;

    @Autowired
    private EmotionInfoDao emotionInfoDao;

    @Autowired
    private JobUserDao jobUserDao;

    @Autowired
    private LiveInfoDao liveInfoDao;

    @Autowired
    private GiftInfoDao giftInfoDao;

    @Autowired
    private ImImportController imImportController;


    /**
     * 保存用户信息
     *
     * @param record
     * @return 用户主键
     */
    @Override
    @Transactional
    public Object insertSelective(UserInfo record) {

        // 校验邮箱是否存在
        String email = record.getEmail();
        if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(checkEmail(email)) && !checkEmail(email).equals("1")) {
            return R.result(0, "该邮箱已经注册", "");
        }
        // 校验电话是否存在
        if (StringUtils.isNotBlank(record.getMobile()) && StringUtils.isNotBlank(checkMobile(record.getMobile()))) {
            return R.result(0, "该电话已经注册", "");
        }
        // 校验用户名是否存在
        if (StringUtils.isNotBlank(record.getUserName()) && StringUtils.isNotBlank(checkMobile(record.getUserName()))) {
            return R.result(0, "该用户名已被占用", "");
        }

        record.setLoginTime(new Date());
        record.setRegTime(new Date());

        // 根据经纬度获取国家信息
        MapResultBean msg = BaiduMapUtils.getMsg(record.getLatitude() + "," + record.getLongitude());
        // 获取国家唯一编码标识
        String coding = msg.getResult().getAddressComponent().getCountry_code_iso2();
        // 定位失败, 不进行下一步操作
        if (StringUtils.isBlank(coding)) {
            return R.result(0, "定位失败", "");
        }
        // 设置国家信息
        record.setCountry(coding);
        // 设置位置信息
        record.setAddress(msg.getResult().getFormatted_address());
        // 根据国家唯一编码标识获取国家信息
        SysCountry country = sysCountryDao.findCountryByCoding(coding);
        record.setCountryId(country.getCountryId());

        userInfoDao.insertSelective(record);

        // 向腾讯云注册账号, 设置注册参数
        Map<String, Object> accountImport = new HashMap<String, Object>(0);
        accountImport.put("Identifier", String.valueOf(record.getUserId()));
        accountImport.put("Nick", record.getNickName() != null ? record.getNickName() : "");
        accountImport.put("FaceUrl", record.getImg() != null ? record.getImg() : "");
        accountImport.put("Type", 0);

        // 进行注册
        Object o = imImportController.accountImport(accountImport);
        Map<String, Object> parse = (Map<String, Object>) JSON.parse(o.toString());

        // 判断注册结果
        if (parse.get("ErrorCode") != null && parse.get("ErrorCode").equals(0)) {
            // 腾讯云账号注册成功
            try {
                String tls = imImportController.createTls(Imutils.SDKAPPID, String.valueOf(record.getUserId()));
                record.setTencentSignature(tls);
                // 将签名保存到数据库中
                userInfoDao.updateTenSignAndLongitudeAndLatitudeByUserId(record);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 设置距离
        UserSet userSet = new UserSet(record.getUserId(), new Date());
        reportInfoService.insertDistance(userSet);

        // 生成token
        R r = sysUserTokenService.createToken(record.getUserId());

        UserInfo userInfo = userInfoDao.selectByPrimaryKey(record.getUserId());
        userInfo.setPwd(null);
        userInfo.setUserSet(userSetDao.selectByDistance(userInfo.getUserId()));

        //查询用户详情
        UserInfoDetail detail = new UserInfoDetail(userInfo.getUserId(), userInfo.getUserName());
        userInfoDetailDao.insertSelective(detail);
        userInfo.setUserInfoDetail(userInfoDetailDao.selectByPrimaryKey(detail.getDetailId()));
        userInfo.setCountryFlag(country.getLogo());

        // 初始化lvl设置
        liveInfoDao.insertLive_v_set(userInfo.getUserId());
        // 初始化推送通知设置
        userInfoDao.insertMessage_push_user_set(userInfo.getUserId());
        r.put("msg", "注册成功").put("result", userInfo);

        // 将用户信息保存到redis中
        redisUtils.set(RedisKeys.USER_INFO + userInfo.getUserId(), userInfo, RedisUtils.NOT_EXPIRE);
        // 将用户唯一信息存入redis中
        saveMsgToRedis(userInfo.getMobile(), userInfo.getEmail(), userInfo.getUserName());

        return r;
    }

    @Override
    public UserInfo selectByPrimaryKey(Integer userId) {
        UserInfo userInfo = userInfoDao.selectByPrimaryKey(userId);
        if (userInfo != null) {
            // 将用户信息保存到redis中
            redisUtils.set(RedisKeys.USER_INFO + userInfo.getUserId(), userInfo, RedisUtils.NOT_EXPIRE);
        }
        return userInfo;
    }

    @Override
    public UserInfo updateByPrimaryKeySelective(UserInfo record) {

        int i = userInfoDao.updateByPrimaryKeySelective(record);
        if (i > 0) {
            UserInfo userInfo = userInfoDao.selectByPrimaryKey(record.getUserId());

            // 将用户信息保存到redis中
            redisUtils.set(RedisKeys.USER_INFO + userInfo.getUserId(), userInfo, RedisUtils.NOT_EXPIRE);

            return userInfo;
        }
        return null;
    }

    /**
     * 发送手机短信验证码
     *
     * @param mobile 手机号码
     * @param type   0 注册, 1找回密码
     * @return
     */
    @Override
    public int sendCodeByMobile(String mobile, Integer type, String areaCode) {
        // 判断手机号码是否已经存在
        String strMobile = checkMobile(mobile);
        boolean flag = StringUtils.isNotBlank(strMobile);
        if (!flag && type == 1) {
            // 手机号码不存在, 不可以进行找回密码操作
            return -1;
        }
        if (flag && type == 0) {
            // 手机号码存在, 不可以进行注册操作
            return -2;
        }

        // 生成短信验证码
        int code = InvertCodeGenerator.getNumber();

        redisUtils.set(RedisKeys.VERIFY_MOBILE + mobile, code, 5 * 60);


        // 发送短信验证码
        flag = "86".equals(areaCode);
        if (type == 0) {
            // 注册
            SMSSendProduct.pushFriendAt(0, areaCode, mobile, flag ? SmsKeys.Domestic.signUp(code, 5) : SmsKeys.Foreign.signUp(code, 5));
            return 1;
        } else if (type == 1) {
            // 找回密码
            SMSSendProduct.pushFriendAt(0, areaCode, mobile, flag ? SmsKeys.Domestic.paswordReset(code) : SmsKeys.Foreign.paswordReset(code));
            return 1;
        }
        return -3;
    }

    /**
     * 发送手机短信验证码
     *
     * @param mobile   手机号码
     * @param userid   用户id
     * @param areaCode 国家区号
     * @return
     * @author:liyan
     */
    @Override
    public int sendCodeByOldMobile(String mobile, Integer userid, String areaCode) {
        // 判断手机号码是否已经存在
        if (StringUtils.isNotBlank(checkMobile(mobile))) {
            // 手机号码存在, 不可以绑定
            return -2;
        }

        // 手机号码不存在,可以进行绑定, 生成短信验证码
        int code = InvertCodeGenerator.getNumber();
        redisUtils.set(RedisKeys.VERIFY_MOBILE + mobile, code, 5 * 60);
        // 发送短信验证码
        SMSSendProduct.pushFriendAt(0, areaCode, mobile, "86".equals(areaCode) ? SmsKeys.Domestic.accountUpgrades(code) : SmsKeys.Foreign.accountUpgrades(code));
        return 1;
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱号码
     * @return
     */
    @Override
    public int sendCodeByEmail(String email) {
        // 判断用户是否存在
        if (StringUtils.isBlank(checkEmail(email))) {
            return -1;
        }

        // 生成令牌
        String code = UUID.randomUUID().toString().replace("-", "");
        redisUtils.set(RedisKeys.VERIFY_EMAIL + email, code, 60 * 60 * 12);
        // 发送邮件
        EmailSendProduct.pushFriendAt(email, MailKeys.ChangePassword.TITLE, MailKeys.ChangePassword.changePwd(email, code));
        return 1;
    }

    /**
     * 校验输入验证码是否正确
     *
     * @param mobile 手机号码
     * @param code   验证码
     * @return
     */
    @Override
    public Integer checkCode(String mobile, String code) {
        if (StringUtils.isBlank(mobile)) {
            return -1;
        }
        String sysCode = redisUtils.get(RedisKeys.VERIFY_MOBILE + mobile);
        if (StringUtils.isBlank(sysCode) || !sysCode.equals(code)) {
            return -1;
        }
        return 1;
    }

    /**
     * 验证邮箱是否已经存在
     *
     * @param email 邮箱号码
     * @return
     */
    @Override
    public String checkEmail(String email) {
        // 从redis中获取邮箱
        String s = redisUtils.get(RedisKeys.USER_INFO_EMAIL + email);
        if (StringUtils.isBlank(s)) {
            // 从数据库中获取邮箱
            List<Integer> list = userInfoDao.checkEmail(email);
            return list.size() > 0 ? "11" : null;
        }
        return s;
    }

    /**
     * 验证电话号码是否已经存在
     *
     * @param mobile 电话号码
     * @return
     */
    @Override
    public String checkMobile(String mobile) {
        // 从redis中获取电话号码
        String s = redisUtils.get(RedisKeys.USER_INFO_MOBILE + mobile);
        if (StringUtils.isBlank(s)) {
            // 从数据库中获取电话号码
            List<Integer> list = userInfoDao.checkMobile(mobile);
            return list.size() > 0 ? "1" : null;
        }
        return s;
    }

    /**
     * 验证用户名是否已经存在
     *
     * @param userName 用户名
     * @return
     */
    @Override
    public String checkUserName(String userName) {
        // 从redis中获取用户名
        String s = redisUtils.get(RedisKeys.USER_INFO_USERNAME + userName);
        if (StringUtils.isBlank(s)) {
            // 从数据库中获取
            List<Integer> list = userInfoDao.checkUserName(userName);
            return list.size() > 0 ? "1" : null;
        }
        return s;
    }

    /**
     * 忘记密码，找回密码
     *
     * @param type  类型, 1 手机找回密码, 2邮箱找回密码
     * @param pwd   新密码
     * @param param 手机号或邮箱或userId
     * @return
     */
    @Override
    public Object retrievePwd(Integer type, String param, String pwd) {
        UserInfo userInfo = new UserInfo();
        userInfo.setPwd(DigestUtils.sha256Hex(pwd));

        if (type == 1) {
            if (StringUtils.isBlank(checkMobile(param))) {
                return R.result(0, "手机号码不存在", param);
            }

            if (userInfoDao.findUserByMobile(param).getPwd().equals(userInfo.getPwd())) {
                return R.result(0, "新密码不能与旧密码一致", param);
            }

            userInfo.setMobile(param);
            userInfoDao.updatePwdByMobile(userInfo);
            return R.result(1, "密码修改成功", param);
        }
        if (type == 2) {
            if (StringUtils.isBlank(checkEmail(param))) {
                return R.result(0, "该邮箱不存在", param);
            }

            if (userInfoDao.findUserByEmail(param).getPwd().equals(userInfo.getPwd())) {
                return R.result(0, "新密码不能与旧密码一致", param);
            }

            userInfo.setEmail(param);
            userInfoDao.updatePwdByEmail(userInfo);
            return R.result(1, "密码修改成功, 请去客户端登录", param);
        }

        return R.result(0, "密码修改失败", param);
    }

    /**
     * 登录状态下重置密码
     *
     * @param userId 用户id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return
     */
    @Override
    public Object resetPwd(Integer userId, String oldPwd, String newPwd) {

        UserInfo userInfo = userInfoDao.selectByPrimaryKey(userId);

        if (!userInfo.getPwd().equals(DigestUtils.sha256Hex(oldPwd))) {
            // 旧密码输入错误
            return R.result(0, "旧密码输入错误", "");
        }
        userInfo.setPwd(DigestUtils.sha256Hex(newPwd));

        Integer i = userInfoDao.updatePwdByUserId(userInfo);
        if (i > 0) {
            return R.result(1, "密码修改成功", "");
        }
        return R.result(0, "密码修改失败", "");
    }

    /**
     * 用户登录
     *
     * @param userInfo 用户信息
     * @return
     */
    @Override
    @Transactional
    public UserInfo login(UserInfo userInfo) {
        Byte type = userInfo.getType();
        UserInfo result = null;
        if (type == null) {
            return null;
        }
        // 用户名密码登录
        if (type == 1) {
            result = userInfoDao.findUserByUserName(userInfo.getUserName());
        } else if (type == 2) {//邮箱密码登录
            result = userInfoDao.findUserByEmail(userInfo.getEmail());
        } else if (type == 3) {// 手机号码登录
            result = userInfoDao.findUserByMobile(userInfo.getMobile());
        }

        if (type >= 1 && type <= 3) {
            if (result != null) {
                Date loginTime = result.getLoginTime();
                result.setLoginTime(new Date());
                result.setLastTime(loginTime);
                result.setLongitude(userInfo.getLongitude());
                result.setLatitude(userInfo.getLatitude());
                try {
                    // 修改腾讯签名
                    String tls = imImportController.createTls(Imutils.SDKAPPID, String.valueOf(result.getUserId()));
                    result.setTencentSignature(tls);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                userInfoDao.updateByPrimaryKeySelective(result);

                // 获取用户标签
                result.setHobbys(getHobbysByUserId(result.getUserId()));
                if (StringUtils.isNotBlank(result.getPayPassword())) {
                    result.setPayPassword("1");
                } else {
                    result.setPayPassword("0");
                }

                // 获取距离设置信息
                result.setUserSet(userSetDao.selectByDistance(result.getUserId()));

                // 获取用户金额详情
                result.setUserInfoDetail(userInfoDetailDao.selectByUserId(result.getUserId()));

                // 将用户信息保存到redis中
                redisUtils.set(RedisKeys.USER_INFO + result.getUserId(), result, RedisUtils.NOT_EXPIRE);

                // 设置国旗
                result.setCountryFlag(sysCountryDao.findCountryById(result.getCountryId()).getLogo());

                return result;
            } else {
                return null;
            }
        }

        //微信登录, QQ登录, 微博登录, facebook登录, twitter登录
        if (type >= 4 && type <= 11) {
            result = userInfoDao.findUserByUid(userInfo);
        } else {
            return null;
        }

        if (result == null) {
            Assert.isBlank(userInfo.getUid(), "获取授权失败");

            // 用户不存在, 表示第一次登录, 将用户信息保存到数据库
            userInfo.setRegTime(new Date());
            userInfo.setLoginTime(new Date());
            userInfo.setSmallImg(userInfo.getImg());

            // 根据经纬度查询国家地址信息
            MapResultBean msg = BaiduMapUtils.getMsg(userInfo.getLatitude() + "," + userInfo.getLongitude());
            // 获取国家编码唯一标识
            String coding = msg.getResult().getAddressComponent().getCountry_code_iso2();
            // 定位失败, 不进行下一步操作
            if (StringUtils.isBlank(coding)) {
                return null;
            }
            userInfo.setCountry(coding);
            // 根据国家编码获取国家id
            userInfo.setCountryId(sysCountryDao.findCountryIdByCoding(coding));
            // 获取当前位置
            userInfo.setAddress(msg.getResult().getFormatted_address());
            // 保存到数据库中
            userInfoDao.insertSelective(userInfo);

            // 把用户存入注册到腾讯云, 设置注册参数
            Map<String, Object> accountImport = new HashMap<String, Object>(0);
            accountImport.put("Identifier", String.valueOf(userInfo.getUserId()));
            accountImport.put("Nick", userInfo.getNickName() != null ? userInfo.getNickName() : "");
            accountImport.put("FaceUrl", userInfo.getImg() != null ? userInfo.getImg() : "");
            accountImport.put("Type", 0);
            // 进行注册操作
            Object o = imImportController.accountImport(accountImport);
            Map<String, Object> parse = (Map<String, Object>) JSON.parse(o.toString());

            // 判断注册结果
            if (parse.get("ErrorCode") != null && parse.get("ErrorCode").equals(0)) {
                // 注册成功, 生成签名
                try {
                    String tls = imImportController.createTls(Imutils.SDKAPPID, String.valueOf(userInfo.getUserId()));
                    userInfo.setTencentSignature(tls);
                    // 将签名保存到数据库中
                    userInfoDao.updateTenSignAndLongitudeAndLatitudeByUserId(userInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 设置距离设置信息
            UserSet userSet = new UserSet(userInfo.getUserId(), new Date());
            reportInfoService.insertDistance(userSet);

            // 查询用户信息, 进行作为返回使用
            userInfo = userInfoDao.selectByPrimaryKey(userInfo.getUserId());
            userInfo.setHobbys(getHobbysByUserId(userInfo.getUserId()));
            if (StringUtils.isNotBlank(userInfo.getPayPassword())) {
                userInfo.setPayPassword("1");
            } else {
                userInfo.setPayPassword("0");
            }
            userInfo.setUserSet(userSetDao.selectByPrimaryKey(userSet.getSetId()));

            //添加用户金额详情
            UserInfoDetail userInfoDetail = new UserInfoDetail(userInfo.getUserId(), userInfo.getUserName());
            userInfoDetailDao.insertSelective(userInfoDetail);
            userInfo.setUserInfoDetail(userInfoDetailDao.selectByPrimaryKey(userInfoDetail.getDetailId()));
            // 设置国旗
            userInfo.setCountryFlag(sysCountryDao.findCountryById(userInfo.getCountryId()).getLogo());
            // 将用户信息保存到redis中
            redisUtils.set(RedisKeys.USER_INFO + userInfo.getUserId(), userInfo, RedisUtils.NOT_EXPIRE);

            // 初始化lvl设置
            liveInfoDao.insertLive_v_set(userInfo.getUserId());
            // 初始化推送通知设置
            userInfoDao.insertMessage_push_user_set(userInfo.getUserId());

            return userInfo;
        }

        // 如果用户信息存在, 修改用户信息, 返回数据
        Date loginTime = result.getLoginTime();
        result.setLoginTime(new Date());
        result.setLastTime(loginTime);
        try {
            // 修改腾讯签名
            String tls = imImportController.createTls(Imutils.SDKAPPID, String.valueOf(result.getUserId()));
            result.setTencentSignature(tls);
        } catch (Exception e) {
            e.printStackTrace();
        }

        userInfoDao.updateByPrimaryKeySelective(result);
        result.setHobbys(getHobbysByUserId(result.getUserId()));
        if (StringUtils.isNotBlank(result.getPayPassword())) {
            result.setPayPassword("1");
        } else {
            result.setPayPassword("0");
        }
        result.setUserSet(userSetDao.selectByDistance(result.getUserId()));
        result.setUserInfoDetail(userInfoDetailDao.selectByUserId(result.getUserId()));

        // 设置国旗
        result.setCountryFlag(sysCountryDao.findCountryById(result.getCountryId()).getLogo());

        // 将用户信息保存到redis中
        redisUtils.set(RedisKeys.USER_INFO + result.getUserId(), result, RedisUtils.NOT_EXPIRE);
        return result;
    }

    /**
     * 将用户信息保存到redis中
     *
     * @param mobile   手机号码
     * @param email    邮箱
     * @param username 用户名
     */
    private void saveMsgToRedis(String mobile, String email, String username) {
        if (StringUtils.isNotBlank(mobile)) {
            redisUtils.set(RedisKeys.USER_INFO_MOBILE + mobile, mobile, RedisUtils.NOT_EXPIRE);
        }
        if (StringUtils.isNotBlank(email)) {
            redisUtils.set(RedisKeys.USER_INFO_EMAIL + email, email, RedisUtils.NOT_EXPIRE);
        }
        if (StringUtils.isNotBlank(mobile)) {
            redisUtils.set(RedisKeys.USER_INFO_USERNAME + username, username, RedisUtils.NOT_EXPIRE);
        }

    }

    /**
     * 个人中心 需要返回的数据 包括头像 魅力值等等
     */
    @Override
    public Object getInfoDetail(int userId) {
        return userInfoDao.getInfoDetail(userId);
    }


    /**
     * @Author:YS
     * @Description: APP端需要返回的数据 是粉丝页面 返回头像 个人头像、昵称、个性签名、互为关注
     * @Date:2018/1/23
     */
    @Override
    public List<Object> getFansDetail(Map<String, Object> map) {
        return userInfoDao.getFansDetail(map);
    }

    /**
     * @Author:YS
     * @Description: APP端传递用户ID 返回这个用户得到的所有的礼物 返回的字段有名字 图片 和价格
     * @Date:2018/1/23
     */
    @Override
    public List<Object> getGiftDetail(Map<String, Object> map) {
        // 查询礼物的名字, 图片, 单价, 数量等信息
        List<Object> giftDetailList = userInfoDao.getGiftDetail(map);

        for (Object obj : giftDetailList) {
            Map<String, Object> giftDetail = (Map<String, Object>) obj;
            Map<String, Object> map1 = userInfoDao.getGiftDetailByUserIdAndAccount(giftDetail);
            giftDetail.put("nick_name", map1.get("nick_name"));
            giftDetail.put("create_time", map1.get("create_time"));
        }
        return giftDetailList;
    }

    @Override
    public int getGiftDetailTotal(Map<String, Object> map) {//分页
        return userInfoDao.getGiftDetailTotal(map);//分页
    }


    /**
     * @Author:YS
     * @Description:APP端传递用户ID 返回这个用户赠送的所有的礼物 返回的字段有名字 图片 和价格
     * @Date:2018/2/28
     * @param:传递用户ID
     */

    @Override
    public List<Object> ZgetGiftDetail(Map<String, Object> map) {
        return userInfoDao.ZgetGiftDetail(map);
    }

    @Override
    public int ZgetGiftDetailTotal(Map<String, Object> map) {
        return userInfoDao.ZgetGiftDetailTotal(map);
    }

    /*我的视频页面*/
    @Override
    public List<Object> getMyVideo(Map<String, Object> map) {
        return userInfoDao.getMyVideo(map);
    }

    //分页
    @Override
    public int getMyVideoTotal(Map<String, Object> map) {
        return userInfoDao.getMyVideoTotal(map);
    }

    /**
     * @Author:YS
     * @Description:6.2.1.6.1.2公开视频礼物页面
     * @Date:2018/2/1
     */

    @Override
    public List<Object> selectGift(Map<String, Object> map) {
        return userInfoDao.selectGift(map);
    }

    /**
     * @Author:YS
     * @Description:查询分页条数
     * @Date:2018/2/1
     */
    @Override
    public int queryTotal(Map<String, Object> map) {
        return userInfoDao.queryTotal(map);
    }

    /**
     * @Author:YS
     * @Description: <!--6.2.1.2粉丝页面分页-->
     * @Date:2018/2/2
     */

    @Override
    public int TotalFans(Map<String, Object> map) {
        return userInfoDao.TotalFans(map);
    }

    /**
     * @Author:YS
     * @Description: 公开视频礼物页面 排行榜
     * @Date:2018/2/2
     */

    @Override
    public List<Object> GiftRanking(Map<String, Object> map) {
        return userInfoDao.GiftRanking(map);
    }

    //分页总条数
    @Override
    public int GiftRankingTotal(Map<String, Object> map) {
        return userInfoDao.GiftRankingTotal(map);
    }

    /**
     * 查询当前用户所有标签名
     *
     * @param userId 用户id
     * @return 标签名
     */
    private String getHobbysByUserId(Integer userId) {
        List<String> tags = hobbyInfoService.findHobbyTagsByUserId(userId);
        if (tags == null || tags.size() < 1) {
            return "";
        }
        return StringUtils.join(tags, ",");
    }

    /**
     * 修改绑定的邮箱，发验证
     *
     * @param email 邮箱号码
     * @return
     * @author：liyan
     */
    @Override
    public int sendRestCodeByEmail(String email, Integer userId) {
        // 判断用户是否存在
        if (StringUtils.isNotBlank(checkEmail(email))) {
            return -1;
        }

        // 生成令牌
        String code = UUID.randomUUID().toString().replace("-", "");
        redisUtils.set(RedisKeys.VERIFY_EMAIL + email + "_" + code, userId, 60 * 60 * 12);
        EmailSendProduct.pushFriendAt(email, MailKeys.ChangeEmail.TITLE, MailKeys.ChangeEmail.changeEmail(email, code));
        return 1;
    }

    /**
     * @author liyan
     * 修改邮箱
     */
    @Override
    public int updateEmailByUserId(Integer userId, String email) {
        Map<String, Object> map = new HashMap<>(0);
        map.put("userId", userId);
        map.put("email", email);
        redisUtils.set(RedisKeys.USER_INFO_EMAIL + email, email, -1);
        return userInfoDao.updateEmailByUserId(map);
    }

    /**
     * @author liyan
     * 修改支付密码
     */
    @Override
    public int updatePayByUserId(Integer userId, String payPassword) {
        Map<String, Object> mape = new HashMap<>();
        payPassword = DigestUtils.sha256Hex(payPassword);
        mape.put("userId", userId);
        mape.put("payPassword", payPassword);
        int pay = userInfoDao.updatePayByUserId(mape);
        redisUtils.delete(userId + "");
        return pay;
    }

    /**
     * 查询输入支付密码是否正确
     */
    @Override
    public Object showOldPassword(Integer userId, String payPassword) {
        String newPayPassword = DigestUtils.sha256Hex(payPassword);
        Map<String, Object> selectPayPasword = userInfoDao.selectPayPasword(userId);
        if (selectPayPasword.get("frozen").toString().equals("0")) {
            return R.result(-1, "支付密码已经冻结，请修改支付密码", "");
        }
        String oldPassword = selectPayPasword.get("pay_password").toString();
        if (oldPassword != null && oldPassword.equals(newPayPassword)) {
            return R.result(1, "一致", "");
        } else {//三次输入错误支付密码就冻结支付密码
            String num = redisUtils.get(userId + "");
            if (num != null) {
                int nums = Integer.parseInt(num) + 1;
                if (nums > 3) {
                    userInfoDao.updateFrozen(0, userId);
                    return R.result(-1, "支付密码已经冻结，请修改支付密码", "");
                } else {
                    redisUtils.set(userId + "", nums + "", 86400);
                }
            } else {
                redisUtils.set(userId + "", "1", 86400);
            }
            return R.result(0, "不一致", "");
        }
    }

    /**
     * @author liyan
     * 修改邮箱
     */
    @Override
    public int updateMobileByUserId(Integer userId, String mobile) {
        Map<String, Object> map = new HashMap<>(0);
        map.put("userId", userId);
        map.put("mobile", mobile);
        redisUtils.set(RedisKeys.USER_INFO_MOBILE + mobile, mobile, -1);
        return userInfoDao.updateMobileByUserId(map);
    }

    /**
     * @author liyan
     * 获取手机号
     */
    @Override
    public String getInfoMobile(Integer userId) {
        return userInfoDao.getInfoMobile(userId);
    }

    /**
     * @author liyan
     * 获取邮箱
     */
    @Override
    public String getInfoEmail(Integer userId) {
        return userInfoDao.getInfoEmail(userId);
    }

    /**
     * @Author:YS
     * @Description: 置顶的页面需求
     * @Date:2018/2/10
     */
    @Override
    public Object findDetail(int userId) {
        return userInfoDao.findDetail(userId);
    }

    /**
     * @Author:YS
     * @Description:<!--查询自己拥有的礼物-->
     * @Date:2018/2/28
     * @param:No userId
     */

    @Override
    public Map<String, Object> myGift(Map<String, Object> map) {
        List<Map<String, Object>> list = userInfoDao.hmyGift(map);
        List<Map<String, Object>> maps = userInfoDao.myGift(map);
        Map<String, Object> diamond = userInfoDao.findDiamondByUserId(map);
        if (list.size() != 0) {
            for (Map<String, Object> stringObjectMap : maps) {
                for (Map<String, Object> objectMap : list) {
                    if (objectMap.get("gift_id").toString().equalsIgnoreCase(stringObjectMap.get("gift_id").toString())) {
                        stringObjectMap.put("counts", objectMap.get("counts"));
                    }
                }
            }
        }
        Map<String, Object> result = new HashMap<>(0);
        result.put("gift", maps);
        if (diamond == null) {
            result.put("diamond", 0);
        } else {
            result.put("diamond", diamond.get("diamond"));
        }
        return result;
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
    @Override
    public Object findAroundMan(Map<String, Object> map) {

        // 判断是否是搜索
        if (map.get("type").toString().equalsIgnoreCase("2")) {
            // 获取现在时间
            Calendar mycalendar = Calendar.getInstance();

            // 判断开始年龄是否为空
            if (!ObjectUtils.isEmpty(map.get("statAge"))) {
                mycalendar.set(mycalendar.get(Calendar.YEAR) - Integer.parseInt(map.get("statAge").toString()), 0, 1, 0, 0, 0);
                map.put("statAge", mycalendar.getTime().getTime() / 1000);
            }
            // 判断结束年龄是否为空
            if (!ObjectUtils.isEmpty(map.get("endAge"))) {
                mycalendar.set(mycalendar.get(Calendar.YEAR) - Integer.parseInt(map.get("endAge").toString()), 11, 31, 23, 59, 59);
                map.put("endAge", mycalendar.getTime().getTime() / 1000);
            }

            // 用户id集合
            List<Integer> userIds = new ArrayList<>(0);

            // 判断是否选择交友状态
            if (!ObjectUtils.isEmpty(map.get("makeFriendIds"))) {
                userIds.addAll(makeFriendIntentionDao.findUserIdByFriendId(Stream.of(map.get("makeFriendIds").toString().split(",")).map(Integer::valueOf).collect(Collectors.toList()).toArray(new Integer[]{})));
            }

            // 判断是否选择语言
            if (!ObjectUtils.isEmpty(map.get("languages"))) {
                userIds.addAll(languageCanDao.findUserIdByLanguageId(map.get("languages").toString().split(",")));
            }

            if (userIds.size() > 0) {
                map.put("userIds", userIds.parallelStream().distinct().collect(Collectors.toList()).toArray(new Integer[]{}));
            }
        }

        Query query = new Query(map);
        // 查询附近的人列表
        List<UserInfoAroundRowVo> list = userInfoDao.findAroundManList(query);

        // 查询附近的人数量或者搜索的人的数量
        Integer total = userInfoDao.findAroundManTotal(query);
        return R.ok("OK").put("result", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * @Author:YS
     * @Description: 查询银行列表 已经增加缓存
     * @Date:2018/4/10
     * @param:
     */
    @Override
    public List selectBank() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            //从redis中取缓存数据
            String json = redisUtils.get("sys_bank");
            if (!StringUtils.isBlank(json)) {
                //把json转换成List
                List<? extends HashMap> maps = JsonUtils.jsonToList(json, map.getClass());
                return maps;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> selectBank = userInfoDetailDao.selectBank();
        //返回结果之前，向缓存中添加数据
        try {

            redisUtils.set("sys_bank", JsonUtils.objectToJson(selectBank));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectBank;
    }

    /**
     * @Author:YS
     * @Description: 插入申请表  1支付宝2微信4信用卡5PayPal支付
     * @Date:2018/4/10
     * @param:
     */
    @Override
    public Object insertWithdrawApplication(Map<String, Object> map) {
        //1 查询一下前台传递的F币 和数据库用户的F 币大小 如果前台传递F币大于了用户数据库的  说明这个用户本来就有10元想提现15元判断为F币不足
        Map<String, Object> withdrawNormById = userInfoDetailDao.selectWithdraw_normById(map);//根据ID获取提现申请表的美元和人民币
        int cf = (int) withdrawNormById.get("F");//传递过来的F币
        Map<String, Object> selectWallet = userInfoDetailDao.selectWallet(Integer.parseInt(map.get("userId").toString()));//数据库查询F币
        int f = (int) selectWallet.get("F");//数据库里面查出来的F币
        if (cf > f) {
            return R.result(0, "F币不足", "");
        }
        if (map.get("pay_type").toString().equals("5")) {
            //如果是贝宝 那么就存美元
            map.put("money", withdrawNormById.get("price_America"));
        } else {
            map.put("money", withdrawNormById.get("price_China"));
        }
        map.put("F", cf);
        userInfoDetailDao.updateDescDiamondByUserId(map);
        userInfoDetailDao.insertWithdrawApplication(map);
        map.clear();
        map.put("F", f - cf);
        return map;

    }

    /**
     * @Author:YS
     * @Description: 订单记录表
     * 1 短视频 2 直播 3 一对一
     * 8充值 9提现',
     * 4送 5收
     * @Date:2018/4/10
     * @param:
     */
    @Override
    public Object selectUser_give_away_log(Map<String, Object> map) throws Exception {
        if (map.get("time").toString().length() != 0) {
            map.put("resultTime", map.get("time"));
            String time = map.get("time").toString() + "-01";//拼接为 2008-08-08形式
            map.put("time", time);
        }
        List<Map<String, Object>> detail = null;
        if (map.get("type").toString().equals("1") || map.get("type").toString().equals("2") || map.get("type").toString().equals("3")) {
            detail = userInfoDetailDao.conditionDetail(map);//账单记录
        } else if (map.get("type").toString().equals("8") || map.get("type").toString().equals("9")) {
            detail = userInfoDetailDao.consumeConditionDetail(map);//账单记录
        } else if (map.get("type").toString().equals("4") || map.get("type").toString().equals("5")) {
            detail = userInfoDetailDao.giftConditionDetail(map);//账单记录
        } else if (map.get("time").toString().length() == 0) {//如果时间为空字符串 就查询当前月的
            detail = userInfoDetailDao.selectUser_give_away_log(map);//账单记录
        } else if (map.get("time").toString().length() != 0 && map.get("type").toString().equals("0")) {//查询传递过来的时间的所有
            detail = userInfoDetailDao.selectTimeUser_give_away_log(map);//账单记录
        }
        Map<String, Object> Income = userInfoDetailDao.selectIncome(map);//收入
        Map<String, Object> payPrice = userInfoDetailDao.selectPayPrice(map);//支出
        String time = userInfoDetailDao.selectYearMonth();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("userDetail", detail);//账单
        result.put("Income", Income == null ? 0 : Income.get("income"));//收入
        result.put("payPrice", payPrice == null ? 0 : payPrice.get("payPrice"));//支出
        if (map.get("time").toString().length() == 0) {//如果传递空字符串就是查询当月的
            result.put("time", time);//本月时间
        } else {
            String replace = map.get("resultTime").toString().replace("-", "年");
            result.put("time", replace + "月");//本月时间
        }

        return result;
    }

    /**
     * @Author:YS
     * @Description: 查询是否有支付密码
     * @Date:2018/4/9
     * @param:
     */
    @Override
    public String selectPayPassword(Integer userId) {
        return userInfoDao.selectPayPassword(userId);
    }

    /**
     * @Author:YS
     * @Description: 充值页面
     * @Date:2018/4/9
     * @param:
     */
    @Override
    public List<Map<String, Object>> selectRecharge_norm() {
        return userInfoDetailDao.selectRecharge_norm();
    }


    /**
     * @Author:YS
     * @Description: 提现页面
     * @Date:2018/4/10
     * @param:
     */
    @Override
    public List<Map<String, Object>> selectWithdraw_norm() {
        return userInfoDetailDao.selectWithdraw_norm();
    }

    /**
     * 根据用户id查询用户昵称
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public String findNickNameByUserId(Integer userId) {
        return userInfoDao.getNickNameByUserId(userId);
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
    @Override
    public Object boundUserName(Map<String, Object> map) {
        userInfoDao.boundUserName(map);
        return R.result(1, "OK", "");
    }

    /**
     * @author: YS
     * @Date:2018/4/15 13:16
     * @param:
     * @explain： <!--查找本国前三名的头像-->
     * @return:
     */
    @Override
    public List<Map<String, Object>> selectThreeImg(Map<String, Object> map) {
       /* String latitude = map.get("latitude").toString();//纬度
        String longitude = map.get("longitude").toString();//经度
        MapResultBean bean = BaiduMapUtils.getMsg(latitude + "," + longitude);
        String country_code_iso2 = bean.getResult().getAddressComponent().getCountry_code_iso2();
        map.put("country",country_code_iso2);*/
        return userInfoDao.selectThreeImg(map);
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
    @Override
    public int toggleArea(Map<String, Object> map) {
        return userInfoDao.toggleArea(map);
    }

    /**
     * 修改用户昵称
     *
     * @param userId   用户id
     * @param nickName 用户昵称
     * @return
     */
    @Override
    public int updateNickNameByUserId(Integer userId, String nickName) {
        return userInfoDao.updateNickNameByUserId(userId, nickName);
    }

    /**
     * 修改用户签名
     *
     * @param userId    用户id
     * @param signature 用户签名(自我介绍)
     * @return
     */
    @Override
    public int updateSignatureByUserId(Integer userId, String signature) {
        return userInfoDao.updateSignatureByUserId(userId, signature);
    }

    /**
     * 修改用户头像
     *
     * @param map 修改信息
     *            userId 用户id
     *            bigImgUrl 用户头像大图
     *            smallImgUrl 用户头像小图
     * @return
     */
    @Override
    public int updateImgByUserId(Map<String, Object> map) {
        return userInfoDao.updateImgByUserId(map);
    }

    /**
     * @author: YS
     * @Date:2018/4/16 20:30
     * @param:
     * @explain： 查询是否是好友 并且返回对方的 头像 等
     * @return:
     */
    @Override
    public Map<String, Object> findDetail(Map<String, Object> map) {
        int useId = Integer.parseInt(map.get("account").toString());
        Map<String, Object> infoDetail = userInfoDao.getInfoDetail(useId);
        Map byAllId = imFriendDao.findByAllId(map);
        if (byAllId == null) {
            infoDetail.put("is_tutual", 0);
        } else {
            infoDetail.put("is_tutual", 1);
        }
        return infoDetail;
    }

    /**
     * @author: YS
     * @Date:2018/4/17 14:53
     * @param:
     * @explain： 更新极光推送的ID
     * @return:
     */
    @Override
    public int updateRegistration_id(Map<String, Object> map) {
        return userInfoDao.updateRegistration_id(map);
    }

    /**
     * 修改用户生日
     *
     * @param userId   用户id
     * @param birthday 用户生日, yyyy-MM-dd
     * @return
     */
    @Override
    public int updateBirthdayByUserId(Integer userId, String birthday) {
        return userInfoDao.updateBirthdayByUserId(userId, birthday);
    }

    /**
     * 修改用户的现居住地址
     *
     * @param userId  用户id
     * @param country 居住国家
     * @return
     */
    @Override
    public int updateNowAdress(Integer userId, String country) {
        return userInfoDetailDao.updateNowAdress(userId, country);
    }


    /**
     * @author: YS
     * @Date:2018/4/18 20:16
     * @param:
     * @explain： 以下方法就是对 非好友用户聊天记录表主键  user_chat 进行CRUD
     * @return:
     */
    //<!--插入非好友用户聊天记录表主键-->  参数  #{user_id}, #{account}, #{create_time}
    @Override
    public int insertUser_chat(Map<String, Object> map) {
        return userInfoDao.insertUser_chat(map);
    }


    //<!--非还有用户聊天记录表 查询还有多少次聊天个数--> 参数   #{user_id} #{account}
    @Override
    public Map<String, Object> selectUser_Chat(Map<String, Object> map) {
        return userInfoDao.selectUser_Chat(map);
    }

    /**
     * 查询用户需要编辑的资料
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public Object findEditorData(Integer userId) {
        // 查询用户情感状态, 一个
        String emotions = emotionInfoDao.findByUserId(userId);
        // 查询用户的交友意向
        List<String> makeFriends = makeFriendIntentionDao.findByUserId(userId);
        //查询用户掌握的语言
        List<String> languages = languageCanDao.findLanguageNameByUserId(userId);
        // 查询用户的其他信息
        UserInfoEditorDataRowVo userInfoEditorDataRowVo = userInfoDao.findEditorData(userId);
        userInfoEditorDataRowVo.setHobby(getHobbysByUserId(userId));
        userInfoEditorDataRowVo.setMakeFriends(StringUtils.join(makeFriends, ","));
        userInfoEditorDataRowVo.setLanguages(StringUtils.join(languages, ","));
        userInfoEditorDataRowVo.setEmotions(emotions);
        return R.ok("OK").put("result", userInfoEditorDataRowVo);
    }

    //    <!--消息推送通知用户设置ID主键 查询-->
    @Override
    public Map<String, Object> SelectMessage_push_user_set(Integer userId) {
        return userInfoDao.SelectMessage_push_user_set(userId);
    }

    // <!--消息推送通知用户设置ID主键 插入-->
    @Override
    public int insertMessage_push_user_set(Integer userId) {
        return userInfoDao.insertMessage_push_user_set(userId);
    }

    @Override
    public int updateMessage_push_user_set(Map<String, Object> map) {
        return userInfoDao.updateMessage_push_user_set(map);
    }


    /**
     * 修改用户身高
     *
     * @param userId  用户id
     * @param stature 用户身高
     * @return
     */
    @Override
    public int updateStature(Integer userId, String stature) {
        return userInfoDao.updateStature(userId, stature);
    }

    /**
     * 修改用户体重
     *
     * @param userId 用户id
     * @param weight 用户体重
     * @return
     */
    @Override
    public int updateWeight(Integer userId, String weight) {
        return userInfoDao.updateWeight(userId, weight);
    }

    /**
     * 修改用户性别
     *
     * @param userId 用户id
     * @param sex    性别, 0女, 1男
     * @return
     */
    @Override
    public int updateSex(Integer userId, Byte sex) {
        // 查询用户性别是否已经设置, 2未设置, 0或1已经设置
        if (userInfoDao.findSexByUserId(userId) == 2) {
            // 已经设置
            return -1;
        }
        return userInfoDao.updateSexByUserId(userId, sex);
    }

    @Override
    public MessagePushSummary selectMessage_push_summary(Integer type_id) {
        return userInfoDao.selectMessage_push_summary(type_id);
    }

    @Override
    public List<Map<String, Object>> selectIs_anchor(Integer userId) {
        return userInfoDao.selectIs_anchor(userId);
    }

    /**
     * @author: YS
     * @Date:2018/4/26 13:59
     * @param:
     * @explain： F转换钻石表
     * @return:
     */
    @Override
    public Object selectTemplate() {
        //添加缓存
        //查询数据库之前先查询缓存，如果有直接返回
        try {
            //从redis中取缓存数据
            String json = redisUtils.get(RedisKeys.F_TRANSFORMATION_DIAMOND);
            if (!StringUtils.isBlank(json)) {
                //把json转换成List
                List<? extends HashMap> list = JsonUtils.jsonToList(json, new HashMap<String, Object>().getClass());
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> list = userInfoDetailDao.selectTemplate();

        //返回结果之前，向缓存中添加数据
        try {
            //为了规范key可以使用工具里面的
            //value是list，需要把list转换成json数据。
            redisUtils.set(RedisKeys.F_TRANSFORMATION_DIAMOND, JsonUtils.objectToJson(list), 2592000L);//一个月
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @author: YS
     * @Date:2018/4/26 14:25
     * @param:
     * @explain：处理F币转换成钻石
     * @return:
     */
    @Override
    public int f_transformation_diamond(Map<String, Object> map) {
        /*
        * 1.接受传递过来的userId 以及主键
        * 2.根据主键查找体现的金额
        * 3.更新数据库相关用户的钱
        */

        int userId = Integer.parseInt(map.get("userId").toString());//取出来userId

        Map<String, Object> templateById = userInfoDetailDao.selectTemplateById(map);
        templateById.put("userId", userId);
        userInfoDetailDao.updateDescDiamondByUserId(templateById);//根据用户id修改用户F币

        BigDecimal priceChina = (BigDecimal) templateById.get("diamond");
        int diamond = priceChina.intValue();//对应的钻石数量 数据库里面
        userInfoDetailDao.updateDiamondByUserId(userId, diamond);//修改钻石数量
/////////插入订单总表 也就是礼物总表
        map.clear();//清除里面内容 重新利用map
        String nick_name = userInfoDao.selectNick_name(userId);
        map.put("user_id", userId);
        map.put("user_name", nick_name);
        map.put("create_time", new Date());
        map.put("is_type", 10);//赠送类型 1 短视频 2 直播 3 一对一 4 聊天 5个人中心送礼物, 6 问答 7PK 8充值 9提现 10 F币转换钻石',
        map.put("price", diamond);
        map.put("ITEM_ID", templateById.get("Transformation_diamond_id"));
        giftInfoDao.insertGiveAwayLog(map);
/////////插入订单总表 也就是礼物总表

        return userInfoDetailDao.findDiamondByUserId(userId);//查找出来钻石
    }

}

