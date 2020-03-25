package com.jh.system.entity;

import java.util.List;

/**
 * Description: 数据集精度关联表
 * 1.
 *
 * @version <1> 2018/8/30 15:44 zhangshen: Created.
 */
public class RelateDataSetResolution {

    private Integer dataSetId; //数据集id
    private Integer resolutionId;//精度id
    private Integer parentIds;
    private String dataSetName;
    private String resolutionName;
    private List<Integer> resolutionIds;

    public Integer getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(Integer dataSetId) {
        this.dataSetId = dataSetId;
    }

    public Integer getResolutionId() {
        return resolutionId;
    }

    public void setResolutionId(Integer resolutionId) {
        this.resolutionId = resolutionId;
    }

    public Integer getParentIds() { return parentIds; }

    public void setParentIds(Integer parentIds) { this.parentIds = parentIds; }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getResolutionName() {
        return resolutionName;
    }

    public void setResolutionName(String resolutionName) {
        this.resolutionName = resolutionName;
    }

    public List<Integer> getResolutionIds() { return resolutionIds; }

    public void setResolutionIds(List<Integer> resolutionIds) { this.resolutionIds = resolutionIds; }
}
