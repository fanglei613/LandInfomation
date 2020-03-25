package com.jh.land.entity;

import com.jh.entity.BaseEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分布实体类
 * @version <1> 2019/8/19 mason:Created.
 */
public class Area extends BaseEntity {

    //id
    private Integer id;

    //‘发布状态
    private Integer publishStatus;

    //发布时间
    private Date publishTime;

    //发布人姓名
    private String publisherName;

    //发布人
    private Integer publisher;

    //区域id
    private Long regionId;

    //作物id
    private Integer cropId;

    //分布面积(亩)
    private BigDecimal area;

    //数据时间
    private Date dataTime;

    //数据集精度
    private Integer resolution;

    //区域名称
    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId")
    private String regionName;

    //作物名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropName;//作物中文名

    //精度名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "resolution")
    private String resolutionName;


    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
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
}