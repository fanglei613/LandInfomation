package com.jh.enums;

/**
 * @description: Yes or No Enum .
 * @version <1> 2018/1/22 djh： Created.
 */
public enum IsEnum {
    YES("Yes","1","是"),
	NO("No","0","否"),
	Error("Error","2","属性检查错误"),
    MAX("MAX","3","短信发送次数已达上限");
	
	private String key ;
    private String value;
    private String text;

    private IsEnum(String key, String value, String text){
        this.key = key;
        this.value = value;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString(){
        return this.getValue();
    }
}