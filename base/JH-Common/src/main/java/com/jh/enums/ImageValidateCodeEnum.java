package com.jh.enums;

/**
 * 图形验证码类型
 * Created by XZG on 2018/5/15.
 */
public enum ImageValidateCodeEnum {

//    Web_Region_Image("web用户注册","webRegionImage"),
    Web_Image_code("用户验证码","ImageCode");


    private String name;
    private String redisCode;

    private ImageValidateCodeEnum(String name,String redisCode){
        this.name = name;
        this.redisCode = redisCode;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRedisCode() {
        return redisCode;
    }

    public void setRedisCode(String code) {
        this.redisCode = code;
    }
}
