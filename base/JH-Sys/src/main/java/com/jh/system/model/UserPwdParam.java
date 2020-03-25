package com.jh.system.model;


import com.jh.entity.PageEntity;

/**
 * @description: 用户修改密码传参POJO
 * @version <1> 2018-03-30 lxy： Created.
 */
public class UserPwdParam extends PageEntity {
    private Integer accountId; //用户账号编号
    private String oldPass;//旧密码
    private String newPass;//新密码
    private String truePass;//确认新密码

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getTruePass() {
        return truePass;
    }

    public void setTruePass(String truePass) {
        this.truePass = truePass;
    }
}
