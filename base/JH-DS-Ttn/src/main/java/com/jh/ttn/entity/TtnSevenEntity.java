package com.jh.ttn.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * description:降雨条件查询对象
 * @version <1> 2018-04-27 cxw: Created.
 */
@ApiModel(value = "Trmm", description = "降雨条件查询对象")
public class TtnSevenEntity {
	@ApiModelProperty(value="区域ID")
	private Long[] regionId;

	@ApiModelProperty(value="查询日期")
	@DateTimeFormat(pattern="yyyy-MM-dd")  
	private LocalDate date;

	public Long[] getRegionId() {
		return regionId;
	}

	public void setRegionId(Long[] regionId) {
		this.regionId = regionId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}