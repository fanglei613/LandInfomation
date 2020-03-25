package com.jh.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* 注册所需的参数
*
* @version <1> 2018-05-10 cxw : Created.
*/
@ApiModel(value = "RegisterEntity", description = "注册所需的参数")
public class RegisterEntity {

    @ApiModelProperty(value = "账号名(账号code)")
    private String accountCode;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "真实姓名")
    private String personName;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "用户类型")
    private Integer personType ;


    @ApiModelProperty(value = "密码")
    private String pwd;

    @ApiModelProperty(value = "确认密码")
    private String confirmPwd;

    @ApiModelProperty(value = "短信验证码")
    private String smsVerifCode;

    @ApiModelProperty(value = "图形验证码")
    private String imgVerifyCode;

    @ApiModelProperty(value = "注册类型")
    private String accountType;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSmsVerifCode() {
        return smsVerifCode;
    }

    public void setSmsVerifCode(String smsVerifCode) {
        this.smsVerifCode = smsVerifCode;
    }

    public String getImgVerifyCode() {
        return imgVerifyCode;
    }

    public void setImgVerifyCode(String imgVerifyCode) {
        this.imgVerifyCode = imgVerifyCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}