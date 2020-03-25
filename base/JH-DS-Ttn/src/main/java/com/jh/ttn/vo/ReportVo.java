package com.jh.ttn.vo;
import java.time.LocalDate;

/**
 * description:生成降温、地温报告对象
 * @version <1> 2018-05-02 cxw: Created.
 */
public class ReportVo {

    private double total; //当年累计降雨/地温数据
    private String addNumText1; //为当年减上年同期数据
    private String addNumText2; //当年减10年同期数据
    private String addNumText3; //当年减上期数据
    private String minAreaVal; //最小降雨/地温区域及值
    private String maxAreaVal; //最大降雨/地温区域及值
    private double maxVal; //最大降雨/地温值(下级区域)
    private String  maxArea; //最大降雨/地温值区域(下级区域)
    private double mostval;//大部分区域降水值
    private String mostArea;//大部分区域
    private double lastval;//上一年数据
    private double lastDateVal;//上一期数据
    private double tenYearsVal;//10年同期数据
    private LocalDate date; //最大降雨/地温日期
    private LocalDate maxDate; //最大降雨/地温日期(下一级区域)
    private double maxParentVal; //最大降雨/地温值(本区域)
    private String  maxParentArea; //最大降雨/地温值区域(本区域)
    private String startDate;//开始日期
    private String endDate;//结束日期
    private String levelStr;//雨量级别
    private String mdate;//最大降雨/地温日期
    private String mxDate; //最大降雨/地温日期(下一级区域)
    private String chinaName;//选择的区域名
    private double avgMinVal;//平均最低气温
    private double avgMaxVal;//平均最高气温
    private double lastDateMinVal;//上期最低气温
    private double lastMinval;//去年同期最低气温
    private double twoYearsMinVal;//前年最低气温
    private double lastDateMaxVal;//上期最高气温
    private String addNumText30;//当前最高气温减去上期最高气温的数据
    private double lastMaxval;//去年同期最高气温
    private String addNumText10;//当前最高气温减去去年同期最高气温的数据
    private double twoYearsMaxVal;//前年同期最高温
    private String addNumText20;//当前最高气温减去前年同期最高气温的数据
    private String m2date;//当前区域最低温日期
    private double minParentVal;//当前区域最低温度
    private String mx2Date;//下级区域最低温日期
    private double minVal;//下级区域最低温
    private String minArea;//最低温的下级区域

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getAddNumText1() {
        return addNumText1;
    }

    public void setAddNumText1(String addNumText1) {
        this.addNumText1 = addNumText1;
    }

    public String getAddNumText2() {
        return addNumText2;
    }

    public void setAddNumText2(String addNumText2) {
        this.addNumText2 = addNumText2;
    }

    public String getAddNumText3() {
        return addNumText3;
    }

    public void setAddNumText3(String addNumText3) {
        this.addNumText3 = addNumText3;
    }

    public String getMinAreaVal() {
        return minAreaVal;
    }

    public void setMinAreaVal(String minAreaVal) {
        this.minAreaVal = minAreaVal;
    }

    public String getMaxAreaVal() {
        return maxAreaVal;
    }

    public void setMaxAreaVal(String maxAreaVal) {
        this.maxAreaVal = maxAreaVal;
    }

    public double getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(double maxVal) {
        this.maxVal = maxVal;
    }

    public String getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(String maxArea) {
        this.maxArea = maxArea;
    }

    public double getMostval() {
        return mostval;
    }

    public void setMostval(double mostval) {
        this.mostval = mostval;
    }

    public String getMostArea() {
        return mostArea;
    }

    public void setMostArea(String mostArea) {
        this.mostArea = mostArea;
    }

    public double getLastval() {
        return lastval;
    }

    public void setLastval(double lastval) {
        this.lastval = lastval;
    }

    public double getLastDateVal() {
        return lastDateVal;
    }

    public void setLastDateVal(double lastDateVal) {
        this.lastDateVal = lastDateVal;
    }

    public double getTenYearsVal() {
        return tenYearsVal;
    }

    public void setTenYearsVal(double tenYearsVal) {
        this.tenYearsVal = tenYearsVal;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(LocalDate maxDate) {
        this.maxDate = maxDate;
    }

    public double getMaxParentVal() {
        return maxParentVal;
    }

    public void setMaxParentVal(double maxParentVal) {
        this.maxParentVal = maxParentVal;
    }

    public String getMaxParentArea() {
        return maxParentArea;
    }

    public void setMaxParentArea(String maxParentArea) {
        this.maxParentArea = maxParentArea;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLevelStr() {
        return levelStr;
    }

    public void setLevelStr(String levelStr) {
        this.levelStr = levelStr;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getMxDate() {
        return mxDate;
    }

    public void setMxDate(String mxDate) {
        this.mxDate = mxDate;
    }

    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }

    public double getAvgMinVal() {
        return avgMinVal;
    }

    public void setAvgMinVal(double avgMinVal) {
        this.avgMinVal = avgMinVal;
    }

    public double getAvgMaxVal() {
        return avgMaxVal;
    }

    public void setAvgMaxVal(double avgMaxVal) {
        this.avgMaxVal = avgMaxVal;
    }

    public double getLastDateMinVal() {
        return lastDateMinVal;
    }

    public void setLastDateMinVal(double lastDateMinVal) {
        this.lastDateMinVal = lastDateMinVal;
    }

    public double getLastMinval() {
        return lastMinval;
    }

    public void setLastMinval(double lastMinval) {
        this.lastMinval = lastMinval;
    }

    public double getTwoYearsMinVal() {
        return twoYearsMinVal;
    }

    public void setTwoYearsMinVal(double twoYearsMinVal) {
        this.twoYearsMinVal = twoYearsMinVal;
    }

    public double getLastDateMaxVal() {
        return lastDateMaxVal;
    }

    public void setLastDateMaxVal(double lastDateMaxVal) {
        this.lastDateMaxVal = lastDateMaxVal;
    }

    public String getAddNumText30() {
        return addNumText30;
    }

    public void setAddNumText30(String addNumText30) {
        this.addNumText30 = addNumText30;
    }

    public double getLastMaxval() {
        return lastMaxval;
    }

    public void setLastMaxval(double lastMaxval) {
        this.lastMaxval = lastMaxval;
    }

    public String getAddNumText10() {
        return addNumText10;
    }

    public void setAddNumText10(String addNumText10) {
        this.addNumText10 = addNumText10;
    }

    public double getTwoYearsMaxVal() {
        return twoYearsMaxVal;
    }

    public void setTwoYearsMaxVal(double twoYearsMaxVal) {
        this.twoYearsMaxVal = twoYearsMaxVal;
    }

    public String getAddNumText20() {
        return addNumText20;
    }

    public void setAddNumText20(String addNumText20) {
        this.addNumText20 = addNumText20;
    }

    public String getM2date() {
        return m2date;
    }

    public void setM2date(String m2date) {
        this.m2date = m2date;
    }

    public double getMinParentVal() {
        return minParentVal;
    }

    public void setMinParentVal(double minParentVal) {
        this.minParentVal = minParentVal;
    }

    public String getMx2Date() {
        return mx2Date;
    }

    public void setMx2Date(String mx2Date) {
        this.mx2Date = mx2Date;
    }

    public double getMinVal() {
        return minVal;
    }

    public void setMinVal(double minVal) {
        this.minVal = minVal;
    }

    public String getMinArea() {
        return minArea;
    }

    public void setMinArea(String minArea) {
        this.minArea = minArea;
    }
}
