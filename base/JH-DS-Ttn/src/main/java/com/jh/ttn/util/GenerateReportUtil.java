package com.jh.ttn.util;

import com.jh.ttn.service.impl.DsTServiceImpl;
import com.jh.ttn.service.impl.DsTempServiceImpl;
import com.jh.ttn.service.impl.DsTrmmServiceImpl;
import com.jh.ttn.vo.ReportVo;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 数据插入freemark模板工具类
 * @version <1> 2018-05-24 cxw : Created.
 */
public class GenerateReportUtil {

    private static DsTServiceImpl dsTServiceImpl;

    private static DsTrmmServiceImpl dsTrmmServiceImpl;

    private static DsTempServiceImpl dsTempService;

    public GenerateReportUtil(DsTrmmServiceImpl dsTrmmServiceImpl){
        this.dsTrmmServiceImpl = dsTrmmServiceImpl;
    }

    public GenerateReportUtil(DsTServiceImpl dsTServiceImpl){
        this.dsTServiceImpl = dsTServiceImpl;
    }

    public GenerateReportUtil(DsTempServiceImpl dsTempServiceImpl){
        this.dsTempService = dsTempServiceImpl;
    }

    /**
     * 将数据插入freemark模板
     * @reportVo：报告对象
     * templateName：模板名称
     * @version <1> 2018-05-24 cxw : Created.
     */
    public static String  generateReportByTemplate(ReportVo reportVo, String templateName, String dataType){
       //模板
        Template template = null;
        //返回结果数据
        String result = null;
        try {
            if(dataType.equals("t")) {//地温
                template = dsTServiceImpl.getConfiguration().getTemplate(templateName);
            }
            else if (dataType.equals("temp")){//气温
                template = dsTempService.getConfiguration().getTemplate(templateName);
            }
            else{//降雨
                template = dsTrmmServiceImpl.getConfiguration().getTemplate(templateName);
            }
            Writer out = new StringWriter();//定义字符串写入流
            template.process(reportVo, out);//将模板内容写入到字符写入流中。
            result = out.toString();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return result;
    }

}
