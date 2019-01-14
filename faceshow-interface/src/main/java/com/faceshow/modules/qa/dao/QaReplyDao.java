package com.faceshow.modules.qa.dao;

import com.faceshow.common.utils.NumUtils;
import com.faceshow.common.utils.Query;
import com.faceshow.modules.qa.vo.QaReplySelectRowVo;
import com.faceshow.modules.qa.vo.QaReplyTopThreeSelectRowVo;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 问答回复操作Dao
 * <p>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:28
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface QaReplyDao extends BaseDao<Object> {

    /**
     * 查询神回复列表
     *
     * @param map 查询条件
     * @return
     */
    List<Map<String, Object>> shenList(Map<String, Object> map);

    /**
     * 查询神回复数量
     *
     * @param map 查询条件
     * @return
     */
    Integer shenTotal(Map<String, Object> map);

    /**
     * 根据问答回复id查询问答回复详情
     *
     * @param map 条件
     *            replyId 问答回复id
     *            longitude 用户当前位置(经度)
     *            latitude 用户当前位置(维度)
     * @return
     */
    QaReplySelectRowVo getQaReplyById(Map<String, Object> map);

    /**
     * 根据问答回复id查询问答回复点赞数和吐槽数量
     *
     * @param replyId 问答回复id
     * @return
     */
    Map<String, Integer> getLikeNumAndMakeNumById(Integer replyId);

    /**
     * 查询当前用户的回答问题列表
     *
     * @param query 查询条件
     * @return
     */
    List<Map<String, Object>> findReplyListByUserId(Query query);

    /**
     * 查询当前用户的回答问题数量
     *
     * @param query 查询条件
     * @return
     */
    Integer findReplyTotalByUserId(Query query);

    /**
     * 根据问答id查询问答回复列表
     *
     * @param query 查询条件
     * @return
     */
    List<Map<String, Object>> findReplyListByInfoId(Query query);

    /**
     * 根据问答id查询问答回复数量
     *
     * @param query 查询条件
     * @return
     */
    Integer findReplyTotalByInfoId(Query query);

    /**
     * 修改问答回复状态为最佳答案
     *
     * @param replyId   问答回复id
     * @param isAptimal 最佳回复状态
     * @return
     */
    int updateIsAptimalByReplyId(@Param("replyId") Integer replyId, @Param("isAptimal") Integer isAptimal);

    /**
     * 根据问答id查询最佳的前三名
     *
     * @param info_id 问答id
     * @return
     */
    List<QaReplyTopThreeSelectRowVo> getBestTopThreeByInfoId(Integer info_id);

    /**
     * 根据回复id查询回复评论数量
     *
     * @param replyId 回复id
     * @return
     */
    Integer findCommentNumByReplyId(int replyId);

    /**
     * 问答回复评论数量+1
     *
     * @param replyId 问答回复id
     * @return
     */
    int addCommentNumByReplyId(Integer replyId);

    /**
     * 修改问答回复评论数量
     *
     * @param replyId
     * @param commentNum
     * @return
     */
    int updateCommentNumByReplyId(@Param("replyId") Integer replyId, @Param("commentNum") Integer commentNum);

    /**
     * 查询本国热门视频
     *
     * @param offset  开始查询数量
     * @param limit   每页显示信息数
     * @param country 当前国家
     * @param state   状态, 1查询本国热门, 2查询国际热门
     * @return
     */
    List<Object> findDomesticHotQaReply(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("country") String country, @Param("state") Integer state);

    /**
     * 根据视频url查询问答信息
     *
     * @param video 视频url
     * @return
     */
    List<Object> findQaReplyByVideoUrl(String video);

    /**
     * 修改点赞数量 +1或者-1
     *
     * @param num
     * @return
     */
    int updateLikeNum(NumUtils num);

    /**
     * 修改点踩数量+1或者-1
     *
     * @param num
     * @return
     */
    int updateMakeNum(NumUtils num);

    /**
     * 修改状态为神回复
     *
     * @param replyId 回复id
     * @param isShen
     * @return
     */
    int updateIsShen(@Param("replyId") Integer replyId, @Param("isShen") Integer isShen);

    /**
     * 问答回复评论数量+1或者-1
     *
     * @param num
     * @return
     */
    int updateCommentNumOne(NumUtils num);

    /**
     * 问答回复数量修改为固定值
     *
     * @param num
     * @return
     */
    int updateCommentNumMany(NumUtils num);

    /**
     * @author: YS
     * @Date:2018/4/25 17:22
     * @param:
     * @explain：<!--根据主键查找userId-->
     * @return:
     */
    int selectUserIdByReplyId(Integer ReplyId);


}