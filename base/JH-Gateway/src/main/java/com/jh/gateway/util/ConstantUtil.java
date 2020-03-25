package com.jh.gateway.util;

/**
* 常量定义
* @version <1> 2018-04-26 15:23:05 Hayden : Created.
*/
public class ConstantUtil{
	//定义过滤器过滤状态的键
	public static String Key_Zuul_Flag = "ZuulFilterFlag";
	//定义URL地址中包含特定路径的键
	public static String Key_Url_NoLog = "nolog";
	//定义URL地址中包含特定路径的键    不需要经过权限过滤器
	public static String Key_Url_NoProduct = "noproduct";
	//定义URL地址中包含特定路径的键    不需要经过权限过滤器
	public static String Key_Url_NoReoprt = "dsReport";
	//查询作物列表不需要经过过滤器
	public static String Key_Url_NoCrop = "cropDataTypeListForDsRegion";


}