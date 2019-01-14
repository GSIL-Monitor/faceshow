package com.faceshow.modules.user.service.impl;

import com.faceshow.modules.user.dao.PersonalHomeDao;
import com.faceshow.modules.user.entity.PersonalHome;
import com.faceshow.modules.user.service.PersonalHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PersonalHomeServiceImpl implements PersonalHomeService {
    @Autowired
    private PersonalHomeDao personalHomeDao;

    //<!-- 6.2.1.10个人主页页面 -->
    @Override
    public List<Object> findPersonalHome(Map<String, Object> map) {
        ArrayList list = new ArrayList();
        PersonalHome home = personalHomeDao.findPersonalHome(map);//个人中心
        if (home == null) {
            return null;
        }
        List<Object> contributionImg = findContributionImg(map);//获取前三名的贡献榜
        List<Map<String, Object>> imgF = findfriendImgF(map);//朋友圈
        List<Map<String, Object>> maps = personalHomeDao.findfriendImgFScreen(map);//屏蔽谁了
        for (Map<String, Object> stringObjectMap : maps) {
            imgF.add(stringObjectMap);
        }
        List<Map<String, Object>> appoint = personalHomeDao.findfriendImgFAppoint(map);//指定谁可以看朋友圈
        for (Map<String, Object> stringObjectMap : appoint) {
            imgF.add(stringObjectMap);
        }
        List<Map<String, Object>> VideoF = findVideoImgF(map);//视频前四条
        List<Object> selectGroup = selectGroup(map);//加入的群
        List<Object> tags = userTags(map);//用户标签
        if (map.get("account") != null) {
            Map<String, Object> stringObjectMap = personalHomeDao.OrAttention(map);//是否相互关注
            //进入我没有关注人家下面的逻辑判断
            if (ObjectUtils.isEmpty(stringObjectMap)) {
                //如果查不出来 2就是我没有关注人家 但是我并不知道人家是否关注了我, //我查看一下 人家是否关注了我
                Map<String, Object> bOrAttention = personalHomeDao.BOrAttention(map);
                if (ObjectUtils.isEmpty(bOrAttention)) {
                    //如果查不出来 人家没有关注我  我没有关注人家
                    home.setIsTutual(2);
                } else {
                    // 人家偷偷关注我了  我没有关注人家
                    home.setIsTutual(3);
                }
            } else {
                //查出来可能是1可能是0 1代表相互关注 0代表我关注了人家 人家没有关注我
                home.setIsTutual(Integer.parseInt(stringObjectMap.get("is_tutual").toString()));
            }
        }
        list.add(home);
        list.add(contributionImg);
        list.add(imgF);
        list.add(VideoF);
        list.add(selectGroup);
        list.add(tags);
        return list;
    }

    //获取前三名的贡献榜
    @Override
    public List<Object> findContributionImg(Map<String, Object> map) {
        return personalHomeDao.findContributionImg(map);
    }

    //<!--是个主页的视频 还需要分页-->
    @Override
    public List<Object> findVideoImg(Map<String, Object> map) {
        return personalHomeDao.findVideoImg(map);
    }

    //<!--是个主页的视频 还需要分页-->
    @Override
    public int findVideoImgTotal(Map<String, Object> map) {
        return personalHomeDao.findVideoImgTotal(map);
    }

    //<!--是个主页的朋友圈 还需要分页-->
    @Override
    public List<Object> findfriendImg(Map<String, Object> map) {
        return personalHomeDao.findfriendImg(map);
    }

    //<!--是个主页的朋友圈 还需要分页-->
    @Override
    public int findfriendImgTotal(Map<String, Object> map) {
        return personalHomeDao.findfriendImgTotal(map);
    }

    //视频默认四个
    @Override
    public List<Map<String, Object>> findVideoImgF(Map<String, Object> map) {
        return personalHomeDao.findVideoImgF(map);
    }

    //朋友圈默认4个
    @Override
    public List<Map<String, Object>> findfriendImgF(Map<String, Object> map) {
        return personalHomeDao.findfriendImgF(map);
    }

    //<!--加入的群-->
    @Override
    public List<Object> selectGroup(Map<String, Object> map) {
        return personalHomeDao.selectGroup(map);
    }

    /*用户标签*/
    @Override
    public List<Object> userTags(Map<String, Object> map) {
        return personalHomeDao.userTags(map);
    }
}
