package com.faceshow.modules.user.controller;

import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.RedisKeys;
import com.faceshow.common.utils.RedisUtils;
import com.faceshow.modules.IM.Imutils;
import com.faceshow.modules.live.service.LiveInfoServicve;
import com.faceshow.modules.music.service.MusicInfoService;
import com.faceshow.modules.qa.service.QaInfoService;
import com.faceshow.modules.qa.service.QaReplyService;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.sys.service.SysCountryService;
import com.faceshow.modules.user.entity.UserInfo;
import com.faceshow.modules.user.service.UserInfoService;
import com.faceshow.modules.video.service.VideoInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 用于页面转跳controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.user.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/10 13:28
 * -------------------------------------------------------------- <br>
 */
@Controller
@RequestMapping("/api/page")
public class PageController extends AbstractController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private VideoInfoService videoInfoService;

    @Autowired
    private QaInfoService qaInfoService;

    @Autowired
    private LiveInfoServicve liveInfoServicve;

    @Autowired
    private SysCountryService sysCountryService;

    @Autowired
    private QaReplyService qaReplyService;

    @Autowired
    private MusicInfoService musicInfoService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 密码盐
     */
    @Value("${passwordSalt}")
    private String passwordSalt;


    /**
     * 返回重置密码页面
     *
     * @param model 封装数据
     * @param token 返回的token
     * @param email 返回的email
     * @return
     */
    @GetMapping("/emailPage")
    public String emailPage(Model model, String token, String email) {
        model.addAttribute("email", email);
        model.addAttribute("token", token);
        return "pass_reset";
    }

    /**
     * 返回邮箱修改绑定邮箱的页面
     *
     * @param email 接收邮箱
     * @return
     * @author:liyan
     */
    @GetMapping("/changeEmail")
    public String changeEmail(Model model, String email, String token) {
        String userId = redisUtils.get(RedisKeys.VERIFY_EMAIL + email + "_" + token);
        model.addAttribute("email", email);
        if (StringUtils.isBlank(userId)) {
            model.addAttribute("msg", "邮箱地址绑定失败");
        } else {
            Integer result = userInfoService.updateEmailByUserId(Integer.parseInt(userId), email);
            if (result > 0) {
                model.addAttribute("msg", "邮箱地址已通过验证");
            }
            model.addAttribute("msg", "邮箱地址绑定失败");
        }
        return "email_success";
    }

    /**
     * 返回邮箱激活成功或失败页面
     *
     * @param model
     * @param email 邮箱
     * @param token token令牌
     * @return
     */
    @GetMapping("/activateEmail")
    public String activateEmail(Model model, String email, String token) {
        String redisToken = redisUtils.get(RedisKeys.VERIFY_EMAIL + email);
        model.addAttribute("email", email);
        if (StringUtils.isNotBlank(redisToken) && redisToken.equals(token)) {
            // 激活成功, 删除redis信息
            redisUtils.delete(email);
            // 设置时间为15分钟, 表示15分钟内要完成注册
            redisUtils.set(RedisKeys.USER_INFO_EMAIL + email, "1", 60 * 15);
            model.addAttribute("msg", "邮箱激活成功, 请于15分钟内完成注册");
            return "email_activate";
        }
        model.addAttribute("msg", "链接已失效, 请重新发送链接");
        return "email_activate";
    }

    /**
     * 转跳到app下载页面
     *
     * @return
     */
    @GetMapping("/download")
    public String goDownload() {
        return "download_app";
    }

    /**
     * 转调到分享连接邀请好友页面
     *
     * @param tb
     * @return
     */
    @GetMapping("/attention")
    public String attention(Model model, String tb) {
        String s = null;
        try {
            tb = tb.substring(6, tb.length() - 6);
            Integer userId = Integer.parseInt(tb);
            userId = userId / 389;
            s = redisUtils.get(RedisKeys.USER_INFO + userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (StringUtils.isBlank(s)) {
            return "error";
        }

        UserInfo userInfo = redisUtils.fromJson(s, UserInfo.class);

        model.addAttribute("nickName", userInfo.getNickName());
        model.addAttribute("signature", userInfo.getSignature());
        model.addAttribute("attentionNum", userInfo.getAttentionNum());
        model.addAttribute("fensiNum", userInfo.getFensiNum());
        model.addAttribute("img", userInfo.getImg());

        return "follow";
    }

    /**
     * 返回邮箱重置密码结果页面
     *
     * @param model
     * @param email 邮箱
     * @param msg   信息
     * @return
     */
    @GetMapping("/result")
    public String result(Model model, String email, String msg) {
        model.addAttribute("email", email);
        model.addAttribute("msg", msg);
        return "pass_success";
    }

    /**
     * 转调到音乐页面分享页面
     *
     * @param model
     * @param url   音乐路径
     * @return
     */
    @GetMapping("/music")
    public String music(Model model, String url) {
        if (StringUtils.isBlank(url)) {
            return "error";
        }
        // 根据音乐url查询音乐名称, 音乐封面
        Map<String, Object> map = musicInfoService.findMusicByUrl(url);
        model.addAttribute("music", map);
        return "music";
    }

    /**
     * 返回魅力值介绍页面
     *
     * @return
     */
    @GetMapping("/glamour")
    public String glamour() {
        return "glamour";
    }

    /**
     * 返回赚取钻石页面
     *
     * @return
     */
    @GetMapping("/earnDiamonds")
    public String earnDiamonds() {
        return "earn_diamonds";
    }

    /**
     * 转调到推荐视频页面
     *
     * @param model
     * @param videoUrl 视频url
     * @param coding   国家唯一编码
     * @param type     分享类型, 1短视频, 2问答, 3直播
     * @return
     */
    @GetMapping("/recommend")
    public String recommendedVideo(Model model, String videoUrl, String type, String coding) {
        if (StringUtils.isBlank(videoUrl) || StringUtils.isBlank(type) || StringUtils.isBlank(coding)) {
            return "error";
        }

        boolean flag = coding.equalsIgnoreCase("cn");

        // 查询分享视频
        if (type.equals("1")) {

            List<Object> videoList = video(coding);

            // 设置参数
            model.addAttribute("list", videoList);

            // 判断该视频是否存在
            List<Object> video = videoInfoService.findVideoImgByVideoUrl(videoUrl);

            if (flag) {
                model.addAttribute("msg", "更多精彩视频");
                model.addAttribute("msg2", "该视频已删除,其他视频也同样精彩哦~");
            } else {
                model.addAttribute("msg", "More Exciting Videos");
                model.addAttribute("msg2", "The video has been removed and other videos are just as wonderful~");
            }

            // 不存在返回视频已删除页面
            if (video == null || video.size() < 1) {
                // 该视频已经被删除
                return "delete_share_video";
            }

            Map<String, Object> map = (Map<String, Object>) video.get(0);
            // 视频分享数量+1
            videoInfoService.addShareNumByVideoId(Integer.parseInt(map.get("videoId").toString()));

            // 设置参数
            model.addAttribute("videoUrl", videoUrl);
            model.addAttribute("video", video.get(0));
            // 存在返回视频播放页面
            return "share_video";
        }

        // 分享问答
        if (type.equals("2")) {
            List<Object> videoList = qa(coding);
            // 设置参数
            model.addAttribute("list", videoList);

            // 判断该视频是否存在
            List<Object> video = qaInfoService.findQaInfoByVideoUrl(videoUrl);

            if (flag) {
                model.addAttribute("msg", "更多精彩视频");
                model.addAttribute("msg2", "该视频已删除,其他视频也同样精彩哦~");
            } else {
                model.addAttribute("msg", "More Exciting Videos");
                model.addAttribute("msg2", "The video has been removed and other videos are just as wonderful~");
            }

            // 不存在返回视频已删除页面
            if (video == null || video.size() < 1) {
                // 该视频已经被删除
                return "delete_share_qa";
            }
            // 设置参数
            model.addAttribute("videoUrl", videoUrl);
            model.addAttribute("video", video.get(0));
            // 存在返回视频播放页面
            return "share_qa";
        }

        // 分享直播
        if (type.equals("3")) {
            List<Object> liveList = live(coding);
            // 设置参数
            model.addAttribute("list", liveList);

            // 判断该直播是否已结束
            List<Map<String, Object>> live = liveInfoServicve.findLiveByLiveUrl(videoUrl);

            if (flag) {
                model.addAttribute("msg", "更多精彩直播");
                model.addAttribute("msg2", "关注");
                model.addAttribute("msg3", "该直播已结束,其他直播也同样精彩哦~");
            } else {
                model.addAttribute("msg", "More exciting Live");
                model.addAttribute("msg2", "Concern");
                model.addAttribute("msg3", "The live broadcast is over and the rest of the live broadcast is just as wonderful~");

            }

            if (live == null || live.size() < 1) {
                return "delete_share_live";
            }

            // 设置参数
            Map<String, Object> map = live.get(0);
            map.put("sdkAppID", Imutils.SDKAPPID);
            map.put("accountType", Imutils.ACCOUNTTYPE);
            model.addAttribute("live1", JsonUtils.objectToJson(map));
            model.addAttribute("live", map);
            // 返回直播页面
            return "share_live_video";
        }

        // 分享问答回复
        if (type.equals("4")) {
            List<Object> qaReplyList = qaReply(coding);
            // 设置参数
            model.addAttribute("list", qaReplyList);

            // 判断该视频是否存在
            List<Object> video = qaReplyService.findQaReplyByVideoUrl(videoUrl);

            if (flag) {
                model.addAttribute("msg", "更多精彩视频");
                model.addAttribute("msg2", "该视频已删除,其他视频也同样精彩哦~");
            } else {
                model.addAttribute("msg", "More Exciting Videos");
                model.addAttribute("msg2", "The video has been removed and other videos are just as wonderful~");
            }

            // 不存在返回视频已删除页面
            if (video == null || video.size() < 1) {
                // 该视频已经被删除
                return "delete_share_qa";
            }
            // 设置参数
            model.addAttribute("videoUrl", videoUrl);
            model.addAttribute("video", video.get(0));
            // 存在返回视频播放页面
            return "share_qa";
        }

        return "error";
    }

    /**
     * 查询推荐视频
     *
     * @param country 当前国家
     * @return
     */
    private List<Object> video(String country) {
        // 查询推荐视频, 本国热门七个, 国际热门3个
        // 查询国内7个
        List<Object> videoList = videoInfoService.findDomesticHotVideo(0, 7, country, 0);
        // 查询国外3个
        List<Object> videoList2 = videoInfoService.findDomesticHotVideo(0, 10 - videoList.size(), country, 1);
        videoList.addAll(videoList2);
        if (videoList.size() < 10) {
            videoList.addAll(videoInfoService.findDomesticHotVideo(0, 10 - videoList.size(), country, 0));
        }
        return videoList;
    }

    /**
     * 查询推荐问答
     *
     * @param country 当前国家
     * @return
     */
    private List<Object> qa(String country) {
        // 查询推荐问答, 本国热门七个, 国际热门3个
        // 查询国内7个
        List<Object> qaList = qaInfoService.findDomesticHotQaInfo(0, 7, country, 0);
        // 查询国外3个
        List<Object> qaList2 = qaInfoService.findDomesticHotQaInfo(0, 10 - qaList.size(), country, 1);
        qaList.addAll(qaList2);
        if (qaList.size() < 10) {
            qaList.addAll(qaInfoService.findDomesticHotQaInfo(0, 10 - qaList.size(), country, 0));
        }
        return qaList;
    }

    /**
     * 查询推荐直播
     *
     * @param country 当前国家
     * @return
     */
    private List<Object> live(String country) {
        Integer countryId = sysCountryService.findCountryIdByCoding(country);
        // 查询推荐直播, 本国七个, 国际3个
        // 查询国内七个
        List<Object> liveList = liveInfoServicve.findDomesticHotLive(0, 7, countryId, 0);
        // 查询国外三个
        List<Object> liveList2 = liveInfoServicve.findDomesticHotLive(0, 10 - liveList.size(), countryId, 1);
        liveList.addAll(liveList2);
        if (liveList.size() < 10) {
            liveList.addAll(liveInfoServicve.findDomesticHotLive(0, 10 - liveList.size(), countryId, 0));
        }
        return liveList;
    }

    /**
     * 查询推荐问答回复
     *
     * @param country 当前国家
     * @return
     */
    private List<Object> qaReply(String country) {
        // 查询推荐问答回复, 本国七个, 国际3个
        // 查询国内七个
        List<Object> qaReplyList = qaReplyService.findDomesticHotQaReply(0, 7, country, 0);
        // 查询国外三个
        List<Object> qaReplyList2 = qaReplyService.findDomesticHotQaReply(0, 10 - qaReplyList.size(), country, 1);
        qaReplyList.addAll(qaReplyList2);
        if (qaReplyList.size() < 10) {
            qaReplyList.addAll(qaReplyService.findDomesticHotQaReply(0, 10 - qaReplyList.size(), country, 0));
        }
        return qaReplyList;
    }
}
