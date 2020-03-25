package com.jh.entity;

import javax.persistence.Transient;
/**
 * description:分页查询条件
 * @version <1> 2018-05-08 cxw： Created.
 */
public class PageEntity {

    //jqgrid分页参数
    private int rows;

    //jqgrid分页参数
    private int page;

    //是否需要当前登录人信息查询的标志
    private boolean userFlag;

    @Transient
    private String sidx;

    @Transient
    private String sord;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public boolean isUserFlag() {
        return userFlag;
    }

    public void setUserFlag(boolean userFlag) {
        this.userFlag = userFlag;
    }
}
