package com.faceshow.modules.hobby.dao;

import com.faceshow.modules.hobby.entity.HobbyUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HobbyUserInfoDao {
    int deleteByPrimaryKey(Integer uhId);

    int insert(HobbyUserInfo record);

    int insertSelective(HobbyUserInfo record);

    HobbyUserInfo selectByPrimaryKey(Integer uhId);

    int updateByPrimaryKeySelective(HobbyUserInfo record);

    int updateByPrimaryKey(HobbyUserInfo record);

    /**
     * 删除当前用户指定标签
     *
     * @param hobbyUserInfo 标签id和用户id
     */
    int deleteByUserIdAndHobbyId(HobbyUserInfo hobbyUserInfo);

    /**
     * 删除当前用户所有标签
     *
     * @param userId 当前用户id
     */
    Integer deleteByUserId(Integer userId);

    /**
     * 给当前用户添加新标签
     *
     * @param list 标签
     * @return
     */
    Integer addHobbyList(List<HobbyUserInfo> list);
}