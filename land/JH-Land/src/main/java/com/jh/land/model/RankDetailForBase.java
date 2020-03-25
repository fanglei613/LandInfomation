package com.jh.land.model;

import java.math.BigDecimal;

public class RankDetailForBase {
    //地块编号
    private String rankNo;

    //用地分类
    private String rankType;

    //地块面积
    private BigDecimal rankArea;

    //地块地址
    private String rankAddress;

    //经纬度
    private String lonAndLat;

    public String getRankNo() {
        return rankNo;
    }

    public void setRankNo(String rankNo) {
        this.rankNo = rankNo;
    }

    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    public BigDecimal getRankArea() {
        return rankArea;
    }

    public void setRankArea(BigDecimal rankArea) {
        this.rankArea = rankArea;
    }

    public String getRankAddress() {
        return rankAddress;
    }

    public void setRankAddress(String rankAddress) {
        this.rankAddress = rankAddress;
    }

    public String getLonAndLat() {
        return lonAndLat;
    }

    public void setLonAndLat(String lonAndLat) {
        this.lonAndLat = lonAndLat;
    }
}
