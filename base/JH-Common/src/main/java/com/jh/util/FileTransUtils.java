package com.jh.util;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 全面支持DOC到DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 转换
 * @version <1> 2018-08-19 lijie : Created.
 */
public class FileTransUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileTransUtils.class);
    /**
     * 常用格式类型，需添加，参考SaveFormat.java中的类型
     */
    static int []formatType ={SaveFormat.DOC, SaveFormat.DOCX,SaveFormat.HTML,SaveFormat.BMP,SaveFormat.JPEG,SaveFormat.PDF,SaveFormat.SWF};

    /**
     * 验证是否有去水印xml
     * @return
     */
    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream in = PropertyUtil.class.getClassLoader().getResourceAsStream("license/pdf_license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(in);
            in.close();
            result = true;
        } catch (Exception e) {
           logger.info("读取license配置异常");
        }
        return result;
    }

    /**
     * DOC转PDF
     * @param fromPath
     * @param toPath
     */
    public static void doc2pdf(String fromPath,String toPath) {
        source2Traget(fromPath,toPath,SaveFormat.PDF);
    }

    /**
     * DOC转PDF,返回File
     * @param fromPath
     * @param toPath
     */
    public static File docToPdf(String fromPath,String toPath) {
        source2Traget(fromPath,toPath,SaveFormat.PDF);
        return new File(toPath);
    }

    /**
     * DOC转JEPG
     * @param fromPath
     * @param toPath
     */
    public static void doc2jpeg(String fromPath,String toPath) {
        source2Traget(fromPath,toPath,SaveFormat.JPEG);
    }

    /**
     * DOC转HTML
     * @param fromPath
     * @param toPath
     */
    public static void doc2html(String fromPath,String toPath) {
        source2Traget(fromPath,toPath,SaveFormat.HTML);
    }

    /**
     * HTML转DOC
     * @param fromPath
     * @param toPath
     */
    public static void html2doc(String fromPath,String toPath) {
        source2Traget(fromPath,toPath,SaveFormat.DOC);
    }

    /**
     * HTML转PDF
     * @param fromPath
     * @param toPath
     */
    public static void html2pdf(String fromPath,String toPath) {
        source2Traget(fromPath,toPath,SaveFormat.PDF);
    }

    /**
     * DOC转DOCX
     * @param fromPath
     * @param toPath
     */
    public static void doc2docx(String fromPath,String toPath) {
        source2Traget(fromPath,toPath,SaveFormat.DOC);
    }

    public static void source2Traget(String sPath,String tPath,int type) {
        logger.info("DocUtils:文档转换开始》》》》》》》》》》》》");
        logger.info("DocUtils:文档转换资源路径为》》》》》》》》》》》》"+sPath);
        logger.info("DocUtils:文档转换目标路径为》》》》》》》》》》》》"+tPath);
        logger.info("DocUtils:文档转换类型为》》》》》》》》》》》》"+type);
        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            logger.info("DocUtils:文档转化验证License失败！》》》》》》");
        }
        if(!ArrayUtils.contains(formatType,type)){
            logger.info("DocUtils:文档转化失败,类型不存在！》》》》》》"+type);
        }
        try {
            File file = new File(tPath);  //新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(sPath); //sPath是将要被转化的word文档
            doc.save(os, type);
            os.flush();
            os.close();
            logger.error("DocUtils:文档转化成功》》》》》》");
        } catch (Exception e) {
            logger.error("DocUtils:文档转化失败》》》》》》"+e.getMessage());
        }
        logger.info("DocUtils:文档转换结束》》》》》》》》》》》》");
    }

    public static void main(String[] args){
        //DocUtils.doc2pdf("C:\\Users\\PC028\\Desktop\\aaa.doc","C:\\Users\\PC028\\Desktop\\aaaa.pdf");
        //DocUtils.doc2jpeg("C:\\Users\\PC028\\Desktop\\aaa.doc","C:\\Users\\PC028\\Desktop\\aaaa.jpeg");
        //DocUtils.doc2html("C:\\Users\\PC028\\Desktop\\test1.docx","C:\\Users\\PC028\\Desktop\\aaaa.html");
        //DocUtils.doc2docx("C:\\Users\\PC028\\Desktop\\aaa.doc","C:\\Users\\PC028\\Desktop\\aaaaa.docx");
        FileTransUtils.html2pdf("C:\\Users\\PC028\\Desktop\\test.html","C:\\Users\\PC028\\Desktop\\1111.pdf");
    }
}

