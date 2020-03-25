package com.jh.layer.model;

/**
 * @description: 地图影像参数
 * @version <1> 2018-01-16 cxj： Created.
 */
public class MapImageParam {
    private String VERSION;
    private String SERVICE;
    private String REQUEST;
    private String FORMAT;
    private String TRANSPARENT;
    private String layers;
    private String CQL_FILTER;
    private String FILTER;
    private String CRS;
    private String STYLES;
    private String WIDTH;
    private String HEIGHT;
    private String BBOX;

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getSERVICE() {
        return SERVICE;
    }

    public void setSERVICE(String SERVICE) {
        this.SERVICE = SERVICE;
    }

    public String getREQUEST() {
        return REQUEST;
    }

    public void setREQUEST(String REQUEST) {
        this.REQUEST = REQUEST;
    }

    public String getFORMAT() {
        return FORMAT;
    }

    public void setFORMAT(String FORMAT) {
        this.FORMAT = FORMAT;
    }

    public String getTRANSPARENT() {
        return TRANSPARENT;
    }

    public void setTRANSPARENT(String TRANSPARENT) {
        this.TRANSPARENT = TRANSPARENT;
    }

    public String getLayers() {
        return layers;
    }

    public void setLayers(String layers) {
        this.layers = layers;
    }

    public String getCQL_FILTER() {
        return CQL_FILTER;
    }

    public void setCQL_FILTER(String CQL_FILTER) {
        this.CQL_FILTER = CQL_FILTER;
    }

    public String getCRS() {
        return CRS;
    }

    public void setCRS(String CRS) {
        this.CRS = CRS;
    }

    public String getSTYLES() {
        return STYLES;
    }

    public void setSTYLES(String STYLES) {
        this.STYLES = STYLES;
    }

    public String getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(String WIDTH) {
        this.WIDTH = WIDTH;
    }

    public String getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(String HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public String getBBOX() {
        return BBOX;
    }

    public void setBBOX(String BBOX) {
        this.BBOX = BBOX;
    }

    public String getFILTER() {
        return FILTER;
    }

    public void setFILTER(String FILTER) {
        this.FILTER = FILTER;
    }
}
