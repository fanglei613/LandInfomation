package com.jh.ttn.mapping;

import com.jh.ttn.vo.TrmmSevenVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * description:7天降雨数据集服务:
 * @version <1> 2018-06-12 cxw: Created.
 */
@Component
@Mapper
public interface IDsTrmmForSevenMapper {

	/*
     *根据区域构造7天降雨数据查询服务
     *@param TtnEntity : 参数，（区域ID，起止日期）
     *@return List<TtnVo> : 降雨数据
     *@Version <1> 2018-06-12 cxw: Created.
     */
	List<TrmmSevenVo> findTrmmForSeven(Map<String, Object> map);

}