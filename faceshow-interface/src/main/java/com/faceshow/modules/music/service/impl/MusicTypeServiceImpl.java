package com.faceshow.modules.music.service.impl;

import com.faceshow.common.utils.R;
import com.faceshow.modules.music.dao.MusicTypeDao;
import com.faceshow.modules.music.service.MusicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 音乐类型操作Service接口实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/5 11:12
 * -------------------------------------------------------------- <br>
 */
@Service
public class MusicTypeServiceImpl implements MusicTypeService {

    @Autowired
    private MusicTypeDao musicTypeDao;

    /**
     * 列表查询音乐类型
     *
     * @param map 查询条件
     * @return
     */
    @Override
    public Object list(Map<String, Object> map) {
        List<Object> list = musicTypeDao.queryList(map);
        if (list == null || list.size() < 1) {
            return R.result(0, "没有查询到数据", "");
        }
        return R.result(1, "查询成功", list);
    }
}
