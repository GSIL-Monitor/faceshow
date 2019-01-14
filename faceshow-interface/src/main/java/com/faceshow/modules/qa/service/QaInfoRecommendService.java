package com.faceshow.modules.qa.service;


import java.util.Map; /**
 * 推荐问题操作Service接口
 * <p>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:28
 * -------------------------------------------------------------- <br>
 */
public interface QaInfoRecommendService {

    /**
     * 查询推荐问题列表
     *
     * @param map 分页条件
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    Object list(Map<String, Object> map);
}
