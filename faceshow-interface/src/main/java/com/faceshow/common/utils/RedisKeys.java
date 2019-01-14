package com.faceshow.common.utils;

/**
 * Redis所有Keys
 *
 * @author Gaosx
 * @email Gaoshanxi@gmail.com
 * @date 2017-07-18 19:51
 */
public class RedisKeys {

    public static String getSysConfigKey(String key) {
        return "sys:config:" + key;
    }

    /**
     * 用户详情Key
     */
    public static final String USER_INFO = "user:user_info_";
    /**
     * 用户关注key userId当前用户id, accountId被关注人
     * user:user_attention_userId_accountId
     */
    public static final String USER_ATTENTION = "user:user_attention_";

    /**
     * 用户邮箱key
     */
    public static final String USER_INFO_EMAIL = "user:user_info_email_";

    /**
     * 用户邮箱激活验证key
     */
    public static final String VERIFY_EMAIL = "verify:email_";

    /**
     * 用户手机短信验证码验证key
     */
    public static final String VERIFY_MOBILE = "verify:mobile_";

    /**
     * 用户电话号码key
     */
    public static final String USER_INFO_MOBILE = "user:user_info_mobile_";

    /**
     * 用户名key
     */
    public static final String USER_INFO_USERNAME = "user:user_info_username_";

    /**
     * 朋友圈详情key
     */
    public static final String FRIEND_RING = "friend:friend_ring_";

    /**
     * 朋友圈点赞key
     */
    public static final String FRIEND_RING_LIKE = "friend:friend_ring_like_";

    /**
     * 朋友圈评论点赞key
     */
    public static final String FRIEND_RING_COMMENT_LIKE = "friend:friend_ring_comment_like_";

    /**
     * 问答点赞key
     */
    public static final String QA_LIKE = "qa:qa_like_";

    /**
     * 问答吐槽key
     */
    public static final String QA_MAKE = "qa:qa_make_";

    /**
     * 问答评论点赞key
     */
    public static final String QA_INFO_COMMENT_LIKE = "qa:qa_info_comment_like_";

    /**
     * 问答回复点赞key
     */
    public static final String QA_REPLY_LIKE = "qa:qa_reply_like_";

    /**
     * 问答回复吐槽key
     */
    public static final String QA_REPLY_MAKE = "qa:qa_reply_make_";

    /**
     * 问答回复评论点赞key
     */
    public static final String QA_REPLY_COMMENT_LIKE = "qa:qa_reply_comment_like_";

    /**
     * 发布问答金额列表key
     */
    public static final String QA_REWARD_MONEY = "qa:qa_reward_money_list";

    /**
     * 问答推荐问题列表key
     */
    public static final String QA_INFO_RECOMMEND = "qa:qa_info_recommend_list";

    /**
     * 问答天数设置列表key
     */
    public static final String QA_SET_DAYS = "qa:qa_set_days_list";

    /**
     * 用户token验证key
     */
    public static final String TB_TOKEN = "token:tb_token_";

    /**
     * 短视频评论点赞key
     */
    public static final String VIDEO_COMMENT_LIKE = "video:video_comment_like_";

    /**
     * 短视频点赞key
     */
    public static final String VIDEO_LIKE = "video:video_like_";

    /**
     * 站内信类型表
     */
    public static final String Message = "Message:message_push_summary_";

    /**
     * 直播分类表
     */
    public static final String LIVE_TYPE = "live_type";
    /**
     * F转换钻石模板表
     */
    public static final String F_TRANSFORMATION_DIAMOND = "f_transformation_diamond";
}
