package com.jh.util;


import com.jh.enums.CacheDataEnum;
import com.jh.enums.CacheTypeEnum;

import java.io.Serializable;

/**
 * Description:缓存工具类：
 * @version <1> 2018-10-16 14:54 lijie: Created.
 */
public class CacheUtil implements Serializable{

    /**数据字典类型*/
    public static final String CACHE_DICT_TYPE="CACHE_DICT";
    /**区域类型*/
    public static final String CACHE_REGION_TYPE="CACHE_REGION";
    /**用户类型*/
    public static final String CACHE_ACCOUNT_TYPE="CACHE_ACCOUNT";
    /**转化类型 名称*/
    public static final String CACHE_TRANS_NAME="_NAME_";
    /**编码*/
    public static final String CACHE_TRANS_CODE="_CODE_";
    /**全部缓存上次刷新时间key*/
    public static final String CACHE_LASTTIME_KEY="ALL_LASTTIME";
    /**数据字典缓存上次刷新时间key*/
    public static final String CACHE_DICT_LASTTIME_KEY="DICT_LASTTIME";
    /**区域缓存上次刷新时间key*/
    public static final String CACHE_REGION_LASTTIME_KEY="REGION_LASTTIME";
    /**用户缓存上次刷新时间key*/
    public static final String CACHE_ACCOUNT_LASTTIME_KEY="ACCOUNT_LASTTIME";
    /**
     * 数据字典获取名称key前缀
     */
    public static final String dict_name_key= CacheTypeEnum.CACHE_TYPE_DICT.getType()+ CacheDataEnum.CACHE_DATA_NAME.getType();
    /**
     * 数据字典获取编码key前缀
     */
    public static final String dict_code_key= CacheTypeEnum.CACHE_TYPE_DICT.getType()+ CacheDataEnum.CACHE_DATA_CODE.getType();
    /**
     * 数据字典获取列表key前缀
     */
    public static final String dict_list_key=CacheTypeEnum.CACHE_TYPE_DICT.getType()+ CacheDataEnum.CACHE_DATA_LIST.getType();
    /**
     * 区域获取名称key前缀
     */
    public static final String region_name_key=CacheTypeEnum.CACHE_TYPE_REGION.getType()+ CacheDataEnum.CACHE_DATA_NAME.getType();
    /**
     * 区域获取编码key前缀
     */
    public static final String region_code_key=CacheTypeEnum.CACHE_TYPE_REGION.getType()+ CacheDataEnum.CACHE_DATA_CODE.getType();
    /**
     * 区域获取列表key前缀
     */
    public static final String region_list_key=CacheTypeEnum.CACHE_TYPE_REGION.getType()+ CacheDataEnum.CACHE_DATA_LIST.getType();
    /**
     * 用户获取名称key前缀
     */
    public static final String account_name_key=CacheTypeEnum.CACHE_TYPE_ACCOUNT.getType()+ CacheDataEnum.CACHE_DATA_NAME.getType();
    /**
     * 用户获取对象key前缀
     */
    public static final String account_obj_key=CacheTypeEnum.CACHE_TYPE_ACCOUNT.getType()+ CacheDataEnum.CACHE_DATA_OBJECT.getType();

    /**
     * 获取帖子点赞数、一级评论数key前缀
     */
    public static final String follow_firstComment_object_key=CacheTypeEnum.CACHE_TYPE_FOLLOW_FIRSTCOMMENT.getType()+ CacheDataEnum.CACHE_DATA_OBJECT.getType();

    /**
     * 获取二级评论数key前缀
     */
    public static final String secondComment_object_key=CacheTypeEnum.CACHE_TYPE_SECONDCOMMENT.getType()+ CacheDataEnum.CACHE_DATA_OBJECT.getType();

    /**点赞 名称type*/
    public static final String CACHE_FORUM_TRANS_LIKE="LIKE";

    /**评论 名称type*/
    public static final String CACHE_FORUM_TRANS_COMMENT="COMMENT";

    /**点赞 名称*/
    public static final String CACHE_FOLLOW_FIRSTCOMMENT="CACHE_FOLLOW_FIRSTCOMMENT";

    /**评论 名称*/
    public static final String CACHE_SECONDCOMMENT="CACHE_SECONDCOMMENT";
}
