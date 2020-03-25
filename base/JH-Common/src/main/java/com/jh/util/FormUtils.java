package com.jh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
* @description:
* 字段验证类,用于验证如邮箱、手机号等
*
* @version <1> 2017-10-18 cxj : Created.
*/
public class FormUtils {
   /**
    * 手机号验证
    * @param str
    * @return
    */
   public static boolean isMobile(final String str) {
       Pattern p = p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
       Matcher m = p.matcher(str);
       return m.matches();
   }

   /**
    * 邮箱验证
    * @param emaile
    * @return
    */
   public static boolean isEmaile(String emaile){
       String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
       Pattern p = Pattern.compile(RULE_EMAIL);
       Matcher m = p.matcher(emaile);
       return m.matches();
   }
}
