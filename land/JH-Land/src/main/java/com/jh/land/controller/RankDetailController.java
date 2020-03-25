package com.jh.land.controller;

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


}
