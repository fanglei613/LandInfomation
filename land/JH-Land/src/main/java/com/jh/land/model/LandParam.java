package com.jh.land.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * 地块相关请求参数
 */
public class LandParam {

    /**
     * 行政区划编码
     */
    private Long regionId;

    /**
     * 作物ID
     */
    private Integer cropId;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页最大数
     */
    private Integer rows;

    /**
     * 总数量
     */
    private Long total;

    /**
     * 地块表名
     */
    private String landTableName;

    /**
     * 地块ID
     */
    private Long rankId;

    /**
     * 经度
     */
    private Double lon;

    /**
     * 纬度
     */
    private Double lat;

    /**
     * 用于请求的空间过滤
     */
    private String wkt;

    /**
     * 分页查询开始索引
     */
    private Integer offset;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getLandTableName() {
        return landTableName;
    }

    public void setLandTableName(String landTableName) {
        this.landTableName = landTableName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

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

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }
}
