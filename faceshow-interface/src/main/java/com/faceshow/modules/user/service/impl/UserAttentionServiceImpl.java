package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.dao.UserAttentionDao;
import com.faceshow.modules.user.entity.UserAttention;
import com.faceshow.modules.user.service.UserAttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Athor GuoChao
 * @Date Create in 11:58 2018/1/15
 */
@Service
public class UserAttentionServiceImpl implements UserAttentionService {

    @Autowired
    private UserAttentionDao userAttentionDao;

    @Override
    public int deleteByPrimaryKey(Integer attentionId) {
        return userAttentionDao.deleteByPrimaryKey(attentionId);
    }

    @Override
    public int insert(UserAttention record) {
        return userAttentionDao.insert(record);
    }

    @Override
    public int insertSelective(UserAttention record) {
        return userAttentionDao.insertSelective(record);
    }

    @Override
    public UserAttention selectByPrimaryKey(Integer attentionId) {
        return userAttentionDao.selectByPrimaryKey(attentionId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserAttention record) {
        return userAttentionDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserAttention record) {
        return userAttentionDao.updateByPrimaryKey(record);
    }

    @Override
    public int selectByUserid(Integer userId, Integer account) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("account", account);
        return userAttentionDao.selectByUserid(map);
    }

    @Override
    //@Transactional
    public int updateTu(Integer userId, Integer account, Boolean isTutual) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("account", account);
        map.put("isTutual", isTutual);
        return userAttentionDao.updateTu(map);
    }

    @Override
    public int deleteByUserid(Integer userId, Integer account) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("account", account);
        return userAttentionDao.deleteByUserid(map);
    }

    @Override
    public List<Object> selectAll(Integer userId, Integer begin, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("begin", begin);
        map.put("userId", userId);
        map.put("pageSize", pageSize);
        return userAttentionDao.selectAll(map);
    }

    @Override
    public List<Object> selectBeAll(Integer account, Integer begin, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("begin", begin);
        map.put("account", account);
        map.put("pageSize", pageSize);
        map.put("user_id", account);
        return userAttentionDao.selectBeAll(map);

    }

    @Override
    public List<Object> selectAllShield(Integer userId, Integer begin, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("begin", begin);
        map.put("userId", userId);
        map.put("pageSize", pageSize);
        return userAttentionDao.selectAllShield(map);
    }

    @Override
    public int deleteBack(Integer userId, Integer account) {
        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("userId", userId);
        return userAttentionDao.deleteBack(map);

    }

    @Override
    public int updateByUserIdAccount(Integer userId, Integer account, Byte shieldCircle) {
        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("userId", userId);
        map.put("shieldCircle", shieldCircle);
        return userAttentionDao.updateByUserIdAccount(map);
    }

    @Override
    public List<Object> selectFriend(Integer userId, Integer begin, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("begin", begin);
        map.put("userId", userId);
        map.put("pageSize", pageSize);
        return userAttentionDao.selectFriend(map);
    }

    @Override
    public int selectFriendNum(Integer userId) {
        return userAttentionDao.selectFriendNum(userId);
    }

    @Override
    public int selectAllNum(Integer userId){
        return  userAttentionDao.selectAllNum(userId);
    }

    @Override
    public int selectBeAllNum(Integer userId){
        return  userAttentionDao.selectBeAllNum(userId);
    }//被关注个数

    @Override
    public int selectAllShieldNum(Integer userId){
        return  userAttentionDao.selectAllShieldNum(userId);
    }//屏蔽动态的列表个数
}
