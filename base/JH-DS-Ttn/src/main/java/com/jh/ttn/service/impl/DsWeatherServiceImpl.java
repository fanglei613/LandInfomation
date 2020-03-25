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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Override
    public ResultMessage findRankWeatherList(Long regionId, String date1) {
        LocalDate date = LocalDate.parse(date1, DateTimeFormatter.ofPattern("yyyy-MM"));
        LocalDate first = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = date.with(TemporalAdjusters.lastDayOfMonth());
        long days = first.until(last, ChronoUnit.DAYS);
        LocalDate startDate = LocalDate.parse(first.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate endDate = LocalDate.parse(last.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        TtnEntity param = new TtnEntity();
        param.setRegionId(regionId);
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        List<TtnVo> weatherList = weatherMapper.findWeatherForRank(param);
        List<TtnVo> ttnList = weatherMapper.findTtnForRank(param);
        List<TtnVo> trmmList = weatherMapper.findTrmmForRank(param);
        List<Map<String,Object>> meteorologicalPhenomena = new ArrayList<>();

        for(int i = 0 ;i < weatherList.size();i++){
            Map<String,Object> mapWeather = (Map<String,Object>)weatherList.get(i);
            LocalDate dataTime = LocalDate.parse(mapWeather.get("DATE").toString());//时间
            for(int j=0;j<ttnList.size();j++){
                Map<String,Object> mapTtn = (Map<String,Object>)ttnList.get(i);
                LocalDate dataTimeTtn = LocalDate.parse(mapWeather.get("DATE").toString());//时间
                if(dataTime.equals(dataTimeTtn)){
                    mapWeather.put("ttn",mapTtn.get("value"));
                }
                for(int k=0;k<trmmList.size();k++){
                    LocalDate dataTimeTrmm = LocalDate.parse(mapWeather.get("DATE").toString());//时间
                    Map<String,Object> mapTrmm = (Map<String,Object>)trmmList.get(i);
                    if(dataTime.equals(dataTimeTrmm)){
                        mapWeather.put("trmm",mapTrmm.get("value"));
                    }
                }
            }
            meteorologicalPhenomena.set(i,mapWeather);
        }
        return ResultMessage.success(meteorologicalPhenomena);
    }

    @Override
    public ResultMessage findRankWeather(Long regionId, String date1) {

        LocalDate date = LocalDate.parse(date1, DateTimeFormatter.ofPattern("yyyy-MM"));
        LocalDate first = date.with(TemporalAdjusters.firstDayOfYear());
        LocalDate last = date.with(TemporalAdjusters.lastDayOfYear());
        long days = first.until(last, ChronoUnit.DAYS);
        LocalDate startDate = LocalDate.parse(first.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate endDate = LocalDate.parse(last.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        TtnEntity param = new TtnEntity();
        param.setRegionId(regionId);
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        //累计降水  全年改地区累计
        BigDecimal totalTrmm = weatherMapper.totalTrmm(param);
        //日均降雨
        BigDecimal avgTrmm = weatherMapper.avgTrmm(param);
        //日均地温
        BigDecimal avgTtn= weatherMapper.avgTtn(param);
        //最低气温
        Integer minWeather= weatherMapper.minWeather(param);
        //日均气温
        Integer avgWeather= weatherMapper.avgWeather(param);
        //最高气温
        Integer maxWeather= weatherMapper.maxWeather(param);
        Map<String,Object> basicFacts = new HashMap<>();
        basicFacts.put("totalTrmm",totalTrmm);
        basicFacts.put("avgTrmm",avgTrmm);
        basicFacts.put("avgTtn",avgTtn);
        basicFacts.put("minWeather",minWeather);
        basicFacts.put("avgWeather",avgWeather);
        basicFacts.put("maxWeather",maxWeather);
        return ResultMessage.success(basicFacts);
    }

    public static void main(String[] args){
        LocalDate date = LocalDate.parse("2019-08", DateTimeFormatter.ofPattern("yyyy-MM"));
        LocalDate first = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = date.with(TemporalAdjusters.lastDayOfMonth());
        long days = first.until(last, ChronoUnit.DAYS);
        System.out.println(days);
        System.out.println(first.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println(last.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }


}
