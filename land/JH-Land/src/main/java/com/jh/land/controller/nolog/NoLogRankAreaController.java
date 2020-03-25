package com.jh.land.controller.nolog;

import com.jh.biz.controller.BaseController;
import com.jh.land.model.AreaVO;
import com.jh.land.model.RankAreaVO;
import com.jh.land.service.IRankAreaService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "地块数据接口",description = "地块数据接口")
@RestController
@RequestMapping("/nolog/rankArea")
public class NoLogRankAreaController extends BaseController {

    @Autowired
    private IRankAreaService rankAreaService;

    /*
     * 功能描述: 查询地块数据集分布时间点集合
     * @Param:
     * @Return:
     * @version<1>  2019/11/21  wangli :Created
     */
    @ApiOperation(value = "根据条件查询分布数据的时间点集合",notes = "根据条件查询分布数据的时间点集合")
    @ApiImplicitParam(name = "areaVO",value = "分布VO",required = true,dataType = "AreaVO")
    @PostMapping("/findRankAreaTimes")
    public ResultMessage findRankAreaTimes(@RequestBody RankAreaVO rankAreaVO){
        return rankAreaService.findAreaTimes(rankAreaVO);
    }


    /*
     * 功能描述:
     * @Param:
     * @Return:
     * @version<1>  2019/11/21  wangli :Created
     */
    @ApiOperation(value = "查询选中区域及其下级区域某一期的分布数据",notes = "查询选中区域及其下级区域某一期的分布数据")
    @ApiImplicitParam(name = "areaVO",value = "分布VO",required = true,dataType = "AreaVO")
    @PostMapping("/findRankAndBeyondArea")
    public ResultMessage findRankAndBeyondArea(@RequestBody AreaVO areaVO) {
        return rankAreaService.findRankAndBeyondArea(areaVO);
    }

    /*
     * 功能描述:
     * @Param:
     * @Return:
     * @version<1>  2019/11/21  wangli :Created
     */
    @ApiOperation(value = "根据条件查询某个区域及其下级区域三年的分布数据",notes = "根据条件查询某个区域及其下级区域三年的分布数据")
    @ApiImplicitParam(name = "areaVO",value = "分布VO",required = true,dataType = "AreaVO")
    @PostMapping("/findThreeYearsArea")
    public ResultMessage findThreeYearsArea(@RequestBody AreaVO areaVO) {
        return rankAreaService.findThreeYearsArea(areaVO);
    }

    /**
     * 功能描述:
     * @Param:
     * @Return:
     * @version<1>  2019/11/21  wangli :Created
     */
    @ApiOperation(value = "查询选中区域及其下级区域某一期的分布数据及对应的当年的估产数据"
            ,notes = "查询选中区域及其下级区域某一期的分布数据及对应的当年的估产数据")
    @ApiImplicitParam(name = "areaVO",value = "分布VO",required = true,dataType = "AreaVO")
    @PostMapping("/findRegionAndBeyondAreaAndYield")
    public ResultMessage findRegionAndBeyondAreaAndYield(@RequestBody AreaVO areaVO) {
        return rankAreaService.findRegionAndBeyondAreaAndYield(areaVO);
    }

    @ApiOperation(value = "统计分布时间轴",notes = "统计分布时间轴")
    @PostMapping("/totalRankAreaTimeStatistics")
    public ResultMessage totalRankAreaTimeStatistics(){
        return  rankAreaService.totalRankAreaTimeStatistics();
    }

}
