package com.jh.constant;

import com.jh.util.PropertyUtil;


/**
 * Description: 各配置文件常量定义
 *
 * @version <1> 2018-07-13  lcw : Created.
 * @version <2> 2018-08-22 lcw : 根据devMode.properties的配置来初始化具体的文件
 */
public class PropertiesConstant {


    //开发环境
    public static final String MODE_DEV = "dev";

    //测试环境
    public static final String MODE_TEST = "test";


    public static final String MODE_PRO = "pro";


    //ceph
    public static final String CEPH_CONFIG;

    //geoserver配置文件常量
    public static final  String GEOSERVER_CONFIG;


    //邮件
    public static final  String EMAIL_CONFFIG;


    //短信
    public static final String SMS_CONFIG;


    //redis
    public static final String REDIS_CONFIG;

    //postgis
    public static final String POSTGIS_CONFIG;

    static {
        String _str = "";
        String devMode = PropertyUtil.getPropertiesForConfig("devMode","config/devMode.properties");
        System.out.println("加载config/" + devMode + "的配置文件");
        if(PropertiesConstant.MODE_DEV.equals(devMode)){
            _str = "config/dev/";
        }else if(PropertiesConstant.MODE_TEST.equals(devMode)){
            _str = "config/test/";
        }else if(PropertiesConstant.MODE_PRO.equals(devMode)){
            _str = "config/pro/";
        }

        CEPH_CONFIG = _str + "config_ceph.properties";
        GEOSERVER_CONFIG = _str + "config_geoserver.properties";
        EMAIL_CONFFIG = _str + "config_email.properties";
        SMS_CONFIG = _str + "config_sms.properties";
        REDIS_CONFIG = _str + "config_redis.properties";

        POSTGIS_CONFIG = _str + "config_postgis.properties";
    }

}
