package com.jh.land.entity;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * 地块属性
 */
@Entity
public class LandEntity {

    private Long rankId;

    private String rankCode;

    private String rankName;

    private BigDecimal area;

    private Long parentId;

    private Float lon;

    private Float lat;

    private Integer cropId;

    private String geometry;

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public String getRankCode() {
        return rankCode;
    }

    public void setRankCode(String rankCode) {
        this.rankCode = rankCode;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
}
