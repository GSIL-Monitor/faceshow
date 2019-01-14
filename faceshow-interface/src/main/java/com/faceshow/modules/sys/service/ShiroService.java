package com.faceshow.modules.sys.service;

import com.faceshow.modules.sys.entity.SysUserTokenEntity;
import com.faceshow.modules.sys.entity.SysUserEntity;
import com.faceshow.modules.user.entity.UserInfo;

import java.util.Set;

/**
 * shiro相关接口
 * @author Gaosx
 * @email Gaoshanxi@gmail.com
 * @date 2017-06-06 8:49
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    UserInfo queryUser(Long userId);
}
