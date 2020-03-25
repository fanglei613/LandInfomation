package com.jh.land.enums;

public enum ProductTypeEnum {

    AREA("ds_area_rank",2602,"分布"),
    GROWTH("ds_growth_rank",2603,"长势"),
    YIELD("ds_yield_rank",2604,"估产"),
    RANK("init_rank",2601,"地块"),
    LAYER("ly_dev_dk",2600,"图层");

    private String key;
    private Integer value;
    private String text;

    ProductTypeEnum(String key, Integer value, String text){
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
