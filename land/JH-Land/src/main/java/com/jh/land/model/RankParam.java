package com.jh.land.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RankParam {
    private Long regionId;

    private Integer rankNum;

    private BigDecimal areaSum;

    private String rankTableName;


    //查询开始日期
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    //查询结束日期
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    private String regionCode;


    //查询开始年份
    private Integer startYear;

    //查询结束年份
    private Integer endYear;

    //查询日期
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataTime;

    //地块id
    private Long rankId;

    //地块表名
    private String tableName;


    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer getRankNum() {
        return rankNum;
    }

    public void setRankNum(Integer rankNum) {
        this.rankNum = rankNum;
    }

    public BigDecimal getAreaSum() {
        return areaSum;
    }

    public void setAreaSum(BigDecimal areaSum) {
        this.areaSum = areaSum;
    }


    public String getRankTableName() {
        return rankTableName;
    }

    public void setRankTableName(String rankTableName) {
        this.rankTableName = rankTableName;
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

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
