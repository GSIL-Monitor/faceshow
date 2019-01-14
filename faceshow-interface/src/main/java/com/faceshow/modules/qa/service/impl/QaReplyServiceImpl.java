package com.faceshow.modules.qa.service.impl;

import com.faceshow.common.MQ.message.likenum.QaReplyLikeOrMakeNumProduct;
import com.faceshow.common.MQ.message.likenum.QaReplyNumProduct;
import com.faceshow.common.MQ.message.push.QaReplyPushProduct;
import com.faceshow.common.utils.*;
import com.faceshow.modules.qa.dao.QaReplyDao;
import com.faceshow.modules.qa.dao.QaReplyLikeDao;
import com.faceshow.modules.qa.dao.QaReplyMakeDao;
import com.faceshow.modules.qa.dao.QaViewDao;
import com.faceshow.modules.qa.entity.QaReply;
import com.faceshow.modules.qa.entity.QaReplyLike;
import com.faceshow.modules.qa.entity.QaReplyMake;
import com.faceshow.modules.qa.service.QaReplyService;
import com.faceshow.modules.qa.vo.QaReplySelectRowVo;
import com.faceshow.modules.user.dao.UserAttentionDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 问答回复操作Service接口实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:50
 * -------------------------------------------------------------- <br>
 */
@Service
@Transactional
public class QaReplyServiceImpl implements QaReplyService {

    @Autowired
    private QaReplyDao qaReplyDao;

    @Autowired
    private QaReplyLikeDao qaReplyLikeDao;

    @Autowired
    private QaReplyMakeDao qaReplyMakeDao;

    @Autowired
    private QaViewDao qaViewDao;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 给问答添加新回复
     *
     * @param qaReply 问答回复详情
     * @return
     */
    @Override
    public Object addReply(QaReply qaReply) {
        // 保存新回复
        int save = qaReplyDao.save(qaReply);
        if (save > 0) {
            // 标记该问答有新回复
            qaViewDao.updateNewAnswerByInfoId(qaReply.getInfoId(), 1);
            // 问答回答数增加1
            QaReplyNumProduct.updateReplyNum(qaReply.getInfoId(), 1);
            // 推送消息有新回复
            QaReplyPushProduct.pushQaReply(qaReply.getInfoId(), qaReply.getUserId());
        }

        return R.result(1, "回答成功", "");
    }

    /**
     * 查询神回复列表
     *
     * @param map 查询条件
     *            limit 每页显示信息数
     *            page 当前页
     *            userId 当前用户
     * @return
     */
    @Override
    public Object shenList(Map<String, Object> map) {
        Query query = new Query(map);
        // 查询神回复列表
        List<Map<String, Object>> list = qaReplyDao.shenList(query);

        // 查询神回复数量
        int total = qaReplyDao.shenTotal(query);
        return R.ok().put("page", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * 问答回复点赞
     *
     * @param userId  当前用户id
     * @param replyId 问答回复id
     * @param isMake  是否已经吐槽 0 未吐槽, 1 已吐槽
     * @return
     */
    @Override
    public Object addLike(Integer userId, Integer replyId, Integer isMake) {
        Map<String, Integer> map = qaReplyDao.getLikeNumAndMakeNumById(replyId);
        // 取消吐槽
        int i = qaReplyMakeDao.deleteByUserIdAndReplyId(userId, replyId);
        if (i > 0) {
            redisUtils.delete(RedisKeys.QA_REPLY_MAKE + replyId + "_" + userId);
            QaReplyLikeOrMakeNumProduct.updateReplyMakeNum(replyId, -1);
            map.put("makeNum", map.get("makeNum") - 1);
        }

        // 添加点赞信息
        QaReplyLike qaReplyLike = new QaReplyLike();
        qaReplyLike.setCreateTime(new Date());
        qaReplyLike.setReplyId(replyId);
        qaReplyLike.setUserId(userId);
        int save = qaReplyLikeDao.save(qaReplyLike);
        if (save > 0) {
            //将点赞信息存入redis中
            redisUtils.set(RedisKeys.QA_REPLY_LIKE + replyId + "_" + userId, 1, RedisUtils.NOT_EXPIRE);
            QaReplyLikeOrMakeNumProduct.updateReplyLikeNum(replyId, 1);
            map.put("likeNum", map.get("likeNum") + 1);
        }
        // 设置为神回复
        if (map.get("likeNum") > 100) {
            qaReplyDao.updateIsShen(replyId, 1);
        }
        return R.ok("点赞成功").put("likeNum", map.get("likeNum")).put("makeNum", map.get("makeNum"));
    }

    /**
     * 问答回复取消点赞
     *
     * @param userId  当前用户id
     * @param replyId 问答回复id
     * @return
     */
    @Override
    public Object deleteLike(Integer userId, Integer replyId) {
        int i = qaReplyLikeDao.deleteByUserIdAndReplyId(userId, replyId);
        redisUtils.delete(RedisKeys.QA_REPLY_LIKE + replyId + "_" + userId);

        if (i > 0) {
            QaReplyLikeOrMakeNumProduct.updateReplyLikeNum(replyId, -1);
        }
        return R.result(1, "取消点赞成功", "");
    }

    /**
     * 问答回复吐槽
     *
     * @param userId  当前用户id
     * @param replyId 问答回复id
     * @param isLike  是否已经点赞 0 未吐槽, 1 已吐槽
     * @return
     */
    @Override
    public Object addMake(Integer userId, Integer replyId, Integer isLike) {
        // 获取点赞数量和吐槽数量
        Map<String, Integer> map = qaReplyDao.getLikeNumAndMakeNumById(replyId);

        // 如果已经点赞, 取消点赞
        int i = qaReplyLikeDao.deleteByUserIdAndReplyId(userId, replyId);
        if (i > 0) {
            redisUtils.delete(RedisKeys.QA_REPLY_LIKE + replyId + "_" + userId);
            QaReplyLikeOrMakeNumProduct.updateReplyLikeNum(replyId, -1);
            map.put("likeNum", map.get("likeNum") - 1);
        }

        // 添加吐槽信息
        QaReplyMake qaReplyMake = new QaReplyMake();
        qaReplyMake.setCreateTime(new Date());
        qaReplyMake.setReplyId(replyId);
        qaReplyMake.setUserId(userId);
        int save = qaReplyMakeDao.save(qaReplyMake);
        if (save > 0) {
            redisUtils.set(RedisKeys.QA_REPLY_MAKE + replyId + "_" + userId, 1, RedisUtils.NOT_EXPIRE);
            QaReplyLikeOrMakeNumProduct.updateReplyMakeNum(replyId, 1);
            map.put("makeNum", map.get("makeNum") + 1);
        }
        return R.ok("吐槽成功").put("makeNum", map.get("makeNum")).put("likeNum", map.get("likeNum"));
    }

    /**
     * 问答回复取消吐槽
     *
     * @param userId  当前用户id
     * @param replyId 问答回复id
     * @return
     */
    @Override
    public Object deleteMake(Integer userId, Integer replyId) {
        int i = qaReplyMakeDao.deleteByUserIdAndReplyId(userId, replyId);
        redisUtils.delete(RedisKeys.QA_REPLY_MAKE + replyId + "_" + userId);

        if (i > 0) {
            // 获取点赞数量和吐槽数量
            QaReplyLikeOrMakeNumProduct.updateReplyMakeNum(replyId, -1);
        }
        return R.result(1, "取消吐槽成功", "");
    }

    /**
     * 根据问答回复id查询问答回复详情
     *
     * @param map 条件
     *            replyId 问答回复id
     *            userId 当前用户id
     *            longitude 用户当前位置(经度)
     *            latitude 用户当前位置(维度)
     * @return
     */
    @Override
    public Object getQaReplyById(Map<String, Object> map) {
        QaReplySelectRowVo result = qaReplyDao.getQaReplyById(map);
        Integer replyId = Integer.parseInt(map.get("replyId").toString());
        Integer userId = Integer.parseInt(map.get("userId").toString());
        result.setIsLike(isLike(replyId, userId));
        result.setIsMake(isMake(replyId, userId));
        return R.ok("").put("result", result);
    }

    /**
     * 查询当前用户的回答列表
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @Override
    public Object findReplyListByUserId(Map<String, Object> map) {
        Query query = new Query(map);
        List<Map<String, Object>> list = qaReplyDao.findReplyListByUserId(query);

        Integer total = qaReplyDao.findReplyTotalByUserId(query);
        return R.ok().put("page", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * 根据问答id查询问答回复信息
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            infoId 当前问答id
     *            qaUserId 当前问答发起人id
     *            latitude 维度
     *            longitude 经度
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @Override
    public Object findReplyListByInfoId(Map<String, Object> map) {
        Query query = new Query(map);
        // 根据问题id查询问题回复列表
        List<Map<String, Object>> list = qaReplyDao.findReplyListByInfoId(query);

        Integer userId = Integer.parseInt(map.get("userId").toString());
        // 判断当前回复该用户是否已经点赞或者吐槽
        for (Map<String, Object> listMap : list) {
            Integer replyId = Integer.parseInt(listMap.get("replyId").toString());
            // 判断当前用户是否已经点赞或吐槽 -- 暂时不使用
            // listMap.put("isLike", isLike(replyId, userId));
            // listMap.put("isMake", isMake(replyId, userId));

            // 判断题主是否点赞
            String lordIsLike = redisUtils.get(RedisKeys.QA_REPLY_MAKE + replyId + "_" + listMap.get("qaUserId"));
            listMap.put("lordIsLike", StringUtils.isNotBlank(lordIsLike) ? 1 : 0);
        }

        // 根据问答id查询问题回复数量
        Integer total = qaReplyDao.findReplyTotalByInfoId(query);
        return R.ok().put("page", new PageUtils(list, total, query.getLimit(), query.getPage()));
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
    public List<Object> findDomesticHotQaReply(Integer offset, Integer limit, String country, Integer state) {
        return qaReplyDao.findDomesticHotQaReply(offset, limit, country, state);
    }

    /**
     * 根据视频url查询问答信息
     *
     * @param video 视频url
     * @return
     */
    @Override
    public List<Object> findQaReplyByVideoUrl(String video) {
        return qaReplyDao.findQaReplyByVideoUrl(video);
    }

    /**
     * 判断是否已经吐槽或者已经点赞
     *
     * @param list 结果集
     * @param map  条件
     */
    private void getLikeOrMake(List<Map<String, Object>> list, Map<String, Object> map) {
        Object userId = map.get("userId");
        // 判断当前回复该用户是否已经点赞或者吐槽
        for (Map<String, Object> listMap : list) {
            Integer replyId = Integer.parseInt(listMap.get("replyId").toString());
            // 判断当前用户是否已经点赞
            String isLike = redisUtils.get(RedisKeys.QA_REPLY_LIKE + replyId + "_" + userId);
            // 判断当前用户是否已经吐槽
            String isMake = redisUtils.get(RedisKeys.QA_REPLY_MAKE + replyId + "_" + userId);
            // 已经点赞为1, 未点赞为0, 已经吐槽为1, 未吐槽为0
            listMap.put("isLike", StringUtils.isNotBlank(isLike) ? 1 : 0);
            listMap.put("isMake", StringUtils.isNotBlank(isMake) ? 1 : 0);
        }
    }

    /**
     * 判断当前用户是否点赞
     *
     * @param replyId 回复id
     * @param userId  用户id
     * @return
     */
    private Integer isLike(Integer replyId, Integer userId) {
        String isLike = redisUtils.get(RedisKeys.QA_REPLY_LIKE + replyId + "_" + userId);
        if (StringUtils.isBlank(isLike)) {
            return qaReplyLikeDao.findByReplyIdAndUserId(replyId, userId);
        }
        return 1;
    }

    /**
     * 判断当前用户是否吐槽
     *
     * @param replyId 回复id
     * @param userId  用户id
     * @return
     */
    private Integer isMake(Integer replyId, Integer userId) {
        String isLike = redisUtils.get(RedisKeys.QA_REPLY_LIKE + replyId + "_" + userId);
        if (StringUtils.isBlank(isLike)) {
            return qaReplyMakeDao.findByReplyIdAndUserId(replyId, userId);
        }
        return 1;
    }

}
