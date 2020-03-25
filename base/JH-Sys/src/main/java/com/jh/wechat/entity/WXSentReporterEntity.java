package com.jh.wechat.entity;

import java.time.LocalDateTime;

/**
 * 记录微信已发送简报消息实体
 * @version <1> 2018-05-16 lxy： Created.
 */
public class WXSentReporterEntity {

    private String wxId;//微信ID
    private Long reporterId;//简报编号
    private LocalDateTime sentTime;//发送时间

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }
}
