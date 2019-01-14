package com.faceshow.common.utils;

import com.faceshow.common.exception.RRException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class FFmpegUtils {
    private Date dt;
    private long begintime;
    private String ffmpegpath;

    public FFmpegUtils() {
        try {
            if (File.separator.equalsIgnoreCase("\\")) {
                ffmpegpath = new ClassPathResource("ffmpeg.exe").getFile().getAbsolutePath();
            } else {
                ffmpegpath = new ClassPathResource("ffmpeg").getFile().getAbsolutePath();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec(new String[]{"/bin/chmod", "755", ffmpegpath});
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("ffmpeg 加载失败");
        }
    }

    /**
     * 转换视频格式
     *
     * @return
     */
    public Map<String, File> beginConver(File source) throws Exception {
        System.out.println("----接收到文件需要转换-------------------------- ");
        if (!checkfile(source)) {
            throw new Exception("文件不存在");
        }
        dt = new Date();
        begintime = dt.getTime();
        System.out.println("----开始转文件-------------------------- ");

        // 压缩转码后视频文件
        File video = process(source);

        Date dt2 = new Date();
        System.out.println("转换成功 ");
        long endtime = dt2.getTime();
        long timecha = (endtime - begintime);
        // String totaltime = sumTime(timecha);
        System.out.println("转换视频格式共用了:" + timecha + " ");

        //视频缩略图
        File img = processImg(source);

        Map<String, File> map = new HashMap<>(0);
        map.put("video", video);
        map.put("img", img);
        source = null;
        return map;

    }

    /**
     * 对视频进行截图
     *
     * @return
     */
    public File processImg(File source) throws Exception {
        if (!checkfile(source)) {
            throw new Exception("文件不存在");
        }

        File desc = new File(UUID.randomUUID().toString() + ".jpg");
        desc = new File(desc.getAbsolutePath());

        List<String> commend = new java.util.ArrayList<String>();
        commend.add(ffmpegpath);
        commend.add("-ss");
        commend.add("00:00:03");
        commend.add("-i");
        commend.add(source.getAbsolutePath());
        commend.add("-f");
        commend.add("image2");
        commend.add("-y");
        commend.add(desc.getAbsolutePath());
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 剥离视频中的音乐
     *
     * @param source 视频文件
     * @return
     */
    public File processMusic(File source) throws Exception {
        if (!checkfile(source)) {
            throw new Exception("文件不存在");
        }

        File desc = new File(UUID.randomUUID().toString() + ".mp3");
        desc = new File(desc.getAbsolutePath());

        List<String> commend = new java.util.ArrayList<String>();
        commend.add(ffmpegpath);
        commend.add("-y");
        commend.add("-i");
        commend.add(source.getAbsolutePath());
        commend.add("-vn");
        commend.add(desc.getAbsolutePath());
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 实际转换视频格式的方法
     *
     * @return
     */
    public File process(File source) throws Exception {
        int type = checkContentType(source);
        File status = null;
        if (type == 0) {
            // 如果type为0用ffmpeg直接转换
            status = processVideoFormat(source);
        } else if (type == 1) {
            throw new Exception("视频格式不支持转换");
        }
        return status;
    }

    /**
     * 检查文件类型
     *
     * @return
     */
    private int checkContentType(File source) {
        String path = source.getAbsolutePath();
        String type = path.substring(path.lastIndexOf(".") + 1, path.length())
                .toLowerCase();
        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mpg")) {
            return 0;
        } else if (type.equals("wmv")) {
            return 0;
        } else if (type.equals("3gp")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("asf")) {
            return 0;
        } else if (type.equals("asx")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        }
        // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        else if (type.equals("wmv9")) {
            return 1;
        } else if (type.equals("rm")) {
            return 1;
        } else if (type.equals("rmvb")) {
            return 1;
        }
        return 9;
    }

    /**
     * 检查文件是否存在
     *
     * @param file
     * @return
     */
    private boolean checkfile(File file) {
        if (!file.isFile()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 转换为指定格式 ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
     *
     * @return
     */
    private File processVideoFormat(File source) throws Exception {
        if (!checkfile(source)) {
            throw new Exception("文件不存在");
        }

        File desc = new File(UUID.randomUUID().toString() + ".mp4");
        desc = new File(desc.getAbsolutePath());
        // ffmpeg -i FILE_NAME.flv -ar 22050 NEW_FILE_NAME.mp4
        List<String> commend = new java.util.ArrayList<>();
        commend.add(ffmpegpath);
        commend.add("-i");
        commend.add(source.getAbsolutePath());
        /*commend.add("-vcodec");
        commend.add("h264");
        commend.add("-b");
        commend.add("600000");
        commend.add("-r");
        commend.add("15");
        commend.add("-acodec");
        commend.add("aac");
        commend.add("-ab");
        commend.add("128000");
        commend.add("-ac");
        commend.add("2");
        commend.add("-ar");
        commend.add("44100");
        commend.add("-f");
        commend.add("mp4");
        commend.add("-y");*/
        commend.add(desc.getAbsolutePath());
        System.err.println(commend);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public int doWaitFor(Process p) {
        InputStream in = null;
        InputStream err = null;
        // returned to caller when p is finished
        int exitValue = -1;
        try {
            System.out.println("comeing");
            in = p.getInputStream();
            err = p.getErrorStream();
            // Set to true when p is finished
            boolean finished = false;

            while (!finished) {
                try {
                    while (in.available() > 0) {
                        Character c = new Character((char) in.read());
                        System.out.print(c);
                    }
                    while (err.available() > 0) {
                        Character c = new Character((char) err.read());
                        System.out.print(c);
                    }

                    exitValue = p.exitValue();
                    finished = true;

                } catch (IllegalThreadStateException e) {
                    Thread.currentThread().sleep(500);
                }
            }
        } catch (Exception e) {
            System.err.println("doWaitFor();: unexpected exception - " + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return exitValue;
    }

}
