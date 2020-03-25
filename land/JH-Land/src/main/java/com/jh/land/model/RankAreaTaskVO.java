package com.jh.land.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class RankAreaTaskVO extends BaseEntity {


    //id
    private Integer taskId;

    //发布状态
    private Integer publishStatus;

    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

    //发布人姓名
    private String publisherName;

    //发布人id
    private Integer publisher;

    //任务名称
    private String taskName;

    //作物id
    private Integer cropId;

    //存储的地址
    private String storageUrl;

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //当年开始时间
    private Date startDate;

    //当年结束时间
    private Date endDate;

    //失败原因
    private String failReason;

    //任务状态
    private Integer taskStatus;

    /**
     * 空间过滤字符串
     */
    private String wkt;

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropName;//作物中文名


    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "taskStatus")
    private String taskStatusName;//任务状态中文名

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "publishStatus")
    private String publishStatusName;//发布状态中文名

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "productType")
    private String typeName;//数据分类


    //作物id
    private Integer productType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataTime;

    private String regionCode;

    private Long regionId;

    private String tableName;

    private String areaTableName; // 关联分布表名

    private List<Long> regionIdList;

    private List<Integer> checkResultList;

    private Integer level;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate shpTime;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getTaskStatusName() {
        return taskStatusName;
    }

    public void setTaskStatusName(String taskStatusName) {
        this.taskStatusName = taskStatusName;
    }

    public String getPublishStatusName() {
        return publishStatusName;
    }

    public void setPublishStatusName(String publishStatusName) {
        this.publishStatusName = publishStatusName;
    }

    public LocalDate getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDate dataTime) {
        this.dataTime = dataTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Long> getRegionIdList() {
        return regionIdList;
    }

    public void setRegionIdList(List<Long> regionIdList) {
        this.regionIdList = regionIdList;
    }

    public List<Integer> getCheckResultList() {
        return checkResultList;
    }

    public void setCheckResultList(List<Integer> checkResultList) {
        this.checkResultList = checkResultList;
    }


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public LocalDate getShpTime() {
        return shpTime;
    }

    public void setShpTime(LocalDate shpTime) {
        this.shpTime = shpTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public String getAreaTableName() {
        return areaTableName;
    }

    public void setAreaTableName(String areaTableName) {
        this.areaTableName = areaTableName;
    }
}
