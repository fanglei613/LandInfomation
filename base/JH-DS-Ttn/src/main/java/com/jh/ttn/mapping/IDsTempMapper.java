package com.jh.ttn.mapping;

import com.jh.ttn.entity.TtnEntity;
import com.jh.ttn.vo.TtnVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * description:气温数据集服务:
 * @version <1> 2018-10-16 huxiaoqiang: Created.
 */
@Component
@Mapper
public interface IDsTempMapper {

	/**
	 * 根据区域、起止日期查询日期段内区域及下一级区域的气温数据
	 * @param param
	 * @return
	 */
	List<TtnVo> findTemp(TtnEntity param);

	/**
	 * 根据区域、起止时间查询时间段内该区域的气温数据
	 * @param param
	 * @return
	 */
    List<TtnVo> findTempByRegion(TtnEntity param);

	/**
	 * 查询当前查询区域和时间段内该区域平均最低气温和平均最高气温
	 * @param param
	 * @return
	 */
	TtnVo findAvgMaxMin(TtnEntity param);

	/**
	 * 根据区域id，开始和结束日期查询该区域当前时间段内，上期(30天前)、去年同期、前年同期的最高温和最低温数据
	 * @param param
	 * @return
	 */
	List<TtnVo> findMaxMinBefore(TtnEntity param);

	/**
	 * 查询当前指定区域在指定时间段内最高温度以及对应的日期
	 * @param param
	 * @return
	 */
	TtnVo findMaxAndDate(TtnEntity param);

	/**
	 * 查询当前指定区域的下级区域中在指定时间段内最高温度以及对应的日期以及区域信息
	 * @param param
	 * @return
	 */
	TtnVo findUnderAreaMaxDate(TtnEntity param);

	/**
	 * 查询当前指定区域在指定时间段内最低温度以及对应的日期
	 * @param param
	 * @return
	 */
	TtnVo findMinAndDate(TtnEntity param);

	/**
	 * 查询当前指定区域的下级区域中在指定时间段内最低温度以及对应的日期以及区域信息
	 * @param param
	 * @return
	 */
	TtnVo findUnderAreaMinDate(TtnEntity param);

	/**
	 * 查询开始和结束日期时间段内有气温数据的所有日期
	 * @param param
	 * @return
	 */
	List<LocalDate> findTempTimes(TtnEntity param);

	/**
	 * 查询开始和结束日期时间段内所有气温数据
	 * @param param
	 * @return
	 */
	List<TtnVo> findAllTemp(TtnEntity param);

}