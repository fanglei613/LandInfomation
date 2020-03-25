package com.jh.land.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.jh.entity.BaseEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @Description：估产VO
 * @version<1> 2019-08-19 mason : Created.
 */
public class YieldVO extends BaseEntity {

    //id
    private Integer id;

    //发布状态
    private Integer publishStatus;

    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishTime;

    //发布人姓名
    private String publisherName;

    //发布人id
    private Integer publisher;

    //区域id
    private Long regionId;

    //作物id
    private Integer cropId;

    //估产值
    private BigDecimal yield;

    //数据时间
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date dataTime;

    //数据集精度
    private Integer resolution;

    //单产
    private BigDecimal yieldUnit;

    //区域编码
    private String regionCode;

    //总产
    private BigDecimal total;

    //查询开始日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    //查询结束日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    private String chinaName;

    //作物名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropName;//作物中文名

    //精度名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "resolution")
    private String resolutionName;

    //区域英文名称
    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId")
    private String regionName;

    //数据时间 开始时间
    private String startTime;

    //数据时间 结束时间
    private String endTime;

    private List<Integer> idList;//批量发布 撤销 id数组

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
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

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public BigDecimal getYieldUnit() {
        return yieldUnit;
    }

    public void setYieldUnit(BigDecimal yieldUnit) {
        this.yieldUnit = yieldUnit;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }
}
