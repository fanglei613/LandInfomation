package com.jh.enums;

import com.jh.util.CacheUtil;

/**
* 数据缓存类型
* @version <1> 2019-02-12 15:26:35 lijie : Created.
*/

public enum CacheTypeEnum {
	CACHE_TYPE_DICT("CACHE_DICT","字典名称缓存"),
	CACHE_TYPE_REGION("CACHE_REGION","区域缓存"),
	CACHE_TYPE_ACCOUNT("CACHE_ACCOUNT","用户缓存"),
	CACHE_TYPE_FOLLOW_FIRSTCOMMENT(CacheUtil.CACHE_FOLLOW_FIRSTCOMMENT,"点赞数、一级评论数缓存"),
	CACHE_TYPE_SECONDCOMMENT(CacheUtil.CACHE_SECONDCOMMENT,"二级评论数缓存");

	private String type ;
	private String desc;

	private CacheTypeEnum(String type, String desc){
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}