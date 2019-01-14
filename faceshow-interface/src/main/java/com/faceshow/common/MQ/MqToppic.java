package com.faceshow.common.MQ;

/**
 * RocketMq 主题
 */
public enum MqToppic {
    /**
     * 主题 观看记录
     */
    top_videoPlayLog,
    /**
     * 主题 支付
     */
    top_pay,
    /**
     * 主题 针对直播一对一的打赏
     */
    top_reward,
    /**
     * 主题 视频评论数量加一或者减一
     */
    top_videoCommentNumOne,
    /**
     * 主题 修改视频评论数量为固定值
     */
    top_videoCommentNumMany,
    /**
     * 主题 视频点赞数量+1 或者-1
     */
    top_videoLikeNum,
    /**
     * 主题 视频评论点赞数量+1 或者-1
     */
    top_videoCommentLikeNum,
    /**
     * 主题 朋友圈点赞数量+1或者-1
     */
    top_friendLikeNum,
    /**
     * 主题 朋友圈评论点赞数量+1或者-1
     */
    top_friendCommentLikeNum,
    /**
     * 主题 问答点赞数量+1或者-1
     */
    top_qaLikeNum,
    /**
     * 主题 问答评论数量+1或者-1
     */
    top_qaCommentNumOne,
    /**
     * 主题 问答评论数量修改为固定值
     */
    top_qaCommentNumMany,
    /**
     * 主题 问答回复数量+1或者-1
     */
    top_qaReplyNum,
    /**
     * 主题 问答评论点赞数量+1或者-1
     */
    top_qaCommentLikeNum,
    /**
     * 主题 问答回复点赞数量+1或者-1
     */
    top_qaReplyLikeNum,
    /**
     * 主题 问答回复点踩数量+1或者-1
     */
    top_qaReplyMakeNum,
    /**
     * 主题 问答回复评论数量+1 或者-1
     */
    top_qaReplyCommentNumOne,
    /**
     * 主题 问答回复评论数量修改为固定值
     */
    top_qaReplyCommentNumMany,
    /**
     * 主题 问答回复评论点赞数量+1或者-1
     */
    top_qaReplyCommentLikeNum,
    /**
     * 主题 文件路径存储
     */
    top_sysFastDfs,
    /**
     * 主题 视频点赞推送
     */
    top_videoLikePush,
    /**
     * 主题 视频发布推送至粉丝
     */
    top_videoPushMany,
    /**
     * 主题 推送站内信保存到数据库
     */
    top_videoPushSave,
    /**
     * 主题 视频评论推送
     */
    top_videoCommentPush,
    /**
     * 主题 视频评论回复推送
     */
    top_videoCommentReplyPush,
    /**
     * 主题 视频@好友推送
     */
    top_videoAtPush,
    /**
     * 主题 朋友圈点赞推送
     */
    top_friendLikePush,
    /**
     * 主题 朋友圈@好友推送
     */
    top_friendAtPush,
    /**
     * 1V1匹配结束表
     */
    live_matching_finish,
    /**
     * 主题 朋友圈评论推送
     */
    top_friendCommentPush,
    /**
     * 主题 朋友圈评论回复推送
     */
    top_friendCommentReplyPush,
    /**
     * 主题 问答回复推送通知
     */
    top_qaReplyPush,
    /**
     * 主题 问答指定最佳答案推送
     */
    top_qaBestPush,
    /**
     * 主题 问答回复被评为第一名推送
     */
    top_qaFirstPush,
    /**
     * 添加好友
     */
    user_attention,
    /**
     * 主播开播
     */
    live_info,

    /**
     * F币提现成功
     */
    WITHDRAW_SUCCESS_TYPE,
    /**
     * F币提现失败
     */
    WITHDRAW_ERROR_TYPE,
    /**
     * 送礼物  //赠送类型 赠送类型 1 短视频 2 直播 3 一对一 4 聊天 5个人中心送礼物, 7PK
     * 分別一个消息队列 减少送礼物接口的访问压力
     */
    senGift,
    sendGiftOne,
    sendGiftTwo,
    sendGiftThree,
    sendGiftFour,
    sendGiftFive,
    sendGiftSeven,
    Eleven,
    /**
     * 邮件发送主题
     */
    top_emailSend,
    /**
     * 短信发送主题
     */
    top_smsSend;

}
