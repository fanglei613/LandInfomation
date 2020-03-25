package com.jh.system.controller;

import com.jh.system.base.controller.BaseController;
import com.jh.system.entity.RelateCropRegion;
import com.jh.system.model.RegionCropParam;
import com.jh.system.service.IRegionAndCropService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 区域与作物配置接口
 * @version <1> 2018-1-25 cxw: Created.
 */
@Api(value = "区域与作物配置接口",description="区域与作物配置接口")
@RestController
@RequestMapping("/regionAndCrop")
public class RegionAndCropController extends BaseController {

    @Autowired
    private IRegionAndCropService regionAndCropService;


    /**
     * 根据区域查询作物
     * 在配置区域与作物时显示某一区域对应的所有作物，且该区域已分配的作物复选框勾选
     * regionId 区域ID
     * <1> 2018-1-25 cxw: Created.
     */
    @ApiOperation(value="根据区域查询作物",notes = "在配置区域与作物时显示某一数据集对应的所有作物，且该区域已分配的作物复选框勾选")
    @RequestMapping(value = "queryCropByRegionId",method = RequestMethod.POST)
    public ResultMessage queryCropByRegionId(@RequestBody RelateCropRegion relateCropRegion)
    {
        return regionAndCropService.findCropByRegionId(relateCropRegion);
    }

    /**
     * 根据区域查询作物
     * 在配置区域与作物时显示某一区域对应的所有作物，且该区域已分配的作物复选框勾选
     * regionId 区域ID
     * <1> 2018-8-13 cxw: Created.
     */
    @ApiOperation(value="根据区域查询作物",notes = "根据区域查询作物")
    @GetMapping("queryCropListByRegionId")
    public ResultMessage queryCropListByRegionId(Long regionId)
    {
        RelateCropRegion relateCropRegion = new RelateCropRegion();
        if(regionId!=null) {
            relateCropRegion.setRegionId(regionId);
            relateCropRegion.setParentIds(500);
        }
        else{
            return  ResultMessage.fail("区域不能为空");
        }
        return regionAndCropService.findCropByRegionId(relateCropRegion);
    }

    /**
     *批量新增数据集与分辨率关联
     * regionId 区域ID
     * cropId 作物ID
     * <1> 2018-1-24 cxw: Created.
     */
    @ApiOperation(value="批量新增区域与作物关联",notes = "批量新增区域与作物关联")
    @RequestMapping(value = "addRegionAndCropRelateData",method = RequestMethod.POST)
    public ResultMessage addRegionAndCropRelateData(@RequestBody RegionCropParam regionCropParam)
    {
        return regionAndCropService.addRegionAndCropRelateData(regionCropParam);
    }
}
