package com.jh.ttn.controller;

import com.jh.ttn.service.IDsWeatherService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 天气预报服务
 */
@Api(value = "天气预报数据集接口",description = "天气预报数据集接口")
@RestController
@RequestMapping("/weather")
public class DsWeatherController {

    @Autowired
    private IDsWeatherService tempService;

    /*
     * 根据区域查询区域及下一级区域的天气预报数据
     * @param 数据集查询参数
     *   regionId: 区域ID
     * @return ResultMessage
     * @version <1> 2018-11-02 Roach: Created.
     */
    @ApiOperation(value = "根据区域查询区域及下一级区域的天气预报数据", notes = "根据区域查询区域及下一级区域的天气预报数据")
    @GetMapping("/queryRegionAndBeyond")
    public ResultMessage queryRegionAndBeyond(Long regionId){
        return tempService.findWeatherRegionAndBeyond(regionId);
    }

    /*
     * 根据区域查询该区域的天气预报数据
     * @param 数据集查询参数
     *   regionId: 区域ID
     * @return ResultMessage
     * @version <1> 2018-11-02 Roach: Created.
     */
    @ApiOperation(value="根据区域查询该区域的天气预报数据",notes="根据区域查询该区域的天气预报数据" )
    @GetMapping("/queryRegionWeather")
    public ResultMessage queryRegionWeather(Long regionId){
        return tempService.findRegionWeather(regionId);
    }

}
