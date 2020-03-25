package com.jh.ttn.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * description:降雨地温条件查询对象
 * @version <1> 2018-04-27 cxw: Created.
 */
@ApiModel(value = "Ttn", description = "地温、降雨条件查询对象")
public class TtnEntity {
	@ApiModelProperty(value="区域ID")
	private Long regionId;

	@ApiModelProperty(value="查询开始日期")
	@DateTimeFormat(pattern="yyyy-MM-dd")  
	private LocalDate startDate;

	@ApiModelProperty(value="查询结束日期")
	@DateTimeFormat(pattern="yyyy-MM-dd")  
	private LocalDate endDate;

	@ApiModelProperty(value="精度")
	private Integer resolution;

	public void setRegionId(Long regionId){ this.regionId = regionId;}
	public Long getRegionId(){return this.regionId;}

	public void setStartDate(LocalDate startDate){this.startDate = startDate;}
	public LocalDate getStartDate(){return this.startDate;}

	public void setEndDate(LocalDate endDate){this.endDate = endDate;}
	public LocalDate getEndDate(){return this.endDate;}

	public Integer getResolution() {
		return resolution;
	}

	public void setResolution(Integer resolution) {
		this.resolution = resolution;
	}
}