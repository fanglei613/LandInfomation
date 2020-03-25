package com.jh.ttn.service;


import com.jh.vo.ResultMessage;

public interface IDsMojiWeatherService {

    /**
     * @Description: 墨迹天气24小时天气预报
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id, token 接口访问授权码
     * @return: 24小时天气预报
     * @throws:
     */
    ResultMessage forecast24hours(String cityId);

    /**
     * @Description: 墨迹天气AQI预报5天
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id, token 接口访问授权码
     * @return: AQI预报5天
     * @throws:
     */
    ResultMessage aqiforecast5days(String cityId);

    /**
     * @Description: 墨迹天气15天天气预报
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id, token 接口访问授权码
     * @return: 15天天气预报
     * @throws:
     */
    ResultMessage forecast15days(String cityId);

    /**
     * @Description: 墨迹天气天气实况
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id, token 接口访问授权码
     * @return: 天气实况
     * @throws:
     */
    ResultMessage condition(String cityId);

    /**
     * @Description: 墨迹天气生活指数
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id, token 接口访问授权码
     * @return: 生活指数
     * @throws:
     */
    ResultMessage index(String cityId);

    /**
     * @Description: 墨迹天气天气预警
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id, token 接口访问授权码
     * @return: 天气预警
     * @throws:
     */
    ResultMessage alert(String cityId);

}
