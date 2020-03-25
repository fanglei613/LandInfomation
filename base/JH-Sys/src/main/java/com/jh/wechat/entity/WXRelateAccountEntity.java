package com.jh.wechat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 微信注册用户关联信息
 * Created by XZG on 2018/4/27.
 */
public class WXRelateAccountEntity {

    private Integer id;
    private String wxId;//微信ID
    private String tel;//手机号
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date registerTime;//注册时间

    private String verifCode;//短信验证码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getVerifCode() {
        return verifCode;
    }

    public void setVerifCode(String verifCode) {
        this.verifCode = verifCode;
    }
}
