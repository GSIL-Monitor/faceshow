package com.faceshow.modules.report.dao;

import com.faceshow.modules.report.entity.ReportInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportInfoDao {
    int deleteByPrimaryKey(Integer reportId);

    int insertSelective(ReportInfo record);

    ReportInfo selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(ReportInfo record);

    List<Object> selectByUserId(Map<String, Object> map);
    int selectByUserIdNum(Integer userId);
}