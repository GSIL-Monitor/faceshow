package com.faceshow.common.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机生成6位字符加数字的验证码
 *
 * @author Administrator
 */
public class InvertCodeGenerator {

    /**
     * 生成随机数
     *
     * @param length 随机数长度
     * @param num    个数
     * @return
     */
    public static List<String> genCodes(int length, long num) {

        List<String> results = new ArrayList<String>();

        for (int j = 0; j < num; j++) {
            String val = "";

            Random random = new Random();
            for (int i = 0; i < length; i++) {
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

                if ("char".equalsIgnoreCase(charOrNum)) // 字符串
                {
                    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                    val += (char) (choice + random.nextInt(26));
                } else if ("num".equalsIgnoreCase(charOrNum)) // 数字
                {
                    val += String.valueOf(random.nextInt(10));
                }
            }
            val = val.toLowerCase();
            if (results.contains(val)) {
                continue;
            } else {
                results.add(val);
            }
        }
        return results;
    }

    public static int getNumber() {
        return (int) (Math.random() * 900000 + 100000);
    }

    public static void main(String[] args) {
        System.out.println(genCodes(10, 1).get(0));
        System.out.println(getNumber());
    }


}