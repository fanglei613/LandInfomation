package com.jh.wechat.enums;

/**
 * Created by XZG on 2018/5/4.
 */
public enum WechatUserEnum {

    NOT_REGIST("NOT_REGIST","NOT_REGIST","用户未注册"),
    BRIEFING("BRIEFING","BRIEFING","用户已订阅简报"),
    BRIEFING_SUCCESS("BRIEFING_SUCCESS","BRIEFING_SUCCESS","简报订阅成功"),
    BRIEFING_FAIL("BRIEFING_FAIL","BRIEFING_FAIL","简报订阅失败");

    private String key;
    private String value;
    private String message;

    private WechatUserEnum(String key, String value, String message){
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
