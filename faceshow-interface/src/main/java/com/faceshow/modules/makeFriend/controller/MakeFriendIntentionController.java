package com.faceshow.modules.makeFriend.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.R;
import com.faceshow.modules.makeFriend.service.MakeFriendIntentionService;
import com.faceshow.modules.makeFriend.vo.MakeFriendIntentionSelectRowVo;
import com.faceshow.modules.sys.controller.AbstractController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 用户交友标签操作controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.makeFriend.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/18 14:24
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/make/friend")
public class MakeFriendIntentionController extends AbstractController {

    @Autowired
    private MakeFriendIntentionService makeFriendIntentionService;

    /**
     * 查询所有标签, 并判定当前用户是否已经拥有该标签
     *
     * @param userId 用户id
     * @return
     */
    @SysLog
    @PostMapping("/findByUserId")
    public Object findByUserId(Integer userId) {
        List<MakeFriendIntentionSelectRowVo> list = makeFriendIntentionService.findAll(getUserInfoId());
        return R.ok("OK").put("result", list == null ? Collections.emptyList() : list);
    }

    /**
     * 修改用户交友状态标签
     *
     * @param userId 用户id
     * @param tagIds 用户标签id, 多个使用逗号拼接
     * @return
     */
    @SysLog
    @PostMapping("/updateTag")
    public Object updateTag(Integer userId, String tagIds) {

        // 用户标签不存在, 删除所有用户交友标签
        if (StringUtils.isBlank(tagIds)) {
            int i = makeFriendIntentionService.deleteByUserId(getUserInfoId());
            return R.result(1, "OK", "");
        }

        Integer[] ids = Stream.of(tagIds.split(",")).map(Integer::valueOf).collect(Collectors.toList()).toArray(new Integer[]{});

        int i = makeFriendIntentionService.updateTagByUserId(getUserInfoId(), ids);
        return R.result(1, "OK", "");
    }
}
