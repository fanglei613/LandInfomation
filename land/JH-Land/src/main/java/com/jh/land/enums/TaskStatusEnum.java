package com.jh.land.enums;

/**
 * 任务状态枚举
 * @version <1> 2019/8/28 9:59 zhangshen:Created.
 */
public enum TaskStatusEnum {
    NOACTIVE("noActive",1001,"未激活"),
    TOBEEXECUTED("toBeExecuted",1002,"待处理"),
    EXECUTING("executing",1003,"处理中"),
    SUCCESS("success",1004,"处理成功"),
    FAIL("fail",1005,"处理失败");

    private String key;
    private Integer value;
    private String text;

    TaskStatusEnum(String key, Integer value, String text){
        this.key = key;
        this.value = value;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
