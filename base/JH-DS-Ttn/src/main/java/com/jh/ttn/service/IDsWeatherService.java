
package com.jh.ttn.service;

import com.jh.vo.ResultMessage;

public interface IDsWeatherService {

	/*
	 * 根据区域查询区域及下一级区域的天气预报数据
	 *   regionId: 区域ID
	 * @return ResultMessage
	 * @version <1> 2018-11-02 Roach: Created.
	 */
	ResultMessage findWeatherRegionAndBeyond(Long regionId);

	/*
	 *根据区域查询该区域的天气预报数据
	 * @param 数据集查询参数
	 *   regionId: 区域ID
	*@return ResultMessage
	 * @version <1> 2018-11-02 Roach: Created.
	*/
	ResultMessage findRegionWeather(Long regionId);

	/*根据年份和区域查询气温列表*/
	ResultMessage findRankWeatherList(Long regionId, String date);

	/*根据年份和地块id查询年平均气象数据*/
	ResultMessage findRankWeather(Long regionId, String date);

	
}