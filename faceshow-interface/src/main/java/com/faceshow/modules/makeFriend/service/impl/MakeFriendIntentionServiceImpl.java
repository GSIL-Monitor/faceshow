package com.faceshow.modules.makeFriend.service.impl;

import com.faceshow.modules.makeFriend.dao.MakeFriendIntentionDao;
import com.faceshow.modules.makeFriend.service.MakeFriendIntentionService;
import com.faceshow.modules.makeFriend.vo.MakeFriendIntentionSelectRowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户交友意向操作Service
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 14:38
 * -------------------------------------------------------------- <br>
 */
@Service
public class MakeFriendIntentionServiceImpl implements MakeFriendIntentionService {

    @Autowired
    private MakeFriendIntentionDao makeFriendIntentionDao;

    /**
     * 查询所有标签, 并判定当前用户是否已经拥有该标签
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public List<MakeFriendIntentionSelectRowVo> findAll(Integer userId) {
        return makeFriendIntentionDao.findAll(userId);
    }

    /**
     * 根据用户id删除用户拥有的标签
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public int deleteByUserId(Integer userId) {
        return makeFriendIntentionDao.deleteByUserId(userId);
    }

    /**
     * 修改用户拥有的标签
     *
     * @param userId 用户id
     * @param ids    标签id数组
     * @return
     */
    @Override
    @Transactional
    public int updateTagByUserId(Integer userId, Integer[] ids) {
        // 删除用户原有标签
        makeFriendIntentionDao.deleteByUserId(userId);
        // 添加新标签
        return makeFriendIntentionDao.saveTagByUserId(userId, ids, new Date());
    }
}
