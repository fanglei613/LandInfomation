package com.jh.land.enums;

/*
*区域 返回 标志
* @version <1> 2018-05-07 xzg : Created.
*/
public enum RegionEnum {
    QUERY_REGION_SUCCESS("QUERY_REGION_SUCCESS","QUERY_REGION_SUCCESS","查询区域成功");

    private String key;
    private String value;
    private String message;

    private RegionEnum(String key, String value, String message){
        this.key = key;
        this.value = value;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
