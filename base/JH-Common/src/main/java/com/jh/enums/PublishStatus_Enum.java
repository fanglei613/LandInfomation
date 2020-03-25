package com.jh.enums;

/**
* 定义数据是否发布
* @version <1> 2018-08-13  cxw : Created.
*/

public enum PublishStatus_Enum {
	PUBLISHED(2201,"已发布"),
	WAIT_PUBLISH(2202,"待发布");

	private Integer code ;
	private String title;

	private PublishStatus_Enum(Integer code, String title){
		this.code = code;
		this.title = title;
	}

	public void setCode(Integer code){ this.code = code;}
	public Integer getCode(){return this.code;}

	public void setTitle(String title){this.title = title;}
	public String getTitle(){return this.title;}
}