package com.jh.system.entity;

import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;

/**
* 区域实体信息对象 
* @version <1> 2017-11-07 Hayden:Created.
*/
@ApiModel(value = "区域实体")
public class InitRegion extends BaseEntity {

	private Long regionId;

	private String regionCode;

	private String regionName;

	private String chinaName;

	private String localName;

	private Integer level;

	private Long parentId;

	private BigDecimal area;

	private BigDecimal lon;

	private BigDecimal lat;

	private String bbox;

	private String vertexBbox;

	private String stripNumber;

	private String centroid;

	private String delFlag;

	private Long capitalId;//省会id

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}


	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode == null ? null : regionCode.trim();
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName == null ? null : regionName.trim();
	}

	public String getChinaName() {
		return chinaName;
	}

	public void setChinaName(String chinaName) {
		this.chinaName = chinaName == null ? null : chinaName.trim();
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName == null ? null : localName.trim();
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getLon() {
		return lon;
	}

	public void setLon(BigDecimal lon) {
		this.lon = lon;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public String getBbox() {
		return bbox;
	}

	public void setBbox(String bbox) {
		this.bbox = bbox;
	}

	public String getVertexBbox() {
		return vertexBbox;
	}

	public void setVertexBbox(String vertexBbox) {
		this.vertexBbox = vertexBbox;
	}

	public String getStripNumber() {
		return stripNumber;
	}

	public void setStripNumber(String stripNumber) {
		this.stripNumber = stripNumber;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Long getCapitalId() { return capitalId; }

	public void setCapitalId(Long capitalId) { this.capitalId = capitalId; }


	public String getCentroid() {
		return centroid;
	}

	public void setCentroid(String centroid) {
		this.centroid = centroid;
	}
}