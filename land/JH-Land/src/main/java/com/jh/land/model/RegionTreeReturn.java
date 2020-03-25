package com.jh.land.model;

/**
 * 区域树映射实体
 *
 * @version <1> 2018-01-31 djh： Created.
 * @version <2> 2018-02-10 djh： 新增regionLevel属性.
 */
public class RegionTreeReturn {
    private Long id;// 主键
    private String name;// 节点名
    private Long pId;//上级节点
    private String regionCode;//区域code
    private Boolean isParent = false; //true为父节点，false为根节点

    private Integer regionLevel;

    public Integer getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Integer regionLevel) {
        this.regionLevel = regionLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Boolean getParent() {
        return isParent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Boolean getIsParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}