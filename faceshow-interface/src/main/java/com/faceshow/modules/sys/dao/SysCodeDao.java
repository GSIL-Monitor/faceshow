package com.faceshow.modules.sys.dao;

import com.faceshow.modules.sys.entity.SysCode;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码暂存
 */
@Mapper
public interface SysCodeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysCode record);

    int insertSelective(SysCode record);

    SysCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysCode record);

    int updateByPrimaryKey(SysCode record);

    /**
     * 根据电话号码查询验证码
     * @param mobile 电话号码
     * @return 验证码
     */
    String findCodeByMobile(String mobile);

    /**
     * 根据电话号码修改信息
     *
     * @param sysCode
     * @return
     */
    int updateByMobile(SysCode sysCode);
}