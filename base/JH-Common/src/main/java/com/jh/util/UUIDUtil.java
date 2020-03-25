package com.jh.util;

import java.util.UUID;

/**
* 用于生成UUID
* @version <1> 2018-05-08 15:17:39 Hayden : Created.
*/
public class UUIDUtil{
	public static String generateUUID(){
		return MD5Util.digest(UUID.randomUUID().toString().replaceAll("-", ""));
	}
}