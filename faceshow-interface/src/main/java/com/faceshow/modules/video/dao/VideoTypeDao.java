package com.faceshow.modules.video.dao;

import com.faceshow.modules.sys.dao.BaseDao;
import com.faceshow.modules.video.entity.VideoType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoTypeDao {
    int deleteByPrimaryKey(Integer typeId);

    int insertSelective(VideoType record);

    VideoType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(VideoType record);


    /**
     * 查询视频分类
     *
     * @param country
     * @return
     */
    List<VideoType> findHotType(String country);

    /**
     * 查找当前分类下得分最高的视频
     *
     * @param search 查询条件
     * @return
     */
    List<Object> findHightScoreVideo(Map<String, Object> search);

    /**
     * 查找当前视频分类下得分最高的五个人
     *
     * @param typeId 视频类型id
     * @return
     */
    List<Object> findHightScoreUser(Integer typeId);

    /**
     * 查询视频总数
     *
     * @param search
     * @return
     */
    Integer findVideoTotalCount(Map<String, Object> search);

    /**
     * 查询视频分类20个
     *
     * @return
     */
    List<VideoType> findList(Map<String, Object> map);

    /**
     * 查询类型信息
     *
     * @param typeId
     * @return
     */
    Map<String, Object> findTypeByTypeId(Integer typeId);
}