package com.faceshow.modules.friend.service;

import com.faceshow.modules.friend.entity.FriendRingFile;

import java.util.List;

/**
 * 朋友圈文件操作Service接口
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/1/26 14:53
 * -------------------------------------------------------------- <br>
 */
public interface FriendRingFileService {

    /**
     * 添加文件
     *
     * @param record
     * @return
     */
    int insertSelective(FriendRingFile record);

    /**
     * 批量添加文件
     *
     * @param fileList
     * @return
     */
    int saveFiles(List<FriendRingFile> fileList);
}
