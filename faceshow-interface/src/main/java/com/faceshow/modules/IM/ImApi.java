package com.faceshow.modules.IM;

import com.faceshow.common.utils.HttpClientUtil;
import com.faceshow.common.utils.JsonUtils;
import com.faceshow.modules.Broadcast.BroadcastApiutils;
import com.faceshow.modules.IM.Bean.*;
import org.thymeleaf.templatewriter.ITemplateWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImApi {
    public static void main(String[] args) {
        String type="AVChatRoom";
        String userId="225";
        String json="{\n" +
                "    \"Member_Account\": \"242\"\n" +
                "}";
        System.out.println(json);
        String BUSINESS="v4/group_open_http_svc/get_joined_group_list";//业务API
        String api =Imutils.BASISAPI+BUSINESS+Imutils.PUBLIC;//拼接URL
        String date= HttpClientUtil.doPostJson(api,json);//发送请求
        System.out.println(date);









        /*ImProfileItemPortraitSet set = new ImProfileItemPortraitSet();
        set.setTag("Tag_Profile_IM_Nick");
        set.setValue("MyNickName");
        ImProfileItemPortraitSet set1 = new ImProfileItemPortraitSet();
        set1.setTag("Tag_Profile_IM_Nick1");
        set1.setValue("MyNickName1");
        ArrayList list = new ArrayList();
        list.add(set);
        list.add(set1);
        ImPortraitSet portraitSet = new ImPortraitSet();
        portraitSet.setProfileItem(list);
        portraitSet.setFrom_Account("heihei");
        System.out.println(JsonUtils.objectToJson(portraitSet));*/


       /* ImMsgContent imMsgContent = new ImMsgContent();
        imMsgContent.setText("hi, beauty");

        ImMsgBody imMsgBody = new ImMsgBody();
        imMsgBody.setMsgContent(imMsgContent);
        imMsgBody.setMsgType("TIMTextElem");

        ImAndroidInfo imAndroidInfo = new ImAndroidInfo();
        imAndroidInfo.setSound("android.mp3");

        ImApnsInfo imApnsInfo = new ImApnsInfo();
        imApnsInfo.setSound("apns.mp3");
        imApnsInfo.setBadgeMode(1);
        imApnsInfo.setTitle("apns title");
        imApnsInfo.setSubTitle("apns subtitle");
        imApnsInfo.setImage("www.image.com");

        ImOfflinePushInfo imOfflinePushInfo = new ImOfflinePushInfo();
        imOfflinePushInfo.setPushFlag(0);
        imOfflinePushInfo.setDesc("离线推送内容");
        imOfflinePushInfo.setExt("这是透传的内容");
        imOfflinePushInfo.setAndroidInfo(imAndroidInfo);
        imOfflinePushInfo.setApnsInfo(imApnsInfo);


        ArrayList<ImMsgBody> imMsgBodies = new ArrayList<>();
        imMsgBodies.add(imMsgBody);

        ImSendMsg imSendMsg = new ImSendMsg();
        imSendMsg.setSyncOtherMachine(2);
        imSendMsg.setFrom_Account("lumotuwe1");
        imSendMsg.setTo_Account("lumotuwe2");
        imSendMsg.setMsgLifeTime(3600);
        imSendMsg.setMsgRandom(1287657);
        imSendMsg.setMsgTimeStamp(5454457);
        imSendMsg.setMsgBody(imMsgBodies);
        imSendMsg.setOfflinePushInfo(imOfflinePushInfo);

        System.out.println(JsonUtils.objectToJson(imSendMsg));*/

    }
}
