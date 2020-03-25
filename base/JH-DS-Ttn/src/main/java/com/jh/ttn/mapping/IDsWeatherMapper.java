package com.jh.ttn.mapping;

import com.jh.ttn.entity.TtnEntity;
import com.jh.ttn.vo.TtnVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * description:天气预报数据集服务:
 * @version <1> 2018-11-02 Roach: Created.
 */
@Component
@Mapper
public interface IDsWeatherMapper {

	/**
	 * 根据区域、起止日期查询日期段内区域及下一级区域的天气预报数据
	 * @param param
	 * @return
	 */
	List<TtnVo> findWeatherRegionAndBeyond(TtnEntity param);

	/**
	 * 根据区域、起止时间查询时间段内该区域的天气预报数据
	 * @param param
	 * @return
	 */
    List<TtnVo> findRegionWeather(TtnEntity param);

}