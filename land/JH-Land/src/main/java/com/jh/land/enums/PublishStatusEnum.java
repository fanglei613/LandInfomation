package com.jh.land.enums;

/**
 * 发布状态枚举
 * @version <1> 2019/8/28 9:59 zhangshen:Created.
 */
public enum PublishStatusEnum {
    PUBLISH_STATUS_WFB("publish_status_wfb",2701,"未发布"),
    PUBLISH_STATUS_DFB("publish_status_dfb",2702,"待发布"),
    PUBLISH_STATUS_FBCG("publish_status_fbcg",2703,"发布成功"),
    PUBLISH_STATUS_FBSB("publish_status_fbsb",2704,"发布失败");

    private String key;
    private Integer value;
    private String text;

    PublishStatusEnum(String key, Integer value, String text){
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
