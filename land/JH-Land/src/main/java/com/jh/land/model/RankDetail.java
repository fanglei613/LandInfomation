package com.jh.land.model;

import com.jh.entity.PageEntity;

public class RankDetail extends PageEntity{

    //地块id
    private Long rankId;

    //年份 和 月份
    private String year;

    //月份
    private String month;

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
