package com.faceshow.modules.Broadcast;
/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 事件消息通知 腾讯的回调方法
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.Broadcast<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/3/10 15:41 
 * -------------------------------------------------------------- <br>
 */
public class CallbackBean {
    private String app;

    private String appname;

    private String channel_id;

    private int event_type;

    private String sign;

    private String stream_id;

    private int t;

    private int event_time;

    private String sequence;

    private String node;

    private String user_ip;

    private int errcode;

    private String errmsg;

    private String stream_param;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public int getEvent_type() {
        return event_type;
    }

    public void setEvent_type(int event_type) {
        this.event_type = event_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStream_id() {
        return stream_id;
    }

    public void setStream_id(String stream_id) {
        this.stream_id = stream_id;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getEvent_time() {
        return event_time;
    }

    public void setEvent_time(int event_time) {
        this.event_time = event_time;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getStream_param() {
        return stream_param;
    }

    public void setStream_param(String stream_param) {
        this.stream_param = stream_param;
    }

    @Override
    public String toString() {
        return "CallbackBean{" +
                "app='" + app + '\'' +
                ", appname='" + appname + '\'' +
                ", channel_id='" + channel_id + '\'' +
                ", event_type=" + event_type +
                ", sign='" + sign + '\'' +
                ", stream_id='" + stream_id + '\'' +
                ", t=" + t +
                ", event_time=" + event_time +
                ", sequence='" + sequence + '\'' +
                ", node='" + node + '\'' +
                ", user_ip='" + user_ip + '\'' +
                ", errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                ", stream_param='" + stream_param + '\'' +
                '}';
    }
}
