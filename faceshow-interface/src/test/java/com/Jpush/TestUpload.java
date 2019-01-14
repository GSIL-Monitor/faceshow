package com.Jpush;

import org.csource.fastdfs.*;
import org.junit.Test;

public class TestUpload {
    @Test
    public void testUpload() throws Exception {
        // 1、把FastDFS提供的jar包添加到工程中
        // 2、初始化全局配置。加载一个配置文件。
        ClientGlobal.init("D:\\SVNAPP\\SVN\\faceshow-interface\\src\\main\\resources\\fdfs_client.conf");
        // 3、创建一个TrackerClient对象。
        TrackerClient trackerClient = new TrackerClient();
        // 4、创建一个TrackerServer对象。
        TrackerServer trackerServer = trackerClient.getConnection();
        // 5、声明一个StorageServer对象，null。
        StorageServer storageServer = null;
        // 6、获得StorageClient对象。
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 7、直接调用StorageClient对象方法上传文件即可。
        String[] strings = storageClient.upload_file("D:\\CloudMusic\\卢焱 - 流浪.mp3", "mp3", null);
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
