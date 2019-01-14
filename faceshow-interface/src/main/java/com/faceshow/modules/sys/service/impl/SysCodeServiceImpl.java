package com.faceshow.modules.sys.service.impl;

import com.faceshow.common.utils.StringTool;
import com.faceshow.modules.sys.dao.SysCodeDao;
import com.faceshow.modules.sys.entity.SysCode;
import com.faceshow.modules.sys.service.SysCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Athor GuoChao
 * @Date Create in 17:11 2018/1/15
 */
@Service
public class SysCodeServiceImpl implements SysCodeService{

    @Autowired
    private SysCodeDao sysCodeDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysCodeDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysCode record) {
        return sysCodeDao.insert(record);
    }

    @Override
    public int insertSelective(SysCode record) {
        // 判断是否已经发送验证码
        String code = sysCodeDao.findCodeByMobile(record.getMobile());
        if(StringTool.isEmpty(code)){
            //验证码为空, 表示没有发送过验证码
            return sysCodeDao.insertSelective(record);
        }
        // 修改验证码
        return sysCodeDao.updateByMobile(record);
    }

    @Override
    public SysCode selectByPrimaryKey(Integer id) {
        return sysCodeDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysCode record) {
        return sysCodeDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysCode record) {
        return sysCodeDao.updateByPrimaryKey(record);
    }

    /**
     * 根据电话号码获取验证码
     *
     * @param mobile 电话号码
     * @return 验证码
     */
    @Override
    public String findCodeByMobile(String mobile) {
        return sysCodeDao.findCodeByMobile(mobile);
    }

    /**
     * 根据电话号码修改验证码状态
     *
     * @param mobile 电话号码
     * @param state  状态码
     */
    @Override
    public int updateStateByMobil(String mobile, Byte state) {
        SysCode sysCode = new SysCode();
        sysCode.setState(state);
        sysCode.setMobile(mobile);
        return sysCodeDao.updateByMobile(sysCode);
    }

    /**
     * 根据电话号码修改验证码
     *
     * @param mobile 电话号码
     * @param code   验证码
     * @return
     */
    @Override
    public int updateCodeByMobile(String mobile, String code) {
        SysCode sysCode = new SysCode();
        sysCode.setCode(code);
        sysCode.setMobile(mobile);
        sysCode.setCreateTime(new Date());
        return sysCodeDao.updateByMobile(sysCode);
    }
}
