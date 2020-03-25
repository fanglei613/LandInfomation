/**
* 文件下载类，用于下载文件或查看文件
* @version <1> 2018-04-27 15:58:20 Hayden : Created.
*/

package com.jh.util;

import com.jh.constant.SysConstant;
import com.jh.util.ceph.CephUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.servlet.http.HttpServletResponse;

public class DownloadUtil{
	/**
     * 根据文件路径输出文件流：用于文件下载、预览
     * @param  fileAbsolutePath : 文件在服务器上的绝对地址
     * @param  response : 获取页面输出流
	* @version <1> 2018-04-27 16:00:45 Hayden : Created.
	*/
 	public static void downloadFileStream(String fileAbsolutePath,String contentType, HttpServletResponse response){
        InputStream iStream = null;
        OutputStream outStrem = null;
        try {
            // iStream = getFileStream(url);
            // response.setContentType("application/jpg");
            response.setHeader("Access-Control-Allow-Headers", "Range");
            response.setHeader("Access-Control-Expose-Headers", "Accept-Ranges, Content-Encoding, Content-Length, Content-Range");
            response.setCharacterEncoding("UTF-8");
            response.setContentType(contentType);
            outStrem = response.getOutputStream();

            String fileName = fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("/")+1);

            File downFile = new File(fileAbsolutePath);
            if (downFile.exists()){
                if (SysConstant.Value_ContentType_Download.equals(contentType)){
                    //设置下载文件名
                    response.setHeader("Content-Disposition","attachment; filename="+fileName);
                }
                iStream = new FileInputStream(downFile);
                byte[] buffer = new byte[2048];
                int len = 0;
                while((len = iStream.read(buffer)) != -1){
                    outStrem.write(buffer,0,len);
                }
            } else {
                response.setHeader("Content-Disposition","attachment; filename=download.txt");
                outStrem.write((fileName + "文件不存在").getBytes());
            }

            outStrem.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(iStream != null){
                try {
                    iStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outStrem != null){
                try {
                    outStrem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载自定义的文件内容
     * @param text  文件内容
     * @version <1> 2018-05-25 16:00:45 xzg : Created.
     */
    public static void downloadTextMsg(String text,String contentType,HttpServletResponse response){
        OutputStream outStrem = null;
        try{
            response.setHeader("Access-Control-Allow-Headers", "Range");
            response.setHeader("Access-Control-Expose-Headers", "Accept-Ranges, Content-Encoding, Content-Length, Content-Range");
            response.setCharacterEncoding("UTF-8");
            response.setContentType(contentType);

            outStrem = response.getOutputStream();
            response.setHeader("Content-Disposition","attachment; filename=fail.txt");
            outStrem.write(text.getBytes());
            outStrem.flush();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if(outStrem != null){
                try {
                    outStrem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }













    /**********************/


    private static DownloadUtil instance = null;

    private DownloadUtil(){}

    public static DownloadUtil getInstance(){
        if(instance == null){
            instance = new DownloadUtil();
        }
        return instance;
    }

    /**
     * 缩略图图片下载
     * @param filePath
     * @param response
     * @version <1> 2018-02-02 lcw： Created.
     */
    public void downloadByThumbnail(String filePath, HttpServletResponse response){
//        String url = DOWNLOAD_URL + File.separator + filePath;  //使用代理

        String url = CephUtils.getAbsolutePath(filePath); //不使用代理
        downloadFileStream(url.replace("\\","/") , response);
    }

    /**
     * 调用浏览器下载数据处理文件
     * @param filePath
     * @param response
     * @version <1> 2018-04-10 xzg： Created.
     */
    public void downloadFile(String filePath, HttpServletResponse response)  {
//        String url = DOWNLOAD_URL + File.separator + filePath;
        String url = CephUtils.getAbsolutePath(filePath);
        downloadFileStream(url.replace("\\","/") , response);
    }


    /**
     * 从第三方URL下载文件
     * @param url
     * @param response
     * @version <1> 2018-02-02 lcw： Created.
     */
    public  void download(String url, HttpServletResponse response){
        InputStream iStream = null;
        OutputStream outStrem = null;
        try {
            iStream = getFileStream(url);
//            response.setContentType("application/jpg");
            response.setHeader("Access-Control-Allow-Headers", "Range");
            response.setHeader("Access-Control-Expose-Headers", "Accept-Ranges, Content-Encoding, Content-Length, Content-Range");
            outStrem = response.getOutputStream();
            byte[] data = readInputStream(iStream);
            outStrem.write(data);
            outStrem.flush();
        } catch (Exception e) {
//            e.printStackTrace();
        }finally {
            if(iStream != null){
                try {
                    iStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outStrem != null){
                try {
                    outStrem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void downloadFileStream(String fileAbsolutePath, HttpServletResponse response){

        System.out.println("下载地址：" +  fileAbsolutePath);
        InputStream iStream = null;
        OutputStream outStrem = null;
        try {
//            iStream = getFileStream(url);
//            response.setContentType("application/jpg");
            response.setHeader("Access-Control-Allow-Headers", "Range");
            response.setHeader("Access-Control-Expose-Headers", "Accept-Ranges, Content-Encoding, Content-Length, Content-Range");
            response.setHeader("Content-Type","application/octet-stream");
            outStrem = response.getOutputStream();

            String fileName = fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("/")+1);

            File downFile = new File(fileAbsolutePath);
            if (downFile.exists()){
                response.setHeader("Content-Disposition","attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
                iStream = new FileInputStream(downFile);
                byte[] buffer = new byte[2048];
                int len = 0;
                while((len = iStream.read(buffer)) != -1){
                    outStrem.write(buffer,0,len);
                }
            } else {
                response.setHeader("Content-Disposition","attachment; filename=download.txt");
                outStrem.write((fileName + "文件不存在").getBytes());
            }

            outStrem.flush();
        } catch (Exception e) {
//            e.printStackTrace();
        }finally {
            if(iStream != null){
                try {
                    iStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outStrem != null){
                try {
                    outStrem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     * @version <1> 2018-02-02 lcw： Created.
     */
    private static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
            bos.flush();
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * HttpClient获取网络路径的文件流
     *
     * @param url 链接字符串
     * @return InputStream
     * @throws IllegalStateException
     * @throws IOException
     */
    private InputStream getFileStream(String url) throws IllegalStateException, IOException {
        //url 中有空格，要使用%20替换掉，才能正确连接到URL，显示缩略图 BY Hayden 2018-04-04
        url = url.replaceAll(" ","%20");
        InputStream inStream = new URL(url).openStream();
        return inStream;
    }


//
//    /**
//     * 批量下载
//     */
//    public  void download(Vector<String> downloadList, String baseUrl, String downloadPath ){
//        // 线程池
//        ExecutorService pool = null;
//        HttpURLConnection connection = null;
//        //循环下载
//        try {
//            for (int i = 0; i < downloadList.size(); i++) {
//                pool = Executors.newCachedThreadPool();
//                String url = (baseUrl + File.separator + downloadList.get(i)).replaceAll("\\\\","/");
//                String filename = getFilename(url);
//                System.out.println("正在下载第" + (i+1) + "个文件，地址：" + url);
//                Future<HttpURLConnection> future = pool.submit(new Callable<HttpURLConnection>(){
//                    @Override
//                    public HttpURLConnection call() throws Exception {
//                        HttpURLConnection connection = null;
//                        connection = (HttpURLConnection) new URL(url).openConnection();
//                        connection.setConnectTimeout(10000);//连接超时时间
//                        connection.setReadTimeout(10000);// 读取超时时间
//                        connection.setDoInput(true);
//                        connection.setDoOutput(true);
//                        connection.setRequestMethod("GET");
//                        //断点续连,每次要算出range的范围,请参考Http 1.1协议
//                        //connection.setRequestProperty("Range", "bytes=0");
//                        connection.connect();
//                        return connection;
//                    }
//                });
//                connection = future.get();
//                System.out.println("下载完成.响应码:"+ connection.getResponseCode());
//                // 写入文件
//                writeFile(new BufferedInputStream(connection.getInputStream()), URLDecoder.decode(filename,"UTF-8"),downloadPath);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != connection)
//                connection.disconnect();
//            if (null != pool)
//                pool.shutdown();
//        }
//    }

    /**
     * 通过截取URL地址获得文件名
     * 注意：还有一种下载地址是没有文件后缀的，这个需要通过响应头中的
     * Content-Disposition字段 获得filename，一般格式为："attachment; filename=\xxx.exe\"
     * @param url
     * @return
     */
    static String getFilename(String url){
        return ("".equals(url) || null == url) ? "" : url.substring(url.lastIndexOf("/") + 1,url.length());
    }

    /**
     * 写入文件
     * @param bufferedInputStream
     */
    static void writeFile(BufferedInputStream bufferedInputStream,String filename,String downloadPath ){
        //创建本地文件
        File destfileFile = new File(downloadPath + File.separator + filename);
        if (destfileFile.exists()) {
            destfileFile.delete();
        }
        if (!destfileFile.getParentFile().exists()) {
            destfileFile.getParentFile().mkdir();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(destfileFile);
            byte[] b = new byte[1024];
            int len = 0;
            // 写入文件
            System.out.println("开始写入本地文件.");
            while ((len = bufferedInputStream.read(b, 0, b.length)) != -1) {
                System.out.println("正在写入字节：" + len);
                fileOutputStream.write(b, 0, len);
                fileOutputStream.flush();
            }
            System.out.println("写入本地文件完成.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fileOutputStream) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                if (null != bufferedInputStream)
                    bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Description: 预览pdf文件报告
     * @param filePath 文件路径
     * @param response
     * @return
     * @version <1> 2018/5/16 15:44 zhangshen: Created.
     */
    public void previewReportPdf(String filePath, HttpServletResponse response){
        String reportUrl = CephUtils.getAbsolutePath("").replace("\\","/");
        String url = reportUrl + filePath.replace("\\","/");
        InputStream iStream = null;
        OutputStream outStrem = null;
        try {
            File file = new File(url);
            response.setContentType("application/pdf");
            response.setHeader("Access-Control-Allow-Headers", "Range");
            response.setHeader("Access-Control-Expose-Headers", "Accept-Ranges, Content-Encoding, Content-Length, Content-Range");
            outStrem = response.getOutputStream();
            if(file.exists()) {
                iStream = new FileInputStream(url);
                byte[] data = readInputStream(iStream);
                outStrem.write(data);
                outStrem.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(iStream != null){
                try {
                    iStream.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            if(outStrem != null){
                try {
                    outStrem.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }
}