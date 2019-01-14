package com.faceshow.modules.qa.dao;

import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 悬赏金额设置表
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/28 16:36
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface QaAmountSetDao extends BaseDao<Object> {

    /**
     * 修改问答信息支付状态
     *
     * @param infoId 问答信息id
     * @param isPay
     * @return
     */
    int updateIsPayByInfoId(@Param("infoId") Integer infoId, @Param("isPay") Integer isPay);

    /**
     * 根据问答id查询问答设置金额
     *
     * @param infoId 问答id
     * @return
     */
    Integer findPriceByInfoId(Integer infoId);

    /**
     * 查询该问答的支付状态
     *
     * @param infoId 问答id
     * @return
     */
    Integer findIsPayByInfoId(Integer infoId);
}
