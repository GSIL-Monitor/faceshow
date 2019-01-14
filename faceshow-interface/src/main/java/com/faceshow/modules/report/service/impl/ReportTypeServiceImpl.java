package com.faceshow.modules.report.service.impl;

import com.faceshow.modules.report.dao.ReportTypeDao;
import com.faceshow.modules.report.entity.ReportType;
import com.faceshow.modules.report.service.ReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Athor GuoChao
 * @Date Create in 13:38 2018/1/15
 */
@Service
public class ReportTypeServiceImpl implements ReportTypeService {

    @Autowired
    private ReportTypeDao reportTypeDao;

    @Override
    public int deleteByPrimaryKey(Integer typeId) {
        return reportTypeDao.deleteByPrimaryKey(typeId);
    }


    @Override
    public int insertSelective(ReportType record) {
        return reportTypeDao.insertSelective(record);
    }

    @Override
    public ReportType selectByPrimaryKey(Integer typeId) {
        return reportTypeDao.selectByPrimaryKey(typeId);
    }

    @Override
    public int updateByPrimaryKeySelective(ReportType record) {
        return reportTypeDao.updateByPrimaryKeySelective(record);
    }


    /**
     * 查询所有的举报类型
     * @return
     */
    @Override
    public List<ReportType> selectAll() {
        return  reportTypeDao.selectAll();
    }

    /**
     * 获取不同页面的举报类型
     *
     * @param type 1短视频举报类型, 2评论举报类型, 3直播举报类型
     */
    @Override
    public List<ReportType> findReportByType(Integer type) {
        return reportTypeDao.findReportByType(type);
    }
}
