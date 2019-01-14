package com.faceshow.modules.friend.dao;

import com.faceshow.modules.friend.entity.FriendRingFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 朋友圈文件操作Dao接口
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/1/26 14:53
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface FriendRingFileDao {
    int deleteByPrimaryKey(Integer fileId);

    int insertSelective(FriendRingFile record);

    FriendRingFile selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(FriendRingFile record);

    /**
     * 批量添加文件
     *
     * @param fileList
     * @return
     */
    int saveFiles(List<FriendRingFile> fileList);

    /**
     * 获取当前朋友圈的图片
     *
     * @param friendId 朋友圈id
     * @return
     */
    List<Object> findFileByFriendId(Integer friendId);

    /**
     * 根据朋友圈id删除文件
     *
     * @param friendId 朋友圈id
     * @return
     */
    Integer deleteFileByFriendId(Integer friendId);
}