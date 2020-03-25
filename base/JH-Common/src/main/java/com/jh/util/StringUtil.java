package com.jh.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * @version <1> 2018-05-14 zhangshen : Created.
 */
public class StringUtil {
    public static boolean isNotNull(String str){
        if(!"null".equals(str) && StringUtils.isNotBlank(str)){
            return true;
        }
        return false;
    }
}
