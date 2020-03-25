package com.jh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.util.CacheUtil;
import com.jh.util.cache.IdTransform;

import java.time.LocalDateTime;


/**
 * @description: 项目实体类的基类
 * @version <1> 2018-01-12 cxj： Created.
 */
public class BaseEntity extends  PageEntity{
    private String dataStatus;
    private String delFlag;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @IdTransform(type= CacheUtil.CACHE_ACCOUNT_TYPE,propName = "creator")
    private String creatorName;
    private Integer creator;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;
    private String modifierName;
    private Integer modifier;
    private String remark;

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
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

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
