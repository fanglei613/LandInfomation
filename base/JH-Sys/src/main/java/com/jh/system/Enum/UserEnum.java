package com.jh.system.Enum;

/**
 * @description: 用于用户返回消息格式制定
 * @version <1> 2018-01-12 cxj： Created.
 * @version <2> 2018/1/23 djh： update.
 *      添加了账号不存在实例
 */
public enum UserEnum {
    ACCOUNT_NAME_EMPTY("ACCOUNT_NAME_EMPTY","00010001","账户为空"),
    ACCOUNT_PWD_EMPTY("ACCOUNT_PWD_EMPTY","00010002","密码为空"),
    ACCOUNT_NAME_NOT_EXIST("ACCOUNT_NAME_NOT_EXIST","00010003","账户不存在"),
    ACCOUNT_NAME_EXIST("ACCOUNT_NAME_EXIST","00010004","账户存在"),
    USER_NOT_LOGIN("USER_NOT_LOGIN","00010005","用户未登录"),
    ACCOUNT_OR_PASSWORD_ERROR("ACCOUNT_OR_PASSWORD_ERROR","00010006","用户名或密码错误"),
    USER_NOT_PERM_ACCESS("USER_NOT_PERM_ACCESS","00010007","用户无权限访问");


    private String key;
    private String value;
    private String mesasge;

    private UserEnum(String key, String value, String message){
        this.key = key;
        this.value = value;
        this.mesasge = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMesasge() {
        return mesasge;
    }

    public void setMesasge(String mesasge) {
        this.mesasge = mesasge;
    }
}
