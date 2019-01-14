package com.faceshow.modules.user.dao;

import com.faceshow.modules.user.entity.UserGiveAwayLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserGiveAwayLogDao {
    int deleteByPrimaryKey(Integer giveId);

    int insertSelective(Map<String, Object> map);

    UserGiveAwayLog selectByPrimaryKey(Integer giveId);

    int updateByPrimaryKeySelective(UserGiveAwayLog record);

    /**
     * 修改用户收支记录表数据
     *
     * @param map 修改条件
     * @return
     */
    int updateByUserIdAndTypeAndItemId(Map<String, Object> map);

    /**
     * 修改用户收支记录表(仅问答第三种情况设置前三名使用使用)
     *
     * @param map
     * @return
     */
    int updateByUserIdAndTypeAndItemIdAndRemark(Map<String, Object> map);

    /**
     * 保存多条数据
     *
     * @param map
     * @return
     */
    int saveBatch(Map<String, Object> map);

    /**
     * 添加充值日志
     * @param map
     * @return
     */
    int insertPaySelective(Map<String, Object> map);

}