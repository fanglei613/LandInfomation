package com.jh.wechat.entity;

/**
 * 微信用户区域作物配置
 * Created by XZG on 2018/4/27.
 */
public class WXRegionCropEntity {

    private Integer id;
    private String tel;//手机号
    private Long regionId;//区域ID
    private Integer[] cropIdList;//作物ID
    private String wxId;//微信ID

    private Integer cropId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer[] getCropIdList() {
        return cropIdList;
    }

    public void setCropIdList(Integer[] cropIdList) {
        this.cropIdList = cropIdList;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }
}
