package com.jh.ttn.mapping;

import com.jh.ttn.entity.TtnEntity;
import com.jh.ttn.vo.TtnVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

	/**
	 * 根据区域、起止时间查询时间段内该区域的气温数据
	 * @param param
	 * @return
	 */
	List<TtnVo> findWeatherForRank(TtnEntity param);


	/**
	 * 根据区域、起止时间查询时间段内该区域的地温数据
	 * @param param
	 * @return
	 */
	List<TtnVo> findTtnForRank(TtnEntity param);

	/**
	 * 根据区域、起止时间查询时间段内该区域的降雨数据
	 * @param param
	 * @return
	 */
	List<TtnVo> findTrmmForRank(TtnEntity param);

	//全年累计降雨
	BigDecimal totalTrmm(TtnEntity param);

	//日均降雨
	BigDecimal avgTrmm(TtnEntity param);

	//日均地温
	BigDecimal avgTtn(TtnEntity param);

	//最低气温
	Integer minWeather(TtnEntity param);
	//日均气温
	Integer avgWeather(TtnEntity param);
	//最高气温
	Integer maxWeather(TtnEntity param);

}