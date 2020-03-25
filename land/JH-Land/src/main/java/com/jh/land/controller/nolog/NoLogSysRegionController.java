package com.jh.land.controller.nolog;

import com.jh.land.entity.InitRegion;
import com.jh.land.model.DictParam;
import com.jh.land.service.ISysRegionService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 区域管理接口
 * Created by XZG on 2018/5/7.
 */
@RestController
@RequestMapping("/nolog/region")
public class NoLogSysRegionController {


    @Autowired
    private ISysRegionService regionService;


    /**
     * 根据区域中文名 模糊查询
     *
     * @param chinaName
     * @return
     */
    @RequestMapping(value = "/queryRegionByChinaName", method = RequestMethod.GET)
    public ResultMessage queryRegionLike(String chinaName) {
        return regionService.findRegionByChinaName(chinaName);
    }

    /**
     * 根据父类区域ID查询其下一级区域列表
     *
     * @version <1> 2017-11-07 Hayden:Created.
     */
    @ApiOperation(value = "Find Region List for Parent Region", notes = "根据父类区域ID查询其下一级区域列表")
    @ApiImplicitParam(name = "parentId", value = "父级区域ID", required = false, paramType = "form", dataType = "Long")
    @RequestMapping(value = "/regionList", method = RequestMethod.GET)
    public ResultMessage queryRegionList(Long parentId) {
        List<InitRegion> list = regionService.findRegionsByParentId(parentId);
        return ResultMessage.success("success", "1", list);
    }

    /**
     * @param regionId 查询单个区域的信息
     * @return ResultMessage
     * @version <1> 2018-08-27 wl： Created.
     */
    @ApiOperation(value = "queryRegionById", notes = "查询单个区域的信息")
    @PostMapping("/queryRegionById")
    public ResultMessage queryRegionById(Long regionId) {
        return regionService.queryInitRegionByInitRegionId(regionId);
    }

    /**
     * @param map 查询区域
     * @return ResultMessage
     * @version <1> 2018-08-27 wl： Created.
     */
    @ApiOperation(value = "findRegion", notes = "查询区域信息")
    @PostMapping("/findRegion")
    public ResultMessage findRegion(@RequestBody Map<String, Object> map) {
        return regionService.findRegion(map);
    }

    /**
     * 根据regionId查询该regionId的家族祖先信息
     *
     * @param regionId
     * @return
     * @version<1> 2018-10-14 lcw :Created.
     */
    @ApiImplicitParam(name = "regionId", value = "区域ID", required = true, dataType = "Long")
    @PostMapping("queryRegionFamily")
    public ResultMessage queryRegionFamily(@RequestParam Long regionId) {

        return regionService.findRegionFamily(regionId);
    }

    /**
     * 根据区域id查询此区域下所有子孙元素,app用
     *
     * @param
     * @return
     * @version <1> 2019/4/13 15:46 zhangshen:Created.
     */
    @ApiOperation(value = "根据区域id查询此区域下所有子孙元素", notes = "根据区域id查询此区域下所有子孙元素")
    /*@ApiImplicitParam(name = "regionId",value = "区域ID",required = true,dataType = "Long")*/
    @PostMapping("queryRegionAppReturn")
    public ResultMessage queryRegionAppReturn(@RequestParam Long regionId) {
        return regionService.queryRegionAppReturn(regionId);
    }

    /**
     * @description: 根据父类区域ID查询其下一级包含地块的区域列表
     */
    @ApiOperation(value = "Find Region List for Parent Region By Block", notes = "根据父类区域ID查询其下一级包含地块的区域列表")
    @ApiImplicitParam(name = "parentId", value = "父级区域ID", required = false, paramType = "form", dataType = "Long")
    @RequestMapping(value = "/blockRegionList", method = RequestMethod.POST)
    public ResultMessage queryBlockRegionList(@RequestBody(required = false) DictParam dictParam) {
        return regionService.findBlockRegionListByParentId(dictParam.getParentId(), dictParam.getLevel());
    }
}
