/*
 地温、降雨数据集服务类,功能如下：
 1. List<DsTtn> findTrmmForMon(DatasetParam param) :
	根据区域、日期段查询区域及下一级区域在日期段内的同期环比降雨数据

 2. List<DsTtn> findTForMon(DatasetParam param):
	根据区域、日期段查询区域及下一级区域在日期段内的同期环比地温数据
 
 3. Map<String,List<DsTtn>> findT(DatasetParam param)

 	查询指定区域及下一级区域，三年内同一日期段地温数据，及十年同一日期内的地温数据。
 	
*/
package com.jh.ttn.service;

import com.jh.vo.ResultMessage;

/**
 * description:7天降雨数据集服务:
 * 1.根据区域构造7天降雨数据查询服务
 * @version <1> 2018-06-12 cxw: Created.
 */
public interface IDsTrmmForSevenService {

	/*
	 * 根据区域构造7天降雨数据查询服务
	 * @param 数据集查询参数
	 *   regionId: 区域ID
     *       date: 日期
	 * @return ResultMessage
	 * @version <1> 2018-04-27 cxw: Created.
	 */
	ResultMessage findTrmmForSeven(Long[] regionId, String date);

}