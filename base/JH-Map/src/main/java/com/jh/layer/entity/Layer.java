package com.jh.layer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.time.LocalDateTime;

/**
 * 图层实体类
 * @version <1> 2019/8/19 mason:Created.
 */
public class Layer extends BaseEntity {

    //id
    private Integer layerId;

    //发布状态
    private Integer publishStatus;

    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

    //发布人姓名
    private String publisherName;

    //发布人id
    private Integer publisher;

    //图层名称
    private String layerName;

    //区域id
    private Long regionId;

    //数据集id
    private Integer datasetId;

    //数据时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dataTime;

    //作物id
    private Integer cropId;

    //数据集精度id
    private Integer accuracyId;

    //文件路径
    private String filePath;

    //任务id
    private Integer taskId;

    //父ID
    private Long parentId;

    //作物名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId")
    private String cropName;//作物中文名

    //区域名称
    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId")
    private String regionName;

    //精度名称
    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "accuracyId")
    private String resolutionName;

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "datasetId")
    private String dsName;

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getResolutionName() {
        return resolutionName;
    }

    public void setResolutionName(String resolutionName) {
        this.resolutionName = resolutionName;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getLayerId() {
        return layerId;
    }

    public void setLayerId(Integer layerId) {
        this.layerId = layerId;
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

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
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

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDateTime dataTime) {
        this.dataTime = dataTime;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}