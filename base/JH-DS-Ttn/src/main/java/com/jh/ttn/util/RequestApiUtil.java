package com.jh.ttn.util;


import com.jh.constant.SysConstant;
import com.jh.util.PropertyUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:  调用第三方API工具类
 * @Author: huxiaoqiang
 * @Date: 2018/9/13 14:20
*/
public class RequestApiUtil {

    /**
     * @Description:调用墨迹天气工具类
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 16:51
     * @param   * @param cityId 城市id
     * @param apiPath 接口路径
     * @param token 接口授权码
     * @return: 结果字符串
     * @throws:
     */
    public static String getResultFromApi(String cityId,String apiPath,String token){
        String path = apiPath;
        String method = "POST";
        String host = PropertyUtil.getPropertiesForConfig("Moji_Weather_baseUrl");
        String appcode = PropertyUtil.getPropertiesForConfig("Moji_Weather_appcode");

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("cityId", cityId);
        bodys.put("token", token);
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
