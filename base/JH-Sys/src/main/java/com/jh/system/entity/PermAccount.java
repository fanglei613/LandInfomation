package com.jh.system.entity;

import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @description: 用户账户实体类
 * @version <1> 2018-01-11 cxj： Created.
 */
@ApiModel(value = "PermAccount",description = "用户账户信息")
public class  PermAccount extends BaseEntity{
    @ApiModelProperty(value = "账号主键")
    private Integer accountId;
    @ApiModelProperty(value = "账号")
    private String accountName;
    @ApiModelProperty(value = "密码")
    private String accountPwd;
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "用户信息")
    private PermPerson permPerson;

    @ApiModelProperty(value = "角色信息")
    private List<PermRole> roleList ;

    @ApiModelProperty(value = "默认首页")
    private String defaultMenu ;


    @ApiModelProperty(value = "数据权限信息")
    private List<DataProduct> permissions;

    @ApiModelProperty(value ="默认访问权限")
    private DataProduct defaultProduct;

    @ApiModelProperty(value = "用户角色")
    private String roles;

    @ApiModelProperty(value = "服务token")
    private String serviceToken;

    @ApiModelProperty(value = "注册类型")
    private Integer accountType;

    @ApiModelProperty(value = "账号code")
    private String accountCode;

    @ApiModelProperty(value = "用户关注的id")
    private Long regionId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public PermPerson getPermPerson() {
        return permPerson;
    }

    public void setPermPerson(PermPerson permPerson) {
        this.permPerson = permPerson;
    }

    public List<PermRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<PermRole> roleList) {
        this.roleList = roleList;
    }


    public String getDefaultMenu() {
        return defaultMenu;
    }

    public void setDefaultMenu(String defaultMenu) {
        this.defaultMenu = defaultMenu;
    }


    public List<DataProduct> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<DataProduct> permissions) {
        this.permissions = permissions;
    }


    public DataProduct getDefaultProduct() {
        return defaultProduct;
    }

    public void setDefaultProduct(DataProduct defaultProduct) {
        this.defaultProduct = defaultProduct;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getServiceToken() {
        return serviceToken;
    }

    public void setServiceToken(String serviceToken) {
        this.serviceToken = serviceToken;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }


    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
}
