package com.jh.system.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jh.system.entity.InitRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 	@description: 区域信息
 * 	@version <1> 2018-01-18 cxj:Created.
 * 	@version <2> 2018/1/26 djh： update.
 * 		添加InitRegion属性
 */
@ApiModel(value = "RegionParam",description = "区域信息")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionParam {
	@ApiModelProperty(value = "区域ID")
	private Long regionId;
	@ApiModelProperty(value = "区域编码")
	private String regionCode;
	@ApiModelProperty(value = "区域名")
	private String regionName;
	@ApiModelProperty(value = "区域中文名")
	private String chinaName;
	@ApiModelProperty(value = "区域级别（1国、2省、3市、4县")
	private Long level;
	@ApiModelProperty(value = "上一级ID")
	private Long parentId;
	@ApiModelProperty(value = "条带号")
	private String stripNumber;

	private Integer countRankNum;

	private Integer countRankArea;


	private InitRegion initRegion;

	public InitRegion getInitRegion() {
		return initRegion;
	}

	public void setInitRegion(InitRegion initRegion) {
		this.initRegion = initRegion;
	}

	public Long getRegionId(){return regionId;}
	public void setRegionId(Long regionId){this.regionId = regionId;}

	public String getRegionCode(){return regionCode;}
	public void setRegionCode(String regionCode){this.regionCode = regionCode;}

	public String getRegionName(){return regionName;}
	public void setRegionName(String regionName){this.regionName = regionName;}

	public String getChinaName(){return chinaName;}
	public void setChinaName(String chinaName){this.chinaName = chinaName;}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getParentId(){return parentId;}
	public void setParentId(Long parentId) { this.parentId = parentId;}

	public boolean equals(Object obj){
		if(obj instanceof RegionParam){
			RegionParam tempObj = (RegionParam)obj;
			return this.regionId.equals(tempObj.getRegionId());
		}
		return super.equals(obj);
	}

	public int hashCode(){
		return this.regionId.hashCode();
	}

	public String toString(){
		return regionId+"-"+regionCode+"-"+chinaName;
	}

	public String getStripNumber() {
		return stripNumber;
	}

	public void setStripNumber(String stripNumber) {
		this.stripNumber = stripNumber;
	}


	public Integer getCountRankNum() {
		return countRankNum;
	}

	public void setCountRankNum(Integer countRankNum) {
		this.countRankNum = countRankNum;
	}

	public Integer getCountRankArea() {
		return countRankArea;
	}

	public void setCountRankArea(Integer countRankArea) {
		this.countRankArea = countRankArea;
	}
}