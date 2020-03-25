package com.jh.system.model;

import com.jh.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * description:
 *
 * @version <1> 2018-01-23 lcw： Created.
 */
@ApiModel(value = "RoleParam", description = "角色查询实体")
public class RoleParam extends PageEntity {
    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色组")
    private Integer roleType ;


    @ApiModelProperty(value="查询开始日期")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;


    @ApiModelProperty(value="查询结束日期")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;


    @ApiModelProperty(value="角色ID")
    private Integer roleId ;

    @ApiModelProperty(value="权限ID集合")
    private Integer[] resIds;

    @ApiModelProperty(value="订单文件阈值")
    private Integer orderFileNum;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer[] getResIds() {
        return resIds;
    }

    public void setResIds(Integer[] resIds) {
        this.resIds = resIds;
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
