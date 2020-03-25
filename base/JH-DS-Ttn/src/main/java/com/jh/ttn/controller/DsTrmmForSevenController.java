package com.jh.ttn.controller;

import com.jh.ttn.service.IDsTrmmForSevenService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:7天降雨数据集服务:
 * 1.根据区域，日期构造7天降雨数据查询服务(91期货)
 * @version <1> 2018-06-12 cxw: Created.
 */
@Api(value="7天降雨数据集接口",description="7天降雨数据集接口")
@RestController
@RequestMapping("/sevenTrmm")
public class DsTrmmForSevenController {

    @Autowired
    private IDsTrmmForSevenService trmmForSevenService;
    /*
    * 根据区域，日期构造7天降雨数据查询服务
     * @param 数据集查询参数
     *   regionId: 区域ID
     *       date: 日期
    * @return ResultMessage
    * @version <1> 2018-09-03 cxw: Created.
    */
    @ApiOperation(value = "查询指定时间段降雨同比数据", notes = "根据区域、时间段查询某一时间段内降雨的近三年和10年均值数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3100000000"),
            @ApiImplicitParam(name = "date",value = "日期",required = true, dataType = "String" ,paramType="query",defaultValue="2018-05-20"),
    })
    @GetMapping("/trmmForSeven")
    public ResultMessage queryTrmmForSeven(Long[] regionId, String date){
        return  trmmForSevenService.findTrmmForSeven(regionId,date);
    }
}
