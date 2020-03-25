package com.jh.ttn.vo;

/**
 *description: 7天降雨数据对象
 * @version <1> 2018-04-28 cxw: Created.
 */
public class TrmmSevenVo {

    private String date; //日期
    private Long region_id;//区域
    private double val; //日均降雨值
    private double avg; //当前日期7天均值
    private double lastavg;//上期7天均值
    private double lastyearavg;//去年上期7天均值
    private String region_name;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Long region_id) {
        this.region_id = region_id;
    }

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        this.val = val;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getLastavg() {
        return lastavg;
    }

    public void setLastavg(double lastavg) {
        this.lastavg = lastavg;
    }

    public double getLastyearavg() {
        return lastyearavg;
    }

    public void setLastyearavg(double lastyearavg) {
        this.lastyearavg = lastyearavg;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
}
