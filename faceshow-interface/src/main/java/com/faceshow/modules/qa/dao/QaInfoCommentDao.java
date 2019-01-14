package com.faceshow.modules.qa.dao;

import com.faceshow.common.utils.NumUtils;
import com.faceshow.common.utils.Query;
import com.faceshow.modules.qa.vo.QaInfoCommentSelectRowVo;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 问答评论信息操作Dao
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/4 11:03
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface QaInfoCommentDao extends BaseDao<Object> {

    /**
     * 查询一级评论
     *
     * @param query 查询条件
     * @return
     */
    List<QaInfoCommentSelectRowVo> findCommentListByInfoId(Query query);

    /**
     * 查询一级评论数量
     *
     * @param infoId 问答id
     * @return
     */
    Integer findCommentTotalByInfoId(Integer infoId);

    /**
     * 根据评论父id查询自评论
     *
     * @param parentCommentId
     * @return
     */
    List<QaInfoCommentSelectRowVo> findCommentByParent(Integer parentCommentId);

    /**
     * 根据父评论id查询自评论id
     *
     * @param parentCommentId
     * @return
     */
    List<Integer> findCommentIdByParentId(Integer parentCommentId);

    /**
     * 查询评论总数量
     *
     * @param infoId 问答id
     * @return
     */
    Integer findCommentNumByInfoId(Integer infoId);

    /**
     * 点赞数量+1或者-1
     *
     * @param num
     * @return
     */
    int updateLikeNum(NumUtils num);

    /**
     * @author: YS
     * @Date:2018/4/25 16:11
     * @param:
     * @explain：    <!--根据主键查看userId-->
     * @return:
     */
    int selectUserIdByCommentId(Integer comment_id);
}
