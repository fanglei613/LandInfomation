package com.jh.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* 登陆所需要参数
*
* @version <1> 2017-11-02 cxj : Created.
*/
@ApiModel(value = "LoginParam",description = "登陆所需要参数")
public class LoginParam {
    @ApiModelProperty(value = "登陆账号")
    private String accountName;
    @ApiModelProperty(value = "登陆密码")
    private String accountPwd;

    @ApiModelProperty(value = "新密码")
    private String newPwd;
    @ApiModelProperty(value = "短信验证码")
    private String smsVerifCode;

    @ApiModelProperty(value = "图形验证码")
    private String imgVerifyCode;

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

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
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
}
