package com.faceshow.common.utils;

import java.io.Serializable;

/**
 * 修改数量工具类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/23 16:31
 * -------------------------------------------------------------- <br>
 */
public class NumUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作信息id
     */
    private Integer id;

    /**
     * 操作数量
     */
    private Integer num;

    public NumUtils() {
    }

    public NumUtils(Integer id, Integer num) {
        this.id = id;
        this.num = num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "NumUtils{" +
                "id=" + id +
                ", num=" + num +
                '}';
    }
}
