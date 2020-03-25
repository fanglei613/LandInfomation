package com.jh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.jh.util.ceph.CephUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * 1.
 *
 * @version <1> 2018/8/17 10:18 zhangshen: Created.
 */
public class LinuxStateForShell {
    //private static Logger logger = LoggerFactory.getLogger(LinuxStateForShell.class);

    public static final String FILES_SHELL = "df -h";
//    public static final String FILES_SHELL = "df -hl " + getPath();
    public static final String FILES_SHELL2 = "top -b -n 1";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static String ipAddr;
    private static String userName;
    private static String password;
    public static final int port = 22;


    /**
     * 获取Linux服务器的IP和用户名密码
     * @version<1> 2018-03-14 lcw : Created.
     */
    public static void init(){
        ipAddr = getIpAddr();
        userName = getUserName();
        password = getPassword();
    }

    private static String getIpAddr(){
        return CephUtils.getCephUrl("LINUX_SERVER");
    }

    private static String getUserName(){
        return CephUtils.getCephUrl("LINUX_SERVER_USER");
    }

    private static String getPassword(){
        return CephUtils.getCephUrl("LINUX_SERVER_PWD");
    }

    private static String getPath(){
        return CephUtils.getCephUrl("LINUX_SERVER_BASEDIR").replace("\\", "/");
    }

    //处理系统磁盘状态

    /**
     * Filesystem            Size  Used Avail Use% Mounted on
     * /dev/sda3             442G  327G   93G  78% /
     * tmpfs                  32G     0   32G   0% /dev/shm
     * /dev/sda1             788M   60M  689M   8% /boot
     * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
     *
     * @param commandResult 处理系统磁盘状态shell执行结果
     * @return 处理后的结果
     */
    private static Map<String, Object> disposeFilesSystem(String commandResult) {
        String[] strings = commandResult.split(LINE_SEPARATOR);

        // final String PATTERN_TEMPLATE = "([a-zA-Z0-9%_/]*)\\s";
        String sizeUnit = "";
        String usedUnit = "";
        String availUnit = "";
        String useUnit = "";
        for (int i = 0; i < strings.length; i++) {

            String[] arr = strings[i].split("\\s+");
            sizeUnit = arr[1];
            usedUnit = arr[2];
            availUnit = arr[3];
            useUnit = arr[4];

        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("size", sizeUnit.substring(0, sizeUnit.length() - 1));
        map.put("used", usedUnit.substring(0, usedUnit.length() - 1));
        map.put("avail", availUnit.substring(0, availUnit.length() - 1));
        map.put("use", useUnit.substring(0, useUnit.length() - 1));
        map.put("sizeUnit", sizeUnit);
        map.put("usedUnit", usedUnit);
        map.put("availUnit", availUnit);
        map.put("useUnit", useUnit);

        return map;
    }

    public static Map<String, Object> getInfo() {
        init();
        Connection con = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            con = new Connection(ipAddr);
            con.connect();
            con.authenticateWithPassword(userName, password);

            //计算存储
            Session session = con.openSession();
            session.execCommand(FILES_SHELL);
            InputStream stdout = session.getStdout();
            stdout = new StreamGobbler(session.getStdout());
            BufferedReader dr = new BufferedReader(new InputStreamReader(stdout));
            String line;
            StringBuilder stringBuffer = new StringBuilder();
            while ((line=dr.readLine()) != null) {
                System.out.println(line);
                String path = getPath();

                String str = line.toString();
                String[] arr = str.split("\\b");

                if(str.indexOf(getPath()) != -1){
                    stringBuffer.append(line.trim()).append(LINE_SEPARATOR);
                }

            }
            map = disposeFilesSystem(stringBuffer.toString());
            stdout.close();
            session.close();

            //计算CPU
            Session sessionCPU = con.openSession();
            sessionCPU.execCommand(FILES_SHELL2);
            InputStream stdoutCPU = sessionCPU.getStdout();
            stdoutCPU = new StreamGobbler(sessionCPU.getStdout());
            BufferedReader drCPU = new BufferedReader(new InputStreamReader(stdoutCPU));
            String lineCPU;
            StringBuilder stringBufferCPU = new StringBuilder();
            while ((lineCPU = drCPU.readLine()) != null) {
                //System.out.println(lineCPU);
                stringBufferCPU.append(lineCPU.trim()).append(LINE_SEPARATOR);
            }
            stdout.close();
            sessionCPU.close();

            String[] strings = stringBufferCPU.toString().split(LINE_SEPARATOR);

            String num = "";
            for (String lineStr : strings) {
                lineStr = lineStr.toUpperCase();//转大写处理
                if (lineStr.contains("CPU(S):")) {
                    //String cpuStr = "CPU 用户使用占有率:";
                    num += lineStr.split(":")[1].split(",")[0].replace("US", "");
                    System.out.println("CPU 用户使用占有率:" + num);
                }
            }
            Pattern pattern = Pattern.compile("^-?[0-9]+\\.?[0-9]*$");
            if (pattern.matcher(num.trim()).matches()) {
                Double dou = Double.parseDouble(num);
                map.put("CPUUsed", dou);
            } else {
                map.put("CPUUsed", 0);
            }

        } catch (Exception e) {
            map.put("size", "100");
            map.put("used", "0");
            map.put("avail", "0");
            map.put("use", "0");
            map.put("sizeUnit", "100G");
            map.put("usedUnit", "0G");
            map.put("availUnit", "0G");
            map.put("useUnit", "0%");
            map.put("CPUUsed", 0);
            e.printStackTrace();
        }
        return map;
    }

    /*public static void main(String[] args) {
        getInfo();
    }*/
}
