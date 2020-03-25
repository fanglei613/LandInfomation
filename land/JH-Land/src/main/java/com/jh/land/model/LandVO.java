package com.jh.land.model;

import java.math.BigDecimal;

/**
 * 地块信息返回结果
 */
public class LandVO {

    // 行政区划编码
    private Long regionId;

    // 行政区划名称
    private String chinaName;

    // 地块数量
    private Integer rankNum;

    // 面积总数(亩)
    private BigDecimal areaSum;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }

    public Integer getRankNum() {
        return rankNum;
    }

    public void setRankNum(Integer rankNum) {
        this.rankNum = rankNum;
    }

    public BigDecimal getAreaSum() {
        return areaSum;
    }

    public void setAreaSum(BigDecimal areaSum) {
        this.areaSum = areaSum;
    }
}
