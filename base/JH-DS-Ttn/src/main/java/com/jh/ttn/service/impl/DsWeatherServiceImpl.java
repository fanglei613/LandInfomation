package com.jh.ttn.service.impl;

import com.jh.ttn.entity.TtnEntity;
import com.jh.ttn.mapping.IDsWeatherMapper;
import com.jh.ttn.service.IDsWeatherService;

import com.jh.ttn.vo.TtnVo;
import com.jh.util.DateUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Transactional
@Service
public class DsWeatherServiceImpl implements IDsWeatherService {

    @Autowired
    private IDsWeatherMapper weatherMapper;


    /*
     * 根据区域查询区域及下一级区域的天气预报数据
     *   regionId: 区域ID
     * @return ResultMessage
     * @version <1> 2018-11-02 Roach: Created.
     */
    @Override
    public ResultMessage findWeatherRegionAndBeyond(Long regionId) {
        ResultMessage result = ResultMessage.fail("查询失败");
        if(null != regionId){
            //封装天气预报条件查询对象
            TtnEntity param = new TtnEntity();
            param.setRegionId(regionId);
            //获取查询日期的数据
            List<TtnVo> curList = weatherMapper.findWeatherRegionAndBeyond(param);
            result = ResultMessage.success(curList);
        }
        return result;
    }

    /*
     *根据区域查询该区域的天气预报数据
     * @param 数据集查询参数
     *   regionId: 区域ID
     *@return ResultMessage
     * @version <1> 2018-11-02 Roach: Created.
     */
    @Override
    public ResultMessage findRegionWeather(Long regionId) {
        ResultMessage result = ResultMessage.fail("查询失败");
        if(null != regionId){
            //封装天气预报条件查询对象
            TtnEntity param = new TtnEntity();
            param.setRegionId(regionId);
            param.setStartDate(LocalDate.now());
            param.setEndDate(LocalDate.now().plusDays(2));
            //获取查询日期的数据
            List<TtnVo> curList = weatherMapper.findRegionWeather(param);
            result = ResultMessage.success(curList);
        }
        return result;
    }


}
