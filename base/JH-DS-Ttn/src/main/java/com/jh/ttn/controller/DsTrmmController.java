/*
降雨地温数据集接口

@version <1> 2017-10-30 Hayden : Created.
*/
package com.jh.ttn.controller;

import com.jh.ttn.service.IDsTrmmService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * description:降雨数据集服务:
 * 1.根据区域，日期构造某一时间段内降雨的近三年和10年均值数据查询服务
 * 2.根据区域，日期构造降雨环比查询服务
 * 3.根据区域，日期构造7天降雨数据查询服务(91期货)
 * @version <1> 2018-04-27 cxw: Created.
 */
@Api(value="降雨数据集接口",description="降雨数据集接口")
@RestController
@RequestMapping("/trmm")
public class DsTrmmController {

	@Autowired
	private IDsTrmmService trmmService;

	/*
	 * 根据区域，日期构造某一时间段内降雨的近三年和10年均值数据查询服务
	 * @param 数据集查询参数
	 *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
     * @return ResultMessage
	 * @version <1> 2018-04-27 cxw: Created.
	 */
	@ApiOperation(value = "查询指定时间段降雨同比数据", notes = "根据区域、时间段查询某一时间段内降雨的近三年和10年均值数据")
	@GetMapping("/queryTrmmForChart")
	public ResultMessage queryTrmmForChart(Long regionId,String startDate,String endDate,Integer accuracyId ){
		return trmmService.findTrmmForThree(regionId,startDate,endDate,accuracyId);
	}

	/*
	* 根据区域，日期构造降雨环比查询服务
    * @param 数据集查询参数
	*   regionId: 区域ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    * @return ResultMessage
	* @version <1> 2018-04-27 cxw: Created.
	*/
	@ApiOperation(value="查询降雨指定日期环比数据",notes="根据区域、日期段查询区域及下一级区域在日期段内的同期环比降雨数据" )
	@GetMapping("/trmmForMon")
	public ResultMessage queryTrmmForMon(Long regionId,String startDate,String endDate,Integer accuracyId){
		return trmmService.findTrmmForMon(regionId,startDate,endDate,accuracyId);
	}

	/*
	* 根据区域，日期构造降雨报告生成服务
	* @param 数据集查询参数
	*   regionId: 区域ID
	*   chinaName:区域中文名
	*  startDate: 开始日期
	*    endDate: 结束日期
	* @return ResultMessage
	* @version <1> 2018-04-27 cxw: Created.
	*/
	@ApiOperation(value="构造降雨报告生成服务",notes="根据区域、日期段查询区域及下一级区域在日期段内的降雨报告" )
	@GetMapping("/trmmForReport")
	public ResultMessage queryTrmmForReport(String chinaName, Long regionId, String startDate, String endDate,Integer accuracyId){
		return trmmService.findTrmmForReport(chinaName,regionId,startDate,endDate,accuracyId);
	}

	/**
	 * 查询开始和结束日期时间段内有降雨数据的所有日期
	 * @param regionId	区域id
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param accuracyId	数据集精度
	 * @return
	 */
	@ApiOperation(value = "查询开始和结束日期时间段内有降雨数据的所有日期",notes = "查询开始和结束日期时间段内有降雨数据的所有日期")
	@GetMapping("/queryTrmmTimes")
	public ResultMessage queryTrmmTimes(Long regionId,String startDate,String endDate,Integer accuracyId){
		return trmmService.findTrmmTimes(regionId,startDate,endDate,accuracyId);
	}

	/**
	 * 根据日期查询所有降雨数据
	 * @param regionId	区域id
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @return
	 * @version <1> 2019-03-20 cxw: Created.
	 */
	@ApiOperation(value = "根据日期查询所有降雨数据",notes = "根据日期查询所有降雨数据")
	@GetMapping("/queryAllTrmm")
	public ResultMessage queryAllTrmm(Long regionId,String startDate,String endDate){
		return trmmService.findAllTrmm(regionId,startDate,endDate);
	}
}