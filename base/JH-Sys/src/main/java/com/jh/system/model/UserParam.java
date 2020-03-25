package com.jh.system.model;

import com.jh.entity.PageEntity;
import com.jh.system.entity.PermAccount;

/**
 * @description: 用户参数
 * @version <1> 2018-01-12 cxj： Created.
 * @version <2> 2018-01-23 djh： update.
 *    添加了一个用于接收图形验证码的字段 verifyCode
 *    添加了一个用于接收手机号的字段 tel
 *    添加了一个用于接收手机验证码的字段 phoneValicode
 */
public class UserParam extends PageEntity {
    private PermAccount permAccount;

    /**该字段用于接收图形验证码*/
    private String verifyCode;
    private String tel;//手机号
    private String phoneValicode;//手机验证码
    private String validToken;
    private String appMenu;  //app菜单ID集合

    public PermAccount getPermAccount() {
        return permAccount;
    }

    public void setPermAccount(PermAccount permAccount) {
        this.permAccount = permAccount;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhoneValicode() {
        return phoneValicode;
    }

    public void setPhoneValicode(String phoneValicode) {
        this.phoneValicode = phoneValicode;
    }

    public String getValidToken() {
        return validToken;
    }

    public void setValidToken(String validToken) {
        this.validToken = validToken;
    }

    public String getAppMenu() {
        return appMenu;
    }

    public void setAppMenu(String appMenu) {
        this.appMenu = appMenu;
    }
}
