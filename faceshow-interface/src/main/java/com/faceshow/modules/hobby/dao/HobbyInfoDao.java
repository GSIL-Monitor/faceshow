package com.faceshow.modules.hobby.dao;

import com.faceshow.modules.hobby.entity.HobbyInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HobbyInfoDao {
    int deleteByPrimaryKey(Integer hobbyId);

    int insert(HobbyInfo record);

    int insertSelective(HobbyInfo record);

    HobbyInfo selectByPrimaryKey(Integer hobbyId);

    int updateByPrimaryKeySelective(HobbyInfo record);

    int updateByPrimaryKey(HobbyInfo record);

    /**
     * 查询所有系统定义标签
     *
     * @return
     */
    List<HobbyInfo> findSysHobbyAll(Integer userId);

    /**
     * 查询当前用户的所有标签
     *
     * @param userId 用户id
     * @return
     */
    List<HobbyInfo> findHobbyByUserId(Integer userId);

    /**
     * 查询用户拥有的标签
     * @param userId 用户id
     * @return
     */
    List<String> findHobbyTagsByUserId(Integer userId);
}