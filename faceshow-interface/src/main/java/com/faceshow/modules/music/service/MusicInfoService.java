package com.faceshow.modules.music.service;


import com.faceshow.modules.music.entity.MusicInfo;

import java.util.List;
import java.util.Map;

public interface MusicInfoService {

    int deleteByPrimaryKey(Integer musicId);


    int insertSelective(MusicInfo record);

    MusicInfo selectByPrimaryKey(Integer musicId);

    int updateByPrimaryKeySelective(MusicInfo record);


    /**
     * 进入音乐详情页
     *
     * @param musicId 音乐id
     * @return
     */
    Object intoMusic(Integer musicId);

    /**
     * 查询音乐列表
     *
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @return
     */
    Object findMusicAll(Integer currPage, Integer pageSize);

    /**
     * 根据音乐类型查询音乐列表
     * <p>
     * typeId 音乐类型ID
     * search 查询条件
     *
     * @param map
     * @return
     */
    Object findMusicByType(Map<String, Object> map);

    /**
     * @Author:YS
     * @Description:直播的时候的背景音乐
     * @Date:2018/3/13
     * @param:No such property: code for class: Script1
     */

    List<Map<String, Object>> backGroundMusic(Map<String, Object> map);

    int backGroundMusicTotal();

    /**
     * 根据音乐url查询音乐信息
     *
     * @param url url路径
     * @return
     */
    Map<String, Object> findMusicByUrl(String url);
}
