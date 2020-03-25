package com.jh.system.entity;

import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "角色对象")
public class PermRole extends BaseEntity {

    @ApiModelProperty(value = "角色编码")
    private String roleCode;


    @ApiModelProperty(value = "角色主键")
    private Integer roleId;


    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色组")
    private Integer roleType;

    @ApiModelProperty(value = "订单文件阈值")
    private Integer orderFileNum;

    private Integer accountId ;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getOrderFileNum() {
        return orderFileNum;
    }

    public void setOrderFileNum(Integer orderFileNum) {
        this.orderFileNum = orderFileNum;
    }
}