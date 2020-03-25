package com.jh.ttn.controller;


import com.jh.ttn.service.IDsMojiWeatherService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "墨迹天气接口",description = "墨迹天气接口")
@RestController
@RequestMapping("/nolog/mojiWeather")
public class DsMojiWeatherController {

    @Autowired
    private IDsMojiWeatherService mojiWeatherService;

    @ApiOperation(value = "查询墨迹天气24小时天气预报",notes = "根据cityId，即城市id编号查询该地区24小时天气预报")
    @GetMapping("/forecast24hours")
    public ResultMessage forecast24hours(String cityId){
        return mojiWeatherService.forecast24hours(cityId);
    }

    @ApiOperation(value = "查询墨迹天气AQI预报5天",notes = "根据cityId，即城市id编号查询该地区AQI预报5天")
    @GetMapping("/aqiforecast5days")
    public ResultMessage aqiforecast5days(String cityId){
        return mojiWeatherService.aqiforecast5days(cityId);
    }

    @ApiOperation(value = "查询墨迹天气15天天气预报",notes = "根据cityId，即城市id编号查询该地区15天天气预报")
    @GetMapping("/forecast15days")
    public ResultMessage forecast15days(String cityId){
        return mojiWeatherService.forecast15days(cityId);
    }

    @ApiOperation(value = "查询墨迹天气天气实况",notes = "根据cityId，即城市id编号查询该地区天气实况")
    @GetMapping("/condition")
    public ResultMessage condition(String cityId){
        return mojiWeatherService.condition(cityId);
    }

    @ApiOperation(value = "查询墨迹天气生活指数",notes = "根据cityId，即城市id编号查询该地区生活指数")
    @GetMapping("/index")
    public ResultMessage index(String cityId){
        return mojiWeatherService.index(cityId);
    }

    @ApiOperation(value = "查询墨迹天气天气预警",notes = "根据cityId，即城市id编号查询该地区天气预警")
    @GetMapping("/alert")
    public ResultMessage alert(String cityId){
        return mojiWeatherService.alert(cityId);
    }

}
