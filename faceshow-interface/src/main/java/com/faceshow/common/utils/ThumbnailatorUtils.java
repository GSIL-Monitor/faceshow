package com.faceshow.common.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ThumbnailatorUtils {

    private static final Logger logger = LoggerFactory.getLogger(ThumbnailatorUtils.class);
    /**
     * 指定大小进行缩放
     * 若图片横比width小，高比height小，不变 若图片横比width小，高比height大，高缩小到height，图片比例不变
     * 若图片横比width大，高比height小，横缩小到width，图片比例不变
     * 若图片横比width大，高比height大，图片按比例缩小，横为width或高为height
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     */
    public void ImgThumb(String source, String output, int width, int height) {
        try {
            Thumbnails.of(source).size(width, height).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定大小进行缩放
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     */
    public void ImgThumb(File source, String output, int width, int height) {
        try {
            Thumbnails.of(source).size(width, height).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照比例进行缩放
     *
     * @param source 输入源
     * @param output 输出源
     * @param scale
     */
    public void ImgScale(String source, String output, double scale) {
        try {
            Thumbnails.of(source).scale(scale).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgScale(File source, String output, double scale) {
        try {
            Thumbnails.of(source).scale(scale).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不按照比例，指定大小进行缩放
     *
     * @param source          输入源
     * @param output          输出源
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public void ImgNoScale(String source, String output, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgNoScale(File source, String output, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 旋转 ,正数：顺时针 负数：逆时针
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param rotate 角度
     */
    public void ImgRotate(String source, String output, int width, int height, double rotate) {
        try {
            Thumbnails.of(source).size(width, height).rotate(rotate).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgRotate(File source, String output, int width, int height, double rotate) {
        try {
            Thumbnails.of(source).size(width, height).rotate(rotate).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 水印
     *
     * @param source       输入源
     * @param output       输入源
     * @param width        宽
     * @param height       高
     * @param position     水印位置 Positions.BOTTOM_RIGHT o.5f
     * @param watermark    水印图片地址
     * @param transparency 透明度 0.5f
     * @param quality      图片质量 0.8f
     */
    public void ImgWatermark(String source, String output, int width, int height, Position position, String watermark, float transparency, float quality) {
        try {
            Thumbnails.of(source).size(width, height).watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(0.8f).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgWatermark(File source, String output, int width, int height, Position position, String watermark, float transparency, float quality) {
        try {
            Thumbnails.of(source).size(width, height).watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(0.8f).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪图片
     *
     * @param source          输入源
     * @param output          输出源
     * @param position        裁剪位置
     * @param x               裁剪区域x
     * @param y               裁剪区域y
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public void ImgSourceRegion(String source, String output, Position position, int x, int y, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(position, x, y).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgSourceRegion(File source, String output, Position position, int x, int y, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(position, x, y).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按坐标裁剪
     *
     * @param source          输入源
     * @param output          输出源
     * @param x               起始x坐标
     * @param y               起始y坐标
     * @param x1              结束x坐标
     * @param y1              结束y坐标
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public void ImgSourceRegion(String source, String output, int x, int y, int x1, int y1, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(x, y, x1, y1).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgSourceRegion(File source, String output, int x, int y, int x1, int y1, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(x, y, x1, y1).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转化图像格式
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param format 图片类型，gif、png、jpg
     */
    public void ImgFormat(String source, String output, int width, int height, String format) {
        try {
            Thumbnails.of(source).size(width, height).outputFormat(format).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgFormat(File source, String output, int width, int height, String format) {
        try {
            Thumbnails.of(source).size(width, height).outputFormat(format).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出到OutputStream
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @return toOutputStream(流对象)
     */
    public OutputStream ImgOutputStream(String source, String output, int width, int height) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(output);
            Thumbnails.of(source).size(width, height).toOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    public OutputStream ImgOutputStream(File source, String output, int width, int height) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(output);
            Thumbnails.of(source).size(width, height).toOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    /**
     * 输出到BufferedImage
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param format 图片类型，gif、png、jpg
     * @return BufferedImage
     */
    public BufferedImage ImgBufferedImage(String source, String output, int width, int height, String format) {
        BufferedImage buf = null;
        try {
            buf = Thumbnails.of(source).size(width, height).asBufferedImage();
            ImageIO.write(buf, format, new File(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }

    public BufferedImage ImgBufferedImage(File source, String output, int width, int height, String format) {
        BufferedImage buf = null;
        try {
            buf = Thumbnails.of(source).size(width, height).asBufferedImage();
            ImageIO.write(buf, format, new File(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }

    /**
     * @param imageFile 图片文件
     * @return
     * @Description:保存图片并且生成缩略图
     */
    public Map<String, Object> uploadFileAndCreateThumbnail1(MultipartFile imageFile) throws Exception {
        long start0 = System.currentTimeMillis();
        // 获取扩展名
        String extName = imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().lastIndexOf(".") + 1);

        String bigImgUrl = "";

        // 如果源文件大于3M, 就进行压缩, 压缩成2M, 如果小于3M, 则不进行处理
        if (imageFile.getSize() > 1024 * 1024 * 3) {
            File newFile = new File(UUID.randomUUID().toString().replace("-", "") + "." + extName);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            // 先转换成jpg
            long start = System.currentTimeMillis();
            if (!"jpg".equalsIgnoreCase(extName)) {
                Thumbnails.of(imageFile.getInputStream()).scale(1f).toFile(newFile);
            }
            long end = System.currentTimeMillis();
            logger.info("转换成jpg时间: " + (end - start));

            // 递归压缩，直到目标文件大小小于desFileSize
            File descFile = multipartToFile(imageFile);

            long end2 = System.currentTimeMillis();
            logger.info("MultipartFile转File时间: " + (end2 - end));

            commpressPicCycle(descFile, 2 * 1024 * 1024, 0.8);

            long end3 = System.currentTimeMillis();
            logger.info("压缩文件时间: " + (end3 - end2));

            // 压缩完成, 进行上传
            byte[] bytes = Files.readAllBytes(newFile.toPath());
            long end4 = System.currentTimeMillis();
            bigImgUrl = FastDFSClient.uploadFile(bytes, extName, null);
            long end5 = System.currentTimeMillis();
            logger.info("上传文件时间" + (end5 - end4));

            descFile.delete();
            newFile.delete();
        } else {
            long start1 = System.currentTimeMillis();
            bigImgUrl = FastDFSClient.uploadFile(imageFile.getBytes(), extName, null);
            long end1 = System.currentTimeMillis();
            logger.info("上传文件时间" + (end1 - start1));
            logger.info("文件大小: " + imageFile.getSize());
        }

        File file = new File(UUID.randomUUID().toString().replace("-", "") + "." + extName);
        if (!file.exists()) {
            file.createNewFile();
        }
        long start = System.currentTimeMillis();
        try {
            Thumbnails.of(imageFile.getInputStream()).size(320, 320).toFile(file);
        } catch (Exception e1) {
            System.out.println(e1);
            logger.error("error: ",e1);
            return null;
        }
        long end = System.currentTimeMillis();
        logger.info("生成缩略图时间: " + (end - start));

        String smallImgUrl = FastDFSClient.uploadFile(Files.readAllBytes(file.toPath()), extName, null);

        file.delete();
        Map<String, Object> map = new HashMap<String, Object>(0);
        map.put("bigImgUrl", bigImgUrl);
        map.put("smallImgUrl", smallImgUrl);
        long end0 = System.currentTimeMillis();
        logger.info("方法执行总时间: " + (end0 - start0));
        return map;
    }

    /**
     * 递归压缩图片到指定大小
     *
     * @param descFile    图片
     * @param desFileSize 指定大小
     * @param accuracy    压缩精度, 建议小于0.9
     * @throws IOException
     */
    private void commpressPicCycle(File descFile, long desFileSize,
                                          double accuracy) throws IOException {
        long srcFileSizeJPG = descFile.length();
        // 2、判断大小，如果小于500kb，不压缩；如果大于等于500kb，压缩
        if (srcFileSizeJPG <= desFileSize) {
            return;
        }
        // 计算宽高
        BufferedImage bim = ImageIO.read(descFile);
        int srcWdith = bim.getWidth();
        int srcHeigth = bim.getHeight();
        int desWidth = new BigDecimal(srcWdith).multiply(
                new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(srcHeigth).multiply(
                new BigDecimal(accuracy)).intValue();

        Thumbnails.of(descFile).size(desWidth, desHeight)
                .outputQuality(accuracy).toFile(descFile);
        commpressPicCycle(descFile, desFileSize, accuracy);
    }

    /**
     * MultipartFile 转换成File
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */
    private File multipartToFile(MultipartFile multfile) throws IOException {
        String extName = multfile.getOriginalFilename().substring(multfile.getOriginalFilename().lastIndexOf("."));
        File file = new File(UUID.randomUUID().toString().replace("-", "") + extName);
        file = new File(file.getAbsolutePath());
        multfile.transferTo(file);
        return file;
    }

    public static void main(String[] args) {
        // ImgThumb("C:\\Users\\mayn\\Desktop\\123\\22083103.jpg", "C:\\Users\\mayn\\Desktop\\123\\12321321.jpg", 200, 200);
    }
}
