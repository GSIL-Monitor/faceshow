package com.faceshow.modules.music.service;

import java.util.Map;

/**
 * 音乐类型操作Service接口
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/5 11:12
 * -------------------------------------------------------------- <br>
 */
public interface MusicTypeService {

    /**
     * 列表查询音乐类型
     *
     * @param map 查询条件
     * @return
     */
    Object list(Map<String, Object> map);
}
