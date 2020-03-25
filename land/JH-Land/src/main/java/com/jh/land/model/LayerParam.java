package com.jh.land.model;

import java.time.LocalDate;

public class LayerParam {

    //区域id
    private Long regionId;

    //开始时间
    private LocalDate startDate;

    //结束时间
    private LocalDate endDate;

    private Integer startYear;

    private Integer endYear;

    private LocalDate dataTime;

    //区域code
    private String regionCode;

    //区域等级
    private Integer level;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public LocalDate getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDate dataTime) {
        this.dataTime = dataTime;
    }
}
