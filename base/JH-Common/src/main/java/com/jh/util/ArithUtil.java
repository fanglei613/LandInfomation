package com.jh.util;

import java.math.BigDecimal;

/**
 * description: 加减乘除基础计算
 * @version <1> 2018/04/28 cxw： Created.
 */

public class ArithUtil {

    private static final int DEF_DIV_SCALE = 10; // 这个类不能实例化

    private ArithUtil() {
    }

    /**
     * 提供精确的加法运算。
     * @param
     * v1 被加数
     * v2 加数
     * @return 两个参数的和
     * @version <1> 2018/04/28 cxw： Created.
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * @param
     * v1 被减数
     * v2 减数
     * @return 两个参数的差
     * @version <1> 2018/04/28 cxw： Created.
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     * @param
     * v1  被乘数
     * v2  乘数
     * @return 两个参数的积
     * @version <1> 2018/04/28 cxw： Created.
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     * @param
     * v1 被除数
     * v2 除数
     * @return 两个参数的商
     * @version <1> 2018/04/28 cxw： Created.
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     * @param
     * v1 被除数
     * v2 除数
     * scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     * @version <1> 2018/04/28 cxw： Created.除数和被除数为零时情况处理，若被除数为0，则value为0  ，若除数为0 ，则value为1 ;
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        if(b1.compareTo(BigDecimal.ZERO)==0){ //被除数（必须写在除数前面，否则可能漏掉被除数为0，除数也为0时的情况处理）
            return 0;
        }else if(b2.compareTo(BigDecimal.ZERO)==0){ //除数
            return 1;
        }else{
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }


    /**
     * 提供精确的小数位四舍五入处理。
     * @param
     * v 需要四舍五入的数字
     * scale 小数点后保留几位
     * @return 四舍五入后的结果
     * @version <1> 2018/04/28 cxw： Created.
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
