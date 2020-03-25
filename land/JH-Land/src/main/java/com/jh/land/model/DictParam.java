package com.jh.land.model;

import com.jh.vo.ResultMessage;

import java.time.LocalDate;

/**
 * description:  数据字典
 * @version <1> 2018/03/05 cxw： Created.
 */

public class DictParam {

    private Integer dictId;// 字典主键
    private String remark; // 备注
    private String dataStatus;// 数据状态（1有效，0无效)
    private LocalDate createTime ;// 创建时间
    private String creatorName;// 创建人名称
    private  Integer creator;//创建人
    private LocalDate modifyTime ;// 修改时间
    private String modifierName; // 修改人名称
    private  Integer modifier ;//修改人
    private String dataName ;// 数据名称
    private String dataCode ;// 数据代码
    private String dataValue  ;// 数据值
    private Integer orderNo ;//序号
    private boolean leaf ; // 是否叶子节点
    private Long parentId ;//上一级
    private Integer level; // 级别
    private String parentName; //上级节点
    private String delFlag; //删除标记
    private String dataType; //数据类型（0为系统初始化，1为用户增加）
    private Integer editId;//字典数据原始id
    private String editCode;//字典的原始code

    public Integer getDictId() {
        return dictId;
    }

    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public LocalDate getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDate modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getEditId() {
        return editId;
    }

    public void setEditId(Integer editId) {
        this.editId = editId;
    }


    public String getEditCode() {
        return editCode;
    }

    public void setEditCode(String editCode) {
        this.editCode = editCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 检查参数是否为空
     * @return
     * @version <1> 2018-2-2 cxw:Created.
     */
    public ResultMessage checkEditParam(){
        ResultMessage result = ResultMessage.success();
        if(this.dictId == null){
            result = ResultMessage.fail();
            result.setMsg("字典主键不能为空");
        }
        if(this.dataName == null){
            result = ResultMessage.fail();
            result.setMsg("数据名称不能为空");
        }
        if(this.dataCode == null){
            result = ResultMessage.fail();
            result.setMsg("数据编码不能为空");
        }
        if(this.orderNo == null){
            result = ResultMessage.fail();
            result.setMsg("序号不能为空");
        }
      if(this.dataValue == null){
            result = ResultMessage.fail();
            result.setMsg("数据值不能为空");
        }
       /* if(this.parentId == null){
            result = ResultMessage.fail();
            result.setMsg("父节点不能为空");
        }*/
        if((this.leaf+"")==null||(this.leaf+"").equals("")){
            result = ResultMessage.fail();
            result.setMsg("是否叶子节点不能为空");
        }
        if(this.dataStatus==null){
            result = ResultMessage.fail();
            result.setMsg("数据状态不能为空");
        }
        return result;
    }

    /**
     * 检查参数是否为空
     * @return
     * @version <1> 2018-2-2 cxw:Created.
     */
    public ResultMessage checkAddParam(){
        ResultMessage result = ResultMessage.success();
        if(this.dictId == null||this.dictId.equals("")){
            result = ResultMessage.fail();
            result.setMsg("数据ID不能为空");
            return result;
        }
        if(this.dataName == null||this.dataName.equals("")){
            result = ResultMessage.fail();
            result.setMsg("数据名称不能为空");
            return result;
        }
        if(this.dataCode == null||this.dataCode.equals("")){
            result = ResultMessage.fail();
            result.setMsg("数据编码不能为空");
            return result;
        }
        if(this.orderNo == null||this.orderNo.equals("")){
            result = ResultMessage.fail();
            result.setMsg("序号不能为空");
            return result;
        }
        if(this.dataValue == null||this.dataValue.equals("")){
            result = ResultMessage.fail();
            result.setMsg("数据值不能为空");
            return result;
        }
        if((this.leaf+"")==null||(this.leaf+"").equals("")){
            result = ResultMessage.fail();
            result.setMsg("是否叶子节点不能为空");
            return result;
        }
       /* if(this.parentId == null||this.parentId .equals("")){
            result = ResultMessage.fail();
            result.setMsg("父节点不能为空");
        }*/
        if(this.dataStatus==null||this.dataStatus.equals("")){
            result = ResultMessage.fail();
            result.setMsg("数据状态不能为空");
            return result;
        }
        return result;
    }

}
