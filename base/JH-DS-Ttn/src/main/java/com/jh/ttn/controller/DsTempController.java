package com.jh.ttn.controller;

import com.jh.ttn.service.IDsTempService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 气温数据集服务
 */
@Api(value = "气温数据集接口",description = "气温数据集接口")
@RestController
@RequestMapping("/temp")
public class DsTempController {

    @Autowired
    private IDsTempService tempService;

    /*
     * 根据区域，日期构造某一时间段内气温的近三年均值数据查询服务
     * @param 数据集查询参数
     *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
     * @return ResultMessage
     * @version <1> 2018-10-16 huxiaoqiang: Created.
     */
    @ApiOperation(value = "查询指定时间段气温同比数据", notes = "根据区域、时间段查询某一时间段内气温的近三年均值数据")
    @GetMapping("/queryTempForChart")
    public ResultMessage queryTempForChart(Long regionId, String startDate, String endDate){
        return tempService.findTempForThree(regionId,startDate,endDate);
    }

    /*
     * 根据区域，日期构造气温环比查询服务
     * @param 数据集查询参数
     *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
     * @return ResultMessage
     * @version <1> 2018-10-16 huxiaoqiang: Created.
     */
    @ApiOperation(value="查询气温指定日期环比数据",notes="根据区域、日期段查询区域及下一级区域在日期段内的同期环比气温数据" )
    @GetMapping("/tempForMon")
    public ResultMessage queryTempForMon(Long regionId,String startDate,String endDate){
        return tempService.findTempForMon(regionId,startDate,endDate);
    }

    /*
     * 根据区域，日期构造气温报告生成服务
     * @param 数据集查询参数
     *   regionId: 区域ID
     *   chinaName:区域中文名
     *  startDate: 开始日期
     *    endDate: 结束日期
     * @return ResultMessage
     * @version <1> 2018-10-16 huxiaoqiang: Created.
     */
    @ApiOperation(value="构造气温报告生成服务",notes="根据区域、日期段查询区域及下一级区域在日期段内的气温报告" )
    @GetMapping("/tempForReport")
    public ResultMessage queryTempForReport(String chinaName,Long regionId, String startDate, String endDate ){
        return tempService.findTempForReport(chinaName,regionId,startDate,endDate);
    }

    /**
     * 查询开始和结束日期时间段内有气温数据的所有日期
     * @param regionId	区域id
     * @param startDate	开始日期
     * @param endDate	结束日期
     * @return
     */
    @ApiOperation(value = "查询开始和结束日期时间段内有气温数据的所有日期",notes = "查询开始和结束日期时间段内有气温数据的所有日期")
    @GetMapping("/queryTempTimes")
    public ResultMessage queryTempTimes(Long regionId,String startDate,String endDate){
        return tempService.findTempTimes(regionId,startDate,endDate);
    }

    /**
     * 查询开始和结束日期时间段内所有气温数据
     * @param regionId	区域id
     * @param startDate	开始日期
     * @param endDate	结束日期
     * @return
     * @version <1> 2019-03-20 cxw: Created.
     */
    @ApiOperation(value = "查询开始和结束日期时间段内所有气温数据",notes = "查询开始和结束日期时间段内所有气温数据")
    @GetMapping("/queryAllTemp")
    public ResultMessage queryAllTemp(Long regionId,String startDate,String endDate){
        return tempService.findAllTemp(regionId,startDate,endDate);
    }
}
