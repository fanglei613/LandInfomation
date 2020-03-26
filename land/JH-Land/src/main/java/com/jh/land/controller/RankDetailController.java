package com.jh.land.controller;

import com.jh.land.entity.RankDetail;
import com.jh.land.service.IRankDetailService;
import com.jh.land.service.IRankInfoService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

@RestController
@RequestMapping("/rankDetail")
public class RankDetailController {

    @Autowired
    private IRankDetailService rankDetailService;
    /*
     * 功能描述:地块基础信息
     * @Param:
     * @Return:
     * @version<1>  2020/3/23  wangli :Created
     */
    @ApiOperation(value="",notes="根据地块id查询地块详情的基本信息")
    @ApiImplicitParam(name = "rankId",value = "地块ID",required = false,paramType="form",dataType = "Long")
    @RequestMapping(value="/rankDetailForBase",method= RequestMethod.GET)
    public ResultMessage rankDetailForBase(Long rankId){
        return  rankDetailService.rankDetailForBase(rankId);
    }

    @ApiOperation(value="",notes="根据地块id和年份查询地块的概况")
    @ApiImplicitParam(name = "rankDetail",value = "地块详情主体",required = false,paramType="form",dataType = "RankDetail")
    @RequestMapping(value="/rankDetailForBasicFacts",method= RequestMethod.GET)
    public ResultMessage rankDetailForBasicFacts(@RequestBody RankDetail rankDetail){
        return  rankDetailService.rankDetailForBasicFacts(rankDetail);
    }

    @ApiOperation(value="",notes="根据地块id和年份、月份查询长势信息")
    @ApiImplicitParam(name = "rankDetail",value = "地块详情主体",required = false,paramType="form",dataType = "RankDetail")
    @RequestMapping(value="/rankDetailForGrowth",method= RequestMethod.GET)
    public ResultMessage rankDetailForGrowth(@RequestBody RankDetail rankDetail){
        return  rankDetailService.rankDetailForGrowth(rankDetail);
    }

    @ApiOperation(value="",notes="根据地块id和年份、月份查询气象信息")
    @ApiImplicitParam(name = "rankDetail",value = "地块详情主体",required = false,paramType="form",dataType = "RankDetail")
    @RequestMapping(value="/rankDetailForMeteorologicalPhenomena",method= RequestMethod.GET)
    public ResultMessage rankDetailForMeteorologicalPhenomena(@RequestBody RankDetail rankDetail){
        return  rankDetailService.rankDetailForMeteorologicalPhenomena(rankDetail);
    }
    public static void main(String[] args){
        LocalDate date = LocalDate.parse("2019-08-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate first = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = date.with(TemporalAdjusters.lastDayOfMonth());
        long days = first.until(last, ChronoUnit.DAYS);
        System.out.println(days);
        System.out.println(first.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println(last.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

}
