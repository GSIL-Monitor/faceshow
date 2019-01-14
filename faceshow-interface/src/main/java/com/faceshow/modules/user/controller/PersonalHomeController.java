package com.faceshow.modules.user.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.Query;
import com.faceshow.common.utils.R;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.user.service.PersonalHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tokens/PersonalHome")
public class PersonalHomeController extends AbstractController {
    @Autowired
    private PersonalHomeService PersonalHomeService;

    /**
     * @Author:YS
     * @Description: //<!-- 6.2.1.10个人主页页面 这个都是默认返回四个-->
     * @Date:2018/2/3
     */
    @SysLog
    @PostMapping("/findPersonalHome")
    public Object findPersonalHome(@RequestParam Map<String, Object> map) {
        if (!map.get("userId").toString().equalsIgnoreCase(getUserInfoId().toString())) {
            map.put("account", getUserInfoId());
        }
        List<Object> personalHome = PersonalHomeService.findPersonalHome(map);//<!-- 6.2.1.10个人主页页面 -->
        return R.ok("OK").put("result", personalHome == null ? Collections.emptyList() : personalHome);
    }

    /**
     * @Author:YS
     * @Description:6.2.1.10个人主页页面 分页 视频 我的朋友圈
     * @Date:2018/2/3
     */
    @SysLog
    @PostMapping("/findPersonalHomePage")
    public Object findPersonalHomePage(@RequestParam Map<String, Object> map) {
        ArrayList list = new ArrayList();
        //分页我的朋友圈
        // 显示他发的每条朋友圈的图片和视频封面，纯文字的朋友圈不显示
        //点击图片和更多图标跳转到他的朋友圈
        Query query = new Query(map);
        List<Object> infoDetail = PersonalHomeService.findfriendImg(query);
        int total = PersonalHomeService.findfriendImgTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        //对方发布了多少个短视频，
        //显示短视频的封面，点击视频跳转到短视频详情页面
        Query queryVideo = new Query(map);
        List<Object> infoDetailVideo = PersonalHomeService.findVideoImg(query);
        int totalVideo = PersonalHomeService.findVideoImgTotal(query);
        PageUtils pageUtilVideo = new PageUtils(infoDetailVideo, totalVideo, queryVideo.getLimit(), queryVideo.getPage());
        list.add(pageUtil);
        list.add(pageUtilVideo);
        return R.ok().put("page", list);
    }

}
