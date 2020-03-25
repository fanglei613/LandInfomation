package com.jh.util.ceph;

import com.jh.constant.PropertiesConstant;
import com.jh.util.DateUtil;
import com.jh.util.PropertyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * description:文件工具类
 *  1.文件下载
 *
 * @version <1> 2018-02-02 lcw： Created.
 * @version<2>  2018-07-10 lcw : 将ceph操作的配置文件剥离开来，单独命名为config_ceph.properties
 */
public class CephUtils {

    public static Logger logger= LoggerFactory.getLogger(CephUtils.class);

//    private static final String CEPH_CONFIG = "config/config_ceph.properties";

    private static CephUtils instance = null;

    private CephUtils(){}

    public static CephUtils getInstance(){
        if(instance == null){
            instance = new CephUtils();
        }
        return instance;
    }


    /**
     * 通过key获取路径：由server+path组成
     * @param configName
     * @return
     * @version<1> 2018-03-09 lcw : created.
     */
    public static String getCephUrl(String configName){
        String importPath = PropertyUtil.getPropertiesForConfig(configName, PropertiesConstant.CEPH_CONFIG);

        return importPath;
    }

    /**
     * 通过key获取路径：获取GEOSERVER_CONFIG配置
     * @param configName
     * @return
     * @version<1> 2018-03-09 lcw : created.
     */
    public static String getGeoserviceUrl(String configName){
        String importPath = PropertyUtil.getPropertiesForConfig(configName, PropertiesConstant.GEOSERVER_CONFIG);
        return importPath;
    }

    /**
     * 通过key获取该key指示的绝对路径
     * @param configName
     * @return
     * @version<1> 2018-03-11 lcw:Created.
     */
//    private static String getCephUrlByKey(String configName){
//        return PropertyUtil.getPropertiesForConfig(configName, CEPH_CONFIG);
//    }

    /**
     * 获取系统server配置
     * @return
     * @version<1> 2018-03-08 lcw : Created.
     */
    public static String getServer(){
//        String server = PropertyUtil.getPropertiesForConfig("CEPH_SERVER", CEPH_CONFIG);
        String server = getCephUrl("CEPH_SERVER");
        return server;
    }


    /**
     * 获取geoserver服务器上对应的samba网盘路径
     * @return
     * @version<1> 2018-08-20 lcw : Created.</1>
     */
    public static String getCephOnGeoserver(){
        String server = getCephUrl("CEPH_ON_GEOSERVER");
        return server;
    }


    /**
     * 获取webservice 所在服务器的samba网盘路径
     * @return
     * @version<1> 2018-08-22 lcw : Created.
     */
    public static String getCephOnService(){
        return getCephUrl("CEPH_ON_SERVICE");
    }

    /**
     * web用户登录系统时，显示的网盘路径
     * （与getServer对应，因为CEPH_SERVER是web服务所在服务器需要使用的网盘路径，在linux中映射的是文件夹，windows映射的是\\192.168.1.221）
     * @return
     * @version<1> 2018-06-11 lcw : created.
     */
    public static String getShowServer(){
//        String showServer =PropertyUtil.getPropertiesForConfig("CEPH_SERVER_SHOW", CEPH_CONFIG);
        String showServer = getCephUrl("CEPH_SERVER_SHOW");
        return showServer;
    }


    /**
     * ceph分布式存储根路径
     * @return
     */
    public static String getCephRoot(){

        return getCephUrl("CEPH_ROOT").replace("\\",File.separator);
//        return PropertyUtil.getPropertiesForConfig("CEPH_ROOT", CEPH_CONFIG).replace("\\",File.separator);
    }

    /**
     * 读取GEOSERVER_CONFIG路径
     */
    public static String getGeoserverUrl(){
        return getGeoserviceUrl("GEOSERVER_URL");
    }

    /**
     * 获取数据下载路径
     * @return
     * @version<1> 2018-03-21 lcw : Created.
     */
    public static String getCephDownloadPath(){
      return   getCephUrl("CEPH_COLLECTION_DOWNLOAD").replace("\\",File.separator);
    }

    /**
     * 内部订单：创建订单文件link存储路径
     * /mnt/data/distribute/year/month/accountName/orderId
     * @param orderCode  订单编号
     * @param accountName 用户账号
     * @return
     * @version<1> 2018-03-21 lcw :Created.
     * @version<2> 2018-04-26 lcw : 将订单的外部订单和内部订单拆分开，形成两个方法。 工具类中不应该包含有业务判断
     */
    public static String makeInnerOrderDirectory(String orderCode, String accountName){
        String path = getCephDistributeInner();
        String directory = path + File.separator + DateUtil.getYear() + File.separator + DateUtil.getDayOfYear() + File.separator + accountName + File.separator + orderCode;
        //检索文件夹，并创建文件夹
        scanDirectory(getServer() + getCephRoot() + directory);
        return directory;
    }


    /**
     * 外部订单：创建订单文件link存储路径
     * /mnt/data/distribute/year/month/accountName/orderId
     * @param orderCode  订单编号
     * @param accountName 用户账号
     * @return
     * @version<1> 2018-04-26 lcw :Created.
     *
     */
    public static String makeOuterOrderDirectory(String orderCode, String accountName){
        String path = getCephDistributeOuter();
        String directory = path + File.separator + DateUtil.getYear() + File.separator + DateUtil.getDayOfYear() + File.separator + accountName + File.separator + orderCode;
        //检索文件夹，并创建文件夹
        scanDirectory(getServer() + getCephRoot() + directory);
        return directory;
    }


    /**
     * 创建数据处理原始数据link 存储路径
     * @param handleTaskId
     * @return
     */
    public static String makerHandleDirectory(Integer handleTaskId){

        String directory = getCephHandle() + File.separator + (DateUtil.dateToString(new Date(),"yyyy-MM-dd")) + File.separator + handleTaskId + File.separator + "originData";

        //检索文件夹，并创建文件夹
        scanDirectory(getServer() + getCephRoot() + directory);
        return directory;
    }

    /**
     * 创建订单文件link存储路径
     * /mnt/data/archive/year/month/accountName/archiveId
     * @param archiveId  订单ID
     * @param accountName
     * @return
     * @version<1> 2018-03-26 wl :Created.
     */
    public static String makeArchiveDirectory(Integer archiveId, String accountName){

        String directory = getCephDataArchive() + File.separator + DateUtil.getYear() + File.separator + DateUtil.getDayOfYear() + File.separator + accountName + File.separator + archiveId;

        //检索文件夹，并创建文件夹
        scanDirectory(getServer() + getCephRoot() + directory);

        return directory;

    }

    /**
     * 创建矢量数据存储路径
     * @return
     * @version <1> 2018-04-24 lcw :Created.
     */
    public static String makeStaticDirectory(String path){
        String directory = getCephStatic() + File.separator + "shapefile" + File.separator + path;
        scanDirectory(getServer() + getCephRoot() + directory);
        return directory;
    }

    /**
     * 创建再加工数据存储路径
     * @return
     * @version <1> 2018-04-24 lcw :Created.
     */
    public static String makeReprocessDirectory(String path){
        String directory = getCephReprocess() + File.separator + DateUtil.getYear() + File.separator + DateUtil.getDayOfYear() + File.separator + path;
        scanDirectory(getServer() + getCephRoot() + directory);
        return directory;
    }



    /**
     * 创建数据集存储路径
     * @param dsCode
     * @param regionCode
     * @param cropCode
     * @param yearStr
     * @return
     * @version<1> 2018-07-03 lcw :Created.
     */
    public static String makeDatasetDirectory(String dsCode, String regionCode,String cropCode, String yearStr ){
        String directory = getCephDataset() + File.separator + dsCode + File.separator + regionCode + File.separator + cropCode
                + File.separator + yearStr;
        scanDirectory(getServer() + getCephRoot() + directory);
        return directory;
    }

    /**
     * 通过路径名称组装绝对路径
     * @param path
     * @return
     * @version<1> 2018-03-23 cxw :Created.
     * @version<2> 2018-03-23 lcw ： 修改方法名称，适合所有模块使用</2>
     */
    public static String getAbsolutePath(String path){
        return getServer() + getCephRoot() + path;

    }

    /**
     * 页面显示的绝对路径(windows系统显示的网盘路径)
     * @param path
     * @return
     * @version<1> 2018-06-11 lcw :Created.
     */
    public static String getShowPath(String path){

        return getShowServer() + getCephRoot().replace(File.separator , "\\") + path.replace(File.separator , "\\");
    }


    /**
     * 获取geoserver发tiff图层所使用的ceph路径（windows：\\192.168.1.223\,Linux:/samba）
     * @param path
     * @return
     */
//    public static String getGeoPath(String path){
//        return getServer() + getCephRoot().replace(File.separator , "\\") + path.replace(File.separator , "\\");
//
//    }


    /**
     * 根据任务Link路径和文件名生成该文件的link绝对路径
     * @param destDir
     * @param fileName
     * @return
     * @version<1> 2018-03-21 lcw :Created.
     */
    public static String getOrderLink(String destDir, String fileName){
        if(StringUtils.isBlank(destDir) || StringUtils.isBlank(fileName)){
            return "";
        }
        String destFile = destDir + File.separator + fileName;
        return destFile.replace("\\",File.separator);

    }



    /**
     * 获取数据导入临时文件夹的指定前缀
     * @return
     * @version<1> 2018-03-08 lcw : Created.
     */
    public static String getCephCollectionImportPrefix(){
//        String importPath = PropertyUtil.getPropertiesForConfig("CEPH_COLLECTION_IMPORT_PREFIX", CEPH_CONFIG);
        String importPath = getCephUrl("CEPH_COLLECTION_IMPORT_PREFIX");
        return importPath;
    }


    /**
     * 获取数据导入临时文件路径
     * @return
     * @version<1> 2018-03-08 lcw : Created.
     */
    public static String getCephCollectionImport(){
        return getCephUrl("CEPH_COLLECTION_IMPORT").replace("\\",File.separator);
    }

    /**
     * 获取再加工数据临时文件路径
     * @return
     * @version<1> 2018-03-08 lcw : Created.
     */
    public static String getCephReprocessImport(){
        return getCephUrl("CEPH_REPROCESS_IMPORT").replace("\\",File.separator);
    }

    /**
     * 获取矢量数据导入临时文件路径
     * @return
     * @version<1> 2018-04-19  lcw: Created.</1>
     */
    public static String getCephStaticImport(){
        return getCephUrl("CEPH_STATIC_IMPORT").replace("\\",File.separator);
    }

    /**
     * 获取数据集数据临时文件路径
     * @return
     * @version<1> 2018-07-02 lcw :Created.
     */
    public static String getCephDatasetImport(){
        return getCephUrl("CEPH_DATASET_IMPORT").replace("\\", File.separator);
    }


    /**
     * 获取简报基础路径
     * @return
     * @version<1> 2018-08-01 lcw : Created.
     */
    public static String getCephBrief(){
        return getCephUrl("CEPH_BRIEF").replace("\\", File.separator);
    }

//
//    /**
//     * 数据采集目录:下载
//     * @return
//     * @version<1> 2018-03-09 lcw : created.
//     */
//    public static String getCephCollectionDownload(){
//        return getServer() + File.separator +   getCephUrl("CEPH_COLLECTION_DOWNLOAD");
//    }
//
    /**
     * 影像数据存储路径
     * @return
     * @version<1> 2018-03-11 lcw : created.
     */
    public static String getCephWork(){
        return getCephUrl("CEPH_WORK").replace("\\",File.separator);
    }


    /**
     *静态数据存储根路径
     * @return
     * @version<1> 2018-03-11 lcw : created.
     */
    public static String getCephStatic(){
        return getCephUrl("CEPH_STATIC").replace("\\",File.separator);
    }


    /**
     * 数据处理存储根路径
     * @return
     * @version<1> 2018-03-11 lcw : created.
     */
    public static String getCephHandle(){
        return getCephUrl("CEPH_HANDLE").replace("\\",File.separator);
    }


    /**
     * 缩略图存储根路径
     * @return
     * @version<1> 2018-03-13 lcw : Created.
     */
    public static String getCephThumbnail(){
        return getCephUrl("CEPH_THUMBNAIL").replace("\\",File.separator);
    }

    /**
     * 再加工数据存储根路径
     * @return
     * @version<1> 2018-03-11 lcw: Created.
     */
    public static String getCephReprocess(){
        return getCephUrl("CEPH_RESULT_REPROCESS").replace("\\",File.separator);
    }

    /**
     * 成果数据存储根路径
     * @return
     * @version<1> 2018-04-19 lcw : Created.
     */
    public static  String getCephDataset(){
        return getCephUrl("CEPH_RESULT_DATASET").replace("\\",File.separator);
    }

    /**
     * 内部用户订单存储路径
     * @return
     * @version<1> 2018-03-11 lcw : created.
     */
    private static String getCephDistributeInner(){
        return getCephUrl("CEPH_DISTRIBUTE_INNER").replace("\\",File.separator);
    }
    /**
     * 外部用户订单存储路径
     * @return
     * @version<1> 2018-04-12 lcw : created.
     */
    private static String getCephDistributeOuter(){
        return getCephUrl("CEPH_DISTRIBUTE_OUTER").replace("\\",File.separator);

    }

    /**
     * 数据归档路径
     * @return
     * @version<1> 2018-03-26 wl : created.
     */
    private static String getCephDataArchive(){
        return getCephUrl("CEPH_DATAARCHIVE").replace("\\",File.separator);
    }

    /**
     * python 下载算法主程序
     * @return
     * @version<1> 2018-03-11 lcw:Created.
     */
//    public static String getCephDownloadMain(){
//        return getCephUrlByKey("CEPH_DOWNLOAD_MAIN").replace("\\",File.separator);
//    }

    /**
     * 文件上传临时存储路径
     * @return
     * @version<1> 2018-12-10 lcw :Created.</1>
     */
    public static String getUploadTmpPath(){
        String uploadTmpPath = getCephUrl("FILE_UPLOAD_PATH");
        //检索文件夹，并创建文件夹
        scanDirectory(uploadTmpPath);
        return uploadTmpPath;
    }


    /**
     * 系统初始化时，需要做校验的文件存储目录(ceph),主要包括如下种类：
     *  1.影像数据目录
     *  2.静态数据目录
     *  3.处理结果目录
     *  4.再加工数据目录
     *  5.数据采集目录：下载
     *  6.数据采集目录：导入
     *  7.再加工数据导入
     *  8.数据分发目录:内部用户
     *  9.数据分发目录:外部用户
     * @return
     * @version<1> 2018-03-12 lcw : Created.
     */
    public static void initBaseCephDirectory(){
        scanDirectory(getAbsolutePath(getCephWork())); //影像数据
        scanDirectory(getAbsolutePath(getCephStatic())); //矢量数据
        scanDirectory(getAbsolutePath(getCephReprocess())); //再加工数据
        scanDirectory(getAbsolutePath(getCephDataset())); //成果数据
        scanDirectory(getAbsolutePath(getCephThumbnail())); //缩略图
        scanDirectory(getAbsolutePath(getCephHandle())); //数据处理
        scanDirectory(getAbsolutePath(getCephDistributeInner())); //内部订单
        scanDirectory(getAbsolutePath(getCephDistributeOuter())); //外部订单
        scanDirectory(getAbsolutePath(getCephDownloadPath())); //下载路径
        scanDirectory(getAbsolutePath(getCephCollectionImport())); //原始数据导入文件夹
        scanDirectory(getAbsolutePath(getCephReprocessImport())); //再加工数据导入文件夹
        scanDirectory(getAbsolutePath(getCephStaticImport())); //矢量数据导入文件夹
        scanDirectory(getAbsolutePath(getCephDataArchive()));
        scanDirectory(getAbsolutePath(getCephBrief())); //简报
    }

//
//    /**
//     * 拼装相对路径
//     * @param satId
//     * @param sensorId
//     * @param fileName
//     * @param dateStr
//     * @return
//     */
//    public static String makeRelativeDir(int satId , int sensorId, String fileName, String dateStr){
//        String workDirectory = "";
//        if(satId != 0 && sensorId != 0 && StringUtils.isNotBlank(fileName)){
//            String fileDir = fileName.substring(0, fileName.lastIndexOf("."));
//             workDirectory =  satId + File.separator + sensorId + File.separator + "L1A"
//                    + File.separator + DateUtil.getYear(dateStr) + File.separator + DateUtil.getDayOfYear(dateStr) + File.separator
//                    + fileDir;
//        }
//        return workDirectory;
//    }
//


    /**
     * 检索文件是否存在，若不存在则直接创建该文件夹
     * @param path
     * @version<1> 2018-03-12 lcw : created.
     */
    private static boolean scanDirectory(String path){
        boolean bool = false;
        File file = new File(path);
        logger.info("文件路径-------------------" + path);
        if(!file.exists()){
            bool = file.mkdirs();
        }else{
            bool = true;
            logger.info("文件路径已经存在-------------------" + path);
        }
        if(!bool){
            logger.info("文件路径创建失败-------------------" + path);
        }else{
            logger.info("文件路径创建成功-------------------" + path);
        }
        return bool;
    }

    /**
     * 获取系统配置的文件后缀字符串
     * @return
     * @version <1> 2018-04-23 lcw:Created.
     */
    public static String getSuffixs(){
//        String suffixs = PropertyUtil.getPropertiesForConfig("LOAD_FILE_SUFFIX", CEPH_CONFIG);
        String suffixs = getCephUrl("LOAD_FILE_SUFFIX");
        return suffixs;
    }

    /**
     * 图层后缀
     * @return
     * @version<1> 2018-07-04 lcw:Created.
     */
    public static String getLayerSuffix(){
        return getCephUrl("DATA_LAYER_SUFFIX");
    }



    /**
     * 遍历指定文件夹下的文件和文件夹
     * @param path ： 访问路径
     * @param ignorePrefix : 过滤以ignorePrefix为前缀的文件
     * @return
     * @version<1> 2018-03-07 lcw : Created.
     */
    public static String[] list(String path,String ignorePrefix){

        File file = new File(path);

        String[] list = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.startsWith(ignorePrefix);
            }
        });
        return list;
    }


    /**
     * 为文件（夹）添加前缀
     *    将文件（夹）更名为带有前缀prefix的文件（夹）名称
     * @param path
     * @return
     * @version<1> 2018-03-09 lcw :Created.
     */
    public static String setPrefix(String path, String name, String prefix){
        boolean bool = false;
        File file = new File(path + File.separator + name);
        int lastSeparator = (name.lastIndexOf("\\")!=-1 ? name.lastIndexOf("\\") : name.lastIndexOf("/")) + 1;
        String first = name.substring(0,lastSeparator);
        String last = name.substring(lastSeparator);

        String absPath =first + "import_";
        File absFile = new File(path + absPath);
        if(!absFile.exists()){
            absFile.mkdirs();
        }

        String curMillis = System.currentTimeMillis() + "";
//        String targetName = absPath + File.separator + prefix + last + "_" +  curMillis;
        String targetName = absPath + File.separator +  last + "_" +  curMillis.substring(curMillis.length()-4);
        File newFile = new File(path  + targetName);

//        if(newFile.exists()){
////            newFile.delete();
//        }

        bool = file.renameTo(newFile);
        if (bool){
            //@version<1> 2018-03-29 xzg :update.
            return targetName;
        }
        return null;
    }

    /**
     * 根据文件路径遍历文件
     * @param path
     * @return File[]
     * @version<1> 2018-03-12 lcw : Created.
     */
    private static File[] getFiles(String path){
        System.out.println(path);
        File file = new File(path);
        File[] files = file.listFiles();

        return files;
    }


    /**
     * 根据文件路径遍历文件: 只过滤显示指定后缀名的文件
     * @param path
     * @return File[]
     * @version<1> 2018-04-23 lcw : Created.
     */
    private static File[] getFiles(String path,String suffixs){
        File file = new File(path);


        File[] list = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(StringUtils.isNotBlank(suffixs)){
                    if(suffixs.contains(name.substring(name.lastIndexOf(".")))){
                        return true;
                    }
                }else{
                    return true;
                }
                return false;
            }
        });
        return list;
    }

    /**
     * 创建文件夹
     * @param path
     * @version<1> 2018-03-12 lcw : Created.
     */
    public static void mkdirs(String path){
        File destDir = new File(path);
        if(!destDir.exists()){
            destDir.mkdirs();
        }
    }

    /**
     * 获取文件夹下文件数量
     * @param path
     * @return
     * @version<1> 2018-03-08 lcw : Created.
     */
    public static int getFilesNum(String path, String suffixs){
        int num = 0;
        //String suffixs = getSuffixs();
       File[] files = getFiles(path,suffixs);
        if(files != null)
            num = files.length;

        return num;
    }
    /**
     * 根据文件路径遍历文件
     * @param path
     * @return List<String>
     * @version<1> 2018-03-12 lcw : Created.
     */
    public static List<Map<String,Object>> listFiles(String path,String suffixs){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//        String suffixs = getSuffixs();
        File[] files = getFiles(path,suffixs);
        if(files != null && files.length > 0){
            for(File obj : files){
                Map<String,Object> map=new HashMap();
                map.put("fileName",obj.getName());
                map.put("filePath",path+ File.separator +obj.getName());

                map.put("fileSize",compileSizeUnit(getFileSize(obj)));
                list.add(map);
            }
        }
        return list;
    }



    /**
     * 根据文件路径遍历文件
     * @param path
     * @return List<String>
     * @version<1> 2018-03-12 lcw : Created.
     */
    public static List<Map<String,Object>> listAllFiles(String path,String suffixs){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//        String suffixs = getSuffixs();
//        File[] files = getFiles(path,suffixs);

        List<File> fileList = new ArrayList<File>();
        getFileList(path, suffixs, fileList);

        if(fileList != null && fileList.size() > 0){
            for(File obj : fileList){
                Map<String,Object> map=new HashMap();
                String absPath = obj.getAbsolutePath();
                String fileName = absPath.replace(path + File.separator, "");
                String fileNa = obj.getName();
                map.put("fileNa",fileNa);//文件名：a.txt
                map.put("fileName",fileName);// b/a.txt
				map.put("filePath",path+ File.separator + fileName);
                //map.put("filePath",File.separator + fileName);
                map.put("fileSize",compileSizeUnit(getFileSize(obj)));
                list.add(map);
            }
        }
        return list;
    }






    /**
     * 根据文件路径遍历文件
     * @param path 文件查询路径
     * @param downPath 拼接文件映射下载路径
     * @return List<String>
     * @version<1> 2018-04-09 cxw : Created.
     */
    public static List<Map<String,Object>> listFilesForOrder(String path,String downPath){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        File[] files = getFiles(path);
        if(files != null && files.length > 0){
            for(File obj : files){
                Map<String,Object> map=new HashMap();
                map.put("fileName",obj.getName());
                System.out.println(downPath+ File.separator +obj.getName());
                map.put("filePath",downPath+ File.separator +obj.getName());

                map.put("fileSize",compileSizeUnit(getFileSize(obj)));

                list.add(map);
            }
        }
        return list;
    }


    public static int getFileSize(File obj){
        int size = 0 ;
        try {
            FileInputStream fileInputStream = new FileInputStream(obj);
            size = fileInputStream.available();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 文件大小计算
     * @param size
     * @return
     * @version<1> 2018-04-02 xzg : Created.
     */
    public static String compileSizeUnit(long size){
        String[] unit = {"B","KB","M","G"};
        int level = 0;
        while (size >= 1024){
            size = size /  1024;
            level++;
        }
        return "" + size + unit[level];
    }


    /**
     * 根据目录移动多个文件
     *   从源目录将文件移动至目标目录（若目标目录）
     * @param targetPath   文件源目录
     * @param destPath   文件目标目录
     * @param fileName   文件名
     * @return
     * @version<1> 2018-03-12 lcw : Created.
     */
    public static boolean moveFiles(String targetPath, String destPath , String fileName){
        boolean bool = false;
        File targetFile = new File(targetPath + File.separator + fileName);
        if(targetFile.exists()){
            mkdirs(destPath); //创建文件夹
            File destFile = new File(destPath + File.separator + fileName);
            if(destFile.exists()){ //删除目标文件夹的文件
                destFile.delete();
            }
            bool = targetFile.renameTo(destFile);
        }
        return bool;
    }

    /**
     * 多文件移动
     * 遍历文件夹，移动该文件夹下的所有文件
     * @param targetPath
     * @param destPath
     * @return
     * @version<1> 2018-03-12 lcw : Created.
     */
    public static boolean moveFiles(String targetPath , String destPath){
        boolean bool = false;
        File[] files = getFiles(targetPath);
        if(files != null && files.length > 0){
            mkdirs(destPath);
            for(File file : files){
                File destFile = new File(destPath + File.separator + file.getName());
                if(destFile.exists()){ //删除目标文件夹的文件
                    destFile.delete();
                }
                file.renameTo(destFile);
            }
            bool = true;
        }
        return bool;
    }

    /**
     * 移动文件
     * @param targetPath 源文件
     * @param destPath 目标文件路径
     * @param fileName 文件名
     * @return
     * @version<1> 2018-07-03 lcw :Created.
     */
    public static boolean moveFile(String targetPath, String destPath, String fileName) {

        boolean bool = false;

        if (StringUtils.isNoneBlank(destPath)) {
            mkdirs(destPath); //创建目标文件路径
        }
        File destFile = new File(destPath + File.separator + fileName);
        if (destFile.exists()) {
            destFile.delete();
        }
        if (StringUtils.isNotBlank(targetPath)) {
            File tarFile = new File(targetPath);
            bool = tarFile.renameTo(destFile); //移动至目标文件夹
        }

        return bool;
    }

    /**
     * 获取路径下的文件
     * @param targetPath 原目录文件路径
     * @param suffix 查询指定文件后缀,为空查询全部
     * @return
     */
    public static File[] getFilesContent (String targetPath, String suffix){
        File[] files = null;
        if(StringUtils.isBlank(suffix)){
            files = getFiles(targetPath);
        }else{
            files = getFiles(targetPath, suffix);
        }
        return files;
    }

    /**
     * 复制文件夹下文件到指定路径
     * @param targetPath 原目录文件路径
     * @param destPath 指定文件路径
     * @param suffix 查询指定文件后缀,为空查询全部
     * @return
     * @version<1> 2018-04-25 zhangshen : Created.
     */
    public static List<File> copyFiles(String targetPath, String destPath, String suffix){
        boolean bool = false;
        List<File> list = new ArrayList<File>();
        File[] files = getFilesContent(targetPath, suffix);//获取路径下的文件
        if(files != null && files.length > 0){
            //mkdirs(destPath);
            for(File file : files){
                if(!file.isDirectory()){
                    File destFile = new File(destPath + File.separator + file.getName());
                    if(destFile.exists()){ //删除目标文件夹的文件
                        destFile.delete();
                    }
                    if(file.renameTo(destFile)){//移动成功
                        list.add(destFile);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取矢量数据导入的文件后缀字符串
     * @return
     * @version <1> 2018-04-27 zhangshen:Created.
     */
    public static String getDataStaticSuffixs(){
        String suffixs = getCephUrl("DATA_STATIC__SUFFIX");
        return suffixs;
    }

    /**
     * 根据文件路径递归遍历文件
     * @param path
     * @return List<File>
     * @version<1> 2018-05-31 zhangshen : Created.
     */
    public static List<File> listAllFilesForDG(String path,String suffixs){
        List<File> fileList = new ArrayList<File>();
        getFileList(path, suffixs, fileList);
        return fileList;
    }




    /**
     * 根据文件路径遍历文件: 只过滤显示指定后缀名的文件
     * @param path
     * @return File[]
     * @version<1> 2018-04-23 lcw : Created.
     */
    private static void getFileList(String path,String suffixs,List<File> list){
        File file = new File(path);

        File[] files = file.listFiles();
        if(files != null){
            for(int i = 0 ; i < files.length; i++){
                String fileName= files[i].getName();
                if(files[i].isDirectory()){
                    getFileList(files[i].getAbsolutePath(), suffixs, list);
                }else{
                    if(StringUtils.isBlank(suffixs) || suffixs.contains(fileName.substring(fileName.lastIndexOf(".")))){
                        list.add(files[i]);
                    }
                }
            }
        }
    }




    /**
     * Description: 删除指定路径下的文件及文件夹
     * @param dir 指定文件绝对路径
     * @version <1> 2018/7/10 11:00 zhangshen: Created.
     */
    public static boolean deleteDirAndFiles(File dir) {
        if (!dir.exists()) return false;
        if (dir.isDirectory()) {
            String[] childrens = dir.list();
            // 递归删除目录中的子目录下
            for (String child : childrens) {
                boolean success = deleteDirAndFiles(new File(dir, child));
                if (!success) return false;
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * Description: 修改路径字符串，windows修改成'\'，linux修改成'/'
     * @param path 路径字符串
     * @version <1> 2019-02-25 zhangshen: Created.
     */
    public static String changePathSlash(String path){
        if (StringUtils.isBlank(path)) {
            return "";
        }
        return path.replace("\\", "/").replace("/", File.separator);
    }

//    public static void main(String[] args){
////        File[] files = new File[100];
////        List<File> list = new ArrayList<File>();
////      getFileList("D:\\tiff",".zip,.tiff,.rpb,.tif",list);
////        if(list!= null && list.size() > 0){
////            for(File file : list){
////                System.out.println(file.getAbsolutePath() +"----" +file.getName());
////            }
////        }
//
//        String path = "D:/test/lcw";
//
//        String path2= "D:/test/lcw/lcw/1.jpg";
//        System.out.print(path2.split(path));
//
//
//    }


    /**
     * 上传图片
     * @param
     * @return url 头像地址
     * @version <1> 2019/3/6 lijie:Created.
     */
    public static String uploadImage(HttpServletRequest request, Integer id) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("photoFile");
        if(file==null){
            return null;
        }
        //系统前缀
        String reportStorage = CephUtils.getAbsolutePath("").replace("\\", File.separator);
        //头像目录
        String photoUrl=CephUtils.getCephUrl("FORUM_PHOTO_STORAGE").replace("\\",File.separator)+File.separator;

        File f = new File(reportStorage+photoUrl);
        if (!f.exists()) {
            f.mkdirs();
        }
        String suff = "jpeg";//文件后缀,默认使用jpg
        //加时间戳，防止文件同名，浏览器缓存问题
        String newName="photo_url_" + id + "_"+ new Date().getTime() + "." + suff;//重新生成用户名，防止中文名
        String storagePath = reportStorage +photoUrl+ newName;
        File copyFile = new File(storagePath);//文件
        try {
            file.transferTo(copyFile);//复制文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoUrl+newName;
    }
}
