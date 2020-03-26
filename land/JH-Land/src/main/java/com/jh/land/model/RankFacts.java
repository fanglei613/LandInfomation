package com.jh.land.model;

import java.math.BigDecimal;

public class RankFacts {

    private String soilType;

    private String cropName;

    private String cropArea;

    private String totalTrmm;

    private String avgTrmm;

    private String avgTtn;

    private String minWeather;

    private String avgWeather;

    private String maxWeather;

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropArea() {
        return cropArea;
    }

    public void setCropArea(String cropArea) {
        this.cropArea = cropArea;
    }

    public String getTotalTrmm() {
        return totalTrmm;
    }

    public void setTotalTrmm(String totalTrmm) {
        this.totalTrmm = totalTrmm;
    }

    public String getAvgTrmm() {
        return avgTrmm;
    }

    public void setAvgTrmm(String avgTrmm) {
        this.avgTrmm = avgTrmm;
    }

    public String getAvgTtn() {
        return avgTtn;
    }

    public void setAvgTtn(String avgTtn) {
        this.avgTtn = avgTtn;
    }

    public String getMinWeather() {
        return minWeather;
    }

    public void setMinWeather(String minWeather) {
        this.minWeather = minWeather;
    }

    public String getAvgWeather() {
        return avgWeather;
    }

    public void setAvgWeather(String avgWeather) {
        this.avgWeather = avgWeather;
    }

    public String getMaxWeather() {
        return maxWeather;
    }

    public void setMaxWeather(String maxWeather) {
        this.maxWeather = maxWeather;
    }
}
