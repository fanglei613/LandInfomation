package com.jh.system.model;

/**
 *树节点对象
 * Created by cxw on 2017-12-29.
 */
public class DictTreeReturn {
    private Integer id ;// 主键
    private String name ;// 节点名
    private Long pId ;//上级节点
    private  Boolean isParent=false ; //true为父节点，false为根节点

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }
}
