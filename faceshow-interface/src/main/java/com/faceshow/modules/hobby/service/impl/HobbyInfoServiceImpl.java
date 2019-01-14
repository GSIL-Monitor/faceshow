package com.faceshow.modules.hobby.service.impl;

import com.faceshow.common.utils.R;
import com.faceshow.common.utils.StringTool;
import com.faceshow.modules.hobby.dao.HobbyInfoDao;
import com.faceshow.modules.hobby.entity.HobbyInfo;
import com.faceshow.modules.hobby.entity.HobbyUserInfo;
import com.faceshow.modules.hobby.service.HobbyInfoService;
import com.faceshow.modules.hobby.service.HobbyUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Athor GuoChao
 * @Date Create in 14:54 2018/1/20
 */
@Service
public class HobbyInfoServiceImpl implements HobbyInfoService {

    @Autowired
    private HobbyInfoDao hobbyInfoDao;

    @Autowired
    private HobbyUserInfoService hobbyUserInfoService;

    @Override
    public int deleteByPrimaryKey(Integer hobbyId) {
        return hobbyInfoDao.deleteByPrimaryKey(hobbyId);
    }

    @Override
    public int insert(HobbyInfo record) {
        return hobbyInfoDao.insert(record);
    }

    @Override
    public int insertSelective(HobbyInfo record) {
        return hobbyInfoDao.insertSelective(record);
    }

    @Override
    public HobbyInfo selectByPrimaryKey(Integer hobbyId) {
        return hobbyInfoDao.selectByPrimaryKey(hobbyId);
    }

    @Override
    public int updateByPrimaryKeySelective(HobbyInfo record) {
        return hobbyInfoDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(HobbyInfo record) {
        return hobbyInfoDao.updateByPrimaryKey(record);
    }

    /**
     * 查询所有系统定义标签
     *
     * @return
     */
    @Override
    public List<HobbyInfo> findSysHobbyAll(Integer userId) {
        return hobbyInfoDao.findSysHobbyAll(userId);
    }

    /**
     * 查询当前用户的所有标签
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public List<HobbyInfo> findHobbyByUserId(Integer userId) {
        return hobbyInfoDao.findHobbyByUserId(userId);
    }

    /**
     * 保存新添加的兴趣爱好标签
     *
     * @param tags 标签名
     * @param type 标签类型, 0 系统添加, 1 用户自定义
     * @return
     */
    @Override
    @Transactional
    public Object addHobby(String tags, Byte type, Integer userId) {
        HobbyInfo hobbyInfo = new HobbyInfo();
        hobbyInfo.setCreateTime(new Date());
        hobbyInfo.setTags(tags);
        hobbyInfo.setType(type);
        hobbyInfoDao.insertSelective(hobbyInfo);

        if (type == 1) {
            // 用户自定义
            HobbyUserInfo hobbyUserInfo = new HobbyUserInfo();
            hobbyUserInfo.setUserId(userId);
            hobbyUserInfo.setHobbyId(hobbyInfo.getHobbyId());
            hobbyUserInfo.setCreateTime(new Date());
            hobbyUserInfoService.insertSelective(hobbyUserInfo);
        }

        return R.result(1, "标签添加成功", hobbyInfo);
    }

    /**
     * 删除标签名
     *
     * @param hobbyIds 标签id
     * @param userId   用户id, 如果是系统管理员删除标签此参数不用填写
     * @return
     */
    @Override
    public Object deleteTags(String hobbyIds, Integer userId) {
        if (StringTool.isEmpty(hobbyIds)) {
            return R.result(0, "没有要删除的标签", "");
        }
        String[] split = hobbyIds.split(",");

        if (userId != null) {
            return hobbyUserInfoService.deleteByUserIdAndHobbyId(split, userId);
        }

        for (String id : split) {
            hobbyInfoDao.deleteByPrimaryKey(Integer.parseInt(id));
        }

        return R.result(1, "删除成功", "");
    }

    /**
     * 修改用户标签
     *
     * @param hobbyIds 标签id
     * @param userId   用户id
     * @return
     */
    @Override
    public Object updateTagsByUserId(String hobbyIds, Integer userId) {
        // 删除原来的标签
        hobbyUserInfoService.deleteByUserId(userId);

        if (StringTool.isEmpty(hobbyIds)) {
            return R.result(1, "OK", "");
        }


        String[] split = hobbyIds.split(",");
        List<HobbyUserInfo> list = new ArrayList<HobbyUserInfo>();
        for (String id : split) {
            HobbyUserInfo hobbyUserInfo = new HobbyUserInfo();
            hobbyUserInfo.setHobbyId(Integer.parseInt(id));
            hobbyUserInfo.setUserId(userId);
            hobbyUserInfo.setCreateTime(new Date());
            list.add(hobbyUserInfo);
        }

        // 添加新标签
        hobbyUserInfoService.addHobbyList(list);

        // 查询当前用户所有的标签
        List<HobbyInfo> hobbyList = hobbyInfoDao.findHobbyByUserId(userId);
        if (hobbyList == null || hobbyList.size() < 1) {
            return R.result(0, "该用户没有标签", "");
        }

        StringBuilder sb = new StringBuilder();
        for (HobbyInfo hobbyInfo : hobbyList) {
            sb.append(hobbyInfo.getTags());
            sb.append(",");
        }

        // 返回用户所有标签名
        String strHobbys = sb.toString();
        return R.result(1, "修改成功", strHobbys.substring(0, strHobbys.length() - 1));
    }

    /**
     * 查询用户拥有的标签
     * @param userId 用户id
     * @return
     */
    @Override
    public List<String> findHobbyTagsByUserId(Integer userId){
        return hobbyInfoDao.findHobbyTagsByUserId(userId);
    }

}
