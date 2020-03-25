package com.jh.system.model;

import com.jh.entity.PageEntity;
import com.jh.system.entity.PermAccount;
import com.jh.system.entity.PermPerson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *  @description: 包括账户、用户信息
 *  @version <1> 2017-11-02 cxj : Created.
 */
@ApiModel(value = "PersonParam",description = "包括账户、用户信息")
public class PersonParam extends PageEntity {
    @ApiModelProperty(value = "用户信息")
    private PermPerson permPerson;
    @ApiModelProperty(value = "账户信息")
    private PermAccount permAccount;

    @ApiModelProperty(value = "人员ID")
    private Integer accountId;

    @ApiModelProperty(value = "姓名")
    private String personName;

    @ApiModelProperty(value = "账号")
    private String accountName ;

    @ApiModelProperty(value = "用户类型")
    private Integer personType;

    @ApiModelProperty(value = "角色Id集合")
    private String[] roleIds; //角色Id集合

    @ApiModelProperty(value = "数据状态")
    private String dataStatus;

    @ApiModelProperty(value = "工作单位")
    private String company;

    @ApiModelProperty(value = "关键词")
    private String keyWord;


    public PermPerson getPermPerson() {
        return permPerson;
    }

    public void setPermPerson(PermPerson permPerson) {
        this.permPerson = permPerson;
    }

    public PermAccount getPermAccount() {
        return permAccount;
    }

    public void setPermAccount(PermAccount permAccount) {
        this.permAccount = permAccount;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}