package com.imTest;

public class ImApi {
    public static void main(String[] args) {
        //修改群资料API测试
        String json ="{\n" +
                "  \"GroupId\": \"852852872\", \n" +
                "  \"ApplyJoinOption\": \"FreeAccess\"\n" +
                "}";//传递的JSON字符串参数
        String BUSINESS="v4/group_open_http_svc/modify_group_base_info";//业务API
        String api =Imutils.BASISAPI+BUSINESS+Imutils.PUBLIC;//拼接URL
        String date= HttpClientUtil.doPostJson(api,json);//发送请求
        System.out.println(date);
     //String dat1e= HttpClientUtil.doPostJson("https://console.tim.qq.com/v4/group_open_http_svc/get_group_info?usersig=eJxlj81Og0AYRfc8xYStRoafIWDipnVIUamYQhq7IZQZxs-SgcDQosZ3t8Umkni359zc3C8NIaQnT6ubvCjqXqpMfTRcR7dIx-r1H2waYFmuMrtl-yAfGmh5lpeKtyM0CSEWxlMHGJcKSrgYOduDnOCO7bJx47fvnMqu7fveVAExwoim8-BlvhhCSw3MewfZByK1W0oerkT5unZcXg6bKKaqquNqu6UiFM8MjkGVsmVyNHZsvZkZM*PtfmlKsz50n33wGC8Su6CFF9G7yaSCPb8cMl3ie9iZXjrwtoNajoKFT4pl43N07Vv7AXExXaM_&identifier=admin&sdkappid=1400063998&random=58bfe85f-af59-45f2-ac8f-38100ec37b2a&contenttype=json", json);
       /* ImDelGropBean bean = new ImDelGropBean();
        bean.setGroupId("12341564564");
        String [] s=new String[]{"zhangsan","lisi"};
        bean.setMemberToDel_Account(s);
        System.out.println(JsonUtils.objectToJson(bean));*/
    }
}
