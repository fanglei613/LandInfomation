package com.jh.land.model;

public class RankAreaParam {

    //所属区域id
    private Long regionId;

    //作物id
    private Integer cropId;

    //精度
    private Integer resolution;

    //地块分布表表名
    private String rankAreaTableName;

    //地块信息表表名
    private String rankTableName;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRankAreaTableName() {
        return rankAreaTableName;
    }

    public void setRankAreaTableName(String rankAreaTableName) {
        this.rankAreaTableName = rankAreaTableName;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public String getRankTableName() {
        return rankTableName;
    }

    public void setRankTableName(String rankTableName) {
        this.rankTableName = rankTableName;
    }
}
