package com.jh.util.geoserver;

import com.jh.constant.PropertiesConstant;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.decoder.RESTStyleList;
import it.geosolutions.geoserver.rest.decoder.utils.NameLinkElem;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSPostGISDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class GeoServerUtil Purpose is to Operate Geoserver for : 
 * 1. Create a Workspace（when publish a style or tiff ,default check workspace exists）
 * 2. Create or update a Style
 * 3. Create or update a layer from a tif
 * <p>
 * @version 2018-06-29 lcw : Created.
 */
public class GeoServerUtil {
    private Logger logger = Logger.getLogger(GeoServerUtil.class);

    /**
     * 默认坐标常量定义 WGS 84 / UTM zone 51N
     */
    private static final  String SRS = "EPSG:4326";

    private String tifDirectory; //tif文件路径

    private NameValuePair gbkCharset = new NameValuePair("charset", "GBK");//SHP文件字符集

    private GSResourceEncoder.ProjectionPolicy shpPolicy = GSResourceEncoder.ProjectionPolicy.REPROJECT_TO_DECLARED;//SHP ProjectionPolicy

    private String geoUrl = null;
    private String geoUser = null;
    private String geoPass = null;
    private String wpName = null;//默认的工作空间

    private String workspace = "www.datall.cn";

    private String postgisHost = null;//数据库所在服务器地址
    private Integer postgisPort = 5432;//端口号
    private String postgisUser = null;//用户名
    private String postgisPass = null;//密码
    private String postgisDatabase = null;//数据库名称
    private String postgisSchema = null;//
    private Integer postgisConnectionTimeout = 20;
    private Integer postgisMaxConnections = 20;
    private Integer postgisMinConnections = 1;

    private GeoServerRESTReader reader = null;
    private GeoServerRESTPublisher publisher = null;
    private GeoServerRESTManager manager = null;


    public GeoServerUtil(){
//        this.geoUrl = "http://localhost:9999/geoserver";
//        this.geoUser = "admin";
//        this.geoPass = "geoserver";
//        this.wpName = "JiaHeDC";

        System.out.println("配置文件： " + PropertiesConstant.GEOSERVER_CONFIG);

        this.geoUrl = PropertyUtil.getPropertiesForConfig("GEOSERVER_URL", PropertiesConstant.GEOSERVER_CONFIG);
        this.geoUser = PropertyUtil.getPropertiesForConfig("GEOSERVER_USER",PropertiesConstant.GEOSERVER_CONFIG);
        this.geoPass = PropertyUtil.getPropertiesForConfig("GEOSERVER_PWD",PropertiesConstant.GEOSERVER_CONFIG);
        this.wpName = PropertyUtil.getPropertiesForConfig("WORKSPACE_DATACENTER",PropertiesConstant.GEOSERVER_CONFIG);

        this.postgisHost = PropertyUtil.getPropertiesForConfig("POSTGIS_HOST",PropertiesConstant.POSTGIS_CONFIG);
        this.postgisPort = Integer.parseInt(PropertyUtil.getPropertiesForConfig("POSTGIS_PORT",PropertiesConstant.POSTGIS_CONFIG));
        this.postgisUser = PropertyUtil.getPropertiesForConfig("POSTGIS_USER",PropertiesConstant.POSTGIS_CONFIG);
        this.postgisPass = PropertyUtil.getPropertiesForConfig("POSTGIS_PASSWORD",PropertiesConstant.POSTGIS_CONFIG);
        this.postgisDatabase = PropertyUtil.getPropertiesForConfig("POSTGIS_DATABASE",PropertiesConstant.POSTGIS_CONFIG);

        this.postgisSchema = PropertyUtil.getPropertiesForConfig("POSTGIS_SCHEMA",PropertiesConstant.POSTGIS_CONFIG);
        this.postgisConnectionTimeout = Integer.parseInt(PropertyUtil.getPropertiesForConfig("POSTGIS_CONNECTION_TIMEOUT",PropertiesConstant.POSTGIS_CONFIG));
        this.postgisMaxConnections = Integer.parseInt(PropertyUtil.getPropertiesForConfig("POSTGIS_MAX_CONNECTIONS",PropertiesConstant.POSTGIS_CONFIG));
        this.postgisMinConnections = Integer.parseInt(PropertyUtil.getPropertiesForConfig("POSTGIS_MIN_CONNECTIONS",PropertiesConstant.POSTGIS_CONFIG));
        initGeoServerUtil();
    }

    /**
     * init {reader} and {publisher}
     *
     */
    public void initGeoServerUtil(){
        try {
            URL url = new URL(geoUrl);
            manager = new GeoServerRESTManager(url,geoUser,geoPass);
            reader = new GeoServerRESTReader(geoUrl, geoUser, geoPass);
            publisher = new GeoServerRESTPublisher(geoUrl, geoUser, geoPass);
        } catch (Exception e) {
            logger.error("GerServerUtil does not connect GeoServer :" + e.getMessage());
            e.printStackTrace();
        }
    }




    /*******************************************************************************************************************
     * ************************************************  publish a tiff method  ****************************************
     * *****************************************************************************************************************
     * *****************************************************************************************************************/

    /**
     * publish a tiff
     * @param geotiff
     * @param styleName
     * @return
     * @version<1> 2018-07-02 lcw :Created.
     */
    public ResultMessage publishTiff(File geotiff, String styleName){
        if(geotiff == null){
            return ResultMessage.fail("file is null ,please set it");
        }
        if(StringUtils.isBlank(styleName)){
            return ResultMessage.fail("style is null, please set it");
        }

        String fileName = geotiff.getName();
        String preName = fileName.substring(0, fileName.lastIndexOf("."));

        String storeName = "store_" + preName;
        String layerName = "ly_" + preName;

        return publishTiff(wpName,storeName, layerName, geotiff, styleName, null);

    }


    /**
     * publish a tiff
     * @param geotiff
     * @param styleName
     * @return
     * @version<1> 2018-07-02 lcw :Created.
     */
    public ResultMessage publishTiff(File geotiff, String styleName, String nativeCRS){
        if(geotiff == null){
            return ResultMessage.fail("file is null ,please set it");
        }
        if(StringUtils.isBlank(styleName)){
            return ResultMessage.fail("style is null, please set it");
        }
        if(StringUtils.isBlank(nativeCRS)){
            return ResultMessage.fail("nativeCRS is null, please set it");
        }

        String fileName = geotiff.getName();
        String preName = fileName.substring(0, fileName.lastIndexOf("."));

        String storeName = "store_" + preName;
        String layerName = "ly_" + preName;

        return publishTiff(wpName,storeName, layerName, geotiff, styleName, nativeCRS);

    }

    /**
     * publish a tiff
     * @param layerName   storeName is layerName replace "ly_" to "store_"
     * @param geotiff
     * @param styleName
     * @return
     * @version<1> 2018-07-02 lcw :Created.
     */
    public ResultMessage publishTiff(String layerName, File geotiff, String styleName){
        if(geotiff == null){
            return ResultMessage.fail("file is null ,please set it");
        }
        if(StringUtils.isBlank(layerName)){
            return ResultMessage.fail("layerName is null, please set it");
        }
        if(StringUtils.isBlank(styleName)){
            return ResultMessage.fail("style is null, please set it");
        }
        String storeName = layerName.replaceFirst("ly_", "store_");
        return publishTiff(wpName,storeName, layerName, geotiff, styleName, null);
    }


    /**
     * publish a tiff
     * @param layerName   storeName is layerName replace "ly_" to "store_"
     * @param geotiff
     * @param styleName
     * @param nativeCRS  坐标
     * @return
     * @version<1> 2018-07-02 lcw :Created.
     */
    public ResultMessage publishTiff(String layerName, File geotiff, String styleName, String nativeCRS){
        if(geotiff == null){
            return ResultMessage.fail("file is null ,please set it");
        }
        if(StringUtils.isBlank(layerName)){
            return ResultMessage.fail("layerName is null, please set it");
        }
        if(StringUtils.isBlank(styleName)){
            return ResultMessage.fail("style is null, please set it");
        }
        if(StringUtils.isBlank(nativeCRS)){
            return ResultMessage.fail("nativeCRS is null, please set it");
        }

        String storeName = layerName.replaceFirst("ly_", "store_");
        return publishTiff(wpName,storeName, layerName, geotiff, styleName, nativeCRS);
    }

    /**
     * publish a tiff with tiff file use default nativeCRS (ESPG:4326)
     * @param storeName
     * @param layerName
     * @param geotiff
     * @param styleName
     * @return
     * @version<1> 2018-07-01 lcw :Created.
     */
    public ResultMessage publishTiff(String storeName, String layerName , File geotiff, String styleName){
        return publishTiff(wpName, storeName, layerName, geotiff, styleName,null);
    }

    /**
     * publish a tiff use nativeCRS
     * @param storeName
     * @param layerName
     * @param geotiff
     * @param styleName
     * @param nativeCRS
     * @return
     * @version<1> 2018-07-01 lcw :Created.
     */
    public ResultMessage publishTiff(String storeName, String layerName , File geotiff , String styleName, String nativeCRS){
        return publishTiff(wpName, storeName, layerName, geotiff, styleName, nativeCRS);
    }


    /**
     * publish an external tiff with tiff file
     *    this type  do not neet upload tiff. just use the tiff's uri
     * @param workspace
     * @param storeName
     * @param layerName  rule:{ly_regionCode-crop-resolution-date}
     * @param geotiff
     * @param styleName
     * @return
     * @version<1> 2018-07-01 lcw :Created.
     * @version<2> 2018-08-27 zhangshen :.update. 文件名带'.'的图层无法发布， 将'.'装换成'_'
     */
    public ResultMessage publishTiff(String workspace , String storeName, String layerName, File geotiff,String styleName, String nativeCRS){
        storeName = storeName.replace(".", "_");
        layerName = layerName.replace(".", "_");

        ResultMessage result = checkTiff(storeName, layerName, geotiff, styleName);
        if(result.isFlag()){
            try {
                boolean bool = setWorkSpace(workspace);
                if(bool){
                    if(StringUtils.isBlank(nativeCRS)){ //若没有自定义的坐标系，则直接使用ESPG:4326
                        nativeCRS = SRS;
                    }

                    if(reader.existsCoveragestore(workspace, storeName)){//remove store
                        bool = publisher.removeCoverageStore(workspace, storeName, true);
                    }
                    if(reader.existsLayer(workspace, layerName)){
                        bool = publisher.removeLayer(workspace, layerName);
                    }

                    System.out.println(geotiff);

                    if(bool){ //publish an externalGeoTIFF
                        bool = publisher.publishExternalGeoTIFF(workspace , storeName, geotiff,  layerName, nativeCRS, shpPolicy, styleName );
                    }
                }
                result.setFlag(bool);
                if(bool){
                    result.setData(workspace + ":" + layerName); //返回图层名称

                    result.setMsg("the tiff publish ok, its storeName is " + storeName + " and its layerName is " + layerName);
                }else{
                    result.setMsg("the tiff publish fail");
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                logger.error("publish geo tiff error, error msg is " + e.getMessage());
            }
        }
        return result;
    }

    /*
     * 功能描述: 发布数据库postgis图层
     * @Param:store_name   //待创建和发布图层的数据存储名称store
     * @Param：table_name // 数据库要发布的表名称,后面图层名称和表名保持一致
     * @Return:
     * @version<1>  2019/12/11  wangli :Created
     */
    public ResultMessage publishPostgis(String table_name){
        String store_name = table_name.replaceFirst("init_rank", "store");
        ResultMessage resultMessage = ResultMessage.fail();

        List<String> workspaces = reader.getWorkspaceNames();
        if(!workspaces.contains(wpName)){
            boolean createws = publisher.createWorkspace(wpName);
            System.out.println("create ws :" + createws);
        }else {
            System.out.println("workspace已经存在了，ws" +wpName);
        }

        //判断数据存储（datastore）是否已经存在，不存在则创建
        RESTDataStore restStore = reader.getDatastore(wpName,store_name);
        if(restStore == null){
            GSPostGISDatastoreEncoder store = new GSPostGISDatastoreEncoder(store_name);
            store.setHost(postgisHost);
            store.setPort(postgisPort);
            store.setUser(postgisUser);
            store.setPassword(postgisPass);
            store.setDatabase(postgisDatabase);
            store.setSchema("public");
            store.setConnectionTimeout(postgisConnectionTimeout);
            store.setMaxConnections(postgisMaxConnections);
            store.setMinConnections(postgisMinConnections);
            store.setName(store_name);//数据存储名称
            boolean createStore = manager.getStoreManager().create(wpName, store);
            System.out.println("create store : " + createStore);
        }else {
            System.out.println("数据存储已经存在了,store:" + store_name);
        }
        //判断图层是否已经存在，不存在则创建并发布
        RESTLayer layer = reader.getLayer(wpName, table_name);
        if(layer == null){
            GSFeatureTypeEncoder pds = new GSFeatureTypeEncoder();
            pds.setTitle(table_name);
            pds.setName(table_name);
            pds.setSRS(SRS);
            pds.setProjectionPolicy(GSResourceEncoder.ProjectionPolicy.REPROJECT_TO_DECLARED);
            pds.addKeyword("features");

            final GSLayerEncoder layerEncoder = new GSLayerEncoder();
            layerEncoder.setEnabled(true);
            layerEncoder.setDefaultStyle("rank");

            boolean publish = publisher.publishDBLayer(wpName, store_name,  pds , layerEncoder);

            System.out.println("=========================================================================================");
            System.out.println("publish : " + publish);
            resultMessage.setFlag(publish);

        }else {
            System.out.println("表已经发布过了,table:" + table_name);
        }

        return  ResultMessage.success();
    }

    /*
     * 功能描述: 检查图层是否存在
     * @Param:
     * @Return:
     * @version<1>  2019/12/10  wangli :Created
     */
    public boolean checkLayer(String storeName){
        return reader.existsDatastore(wpName, storeName);
    }

    /**
     *  remove a store
     * @param storeName
     * @return
     */
    public ResultMessage removeStore(String storeName){
        if(StringUtils.isBlank(storeName)){
            return ResultMessage.fail("storeName is null, please set it");
        }
        return removeStore(wpName, storeName);
    }


    /**
     * remove a store
     * @param workspace
     * @param storeName
     * @return
     */
    public ResultMessage removeStore(String workspace , String storeName){

        ResultMessage result = ResultMessage.fail();

        if(StringUtils.isBlank(workspace)){
            return ResultMessage.fail("workspace is null, please set it");
        }

        if(StringUtils.isBlank(storeName)){
            return ResultMessage.fail("storeName is null, please set it");
        }

        boolean bool = false;
        if(reader.existsCoveragestore(workspace, storeName)){
            bool = publisher.removeCoverageStore(workspace, storeName, true);
            if(bool){
                result.setMsg("store " + storeName + " in  workspace " + workspace + " remove success");
            }else{

                result.setMsg("store " + storeName + " in  workspace " + workspace + " remove fail");
            }
        }else{
            result.setMsg("store " + storeName + " in  workspace " + workspace + " is not exist, do not need remove it" );
        }
        result.setFlag(bool);

        return result;
    }

    /**
     * when publish a tiff ,first check {storeName},{layerName},{geotiff},{styleName}
     * @param storeName
     * @param layerName
     * @param geotiff
     * @param styleName
     * @return
     * @version<1> 2018-07-01 lcw : Created.
     */
    private ResultMessage checkTiff(String storeName , String layerName , File geotiff, String styleName){

        ResultMessage result = ResultMessage.success();

        if(StringUtils.isBlank(storeName)){
            return ResultMessage.fail("storeName is null , please set it first");
        }

        if(StringUtils.isBlank(layerName)){
            return ResultMessage.fail("layerName is null , please set it first");
        }

        if(geotiff == null){
            return ResultMessage.fail("geotiff is null , please set it first");
        }

        if(StringUtils.isBlank(styleName)){
            return ResultMessage.fail("styleName is null , please set it first");
        }

        return result;
    }


    /*******************************************************************************************************************
     * ************************************************  publish style method  ****************************************
     * *****************************************************************************************************************
     * *****************************************************************************************************************/

    /**
     * publish a new style
     * @param styleFile
     * @return
     * @version<1> 2018-07-01 lcw : Created.
     */
    public ResultMessage publishStyle(File styleFile){
        ResultMessage result = checkStyle(styleFile);
        if(!result.isFlag()){
            return result;
        }

        String styleName = styleFile.getName();
        return publishStyle(wpName, styleName, styleFile);
    }


    /**
     * publish style in workspace
     * check {style} in {workspace}, if not exists then publish it and return {bool}
     * @version<1> 2018-06-29 lcw : Created.
     */
    public ResultMessage publishStyle(String styleName, File styleFile){

        return publishStyle(wpName, styleName, styleFile);
    }


    /**
     * publish style in workspace
     * check {style} in {workspace}, if not exists then publish it and return {bool}
     * @version<1> 2018-06-29 lcw : Created.
     */
    public ResultMessage publishStyle(String workspace, String styleName, File styleFile ){

        ResultMessage result = checkStyle(styleFile);

        if(result.isFlag()){
            if(StringUtils.isBlank(styleName)){
                styleName = styleFile.getName();
            }

            //check workspace exists , if not , create it, then return {bool}
            boolean bool = setWorkSpace(workspace);

            if(bool){
                if(!reader.existsStyle(workspace, styleName)){ //if not exists ,then publish it
                    bool = publisher.publishStyleInWorkspace(workspace, styleFile, styleName);
                }
            }
            result.setFlag(bool);
            if(bool){
                result.setData(styleName);
                result.setMsg("style publish ok");
            }else{
                result.setMsg("style publish fail");
            }
        }

        return result;
    }


    /**
     * update style by styleName. if styleName exists , then updateInWorkspace
     * @param styleFile
     * @return
     * @version<1> 2018-07-01 lcw : Created.
     */
    public ResultMessage updateStyle(File styleFile){
        ResultMessage result = checkStyle(styleFile);
        if(!result.isFlag()){
            return result;
        }

        String styleName = styleFile.getName();
        return updateStyle(wpName, styleName, styleFile);
    }

    /**
     * 更新样式
     * @param styleName
     * @param styleFile
     * @return
     * @version<1> 2018-07-01 lcw : Created.
     */
    public ResultMessage updateStyle( String styleName, File styleFile){
        return updateStyle(wpName, styleName, styleFile);
    }

    /**
     * 更新样式
     * @param workspace
     * @param styleName
     * @param styleFile
     * @return
     * @version<1> 2018-07-01 lcw : Created.
     */
    public ResultMessage updateStyle(String workspace, String styleName, File styleFile){
        ResultMessage result = checkStyle( styleFile);
        if(result.isFlag()){

            if(StringUtils.isBlank(styleName)){
                styleName = styleFile.getName();
            }

            //check workspace exists , if not , create it, then return {bool}
            boolean bool = setWorkSpace(workspace);

            if(bool){
                if(reader.existsStyle(workspace, styleName)){
                    bool = publisher.updateStyleInWorkspace(workspace, styleFile, styleName);

                }else{
                    bool = publisher.publishStyleInWorkspace(workspace, styleFile, styleName);
                }
            }

            result.setFlag(bool);
            if(bool){
                result.setData(styleName);
                result.setMsg("style update ok");
            }else{
                result.setMsg("style update fail");
            }
        }

        return result;
    }


    /**
     * 获取默认工作空间的样式
     * @return
     * @version<1> 2018-07-11 lcw:Created.
     */
    public ResultMessage getStylesInWorkspace(){
        return  getStylesInWorkspace(wpName);
    }

    /**
     * get all workspaceName from workspace
     * @param workspace
     * @return
     * @version<1> 2018-07-01 lcw:Created.
     */
    public ResultMessage getStylesInWorkspace(String workspace){

        List<String> list = new ArrayList<String>();
        RESTStyleList restStyleList = null;

        if(StringUtils.isBlank(workspace)){
            restStyleList = reader.getStyles();
        }else{
            restStyleList = reader.getStyles(workspace);
        }
        if(restStyleList != null){
            for(NameLinkElem e : restStyleList){
                list.add(e.getName());
            }
        }
        return ResultMessage.success(list);
    }

    /**
     * remove a style
     * @param workspace
     * @param styleName
     * @return
     */
    public ResultMessage removeStyleInWorkspace(String workspace, String styleName){

        ResultMessage result = ResultMessage.fail();
        if(StringUtils.isNotBlank(workspace) && StringUtils.isNotBlank(styleName)){
            boolean bool =  publisher.removeStyleInWorkspace(workspace, styleName);
            result.setFlag(bool);
            if(bool){
                result.setMsg("style remove ok");
            }else{
                result.setMsg("style remove fail");
            }
        }else{
            result.setMsg("workspace or styleName is null ,please set it");
        }

        return result;
    }

    /**
     * when publish a style ,first check {styleFile}
     * @param styleFile
     * @return
     */
    private ResultMessage checkStyle(File styleFile){
        ResultMessage result = ResultMessage.success();

        if(styleFile == null){
            result = ResultMessage.fail("styleFile is null , please set it ");
            return result;
        }
        return result;
    }


    /*******************************************************************************************************************
     * ************************************************  default set workspace *****************************************
     * *****************************************************************************************************************
     * *****************************************************************************************************************/

    /**
     * get all workspace
     * @return
     */
    public ResultMessage getAllWorkspace(){
        return ResultMessage.success(reader.getWorkspaceNames());
    }

    /**
     * check {workspace} ,if exists then return it, else create  and return it
     * @return
     * @version<1> 2018-07-01  lcw: Created
     */
    private boolean setWorkSpace(String workspace){
        boolean bool = false;
        if(StringUtils.isBlank(workspace)){
            workspace = wpName;
        }
        if(reader != null && reader.existsWorkspace(workspace)){
            return true;
        }

        if(publisher != null){
            bool = publisher.createWorkspace(workspace);
        }
        return bool;
    }

//    /**
//     * 北半球地区，选择最后字母为“N”的带；
//     * 可根据公式计算，带数=（经度整数位/6）的整数部分+31
//     * @param lon
//     * @return
//     * @version<1> 2018-07-04 lcw :Created.
//     */
//    public String getUTMZone(Double lon){
//        if(lon == null ){
//            return SRS;
//        }
//        int bandNumber = new Double(lon).intValue() / 6 + 31; //带数
////        return "EPSG:326" + bandNumber;
//
//
//        return SRS;
//    }


    public static void main(String[] args){

        GeoServerUtil geoUtils = new GeoServerUtil();

    }

}
