package com.jh.system.entity;

import com.jh.entity.BaseEntity;

public class PermResource extends BaseEntity{

    private Integer resId;

    private String resCode;

    private Integer resType;

    private String resName;

    private String resUrl;

    private String icoStyle;

    private Short resNo;

    private Boolean isLeaf;

    private Integer parentId;


    private Integer roleId;

    private String parentName;

    private String resTypeName;

    private Boolean isParent;

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getIcoStyle() {
        return icoStyle;
    }

    public void setIcoStyle(String icoStyle) {
        this.icoStyle = icoStyle;
    }

    public Short getResNo() {
        return resNo;
    }

    public void setResNo(Short resNo) {
        this.resNo = resNo;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getResTypeName() {
        return resTypeName;
    }

    public void setResTypeName(String resTypeName) {
        this.resTypeName = resTypeName;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean parent) {
        isParent = parent;
    }
}