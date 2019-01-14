package com.faceshow.modules.music.service.impl;


import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.Query;
import com.faceshow.common.utils.R;
import com.faceshow.modules.music.dao.MusicInfoDao;
import com.faceshow.modules.music.entity.MusicInfo;
import com.faceshow.modules.music.service.MusicInfoService;
import com.faceshow.modules.video.dao.VideoInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.templatewriter.ITemplateWriter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MusicInfoServiceImpl implements MusicInfoService {

    @Autowired
    private MusicInfoDao musicInfoDao;

    @Autowired
    private VideoInfoDao videoInfoDao;

    @Override
    public int deleteByPrimaryKey(Integer musicId) {
        return musicInfoDao.deleteByPrimaryKey(musicId);
    }


    @Override
    public int insertSelective(MusicInfo record) {
        return musicInfoDao.insertSelective(record);
    }

    @Override
    public MusicInfo selectByPrimaryKey(Integer musicId) {
        return musicInfoDao.selectByPrimaryKey(musicId);
    }

    @Override
    public int updateByPrimaryKeySelective(MusicInfo record) {
        return musicInfoDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 进入音乐详情页
     *
     * @param musicId 音乐id
     * @return
     */
    @Override
    public Object intoMusic(Integer musicId) {

        // 查询音乐发起人信息及音乐简介信息
        Map<String, Object> mumicInfo = musicInfoDao.findUserAndMumicByMusicId(musicId);

        // 查询得分最高的六个用户
        List<Object> firstSix = videoInfoDao.findHightScoreUser(3, musicId);
        mumicInfo.put("firstSix", firstSix);

        return R.ok("OK").put("result", mumicInfo);
    }

    /**
     * 查询音乐列表
     *
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @return
     */
    @Override
    public Object findMusicAll(Integer currPage, Integer pageSize) {
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("currPage", (currPage - 1) * pageSize);
        search.put("pageSize", pageSize);
        List<Map<String, Object>> musics = musicInfoDao.findMusicByCriteria(search);

        // 查询音乐总数
        Integer musicTotalCount = musicInfoDao.findMusicTotalCount(search);
        return R.result(1, "查询成功", new PageUtils(musics, musicTotalCount, pageSize, currPage));
    }

    /**
     * 根据音乐类型查询音乐列表
     * <p>
     * typeId 音乐类型ID
     * search 查询条件
     *
     * @param map
     * @return
     */
    @Override
    public Object findMusicByType(Map<String, Object> map) {
        if (map.get("limit") == null) {
            map.put("limit", 10);
        }
        if (map.get("page") == null) {
            map.put("page", 1);
        }
        Query query = new Query(map);
        List<Object> list = musicInfoDao.queryList(map);
        int total = musicInfoDao.queryTotal(query);
        return R.result(1, "查询成功", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * @Author:YS
     * @Description:直播的时候的背景音乐
     * @Date:2018/3/13
     * @param:No such property: code for class: Script1
     */

    @Override
    public List<Map<String, Object>> backGroundMusic(Map<String, Object> map) {
        List<Map<String, Object>> maps = musicInfoDao.backGroundMusic(map);
        for (Map<String, Object> stringObjectMap : maps) {
            String url = stringObjectMap.get("url").toString();
            stringObjectMap.put("musicName", url.substring(url.lastIndexOf("/") + 1));
        }
        return maps;
    }

    @Override
    public int backGroundMusicTotal() {
        return musicInfoDao.backGroundMusicTotal();
    }

    /**
     * 根据音乐url查询音乐信息
     *
     * @param url url路径
     * @return
     */
    @Override
    public Map<String, Object> findMusicByUrl(String url) {
        return musicInfoDao.findMusicByUrl(url);
    }

}
