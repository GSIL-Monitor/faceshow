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
public class RCopy extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public RCopy() {
        put("code", 0);
    }

    public static RCopy error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static RCopy error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static RCopy error(int code, String msg) {
        RCopy r = new RCopy();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static RCopy ok(String msg) {
        RCopy r = new RCopy();
        r.put("msg", msg);
        return r;
    }

    public static RCopy ok(Map<String, Object> map) {
        RCopy r = new RCopy();
        r.putAll(map);
        return r;
    }

    public static RCopy ok() {
        return new RCopy();
    }

    public RCopy put(String key, Object value) {
        super.put(key, value);
        return this;
    }


    /**
     * @Ahthor: Gaosx
     * @Date: 2017-10-21 15:15
     * @Explanation: api端口返回的三个对象，
     */
    public static Object ajaxResult(Integer code, String msg, Object obj) {
        AjaxResult ajaxResult = new AjaxResult();
//		GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
//		Gson gson = gsonbuilder.create();
        Gson gson = new Gson();
        ajaxResult.setCode(code);
        ajaxResult.setMsg(msg);
        ajaxResult.setResult(obj);
        return gson.toJson(ajaxResult);
    }


    /**
     * @param code
     * @param msg
     * @param
     * @return
     */
    public static Object ajaxResult2(Integer code, String msg) {
        AjaxResult ajaxResult = new AjaxResult();
        Gson gson = new Gson();
        ajaxResult.setCode(code);
        ajaxResult.setMsg(msg);
        return gson.toJson(ajaxResult);
    }


}
