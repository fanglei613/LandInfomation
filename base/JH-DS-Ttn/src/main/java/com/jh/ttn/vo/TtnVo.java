/**
* 用于定义两个服务查询的结果集：
* 服务1 ：查询当前日期及同期数据环比：
* 服务2 ：查询当前日期截止的三年数据与十年均值数据：
*
* @version <1> 2017-11-17 Hayden:Created.
*/
package com.jh.ttn.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * description:降雨地温返回对象
 * @version <1> 2018-04-27 cxw: Created.
 */
public class TtnVo {

    private Long regionId; //区域ID

    private String regionName; //区域名称

    private Float value; //当前降雨或地温值

    private Float lastValue; //上一期降雨或地温值

    private Float percent; //当前降雨或地温值相对于上一期的增长率

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date; //日期

    private String monthAndDay; //日期（月-日）

    private Integer year; //年

    private String cropName; //作物名称

    private String regionCode;//区域代码

    private Float maxValue;//气温当日最大值

    private Float minValue;//气温当日最小值

    private String timeFlag;//时间标签

    private Float lastMaxValue;//上期最大值

    private Float lastMinValue;//上期最小值

    private Float maxPercent;//最大值的与上期同比的增长率

    private Float minPercent;//最小值的与上期同比的增长率

    private String remark;//备注

    private String weather;//天气

    private Integer resolution;//精度


    private String dataMonth; //降雨地温数据所属年月

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getLastValue() {
        return lastValue;
    }

    public void setLastValue(Float lastValue) {
        this.lastValue = lastValue;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMonthAndDay() {
        return monthAndDay;
    }

    public void setMonthAndDay(String monthAndDay) {
        this.monthAndDay = monthAndDay;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Float maxValue) {
        this.maxValue = maxValue;
    }

    public Float getMinValue() {
        return minValue;
    }

    public void setMinValue(Float minValue) {
        this.minValue = minValue;
    }

    public String getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(String timeFlag) {
        this.timeFlag = timeFlag;
    }

    public Float getLastMaxValue() {
        return lastMaxValue;
    }

    public void setLastMaxValue(Float lastMaxValue) {
        this.lastMaxValue = lastMaxValue;
    }

    public Float getLastMinValue() {
        return lastMinValue;
    }

    public void setLastMinValue(Float lastMinValue) {
        this.lastMinValue = lastMinValue;
    }

    public Float getMaxPercent() {
        return maxPercent;
    }

    public void setMaxPercent(Float maxPercent) {
        this.maxPercent = maxPercent;
    }

    public Float getMinPercent() {
        return minPercent;
    }

    public void setMinPercent(Float minPercent) {
        this.minPercent = minPercent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public String getDataMonth() {
        return dataMonth;
    }

    public void setDataMonth(String dataMonth) {
        this.dataMonth = dataMonth;
    }
}