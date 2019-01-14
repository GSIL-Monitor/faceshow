package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserGiveAwayLog;

import java.util.Map;

public interface UserGiveAwayLogService {
    int deleteByPrimaryKey(Integer giveId);

    int insertSelective(Map<String, Object> map);

    UserGiveAwayLog selectByPrimaryKey(Integer giveId);

    int updateByPrimaryKeySelective(UserGiveAwayLog record);
}