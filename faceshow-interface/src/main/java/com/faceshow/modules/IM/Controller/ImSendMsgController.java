package com.faceshow.modules.IM.Controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.exception.RRException;
import com.faceshow.common.utils.HttpClientUtil;
import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.R;
import com.faceshow.modules.IM.Bean.ImFriendAdd;
import com.faceshow.modules.IM.Bean.ImSendMsg;
import com.faceshow.modules.IM.Imutils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tokens/im/sendMsg")
public class ImSendMsgController {
    @SysLog
    @PostMapping("/sendMsg")
    public  Object sendMsg (ImSendMsg imSendMsg){
        String BUSINESS="v4/openim/sendmsg";//业务API
        String api = Imutils.BASISAPI+BUSINESS+Imutils.PUBLIC;//拼接URL
        String date= HttpClientUtil.doPostJson(api, JsonUtils.objectToJson(imSendMsg));//发送请求
        Map json = JsonUtils.jsonToPojo(date, new HashMap<>().getClass());
        if (json.get("ActionStatus").toString().equalsIgnoreCase("FAIL")){
            throw  new RRException(date);
        }
        return R.result(1,"ok",date);
    }
}
