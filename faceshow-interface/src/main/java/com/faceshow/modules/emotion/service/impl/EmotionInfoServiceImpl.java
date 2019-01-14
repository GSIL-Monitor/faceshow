package com.faceshow.modules.emotion.service.impl;

import com.faceshow.modules.emotion.dao.EmotionInfoDao;
import com.faceshow.modules.emotion.service.EmotionInfoService;
import com.faceshow.modules.makeFriend.vo.MakeFriendIntentionSelectRowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.emotion.service.impl<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 16:56
 * -------------------------------------------------------------- <br>
 */
@Service
public class EmotionInfoServiceImpl implements EmotionInfoService {

    @Autowired
    private EmotionInfoDao emotionInfoDao;

    /**
     * 查询所有标签, 并判定当前用户是否已经拥有该标签
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public List<MakeFriendIntentionSelectRowVo> findAll(Integer userId) {
        return emotionInfoDao.findAll(userId);
    }

    /**
     * 根据用户id删除用户拥有的标签
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public int deleteByUserId(Integer userId) {
        return emotionInfoDao.deleteByUserId(userId);
    }

    /**
     * 修改用户拥有的标签
     *
     * @param userId 用户id
     * @param tagId  标签id数组
     * @return
     */
    @Override
    @Transactional
    public int updateTagByUserId(Integer userId, Integer tagId) {
        // 删除用户原有标签
        emotionInfoDao.deleteByUserId(userId);
        // 添加新标签
        return emotionInfoDao.saveTagByUserId(userId, tagId, new Date());
    }
}
