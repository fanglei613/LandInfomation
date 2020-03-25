package com.jh.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件的操作
 * @version <1> 2017-05-08 cxj : Created.
 */
public class PropertyUtil {

    public static String getPropertiesForConfig(String key){
        return  getProperties("config.properties").getProperty(key);
    }

    public static String getPropertiesForConfig(String key,String configFileName){
        return  getProperties(configFileName).getProperty(key);
    }

    /**
     * 读取属性文件
     * @version <1> 2017-05-08 cxj : Created.
     */
    public static Properties getProperties(String propFile){
        Properties p = null;
        try{
            InputStream in = PropertyUtil.class.getClassLoader().getResourceAsStream(propFile);
            p = new Properties();
            p.load(in);
        }catch(Exception e){
            e.printStackTrace();
        }
        return p;
    }
}
