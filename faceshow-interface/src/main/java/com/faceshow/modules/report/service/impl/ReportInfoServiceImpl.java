package com.faceshow.modules.report.service.impl;

import com.faceshow.common.utils.R;
import com.faceshow.modules.report.dao.ReportInfoDao;
import com.faceshow.modules.report.entity.ReportInfo;
import com.faceshow.modules.report.service.ReportInfoService;
import com.faceshow.modules.user.dao.*;
import com.faceshow.modules.user.entity.AppFeedback;
import com.faceshow.modules.user.entity.BackList;
import com.faceshow.modules.user.entity.UserSet;
import com.faceshow.modules.user.service.UserAttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Athor GuoChao
 * @Date Create in 13:36 2018/1/15
 */
@Service
@Transactional
public class ReportInfoServiceImpl implements ReportInfoService {

    @Autowired
    private ReportInfoDao reportInfoDao;

    @Autowired
    private BackListDao backListDao;

    @Autowired
    private AppFeedbackDao appFeedbackDao;

    @Autowired
    private UserSetDao userSetDao;

    @Autowired
    private UserAttentionDao userAttentionDao;
    @Autowired
    private UserAttentionService userAttentionService;
    @Autowired
    private ReportInfoService reportInfoService;

    @Override
    public int deleteByPrimaryKey(Integer reportId) {
        return reportInfoDao.deleteByPrimaryKey(reportId);
    }

    @Override
    @Transactional
    public Object insertSelective(ReportInfo record) {
        Integer res = reportInfoDao.insertSelective(record);
        if (res == null || res == 0) {
            return R.result(0, "举报失败", "");
        } else {
            Integer userid = record.getUserId();
            Integer account = record.getAccount();
            userAttentionService.deleteBack(userid, account);//取消双方的关注

            Integer backNum = reportInfoService.selectByUseridAccount(userid, account);//查看是否已经在黑名单里
            if (backNum == 0) {//拉黑
                BackList backList = new BackList();
                backList.setCreateTime(new Date());
                backList.setUserId(userid);
                backList.setAccount(account);
                reportInfoService.insertBackList(backList);
            }

            return R.result(1, "举报成功,我们将很快解决这个问题", "");
        }
    }

    @Override
    public ReportInfo selectByPrimaryKey(Integer reportId) {
        return reportInfoDao.selectByPrimaryKey(reportId);
    }

    @Override
    public int updateByPrimaryKeySelective(ReportInfo record) {
        return reportInfoDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Object> selectByUserId(Integer userId, Integer begin, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("begin", begin);
        map.put("pageSize", pageSize);
        return reportInfoDao.selectByUserId(map);
    }

    @Override
    public int insertBackList(BackList backList) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = backList.getAccount();
        Integer account = backList.getUserId();
        map.put("userId", userId);
        map.put("account", account);
        Integer backNum = backListDao.selectByUseridAccount(map);//查询是否已经拉黑
        if (backNum == 0) {

            backList.setCreateTime(new Date());
            Integer res = backListDao.insertSelective(backList);//拉黑
            if (res == null) {
                return 0;
            } else {
                userAttentionDao.deleteBack(map);//取消关注
                return 1;
            }
        } else {
            return 0;
        }
    }

    @Override
    public int selectByUseridAccount(Integer userId, Integer account) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("account", account);
        return backListDao.selectByUseridAccount(map);
    }

    @Override
    public int deleteByUserid(Integer userId, Integer account) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("account", account);
        return backListDao.deleteByUserid(map);
    }

    @Override
    public List<Object> selectByBackUserid(Integer userId, Integer begin, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("begin", begin);
        map.put("pageSize", pageSize);
        return backListDao.selectByUserid(map);
    }

    @Override
    public int insertFeedBack(AppFeedback appFeedback) {
        return appFeedbackDao.insertSelective(appFeedback);
    }

    @Override
    public int insertDistance(UserSet userSet) {
        return userSetDao.insertSelective(userSet);
    }

    @Override
    public UserSet selectByDistance(Integer userId) {
        return userSetDao.selectByDistance(userId);
    }

    @Override
    public int updateDistance(UserSet userSet) {
        return userSetDao.updateByUserId(userSet);
    }

    @Override
    public int selectByBackUseridNum(Integer userId) {
        return backListDao.selectByBackUseridNum(userId);
    }

    @Override
    public int selectByUserIdNum(Integer userId) {
        return reportInfoDao.selectByUserIdNum(userId);
    }
}
