package com.faceshow.modules.qa.service.impl;

import com.faceshow.common.MQ.message.likenum.QaLikeNumProduct;
import com.faceshow.common.MQ.message.push.QaBestPushProduct;
import com.faceshow.common.MQ.message.push.QaFirstPushProduct;
import com.faceshow.common.utils.*;
import com.faceshow.modules.qa.dao.*;
import com.faceshow.modules.qa.entity.*;
import com.faceshow.modules.qa.service.QaInfoService;
import com.faceshow.modules.qa.vo.QaAmountSetSelectRowVo;
import com.faceshow.modules.qa.vo.QaInfoSelectListVo;
import com.faceshow.modules.qa.vo.QaInfoSelectRowVo;
import com.faceshow.modules.qa.vo.QaReplyTopThreeSelectRowVo;
import com.faceshow.modules.user.dao.UserAttentionDao;
import com.faceshow.modules.user.dao.UserGiveAwayLogDao;
import com.faceshow.modules.user.dao.UserInfoDetailDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 问答详情操作Service接口实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:37
 * -------------------------------------------------------------- <br>
 */
@Service
@Transactional
public class QaInfoServiceImpl implements QaInfoService {

    @Autowired
    private QaInfoDao qaInfoDao;

    @Autowired
    private QaLikeDao qaLikeDao;

    @Autowired
    private QaMakeDao qaMakeDao;

    @Autowired
    private QaViewDao qaViewDao;

    @Autowired
    private QaReplyDao qaReplyDao;

    @Autowired
    private QaPayLogDao qaPayLogDao;

    @Autowired
    private UserAttentionDao userAttentionDao;

    @Autowired
    private UserInfoDetailDao userInfoDetailDao;

    @Autowired
    private UserGiveAwayLogDao userGiveAwayLogDao;

    @Autowired
    private QaAmountSetDao qaAmountSetDao;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 保存发布的问题
     *
     * @param qaInfo      问题内容信息
     * @param nickName    发布人昵称
     * @param qaAmountSet 金额设置表
     * @return
     */
    @Override
    public Object save(QaInfo qaInfo, String nickName, QaAmountSet qaAmountSet, Integer price) {
        // 保存发布记录
        qaInfoDao.save(qaInfo);

        // 获取当前用户id
        Integer userId = qaInfo.getUserId();

        // 1 无偿问答, 2 指定最佳答案, 3 前三名
        if (qaInfo.getAmountType() == 2) {
            // 发布有奖问答, 并且余额充足, 从账户中减去奖励的金额
            userInfoDetailDao.updateDiamondByUserId(userId, -price);

            // 向收支记录表中插入数据
            Map<String, Object> map = new HashMap<>(0);
            map.put("userId", userId);
            map.put("userName", nickName);
            map.put("createTime", new Date());
            map.put("isType", 6);
            map.put("price", price);
            map.put("itemId", qaInfo.getInfoId());
            userGiveAwayLogDao.insertSelective(map);

            // 向金额表中插入数据
            qaAmountSet.setInfoId(qaInfo.getInfoId());
            qaAmountSetDao.save(qaAmountSet);
        }

        // 前三名
        if (qaInfo.getAmountType() == 3) {
            // 发布有奖问答, 并且余额充足, 从账户中减去奖励的金额
            userInfoDetailDao.updateDiamondByUserId(userId, -price);

            // 向收支记录表中插入数据
            Map<String, Object> map = new HashMap<>(0);
            map.put("userId", userId);
            map.put("userName", nickName);
            map.put("createTime", new Date());
            map.put("isType", 6);
            map.put("qaAmountSet", qaAmountSet);
            map.put("itemId", qaInfo.getInfoId());

            userGiveAwayLogDao.saveBatch(map);

            // 向金额表中插入数据
            qaAmountSet.setInfoId(qaInfo.getInfoId());
            qaAmountSetDao.save(qaAmountSet);
        }

        return R.result(1, "问题发布成功", "");
    }

    /**
     * 根据用户id查询用户的提问列表
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @Override
    public Object findQaInfoListByUserId(Map<String, Object> map) {
        Query query = new Query(map);

        // 查询提问列表
        List<QaInfoSelectListVo> list = qaInfoDao.findQaInfoListByUserId(query);

        // 查询提问数量
        Integer total = qaInfoDao.findQaInfoTotalByUserId(query);
        if (total == 0) {
            return R.ok().put("page", new PageUtils(Collections.emptyList(), total, query.getLimit(), query.getPage()));
        }
        return R.ok().put("page", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * 问答点赞
     *
     * @param userId 当前用户id
     * @param infoId 问答id
     * @return
     */
    @Override
    public Object addLike(Integer userId, Integer infoId) {
        // 获取点赞数量和吐槽数量
        Map<String, Integer> map = qaInfoDao.getLikeNumAndMakeNumById(infoId);

        // 添加点赞信息
        QaLike qaLike = new QaLike();
        qaLike.setCreateTime(new Date());
        qaLike.setInfoId(infoId);
        qaLike.setUserId(userId);
        int save = qaLikeDao.save(qaLike);
        if (save > 0) {
            //将点赞信息存入redis中
            redisUtils.set(RedisKeys.QA_LIKE + infoId + "_" + userId, 1, -1);
            // 修改问答点赞数
            QaLikeNumProduct.updateQaLikeNum(infoId, 1);
        }
        return R.ok("吐槽成功").put("makeNum", map.get("makeNum")).put("likeNum", map.get("likeNum"));
    }

    /**
     * 问答取消点赞
     *
     * @param userId 当前用户id
     * @param infoId 问答id
     * @return
     */
    @Override
    public Object deleteLike(Integer userId, Integer infoId) {
        redisUtils.delete(RedisKeys.QA_LIKE + infoId + "_" + userId);
        int i = qaLikeDao.deleteByUserIdAndInfoId(userId, infoId);

        if (i > 0) {
            QaLikeNumProduct.updateQaLikeNum(infoId, -1);
        }
        return R.result(1, "取消点赞成功", "");
    }

    /**
     * 查询有奖回答列表
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @Override
    public Object findWaitQaInfo(Map<String, Object> map) {
        Query query = new Query(map);

        // 查询有奖回答列表
        List<QaInfoSelectListVo> list = qaInfoDao.findWaitQaInfoList(query);
        // 查询有奖回答信息数量
        Integer total = qaInfoDao.findWaitQaInfoTotal(query);

        return R.ok().put("page", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * 查询免费广场信息
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @Override
    public Object findFreeQaInfo(Map<String, Object> map) {
        Query query = new Query(map);

        // 查询待答广场列表
        List<QaInfoSelectListVo> list = qaInfoDao.findFreeQaInfoList(query);
        // 查询待答广场信息数量
        Integer total = qaInfoDao.findFreeQaInfoTotal(query);

        return R.ok().put("page", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * 查询问答回复状况
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            infoId 当前问答id
     * @return
     */
    @Override
    public Object findQaInfoById(Map<String, Object> map) {
        map.put("createTime", new Date());

        // 标记该问答的所有回复都是处于已查看状态
        int i = qaViewDao.update(map);
        if (i < 1) {
            // 表示该用户事首次查看该问答信息, 向问答查看表中插入一条数据
            qaViewDao.save(map);
        }

        // 根据问题id查询问题详情
        QaInfoSelectRowVo result = qaInfoDao.findQaInfoByInfoId(map);

        // 判断当前用户是否已经关注该问答回复人
        result.setIsLike(getQaInfoIsLike(result.getInfoId(), Integer.parseInt(map.get("userId").toString())));

        return R.ok("查看成功").put("result", result);
    }

    /**
     * 标记问答回复为最佳答案
     *
     * @param map 条件
     *            userId 当前用户id
     *            account 回复视频用户id
     *            accountName 回复视频用户昵称
     *            infoId 问答id
     *            amountType 问答类型
     *            replyId 回复视频id
     * @return
     */
    @Override
    public Object bestAnswer(Map<String, Object> map) {
        Integer infoId = Integer.parseInt(map.get("infoId").toString());

        // 查询该问答的支付状态, 如果已支付, 禁止第二次设置
        Integer isPay = qaAmountSetDao.findIsPayByInfoId(infoId);
        if (isPay == 1) {
            // 该问答已经有了最佳答案
            return R.result(0, "已经有了最佳答案", "");
        }

        // 问答发布支付类型(1免费,2指定,3前三名)
        int amountType = Integer.parseInt(map.get("amountType").toString());

        if (amountType == 3) {
            return R.result(1, "无法指定最佳答案", "");
        }

        // 修改问答回复状态为最佳答案
        int i = qaReplyDao.updateIsAptimalByReplyId(Integer.parseInt(map.get("replyId").toString()), 4);
        if (i < 1) {
            return R.result(0, "设置失败", "");
        }
        // 设置推送消息
        QaBestPushProduct.pushQaBest(Integer.parseInt(map.get("replyId").toString()), Integer.parseInt(map.get("userId").toString()), Integer.parseInt(map.get("account").toString()));

        if (amountType == 1) {
            // 无偿问答
            return R.result(1, "设置成功", "");
        }

        Integer userId = Integer.parseInt(map.get("userId").toString());
        Integer account = Integer.parseInt(map.get("account").toString());

        // 查询问答设置金额
        Integer price = 0;

        // 修改问答信息支付状态为已支付
        qaAmountSetDao.updateIsPayByInfoId(infoId, 1);

        if (amountType == 2) {
            // 指定最佳答案
            price = qaAmountSetDao.findPriceByInfoId(infoId);
        }

        // 向问答支付信息表中插入数据
        QaPayLog qaPayLog = new QaPayLog();
        qaPayLog.setAccountId(account);
        qaPayLog.setCreateTime(new Date());
        qaPayLog.setInfoId(infoId);
        qaPayLog.setUserId(userId);
        qaPayLog.setPrice(price);
        qaPayLog.setPayType((byte) 1);
        qaPayLogDao.save(qaPayLog);

        // 修改用户收支记录表数据
        map.put("isType", 6);
        map.put("itemId", map.get("infoId"));
        userGiveAwayLogDao.updateByUserIdAndTypeAndItemId(map);

        // 修改问答回复人的钻石币数量
        userInfoDetailDao.updateDiamondByUserId(account, price);

        return R.result(1, "设置成功", "");
    }

    /**
     * 判断是否点赞
     *
     * @param infoId 问答信息id
     * @param userId 用户id
     * @return 0 未点赞, 1 已点赞
     */
    private Integer getQaInfoIsLike(Integer infoId, Integer userId) {
        // 判断当前用户是否已经点赞
        String isLike = redisUtils.get(RedisKeys.QA_LIKE + infoId + "_" + userId);
        if (StringUtils.isBlank(isLike)) {
            return qaLikeDao.findByInfoIdAndUserId(userId, infoId);
        }
        return 1;

    }

    private Integer getQaInfoIsMake(Integer infoId, Integer userId) {
        // 判断当前用户是否已经吐槽
        String isMake = redisUtils.get(RedisKeys.QA_MAKE + infoId + "_" + userId);
        if (StringUtils.isBlank(isMake)) {
            return qaMakeDao.findByInfoIdAndUserId(userId, infoId);
        }
        return 1;
    }

    /**
     * 指定最佳的前三名
     */
    @Override
    public void bestAnswerToThree() {
        // 获取当前时间的时间戳
        long nowTime = System.currentTimeMillis();
        // 查询指定的问答信息(金额问前三名, 并且没有指定最佳答案的)
        List<QaAmountSetSelectRowVo> qainfoList = qaInfoDao.findByTopThree();

        // 判断是否到达指定时间
        for (QaAmountSetSelectRowVo qainfo : qainfoList) {
            // 问答id, 用户id
            Integer infoId = qainfo.getInfoId();
            Integer userId = qainfo.getUserId();
            // 到期时间毫秒值
            long createTimr = qainfo.getCreateTime() + qainfo.getDays() * 24 * 60 * 60 * 1000;
            if (nowTime >= createTimr) {
                // 到达指定时间
                // 获取前三名最佳答案
                List<QaReplyTopThreeSelectRowVo> qareplyList = qaReplyDao.getBestTopThreeByInfoId(infoId);
                // 获取集合长度, (判断是否少于三人回答)
                int size = qareplyList.size();

                // 指定最佳前三名
                if (size == 3) {
                    // 修改问答回复人的钻石币数量
                    userInfoDetailDao.updateDiamondByUserId(qareplyList.get(0).getUserId(), qainfo.getPrice());
                    userInfoDetailDao.updateDiamondByUserId(qareplyList.get(1).getUserId(), qainfo.getTwoPrice());
                    userInfoDetailDao.updateDiamondByUserId(qareplyList.get(2).getUserId(), qainfo.getThreePrice());

                    // 向问答支付信息表中插入数据
                    Map<String, Object> map = new HashMap<>(0);
                    map.put("accountId", qareplyList);
                    map.put("createTime", new Date());
                    map.put("infoId", infoId);
                    map.put("userId", userId);
                    map.put("price", qainfo);
                    map.put("payType", 1);
                    qaPayLogDao.saveTopThreePayLog(map);

                    // 修改用户收支记录表数据
                    map = new HashMap<>(0);
                    map.put("isType", 6);
                    map.put("userId", userId);
                    map.put("itemId", infoId);
                    map.put("remark", "1");
                    map.put("account", qareplyList.get(0).getUserId());
                    map.put("accountName", qareplyList.get(0).getNickName());
                    userGiveAwayLogDao.updateByUserIdAndTypeAndItemIdAndRemark(map);
                    map.put("remark", "2");
                    map.put("account", qareplyList.get(1).getUserId());
                    map.put("accountName", qareplyList.get(1).getNickName());
                    userGiveAwayLogDao.updateByUserIdAndTypeAndItemIdAndRemark(map);
                    map.put("remark", "3");
                    map.put("account", qareplyList.get(2).getUserId());
                    map.put("accountName", qareplyList.get(2).getNickName());
                    userGiveAwayLogDao.updateByUserIdAndTypeAndItemIdAndRemark(map);

                } else if (size == 2) {
                    // 修改问答回复人的钻石币数量
                    userInfoDetailDao.updateDiamondByUserId(qareplyList.get(0).getUserId(), qainfo.getPrice());
                    userInfoDetailDao.updateDiamondByUserId(qareplyList.get(1).getUserId(), qainfo.getTwoPrice());

                    // 向问答支付信息表中插入数据
                    Map<String, Object> map = new HashMap<>(0);
                    map.put("accountId", qareplyList);
                    map.put("createTime", new Date());
                    map.put("infoId", infoId);
                    map.put("userId", userId);
                    map.put("price", qainfo);
                    map.put("payType", 1);
                    qaPayLogDao.saveTopTwoPayLog(map);

                    // 修改用户收支记录表数据
                    map = new HashMap<>(0);
                    map.put("isType", 6);
                    map.put("userId", userId);
                    map.put("remark", "1");
                    map.put("itemId", infoId);
                    map.put("account", qareplyList.get(0).getUserId());
                    map.put("accountName", qareplyList.get(0).getNickName());
                    userGiveAwayLogDao.updateByUserIdAndTypeAndItemIdAndRemark(map);
                    map.put("remark", "2");
                    map.put("account", qareplyList.get(1).getUserId());
                    map.put("accountName", qareplyList.get(1).getNickName());
                    userGiveAwayLogDao.updateByUserIdAndTypeAndItemIdAndRemark(map);

                    // 修改问答的钻石币数量(返现)
                    userInfoDetailDao.updateDiamondByUserId(userId, qainfo.getThreePrice());
                    // 修改用户收支记录表数据
                    map = new HashMap<>(0);
                    map.put("isType", 6);
                    map.put("userId", userId);
                    map.put("remark", "3");
                    map.put("itemId", infoId);
                    map.put("account", userId);
                    map.put("accountName", qainfo.getNickName());
                    userGiveAwayLogDao.updateByUserIdAndTypeAndItemIdAndRemark(map);
                } else if (size == 1) {
                    // 修改问答回复人的钻石币数量
                    userInfoDetailDao.updateDiamondByUserId(qareplyList.get(0).getUserId(), qainfo.getPrice());

                    // 向问答支付信息表中插入数据
                    QaPayLog qaPayLog = new QaPayLog();
                    qaPayLog.setAccountId(qareplyList.get(0).getUserId());
                    qaPayLog.setCreateTime(new Date());
                    qaPayLog.setInfoId(infoId);
                    qaPayLog.setUserId(userId);
                    qaPayLog.setPrice(qainfo.getPrice());
                    qaPayLog.setPayType((byte) 1);
                    qaPayLogDao.save(qaPayLog);

                    // 修改用户收支记录表数据
                    Map<String, Object> map = new HashMap<>(0);
                    map.put("isType", 6);
                    map.put("userId", userId);
                    map.put("remark", "1");
                    map.put("itemId", infoId);
                    map.put("account", qareplyList.get(0).getUserId());
                    map.put("accountName", qainfo.getNickName());
                    userGiveAwayLogDao.updateByUserIdAndTypeAndItemIdAndRemark(map);

                    // 返现
                    // 修改问答回复人的钻石币数量
                    userInfoDetailDao.updateDiamondByUserId(userId, qainfo.getTwoPrice());
                    userInfoDetailDao.updateDiamondByUserId(userId, qainfo.getThreePrice());

                    // 修改用户收支记录表数据
                    map.put("isType", 6);
                    map.put("userId", userId);
                    map.put("remark", "2");
                    map.put("itemId", infoId);
                    map.put("account", userId);
                    map.put("accountName", qainfo.getNickName());
                    userGiveAwayLogDao.updateByUserIdAndTypeAndItemIdAndRemark(map);
                    map.put("remark", "3");
                    userGiveAwayLogDao.updateByUserIdAndTypeAndItemIdAndRemark(map);
                } else {
                    // 全部返现
                    // 修改问答回复人的钻石币数量
                    userInfoDetailDao.updateDiamondByUserId(userId, qainfo.getPrice() + qainfo.getTwoPrice() + qainfo.getThreePrice());

                    // 修改用户收支记录表数据
                    Map<String, Object> map = new HashMap<>(0);
                    map.put("isType", 6);
                    map.put("userId", userId);
                    map.put("itemId", infoId);
                    map.put("account", userId);
                    map.put("accountName", qainfo.getNickName());
                    userGiveAwayLogDao.updateByUserIdAndTypeAndItemId(map);
                }

                // 设置前三名状态
                for (int i = 0; i < qareplyList.size(); i++) {
                    // 修改名次
                    Integer replyId = qareplyList.get(i).getReplyId();
                    int best = qaReplyDao.updateIsAptimalByReplyId(replyId, i + 1);
                    if (best == 1 && i == 0) {
                        //推送通知被评为第一名
                        QaFirstPushProduct.pushQaBest(replyId, qareplyList.get(0).getUserId());
                    }
                }
            }
        }
    }

    /**
     * 查询本国热门视频
     *
     * @param offset  开始查询数量
     * @param limit   每页显示信息数
     * @param country 当前国家
     * @param state   状态, 1查询本国热门, 2查询国际热门
     * @return
     */
    @Override
    public List<Object> findDomesticHotQaInfo(Integer offset, Integer limit, String country, Integer state) {
        return qaInfoDao.findDomesticHotQaInfo(offset, limit, country, state);
    }

    /**
     * 根据视频url查询问答信息
     *
     * @param video 视频url
     * @return
     */
    @Override
    public List<Object> findQaInfoByVideoUrl(String video) {
        return qaInfoDao.findQaInfoByVideoUrl(video);
    }
}
