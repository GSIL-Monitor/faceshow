package com.faceshow.modules.music.dao;

import com.faceshow.modules.music.entity.MusicInfo;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MusicInfoDao extends BaseDao<Object> {

    int deleteByPrimaryKey(Integer musicId);

    int insertSelective(MusicInfo record);

    MusicInfo selectByPrimaryKey(Integer musicId);

    int updateByPrimaryKeySelective(MusicInfo record);

    /**
     * 首次进入发现搜索页面展示数据
     *
     * @param map 分页参数
     * @return
     */
    List<Map<String, Object>> findMusicByCriteria(Map<String, Object> map);

    /**
     * 进入音乐详情页面查询音乐和用户信息
     *
     * @param musicId 音乐id
     * @return
     */
    Map<String, Object> findUserAndMumicByMusicId(Integer musicId);

    /**
     * 查询得分最高的视频
     *
     * @param map 查询条件
     * @return
     */
    List<Object> findHightScoreVideo(Map<String, Object> map);

    /**
     * 视屏总数
     *
     * @param musicId
     * @return
     */
    Integer findHightScoreVideoTotalCount(Integer musicId);

    /**
     * 查询音乐总数
     *
     * @return
     */
    Integer findMusicTotalCount(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description:直播的时候的背景音乐
     * @Date:2018/3/13
     * @param:No such property: code for class: Script1
     */

    List<Map<String, Object>> backGroundMusic(Map<String, Object> map);

    int backGroundMusicTotal();

    /**
     * @author: YS
     * @Date:2018/4/13 20:38
     * @param:
     * @explain： 首页发现页面使用
     * @return:
     */
    List<Map<String, Object>> recommendMusic();

    /**
     * 根据音乐url查询音乐信息
     *
     * @param url url路径
     * @return
     */
    Map<String, Object> findMusicByUrl(String url);
}
