package com.faceshow.modules.report.service;

import com.faceshow.modules.report.entity.ReportType;

import java.util.List;

public interface ReportTypeService {
    int deleteByPrimaryKey(Integer typeId);

    int insertSelective(ReportType record);

    ReportType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(ReportType record);

    List<ReportType> selectAll();

    /**
     * 获取不同页面的举报类型
     *
     * @param type 1短视频举报类型, 2评论举报类型, 3直播举报类型
     */
    List<ReportType> findReportByType(Integer type);
}