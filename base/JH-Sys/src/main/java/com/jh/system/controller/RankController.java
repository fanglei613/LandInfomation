package com.jh.system.controller;

import com.jh.system.model.RankParam;
import com.jh.system.service.IRankService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "地块操作接口")
@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private IRankService rankService;

    /**
     *  @description: 根据父类区域ID查询其下一级区域列表
     *  @version <1> 2018-01-18 cxj:Created.
     */
    @ApiOperation(value="Find Rank Num And Area By RegionID",notes="根据区域ID查询其下一级区域的地块数量和面积")
    @ApiImplicitParam(name = "parentId",value = "区域ID",required = false,paramType="form",dataType = "Long")
    @RequestMapping(value="/rankStatistics",method= RequestMethod.POST)
    public ResultMessage queryRegionRankStatistics(@RequestBody RankParam rankParam){
        return rankService.queryRegionRankStatistics(rankParam.getParentId());
    }

    /*
     * 功能描述: 根据条件查询地块分布的数据时间点
     * @Param:
     * @Return: [rankParam]
     * @version<1>  2019/11/6  wangli :Created
     */
    @ApiOperation(value = "根据条件查询地块分布的数据时间点",notes = "根据条件查询地块分布的数据时间点")
    @ApiImplicitParam(name = "rankParam",value = "rankParam",required = true,dataType = "RankParam")
    @PostMapping("/findRankTimes")
    public ResultMessage findRankTimes(@RequestBody RankParam rankParam){
        return rankService.findRankTimes(rankParam);
    }

}
