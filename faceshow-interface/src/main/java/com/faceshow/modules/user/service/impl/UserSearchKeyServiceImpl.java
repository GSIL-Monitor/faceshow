package com.faceshow.modules.user.service.impl;


import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.StringTool;
import com.faceshow.modules.IM.dao.ImFriendDao;
import com.faceshow.modules.IM.dao.ImGroupDao;
import com.faceshow.modules.live.dao.LiveInfoDao;
import com.faceshow.modules.music.dao.MusicInfoDao;
import com.faceshow.modules.user.dao.*;
import com.faceshow.modules.user.entity.UserSearchKey;
import com.faceshow.modules.user.service.UserSearchKeyService;
import com.faceshow.modules.video.dao.VideoInfoDao;
import com.faceshow.modules.video.dao.VideoTopicDao;
import com.faceshow.modules.video.dao.VideoTypeDao;
import com.faceshow.modules.video.entity.VideoType;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserSearchKeyServiceImpl implements UserSearchKeyService {
    @Autowired
    private LiveInfoDao liveInfoDao;
    @Autowired
    private UserSearchKeyDao userSearchKeyDao;

    @Autowired
    private VideoTopicDao videoTopicDao;

    @Autowired
    private UserNewsletterGroupDao userNewsletterGroupDao;

    @Autowired
    private MusicInfoDao musicInfoDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private VideoTypeDao videoTypeDao;

    @Autowired
    private VideoInfoDao videoInfoDao;
    @Autowired
    private ImFriendDao imFriendDao;
    @Autowired
    private UserFoundDao userFoundDao;
    @Autowired
    private ImGroupDao imGroupDao;


    @Override
    public int deleteByPrimaryKey(Integer keyId) {
        return userSearchKeyDao.deleteByPrimaryKey(keyId);
    }

    @Override
    public int insert(UserSearchKey record) {
        return userSearchKeyDao.insert(record);
    }

    @Override
    public int insertSelective(UserSearchKey record) {
        return userSearchKeyDao.insertSelective(record);
    }

    @Override
    public UserSearchKey selectByPrimaryKey(Integer keyId) {
        return userSearchKeyDao.selectByPrimaryKey(keyId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserSearchKey record) {
        return userSearchKeyDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserSearchKey record) {
        return userSearchKeyDao.updateByPrimaryKey(record);
    }

    /**
     * 进入发现搜索页面, 查询用户信息
     *
     * @param :country 当前用户所在国家
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @param userId   当前查询用户id
     * @param search   查询条件
     * @param type     1用户搜索，2话题搜索，3群组搜索，4音乐
     * @return
     */
    @Override
    public Object intoSearch(Integer typeId, Integer type_id, Integer userId, String search, Integer currPage, Integer pageSize, int type) {
        // 封装查询条件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("typeId", typeId);
        map.put("type_id", type_id);
        map.put("search", search);
        map.put("currPage", (currPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("search", search);

        List<Map<String, Object>> result = null;
        Integer totalCount = null;

        if (type == 1) {
            result = userInfoDao.findUserByCriteria(map);//传递用户ID
            for (Map<String, Object> stringObjectMap : result) {
              //  map.put("userId", stringObjectMap.get("user_id"));
              //  map.put("account", userId);
                  map.put("account", stringObjectMap.get("user_id"));
                 map.put("userId", userId);
                Map byAllId = imFriendDao.findByAllId(map);
                if (byAllId == null) {
                    stringObjectMap.put("is_tutual", "0");
                } else {
                    stringObjectMap.put("is_tutual", "1");
                }
            }
            totalCount = userInfoDao.findUserTotalCount(map);
        } else if (type == 2) {
            result = videoTopicDao.findTopicByCriteria(map);//不需要参数 仅仅需要查询条件
            totalCount = videoTopicDao.findTopicTotalCount(map);
        } else if (type == 3) {
            result = userNewsletterGroupDao.findNewsletterGroupByCriteria(map);//不需要傳遞參數
            for (Map<String, Object> stringObjectMap : result) {
                stringObjectMap.put("userId", userId);
                Map whetherBelong = imGroupDao.WhetherBelong(stringObjectMap);
                if (whetherBelong == null) {
                    stringObjectMap.put("whetherBelong", "0");
                } else {
                    stringObjectMap.put("whetherBelong", "1");
                }
            }


            totalCount = userNewsletterGroupDao.findNewsletterGroupTotalCount(map);
        } else if (type == 4) {
            result = musicInfoDao.findMusicByCriteria(map);//不需要传递参数
            totalCount = musicInfoDao.findMusicTotalCount(map);
        }

        // 向查询表中插入数据
        UserSearchKey userSearchKey = new UserSearchKey();
        userSearchKey.setCreateTime(new Date());
        userSearchKey.setKeyValue(StringTool.notEmpty(search) ? search : "无条件查询查询");
        userSearchKey.setSearchType((byte) type);
        userSearchKey.setUserId(userId);
        try {
            userSearchKeyDao.insertSelective(userSearchKey);
        } catch (Exception e) {
        }


        return R.result(1, "查询成功", new PageUtils(result, totalCount, pageSize, currPage));
    }


    /**
     * 进入发现页面
     *
     * @param country  当前国家
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @return
     */
    @Override
    public Object intoFind(String country, Integer currPage, Integer pageSize) {
        // 查询热门视频分类
        List<VideoType> hotVideoType = videoTypeDao.findHotType(country);

        // 分页查询热门话题
        Map<String, Object> pageQuery = new HashMap<String, Object>(0);
        pageQuery.put("currPage", (currPage - 1) * pageSize);
        pageQuery.put("pageSize", pageSize);
        List<Map<String, Object>> hotTopic = videoTopicDao.findTopicByCriteria(pageQuery);
        // 查询话题总数
        Integer topicTotalCount = videoTopicDao.findTopicTotalCount(pageQuery);

        // 查询热门话题下面的热门视频
        for (Object obj : hotTopic) {
            Map<String, Object> map = (Map<String, Object>) obj;
            // 热门视频
            List<Object> hotVideo = videoInfoDao.findHotVideoByTypeId(Integer.parseInt(map.get("topic_id").toString()));
            map.put("hotVideo", hotVideo);
        }

        Map<String, Object> result = new HashMap<String, Object>(0);
        result.put("hotVideoType", hotVideoType);
        result.put("hotTopic", new PageUtils(hotTopic, topicTotalCount, pageSize, currPage));

        return result;
    }

    /**
     * @author: YS
     * @Date:2018/4/14 9:24
     * @param: type_id
     * @explain：<!--发现页面的某种类型的视频发布的所有短视频在7天内获得的赞数，按倒叙排列-->
     * @return:
     */
    @Override
    public List<Map<String, Object>> weekSort(Map<String, Object> map) {
        return userFoundDao.weekSort(map);
    }

    @Override
    public int weekSortTotal(Map<String, Object> map) {
        return userFoundDao.weekSortTotal(map);
    }

    /**
     * @author: YS
     * @Date:2018/4/14 10:00
     * @param:
     * @explain： 查询热门音乐
     * @return:
     */
    @Override
    public List<Map<String, Object>> hotMusic(Map<String, Object> map) {
        return userFoundDao.hotMusic(map);
    }

    @Override
    public int hotMusicTotal(Map<String, Object> map) {
        return userFoundDao.hotMusicTotal(map);
    }


    /**
     * @author: YS
     * @Date:2018/4/14 11:15
     * @param:
     * @explain： 热门视频 带分页
     * @return:
     */
    @Override
    public List<Map<String, Object>> hotVideo(Map<String, Object> map) {
        return userFoundDao.hotVideo(map);
    }

    @Override
    public int hotVideoTotal(Map<String, Object> map) {
        return userFoundDao.hotVideoTotal(map);
    }

    /**
     * 排行榜, 查询用户获赞数
     *
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @param country  当前国家
     * @return
     */
    @Override
    public Object intoRanklist(String country, Integer currPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("currPage", (currPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("country", country);
        List<Object> list = userSearchKeyDao.findRanklist(map);
        if (list == null || list.size() < 1) {
            return R.result(0, "没有查询到数据", "");
        }
        map.put("search", country);
        Integer totalCount = userInfoDao.findUserTotalCount(map);

        return R.result(1, "查询成功", new PageUtils(list, totalCount, pageSize, currPage));
    }

    /**
     * @author: YS
     * @Date:2018/4/13 15:46
     * @param:
     * @explain： <!-- 热门后台推荐的话题 这个是发现页面使用-->
     * @return:
     */
    @Override
    public Object findHotTopic() {
        List<Map<String, Object>> hotTopic = userFoundDao.findHotTopic();
        for (Map<String, Object> stringObjectMap : hotTopic) {
            List<Map<String, Object>> selectByTopic = userFoundDao.selectByTopic(stringObjectMap);
            stringObjectMap.put("video", selectByTopic);
        }
        return hotTopic;
    }

    /**
     * @author: YS
     * @Date:2018/4/13 16:18
     * @param:
     * @explain：发现的页面 点击更多调取接口带分页
     * @return:
     */
    @Override
    public int selectByTopicTotal(Map<String, Object> map) {
        return userFoundDao.selectByTopicTotal(map);
    }

    /**
     * @author: YS
     * @Date:2018/4/13 16:18
     * @param:
     * @explain：发现的页面 点击更多调取接口带分页
     * @return:
     */
    @Override
    public List<Map<String, Object>> selectByTopic(Map<String, Object> map) {
        return userFoundDao.selectByTopic(map);
    }

    /**
     * @author: YS
     * @Date:2018/4/13 17:27
     * @param:
     * @explain： <!--进入发现页面随机返回-->
     * @return: 搜索类型， 1用户搜索，2话题搜索，3群组搜索，4音乐
     */
    @Override
    public List<Map<String, Object>> recommendUser(Map<String, Object> map) {
        List<Map<String, Object>> recommend = null;
        if (map.get("type").toString().equals("1")) {
            recommend = userFoundDao.recommendUser(map);//人用户6个本国4个外国 传递国家ID
            for (Map<String, Object> stringObjectMap : recommend) {
                map.put("juser_id", stringObjectMap.get("user_id"));
                map.put("yuser_id", map.get("userId"));
                Map<String, Object> orAttention = liveInfoDao.OrAttention(map);
                if (orAttention == null) {
                    stringObjectMap.put("is_tutual", "0");
                } else {
                    stringObjectMap.put("is_tutual", orAttention.get("is_tutual"));
                }
            }
        } else if (map.get("type").toString().equals("2")) {
            recommend = userFoundDao.findHotTopic();//热门话题 官方推荐
        } else if (map.get("type").toString().equals("3")) {
            recommend = imGroupDao.selectGroupOrderByActivity(map);// <!--發現推荐，按照活跃度排行-->
            for (Map<String, Object> stringObjectMap : recommend) {
                stringObjectMap.put("userId", map.get("userId"));
                Map whetherBelong = imGroupDao.WhetherBelong(stringObjectMap);
                if (whetherBelong == null) {
                    stringObjectMap.put("whetherBelong", "0");
                } else {
                    stringObjectMap.put("whetherBelong", "1");
                }

            }

        } else if (map.get("type").toString().equals("4")) {
            recommend = musicInfoDao.recommendMusic();//音乐默认按照使用程度返回  带分页
        }
        return recommend;
    }
}
