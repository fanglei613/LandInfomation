package com.jh.system.Enum;

/**
 * Description: 用户性别枚举类
 * @version <1> 2018-03-15  lcw : Created.
 */
public enum PersonSexEnum {

    PERSON_SEX_MALE(301,"PERSON_SEX_MALE",1,"男"),
    PERSON_SEX_FEMALE(302,"PERSON_SEX_FEMALE",2,"女");

    private Integer id;
    private String key;
    private Integer value;
    private String msg;
    private PersonSexEnum(Integer id, String key,Integer value,String msg){
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
