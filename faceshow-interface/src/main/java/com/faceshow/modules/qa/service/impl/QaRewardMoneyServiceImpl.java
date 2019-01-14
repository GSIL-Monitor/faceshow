package com.faceshow.modules.qa.service.impl;

import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.RedisKeys;
import com.faceshow.common.utils.RedisUtils;
import com.faceshow.modules.qa.dao.QaRewardMoneyDao;
import com.faceshow.modules.qa.dao.QaSetDaysDao;
import com.faceshow.modules.qa.entity.QaSetDays;
import com.faceshow.modules.qa.service.QaRewardMoneyService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 悬赏金额操作Service接口实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/20 11:10
 * -------------------------------------------------------------- <br>
 */
@Service
public class QaRewardMoneyServiceImpl implements QaRewardMoneyService {

    @Autowired
    private QaRewardMoneyDao qaRewardMoneyDao;

    @Autowired
    private QaSetDaysDao qaSetDaysDao;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 查询悬赏金额列表
     *
     * @return
     */
    @Override
    public Object list() {
        // 从redis中获取悬赏金额列表
        String moneyList = redisUtils.get(RedisKeys.QA_REWARD_MONEY);
        if (StringUtils.isBlank(moneyList)) {
            // 从数据库中获取悬赏金额列表
            List<String> list = qaRewardMoneyDao.queryList(null);
            moneyList = JsonUtils.objectToJson(list);

            // 将悬赏金额存入redis中
            if (list.size() > 0) {
                redisUtils.set(RedisKeys.QA_REWARD_MONEY, moneyList, -1);
            }
        }

        // 从redis中获取天数列表
        String dayList = redisUtils.get(RedisKeys.QA_SET_DAYS);
        if (StringUtils.isBlank(dayList)) {
            // 从数据库中获取天数
            List<String> list = qaSetDaysDao.queryList(null);
            dayList = JsonUtils.objectToJson(list);

            // 将天数存入redis中
            if (list.size() > 0) {
                redisUtils.set(RedisKeys.QA_SET_DAYS, dayList, -1);
            }
        }

        return R.ok("OK").put("moneyList", moneyList).put("dayList", dayList);
    }
}
