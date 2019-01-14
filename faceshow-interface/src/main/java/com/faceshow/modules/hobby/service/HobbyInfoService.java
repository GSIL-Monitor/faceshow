package com.faceshow.modules.hobby.service;

import com.faceshow.common.utils.R;
import com.faceshow.modules.hobby.entity.HobbyInfo;

import java.util.List;

/**
 * @Athor GuoChao
 * @Date Create in 14:53 2018/1/20
 */
public interface HobbyInfoService {

    int deleteByPrimaryKey(Integer hobbyId);

    int insert(HobbyInfo record);

    int insertSelective(HobbyInfo record);

    HobbyInfo selectByPrimaryKey(Integer hobbyId);

    int updateByPrimaryKeySelective(HobbyInfo record);

    int updateByPrimaryKey(HobbyInfo record);

    /**
     * 查询所有系统定义标签
     *
     * @return
     */
    List<HobbyInfo> findSysHobbyAll(Integer userId);

    /**
     * 查询当前用户的所有标签
     *
     * @param userId 用户id
     * @return
     */
    List<HobbyInfo> findHobbyByUserId(Integer userId);

    /**
     * 保存新添加的兴趣爱好标签
     * @param hobby 标签名
     * @param type 标签类型, 0 系统添加, 1 用户自定义
     * @param userId 用户id
     * @return
     */
    Object addHobby(String hobby, Byte type, Integer userId);

    /**
     * 删除标签名
     *
     * @param hobbyIds 标签id
     * @param userId   用户id, 如果是系统管理员删除标签此参数不用填写
     * @return
     */
    Object deleteTags(String hobbyIds, Integer userId);

    /**
     * 修改用户标签
     * @param hobbyIds 标签id
     * @param userId 用户id
     * @return
     */
    Object updateTagsByUserId(String hobbyIds, Integer userId);

    /**
     * 查询用户拥有的标签
     * @param userId 用户id
     * @return
     */
    List<String> findHobbyTagsByUserId(Integer userId);
}
