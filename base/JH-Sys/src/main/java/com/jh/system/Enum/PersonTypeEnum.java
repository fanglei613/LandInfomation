package com.jh.system.Enum;

/**
 * Description: 用户类型枚举类
 *
 * @version <1> 2018-03-15  lcw : Created.
 */
public enum PersonTypeEnum {

    PERSON_TYPE_INNER(1701,"PERSON_TYPE_INNER",1,"内部用户"),
    PERSON_TYPE_OUTER(1702,"PERSON_TYPE_OUTER",2,"外部用户"),
    PERSON_TYPE_COLLECTION(1703,"PERSON_TYPE_OUTER",3,"外部采集APP用户");

    private Integer id;
    private String key;
    private Integer value;
    private String msg;
    private PersonTypeEnum(Integer id, String key,Integer value,String msg){
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
