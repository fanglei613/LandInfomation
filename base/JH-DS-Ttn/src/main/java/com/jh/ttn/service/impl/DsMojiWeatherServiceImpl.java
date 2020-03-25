package com.jh.ttn.service.impl;


import com.jh.constant.SysConstant;
import com.jh.ttn.service.IDsMojiWeatherService;
import com.jh.ttn.util.RequestApiUtil;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.sf.json.JSONObject;

@Service
@Transactional
public class DsMojiWeatherServiceImpl implements IDsMojiWeatherService {


    /**
     * @Description: 墨迹天气24小时天气预报
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id, token 接口访问授权码
     * @return: 24小时天气预报
     * @throws:
     */
    @Override
    public ResultMessage forecast24hours(String cityId) {
        String apiPath = PropertyUtil.getPropertiesForConfig("Moji_Weather_url_Forecast24hours");//api接口的路径
        String token = PropertyUtil.getPropertiesForConfig("Moji_Weather_token_Forecast24hours");//api接口的token，每个接口对应一个唯一的token
        String resultStr = RequestApiUtil.getResultFromApi(cityId,apiPath,token);
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (StringUtils.isNotEmpty(resultStr)){
            return ResultMessage.success("获取24小时天气预报成功",resultJson);
        }
        return ResultMessage.fail("获取24小时天气预报失败");
    }

    /**
     * @Description: 墨迹天气AQI预报5天
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id, token 接口访问授权码
     * @return: AQI预报5天
     * @throws:
     */
    @Override
    public ResultMessage aqiforecast5days(String cityId) {
        String apiPath = PropertyUtil.getPropertiesForConfig("Moji_Weather_url_Aqiforecast5days");//api接口的路径
        String token = PropertyUtil.getPropertiesForConfig("Moji_Weather_token_Aqiforecast5days");//api接口的token，每个接口对应一个唯一的token
        String resultStr = RequestApiUtil.getResultFromApi(cityId,apiPath,token);
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (StringUtils.isNotEmpty(resultStr)){
            return ResultMessage.success("获取AQI预报5天成功",resultJson);
        }
        return ResultMessage.fail("获取AQI预报5天失败");
    }

    /**
     * @Description: 墨迹天气15天天气预报
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id, token 接口访问授权码
     * @return: 15天天气预报
     * @throws:
     */
    @Override
    public ResultMessage forecast15days(String cityId) {
        String apiPath = PropertyUtil.getPropertiesForConfig("Moji_Weather_url_Forecast15days");//api接口的路径
        String token = PropertyUtil.getPropertiesForConfig("Moji_Weather_token_Forecast15days");//api接口的token，每个接口对应一个唯一的token
        String resultStr = RequestApiUtil.getResultFromApi(cityId,apiPath,token);
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (StringUtils.isNotEmpty(resultStr)){
            return ResultMessage.success("获取15天天气预报成功",resultJson);
        }
        return ResultMessage.fail("获取15天天气预报失败");
    }

    /**
     * @Description: 墨迹天气天气实况
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id, token 接口访问授权码
     * @return: 天气实况
     * @throws:
     */
    @Override
    public ResultMessage condition(String cityId) {
        String apiPath = PropertyUtil.getPropertiesForConfig("Moji_Weather_url_condition");//api接口的路径
        String token = PropertyUtil.getPropertiesForConfig("Moji_Weather_token_condition");//api接口的token，每个接口对应一个唯一的token
        String resultStr = RequestApiUtil.getResultFromApi(cityId,apiPath,token);
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (StringUtils.isNotEmpty(resultStr)){
            return ResultMessage.success("获取天气实况成功",resultJson);
        }
        return ResultMessage.fail("获取天气实况失败");
    }

    /**
     * @Description: 墨迹天气生活指数
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id, token 接口访问授权码
     * @return: 生活指数
     * @throws:
     */
    @Override
    public ResultMessage index(String cityId) {
        String apiPath = PropertyUtil.getPropertiesForConfig("Moji_Weather_url_index");//api接口的路径
        String token = PropertyUtil.getPropertiesForConfig("Moji_Weather_token_index");//api接口的token，每个接口对应一个唯一的token
        String resultStr = RequestApiUtil.getResultFromApi(cityId,apiPath,token);
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (StringUtils.isNotEmpty(resultStr)){
            return ResultMessage.success("获取天气生活指数成功",resultJson);
        }
        return ResultMessage.fail("获取天气生活指数失败");
    }

    /**
     * @Description: 墨迹天气天气预警
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id, token 接口访问授权码
     * @return: 天气预警
     * @throws:
     */
    @Override
    public ResultMessage alert(String cityId) {
        String apiPath = PropertyUtil.getPropertiesForConfig("Moji_Weather_url_alert");//api接口的路径
        String token = PropertyUtil.getPropertiesForConfig("Moji_Weather_token_alert");//api接口的token，每个接口对应一个唯一的token
        String resultStr = RequestApiUtil.getResultFromApi(cityId,apiPath,token);
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (StringUtils.isNotEmpty(resultStr)){
            return ResultMessage.success("获取天气预警成功",resultJson);
        }
        return ResultMessage.fail("获取天气预警失败");
    }
}
