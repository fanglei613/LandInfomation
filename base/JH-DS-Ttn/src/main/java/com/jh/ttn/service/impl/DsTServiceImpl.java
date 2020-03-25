package com.jh.ttn.service.impl;

import com.jh.ttn.entity.TtnEntity;
import com.jh.ttn.mapping.IDsTMapper;
import com.jh.ttn.service.IDsTService;
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
 * description:地温数据集服务:
 * 1.根据区域构造某一时间段内地温的近三年和10年均值数据查询服务
 * 2.根据区域、日期段查询区域及下一级区域在日期段内的同期环比地温数据
 * @version <1> 2018-04-27 cxw: Created.
 */
@Transactional
@Service
public class DsTServiceImpl implements IDsTService {

    @Autowired
    private IDsTMapper tMapper;

    @Autowired
    private Configuration configuration;//freemarker的模板配置

    private GenerateReportUtil generateReportUtil =  new GenerateReportUtil(this);

    /*
     * 根据区域构造某一时间段内地温的近三年和10年均值数据查询服务
     * @param  数据集查询参数
     *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
     * @return ResultMessage
     @version <1> 2018-04-27 cxw: Created.
     */
    @Override
    public ResultMessage findTForThree(Long regionId, String startDate, String endDate,Integer resolution) {
        //检验参数，校验通过方法才可继续
        ResultMessage result = paramCheck(regionId,startDate,endDate,resolution);
        int avgYearNum = 10; //10年平均
        int showYearNum = 3; //显示最近三年数据
        LocalDate sDate = DateUtil.strToLocalDate(startDate); //开始如期
        LocalDate eDate = DateUtil.strToLocalDate(endDate); //结束日期

        if(result.isFlag()){
            //用于保存三年及十年均值
            Map<String,Object> allMap = new HashMap<String,Object>();
            //临时保存十年均值
            List<TtnVo> avgList = new ArrayList<TtnVo>();
            //封装降雨条件查询对象
            TtnEntity param = new TtnEntity();
            param.setRegionId(regionId);
            param.setStartDate(sDate);
            param.setEndDate(eDate);
            param.setResolution(resolution);
            //获取查询年份日期的数据
            for(int i=0;i<avgYearNum;i++){
                //获取每一年的数据，并将年做为键，保存三年的数据
                List<TtnVo> dataList = tMapper.findTByRegion(param);
                int year = param.getStartDate().getYear();
                String key = year + "";
                if(i<showYearNum){
                    allMap.put(key,dataList);
                }

                addByTenYears(dataList, avgList); //按查询日期和区域分组（SQL中分组），对最近10年的值进行累加
                //用于循环每年数据
                param.setStartDate(param.getStartDate().plusYears(-1));
                param.setEndDate(param.getEndDate().plusYears(-1));
            }

            avgByTenYears(avgList); //计算10年平均值
            allMap.put("10",avgList);

            //设置返回值
            result = ResultMessage.success(allMap);
        }
        return result;
    }

    /*
	 *根据区域、日期段查询区域及下一级区域在日期段内的同期环比地温数据
	 * @param  数据集查询参数
	 *   regionId: 区域ID
     *  startDate: 开始日期
     *    endDate: 结束日期
	*@return ResultMessage
	*@version <1> 2018-04-27 cxw: Created.
	*/
    @Override
    public ResultMessage findTForMon(Long regionId, String startDate, String endDate,Integer resolution) {
        //检验参数，校验通过方法才可继续
        ResultMessage result = paramCheck(regionId,startDate,endDate,resolution);
        if(result.isFlag()){
            LocalDate sDate = DateUtil.strToLocalDate(startDate); //开始如期
            LocalDate eDate = DateUtil.strToLocalDate(endDate); //结束日期
            //封装降雨条件查询对象
            TtnEntity param = new TtnEntity();
            param.setRegionId(regionId);
            param.setStartDate(sDate);
            param.setEndDate(eDate);
            param.setResolution(resolution);
            //获取查询日期的数据
            List<TtnVo> curList = tMapper.findT(param);

            //获取前一年的数据
            LocalDate lastStartDate = sDate.plusMonths(-1);
            LocalDate lastEndDate = eDate.plusMonths(-1);
            param.setStartDate(lastStartDate);
            param.setEndDate(lastEndDate);
            List<TtnVo> lastList = tMapper.findT(param);

            getPercent(curList, lastList);  //上一月value， 以及增长率（百分比）
            result = ResultMessage.success(curList);
        }
        return result;
    }


    /*
     * 提取各接口中计算10年平均的公共部分
     * 实现10年数据的平均计算
     * @param avgList 临时保存十年均值
     * @version <1> 2018-04-28 cxw : created.
     */
    private void avgByTenYears(List<TtnVo> avgList){
        if(avgList != null && avgList.size() > 0){
            //计算十年均值，并保存
            for(TtnVo ds : avgList){
                if(ds.getValue()!=null){
                    double temp = ArithUtil.div(ds.getValue(),ds.getPercent(),2);
                    BigDecimal avg = new BigDecimal(Double.toString(temp));
                    ds.setValue(avg.floatValue());
                }
            }
        }
    }

    /*
     * 提取分组10年数据累加公共部分
     * 按照查询时间（段）和区域分组，对10年数据进行累加（若当年没有数据，则不作为平均年份计算）
     * @param dataList 一年数据
     * @param avgList 临时保存十年均值
     * @version <1> 2018-04-28 cxw: created.
     */
    private void addByTenYears(List<TtnVo> dataList , List<TtnVo> avgList ){
        if(dataList != null && dataList.size() > 0){
            //获取十年均值，如果当年没有值，则不计入平均值的计算。
            for(int j=0;j<dataList.size();j++){
                TtnVo avgDs = null;
                if(j < avgList.size()){
                    avgDs = avgList.get(j);
                }else{
                    avgDs = new TtnVo();
                    avgList.add(avgDs);
                }
                TtnVo tempDs = dataList.get(j);
                avgDs.setRegionId(tempDs.getRegionId());
                avgDs.setMonthAndDay(tempDs.getMonthAndDay()); //横坐标为查询时间段内的月日（mm-dd）
                avgDs.setRegionName(tempDs.getRegionName());
                float total = 0.0f,avgNum;
                total = avgDs.getValue()==null ? total : total + avgDs.getValue();
                total = tempDs.getValue()==null ? total : total + tempDs.getValue();
                avgNum = avgDs.getPercent()==null ? 0.0f:avgDs.getPercent();
                avgNum = tempDs.getValue()==null ? avgNum : avgNum + 1;
                //累加每年的值
                avgDs.setValue(total);
                //计算累加的年份数目
                avgDs.setPercent(avgNum);
            }
        }
    }

    /*
    * 校验地温参数
    * @param 数据集查询参数
    *   regionId: 区域ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    * @return ResultMessage
    * @version <1> 2018-04-27 cxw: Created.
    */
    public ResultMessage paramCheck(Long regionId, String startDate, String endDate,Integer resolution){
        ResultMessage res =ResultMessage.success(null,null,null);
        if(regionId!=null&& StringUtils.isNotBlank(startDate)&& StringUtils.isNotBlank(endDate)&&resolution!=null)
        {
            res =ResultMessage.success();
        }
        else{
             res =ResultMessage.fail("查询失败");
        }
        return res;
    }

    /*
    * 校验地温参数
    * @param 数据集查询参数
    *   regionId: 区域ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    * @return ResultMessage
    * @version <1> 2019-03-20 cxw: Created.
    */
    public ResultMessage paramCheck(Long regionId, String startDate, String endDate){
        ResultMessage res =ResultMessage.success(null,null,null);
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
     * @version <1> 2018-05-02 cxw:created
     *
     */
    private void getPercent(List<TtnVo> curList, List<TtnVo> lastList){
        //将前一年的数据放入lastValue字段中，并计算percent.
        for(TtnVo ds : curList){
            TtnVo lastDs = isEqualRegion(ds,lastList);//ds.isEqualRegion(lastList);
            if(lastDs!=null){
                ds.setLastValue(lastDs.getValue());
                if(ds.getValue()!=null && lastDs.getValue()!=null){
                    double temp = ArithUtil.div(ArithUtil.sub(ds.getValue() , lastDs.getValue()), lastDs.getValue(),4);
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
     * @version <1> 2018-05-02 cxw:Created.
     */
    public TtnVo isEqualRegion(TtnVo ttnVo,List<TtnVo> dataList){
        for(TtnVo ds : dataList){
            if(ttnVo.getRegionId().equals(ds.getRegionId()) && ttnVo.getDate().plusMonths(-1).equals(ds.getDate()) ){
                return ds;
            }
        }
        return null;
    }

    /*
    * 根据区域，日期生成地温报告
    * @param  数据集查询参数
    *   regionId: 区域ID
    *  chinaName:区域中文名
    *  startDate: 开始日期
    *    endDate: 结束日期
    * @return ResultMessage
    * @version <1> 2018-05-02 cxw: Created.
    */
    @Override
    public ResultMessage findTForReport(String chinaName,Long regionId, String startDate, String endDate,Integer resolution) {


        //检验参数，校验通过方法才可继续
        ResultMessage result = paramCheck(regionId,startDate,endDate,resolution);
        List<TtnVo> list2 =new ArrayList<TtnVo>(); //最高地温区域
        List<TtnVo> list3 =new ArrayList<TtnVo>(); //各区域值均值
        List<TtnVo> list4 =new ArrayList<TtnVo>(); //各区中最高地温值
        Map<String,Object> map =  new HashMap<String,Object>();//二年和10年均值数据(只包含选择区域)
        if(result.isFlag()) {

            LocalDate sDate = DateUtil.strToLocalDate(startDate); //开始如期
            LocalDate eDate = DateUtil.strToLocalDate(endDate); //结束日期
            //封装地温条件查询对象
            TtnEntity param = new TtnEntity();
            param.setRegionId(regionId);
            param.setStartDate(sDate);
            param.setEndDate(eDate);
            param.setResolution(resolution);

            TtnEntity paramData =  param;
            LocalDate st = param.getStartDate();
            LocalDate et = param.getEndDate();
            list3 = tMapper.findTById(param) ;
            if (list3.size() > 0) {
                list2 = tMapper.findTMaxByDate(param);
                list4 = tMapper.findMaxTById(param);
                map = findTForTwoById(param);
                paramData.setStartDate(st.plusDays(-30));
                paramData.setEndDate(et.plusDays(-30));
                double drn = tMapper.findAvgTById(paramData);

                double d1 = 0d, d2 = 0d, d3 = 0d,d4=0d,addNum1=0d,addNum2=0d,addNum3=0d;//d1为上一年数据，d2为当前年数据，d3为同期10年均值,d4为上期数据,addNum1为当年减上年同期数据，addNum2为当年减10年同期数据，addNum3为当年减上期数据，
                String addNumText1 = "",addNumText2 = "",addNumText3 = "";
                Iterator<Map.Entry<String,Object>> entries =  map.entrySet().iterator();
                int a=0,b=0,c=0;
                while (entries.hasNext()) {
                    Map.Entry<String,Object> entry = entries.next();
                    int key =  Integer.parseInt(entry.getKey());
                    List<TtnVo> value = (List<TtnVo>) entry.getValue();
                    for(int i=0 ;i<value.size();i++)
                    {
                        double val = 0d;
                        if(value.get(i).getValue()!=null) {
                            val = value.get(i).getValue();
                        }
                        if (key  == (st.getYear()-1)) {
                            d1 += val;
                            a++;
                        }
                        if (key  == st.getYear()) {
                            d2 += val;
                            b++;
                        }
                        if (key  == 10) {
                            d3 += val;
                            c++;
                        }
                    }
                }
                if(a!=0) {
                    d1 = ArithUtil.round(d1 / a, 2);
                }
                else{
                    d1 =ArithUtil.round(d1 , 2);
                }
                if(b!=0) {
                    d2 = ArithUtil.round(d2 / b, 2);
                }
                else{
                    d2 = ArithUtil.round(d2 , 2);
                }
                if(c!=0)
                {
                    d3 = ArithUtil.round(d3/c,2);
                }
                else{
                    d3 = ArithUtil.round(d3,2);
                }

                //构造地温报告对象
                ReportVo rp = makeTtnReportObject(chinaName,startDate,endDate,addNum1,addNumText1,addNum2, addNumText2, addNum3, addNumText3, drn,d1, d2, d3, d4,list2, list3, list4, "t");
                String reStr;
                if (list3.size() == 1){
                    reStr = generateReportUtil.generateReportByTemplate(rp,"noBeyondTReporter.html","t");
                }else{
                    reStr = generateReportUtil.generateReportByTemplate(rp,"tReporter.html","t");
                }

                if(StringUtils.isNotBlank(reStr))
                {
                    // result = ResultMessage.success(reStr);
                    result = ResultMessage.success("t","生成地温报告成功",reStr);
                }
                else{
                    result = ResultMessage.fail("t","生成地温报告失败");
                }
            }
            else{
                result = ResultMessage.fail("t", "地温数据为空");
            }
        }

        return result;
    }

    /*
   * 根据区域构造某一时间段内地温的近二年和10年均值数据查询服务
   * @param param 地温数据集参数对象
   * @return
   * @Version <1> 2018-05-02 cxw: Created.
   */
    public Map<String,Object> findTForTwoById(TtnEntity param) {
        int avgYearNum = 10;
        int showYearNum = 2;
        //用于保存二年及十年均值
        Map<String,Object> allMap = new HashMap<String,Object>();
        //临时保存十年均值
        List<TtnVo> avgList = new ArrayList<TtnVo>();
        if(param != null){
            //获取查询年份日期的数据
            for(int i=0;i<avgYearNum;i++){
                //获取每一年的数据，并将年做为键，保存二年的数据
                List<TtnVo> dataList = tMapper.findTForMonById(param);
                int year = param.getStartDate().getYear();
                String key = year + "";
                if(i<showYearNum){
                    allMap.put(key,dataList);
                }
                addByTenYears(dataList, avgList);  //按查询日期和区域分组（SQL中分组），对最近10年的值进行累加
                //用于循环每年数据
                param.setStartDate(param.getStartDate().plusYears(-1));
                param.setEndDate(param.getEndDate().plusYears(-1));
            }
            avgByTenYears(avgList); //计算10年平均值
            allMap.put("10",avgList);
        }
        return allMap;
    }


    /*
     * 构造地温报告结果数据对象
     * @param 数据集查询参数
     *   regionId: 区域ID
     *       mdate: 日期
     * @return ResultMessage
     * @version <1> 2018-05-07 cxw: Created.
     */
    public ReportVo makeTtnReportObject(String chinaName,String startDate,String endDate,double addNum1,String addNumText1,double addNum2,String addNumText2,
                                        double addNum3,String addNumText3,double drn,double d1,double d2,double d3,double d4,
                                        List<TtnVo> list2,List<TtnVo> list3,List<TtnVo> list4,String dataSetType){
        addNum1 = ArithUtil.round(d2-d1,2);
        addNum2 = ArithUtil.round(d2-d3,2);
        if(addNum1==0d){
            addNumText1="基本持平";
        }
        else if(addNum1>0d){
            addNumText1=(dataSetType == "t"?"偏高":"增加")+addNum1;
        }
        else {
            addNumText1=(dataSetType == "t"?"偏低":"减少")+(-addNum1);
        }
        if(addNum2==0d){
            addNumText2="基本持平";
        }
        else if(addNum2>0d){
            addNumText2=(dataSetType == "t"?"偏高":"增加")+addNum2;
        }
        else {
            addNumText2=(dataSetType == "t"?"偏低":"减少")+(-addNum2);
        }

        if(drn!=0d)
        {
            d4 = ArithUtil.round(drn,2);
        }
        addNum3 = ArithUtil.round(d2-d4,2);
        if(addNum3==0d){
            addNumText3="基本持平";
        }
        else if(addNum3>0d){
            addNumText3=(dataSetType == "t"?"偏高":"增加")+addNum3;
        }
        else {
            addNumText3=(dataSetType == "t"?"偏低":"减少")+(-addNum3);
        }

        double min = 0d,max=0d,most=0d; //min最小值，max最大值，most大部分某值
        String minNameText="",maxNameText="",mostNameText="",maxParentArea="";//minNameText最小值区域，maxNameText最大值区域，mostNameText大部分某值区域,maxArea(下一级区域最大值区域)
        if(list3.size()>0) {
            min = ArithUtil.round(list3.get(0).getValue(),2);
            max = ArithUtil.round(list3.get(list3.size() - 1).getValue(),2);
            minNameText = min + "(" + list3.get(0).getRegionName() + ")";
            maxNameText = max + "(" + list3.get(list3.size() - 1).getRegionName() + ")";
        }
        if(list3.size()>4) {
            most = ArithUtil.round(list3.get(list3.size() - 4).getValue(),2);
            mostNameText = list3.get(list3.size() - 1).getRegionName()+","+ list3.get(list3.size() - 2).getRegionName()+","+ list3.get(list3.size() - 3).getRegionName();
        }
        if(list3.size()==3) {
            most = ArithUtil.round(list3.get(0).getValue(),2);
            mostNameText = list3.get(1).getRegionName()+","+ list3.get(2).getRegionName();
        }
        if(list3.size()==2) {
            most = ArithUtil.round(list3.get(1).getValue(),2);
            mostNameText = list3.get(1).getRegionName();
        }

        double maxParent=0d;//当前根区域最大值
        String mdate = null;
        if(list4.size()>0) {
            if(list4.get(0).getValue()!=null) {
                maxParent = ArithUtil.round(list4.get(0).getValue(),2);
            }
            mdate = DateUtil.localDateToYMD(list4.get(0).getDate());
            maxParentArea = list4.get(0).getRegionName();
        }

        double maxData=0d;//下一级区域最大值
        String maxArea="";//下一级区值最大的区域
        String mxDate = null;
        if(list2.size()>0) {
            if(list2.get(0).getValue()!=null) {
                maxData = ArithUtil.round(list2.get(0).getValue(),2);
            }
            mxDate =DateUtil.localDateToYMD(list2.get(0).getDate());
            maxArea = list2.get(0).getRegionName();
        }
        ReportVo rp = new ReportVo();
        rp.setTotal(d2);
        rp.setAddNumText1(addNumText1);
        rp.setAddNumText2(addNumText2);
        rp.setAddNumText3(addNumText3);
        rp.setMinAreaVal(minNameText);
        rp.setMaxAreaVal(maxNameText);
        rp.setMostval(most);
        rp.setMostArea(mostNameText);
        rp.setMaxVal(maxData);
        rp.setMaxArea(maxArea);
        rp.setMxDate(mxDate);
        rp.setLastval(d1);
        rp.setLastDateVal(d4);
        rp.setTenYearsVal(d3);
        rp.setMdate(mdate);
        rp.setMaxParentVal(maxParent);
        rp.setMaxParentArea(maxParentArea);
        rp.setChinaName(chinaName);
        rp.setStartDate(DateUtil.strToYMD(startDate));
        rp.setEndDate(DateUtil.strToYMD(endDate));
        return  rp;
    }

    /**
     * 查询开始和结束日期时间段内有地温数据的所有日期
     * @param regionId	区域id
     * @param startDate	开始日期
     * @param endDate	结束日期
     * @param accuracyId	数据集精度
     * @return
     */
    @Override
    public ResultMessage findTTimes(Long regionId, String startDate, String endDate, Integer accuracyId) {
        // 校验参数
        ResultMessage result = paramCheck(regionId,startDate,endDate,accuracyId);
        if (result.isFlag()) {
            LocalDate sDate = DateUtil.strToLocalDate(startDate); //开始如期
            LocalDate eDate = DateUtil.strToLocalDate(endDate); //结束日期
            TtnEntity param = new TtnEntity();
            param.setRegionId(regionId);
            param.setStartDate(sDate);
            param.setEndDate(eDate);
            param.setResolution(accuracyId);

            List<LocalDate> dataTimes = new ArrayList<LocalDate>();
            dataTimes = tMapper.findTTimes(param);
            if (dataTimes.size() > 0) {
                result.setData(dataTimes);
            } else {
                result = ResultMessage.fail("无地温数据");
            }
        }
        return result;
    }

    /**
     * 查询开始和结束日期时间段内所有有地温数据
     * @param regionId	区域id
     * @param startDate	开始日期
     * @param endDate	结束日期
     * @return
     * @version <1> 2019-03-20 cxw: Created.
     */
    @Override
    public ResultMessage findAllT(Long regionId, String startDate, String endDate) {
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
            list = tMapper.findAllT(param);
            result.setData(list);
        }
        return result;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
