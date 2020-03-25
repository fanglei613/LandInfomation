package com.jh.system.entity;


/**
 * 区域与作物关联实体，继承数据字典类获取数据代码字段参数
 * @version <1> 2018-1-25 cxw: Created.
 */
public class RelateCropRegion extends Dict {

    private Integer cropId; //作物ID
    private Long regionId;//区域ID
    private Integer parentIds;//dictId

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer getParentIds() {
        return parentIds;
    }

    public void setParentIds(Integer parentIds) {
        this.parentIds = parentIds;
    }
}
