//package com.jh.system.base;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//
//import java.time.LocalDateTime;
//
///**
// * @description: 项目实体类的基类
// * @version <1> 2018-01-12 cxj： Created.
// */
//@ApiModel(value = "BaseEntity",description = "实体基类")
//public class BaseEntity extends  PageEntity{
//    @ApiModelProperty(value = "数据状态")
//    private String dataStatus;
//    @ApiModelProperty(value = "删除标记")
//    private String delFlag;
//    @ApiModelProperty(value = "创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createTime;
//    @ApiModelProperty(value = "创建人名称")
//    private String creatorName;
//    @ApiModelProperty(value = "创建人")
//    private Integer creator;
//    @ApiModelProperty(value = "修改时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime modifyTime;
//    @ApiModelProperty(value = "修改人名称")
//    private String modifierName;
//    @ApiModelProperty(value = "修改人")
//    private Integer modifier;
//    @ApiModelProperty(value = "备注")
//    private String remark;
//
//    public String getDataStatus() {
//        return dataStatus;
//    }
//
//    public void setDataStatus(String dataStatus) {
//        this.dataStatus = dataStatus;
//    }
//
//    public String getDelFlag() {
//        return delFlag;
//    }
//
//    public void setDelFlag(String delFlag) {
//        this.delFlag = delFlag;
//    }
//
//    public LocalDateTime getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(LocalDateTime createTime) {
//        this.createTime = createTime;
//    }
//
//    public String getCreatorName() {
//        return creatorName;
//    }
//
//    public void setCreatorName(String creatorName) {
//        this.creatorName = creatorName;
//    }
//
//    public Integer getCreator() {
//        return creator;
//    }
//
//    public void setCreator(Integer creator) {
//        this.creator = creator;
//    }
//
//    public LocalDateTime getModifyTime() {
//        return modifyTime;
//    }
//
//    public void setModifyTime(LocalDateTime modifyTime) {
//        this.modifyTime = modifyTime;
//    }
//
//    public String getModifierName() {
//        return modifierName;
//    }
//
//    public void setModifierName(String modifierName) {
//        this.modifierName = modifierName;
//    }
//
//    public Integer getModifier() {
//        return modifier;
//    }
//
//    public void setModifier(Integer modifier) {
//        this.modifier = modifier;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//}
