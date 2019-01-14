package com.faceshow.modules.hobby.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.R;
import com.faceshow.modules.hobby.entity.HobbyInfo;
import com.faceshow.modules.hobby.service.HobbyInfoService;
import com.faceshow.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户兴趣爱好相关
 *
 * @Athor GuoChao
 * @Date Create in 12:59 2018/1/19
 */
@RestController
@RequestMapping("/tokens/hobby")
public class HobbyInfoController extends AbstractController {

    @Autowired
    private HobbyInfoService hobbyInfoService;


    /**
     * 查询所有标签及用户自定义标签
     *
     * @return
     */
    @SysLog
    @PostMapping("/queryHobbyByUserId")
    public Object queryHobbyByUserId() {
        Integer userId = getUserInfoId();
        // 查询所有系统标签 以及自己的标签
        List<HobbyInfo> sysHobbies = hobbyInfoService.findSysHobbyAll(userId);
        // 查询当前用户的所有标签
        List<HobbyInfo> userHobbies = hobbyInfoService.findHobbyByUserId(userId);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sysHobbies", sysHobbies);
        map.put("userHobbies", userHobbies);

        return R.result(1, "信息查询成功", map);
    }

    /**
     * 添加自定义标签
     *
     * @param tags 标签名
     * @param type 标签类型, 0系统管理员添加标签, 1 用户自定义标签
     * @return
     */
    @SysLog
    @PostMapping("/addTags")
    public Object addTags(String tags, Byte type) {
        return hobbyInfoService.addHobby(tags, type, getUserInfoId());
    }

    /**
     * 删除标签名
     *
     * @param hobbyIds 标签id
     * @return
     */
    @SysLog
    @PostMapping("/deleteTags")
    public Object deleteTags(String hobbyIds) {
        return hobbyInfoService.deleteTags(hobbyIds, getUserInfoId());
    }

    /**
     * 修改用户标签
     *
     * @param hobbyIds 标签id
     * @return
     */
    @SysLog
    @PostMapping("/updateTagsByUserId")
    public Object updateTagsByUserId(String hobbyIds) {
        return hobbyInfoService.updateTagsByUserId(hobbyIds, getUserInfoId());
    }
}
