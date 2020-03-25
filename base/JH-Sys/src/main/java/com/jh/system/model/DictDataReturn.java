package com.jh.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * description: 数据字典与关联表查询数据返回参数实体
 *
 * @version <1> 2018-1-24 cxw： Created.
 */

@ApiModel(value = "DictDataReturn",description = "数据字典与关联表查询数据集成对象")
public class DictDataReturn {

    @ApiModelProperty(value="字典ID")
    private Integer dictId;


    @ApiModelProperty(value = "字典名称")
    private String dataName;

    @ApiModelProperty(value = "字典代码")
    private String dataCode;

    @ApiModelProperty(value="关联表对应Id")
    private Integer dataId;

    @ApiModelProperty(value="字典值")
    private String dataValue;


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

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }
}
