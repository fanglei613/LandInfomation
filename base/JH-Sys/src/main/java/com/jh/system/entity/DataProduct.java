package com.jh.system.entity;

import com.jh.entity.BaseEntity;

import java.util.Date;

/**
 * 数据权限
 * @version <1> 2018-06-06 xzg:Created.
 */
public class DataProduct extends BaseEntity {

    private Integer productId;//权限主键
    private Long regionId;//区域主键
    private Integer datasetId;//数据集主键
    private Integer accuracyId;//数据集精度主键
    private Integer cropId;//作物主键
    private String startDate;//有效时间
    private String endDate;//失效时间
    private Integer productType;//权限类型 (1、默认权限  2、自定义权限)
    private Boolean defaultShow;//是否默认显示
    private Integer accountId;//账号id

    private String datasetName;

    private String chinaName;
    private String cropName;



    private String accuracyName;
    private Date productStartDate;
    private Date productEndDate;

    /**
     * 数据集编码
     */
    private String datasetCode;


    /**
     * 作物code
     */
    private String cropCode;


    /**
     * 数据源编码
     */
    private String datatypeCode;
    /**
     * 数据源名称
     */
    private String datatypeName;

    /**
     *区域编码
     */
    private String regionCode;

    /**
     * 区域名称
     */
    private String regionName ;

    /**
     * 区域级别
     */
    private Integer level;


    /**
     * 上级区域ID
     */
    private Long parentId;

    /**
     * 数据源值
     */
    private String datatypeValue;



    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public Integer getAccuracyId() {
        return accuracyId;
    }

    public void setAccuracyId(Integer accuracyId) {
        this.accuracyId = accuracyId;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Boolean getDefaultShow() {
        return defaultShow;
    }

    public void setDefaultShow(Boolean defaultShow) {
        this.defaultShow = defaultShow;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getAccuracyName() {
        return accuracyName;
    }

    public void setAccuracyName(String accuracyName) {
        this.accuracyName = accuracyName;
    }

    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Date getProductStartDate() {
        return productStartDate;
    }

    public void setProductStartDate(Date productStartDate) {
        this.productStartDate = productStartDate;
    }

    public Date getProductEndDate() {
        return productEndDate;
    }

    public void setProductEndDate(Date productEndDate) {
        this.productEndDate = productEndDate;
    }
}
