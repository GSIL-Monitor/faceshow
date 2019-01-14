package com.faceshow.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 视频操作工具类, 实现视频压缩转码, 获取视频缩略图功能
 */
@Component
public class VideoUtils {

    private static final Logger logger = LoggerFactory.getLogger(VideoUtils.class);

    @Autowired
    private FFmpegUtils fFmpegUtils;

    /**
     * MultipartFile 转换成File
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */
    private File multipartToFile(MultipartFile multfile) throws IOException {
        String name = multfile.getOriginalFilename();
        File file = new File(UUID.randomUUID().toString().replace("-", "") + name.substring(name.lastIndexOf(".")));
        file = new File(file.getAbsolutePath());
        multfile.transferTo(file);
        return file;
    }

    /**
     * 获取视频第一帧图片
     *
     * @param multfile 图片
     * @return
     */
    public Map<String, String> videoImgUpload(MultipartFile multfile, boolean flag) {
        File source = null;
        File img = null;
        Map<String, String> map = new HashMap<>(0);
        try {
            // 转file
            long start = System.currentTimeMillis();
            source = multipartToFile(multfile);
            logger.info("multfile转file时间 : " + (System.currentTimeMillis() - start));

            // 使用ffmpeg
            start = System.currentTimeMillis();

            img = fFmpegUtils.processImg(source);
            logger.info("获取图片缩略图时间 : " + (System.currentTimeMillis() - start));

            start = System.currentTimeMillis();
            String imgUrl = FastDFSClient.uploadFile(Files.readAllBytes(img.toPath()), "jpg", null);
            map.put("imgUrl", imgUrl);
            // 发布到mq
            logger.info("图片上传到FastDFS时间 : " + (System.currentTimeMillis() - start));
            if (flag) {
                start = System.currentTimeMillis();
                String videoUrl = FastDFSClient.uploadFile(source.getAbsolutePath(), "mp4");
                map.put("videoUrl", videoUrl);
                logger.info("视频上传到FastDFS时间 : " + (System.currentTimeMillis() - start));
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 删除视频图片文件
            deleteFile(img);
            deleteFile(source);
        }
        return map;

    }

    /**
     * 上传视频,
     *
     * @param multfile  视频文件
     * @param flag      true表示用户没有上传缩略图, 生成缩略图, false表示用户有上传缩略图
     * @param musicFlag true表示需要把视频音乐从视频红剥离出来, false表示不需要
     * @return 返回视频和图片路径
     */
    public Map<String, String> videoUpload(MultipartFile multfile, boolean flag, boolean musicFlag) throws Exception {

        File source = null;
        File video = null;
        File img = null;
        File music = null;

        Map<String, String> urlMap = new HashMap<>(0);

        try {
            // 转file
            long start = System.currentTimeMillis();
            source = multipartToFile(multfile);
            logger.info("multfile转file时间 : " + (System.currentTimeMillis() - start));

            // 使用ffmpeg
            start = System.currentTimeMillis();

            logger.info("视频类型为: " + source.getName().substring(source.getName().lastIndexOf(".")));
            logger.info("视频文件大小为: " + source.length() + "k");

            if (source.getName().endsWith("mp4") && source.length() < 1024 * 1024 * 10) {
                // 如果视频源文件是MP4结尾, 并且小于10M, 不进行压缩
                video = source;
            } else {
                // 否则使用ffmpeg进行处理
                video = fFmpegUtils.process(source);
            }

            logger.info("压缩转码视频时间 : " + (System.currentTimeMillis() - start));
            // 上传视频
            start = System.currentTimeMillis();
            String videoUrl = FastDFSClient.uploadFile(Files.readAllBytes(video.toPath()), "mp4", null);
            logger.info("视频上传到FastDFS时间 : " + (System.currentTimeMillis() - start));
            urlMap.put("videoUrl", videoUrl);

            // 上传图片, 用户没有上传缩略图, 使用ffmpeg生成视频缩略图
            if (flag) {
                start = System.currentTimeMillis();
                img = fFmpegUtils.processImg(source);
                logger.info("获取图片缩略图时间 : " + (System.currentTimeMillis() - start));

                start = System.currentTimeMillis();
                String imgUrl = FastDFSClient.uploadFile(Files.readAllBytes(img.toPath()), "jpg", null);
                logger.info("图片上传到FastDFS时间 : " + (System.currentTimeMillis() - start));
                urlMap.put("imgUrl", imgUrl);
            }

            // 上传音乐文件, 如果用户没有上传音乐, 使用ffmpeg剥离视频音乐
            if (musicFlag) {
                start = System.currentTimeMillis();
                music = fFmpegUtils.processMusic(source);
                logger.info("剥离音乐消耗时间时间 : " + (System.currentTimeMillis() - start));

                start = System.currentTimeMillis();
                String musicUrl = FastDFSClient.uploadFile(Files.readAllBytes(music.toPath()), "mp3", null);
                logger.info("音乐上传到FastDFS时间 : " + (System.currentTimeMillis() - start));
                urlMap.put("musicUrl", musicUrl);
            }

        } finally {
            // 删除视频图片文件
            deleteFile(video);
            deleteFile(img);
            deleteFile(source);
            deleteFile(music);
        }

        return urlMap;
    }

    private void deleteFile(File file) {
        if (file != null) {
            System.out.println(file.delete());
        }
    }

}
