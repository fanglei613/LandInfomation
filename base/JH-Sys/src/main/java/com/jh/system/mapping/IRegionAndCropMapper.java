package com.jh.system.mapping;

import com.jh.system.model.RegionCropParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 区域与作物配置接口
 * @version <1> 2018-1-25 cxw: Created.
 */
@Component
@Mapper
public interface IRegionAndCropMapper {
    /**
     * 根据区域查询作物
     * 在配置区域与作物时显示某一区域对应的所有作物，且该区域已分配的作物复选框勾选
     * regionId 区域ID
     * <1> 2018-1-25 cxw: Created.
     */
   // List<Dict> findCropByRegionId(RelateCropRegion relateCropRegion);

    /**
     *批量新增区域与分辨率关联
     * regionId 区域ID
     * cropId 作物ID
     * <1> 2018-1-24 cxw: Created.
     * <2> 2018-8-7 wl: update.
     */
    public int addRegionAndCropRelateData(List<RegionCropParam> regionCropParamList);

    /**
     *批量删除数据集与分辨率关联
     * regionId 区域ID
     * cropId 作物ID
     * <1> 2018-1-26 cxw: Created.
     */
    public int delRegionAndCropRelateData(List<Long> regionIds);

}
