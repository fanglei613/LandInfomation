package com.jh.system.model;

import java.util.List;

/**
 * Created by pc026 on 2018/8/7.
 */
public class RegionCropParam {
    private List<Integer> cropsId;
    private List<Long> regionIds;//简报编号集合，用于批量简报批量发布的状态设置
    private List<String> regionCodes;//选中区域的code
    private Integer cropId;//设置单个作物
    private Long regionId;//设置单个区域
    private Integer yesOrNot;//是否应用下级  1 应用于下级  0 不应用

    public List<Integer> getCropsId() {
        return cropsId;
    }

    public void setCropsId(List<Integer> cropsId) {
        this.cropsId = cropsId;
    }

    public List<Long> getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(List<Long> regionIds) {
        this.regionIds = regionIds;
    }

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

    public List<String> getRegionCodes() {
        return regionCodes;
    }

    public void setRegionCodes(List<String> regionCodes) {
        this.regionCodes = regionCodes;
    }

    public Integer getYesOrNot() {
        return yesOrNot;
    }

    public void setYesOrNot(Integer yesOrNot) {
        this.yesOrNot = yesOrNot;
    }
}
