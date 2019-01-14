package com.faceshow.modules.user.service;

import com.faceshow.modules.user.entity.UserApprove;

/**
 * Create by liyan on 2018/2/3
 */
public interface UserApproveService {
    int deleteByPrimaryKey(Integer joinId);

    int insert(UserApprove record);

    int insertSelective(UserApprove record);

    UserApprove selectByPrimaryKey(Integer joinId);

    int updateByPrimaryKeySelective(UserApprove record);

    int updateByPrimaryKey(UserApprove record);
}
