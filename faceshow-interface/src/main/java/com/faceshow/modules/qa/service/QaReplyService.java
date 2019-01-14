package com.faceshow.modules.qa.service;

import com.faceshow.modules.qa.entity.QaReply;

import java.util.List;
import java.util.Map;

/**
 * 问答回复操作Service接口
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:49
 * -------------------------------------------------------------- <br>
 */
public interface QaReplyService {

    /**
     * 给问答添加新回复
     *
     * @param qaReply 问答回复详情
     * @return
     */
    Object addReply(QaReply qaReply);

    /**
     * 查询神回复列表
     *
     * @param map 查询条件
     *            limit 每页显示信息数
     *            page 当前页
     *            userId 当前用户
     * @return
     */
    Object shenList(Map<String, Object> map);

    /**
     * 问答回复点赞
     *
     * @param userId  当前用户id
     * @param replyId 问答回复id
     * @param isMake  是否已经吐槽 0 未吐槽, 1 已吐槽
     * @return
     */
    Object addLike(Integer userId, Integer replyId, Integer isMake);

    /**
     * 问答回复取消点赞
     *
     * @param userId  当前用户id
     * @param replyId 问答回复id
     * @return
     */
    Object deleteLike(Integer userId, Integer replyId);

    /**
     * 问答回复吐槽
     *
     * @param userId  当前用户id
     * @param replyId 问答回复id
     * @param isLike  是否已经点赞 0 未吐槽, 1 已吐槽
     * @return
     */
    Object addMake(Integer userId, Integer replyId, Integer isLike);

    /**
     * 问答回复取消吐槽
     *
     * @param userId  当前用户id
     * @param replyId 问答回复id
     * @return
     */
    Object deleteMake(Integer userId, Integer replyId);

    /**
     * 根据问答回复id查询问答回复详情
     *
     * @param map 条件
     *            replyId 问答回复id
     *            userId 当前用户id
     *            currentLongitude 用户当前位置(经度)
     *            currentLatitude 用户当前位置(维度)
     * @return
     */
    Object getQaReplyById(Map<String, Object> map);

    /**
     * 查询当前用户的回答列表
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    Object findReplyListByUserId(Map<String, Object> map);

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
    Object findReplyListByInfoId(Map<String, Object> map);

    /**
     * 查询本国热门视频
     *
     * @param offset  开始查询数量
     * @param limit   每页显示信息数
     * @param country 当前国家
     * @param state   状态, 1查询本国热门, 2查询国际热门
     * @return
     */
    List<Object> findDomesticHotQaReply(Integer offset, Integer limit, String country, Integer state);

    /**
     * 根据视频url查询问答信息
     *
     * @param video 视频url
     * @return
     */
    List<Object> findQaReplyByVideoUrl(String video);
}
