package com.jh.layer.model;

/**
 * @description: 地图数据参数
 * @version <1> 2018-01-16 cxj： Created.
 */
public class MapDataParam {
    private String typeName;
    private String service;
    private String request;
    private String outputFormat;
    private String CQL_FILTER;
    private String FILTER;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getCQL_FILTER() {
        return CQL_FILTER;
    }

    public void setCQL_FILTER(String CQL_FILTER) {
        this.CQL_FILTER = CQL_FILTER;
    }

    public String getFILTER() {
        return FILTER;
    }

    public void setFILTER(String FILTER) {
        this.FILTER = FILTER;
    }
}
