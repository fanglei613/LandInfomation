package com.jh.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Title: CollectionUtil
 * Description: 集合工具类
 *
 * @vsesion <1> 2018-06-24 16:08 ZhangShen Created.
 */
public final class CollectionUtil {

    /**
     * 判断Collection是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection){
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断Collection是否非空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }

    /**
     * 判断Map是否为空
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map){
        return MapUtils.isEmpty(map);
    }

    /**
     * 判断Map是否非空
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> map){
        return !isEmpty(map);
    }

    /**
     * 调换集合中两个指定位置的元素, 若两个元素位置中间还有其他元素，需要实现中间元素的前移或后移的操作。
     * @param list 集合
     * @param oldPosition 需要调换的元素
     * @param newPosition 被调换的元素
     * @param <T>
     * @version <1> 2018/7/20 8:38 zhangshen: Created.
     */
    public static <T> List<T> swap1(List<T> list, int oldPosition, int newPosition){
        if(null == list){
            throw new IllegalStateException("The list can not be empty...");
        }
        T tempElement = list.get(oldPosition);

        // 向前移动，前面的元素需要向后移动
        if(oldPosition < newPosition){
            for(int i = oldPosition; i < newPosition; i++){
                list.set(i, list.get(i + 1));
            }
            list.set(newPosition, tempElement);
        }
        // 向后移动，后面的元素需要向前移动
        if(oldPosition > newPosition){
            for(int i = oldPosition; i > newPosition; i--){
                list.set(i, list.get(i - 1));
            }
            list.set(newPosition, tempElement);
        }
        return list;
    }

    /**
     * 字符串以分隔符转化为List
     * @param str
     * @param separator
     * @return
     */
    public static List<String> strToList(String str, String separator){
        if(StringUtils.isEmpty(str) ){
            return new ArrayList<>();
        }
        return Arrays.asList(str.split(","));
    }

}
