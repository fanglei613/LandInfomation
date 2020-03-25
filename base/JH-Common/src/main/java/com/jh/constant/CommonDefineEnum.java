package com.jh.constant;

/**
 * 通用接口常量
 *
 * @version <2> 2018/1/25 djh： update.
 *      添加一些状态
 */
public enum CommonDefineEnum {
    SUCCESS("SUCCESS","success","请求成功"),
    FAIL("FAIL","fail","请求失败"),
    STATUS("STATUS","status","返回状态"),
    CODE("CODE","code","状态码"),
    DATA("DATA","data","数据"),
    REQUEST_SUCCESS("REQUEST_SUCCESS","00000000","成功"),
    REQUEST_FAIL("REQUEST_FAIL","00000001","系统错误"),
    NO_LOGIN_FAIL("NO_LOGIN_FAIL","00000002","请登陆"),
    REGISTER_SUCCESS("REGISTER_SUCCESS","00000003","注册成功"),
    REGISTER_FAIL("REGISTER_FAIL","00000004","注册失败"),

    /***********************  2018/1/25 djh 添加内容 - 开始 ***********************/
    FIND_PARAMETER_NOT_EXISTS("FIND_PARAMETER_NOT_EXISTS", "01", "没有传入查询参数"),
    FIND_OBJECT_NOT_EXISTS("FIND_OBJECT_NOT_EXISTS", "02", "查询对象不存在"),
    RECORD_UPDATE_SUCCESS("RECORD_UPDATE_SUCCESS", "03", "记录更新成功"),
    RECORD_UPDATE_FAIL("RECORD_UPDATE_FAIL", "04", "记录更新失败"),
    SAVE_OBJECT_SUCCESS("SAVE_OBJECT_SUCCESS", "05", "对象保存成功"),
    SAVE_OBJECT_FAIL("SAVE_OBJECT_FAIL", "06", "对象保存失败"),
    DELETE_OBJECT_FAIL("DELETE_OBJECT_FAIL","07","对象删除失败"),
    DELETE_OBJECT_SUCCESS("DELETE_OBJECT_SUCCESS","08","对象删除成功"),
    ADD_PRODUCT_EXISTS("ADD_PRODUCT_EXISTS","09","数据权限已存在"),
    ADD_PRODUCT_SUCCESS("ADD_PRODUCT_EXISTS","10","添加数据权限成功"),
    ADD_PRODUCT_FAIL("ADD_PRODUCT_EXISTS","11","添加数据权限失败");
    /***********************  2018/1/25 djh 添加内容 - 结束 ***********************/

    private String key;
    private String value;
    private String mesasge;

    private CommonDefineEnum(String key, String value, String message){
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
