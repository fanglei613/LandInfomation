package com.jh.system.model;


import com.jh.entity.PageEntity;

/**
 * @version <1> 2017-03-12 xzg： Created.
 */
public class LogParam extends PageEntity {

    private String operatorName;//操作人
    private String startLogDate;//开始时间
    private String endLogDate;//结束时间
    private String logContentKey;//关键字

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getStartLogDate() {
        return startLogDate;
    }

    public void setStartLogDate(String startLogDate) {
        this.startLogDate = startLogDate;
    }

    public String getEndLogDate() {
        return endLogDate;
    }

    public void setEndLogDate(String endLogDate) {
        this.endLogDate = endLogDate;
    }

    public String getLogContentKey() {
        return logContentKey;
    }

    public void setLogContentKey(String logContentKey) {
        this.logContentKey = logContentKey;
    }
}
