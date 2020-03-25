package com.jh.ttn.service.impl;

import com.jh.ttn.entity.TtnEntity;
import com.jh.ttn.mapping.IDsTempMapper;
import com.jh.ttn.service.IDsTempService;
import com.jh.ttn.util.GenerateReportUtil;
import com.jh.ttn.vo.ReportVo;
import com.jh.ttn.vo.TtnVo;
import com.jh.util.ArithUtil;
import com.jh.util.DateUtil;
import com.jh.vo.ResultMessage;
import freemarker.template.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * description:气温数据集服务:
 * 1.根据区域构造某一时间段内气温的近三年均值数据查询服务
 * 2.根据区域、日期段查询区域及下一级区域在日期段内的同期环比气温数据
 * 3.根据区域构造7天气温数据查询服务
 * @version <1> 2018-10-16 huxiaoqiang: Created.
 */
@Transactional
@Service
public class DsTempServiceImpl implements IDsTempService {

    @Autowired
    private IDsTempMapper tempMapper;

    @Autowired
    private Configuration configuration;//freemarker的模板配置

    private GenerateReportUtil generateReportUtil =  new GenerateReportUtil(this);

    /*
     * 根据区域构造某一时间段内气温的近三年均值数据查询服务
     * @param datasetParam 数据集查询参数对象
     *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
     * @return ResultMessage
     * @version <1> 2018-10-16 huxiaoqiang: Created.
     */
    @Override
    public ResultMessage findTempForThree(Long regionId, String startDate, String endDate) {
        //检验参数，校验通过方法才可继续
        ResultMessage result = paramCheck(regionId,startDate,endDate);
        if(result.isFlag())
        {
            int avgYearNum = 10; //10年平均
            int showYearNum = 3; //显示最近三年数据
            LocalDate sDate = DateUtil.strToLocalDate(startDate); //开始如期
            LocalDate eDate = DateUtil.strToLocalDate(endDate); //结束日期

            //封装气温条件查询对象
            TtnEntity param = new TtnEntity();
            param.setRegionId(regionId);
            param.setStartDate(sDate);
            param.setEndDate(eDate);
            //用于保存三年及十年均值
            Map<String,Object> allMap = new HashMap<String,Object>();

            //获取查询年份日期的数据
            for(int i=0;i<avgYearNum;i++){
                //获取每一年的数据，并将年做为键，保存三年的数据
                List<TtnVo> dataList = tempMapper.findTempByRegion(param);
                int year = param.getStartDate().getYear();
                String key = year + "";
                if(i<showYearNum){
                    allMap.put(key,dataList);
                }


                //用于循环每年数据
                param.setStartDate(param.getStartDate().plusYears(-1));
                param.setEndDate(param.getEndDate().plusYears(-1));
            }

            //设置返回值
            result = ResultMessage.success(allMap);
        }
        return result;
    }

    /*
     *根据区域、日期段查询区域及下一级区域在日期段内的同期环比气温数据
     * @param 数据集查询参数
     *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
    *@return ResultMessage
    *@version <1> 2018-10-16 huxiaoqiang: Created.
    */
    @Override
    public ResultMessage findTempForMon(Long regionId, String startDate, String endDate) {
        //检验参数，校验通过方法才可继续
        ResultMessage result = paramCheck(regionId,startDate,endDate);
        if(result.isFlag()){
            LocalDate sDate = DateUtil.strToLocalDate(startDate); //开始如期
            LocalDate eDate = DateUtil.strToLocalDate(endDate); //结束日期
            //封装气温条件查询对象
            TtnEntity param = new TtnEntity();
            param.setRegionId(regionId);
            param.setStartDate(sDate);
            param.setEndDate(eDate);
            //获取查询日期的数据
            List<TtnVo> curList = tempMapper.findTemp(param);

            //获取前一年的数据
            LocalDate lastStartDate =sDate.plusYears(-1);
            LocalDate lastEndDate = eDate.plusYears(-1);
            param.setStartDate(lastStartDate);
            param.setEndDate(lastEndDate);
            int lastYear = param.getStartDate().getYear();
            List<TtnVo> lastList = tempMapper.findTemp(param);

            //将前一年的数据放入lastValue字段中，并计算percent.
            getPercent(curList, lastList);
            result = ResultMessage.success(curList);
        }
        return result;
    }



    /*
    * 校验气温气温参数
    * @param 数据集查询参数
    *   regionId: 区域ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    * @return ResultMessage
    * @version <1> 2018-10-16 huxiaoqiang: Created.
    */
    private ResultMessage paramCheck(Long regionId, String startDate, String endDate ){
        ResultMessage res =ResultMessage.success();
        if(regionId!=null&& StringUtils.isNotBlank(startDate)&& StringUtils.isNotBlank(endDate))
        {
            res =ResultMessage.success();
        }
        else{
             res =ResultMessage.fail("查询失败");
        }
        return res;
    }

    /*
     * @description:同一区域上一年度同期value放入lastValue值中，并计算环比增长率
     * @param curList 当前日期数据
     * @param lastList 上一期数据
     * @version <1> 2018-10-16 huxiaoqiang: Created.
     *
     */
    private void getPercent(List<TtnVo> curList, List<TtnVo> lastList){
        //将前一年的数据放入lastValue字段中，并计算percent.
        for(TtnVo ds : curList){
            TtnVo lastDs = isEqualRegion(ds,lastList);//ds.isEqualRegion(lastList);
            if(lastDs!=null){
//                ds.setLastMaxValue(lastDs.getMaxValue());
//                if (ds.getMaxValue() != null && lastDs.getMaxValue() != null){
//                    double temp = ArithUtil.div(ArithUtil.sub(ds.getMaxValue(),lastDs.getMaxValue()),lastDs.getMaxValue(),4);
//                    BigDecimal maxPercent = new BigDecimal(temp);
//                    ds.setMaxPercent(maxPercent.floatValue());
//                }
//                ds.setLastMinValue(lastDs.getMinValue());
//                if (ds.getMinValue() != null && lastDs.getMinValue() != null){
//                    double temp = ArithUtil.div(ArithUtil.sub(ds.getMinValue(),lastDs.getMinValue()),lastDs.getMinValue(),4);
//                    BigDecimal minPercent = new BigDecimal(temp);
//                    ds.setMinPercent(minPercent.floatValue());
//                }
                ds.setLastValue(lastDs.getValue());
                if (ds.getValue() != null && lastDs.getValue() != null){
                    double temp = ArithUtil.div(ArithUtil.sub(ds.getValue(),lastDs.getValue()),lastDs.getValue(),4);
                    BigDecimal percent = new BigDecimal(temp);
                    ds.setPercent(percent.floatValue());
                }
            }
        }
    }

    /**
     * @description:在列表中根据区域、时间找相同的对象
     * @param ttnVo 当前日期数据对象
     * @param dataList 上一期数据
     * @version <1> 2018-10-16 huxiaoqiang: Created.
     */
    public TtnVo isEqualRegion(TtnVo ttnVo,List<TtnVo> dataList){
        for(TtnVo ds : dataList){
            if(ttnVo.getRegionId().equals(ds.getRegionId()) && ttnVo.getDate().plusYears(-1).equals(ds.getDate()) ){
                return ds;
            }
        }
        return null;
    }


    /**
     * 生成气温报告
     * @param regionId  区域id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     * @version <1> 2018-10-16 huxiaoqiang: Created.
     */
    @Override
    public ResultMessage findTempForReport(String chinaName,Long regionId, String startDate, String endDate) {
        //检验参数，校验通过方法才可继续
        ResultMessage result = paramCheck(regionId,startDate,endDate);

        LocalDate sDate = DateUtil.strToLocalDate(startDate); //开始如期
        LocalDate eDate = DateUtil.strToLocalDate(endDate); //结束日期

        //封装数据查询参数
        TtnEntity param = new TtnEntity();
        param.setRegionId(regionId);
        param.setStartDate(sDate);
        param.setEndDate(eDate);


        //查询当前指定区域在指定时间段内最高温度以及对应的日期
        TtnVo maxAndDate = tempMapper.findMaxAndDate(param);
        //查询当前指定区域在指定时间段内最低温度以及对应的日期
        TtnVo minAndDate = tempMapper.findMinAndDate(param);


        if (maxAndDate != null && minAndDate != null){
            //查询当前查询区域和时间段内该区域平均最低气温和平均最高气温
            TtnVo avgMaxMin = tempMapper.findAvgMaxMin(param);

            //根据区域id，开始和结束日期查询该区域当前时间段内，上期(30天前)、去年同期、前年同期的最高温和最低温数据
            List<TtnVo> maxMinBeforeList = tempMapper.findMaxMinBefore(param);

            //查询当前指定区域的下级区域中在指定时间段内最高温度以及对应的日期以及区域信息
            TtnVo underAreaMaxDate = tempMapper.findUnderAreaMaxDate(param);
            //查询当前指定区域的下级区域中在指定时间段内最低温度以及对应的日期以及区域信息
            TtnVo underAreaMinDate = tempMapper.findUnderAreaMinDate(param);

            //构造气温报告对象
            ReportVo rp = makeTtnReportObject(chinaName,startDate,endDate,maxAndDate,minAndDate,avgMaxMin,maxMinBeforeList,underAreaMaxDate,underAreaMinDate);
            String reStr;
            if (null != underAreaMaxDate && null != underAreaMinDate){
                reStr = generateReportUtil.generateReportByTemplate(rp,"tempReporter.html","temp");
            }else {
                reStr = generateReportUtil.generateReportByTemplate(rp,"noBeyondTempReporter.html","temp");
            }

            if(StringUtils.isNotBlank(reStr))
            {
                result = ResultMessage.success("temp","生成气温报告成功",reStr);
            }
            else{
                result = ResultMessage.fail("temp","生成气温报告失败");
            }

        }else {
            result = ResultMessage.fail("temp", "气温数据为空");
        }

        return result;
    }



    /**
     * 构造气温报告结果数据对象
     * @param chinaName 区域中文名
     * @param startDate 查询开始日期
     * @param endDate   查询结束日期
     * @param maxAndDate    当前指定区域在指定时间段内最高温度以及对应的日期对象
     * @param minAndDate    当前指定区域在指定时间段内最低温度以及对应的日期对象
     * @param avgMaxMin     当前查询区域和时间段内该区域平均最低气温和平均最高气温对象
     * @param maxMinBeforeList  根据区域id，开始和结束日期查询该区域当前时间段内，上期(30天前)、去年同期、前年同期的最高温和最低温数据列表
     * @param underAreaMaxDate  当前指定区域的下级区域中在指定时间段内最高温度以及对应的日期以及区域信息对象
     * @param underAreaMinDate  当前指定区域的下级区域中在指定时间段内最低温度以及对应的日期以及区域信息对象
     * @return
     */
    private ReportVo makeTtnReportObject(String chinaName,String startDate,String endDate,TtnVo maxAndDate,TtnVo minAndDate,TtnVo avgMaxMin,List<TtnVo> maxMinBeforeList, TtnVo underAreaMaxDate, TtnVo underAreaMinDate ){

        double avgMinVal = 0d;//最高气温平均值
        double avgMaxVal = 0d;//最低气温平均

        double nowMaxVal = 0d;//当前选定时间段内该区域的最高气温
        double nowMinVal = 0d;//当前选定时间段内该区域的最低气温
        double lastDateMinVal = 0d;//上期(30天前)该区域时间段内的最低气温
        double lastMinval = 0d;//去年同期该区域时间段内的最低气温
        double twoYearsMinVal = 0d;//前年同期该区域时间段内的最低气温
        double lastDateMaxVal = 0d;//上期(30天前)该区域时间段内的最高气温
        double lastMaxval = 0d;//去年同期该区域时间段内的最高气温
        double twoYearsMaxVal = 0d;//前年同期该区域时间段内的最高气温

        avgMinVal = avgMaxMin.getMinValue();//最高气温平均值
        avgMaxVal = avgMaxMin.getMaxValue();//最低气温平均


        if (maxMinBeforeList != null && maxMinBeforeList.size() > 0){
            for (TtnVo maxMinBefore : maxMinBeforeList)  {
                String timeFlag = maxMinBefore.getTimeFlag();
                if ("now".equals(timeFlag)){//当前选定时间
                    nowMinVal = maxMinBefore.getMinValue();
                    nowMaxVal = maxMinBefore.getMaxValue();
                }else if ("30daysAgo".equals(timeFlag)){//当前选定时间的30天前
                    lastDateMinVal = maxMinBefore.getMinValue();
                    lastDateMaxVal = maxMinBefore.getMaxValue();
                }else if ("1yearAgo".equals(timeFlag)){//当前选定时间段的一年前同期
                    lastMinval = maxMinBefore.getMinValue();
                    lastMaxval = maxMinBefore.getMaxValue();
                }else if ("2yearAgo".equals(timeFlag)){//当前选定时间段的二年前同期
                    twoYearsMinVal = maxMinBefore.getMinValue();
                    twoYearsMaxVal = maxMinBefore.getMaxValue();
                }

            }
        }

        String addNumText3="",addNumText1="",addNumText2="",addNumText30="",addNumText10="",addNumText20="";

        //最低温与上期相比
        addNumText3 = compareLast(lastDateMinVal,nowMinVal);

        //最低温与去年同期相比
        addNumText1 = compareLast(lastMinval,nowMinVal);

        //最低温与前年同期相比
        addNumText2 = compareLast(twoYearsMinVal,nowMinVal);

        //最高温与上期相比
        addNumText30 = compareLast(lastDateMaxVal,nowMaxVal);

        //最高温与去年同期相比
        addNumText10 = compareLast(lastMaxval,nowMaxVal);

        //最高温与前年同期相比
        addNumText20 = compareLast(twoYearsMaxVal,nowMaxVal);

        //当前区域最高温度的日期
        String mdate = DateUtil.localDateToYMD(maxAndDate.getDate());
        //当前区域的最高温度
        double maxParentVal = maxAndDate.getMaxValue();

        //当前区域最低温度的日期
        String m2date = DateUtil.localDateToYMD(minAndDate.getDate());
        //当前区域最低温度
        double minParentVal = minAndDate.getMinValue();

        ReportVo rp = new ReportVo();

        if (null != underAreaMaxDate){
            //当前区域的下级区域中最高温度的日期
            String mxDate = DateUtil.localDateToYMD(underAreaMaxDate.getDate());
            //当前区域的下级区域中的最高温度
            double maxVal = underAreaMaxDate.getMaxValue();
            String maxArea = underAreaMaxDate.getRegionName();
            rp.setMxDate(mxDate);
            rp.setMaxArea(maxArea);
            rp.setMaxVal(maxVal);
        }

        if (null != underAreaMinDate){
            //当前区域的下级区域中最低温度的日期
            String mx2Date = DateUtil.localDateToYMD(underAreaMinDate.getDate());
            //当前区域的下级区域中的最低温度
            double minVal = underAreaMinDate.getMinValue();
            String minArea = underAreaMinDate.getRegionName();
            rp.setMx2Date(mx2Date);
            rp.setMinVal(minVal);
            rp.setMinArea(minArea);
        }


        rp.setChinaName(chinaName);
        rp.setStartDate(DateUtil.strToYMD(startDate));
        rp.setEndDate(DateUtil.strToYMD(endDate));
        rp.setAvgMinVal(avgMinVal);
        rp.setAvgMaxVal(avgMaxVal);
        rp.setLastDateMinVal(lastDateMinVal);
        rp.setAddNumText3(addNumText3);
        rp.setLastMinval(lastMinval);
        rp.setAddNumText1(addNumText1);
        rp.setTwoYearsMinVal(twoYearsMinVal);
        rp.setAddNumText2(addNumText2);
        rp.setLastDateMaxVal(lastDateMaxVal);
        rp.setAddNumText30(addNumText30);
        rp.setAddNumText20(addNumText20);
        rp.setAddNumText10(addNumText10);
        rp.setMdate(mdate);
        rp.setMaxParentVal(maxParentVal);
        rp.setMinAreaVal(String.valueOf(nowMinVal));
        rp.setMaxAreaVal(String.valueOf(nowMaxVal));
        rp.setLastMaxval(lastMaxval);
        rp.setTwoYearsMaxVal(twoYearsMaxVal);
        rp.setM2date(m2date);
        rp.setMinParentVal(minParentVal);
        return  rp;
    }


    private String compareLast(double before,double now){
        String result = "";
        if (now - before > 0d){
            result = "偏高" + (now - before);
        }else if(now - before < 0d){
            result = "偏低" + (before - now);
        }else {
            result = "持平";
        }
        return result;
    }

    /**
     * 查询开始和结束日期时间段内有气温数据的所有日期
     * @param regionId	区域id
     * @param startDate	开始日期
     * @param endDate	结束日期
     * @return
     */
    @Override
    public ResultMessage findTempTimes(Long regionId, String startDate, String endDate) {
        // 校验参数
        ResultMessage result = paramCheck(regionId,startDate,endDate);
        if (result.isFlag()) {
            LocalDate sDate = DateUtil.strToLocalDate(startDate); //开始如期
            LocalDate eDate = DateUtil.strToLocalDate(endDate); //结束日期
            TtnEntity param = new TtnEntity();
            param.setRegionId(regionId);
            param.setStartDate(sDate);
            param.setEndDate(eDate);

            List<LocalDate> dataTimes = new ArrayList<LocalDate>();
            dataTimes = tempMapper.findTempTimes(param);
            if (dataTimes.size() > 0) {
                result.setData(dataTimes);
            } else {
                result = ResultMessage.fail("无地温数据");
            }
        }
        return result;
    }

    /**
     * 查询开始和结束日期时间段内有气温数据的所有日期
     * @param regionId	区域id
     * @param startDate	开始日期
     * @param endDate	结束日期
     * @version <1> 2019-03-20 cxw: Created.
     * @return
     */
    @Override
    public ResultMessage findAllTemp(Long regionId, String startDate, String endDate) {
        // 校验参数
        ResultMessage result = paramCheck(regionId,startDate,endDate);
        if (result.isFlag()) {
            LocalDate sDate = DateUtil.strToLocalDate(startDate); //开始如期
            LocalDate eDate = DateUtil.strToLocalDate(endDate); //结束日期
            TtnEntity param = new TtnEntity();
            param.setRegionId(regionId);
            param.setStartDate(sDate);
            param.setEndDate(eDate);

            List<TtnVo> list = new ArrayList<TtnVo>();
            list = tempMapper.findAllTemp(param);
            result.setData(list);
        }
        return result;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
