package com.faceshow.modules.video.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.Query;
import com.faceshow.common.utils.R;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.video.service.VideoTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 视频话题操作<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.video.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/1/25 16:28
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/topic")
public class VideoTopicController extends AbstractController {

    @Autowired
    private VideoTopicService videoTopicService;

    /**
     * 进入话题详情页
     *
     * @param topicId  话题id
     * @return
     */
    @SysLog
    @PostMapping("/intoVideoTopic")
    public Object intoVideoTopic(Integer topicId) {
        return videoTopicService.intoVideoTopic(topicId);
    }

    /**
     * @Author:YS
     * @Description:查找相关数据
     * @Date:2018/2/25
     */
    @SysLog
    @PostMapping("/findTopicRelevant")
    public Object findTopicRelevant(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = videoTopicService.findTopicRelevant(query);
        int total = videoTopicService.findTopicRelevantTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * @param map 查询条件
     *            limit 每页显示信息数
     *            page 当前页
     * @Author:YS
     * @Description: 查找话题表 所有
     * @Date:2018/2/26
     */
    @SysLog
    @PostMapping("/findAll")
    public Object findAll(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> list = videoTopicService.findAll(query);
        Integer total = videoTopicService.findAllTotal();
        return R.ok().put("page", new PageUtils(list, total, query.getLimit(), query.getPage()));
    }

    /**
     * @Author:YS
     * @Description: <!--查看这个话题参与者按照魅力值分页-->
     * @Date:2018/2/26
     * @param: type_id 话题类型Id
     */
    @SysLog
    @PostMapping("/findRankingList")
    public Object findRankingList(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Object> infoDetail = videoTopicService.findRankingList(query);
        int total = videoTopicService.findRankingListTotal(query);
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }
}
