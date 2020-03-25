package com.jh.system.Enum;

/**
 * @description: 用于数据字典的验证返回
 * @version <1> 2018-04-16 wl： Created.
 */
public enum DictEnum {
    DICT_CODE_REPEAT("DICT_CODE_REPEAT","1","数据字典编码不可重复"),
    DICT_ID_REPEAT("DICT_ID_REPEAT","0","数据字典ID不可重复"),
    QUERY_DICT_SUCCESS("QUERY_DICT_SUCCESS","QUERY_DICT_SUCCESS","查询字典成功");


    private String key;
    private String value;
    private String mesasge;

    private DictEnum(String key, String value, String message){
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
