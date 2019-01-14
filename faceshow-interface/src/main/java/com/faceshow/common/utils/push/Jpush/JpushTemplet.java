package com.faceshow.common.utils.push.Jpush;

/**
 * 推送内容信息
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/25 8:37
 * -------------------------------------------------------------- <br>
 */
public class JpushTemplet {

    public static final String VIDEO_LIKE = "{0}赞了你的Facecast视频";

    public static final String VIDEO_MUSIC = "{0}使用你的原创声音创建了视频";

    public static final String VIDEO_GIFT = "{0}给你的视频赠送了{1}礼物";

    public static final String VIDEO_PUSH = "{0}发布了一条Facecast视频";

    public static final String VIDEO_AT = "{0}在发布的Facecast视频中@了你";

    public static final String FRIEND_AT = "{0}在发布的Facecast动态中@了你";

    public static final String FRIEND_LIKE = "{0}赞了你的Facecast动态";

    public static final String SEND_GIFT = "{0}给你赠送了{1}礼物";

    public static final String l2l_GIFT = "{0}给你赠送了{1}礼物，想和你聊天";

    public static final String ATTENTION = "{0}关注了你";

    public static final String COMMENT = "{0}评论：{1}";

    public static final String COMMENT_REPLY = "{0}回复了你的评论";

    public static final String QA_REPLY = "{0}回答了你的Facecast问答";

    public static final String QA_BEST = "{0}指定了你的回答为最佳答案";

    public static final String QA_FIRST = "你的Facecast回答被评为第一名";

    public static final String LIVE_START = "{0}开播了";

    public static final String SYSTEM_NOTICE = "系统通知";

    public static final String WITHDRAW_SUCCESS = "F币提现成功";

    public static final String WITHDRAW_ERROR = "F币提现失败";
    /**
     * 视频点赞
     */
    public static final Integer VIDEO_LIKE_TYPE = 1;
    /**
     * 使用原声创建视频
     */
    public static final Integer VIDEO_MUSIC_TYPE = 2;
    /**
     * 给视频赠送礼物
     */
    public static final Integer VIDEO_GIFT_TYPE = 3;
    /**
     * 发布了一条视频
     */
    public static final Integer VIDEO_CREATE_TYPE = 4;
    /**
     * 视频中@好友
     */
    public static final Integer VIDEO_AT_TYPE = 5;
    /**
     * 评论了你的视频
     */
    public static final Integer VIDEO_COMMENT_TYPE = 6;
    /**
     * 回复了你的视频评论
     */
    public static final Integer VIDEO_COMMENT_REPLY_TYPE = 7;
    /**
     * 朋友圈点赞
     */
    public static final Integer FRIEND_LIKE_TYPE = 8;
    /**
     * 朋友圈中@好友
     */
    public static final Integer FRIEND_AT_TYPE = 9;
    /**
     * 评论了朋友圈
     */
    public static final Integer FRIEND_COMMENT_TYPE = 10;
    /**
     * 回复了朋友圈评论
     */
    public static final Integer FRIEND_COMMENT_REPLY_TYPE = 11;
    /**
     * 给你赠送了礼物
     */
    public static final Integer GIFT_SENT_TYPE = 12;
    /**
     * 给你赠送了玫瑰花礼物，想和你聊天
     */
    public static final Integer GIFT_CHAT_TYPE = 13;
    /**
     * 新粉丝关注
     */
    public static final Integer ATTENTION_TYPE = 14;
    /**
     * 主播开播
     */
    public static final Integer ANCHOR_TYPE = 15;
    /**
     * 回答了你的Facecast问答
     */
    public static final Integer QA_REPLY_TYPE = 16;
    /**
     * 指定了你的回答为最佳答案
     */
    public static final Integer QA_BEST_TYPE = 17;
    /**
     * 你的Facecast回答被评为第一名
     */
    public static final Integer QA_FIRST_TYPE = 18;
    /**
     * 系统通知
     */
    public static final Integer SYSTEM_TYPE = 19;
    /**
     * F币提现成功
     */
    public static final Integer WITHDRAW_SUCCESS_TYPE = 20;
    /**
     * F币提现失败
     */
    public static final Integer WITHDRAW_ERROR_TYPE = 21;
}
