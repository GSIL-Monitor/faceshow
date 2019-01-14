package com.faceshow.modules.report.service;

import com.faceshow.modules.report.entity.ReportInfo;
import com.faceshow.modules.user.entity.AppFeedback;
import com.faceshow.modules.user.entity.BackList;
import com.faceshow.modules.user.entity.UserSet;

import java.util.List;

public interface ReportInfoService {
    int deleteByPrimaryKey(Integer reportId);

    Object insertSelective(ReportInfo record);

    ReportInfo selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(ReportInfo record);

    List<Object> selectByUserId(Integer userId,Integer begin,Integer pageSize);

    int insertBackList(BackList backList);//拉黑

    int selectByUseridAccount(Integer userId, Integer account);//查询是否已经拉黑

    int deleteByUserid(Integer userId, Integer account);//移除黑名单

    List<Object> selectByBackUserid(Integer userId,Integer begin,Integer pageSize);

    int insertFeedBack(AppFeedback appFeedback);//问题反馈

    int insertDistance(UserSet userSet);//添加距离设置

    int updateDistance(UserSet userSet);//距离设置

    int selectByBackUseridNum(Integer userId);

    int selectByUserIdNum(Integer userId);

    UserSet selectByDistance(Integer userId);//获取距离设置信息

}