package com.jh.enums;

/**
* 定义数据是否删除: '1' 正常 '0' 删除
* @version <1> 2018-05-08 15:26:35 Hayden : Created.
*/

public enum DelStatusEnum{
	Normal("1","正常"),
	Delete("0","删除");

	private String code ;
	private String title;

	private DelStatusEnum(String code,String title){
		this.code = code;
		this.title = title;
	}

	public void setCode(String code){ this.code = code;}
	public String getCode(){return this.code;}

	public void setTitle(String title){this.title = title;}
	public String getTitle(){return this.title;}
}