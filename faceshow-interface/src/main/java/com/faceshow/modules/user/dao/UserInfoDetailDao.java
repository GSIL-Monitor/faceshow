package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserInfoDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserInfoDetailDao {
    int deleteByPrimaryKey(Integer levelId);

    int insertSelective(UserInfoDetail record);

    UserInfoDetail selectByPrimaryKey(Integer levelId);

    int updateByPrimaryKeySelective(UserInfoDetail record);

    /**
     * 查询用户拥有的钻石数量
     *
     * @param userId 用户id
     * @return 钻室数量
     */
    Integer findDiamondByUserId(Integer userId);

    /**
     * 根据用户id查询用户详情
     *
     * @param userId 用户id
     * @return
     */
    UserInfoDetail selectByUserId(Integer userId);

    /**
     * 根据用户id修改用户钻石币数量
     *
     * @param userId  用户id
     * @param diamond 钻石币数量
     * @return
     */
    int updateDiamondByUserId(@Param("userId") Integer userId, @Param("diamond") Integer diamond);

    /**
     * 根据用户id修改用户钻石币数量
     *
     * @param :F f b
     * @return
     * @param: userId  用户id
     */

    int updateDescDiamondByUserId(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查询我的钱包
     * @Date:2018/4/9
     * @param:
     */
    Map<String, Object> selectWallet(Integer userId);

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
     * @Author:YS
     * @Description: 根据主键查询提现表
     * @Date:2018/4/10
     * @param:
     */
    Map<String, Object> selectWithdraw_normById(Map<String, Object> map);

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
    int insertWithdrawApplication(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 订单记录表  当前月的
     * @Date:2018/4/10
     * @param:
     */
    List<Map<String, Object>> selectUser_give_away_log(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 订单记录表  传递月份的
     * @Date:2018/4/11
     * @param:
     */
    List<Map<String, Object>> selectTimeUser_give_away_log(Map<String, Object> map);


    /**
     * @Author:YS
     * @Description: 本月消费
     * @Date:2018/4/11
     * @param:No such property: code for class: Script1
     */

    Map<String, Object> selectPayPrice(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 本月收入
     * @Date:2018/4/11
     * @param:No such property: code for class: Script1
     */
    Map<String, Object> selectIncome(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description: 查询当前月份
     * @Date:2018/4/11
     * @param:
     */
    String selectYearMonth();


    List<Map<String, Object>> consumeConditionDetail(Map<String, Object> map);//8充值 9体现',  这个是充值和提现

    List<Map<String, Object>> conditionDetail(Map<String, Object> map);// 1 短视频 2 直播 3 一对一

    List<Map<String, Object>> giftConditionDetail(Map<String, Object> map);//4送 5收

    /**
     * 修改用户的现居住地址
     *
     * @param userId    用户id
     * @param country 居住国家id
     * @return
     */
    int updateNowAdress(@Param("userId") Integer userId, @Param("country") String country);

    /**
     * @author: YS
     * @Date:2018/4/26 13:59
     * @param:
     * @explain： F转换钻石表
     * @return:
     */
    List<Map<String,Object>>selectTemplate();

    /**
     * @author: YS
     * @Date:2018/4/26 13:59
     * @param:
     * @explain： F转换钻石表 根据主键查找
     * @return:
     */
    Map<String,Object>selectTemplateById(Map<String,Object>map);
}