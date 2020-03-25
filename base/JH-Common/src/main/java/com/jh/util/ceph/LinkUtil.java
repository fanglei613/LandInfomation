package com.jh.util.ceph;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: Java远程操作Linux系统创建link文件
 *
 * @version <1> 2018-03-14  lcw : Created.
 */
public class LinkUtil {

    private static LinkUtil linkUtil = null;

    private LinkUtil(){}

    public static LinkUtil getInstance(){
        if(linkUtil == null){
            linkUtil = new LinkUtil();
        }
        return linkUtil;
    }

    private Connection conn;
    private String ipAddr;
    private String userName;
    private String password;

    /**
     * 获取Linux服务器的IP和用户名密码
     * @version<1> 2018-03-14 lcw : Created.
     */
    public void init(){
        this.ipAddr = getIpAddr();
        this.userName = getUserName();
        this.password = getPassword();
    }


    /**
     * 根据源文件路径、目标文件路径和文件名创建linux链接
     *  注意：linux脚本中使用的是“/"而不是”\"
     * @param targetDir : 源文件路径
     * @param destDir ： 目标位置路径
     * @return
     * @version<1> 2018-03-14 lcw :Created.
     */
    public static String makeLink(String targetDir, String destDir){
        String targetFile = "\"" + getBaseDir() + CephUtils.getCephRoot() +   targetDir + "\"";
        String destFile = "\"" + getBaseDir() + CephUtils.getCephRoot() +   destDir + "\"";
        String link = "ln -s " + targetFile.replace("\\","/") + " " + destFile.replace("\\","/");
        System.out.println(link);

        return link;
    }


    /**
     * 创建删除link的脚本(由于脚本是root用户通过linux的ln命令创建的，具有的是root权限，所以在移除时，也要通过root用户通过linux命令进行删除)
     * @param path
     * @return
     * @version<1> 2018-04-02 lcw :Created.
     */
    public static String getRemoveScript(String path){
        String targetFile = getBaseDir() + CephUtils.getCephRoot() + path;
        return "rm -rf " + targetFile.replace("\\",File.separator);
    }

    /**
     * 根据目标位置路径和文件名获取link路径
     * @param destDir
     * @param fileName
     * @return
     * @version<1 >  2018-03-16 lcw :Created.
     */
//    public static String getLinkPath(String destDir, String fileName){
//        if(StringUtils.isBlank(destDir) || StringUtils.isBlank(fileName)){
//            return "";
//        }
//        String destFile = destDir + File.separator + fileName;
//        return destFile.replace("\\","/");
//
//    }

    /**
     * 执行linux脚本
     * @param links
     * @return
     * @version<1> 2018-03-14 lcw :Created.
     * @version<2> 2018-05-30 lcw : 将执行脚本分批执行（如果link脚本太长，一次性执行会报错。）
     */
    public boolean exec(List<String> links){
        boolean bool = false;
        if(links == null || links.size() == 0 ){
            return bool;
        }
        if (this.login()) {
            try {
                List<String> execLinks = new ArrayList<String>();
                for(int i = 0 ; i < links.size() ; i++){
                    System.out.println(links.get(i));
                    execLinks.add(links.get(i));
                    if(i > 0 && i % 100 == 0){
                        String cmds= StringUtils.join(execLinks, ";");
                        execCommand(cmds);
                        execLinks = new ArrayList<String>();
                    }

                    if(i == links.size() - 1){
                        String cmds= StringUtils.join(execLinks, ";");
                        System.out.println(cmds);
                        execLinks = null;
                        execCommand(cmds);
                    }
                }

                bool = true;
            } finally {
                conn.close();
            }
        }
        return bool;
    }



    private void execCommand(String cmds){
        try {
            Session session = conn.openSession();
            session.execCommand(cmds);
            session.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 执行linux脚本（单行脚本）
     * @param script
     * @return
     * @version<1> 2018-04-02 lcw :Created.
     */
    public boolean exec(String script){
        boolean bool = false;
        if (this.login()) {
            try {
                Session session = conn.openSession();
                session.execCommand(script);
                session.close();
                bool = true;
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                conn.close();
            }
        }
        return bool;
    }

    private boolean login(){
        boolean bool = false;
        try {
            this.init();
            conn = new Connection(ipAddr);
            conn.connect();
            bool= conn.authenticateWithPassword(userName, password);
            if(bool){
                System.out.println("登录" + ipAddr + "成功");
            }else{
                System.out.println("登录" + ipAddr + "失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bool;
    }

    private String getIpAddr(){
        //return PropertyUtil.getPropertiesForConfig("LINUX_SERVER");
        return CephUtils.getCephUrl("LINUX_SERVER");
    }

    private String getUserName(){
        //return PropertyUtil.getPropertiesForConfig("LINUX_SERVER_USER");
        return CephUtils.getCephUrl("LINUX_SERVER_USER");
    }

    private String getPassword(){
        //return PropertyUtil.getPropertiesForConfig("LINUX_SERVER_PWD");
        return CephUtils.getCephUrl("LINUX_SERVER_PWD");
    }

    /**
     * 通过linux远程访问的根路径： 与该服务器的共享目录对应
     * @return
     * @version<1> 2018-03-14 lcw :Created.
     */
    private static String getBaseDir(){
        //return PropertyUtil.getPropertiesForConfig("LINUX_SERVER_BASEDIR");
        return CephUtils.getCephUrl("LINUX_SERVER_BASEDIR");
    }


//    /**
//     * 测试
//     * @param args
//     */
//    public static void main(String[] args){
//
//        LinkUtil linkUtil = LinkUtil.getInstance();
//
//
//        String removeScript = LinkUtil.getRemoveScript("distribute\\2018\\80\\15988888888\\65");
//        String removeScript2 = LinkUtil.getRemoveScript("distribute\\2018\\80\\15988888888\\66");
//        List<String> list = new ArrayList<>();
//        list.add(removeScript);
//        list.add(removeScript2);
//        System.out.println(removeScript);
//        linkUtil.exec(list);
//
//
////        List<String> list = new ArrayList<>();
////        String relativeDir = CephUtils.makeOrderDirectory(100,"15988888888");
////
////       String a1= linkUtil.makeLink("collection/import/import_GF_TEST/GF1_PMS2_E120.7_N40.2_20170202_L1A0002159875.tar.gz",CephUtils.getOrderLink(relativeDir,"GF1_PMS2_E120.7_N40.2_20170202_L1A0002159875.tar.gz"));
////        String a2 = linkUtil.makeLink("collection/import/import_GF_TEST/GF1_WFV1_E120.6_N38.0_20170201_L1A0002159212.tar.gz",CephUtils.getOrderLink(relativeDir,"GF1_WFV1_E120.6_N38.0_20170201_L1A0002159212.tar.gz"));
////        list.add(a1);
////        list.add(a2);
////        linkUtil.exec(list);
//
//
//    }

    /**
     * 给再加工数据和矢量数据添加link
     * @param listFiles
     * @param newPath
     * @param oldPath
     * @return
     * @version <1> 2018-05-02 zhangshen:Created.
     */
    public boolean makeDataLink(List<File> listFiles, String newPath, String oldPath){
        boolean b = false;
        List<String> listLinks = new ArrayList<>();
        if(listFiles != null && listFiles.size() > 0){
            for(int i = 0; i < listFiles.size(); i++){
                //去掉路径中\\192.168.1.210\mnt\,然后将'\'转换成'/'
                String name = listFiles.get(i).getName();//获取文件名
                String dataPath = CephUtils.getOrderLink(newPath, name);
                String reworkFilePath = CephUtils.getOrderLink(oldPath, name);
                String link= this.makeLink(dataPath,reworkFilePath);
                listLinks.add(link);
            }
            b = this.exec(listLinks); //执行link命令
        }
        return b;
    }

    /**
     * 给再加工数据和矢量数据添加link
     * @param f
     * @param newPath
     * @param oldPath
     * @return
     * @version <1> 2018-05-02 zhangshen:Created.
     */
    public boolean makeDataLinkForFile(File f, String newPath, String oldPath){
        boolean b = false;
        List<String> listLinks = new ArrayList<>();
        //去掉路径中\\192.168.1.210\mnt\,然后将'\'转换成'/'
        String name = f.getName();//获取文件名
        String dataPath = CephUtils.getOrderLink(newPath, name);
        String p = f.getPath();
        String path= p.substring(p.lastIndexOf(oldPath),p.lastIndexOf(File.separator));
        String reworkFilePath = CephUtils.getOrderLink(path, name);
        String link= this.makeLink(dataPath,reworkFilePath);
        listLinks.add(link);
        if(listLinks.size() > 0){
            b = this.exec(listLinks); //执行link命令
        }
        return b;
    }

}
