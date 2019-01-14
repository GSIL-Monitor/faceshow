package com.faceshow.modules.qa.service;

import com.faceshow.modules.qa.entity.QaAmountSet;
import com.faceshow.modules.qa.entity.QaInfo;

import java.util.List;
import java.util.Map;

/**
 * 问答详情操作Service接口
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:36
 * -------------------------------------------------------------- <br>
 */
public interface QaInfoService {

    /**
     * 保存发布的问题
     *
     * @param qaInfo      问题内容信息
     * @param nickName    发布人昵称
     * @param qaAmountSet 金额设置表
     * @return
     */
    Object save(QaInfo qaInfo, String nickName, QaAmountSet qaAmountSet, Integer price);

    /**
     * 根据用户id查询用户的提问列表
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    Object findQaInfoListByUserId(Map<String, Object> map);

    /**
     * 问答点赞
     *
     * @param userId 当前用户id
     * @param infoId 问答id
     * @return
     */
    Object addLike(Integer userId, Integer infoId);

    /**
     * 问答取消点赞
     *
     * @param userId 当前用户id
     * @param infoId 问答id
     * @return
     */
    Object deleteLike(Integer userId, Integer infoId);

    /**
     * 查询有奖回答列表
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    Object findWaitQaInfo(Map<String, Object> map);

    /**
     * 查询免费广场信息
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    Object findFreeQaInfo(Map<String, Object> map);

    /**
     * 查询问答回复状况
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            infoId 当前问答id
     * @return
     */
    Object findQaInfoById(Map<String, Object> map);


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
    Object bestAnswer(Map<String, Object> map);

    /**
     * 指定最佳的前三名
     */
    void bestAnswerToThree();


    /**
     * 查询本国热门视频
     *
     * @param offset  开始查询数量
     * @param limit   每页显示信息数
     * @param country 当前国家
     * @param state   状态, 1查询本国热门, 2查询国际热门
     * @return
     */
    List<Object> findDomesticHotQaInfo(Integer offset, Integer limit, String country, Integer state);

    /**
     * 根据视频url查询问答信息
     *
     * @param video 视频url
     * @return
     */
    List<Object> findQaInfoByVideoUrl(String video);
}
