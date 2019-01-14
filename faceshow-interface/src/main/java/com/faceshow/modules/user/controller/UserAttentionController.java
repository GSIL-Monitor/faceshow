package com.faceshow.modules.user.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.R;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.user.entity.UserAttention;
import com.faceshow.modules.user.service.UserAttentionService;
import org.codehaus.groovy.runtime.typehandling.IntegerMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 关注相关, 用户注册前的数据校验操作<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.user.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 李燕 on 2018/01/24 10:37
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/user/attention")
public class UserAttentionController extends AbstractController {
    @Autowired
    private UserAttentionService userAttentionService;

    /**
     * 添加关注
     * 如果已经被关注则互关
     */
    @SysLog
    @PostMapping("/addAttention")
    public Object addAttention(UserAttention userAttention) {
        Integer userid = getUserInfoId();
        userAttention.setUserId(userid);
        Integer account = userAttention.getAccount();

        //查看是否已经关注
        Integer attention_num = userAttentionService.selectByUserid(userid, account);
        logger.info("获取两个用户的关注状态, userid: " + userid + ", account: " + account + ", 结果为: " + attention_num);
        if (attention_num <= 0) {
            //查看是否已经“被”关注
            Integer Beattention_num = userAttentionService.selectByUserid(account, userid);
            logger.info("查询用户account: " + account + ", 是否已经被用户userid: " + userid + " 所关注, 结果为: " + Beattention_num);
            //如果已经被关注过
            if (Beattention_num > 0) {
                //互关注
                userAttention.setIsTutual((byte) 1);
            }
            userAttention.setCreateTime(new Date());
            //关注
            Integer result = userAttentionService.insertSelective(userAttention);
            logger.info("用户userid: " + userid + " 关注用户account: " + account + ", 关注结果为: " + result);
            if (result == null || result == 0) {
                return R.result(0, "关注失败", "");
            } else {
                userAttentionService.updateTu(account, userid, true);
                return R.result(1, "关注成功", "");
            }
        } else {
            return R.result(0, "已经关注过了", "");
        }
    }

    /**
     * 取消关注
     *
     * @param userAttention
     * @return
     */
    @SysLog
    @PostMapping("/deleteAttention")
    public Object deleteAttention(UserAttention userAttention) {
        Integer userid = getUserInfoId();
        userAttention.setUserId(userid);
        Integer account = userAttention.getAccount();
        Integer re = userAttentionService.deleteByUserid(userid, account);
        if (re > 0) {
            Integer Beattention_num = userAttentionService.selectByUserid(account, userid);//查看是否已经“被”关注
            if (Beattention_num > 0) {
                userAttentionService.updateTu(account, userid, false);//取消互关
            }
            return R.result(1, "取消关注成功", "");
        } else {
            Integer attention_num = userAttentionService.selectByUserid(userid, account);//查看是否已经取消关注
            if (attention_num <= 0) {
                return R.result(1, "已经取消关注，不要重复取消", "");
            } else {
                return R.result(0, "取消关注失败", "");
            }

        }


    }


    /**
     * 我关注的列表
     * types=0 为 我关注的，1为关注我的
     */
    @SysLog
    @PostMapping("/listAttention")
    public Object listAttention(Integer userId, @RequestParam(defaultValue = "0") Integer types, @RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Object> res;
        Integer friendNum;
        if (types == null || types == 0) {
            friendNum = userAttentionService.selectAllNum(getUserInfoId());
            res = userAttentionService.selectAll(getUserInfoId(), (currPage - 1) * pageSize, pageSize);
        } else {
            friendNum = userAttentionService.selectBeAllNum(getUserInfoId());
            res = userAttentionService.selectBeAll(getUserInfoId(), (currPage - 1) * pageSize, pageSize);
        }

        if (res == null) {
            return R.result(0, "获取关注列表失败", "");
        } else {
            return R.result(1, "获取关注列表成功", new PageUtils(res, friendNum, pageSize, currPage));
        }

    }

    /**
     * 好友
     * 相互关注的列表
     */
    @SysLog
    @PostMapping("/getFriendsList")
    public Object getFriendsList(Integer userId, @RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Object> res = userAttentionService.selectFriend(getUserInfoId(), (currPage - 1) * pageSize, pageSize);
        Integer friendNum = userAttentionService.selectFriendNum(getUserInfoId());
        if (res == null) {
            return R.ok().put("code", 0);
        } else {
            return R.ok().put("result", new PageUtils(res, friendNum, pageSize, currPage));
        }
    }

    /**
     * 朋友圈动态屏蔽
     */
    @SysLog
    @PostMapping("/updateshieldcircle")
    public Object updateShieldCircle(Integer userId, Integer account, Byte shieldCircle) {
        Integer res = userAttentionService.updateByUserIdAccount(getUserInfoId(), account, shieldCircle);
        if (res == null || res == 0) {
            return R.result(0, "更新失败", "");
        } else {
            return R.result(1, "更新成功", "");
        }

    }

    /**
     * 屏蔽不看动态的人员列表
     */
    @SysLog
    @PostMapping("/listshieldcircle")
    public Object listShieldCircle(Integer userId, @RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Object> res = userAttentionService.selectAllShield(getUserInfoId(), (currPage - 1) * pageSize, pageSize);
        Integer friendNum = userAttentionService.selectAllShieldNum(getUserInfoId());
        if (res == null) {
            return R.result(0, "获取列表失败", "");
        } else {
            return R.result(1, "获取好友列表成功", new PageUtils(res, friendNum, pageSize, currPage));
        }
    }

}
