package com.jh.enums;


/**
 * 数据缓存类型
 * @version <1> 2019-03-13 cxw: Created.
 */
public enum DsEnum {
    distribution(1801,"分布"),
    yield(1802,"估产"),
    growth(1803,"长势"),
    t(1804,"地温"),
    trmm(1805,"降雨"),
    temperature(1807,"气温");

    private Integer id ;
    private String title;

    private DsEnum(Integer id, String title){
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title){this.title = title;}
    public String getTitle(){return this.title;}
}
