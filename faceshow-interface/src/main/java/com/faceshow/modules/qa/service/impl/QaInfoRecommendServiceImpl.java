package com.faceshow.modules.qa.service.impl;

import com.faceshow.common.utils.*;
import com.faceshow.modules.qa.dao.QaInfoRecommendDao;
import com.faceshow.modules.qa.service.QaInfoRecommendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 推荐问题操作Service接口实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/20 10:06
 * -------------------------------------------------------------- <br>
 */
@Service
public class QaInfoRecommendServiceImpl implements QaInfoRecommendService {

    @Autowired
    private QaInfoRecommendDao qaInfoRecommendDao;

    /**
     * 查询推荐问题列表
     *
     * @param map 分页条件
     *            limit 每页显示信息数
     *            page 当前页
     * @return
     */
    @Override
    public Object list(Map<String, Object> map) {
        Query query = new Query(map);

        List<Object> list = qaInfoRecommendDao.queryList(query);
        int total = qaInfoRecommendDao.queryTotal();

        return R.ok("OK").put("page", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }
}
