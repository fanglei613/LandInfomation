package com.jh.system.Enum;

/**
 * Description:
 *
 * @version <1> 2018-03-18  lcw : Created.
 */
public enum RoleTypeEnum {

    SYSTEM_ROLE_WEB(16001,"SYSTEM_ROLE_WEB",1,"私有云平台"),
    SYSTEM_ROLE_SHOW(16002,"SYSTEM_ROLE_SHOW",2,"珈和农情遥感展示平台"),
    SYSTEM_ROLE_FORUM(16003,"SYSTEM_ROLE_FORUM",3,"珈和遥感社区");

    private Integer id;
    private String key;
    private Integer value;
    private String msg;
    private RoleTypeEnum(Integer id, String key,Integer value, String msg){
        this.id = id;
        this.key = key;
        this.value = value;
        this.msg = msg;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
