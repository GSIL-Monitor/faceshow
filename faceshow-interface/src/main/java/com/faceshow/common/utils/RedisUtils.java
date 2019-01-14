package com.faceshow.common.utils;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author Gaosx
 * @email Gaoshanxi@gmail.com
 * @date 2017-07-17 21:12
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    /**
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;
    private final static Gson gson = new Gson();

    public void set(String key, Object value, long expire) {
        valueOperations.set(key, toJson(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * @param key
     * @param value
     * @description 简单的往数组里面添加元素
     */
    public void listSet(String key, Object value, long expire) {
        listOperations.leftPush(key, value);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * @param key
     * @param offset
     * @description 获取指定下标的数组元素
     */
    public String listGet(String key, int offset) {
        if (listOperations.index(key, offset) == null) {//如果没有返回NULL
            return "";
        }
        String index = listOperations.index(key, offset).toString();
        return index;
    }

    /**
     * @param key
     * @param start
     * @param end
     * @description 查询区间范围内的元素
     */
    public List lrange(String key, int start, int end) {
        List<Object> range = listOperations.range(key, start, end);
        return range;
    }

    /**
     * @param key
     * @param count 负数：从右往左     整数：从左往右
     * @param value 移除的值
     * @description 移除数组匹配到的数据元素
     */

    public void remove(String key, long count, String value) {
        int backValue = listOperations.remove(key, count, value).intValue();
        System.out.println(key + " 数组长度为 : " + backValue);
    }

    /**
     * @param key
     * @description 从左到右，删除第一个元素
     */
    public void listPop(String key) {
        listOperations.leftPop(key);
    }


    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 获取所有的key
     *
     * @param key
     * @return
     */
    public Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }

    /**
     * 批量删除key
     *
     * @param keys
     */
    public void delete(Set<String> keys) {
        if (keys != null && keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * Object转成JSON数据
     */
    public String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return gson.toJson(object);
    }

    /**
     * JSON数据，转成Object
     */
    public <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
