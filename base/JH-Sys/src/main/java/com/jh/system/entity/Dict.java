package com.jh.system.entity;

import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 字典实体类
 * @version <1> 2018-01-17 cxj： Created.
 */
@ApiModel(value = "数据字典实体")
public class Dict extends BaseEntity {
    @ApiModelProperty(value = "字典主键")
    private Integer dictId;
    @ApiModelProperty(value = "数据名称")
    private String dataName;
    @ApiModelProperty(value = "数据编码")
    private String dataCode;
    @ApiModelProperty(value = "数据值")
    private String dataValue;
    @ApiModelProperty(value = "序号")
    private Integer orderNo;
    @ApiModelProperty(value = "是否叶子节点")
    private boolean leaf;
    @ApiModelProperty(value = "上一级")
    private Integer parentId;
    @ApiModelProperty(value = "字典类型")
    private String dataType;


    private String parentName; //上级节点


    public Integer getDictId() {
        return dictId;
    }

    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
