package com.jh.enums;

/**
* 缓存数据类型
* @version <1> 2019-02-12 15:26:35 lijie : Created.
*/

public enum CacheDataEnum {
	CACHE_DATA_NAME("_NAME_","缓存值为名称字"),
	CACHE_DATA_CODE("_CODE_","缓存值为编码"),
	CACHE_DATA_OBJECT("_OBJECT_","缓存值为json对象"),
	CACHE_DATA_LIST("_LIST_","缓存值为json列表");

	private String type ;
	private String desc;

	CacheDataEnum(String type, String desc){
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