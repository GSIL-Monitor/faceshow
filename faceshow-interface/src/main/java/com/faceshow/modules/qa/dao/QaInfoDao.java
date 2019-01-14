package com.faceshow.modules.qa.dao;

import com.faceshow.common.utils.NumUtils;
import com.faceshow.modules.qa.vo.QaAmountSetSelectRowVo;
import com.faceshow.modules.qa.vo.QaInfoSelectListVo;
import com.faceshow.modules.qa.vo.QaInfoSelectRowVo;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 问答详情操作Dao
 * <p>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:28
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface QaInfoDao extends BaseDao<Object> {

    /**
     * 查看当前用户的提问列表
     *
     * @param map 查询条件
     * @return
     */
    List<QaInfoSelectListVo> findQaInfoListByUserId(Map<String, Object> map);

    /**
     * 查看当前用户的提问数量
     *
     * @param map 查询条件
     * @return
     */
    Integer findQaInfoTotalByUserId(Map<String, Object> map);

    /**
     * 获取问答的点赞数和吐槽数
     *
     * @param infoId 问答id
     * @return
     */
    Map<String, Integer> getLikeNumAndMakeNumById(Integer infoId);

    /**
     * 查询待答广场列表
     *
     * @param map 查询条件
     * @return
     */
    List<QaInfoSelectListVo> findWaitQaInfoList(Map<String, Object> map);

    /**
     * 查询待答广场信息数量
     *
     * @param map 查询条件
     * @return
     */
    Integer findWaitQaInfoTotal(Map<String, Object> map);

    /**
     * 查询免费广场列表
     *
     * @param map 查询条件
     * @return
     */
    List<QaInfoSelectListVo> findFreeQaInfoList(Map<String, Object> map);

    /**
     * 查询免费广场信息数量
     *
     * @param map 查询条件
     * @return
     */
    Integer findFreeQaInfoTotal(Map<String, Object> map);

    /**
     * 根据问答id查询问答详情
     *
     * @param map 问答id
     * @return
     */
    QaInfoSelectRowVo findQaInfoByInfoId(Map<String, Object> map);

    /**
     * 查询指定的问答信息(金额问前三名, 并且没有指定最佳答案的)
     *
     * @return
     */
    List<QaAmountSetSelectRowVo> findByTopThree();

    /**
     * 根据id查询评论总数
     *
     * @param infoId 问答id
     * @return
     */
    Integer findCommentNumByInfoId(int infoId);

    /**
     * 查询本国热门视频
     *
     * @param offset  开始查询数量
     * @param limit   每页显示信息数
     * @param country 当前国家
     * @param state   状态, 1查询本国热门, 2查询国际热门
     * @return
     */
    List<Object> findDomesticHotQaInfo(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("country") String country, @Param("state") Integer state);

    /**
     * 根据视频url查询问答信息
     *
     * @param video 视频url
     * @return
     */
    List<Object> findQaInfoByVideoUrl(String video);

    /**
     * 修改问答点赞数量+1或者-1
     *
     * @param numUtils
     * @return
     */
    int updateLikeNum(NumUtils numUtils);

    /**
     * 修改问答评论数量+1或者-1
     *
     * @param num
     * @return
     */
    int updateCommentNumOne(NumUtils num);

    /**
     * 修改问答评论数量为固定值
     *
     * @param num
     * @return
     */
    int updateCommentNumMany(NumUtils num);

    /**
     * 修改问答回答数量+1或者-1
     *
     * @param num
     * @return
     */
    int updateReplyNum(NumUtils num);

    /**
     * @author: YS
     * @Date:2018/4/25 15:52
     * @param:
     * @explain： <!--根据主键查看userId-->
     * @return:
     */
    int selectUserIdByInfoId(Integer info_id);

    /**
     * 根据问答回复id查询问答发起人id
     *
     * @param replyId 问答回复id
     * @return
     */
    Integer findUserIdReplyId(Integer replyId);
}