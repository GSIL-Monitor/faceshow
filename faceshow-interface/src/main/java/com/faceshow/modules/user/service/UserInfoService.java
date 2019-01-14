package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserInfo;
import com.faceshow.modules.user.vo.MessagePushSummary;

import java.util.List;
import java.util.Map;

public interface UserInfoService {

    Object insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer userId);

    UserInfo updateByPrimaryKeySelective(UserInfo record);

    /**
     * 发送手机短信验证码
     *
     * @param mobile 手机号码
     * @return
     */
    int sendCodeByMobile(String mobile, Integer type, String areaCode);

    /**
     * 发送手机短信验证码
     *
     * @param mobile 手机号码
     * @return
     * @author:liyan
     */
    int sendCodeByOldMobile(String mobile, Integer userId, String areaCode);

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱号码
     * @return
     */
    int sendCodeByEmail(String email);

    /**
     * 校验输入验证码是否正确
     *
     * @param mobile 手机号码
     * @param code   验证码
     * @return
     */
    Integer checkCode(String mobile, String code);

    /**
     * 验证邮箱是否已经存在
     *
     * @param email 邮箱号码
     * @return
     */
    String checkEmail(String email);


    /**
     * 验证用户名是否已经存在
     *
     * @param userName 用户名
     * @return
     */
    String checkUserName(String userName);

    /**
     * 验证电话号码是否已经存在
     *
     * @param mobile 电话号码
     * @return
     */
    String checkMobile(String mobile);

    /**
     * 忘记密码，找回密码
     *
     * @param type  类型, 1 手机找回密码, 2邮箱找回密码
     * @param pwd   新密码
     * @param param 手机号或邮箱或userId
     * @return
     */
    Object retrievePwd(Integer type, String param, String pwd);

    /**
     * 登录状态下重置密码
     *
     * @param userId 用户id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return
     */
    Object resetPwd(Integer userId, String oldPwd, String newPwd);

    /**
     * 用户登录
     *
     * @param userInfo 用户信息
     * @return
     */
    UserInfo login(UserInfo userInfo);


    /**
     * 点击个人中心 返回的一些数据 包括头像 魅力值等等
     *
     * @param userId 用户信息
     * @return
     */
    Object getInfoDetail(int userId);


    /**
     * @Author:YS
     * @Description: APP端口 点击粉丝页面需要返回的数据
     * @Date:2018/1/23
     */
    List<Object> getFansDetail(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: APP端传递用户ID 返回这个用户得到的所有的礼物 返回的字段有名字 图片 和价格
     * @Date:2018/1/23
     */
    List<Object> getGiftDetail(Map<String, Object> map);

    int getGiftDetailTotal(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description:APP端传递用户ID 返回这个用户赠送的所有的礼物 返回的字段有名字 图片 和价格
     * @Date:2018/2/28
     * @param:传递用户ID
     */

    List<Object> ZgetGiftDetail(Map<String, Object> map);

    int ZgetGiftDetailTotal(Map<String, Object> map);//分页

    /**
     * @Author:YS
     * @Description: APP端我的视频页面  传递用户ID  is_private是否是私密视频 0私有 1公开'
     * @Date:2018/1/23
     */
    List<Object> getMyVideo(Map<String, Object> map);

    int getMyVideoTotal(Map<String, Object> map);//分页

    /**
     * @Author:YS
     * @Description: 6.2.1.6.1.2公开视频礼物页面
     * @Date:2018/2/1
     */
    List<Object> selectGift(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description:分页查询总条数
     * @Date:2018/2/1
     */

    int queryTotal(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description:分页查询总条数
     * @Date:2018/2/2
     */

    int TotalFans(Map<String, Object> map);


    /**
     * userid 公开视频礼物页面 排行榜
     *
     * @param :useId
     * @return
     */
    List<Object> GiftRanking(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description:分页查询总条数
     * @Date:2018/2/2
     */
    int GiftRankingTotal(Map<String, Object> map);

    /**
     * @param email
     * @return
     * @Author:liyan 发送邮件
     */
    int sendRestCodeByEmail(String email, Integer userId);

    /**
     * @author liyan
     * 修改邮箱
     */
    int updateEmailByUserId(Integer userId, String email);

    /**
     * @author liyan
     * 修改支付密码
     */
    int updatePayByUserId(Integer userId, String payPassword);

    /**
     * 查询输入支付密码是否正确
     */
    Object showOldPassword(Integer userId, String payPassword);

    /**
     * @author liyan
     * 修改邮箱
     */
    int updateMobileByUserId(Integer userId, String mobile);

    String getInfoMobile(Integer userId);

    /**
     * @author liyan
     * 获取邮箱
     */

    String getInfoEmail(Integer userId);

    /**
     * @Author:YS
     * @Description:查询用户的头像姓名和简介
     * @Date:2018/2/10
     */

    Object findDetail(int userId);

    /**
     * @Author:YS
     * @Description:<!--查询自己拥有的礼物-->
     * @Date:2018/2/28
     * @param:No userId
     */

    Map<String, Object> myGift(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查询是否有支付密码
     * @Date:2018/4/9
     * @param:No such property: code for class: Script1
     */
    String selectPayPassword(Integer userId);

    /**
     * @Author:YS
     * @Description: 充值页面
     * @Date:2018/4/9
     * @param:
     */
    List<Map<String, Object>> selectRecharge_norm();

    /**
     * @Author:YS
     * @Description: 提现页面
     * @Date:2018/4/10
     * @param:
     */
    List<Map<String, Object>> selectWithdraw_norm();

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
    Object findAroundMan(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查询银行列表
     * @Date:2018/4/10
     * @param:
     */
    List<Map<String, Object>> selectBank();

    /**
     * @Author:YS
     * @Description: 插入申请表
     * @Date:2018/4/10
     * @param:
     */
    Object insertWithdrawApplication(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 订单记录表
     * @Date:2018/4/10
     * @param:
     */
    Object selectUser_give_away_log(Map<String, Object> map) throws Exception;

    /**
     * 根据用户id查询用户昵称
     *
     * @param userId 用户id
     * @return
     */
    String findNickNameByUserId(Integer userId);

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
    Object boundUserName(Map<String, Object> map);

    /**
     * @author: YS
     * @Date:2018/4/15 13:16
     * @param:
     * @explain： <!--查找本国前三名的头像-->
     * @return:
     */
    List<Map<String, Object>> selectThreeImg(Map<String, Object> map);

    /**
     * 切换国家和地区
     *
     * @param map 条件
     *            userId    用户id
     *            countryId 国家id
     *            coding    国家唯一编码
     * @return
     */
    int toggleArea(Map<String, Object> map);

    /**
     * 修改用户昵称
     *
     * @param userId   用户id
     * @param nickName 用户昵称
     * @return
     */
    int updateNickNameByUserId(Integer userId, String nickName);

    /**
     * 修改用户签名
     *
     * @param userId    用户id
     * @param signature 用户签名(自我介绍)
     * @return
     */
    int updateSignatureByUserId(Integer userId, String signature);

    /**
     * 修改用户头像
     *
     * @param map 修改信息
     *            userId 用户id
     *            bigImgUrl 用户头像大图
     *            smallImgUrl 用户头像小图
     * @return
     */
    int updateImgByUserId(Map<String, Object> map);


    /**
     * @author: YS
     * @Date:2018/4/16 20:30
     * @param:
     * @explain： 查询是否是好友 并且返回对方的 头像 等
     * @return:
     */
    Map<String, Object> findDetail(Map<String, Object> map);

    /**
     * @author: YS
     * @Date:2018/4/17 14:53
     * @param:
     * @explain： 更新极光推送的ID
     * @return:
     */
    int updateRegistration_id(Map<String, Object> map);

    /**
     * 修改用户生日
     *
     * @param userId   用户id
     * @param birthday 用户生日, yyyy-MM-dd
     * @return
     */
    int updateBirthdayByUserId(Integer userId, String birthday);

    /**
     * 修改用户的现居住地址
     *
     * @param userId  用户id
     * @param country 居住国家
     * @return
     */
    int updateNowAdress(Integer userId, String country);

    /**
     * @author: YS
     * @Date:2018/4/18 20:16
     * @param:
     * @explain： 以下方法就是对 非好友用户聊天记录表主键  user_chat 进行CRUD
     * @return:
     */
    int insertUser_chat(Map<String, Object> map);//<!--插入非好友用户聊天记录表主键-->  参数  #{user_id}, #{account}, #{nums},#{create_time}


    Map<String, Object> selectUser_Chat(Map<String, Object> map);//  <!--非还有用户聊天记录表 --> 参数   #{user_id} #{account}


    /**
     * 查询用户需要编辑的资料
     *
     * @param userId 用户id
     * @return
     */
    Object findEditorData(Integer userId);

    Map<String,Object> SelectMessage_push_user_set(Integer userId);//    <!--消息推送通知用户设置ID主键 查询-->

    int insertMessage_push_user_set(Integer userId);// <!--消息推送通知用户设置ID主键 插入-->

    int updateMessage_push_user_set(Map<String, Object> map);//  <!--消息推送通知用户设置ID主键批量更新-->

    /**
     * 修改用户身高
     * @param userId 用户id
     * @param stature 用户身高
     * @return
     */
    int updateStature(Integer userId, String stature);

    /**
     * 修改用户体重
     * @param userId 用户id
     * @param weight 用户体重
     * @return
     */
    int updateWeight(Integer userId, String weight);

    /**
     * 修改用户性别
     *
     * @param userId 用户id
     * @param sex    性别, 0女, 1男
     * @return
     */
    int updateSex(Integer userId, Byte sex);

   /**
     * @author: YS
     * @Date:2018/4/20 20:42
     * @param:
     * @explain：<!--消息推送摘要表 前半段 后半段-->
     * @return:
     */
    MessagePushSummary selectMessage_push_summary(Integer type_id);

    /**
     * @author: YS
     * @Date:2018/4/23 14:01
     * @param:
     * @explain：<!--主播推送专用SQL-->
     * @return:
     */
    List<Map<String, Object>> selectIs_anchor(Integer userId);
    /**
     * @author: YS
     * @Date:2018/4/26 13:59
     * @param:
     * @explain： F转换钻石表
     * @return:
     */
    Object selectTemplate();
    /**
     * @author: YS
     *@Date:2018/4/26 14:25
     * @param:
     * @explain：处理F币转换成钻石
     * @return:
     */
    int f_transformation_diamond(Map<String,Object>map);

}