package com.jh.enums;

/**
* 定义数据是否有效: '1' 有效 '0' 无效
* @version <1> 2018-05-08 15:26:35 Hayden : Created.
*/

public enum DataStatusEnum{
	Valid("1","有效"),
	Invalid("0","无效");

	private String code ;
	private String title;

	private DataStatusEnum(String code,String title){
		this.code = code;
		this.title = title;
	}

	public void setCode(String code){ this.code = code;}
	public String getCode(){return this.code;}

	public void setTitle(String title){this.title = title;}
	public String getTitle(){return this.title;}
}