package com.jh.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 
 *
 *@version <1> 2016-10-14-Hayden:Created.
 */
public class MD5Util {

	public static String digest(String s)
	{
		char hexDigits[] =
		{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		byte[] strTemp = s.getBytes();
		MessageDigest mdTemp;
		byte[] md = null;
		try
		{
			mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			md = mdTemp.digest();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++)
		{
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}
	
	public static void main(String[] args) {
		//System.out.println(getDecodeMessage("%E9%99%88%E9%92%A2"));
		System.out.println(digest("Yc201712"));
	}
}
