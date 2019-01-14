package com.faceshow;

import com.faceshow.modules.live.dao.LiveInfoDao;
import com.faceshow.modules.user.dao.UserInfoDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/23 9:49
 * -------------------------------------------------------------- <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IniTest {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private LiveInfoDao liveInfoDao;

}
