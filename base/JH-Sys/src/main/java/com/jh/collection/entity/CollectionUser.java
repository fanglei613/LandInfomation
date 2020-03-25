package com.jh.collection.entity;

import com.jh.entity.PageEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 字段模型实体
 *
 * @version <1> 2018-11-22 xy： Created.
 */
public class CollectionUser extends PageEntity implements Serializable {
    private static final long serialVersionUID = 42L;
    private Integer id; //主键ID
    @NotNull
    private String phone;//手机号
    @NotNull
    private String pwd;//密码
    private String wxId;//微信号
    private String qqId;//qq号
    private String nickName;//昵称
    private String userStatus;//状态
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private String createor;//创建者
    private String updateor;//更新者
    private String remark;//备注
    private Integer delFlag;//删除标志
    private String workplace;//工作单位
    private String job;//职位
    private String newPhone;//新手机号
    private String newPwd;//新密码


    //查询参数
    private String startCreateTime;//查询开始时间
    private String endCreateTime;//查询结束时间
    private String smsCode;//短信验证码

    private String version;//登录版本，唯一标识
    private int expireMinite;//过期时间
    private long expireFreshTime;//过期刷新时间

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private String sessionId;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getExpireMinite() {
        return expireMinite;
    }

    public void setExpireMinite(int expireMinite) {
        this.expireMinite = expireMinite;
    }

    public long getExpireFreshTime() {
        return expireFreshTime;
    }

    public void setExpireFreshTime(long expireFreshTime) {
        this.expireFreshTime = expireFreshTime;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(String startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId == null ? null : wxId.trim();
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId == null ? null : qqId.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateor() {
        return createor;
    }

    public void setCreateor(String createor) {
        this.createor = createor == null ? null : createor.trim();
    }

    public String getUpdateor() {
        return updateor;
    }

    public void setUpdateor(String updateor) {
        this.updateor = updateor == null ? null : updateor.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }
}