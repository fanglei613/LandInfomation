package com.jh.system.Enum;

/**
 * Description:
 *
 * @version <1> 2018-07-13  lcw : Created.
 */
public enum AccountTypeEnum {

    ACCOUNT_TYPE_WX(1901,"微信端"),
    ACCOUNT_TYPE_WEB(1902,"网页端");

    private Integer code;
    private String title;

    private AccountTypeEnum( Integer code, String title){
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
