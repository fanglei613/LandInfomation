package com.jh.system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.jh.entity.BaseEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @Description：分布VO
 * @version<1> 2019-08-19 mason : Created.
 */
public class AreaVO extends BaseEntity {

    //id
    private Integer id;

    private Integer quadratType;
    //当前登陆人id
    private Integer accountId;

    //发布状态
    private Integer publishStatus;

    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishTime;

    //发布人姓名
    private String publisherName;

    //区域名称
    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId")
    private String regionName;

    //区域编码
    private String regionCode;

    //发布人
    private Integer publisher;

    //区域id
    private Long regionId;

    //作物id
    private Integer cropId;

    //作物名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropName;//作物中文名

    //精度名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "resolution")
    private String resolutionName;

    //分布面积(亩)
    private BigDecimal area;

    //估产(吨)
    private BigDecimal yield;

    //数据时间
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate dataTime;

    //数据集精度
    private Integer resolution;

    //查询开始日期
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    //查询结束日期
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    private String chinaName;

    //数据时间 开始时间
    private String startTime;

    //数据时间 结束时间
    private String endTime;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 年
     */
    private Integer yearCount;

    /*
     * 区域等级
     */
    private Integer level;

    private String dataDate;

    private List<Integer> idList;//批量发布 撤销 id数组

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getYearCount() {
        return yearCount;
    }

    public void setYearCount(Integer yearCount) {
        this.yearCount = yearCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public LocalDate getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDate dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
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

    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getResolutionName() {
        return resolutionName;
    }

    public void setResolutionName(String resolutionName) {
        this.resolutionName = resolutionName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }


    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getQuadratType() {
        return quadratType;
    }

    public void setQuadratType(Integer quadratType) {
        this.quadratType = quadratType;
    }
}
