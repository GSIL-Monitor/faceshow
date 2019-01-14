package com.faceshow.common.utils;


import java.io.*;

import com.faceshow.common.MQ.message.likenum.FastDFSProduct;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class FastDFSClient {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

    public static String[] storege_ips = {"104.243.131.10-img10.gchao.com", "104.243.131.11-img11.gchao.com"};

    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageClient1 storageClient;
    private static StorageServer storageServer;

    static {
        try {
            ClientGlobal.init("fdfs_client.conf");
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient1(trackerServer, storageServer);
        } catch (Exception e) {
            logger.error("FastDFS Client Init Fail!", e);
        }
    }


    /**
     * 将字节流写到磁盘生成文件
     *
     * @param b        文件的字节数组
     * @param path     文件保存路径
     * @param fileName 文件名
     */
    private static void saveFile(byte[] b, String path, String fileName) {
        File file = new File(path + fileName);
        if (!new File(path).exists()) {
            file.mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 上传文件方法
     * <p>
     * Title: uploadFile
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @param fileName 文件全路径
     * @param extName  文件扩展名，不包含（.）
     * @param metas    文件扩展信息
     * @return
     * @throws Exception
     */
    public static String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String url = getStorageUrl() + storageClient.upload_file1(fileName, extName, metas);
        FastDFSProduct.saveFileUrl(url);
        return url;
    }

    public static String uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, null, null);
    }

    public static String uploadFile(String fileName, String extName) throws Exception {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 上传文件方法
     * <p>
     * Title: uploadFile
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @param fileContent 文件的内容，字节数组
     * @param extName     文件扩展名
     * @param metas       文件扩展信息
     * @return
     * @throws Exception
     */
    public static String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {
        String url = getStorageUrl() + storageClient.upload_file1(fileContent, extName, metas);
        FastDFSProduct.saveFileUrl(url);
        return url;
    }

    /**
     * 根据groupName和文件名获取文件信息。
     *
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static InputStream downFile(String groupName, String remoteFileName) {
        try {
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            InputStream ins = new ByteArrayInputStream(fileByte);
            return ins;
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param groupName
     * @param remoteFileName
     * @throws Exception
     */
    public static void deleteFile(String groupName, String remoteFileName) throws Exception {
        int i = storageClient.delete_file(groupName, remoteFileName);
        logger.info("delete file successfully!!!" + i);
    }

    public static StorageServer[] getStoreStorages(String groupName) throws IOException {
        return trackerClient.getStoreStorages(trackerServer, groupName);
    }

    public static ServerInfo[] getFetchStorages(String groupName, String remoteFileName) throws IOException {
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    /**
     * 获取fastdfs Tracker IP
     *
     * @return
     */
    public static String getTrackerUrl() {
        return "http://" + trackerServer.getInetSocketAddress().getHostString() + "/";
    }

    /**
     * 获取fastdfs Storage IP
     *
     * @return
     */
    public static String getStorageUrl() throws IOException {
        String host = storageServer.getInetSocketAddress().getHostString();
        for (String ip : storege_ips) {
            if (ip.startsWith(host + "-")) {
                host = ip.replace(host + "-", "");
            }
        }
        return "http://" + host + "/";
    }


    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String file = uploadFile("D:\\a.jpg", "jpg", null);
                    System.out.println(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}