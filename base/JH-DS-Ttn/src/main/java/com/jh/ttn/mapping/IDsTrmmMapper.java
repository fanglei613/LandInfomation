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
 * description:降雨数据集服务:
 * @version <1> 2018-04-27 cxw: Created.
 */
@Component
@Mapper
public interface IDsTrmmMapper {

	/*
	*根据区域、起止日期查询日期段内区域及下一级区域的降雨数据
	*@param TtnEntity : 参数，（区域ID，起止日期）
	*@return List<TtnVo> : 降雨数据
	*@Version <1> 2018-04-28 cxw: Created.
	*/
	List<TtnVo> findTrmm(TtnEntity param);

	/*
	 *根据区域、起止时间查询时间段内该区域的降雨数据
	 *@param param : 包含region_id和startDate和endDate
	 *@return List<TtnVo> ：
	 *@Version <1> 2018-04-28 cxw: Created.
	 */
    List<TtnVo> findTrmmByRegion(TtnEntity param);

	/*
     *根据区域构造7天降雨数据查询服务
     *@param TtnEntity : 参数，（区域ID，起止日期）
     *@return List<TtnVo> : 降雨数据
     *@Version <1> 2018-04-28 cxw: Created.
     */
	List<TrmmSevenVo> findTrmmForSeven(Map<String, Object> map);


	/*
	*根据区域、起止日期查询日期段内下一级区域的降雨数据(按区域值相加)
	*@param TtnEntity : 参数，（区域ID，起止日期）
	*@return List<TtnVo> : 降雨数据
	*@Version <1> 2018-04-28 cxw: Created.
	*/
	List<TtnVo> findTrmmById(TtnEntity param);


	/*
	*根据区域、起止日期查询日期段内区域的降雨数据(按区域值相加)
	*@param TtnEntity : 参数，（区域ID，起止日期）
	*@return List<TtnVo> : 降雨数据
	*@Version <1> 2018-04-28 cxw: Created.
	*/
	double findSumTrmmById(TtnEntity param);

	/*
	*根据区域、起止日期查询日期段内区域最大降雨数据
	*@param TtnEntity : 参数，（区域ID，起止日期）
	*@return List<TtnVo> : 降雨数据
	*@Version <1> 2018-04-28 cxw: Created.
	*/
	List<TtnVo> findMaxTrmmById(TtnEntity param);


	/*
	*根据区域、起止日期查询日期段内区域的降雨数据
	*@param TtnEntity : 参数，（区域ID，起止日期）
	*@return List<TtnVo> : 降雨数据
	*@Version <1> 2018-04-28 cxw: Created.
	*/
	List<TtnVo> findTrmmForMonById(TtnEntity param);

	/*
    *根据区域、起止日期查询日期段内下一级区域的降雨数据(某天最大降雨城市)
    *@param TtnEntity : 参数，（区域ID，起止日期）
    *@return List<TtnVo> : 降雨数据
    *@Version <1> 2018-04-28 cxw: Created.
    */
	List<TtnVo> findTrmmMaxByDate(TtnEntity param);

	/**
	 * 查询开始和结束日期时间段内有降雨数据的所有日期
	 * @param param
	 * @return
	 */
	List<LocalDate> findTrmmTimes(TtnEntity param);

	/**
	 * 根据日期查询所有降雨数据
	 * @return
	 * @version <1> 2019-03-20 cxw: Created.
	 */
	List<TtnVo> findAllTrmm(TtnEntity param);
}