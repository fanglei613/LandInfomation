package com.jh.util.sms;

/**
 * 邮箱参数对象
 * @version <1> 2018-06-07 cxw： Created.
 */
public class EmailParam {

    private String personName; //姓名
    private String mobile; //电话号码
    private String eamil; //邮箱
    private String company; //公司名称
    private String remark; //备注

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEamil() {
        return eamil;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
