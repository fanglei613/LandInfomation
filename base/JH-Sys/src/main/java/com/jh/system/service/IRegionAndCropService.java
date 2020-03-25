package com.jh.system.service;


import com.jh.system.entity.RelateCropRegion;
import com.jh.system.model.RegionCropParam;
import com.jh.vo.ResultMessage;

/**
 * 区域与作物配置接口
 * @version <1> 2018-1-25 cxw: Created.
 */
public interface IRegionAndCropService {

    /**
     * 根据区域查询作物
     * 在配置区域与作物时显示某一区域对应的所有作物，且该区域已分配的作物复选框勾选
     * regionId 区域ID
     * <1> 2018-1-25 cxw: Created.
     */
    ResultMessage findCropByRegionId(RelateCropRegion relateCropRegion);

    /**
     *批量新增数据集与分辨率关联
     * regionId 区域ID
     * cropId 作物ID
     * <1> 2018-1-24 cxw: Created.
     */
    ResultMessage addRegionAndCropRelateData(RegionCropParam regionCropParam);
}
