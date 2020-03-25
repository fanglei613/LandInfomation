package com.jh.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

@ApiModel(value = "日志记录")
public class Logs {

    @ApiModelProperty(value = "日志记录主键")
    private Integer logId;

    @ApiModelProperty(value = "操作人名字")
    private String operatorName;

    @ApiModelProperty(value = "操作人ID")
    private Integer operator;

    @ApiModelProperty(value = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime opTime;

    @ApiModelProperty(value = "操作电脑IP")
    private String opIp;

    @ApiModelProperty(value = "操作内容")
    private String opContent;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public LocalDateTime getOpTime() {
        return opTime;
    }

    public void setOpTime(LocalDateTime opTime) {
        this.opTime = opTime;
    }

    public String getOpIp() {
        return opIp;
    }

    public void setOpIp(String opIp) {
        this.opIp = opIp;
    }

    public String getOpContent() {
        return opContent;
    }

    public void setOpContent(String opContent) {
        this.opContent = opContent;
    }
}