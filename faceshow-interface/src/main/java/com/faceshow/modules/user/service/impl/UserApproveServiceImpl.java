package com.faceshow.modules.user.service.impl;
import com.faceshow.modules.user.dao.UserApproveDao;
import com.faceshow.modules.user.entity.UserApprove;
import com.faceshow.modules.user.service.UserApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by liyan on 2018/2/3
 */
@Service
public class UserApproveServiceImpl implements UserApproveService {

    @Autowired
    private UserApproveDao userApproveDao;

    @Override
    public int deleteByPrimaryKey(Integer joinId) {
        return userApproveDao.deleteByPrimaryKey(joinId);
    }

    @Override
    public int insert(UserApprove record) {
        return userApproveDao.insert(record);
    }

    @Override
    public int insertSelective(UserApprove record) {
        return userApproveDao.insertSelective(record);
    }

    @Override
    public UserApprove selectByPrimaryKey(Integer joinId) {
        return  userApproveDao.selectByPrimaryKey(joinId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserApprove record) {
        return userApproveDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserApprove record) {
        return userApproveDao.updateByPrimaryKey(record);
    }
}
