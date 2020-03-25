package com.jh.system.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version <1> 2019/5/13: lijie  Created.
 * @Description: 生产随机字符串
 */
public class RandomUtils {


    private static int maxvaluefive=99999999;
    private static int minvaluefive=0;
    public static String USER_NAME = "用户_";//昵称前缀
    public static Integer USER_TOTAL_LENGTH = 7; //昵称后缀总长度
    private static AtomicInteger atomic = new AtomicInteger(minvaluefive);

    private static Random strGen = new Random();;
    private static Random numGen = new Random();;
    private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz").toCharArray();;
    private static char[] numbers = ("0123456789").toCharArray();;

    /**  生成序列号 */
    public static String getSeqFive(int coverPad) {
        for (; ; ) {
            int current = atomic.get();
            int newValue = current >= maxvaluefive ? minvaluefive : current + 1;
            if (atomic.compareAndSet(current, newValue)) {
                return StringUtils.leftPad(String.valueOf(current), coverPad, "0");
            }
        }
    }

    /** * 产生随机字符串 * */
    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[strGen.nextInt(36)];
        }
        return new String(randBuffer);
    }

    /** * 产生随机数值字符串 * */
    public static final String randomNumStr(int length) {
        if (length < 1) {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbers[numGen.nextInt(9)];
        }
        return new String(randBuffer);
    }

    public static void main(String [] args){
        //System.out.println(randomString(4));
        //System.out.println(randomNumStr(6));

        String accountIdStr = "12";
        Integer accountLen = accountIdStr.length();
        Integer totalLen = RandomUtils.USER_TOTAL_LENGTH;
        Integer randomStr = accountLen >= totalLen ? 0 : totalLen - accountIdStr.length();
        String nickName = RandomUtils.USER_NAME + accountIdStr;
        if(randomStr > 0){
            nickName = nickName  + RandomUtils.randomString(randomStr);
        }
        System.out.println(nickName);
    }
}
