package com.faceshow.modules.hobby.service;

import com.faceshow.modules.hobby.entity.HobbyUserInfo;

import java.util.List;

/**
 * @Athor GuoChao
 * @Date Create in 15:03 2018/1/20
 */
public interface HobbyUserInfoService {
    int deleteByPrimaryKey(Integer uhId);

    int insert(HobbyUserInfo record);

    int insertSelective(HobbyUserInfo record);

    HobbyUserInfo selectByPrimaryKey(Integer uhId);

    int updateByPrimaryKeySelective(HobbyUserInfo record);

    int updateByPrimaryKey(HobbyUserInfo record);

    /**
     * 删除当前用户指定标签
     *
     * @param hobbyIds 标签id
     * @param userId   当前用户id
     */
    Object deleteByUserIdAndHobbyId(String[] hobbyIds, Integer userId);

    /**
     * 删除当前用户所有标签
     * @param userId 用户id
     * @return
     */
    Object deleteByUserId(Integer userId);

    /**
     * 给当前用户添加新标签
     * @param list 标签
     * @return
     */
    Integer addHobbyList(List<HobbyUserInfo> list);
}
