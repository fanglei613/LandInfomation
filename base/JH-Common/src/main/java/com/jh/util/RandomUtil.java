package com.jh.util;

import java.util.Random;

/**
 * 生成各类随机数
 * Created by zhaoyong on 2016年12月13日.
 */
public class RandomUtil {

    /**
     * 生成一个四位数子的随机数
     * @version <1> 2016-12-13 zhaoyong
     * @return
     */
    public static String RandomFourNumber(){
        return String.valueOf(Math.round(Math.random() * 8999 + 1000));
    }

    /**
     * 生成一个六位数子的随机数
     * @version <1> 2016-12-13 zhaoyong
     * @return
     */
    public static String RandomSixNumber(){
        return String.valueOf(Math.round(Math.random() * 899999 + 100000));
    }

    /**
     * 生成指定长度的数字和字符的随机字符
     * @version <1> 2016-12-13 zhaoyong
     * @param length
     * @return
     */
    private static String RandomStringAndNumber(int length){
        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 生成六位数字和字母混合随机字符
     * @version <1> 2016-12-13 zhaoyong
     * @return
     */
    public static String RandomSixStringAndNumber(){
        return RandomStringAndNumber(6);
    }

    /**
     * 生成四位数字和字母混合随机字符
     * @version <1> 2016-12-13 zhaoyong
     * @return
     */
    public static String RandomFourStringAndNumber(){
        return RandomStringAndNumber(4);
    }
}
