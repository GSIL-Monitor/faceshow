package com.faceshow.common.utils;


/**
 * @Anthor: Gaosx
 * @Date: Created by lenovo on 2017-10-21 16:50
 * @Explanation: JSON的处理方法
 */
public class AjaxResult {

    //错误状态编码，0失败，1成功。
    private int code = -1;
    //结果对象
    private Object result;
    //结果对象
 //   private Object results;//多个对象时使用
    //信息内容
    private String msg;
    //JSOn
//    private Object jsons;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setResult(Object result) {
        this.result = result;
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//    public Object getJsons() {
//        return jsons;
//    }
//
//    public void setJsons(Object jsons) {
//        this.jsons = jsons;
//    }
//
//    public Object getResults() {
//        return results;
//    }
//
//    public void setResults(Object results) {
//        this.results = results;
//    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "code=" + code +
                ", result=" + result +
 //               ", results=" + results +
                ", msg='" + msg + '\'' +
//                ", jsons=" + jsons +
                '}';
    }
}

