package com.jh.land.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 数据集入库任务VO
 * @version <1> 2019/8/27 10:05 zhangshen:Created.
 */
public class WarehousingTaskVO extends BaseEntity {

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

    //区域id
    private Long regionId;

    //数据集id
    private Integer datasetId;

    //作物id
    private Integer cropId;

    //数据集精度id
    private Integer accuracyId;

    //存储的地址
    private String storageUrl;

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //失败原因
    private String failReason;

    //任务状态
    private Integer taskStatus;

    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId")
    private String regionName;//区域中文名

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "datasetId")
    private String datasetName;//数据集中文名

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropName;//作物中文名

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "accuracyId")
    private String accuracyName;//精度中文名

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "taskStatus")
    private String taskStatusName;//任务状态中文名

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "publishStatus")
    private String publishStatusName;//发布状态中文名

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

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Integer datasetId) {
        this.datasetId = datasetId;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Integer getAccuracyId() {
        return accuracyId;
    }

    public void setAccuracyId(Integer accuracyId) {
        this.accuracyId = accuracyId;
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getAccuracyName() {
        return accuracyName;
    }

    public void setAccuracyName(String accuracyName) {
        this.accuracyName = accuracyName;
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
}
