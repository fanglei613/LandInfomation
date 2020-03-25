package com.jh.ttn.mapping;

import com.jh.ttn.entity.TtnEntity;
import com.jh.ttn.vo.TrmmSevenVo;
import com.jh.ttn.vo.TtnVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * description:地温数据集服务:
 * @version <1> 2018-04-27 cxw: Created.
 */
@Component
@Mapper
public interface IDsTMapper {
	/*
	*根据区域、起止日期查询日期段内区域及下一级区域的地温数据
	*@param TtnEntity : 参数，（区域ID，起止日期）
	*@return List<TtnVo> : 地温数据
	*@Version <1> 2018-04-28 cxw: Created.
	*/
	List<TtnVo> findT(TtnEntity param);

	/*
	 *根据区域、起止时间查询时间段内该区域的地温数据
	 *@param param : 包含region_id和startDate和endDate
	 *@return List<TtnVo> ：
	 *@Version <1> 2018-04-28 cxw: Created.
	 */
    List<TtnVo> findTByRegion(TtnEntity param);


	/*
    *根据区域、起止日期查询日期段内下一级区域的地温均值数据
    *@param TtnEntity : 参数，（区域ID，起止日期）
    *@return List<TtnVo> : 地温数据
    *@Version <1> 2018-04-28 cxw: Created.
    */
	List<TtnVo> findTById(TtnEntity param);

	/*
    *根据区域、起止日期查询日期段内区域及的地温数据
    *@param TtnEntity : 参数，（区域ID，起止日期）
    *@return List<TtnVo> : 地温数据
    *@Version <1> 2018-04-28 cxw: Created.
    */
	List<TtnVo> findTForMonById(TtnEntity param);

	/*
    *根据区域、起止日期查询日期段内区域最高地温数据
    *@param TtnEntity : 参数，（区域ID，起止日期）
    *@return List<TtnVo> : 地温数据
    *@Version <1> 2018-04-28 cxw: Created.
    */
	List<TtnVo> findMaxTById(TtnEntity param);

	/*
    *根据区域、起止日期查询日期段内区域的地温数据(区域均值)
    *@param TtnEntity : 参数，（区域ID，起止日期）
    *@return List<TtnVo> : 地温数据
    *@Version <1> 2018-04-28 cxw: Created.
    */
	double findAvgTById(TtnEntity param);

	/*
    *根据区域、起止日期查询日期段内下一级区域的地温数据(某天最高地温城市)
    *@param TtnEntity : 参数，（区域ID，起止日期）
    *@return List<TtnVo> : 地温数据
    *@Version <1> 2018-04-28 cxw: Created.
    */
	List<TtnVo> findTMaxByDate(TtnEntity param);


	/**
	 * 查询开始和结束日期时间段内有地温数据的所有日期
	 * @param param
	 * @return
	 */
	List<LocalDate> findTTimes(TtnEntity param);

	/**
	 * 查询开始和结束日期时间段内所有有地温数据
	 * @return
	 * @version <1> 2019-03-20 cxw: Created.
	 */
	public List<TtnVo>  findAllT(TtnEntity param);

}