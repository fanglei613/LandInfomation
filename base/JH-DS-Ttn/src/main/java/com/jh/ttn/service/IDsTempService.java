/*
 地温、气温数据集服务类,功能如下：
 1. List<DsTtn> findTempForMon(DatasetParam param) :
	根据区域、日期段查询区域及下一级区域在日期段内的同期环比气温数据

 2. List<DsTtn> findTForMon(DatasetParam param):
	根据区域、日期段查询区域及下一级区域在日期段内的同期环比地温数据
 
 3. Map<String,List<DsTtn>> findT(DatasetParam param)

 	查询指定区域及下一级区域，三年内同一日期段地温数据，及十年同一日期内的地温数据。
 	
*/
package com.jh.ttn.service;

import com.jh.vo.ResultMessage;

/**
 * description:气温数据集服务:
 * 1.根据区域构造某一时间段内气温的近三年和10年均值数据查询服务
 * 2.根据区域、日期段查询区域及下一级区域在日期段内的同期环比气温数据
 * 3.根据区域构造7天气温数据查询服务
 * @version <1> 2018-10-16 huxiaoqiang: Created.
 */
public interface IDsTempService {

	/*
	 * 根据区域构造某一时间段内气温的近三年和10年均值数据查询服务
	 * @param  数据集查询参数
	 *   regionId: 区域ID
	 *  startDate: 开始日期
	 *    endDate: 结束日期
	 * @return ResultMessage
	 * @version <1> 2018-10-16 huxiaoqiang: Created.
	 */
	ResultMessage findTempForThree(Long regionId, String startDate, String endDate);

	/*
	 *根据区域、日期段查询区域及下一级区域在日期段内的同期环比气温数据
	 * @param 数据集查询参数
	 *   regionId: 区域ID
	 *  startDate: 开始日期
	 *    endDate: 结束日期
	*@return ResultMessage
	 * @version <1> 2018-10-16 huxiaoqiang: Created.
	*/
	ResultMessage findTempForMon(Long regionId, String startDate, String endDate);


	/*
	 * 根据区域，日期生成气温报告
	 * @param 数据集查询参数
	 *   regionId: 区域ID
	 *  chinaName:区域中文名
	 *  startDate: 开始日期
	 *    endDate: 结束日期
	 * @return ResultMessage
	 * @version <1> 2018-10-16 huxiaoqiang: Created.
	 */
	ResultMessage findTempForReport(String chinaName,Long regionId, String startDate, String endDate);


	/**
	 * 查询开始和结束日期时间段内有气温数据的所有日期
	 * @param regionId	区域id
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @return
	 */
	ResultMessage findTempTimes(Long regionId,String startDate,String endDate);

	/**
	 * 查询开始和结束日期时间段内所有气温数据
	 * @param regionId	区域id
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @return
	 * @version <1> 2019-03-20 cxw: Created.
	 */
	ResultMessage findAllTemp(Long regionId,String startDate,String endDate);

}