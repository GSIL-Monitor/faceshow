package com.faceshow.modules.sys.service;

import com.faceshow.modules.sys.entity.SysCode;

/**
 * @Athor GuoChao
 * @Date Create in 17:10 2018/1/15
 */
public interface SysCodeService {
    int deleteByPrimaryKey(Integer id);

    int insert(SysCode record);

    int insertSelective(SysCode record);

    SysCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysCode record);

    int updateByPrimaryKey(SysCode record);

    /**
     * 根据电话号码获取验证码
     *
     * @param mobile 电话号码
     * @return 验证码
     */
    String findCodeByMobile(String mobile);

    /**
     * 根据电话号码修改验证码状态
     * @param mobile 电话号码
     * @param state 状态码
     * @return
     */
    int updateStateByMobil(String mobile, Byte state);

    /**
     * 根据电话号码修改验证码
     * @param mobile 电话号码
     * @param code 验证码
     * @return
     */
    int updateCodeByMobile(String mobile, String code);
}
