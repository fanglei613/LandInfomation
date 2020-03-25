package com.jh.land.controller;

import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rankDetail")
public class RankDetailController {
    /*
     * 功能描述:地块概览
     * @Param:
     * @Return:
     * @version<1>  2020/3/23  wangli :Created
     */
    /**
     *  @description: 根据父类区域ID查询其下一级区域列表
     *  @version <1> 2018-01-18 cxj:Created.
     */
    @ApiOperation(value="",notes="根据地块id查询地块详情的基本信息")
    @ApiImplicitParam(name = "rankId",value = "地块ID",required = false,paramType="form",dataType = "Long")
    @RequestMapping(value="/rankDetailForBase",method= RequestMethod.GET)
    public ResultMessage rankDetailForBase(Long rankId){

        //
        return null;
    }


}
