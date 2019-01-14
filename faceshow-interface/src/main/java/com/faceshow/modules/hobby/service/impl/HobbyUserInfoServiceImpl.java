package com.faceshow.modules.hobby.service.impl;

import com.faceshow.common.utils.R;
import com.faceshow.modules.hobby.dao.HobbyUserInfoDao;
import com.faceshow.modules.hobby.entity.HobbyUserInfo;
import com.faceshow.modules.hobby.service.HobbyUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Athor GuoChao
 * @Date Create in 15:03 2018/1/20
 */
@Service
public class HobbyUserInfoServiceImpl implements HobbyUserInfoService {

    @Autowired
    private HobbyUserInfoDao hobbyUserInfoDao;

    @Override
    public int deleteByPrimaryKey(Integer uhId) {
        return hobbyUserInfoDao.deleteByPrimaryKey(uhId);
    }

    @Override
    public int insert(HobbyUserInfo record) {
        return hobbyUserInfoDao.insert(record);
    }

    @Override
    public int insertSelective(HobbyUserInfo record) {
        return hobbyUserInfoDao.insertSelective(record);
    }

    @Override
    public HobbyUserInfo selectByPrimaryKey(Integer uhId) {
        return hobbyUserInfoDao.selectByPrimaryKey(uhId);
    }

    @Override
    public int updateByPrimaryKeySelective(HobbyUserInfo record) {
        return hobbyUserInfoDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(HobbyUserInfo record) {
        return hobbyUserInfoDao.updateByPrimaryKey(record);
    }

    /**
     * 删除当前用户指定标签
     *
     * @param hobbyIds 标签id
     * @param userId   当前用户id
     */
    @Override
    public Object deleteByUserIdAndHobbyId(String[] hobbyIds, Integer userId) {
        if (hobbyIds == null || hobbyIds.length == 0) {
            return R.result(0, "没有要删除的标签", "");
        }

        for (String id : hobbyIds) {
            HobbyUserInfo hobbyUserInfo = new HobbyUserInfo();
            hobbyUserInfo.setUserId(userId);
            hobbyUserInfo.setHobbyId(Integer.parseInt(id));
            hobbyUserInfoDao.deleteByUserIdAndHobbyId(hobbyUserInfo);
        }
        return R.result(1, "删除成功", "");
    }

    /**
     * 删除当前用户所有标签
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public Object deleteByUserId(Integer userId) {
        Integer i = hobbyUserInfoDao.deleteByUserId(userId);
        if (i > 0) {
            //删除成功
            return R.result(1, "删除成功", "");
        }
        return R.result(0, "删除失败", "");
    }

    /**
     * 给当前用户添加新标签
     *
     * @param list 标签
     * @return
     */
    @Override
    public Integer addHobbyList(List<HobbyUserInfo> list) {
        if (list == null || list.size() == 0) {
            return -1;
        }
        return hobbyUserInfoDao.addHobbyList(list);
    }
}
