package com.faceshow.modules.sys.service.impl;

import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.RedisKeys;
import com.faceshow.common.utils.RedisUtils;
import com.faceshow.modules.sys.entity.SysUserTokenEntity;
import com.faceshow.modules.sys.oauth2.TokenGenerator;
import com.faceshow.modules.sys.dao.SysUserTokenDao;
import com.faceshow.modules.sys.service.SysUserTokenService;
import com.faceshow.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl implements SysUserTokenService {
    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    @Autowired
    private RedisUtils redisUtils;

    //30天后过期
    private final static int EXPIRE = 3600 * 24 * 30 * 24;

    @Override
    public SysUserTokenEntity queryByUserId(Long userId) {
        return sysUserTokenDao.queryByUserId(userId);
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        SysUserTokenEntity tokenEntity = sysUserTokenDao.queryByToken(token);
        if (tokenEntity != null) {
            // 保存token到redis
            redisUtils.set(RedisKeys.TB_TOKEN + token, tokenEntity, EXPIRE);
        }
        return tokenEntity;
    }

    @Override
    public void save(SysUserTokenEntity token) {
        sysUserTokenDao.save(token);
    }

    @Override
    public void update(SysUserTokenEntity token) {
        sysUserTokenDao.update(token);
    }

    @Override
    public R createToken(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        // 从redis中获取token信息
        SysUserTokenEntity tokenEntity = queryByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //保存token
            save(tokenEntity);
        } else {
            // 删除之前的token信息
            redisUtils.delete(RedisKeys.TB_TOKEN + tokenEntity.getToken());
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            update(tokenEntity);
        }

        // 保存token到redis
        redisUtils.set(RedisKeys.TB_TOKEN + token, tokenEntity, EXPIRE);

        R r = R.ok().put("token", token).put("expire", EXPIRE);

        return r;
    }
}
