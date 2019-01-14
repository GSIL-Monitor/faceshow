package com.faceshow.modules.sys.service.impl;

import com.faceshow.common.utils.Constant;
import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.RedisKeys;
import com.faceshow.common.utils.RedisUtils;
import com.faceshow.modules.sys.dao.SysUserDao;
import com.faceshow.modules.sys.entity.SysUserTokenEntity;
import com.faceshow.modules.sys.service.ShiroService;
import com.faceshow.modules.sys.dao.SysMenuDao;
import com.faceshow.modules.sys.dao.SysUserTokenDao;
import com.faceshow.modules.sys.entity.SysMenuEntity;
import com.faceshow.modules.sys.entity.SysUserEntity;
import com.faceshow.modules.sys.service.SysUserTokenService;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.user.entity.UserInfo;
import com.faceshow.modules.user.service.UserInfoService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenuEntity> menuList = sysMenuDao.queryList(new HashMap<>());
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }


    /**
     * 在redis中回去用户的token 如果有直接放回没有调用queryByToken返回
     * */
    @Override
    public SysUserTokenEntity queryByToken(String token) {
        // 从redis中获取
        String tokenStr = redisUtils.get(RedisKeys.TB_TOKEN + token);
        if (StringUtils.isNotBlank(tokenStr)) {
            return redisUtils.fromJson(tokenStr, SysUserTokenEntity.class);
        }
        // 从数据库中获取
        return sysUserTokenService.queryByToken(token);
    }


    /**
     * 根据token获取到用户的ID然后根据ID获取用户信息，如果是正常登陆的话Redis中是有正常的值
     * */
    @Override
    public UserInfo queryUser(Long userId) {
        // 从redis中获取
        String userStr = redisUtils.get(RedisKeys.USER_INFO + userId);
        if (StringUtils.isNotBlank(userStr)) {
            return redisUtils.fromJson(userStr, UserInfo.class);
        }
        // 从数据库中获取
        return userInfoService.selectByPrimaryKey(userId.intValue());
    }

}
