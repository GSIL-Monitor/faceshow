package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserApprove;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserApproveDao {
    int deleteByPrimaryKey(Integer approveId);

    int insert(UserApprove record);

    int insertSelective(UserApprove record);

    UserApprove selectByPrimaryKey(Integer approveId);

    int updateByPrimaryKeySelective(UserApprove record);

    int updateByPrimaryKey(UserApprove record);
}