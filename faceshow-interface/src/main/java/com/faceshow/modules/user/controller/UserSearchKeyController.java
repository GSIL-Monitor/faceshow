package com.faceshow.modules.user.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.Query;
import com.faceshow.common.utils.R;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.user.service.UserSearchKeyService;
import com.faceshow.modules.video.service.VideoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 用户搜索发现页面功能实现<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.user.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/1/24 15:28
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/search")
public class UserSearchKeyController extends AbstractController {

    @Autowired
    private UserSearchKeyService userSearchKeyService;

    @Autowired
    private VideoTypeService videoTypeService;

    /**
     * @author: YS
     * @Date:2018/4/13 14:08
     * @param:
     * @explain： 發現的首页展示 官方热度推荐的话题
     * @return:
     */
    @SysLog
    @PostMapping("/intoFind")
    public Object intoFind(String country, @RequestParam(defaultValue = "1") Integer currPage,  @RequestParam(defaultValue = "10") Integer pageSize) {
        return R.result(1,"ok",userSearchKeyService.intoFind( country,  currPage,  pageSize));
    }

    /**
     * 进入发现搜索页面, 查询用户信息
     *
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @param userId   当前查询用户id
     * @param search   查询条件
     * @param type     1用户搜索，2话题搜索，3群组搜索，4音乐
     * @return
     */
    @SysLog
    @PostMapping("/intoSearch")
    public Object intoSearch(Integer typeId, Integer type_id,Integer userId, String search, @RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer type) {
        return userSearchKeyService.intoSearch(typeId,type_id, getUserInfoId(), search, currPage, pageSize, type);
    }

    /**
     * 视频分类内容详情
     *
     * @param typeId   查询视频类型id
     * @return
     */
    @SysLog
    @PostMapping("/findVideoTypeInfo")
    public Object findVideoTypeInfo(Integer typeId) {
        return videoTypeService.intoVideoType(typeId);
    }

    /**
     * 进入排行榜
     *
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @param country  国家
     * @return
     */
    @SysLog
    @PostMapping("intoRanklist")
    public Object intoRanklist(String country, @RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        return userSearchKeyService.intoRanklist(country, currPage, pageSize);
    }
    /**
     * @author: YS
     * @Date:2018/4/13 15:46
     * @param:
     * @explain：   <!-- 热门后台推荐的话题 这个是发现页面使用-->
     * @return:
     */
    @SysLog
    @PostMapping("findHotTopic")
    public Object findHotTopic() {
        return R.result(1,"查詢成功",userSearchKeyService.findHotTopic()) ;
    }
    /**
     * @author: YS
     *@Date:2018/4/13 16:18
     * @param: topic_id   limit page
     * @explain：发现的页面 点击更多调取接口带分页
     * @return:
     */
    @SysLog
    @PostMapping("selectByTopicTotal")
    public Object selectByTopicTotal(@RequestParam Map<String,Object>map) {
        Query query = new Query(map);
        List<Map<String, Object>> maps = userSearchKeyService.selectByTopic(query);
        int total = userSearchKeyService.selectByTopicTotal(query);
        PageUtils pageUtil = new PageUtils(maps, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);

    }

    /**
     * @author: YS
     * @Date:2018/4/13 17:27
     * @param:
     * @explain： <!--进入发现页面随机返回-->
     * @return: 搜索类型， 1用户搜索，2话题搜索，3群组搜索，4音乐
     */
    @SysLog
    @PostMapping("recommendUser")
    public Object recommendUser(@RequestParam Map<String,Object>map) {
        return R.result(1,"查詢成功",userSearchKeyService.recommendUser(map)) ;
    }

    /**
     * @author: YS
     * @Date:2018/4/14 9:24
     * @param: type_id
     * @explain：<!--发现页面的某种类型的视频发布的所有短视频在7天内获得的赞数，按倒叙排列-->
     * @return:
     */
    @SysLog
    @PostMapping("weekSort")
    public Object weekSort(@RequestParam Map<String,Object>map) {
        Query query = new Query(map);
        List<Map<String,Object>> infoDetail = userSearchKeyService.weekSort(query);
        int total = userSearchKeyService.weekSortTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }
    /**
     * @author: YS
     * @Date:2018/4/14 10:00
     * @param:
     * @explain： 查询热门音乐
     * @return:
     */
    @SysLog
    @PostMapping("hotMusic")
    public Object hotMusic(@RequestParam Map<String,Object>map) {
        if (map.get("limit")==null){
            map.put("limit",10);
            map.put("page",1);
        }
        Query query = new Query(map);
        List<Map<String, Object>> infoDetail = userSearchKeyService.hotMusic(query);
        int total = userSearchKeyService.hotMusicTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }


    /**
     * @author: YS
     * @Date:2018/4/14 11:15
     * @param:
     * @explain： 热门视频 带分页
     * @return:
     */
    @SysLog
    @PostMapping("/hotVideo")
    public Object hotVideo(@RequestParam Map<String,Object> map){
        Query query = new Query(map);
        List<Map<String, Object>> infoDetail = userSearchKeyService.hotVideo(query);
        int total = userSearchKeyService.hotVideoTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }
}

