package com.jh.system.model;

import java.util.List;

/**
 * 根据区域id查询此区域下所有子孙元素,app用
 * @version <1> 2019/4/13 15:46 zhangshen:Created.
 */
public class RegionAppReturn {

    private Long value;
    private String text;
    private List<RegionAppReturn> children;
    private Boolean isParent = false; //true为父节点，false为根节点

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<RegionAppReturn> getChildren() {
        return children;
    }

    public void setChildren(List<RegionAppReturn> children) {
        this.children = children;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }
}
