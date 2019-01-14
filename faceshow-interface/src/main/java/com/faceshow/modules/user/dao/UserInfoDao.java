package com.faceshow.modules.user.dao;

import com.faceshow.common.utils.Query;
import com.faceshow.modules.user.entity.UserInfo;
import com.faceshow.modules.user.vo.MessagePushSummary;
import com.faceshow.modules.user.vo.UserAttentionSetRowVo;
import com.faceshow.modules.user.vo.UserInfoAroundRowVo;
import com.faceshow.modules.user.vo.UserInfoEditorDataRowVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserInfoDao {

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserInfo record);

    /**
     * 验证邮箱是否已经存在
     *
     * @param email 邮箱号码
     * @return
     */
    List<Integer> checkEmail(String email);

    /**
     * 验证电话号码是否已经存在
     *
     * @param mobile 电话号码
     * @return
     */
    List<Integer> checkMobile(String mobile);

    /**
     * 验证用户名是否已经存在
     *
     * @param userName 用户名
     * @return
     */
    List<Integer> checkUserName(String userName);

    /**
     * 根据用户名 查找用户
     *
     * @param userName 用户名
     * @return 用户信息
     */
    UserInfo findUserByUserName(String userName);

    /**
     * 根据邮箱 查找用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    UserInfo findUserByEmail(String email);

    /**
     * 根据openid查找用户
     *
     * @param openid 微信用户唯一标识
     * @return 用户信息
     */
    UserInfo findUserByOpenid(String openid);

    /**
     * 根据第三方uid和第三方用户token查找用户
     *
     * @param userInfo 第三方用户token和第三方uid
     * @return 用户信息
     */
    UserInfo findUserByUid(UserInfo userInfo);

    /**
     * 根据电话号码 查找用户
     *
     * @param mobile 电话号码
     * @return 用户信息
     */
    UserInfo findUserByMobile(String mobile);

    /**
     * 手机修改密码
     *
     * @param userInfo
     * @return
     */
    Integer updatePwdByMobile(UserInfo userInfo);

    /**
     * 邮箱修改密码
     *
     * @param userInfo
     * @return
     */
    Integer updatePwdByEmail(UserInfo userInfo);

    /**
     * userid修改密码
     *
     * @param userInfo
     * @return
     */
    Integer updatePwdByUserId(UserInfo userInfo);


    /**
     * userid 个人中心的查询
     *
     * @param :useId
     * @return
     */
    Map<String, Object> getInfoDetail(int useId);

    /**
     * APP端点击我的粉丝 需要返回的信息
     * userid
     *
     * @param :useId
     * @return
     */
    List<Object> getFansDetail(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: APP端传递用户ID 返回这个用户得到的所有的礼物 返回的字段有名字 图片 和价格
     * @Date:2018/1/23
     */
    List<Object> getGiftDetail(Map<String, Object> map);

    Map<String, Object> getGiftDetailByUserIdAndAccount(Map<String, Object> map);

    int getGiftDetailTotal(Map<String, Object> map);//分页


    /**
     * @Author:YS
     * @Description:APP端传递用户ID 返回这个用户赠送的所有的礼物 返回的字段有名字 图片 和价格
     * @Date:2018/2/28
     * @param:传递用户ID
     */

    List<Object> ZgetGiftDetail(Map<String, Object> map);

    int ZgetGiftDetailTotal(Map<String, Object> map);//分页

    List<UserInfo> selectAll(Integer id);

    /**
     * 更新余额
     */
    //Integer upateBalance(Integer id,Double balance);
    Integer upateBalance(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: APP端我的视频页面  传递用户ID  is_private是否是私密视频 0私有 1公开'
     * @Date:2018/1/23
     */
    List<Object> getMyVideo(Map<String, Object> map);

    int getMyVideoTotal(Map<String, Object> map);//分页

    /**
     * 首次进入发现搜索页面展示数据
     *
     * @param map 分页参数
     * @return
     */
    List<Map<String, Object>> findUserByCriteria(Map<String, Object> map);

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
     * @author liyan
     * 修改邮箱
     */
    int updateEmailByUserId(Map<String, Object> map);

    /**
     * @author liyan
     * 修改手机号
     */
    int updateMobileByUserId(Map<String, Object> map);

    /**
     * 修改支付密码
     *
     * @return
     */
    int updatePayByUserId(Map<String, Object> map);

    /**
     * 查询支付密码
     *
     * @param userId
     * @return
     */
    Map<String, Object> selectPayPasword(Integer userId);

    /**
     * @author liyan
     * 获取手机号
     */
    String getInfoMobile(Integer userId);

    /**
     * @author liyan
     * 获取邮箱
     */
    String getInfoEmail(Integer userId);

    /**
     * 条件查询用户总数
     *
     * @param search
     * @return
     */
    Integer findUserTotalCount(Map<String, Object> search);

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

    List<Map<String, Object>> myGift(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 补充自己拥有的礼物
     * @Date:2018/3/27
     * @param:No such property: code for class: Script1
     */
    List<Map<String, Object>> hmyGift(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查询拥有的钻石数量
     * @Date:2018/3/30
     * @param:No such property: code for class: Script1
     */
    Map<String, Object> findDiamondByUserId(Map<String, Object> map);

    /**
     * 根据用户id修改用户腾讯云签名
     *
     * @param userInfo
     * @return
     */
    int updateTenSignAndLongitudeAndLatitudeByUserId(UserInfo userInfo);

    /**
     * 查询附近的人列表
     *
     * @param query 查询条件
     * @return
     */
    List<UserInfoAroundRowVo> findAroundManList(Query query);

    /**
     * 查询附近的人数量
     *
     * @param query 查询条件
     * @return
     */
    Integer findAroundManTotal(Query query);

    /**
     * @Author:YS
     * @Description: 查询是否有支付密码
     * @Date:2018/4/9
     * @param:No such property: code for class: Script1
     */
    String selectPayPassword(Integer userId);

    /**
     * @Author:YS
     * @Description: 修改冻结标识符
     * @Date:2018/4/9
     * @param:
     */
    int updateFrozen(@Param("frozen") int frozen, @Param("userId") int userId);

    /**
     * 根据用户id查询用户昵称
     *
     * @param userId 用户id
     * @return
     */
    String getNickNameByUserId(Integer userId);

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
    int boundUserName(Map<String, Object> map);

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
    int updateNickNameByUserId(@Param("userId") Integer userId, @Param("nickName") String nickName);

    /**
     * 修改用户签名
     *
     * @param userId    用户id
     * @param signature 用户签名(自我介绍)
     * @return
     */
    int updateSignatureByUserId(@Param("userId") Integer userId, @Param("signature") String signature);

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
     * 修改用户基本资料
     *
     * @param userId   用户id
     * @param birthday 生日
     * @return
     */
    int updateBirthdayByUserId(@Param("userId") Integer userId, @Param("birthday") String birthday);

    /**
     * @author: YS
     * @Date:2018/4/17 14:53
     * @param:
     * @explain： 更新极光推送的ID
     * @return:
     */
    int updateRegistration_id(Map<String, Object> map);

    /**
     * @author: YS
     * @Date:2018/4/17 16:11
     * @param:
     * @explain： 查询极光推送的ID 准备推送
     * @return:
     */
    String selectRegistration_id(int userId);

    String selectNick_name(int userId);//根據用戶ID查询用户的名字 极光推送用

    /**
     * @author: YS
     * @Date:2018/4/18 20:16
     * @param:
     * @explain： 以下方法就是对 非好友用户聊天记录表主键  user_chat 进行CRUD
     * @return:
     */
    int insertUser_chat(Map<String, Object> map);//<!--插入非好友用户聊天记录表主键-->  参数  #{user_id}, #{account}, #{nums},#{create_time}

    Map<String, Object> selectUser_Chat(Map<String, Object> map);//  <!--非好友用户聊天记录表 -> 参数

    Map<String, Object> SelectMessage_push_user_set(Integer userId);//    <!--消息推送通知用户设置ID主键-->

    int insertMessage_push_user_set(Map<String, Object> map);// <!--消息推送通知用户设置ID主键批量更新-->

    int insertMessage_push_user_set(Integer userId);// <!--消息推送通知用户设置ID主键批量更新-->

    int updateMessage_push_user_set(Map<String, Object> map);//  <!--消息推送通知用户设置ID主键批量更新-->

    /**
     * 查询用户需要编辑的资料
     *
     * @param userId 用户id
     * @return
     */
    UserInfoEditorDataRowVo findEditorData(Integer userId);

    /**
     * 修改用户身高
     *
     * @param userId  用户id
     * @param stature 用户身高
     * @return
     */
    int updateStature(@Param("userId") Integer userId, @Param("stature") String stature);

    /**
     * 修改用户体重
     *
     * @param userId 用户id
     * @param weight 用户体重
     * @return
     */
    int updateWeight(@Param("userId") Integer userId, @Param("weight") String weight);

    /**
     * 修改用户性别
     *
     * @param userId 用户id
     * @param sex    性别, 0女, 1男
     * @return
     */
    int updateSexByUserId(@Param("userId") Integer userId, @Param("sex") Byte sex);

    /**
     * 查询用户性别
     *
     * @param userId
     * @return
     */
    Integer findSexByUserId(Integer userId);

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
     * @Date:2018/4/20 20:43
     * @param:
     * @explain：<!--消息推送通知类型表-->
     * @return:
     */
    List<Map<String, Object>> selectMessage_push_info_type(Map<String, Object> map);

    /**
     * @author: YS
     * @Date:2018/4/20 20:43
     * @param: typeId 消息推送通知类型ID主键
     * @param: userId 推送者
     * @param: accountId 接受者
     * @param: createTime 创建时间
     * @param: conten 内容
     * @explain： <!--消息推送通知表-->
     * @return:
     */
    int insertMessage_push_info(@Param("typeId") int typeId, @Param("userId") int userId, @Param("accountId") int accountId, @Param("createTime") Date date, @Param("content") String content);

    /**
     * @author: YS
     * @Date:2018/4/23 14:01
     * @param:
     * @explain：<!--主播推送专用SQL-->
     * @return:
     */
    List<Map<String, Object>> selectIs_anchor(Integer userId);



    /**
     * 查询用户粉丝, 及推送接受设置状态
     *
     * @param userId 用户id
     * @return
     */
    List<UserAttentionSetRowVo> findVideoAttentionSet(Integer userId);

    /**
     * 批量保存站内信信息
     *
     * @param attentions 信息
     * @param date       创建时间
     * @param type       类型
     * @return
     */
    int insertMessage_push_info_batch(@Param("attentions") List<UserAttentionSetRowVo> attentions, @Param("createTime") Date date, @Param("type") Integer type, @Param("content") String content);

    /**
     * @author: YS
     * @Date:2018/4/25 14:24
     * @param:
     * @explain： <!--根据ID查询魅力值-->
     * @return:
     */
    int selectCharm_values(Integer userId);


    /**
     * @author: YS
     * @Date:2018/4/25 14:25
     * @param:
     * @explain：
     * @return:
     */
    int updateCharm_values(@Param("charm_values") Integer charm_values, @Param("userId") Integer userId);
}