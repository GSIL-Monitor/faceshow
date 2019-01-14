package com.faceshow.modules.report.dao;

import com.faceshow.modules.report.entity.ReportType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportTypeDao {
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