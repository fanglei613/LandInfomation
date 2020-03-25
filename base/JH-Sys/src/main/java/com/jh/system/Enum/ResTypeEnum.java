package com.jh.system.Enum;

/**
 * @description: 用于用户返回消息格式制定
 * @version <1> 2018-01-12 cxj： Created.
 * @version <2> 2018/1/23 djh： update.
 *      添加了账号不存在实例
 */
public enum ResTypeEnum {
    Menu(202,"Menu","202","菜单"),
    Button(203,"Button","203","按钮"),
    Data(201,"Data","201","系统");

    private Integer id;
    private String key;
    private String value;
    private String mesasge;

    private ResTypeEnum(Integer id , String key, String value, String message){
        this.id = id;
        this.key = key;
        this.value = value;
        this.mesasge = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
