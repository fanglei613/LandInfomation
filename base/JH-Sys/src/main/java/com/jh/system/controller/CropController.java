package com.jh.system.controller;

import com.jh.system.service.impl.CropService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加载作物接口
 * @version <1> 2017-12-06 cxw : Created.
 */
@Api(value = "crop Service Interface",description="作物操作接口")
@RestController
@RequestMapping("/nolog/crop")
public class CropController {

    private static Logger logger = Logger.getLogger(CropController.class);

    @Autowired
    private CropService cropService;

    /**
     * 根据区域加载作物
     * @param regionId : 区域ID
     * @return
     * @version <1> 2017-12-06 cxw : Created.
     */
    @ApiOperation(value="查询作物列表",notes="根据区域查询作物列表" )
    @RequestMapping(value="/queryCropList", method= RequestMethod.GET)
    public ResultMessage queryCropList(@RequestParam Long regionId)
    {
        return cropService.findCropList(regionId);
    }
}
