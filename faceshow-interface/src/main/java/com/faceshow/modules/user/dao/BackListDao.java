package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.BackList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BackListDao {
    int deleteByPrimaryKey(Integer blackId);

    int insert(BackList record);

    int insertSelective(BackList record);

    BackList selectByPrimaryKey(Integer blackId);

    int updateByPrimaryKeySelective(BackList record);

    int updateByPrimaryKey(BackList record);

    int selectByUseridAccount(Map<String, Object> map);

    int deleteByUserid(Map<String, Object> map);

    List<Object> selectByUserid(Map<String, Object> map);

    int selectByBackUseridNum(Integer userId);
}