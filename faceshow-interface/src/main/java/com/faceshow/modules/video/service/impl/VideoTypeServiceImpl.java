package com.faceshow.modules.video.service.impl;

import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.RedisUtils;
import com.faceshow.modules.video.dao.VideoInfoDao;
import com.faceshow.modules.video.dao.VideoTypeDao;
import com.faceshow.modules.video.entity.VideoType;
import com.faceshow.modules.video.service.VideoTypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoTypeServiceImpl implements VideoTypeService {

    @Autowired
    private VideoTypeDao videoTypeDao;

    @Autowired
    private VideoInfoDao videoInfoDao;
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public int deleteByPrimaryKey(Integer typeId) {
        return videoTypeDao.deleteByPrimaryKey(typeId);
    }

    @Override
    public int insertSelective(VideoType record) {
        return videoTypeDao.insertSelective(record);
    }

    @Override
    public VideoType selectByPrimaryKey(Integer typeId) {
        return videoTypeDao.selectByPrimaryKey(typeId);
    }

    @Override
    public int updateByPrimaryKeySelective(VideoType record) {
        return videoTypeDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 查询视频分类20个
     *
     * @return
     */
    @Override
    public Object list(Map<String,Object> map) {
        //添加缓存
        //查询数据库之前先查询缓存，如果有直接返回
     /*   try {
            //从redis中取缓存数据
            String json = redisUtils.get("video_type");
            if (!StringUtils.isBlank(json)) {
                //把json转换成List
                List<VideoType> list = JsonUtils.jsonToList(json, VideoType.class);
                return R.result(1, "查询成功", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        List<VideoType> list = videoTypeDao.findList(map);//执行查询
        //返回结果之前，向缓存中添加数据
    /*    try {
            //value是list，需要把list转换成json数据。
            redisUtils.set("video_type",JsonUtils.objectToJson(list),2592000);//一个月
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return R.result(1, "查询成功", list);
    }

    /**
     * 进入分类详情页
     *
     * @param typeId 分类id
     * @return
     */
    @Override
    public Object intoVideoType(Integer typeId) {

        // 查询类型简介信息
        Map<String, Object> typeInfo = videoTypeDao.findTypeByTypeId(typeId);

        // 查询该分类下得分最高的前六名
        List<Object> firstSix = videoInfoDao.findHightScoreUser(2, typeId);
        typeInfo.put("firstSix", firstSix);

        return R.ok("OK").put("result", typeInfo);
    }
}
