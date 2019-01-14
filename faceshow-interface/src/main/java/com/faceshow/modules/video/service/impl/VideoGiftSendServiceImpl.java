package com.faceshow.modules.video.service.impl;

import com.faceshow.modules.video.dao.VideoGiftSendDao;
import com.faceshow.modules.video.service.VideoGiftSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 视频礼物操作Service接口实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/2 14:13
 * -------------------------------------------------------------- <br>
 */
@Service
public class VideoGiftSendServiceImpl implements VideoGiftSendService {

    @Autowired
    private VideoGiftSendDao videoGiftSendDao;

    @Override
    public int insertSelective(Map<String,Object> map) {
        return videoGiftSendDao.insertSelective(map);
    }
}
