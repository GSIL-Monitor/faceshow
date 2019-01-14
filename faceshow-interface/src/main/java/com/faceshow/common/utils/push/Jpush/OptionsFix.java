package com.faceshow.common.utils.push.Jpush;
import cn.jpush.api.push.model.Options;


public class OptionsFix  {

    public static Options.Builder newBuilder() {
        return new Options.Builder();
    }
    public static Options TimeToLive() {
        return newBuilder().setTimeToLive(259200L).build();
    }
}
