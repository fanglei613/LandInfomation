package com.jh.system.model;

import java.math.BigDecimal;

public class RankParam {
    private Long parentId;

    private Integer rankNum;

    private BigDecimal areaSum;


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
