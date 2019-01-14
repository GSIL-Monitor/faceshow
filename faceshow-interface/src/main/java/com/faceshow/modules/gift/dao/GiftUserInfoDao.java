package com.faceshow.modules.gift.dao;

import com.faceshow.modules.gift.entity.GiftUserInfo;
import com.faceshow.modules.video.entity.VideoGiftSend;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface GiftUserInfoDao {
    int deleteByPrimaryKey(Integer infoId);

    int insertSelective(GiftUserInfo record);

    GiftUserInfo selectByPrimaryKey(Integer infoId);

    int updateByPrimaryKeySelective(GiftUserInfo record);

    /**
     * 查询当前用户是否拥有该礼物以及拥有数量
     *
     * @param search 查询条件
     * @return
     */
    Integer findGiftByUserIdAndGiftId(Map<String, Object> search);

    /**
     * 修改用户拥有礼物数量
     *
     * @param update
     * @return
     */
    int updateGiftNumByUserIdAndGiftId(Map<String, Object> update);

    /**
     * 删除用户拥有礼物
     *
     * @param delete
     * @return
     */
    int deleteGiftByUserIdAndGiftId(Map<String, Object> delete);
}