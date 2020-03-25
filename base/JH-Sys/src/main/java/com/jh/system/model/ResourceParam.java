package com.jh.system.model;

import com.jh.entity.PageEntity;
import io.swagger.annotations.ApiModel;

/**
 * @description: 系统资源查询参数
 * @version <1> 2018-02-02 cxj： Created.
 */
@ApiModel(value = "ResourceParam", description = "系统资源查询参数")
public class ResourceParam extends PageEntity {
    private String resName ;

    private Integer parentId;

    private  boolean isLeaf;

    private String resCode;

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }
}
