package com.faceshow.modules.music.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.FastDFSClient;
import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.Query;
import com.faceshow.common.utils.R;
import com.faceshow.modules.music.entity.MusicInfo;
import com.faceshow.modules.music.service.MusicInfoService;
import com.faceshow.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 用户音乐操作功能<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.user.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/1/25 18:07
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/music")
public class MusicInfoController extends AbstractController{

    @Autowired
    private MusicInfoService musicInfoService;

    /**
     * 进入音乐详情页
     *
     * @param musicId  音乐id
     * @return
     */
    @SysLog
    @PostMapping("/intoMusic")
    public Object intoMusic(Integer musicId) {
        return musicInfoService.intoMusic(musicId);
    }

    /**
     * 查询音乐列表
     *
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @return
     */
    @SysLog
    @PostMapping("/findMusicAll")
    public Object findMusicAll(@RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        return musicInfoService.findMusicAll(currPage, pageSize);
    }

    /**
     * 根据音乐类型查询音乐列表
     * <p>
     * typeId 音乐类型ID
     * search 查询条件
     *
     * @param map
     * @return
     */
    @SysLog
    @PostMapping("/findByType")
    public Object findMusicByType(@RequestParam Map<String, Object> map) {
        return musicInfoService.findMusicByType(map);
    }

    /**
     * 上传音乐到服务器
     *
     * @param userId 上传人id
     * @param file   上传的音乐文件
     * @return
     */
    @SysLog
    @PostMapping("/uploadMusic")
    public Object uploadMusic(Integer userId, MultipartFile file) {
        if (file == null) {
            return R.result(0, "没有要上传的文件", "");
        }
        String musicName = file.getOriginalFilename();//音乐名称
        MusicInfo musicInfo = new MusicInfo();
        musicInfo.setMname(musicName);
        musicInfo.setUserId(getUserInfoId());
        try {
            // 上传音乐到服务器
            String url = FastDFSClient.uploadFile(file.getBytes(), musicName.substring(musicName.lastIndexOf(".") + 1), null);
            musicInfo.setUrl(url);
            musicInfo.setCreateTime(new Date());
            int i = musicInfoService.insertSelective(musicInfo);
            if (i > 0) {
                // 上传成功
                return R.result(1, "上传成功", musicInfoService.selectByPrimaryKey(musicInfo.getMusicId()));
            }
            return R.result(0, "上传失败", "");
        } catch (Exception e) {
            e.printStackTrace();
            return R.result(0, "上传失败", "");
        }
    }
    /**
     *@Author:YS
     *@Description:直播的时候的背景音乐
     *@Date:2018/3/13
     *@param:No such property: code for class: Script1
     */
    @SysLog
    @PostMapping("/backGroundMusic")
    public Object backGroundMusic(@RequestParam Map<String, Object> map) {
        Query query = new Query(map);
        List<Map<String, Object>> infoDetail = musicInfoService.backGroundMusic(query);
        int total = musicInfoService.backGroundMusicTotal();
        PageUtils pageUtil = new PageUtils(infoDetail, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

}
