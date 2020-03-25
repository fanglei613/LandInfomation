package com.jh.system.Enum;

/**
 * @description: 用于资源返回消息格式制定
 * @version <1> 2018-03-19 cxj： Created.
 */
public enum ResourceEnum {
    RESOURCE_BUTTON_NO_JURISDICTION("RESOURCE_BUTTON_NO_JURISDICTION","00020001","按钮无操作权限"),
    RESOURCE_CODE_REPEAT("RESOURCE_CODE_REPEAT","00020002","编码ID不可重复"),
    RESOURCE_NAME_REPEAT("RESOURCE_NAME_REPEAT","00020003","资源名称不可重复");


    private String key;
    private String value;
    private String mesasge;

    private ResourceEnum(String key, String value, String message){
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
