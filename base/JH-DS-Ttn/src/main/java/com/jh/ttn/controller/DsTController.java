/*
地温地温数据集接口

@version <1> 2017-10-30 Hayden : Created.
*/
package com.jh.ttn.controller;

import com.jh.ttn.service.IDsTService;
import com.jh.ttn.service.IDsTService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:地温数据集服务:
 * 1.根据区域，日期构造某一时间段内地温的近三年和10年均值数据查询服务
 * 2.根据区域，日期构造地温环比查询服务
 * @version <1> 2018-04-27 cxw: Created.
 */
@Api(value="地温数据集接口",description="地温数据集接口")
@RestController
@RequestMapping("/t")
public class DsTController {

	@Autowired
	private IDsTService tService;

	/*
	 * 根据区域，日期构造某一时间段内地温的近三年和10年均值数据查询服务
	 * @param  数据集查询参数
	 *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
    * @return ResultMessage
	* @version <1> 2018-04-27 cxw: Created.
	 */
	@ApiOperation(value = "查询指定时间段地温同比数据", notes = "根据区域、时间段查询某一时间段内地温的近三年和10年均值数据")
	@GetMapping("/queryTForChart")
	public ResultMessage queryTForChart(Long regionId,String startDate,String endDate,Integer accuracyId){
		return tService.findTForThree(regionId,startDate,endDate,accuracyId);
	}

	/*
	* 根据区域，日期构造地温环比查询服务
 	 * @param 数据集查询参数
	 *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
    * @return ResultMessage
	* @version <1> 2018-04-27 cxw: Created.
	*/
	@ApiOperation(value="查询地温指定日期环比数据",notes="根据区域、日期段查询区域及下一级区域在日期段内的同期环比地温数据" )
	@GetMapping("/tForMon")
	public ResultMessage queryTForMon(Long regionId,String startDate,String endDate,Integer accuracyId){
        return tService.findTForMon(regionId,startDate,endDate,accuracyId);
	}

	/*
	* 根据区域，日期构造地温报告生成服务
	* @param  数据集查询参数
	*   regionId: 区域ID
	*   chinaName:区域中文名
	*  startDate: 开始日期
	*    endDate: 结束日期
	* @return ResultMessage
	* @version <1> 2018-05-25 cxw: Created.
	*/
	@ApiOperation(value="构造地温报告生成服务",notes="根据区域、日期段查询区域及下一级区域在日期段内的地温报告" )
	@GetMapping("/tForReport")
	public ResultMessage queryTForReport(String chinaName, Long regionId, String startDate, String endDate,Integer accuracyId){
		return tService.findTForReport(chinaName,regionId,startDate,endDate,accuracyId);
	}

	/**
	 * 查询开始和结束日期时间段内有地温数据的所有日期
	 * @param regionId	区域id
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param accuracyId	数据集精度
	 * @return
	 */
	@ApiOperation(value = "查询开始和结束日期时间段内有地温数据的所有日期",notes = "查询开始和结束日期时间段内有地温数据的所有日期")
	@GetMapping("/queryTTimes")
	public ResultMessage queryTTimes(Long regionId,String startDate,String endDate,Integer accuracyId){
		return tService.findTTimes(regionId,startDate,endDate,accuracyId);
	}

	/**
	 * 查询开始和结束日期时间段内所有有地温数据
	 * @param regionId	区域id
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @return
	 * @version <1> 2019-03-20 cxw: Created.
	 */
	@ApiOperation(value = "查询开始和结束日期时间段内所有有地温数据",notes = "查询开始和结束日期时间段内所有有地温数据")
	@GetMapping("/queryAllT")
	public ResultMessage queryAllT(Long regionId,String startDate,String endDate){
		return tService.findAllT(regionId,startDate,endDate);
	}
}