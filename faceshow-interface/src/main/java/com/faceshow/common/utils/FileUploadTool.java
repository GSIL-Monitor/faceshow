package com.faceshow.common.utils;

import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

@RestController
public class FileUploadTool {
    // 文件最大100M
    private static long upload_maxsize = 100 * 1024 * 1024;
    // 文件允许格式
    private static String[] allowFiles = {".rar", ".doc", ".docx", ".zip",
            ".pdf", ".txt", ".swf", ".xlsx", ".gif", ".png", ".jpg", ".jpeg",
            ".bmp", ".xls", ".mp4", ".flv", ".ppt", ".avi", ".mpg", ".wmv",
            ".3gp", ".mov", ".asf", ".asx", ".vob", ".wmv9", ".rm", ".rmvb"};

    @RequestMapping("/fileUpload")
    @ResponseBody
    public R createFile(MultipartFile multipartFile, HttpServletRequest request, String type) {
        String fileName = multipartFile.getOriginalFilename().toString();
        // 判断文件不为空
        if (multipartFile.getSize() == 0 || multipartFile.isEmpty()) {
            System.out.println("文件为空");
            return R.error("文件为空");
        }
        // 判断文件大小
        if (multipartFile.getSize() > upload_maxsize) {
            System.out.println("文件大小超范围");
            return R.error("文件大小超范围");
        }

        // 文件类型判断
        if (!this.checkFileType(fileName)) {
            System.out.println("文件类型不允许");
            return R.error("文件类型不允许");
        }

        // 保存文件的
        String realPathDir = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        File saveFile = new File(realPathDir);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        System.out.println("文件名称：" + name);
        // 新的文件名
        String newFileName = this.getName(fileName);
        // 文件扩展名
        String fileEnd = this.getFileExt(fileName);
        // 绝对路径
        String fileNamedirs = realPathDir + File.separator
                + newFileName + fileEnd;
        System.out.println("保存的路径：" + fileNamedirs);
        File filedirs = new File(fileNamedirs);
        // 转入文件
        try {
            multipartFile.transferTo(filedirs);

            String url = newFileName + fileEnd;
            return R.ok("文件上传成功").put("result", url);

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.error("文件上传失败");
    }

    /**
     * 文件类型判断
     *
     * @param fileName
     * @return
     */
    private boolean checkFileType(String fileName) {
        Iterator<String> type = Arrays.asList(allowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取文件扩展名
     *
     * @return string
     */
    private String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 依据原始文件名生成新文件名
     *
     * @return
     */
    private String getName(String fileName) {
        Random random = new Random();
        return "" + random.nextInt(10000) + System.currentTimeMillis();

    }

    /**
     * 生成新的文件名字
     *
     * @return
     */
    private String getName2() {
        Random random = new Random();
        return "" + random.nextInt(10000) + System.currentTimeMillis();

    }

    /**
     * base64编码上传图片
     *
     * @param
     * @param request
     * @param type
     * @return
     * @throws Exception
     */

    public String uploadPoster(HttpServletRequest request, String type, String s) {

        String base64Img = s;
        String uploadImg = "/upload/" + type;

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {

            e1.printStackTrace();

            return "失败";
        }
        String realPath = request.getServletContext().getRealPath(uploadImg);

        File file_normer = new File(realPath);
        if (!file_normer.exists()) {
            file_normer.mkdirs();
        }

        if (base64Img == null) // 图像数据为空
            return "失败";
        base64Img = base64Img.replaceAll("data:image/jpeg;base64,", "");
        BASE64Decoder decoder = new BASE64Decoder();
        String name = this.getName2();
        try {
            // Base64解码
            //	byte[] b = decoder.decodeBuffer(base64Img);

            byte[] b = decoder.decodeBuffer(base64Img);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }

            File file = new File(realPath + File.separator + name + ".jpg");

            OutputStream out = new FileOutputStream(file);
            out.write(b);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String image_url = name + ".jpg";
        return image_url;
    }

    /**
     * 注册, 修改个人信息, 保存上传图片
     *
     * @param imgFile 图片
     * @param request
     * @return 图片保存路径
     */
    public String fileUpload(MultipartFile imgFile, HttpServletRequest request) {
        if (imgFile != null && StringTool.notEmpty(imgFile.getOriginalFilename())) {
            String fileName = imgFile.getOriginalFilename();
            // 文件类型判断
            if (!checkFileType(fileName)) {
                System.out.println("文件类型不允许");
                return null;
            }
            // 保存文件的真是路径
            String realPathDir = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
            File saveFile = new File(realPathDir);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            }

            String name = fileName.substring(0, fileName.lastIndexOf("."));
            System.out.println("文件名称：" + name);

            Random random = new Random();

            // 新的文件名
            String newFileName = "" + random.nextInt(10000) + System.currentTimeMillis();
            // 文件扩展名
            String fileEnd = fileName.substring(fileName.lastIndexOf("."));

            // 绝对路径
            String fileNamedirs = realPathDir + File.separator + newFileName + fileEnd;
            System.out.println("保存的路径：" + fileNamedirs);
            File filedirs = new File(fileNamedirs);

            // 转入文件
            try {
                imgFile.transferTo(filedirs);

                return request.getContextPath() + File.separator + newFileName + fileEnd;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}