package com.faceshow.common.utils;

import com.google.gson.Gson;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Gaosx
 * @email Gaoshanxi@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 1);
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }


    /**
     * @Ahthor: Gaosx
     * @Date: 2017-10-21 15:15
     * @Explanation: api端口返回的三个对象，
     */
    public static Object result(Integer code, String msg, Object obj) {
        AjaxResult ajaxResult = new AjaxResult();
        Gson gson = new Gson();
        ajaxResult.setCode(code);
        ajaxResult.setMsg(msg);
        ajaxResult.setResult(obj);
;        return gson.toJson(ajaxResult);
    }




}
