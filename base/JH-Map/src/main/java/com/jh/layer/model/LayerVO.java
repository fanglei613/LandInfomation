package com.jh.layer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 图层实体类
 * @version <1> 2019/8/19 mason:Created.
 */
public class LayerVO extends BaseEntity {

    //id
    private Integer layerId;

    //发布状态
    private Integer publishStatus;

    //发布时间
    private Date publishTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataTime;

    //作物id
    private Integer cropId;

    //数据集精度id
    private Integer accuracyId;

    //文件路径
    private String filePath;

    //任务id
    private Integer taskId;

    private List<Integer> idList;//批量发布 撤销 id数组

    @IdTransform(type= CacheUtil.CACHE_REGION_TYPE,propName = "regionId",transType = CacheUtil.CACHE_TRANS_CODE)
    private String regionCode;

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "cropId",transType = CacheUtil.CACHE_TRANS_CODE)
    private String cropCode;

    @IdTransform(type= CacheUtil.CACHE_DICT_TYPE,propName = "datasetId",transType = CacheUtil.CACHE_TRANS_CODE)
    private String datasetCode;

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
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

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCropCode() {
        return cropCode;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public String getDatasetCode() {
        return datasetCode;
    }

    public void setDatasetCode(String datasetCode) {
        this.datasetCode = datasetCode;
    }
}