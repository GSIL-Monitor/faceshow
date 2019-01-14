package com.faceshow.common.MQ.message.likenum;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.NumUtils;
import com.faceshow.modules.sys.entity.SysFastdfs;

import java.util.Date;

/**
 * 朋友圈点赞数量修改操作生产者
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/23 16:11
 * -------------------------------------------------------------- <br>
 */
public class FastDFSProduct {

    /**
     * 视朋友圈点赞数量加一或者减一操作生产者
     *
     * @param url 文件路径
     */
    public static void saveFileUrl(String url) {
        String mssage = JSON.toJSONString(new SysFastdfs(url, new Date()));
        ProductMQ.sendMessage(MqToppic.top_sysFastDfs, mssage);
    }
}
