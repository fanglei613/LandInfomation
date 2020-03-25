package com.jh.system.controller;

import com.jh.system.entity.RelateDataSetResolution;
import com.jh.system.service.IDataSetAndResolutionService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: 数据集和精度关联Controller
 * 1.
 *
 * @version <1> 2018/8/30 15:41 zhangshen: Created.
 */
@Api(value = "数据集和精度关联",description="数据集和精度关联")
@RestController
@RequestMapping("/dataSetAndResolution")
public class DataSetAndResolutionController {

    @Autowired
    private IDataSetAndResolutionService dataSetAndResolutionService;

    /**
     * Description: 根据数据集id 查询精度列表(数据集精度关联表为主表)
     * @param dataSetId
     * @return 精度列表
     * @version <1> 2018/8/30 15:49 zhangshen: Created.
     */
    @ApiOperation(value="根据数据集id查询精度列表",notes = "根据数据集id查询精度列表")
    @PostMapping("queryResolutionListByDataSetId")
    public ResultMessage queryResolutionListByDataSetId(Integer dataSetId) {
        return dataSetAndResolutionService.queryResolutionListByDataSetId(dataSetId);
    }

    /**
     * 根据数据集id 查询精度列表(数据字典表为主表)
     * @param relateDataSetResolution
     * @return 精度列表
     */
    @ApiOperation(value="根据数据集id查询精度列表",notes = "根据数据集id查询精度列表")
    @RequestMapping(value = "queryResolutionListByDSId",method= RequestMethod.POST)
    public ResultMessage queryResolutionListByDSId(@RequestBody RelateDataSetResolution relateDataSetResolution) {
        return dataSetAndResolutionService.queryResolutionListByDSId(relateDataSetResolution);
    }

    @ApiOperation(value="新增或者修改数据集与精度的绑定关系",notes = "新增或者修改数据集与精度的绑定关系")
    @RequestMapping(value="addDatasetAndResolutionData",method = RequestMethod.POST)
    public ResultMessage addDatasetAndResolutionData(@RequestBody RelateDataSetResolution relateDataSetResolution){
        return dataSetAndResolutionService.addDatasetAndResolutionRelateData(relateDataSetResolution);
    }

    /**
     * Description: 查询数据集精度列表
     * @param
     * @return 数据精度列表
     * @version <1> 2019-03-13  cxw: Created.
     */
    @ApiOperation(value="查询数据集精度列表",notes = "根据数据集id查询精度列表")
    @GetMapping("queryDsResolutionList")
    public ResultMessage queryDsResolutionList() {
        return dataSetAndResolutionService.findDsResolutionList();
    }
}
