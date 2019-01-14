package com.faceshow.modules.friend.service.impl;

import com.faceshow.modules.friend.dao.FriendRingFileDao;
import com.faceshow.modules.friend.entity.FriendRingFile;
import com.faceshow.modules.friend.service.FriendRingFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 朋友圈文件操作Service接口实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/1/26 14:53
 * -------------------------------------------------------------- <br>
 */
@Service
public class FriendRingFileServiceImpl implements FriendRingFileService {

    @Autowired
    private FriendRingFileDao friendRingFileDao;

    /**
     * 添加文件
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(FriendRingFile record) {
        return friendRingFileDao.insertSelective(record);
    }

    /**
     * 批量添加文件
     *
     * @param fileList
     * @return
     */
    @Override
    public int saveFiles(List<FriendRingFile> fileList) {
        return friendRingFileDao.saveFiles(fileList);
    }
}
