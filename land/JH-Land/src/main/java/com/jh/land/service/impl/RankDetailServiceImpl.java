package com.jh.land.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.biz.feign.IDsTtnService;
import com.jh.biz.feign.IRegionService;
import com.jh.land.entity.DsAreaRank;
import com.jh.land.entity.InitRank;
import com.jh.land.entity.InitSoilTypeName;
import com.jh.land.model.RankDetail;
import com.jh.land.model.*;
import com.jh.land.service.*;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankDetailServiceImpl implements IRankDetailService {

    @Autowired
    private IRegionService regionService;

    @Autowired
    private IRankInfoService rankInfoService;

    @Autowired
    private IDsTtnService dsTtnService;

    @Autowired
    private IRankSoilTypeService rankSoilTypeService;

    @Autowired
    private IRankSoilTypeNameService rankSoilTypeNameService;

    @Autowired
    private IRankAreaService rankAreaService;


    @Override
    public ResultMessage rankDetailForBase(Long rankId) {
        /**
         * 地块名称（省+市+县+乡镇+自然数+号，自然号 需要截取入库时的编号去掉前面的年份和乡镇id）
         * 用地分类（全部暂时默认为耕地，需要在所有地块表中增加一个字段）
         * 地块面积
         * 经纬度
         * 地址  省市县乡镇
         */
        //截取地块所属年份 +所属乡镇+自然数
        RankInfo rankInfo = getRankInfo(rankId);
        String proviceName = rankInfo.getProviceName();
        String cityName = rankInfo.getCityName();
        String countyName = rankInfo.getCountyName();
        String townName = rankInfo.getTownName();
        Integer id = rankInfo.getId();
        //地块名称
        String rankName = proviceName + cityName + countyName + townName + id + "号";
        //用地分类  通过地块id  查询对应的地块表
        String tableName = rankInfo.getTableName();
        RankParam rankParam = new RankParam();
        rankParam.setTableName(tableName);
        rankParam.setRankId(rankId);
        InitRank rankDetail = rankInfoService.queryRankById(rankParam);
        //用地分类
        String rankType = rankDetail.getRankTypeName();
        //地块面积
        BigDecimal rankArea = rankDetail.getArea();
        //地块经纬度
        //经度  正数是东经  负数是西经
        BigDecimal lon = rankDetail.getLon();
        String lonStr = getLonAndLat(lon,0);
        //纬度 正数是北纬  负数是南纬
        BigDecimal lat = rankDetail.getLat();
        String latStr = getLonAndLat(lat,1);
        //经纬度
        String lonAndLat = lonStr + latStr;
        //地块地址
        String rankAddress = proviceName + cityName + countyName + townName;

        RankDetailForBase rankDetailForBase = new RankDetailForBase();
        rankDetailForBase.setRankNo(rankName);
        rankDetailForBase.setRankType(rankType);
        rankDetailForBase.setRankArea(rankArea);
        rankDetailForBase.setRankAddress(rankAddress);
        rankDetailForBase.setLonAndLat(lonAndLat);
        return ResultMessage.success(rankDetailForBase);
    }

    /*
     * 功能描述: 查询地块详情中的概况信息
     * @Param:
     * @Return: [rankDetail]
     * @version<1>  2020/3/24  wangli :Created
     */
    @Override
    public ResultMessage rankDetailForBasicFacts(RankDetail rankDetail) {
        RankFacts rankFacts = new RankFacts();

        //根据地块id获取所属的区县id  气象数据只到level4
        RankInfo rankInfo = getRankInfo(rankDetail.getRankId());
        Long countyId = rankInfo.getCountyId();//所属区县id
        String townCode = rankInfo.getTownCode();//所属乡镇的编码

        // 土壤类型
        //根据所属的乡镇code  查询对应的土壤类型编码  根据土壤类型编码 查询对应的土壤类型
        Integer soilTypeId = rankSoilTypeService.findSoilTypeByTownCode(townCode);
        InitSoilTypeName soilTypeName = rankSoilTypeNameService.findRankSoilTypeNameByTypeId(soilTypeId);
        String soilType = soilTypeName.getThirdTypeName();//亚类名称

        String tableName =  "init_rank_" + rankDetail.getYear() + "_" + rankInfo.getCityId();
        //种植作物 （多个）
        //根据年份  和 所属地级市的id  查询对应的地块分布表中的 对应地块的种植信息
        RankAreaVO rankAreaVO = new RankAreaVO();
        rankAreaVO.setRegionId(rankDetail.getRankId());
        rankAreaVO.setAreaTableName(tableName);
        List<DsAreaRank> areaRankList = rankAreaService.findAreaRankCropById(rankAreaVO);
        String cropName = "";
        for(int i=0;i<areaRankList.size();i++){
            DsAreaRank areaRank = areaRankList.get(i);
            cropName += areaRank.getCropName() + "/";
        }
        cropName.substring(0,cropName.length()-1);
        //种植面积 以最后一个作物的种植面积为准
        BigDecimal cropArea = new BigDecimal("0");
        if(areaRankList.size()>0){
             cropArea = areaRankList.get(0).getArea();
        }
        rankFacts.setSoilType(soilType);
        rankFacts.setCropName(cropName);
        rankFacts.setCropArea(cropArea.toString()+"亩");
        ResultMessage weatherStaticsMsg = dsTtnService.findRankWeather(countyId,LocalDate.parse(rankDetail.getYear()+"-01-01"),LocalDate.parse(rankDetail.getYear()+"-12-31"));
        if(weatherStaticsMsg.isFlag()){
           Map<String,Object> weather = (Map<String,Object>)weatherStaticsMsg.getData();
            //累计降水
            rankFacts.setTotalTrmm(weather.get("totalTrmm").toString()+"mm");
            //日均降水
            rankFacts.setAvgTrmm(weather.get("avgTrmm").toString()+"mm");
            //日均地温
            rankFacts.setAvgTtn(weather.get("avgTtn").toString()+"℃");
            //最低气温
            rankFacts.setMinWeather(weather.get("minWeather").toString()+"℃");
            //日均温度
            rankFacts.setAvgWeather(weather.get("avgWeather").toString()+"℃");
            //最高气温
            rankFacts.setMaxWeather(weather.get("maxWeather").toString()+"℃");
        }
        return ResultMessage.success(rankFacts);
    }

    /*
     * 功能描述: 地块详情中的长势信息列表
     * @Param:
     * @Return: [rankDetail]
     * @version<1>  2020/3/25  wangli :Created
     */
    @Override
    public ResultMessage rankDetailForGrowth(RankDetail rankDetail) {
        //根据年份和月份查询作物当月的所有长势及种植信息
        Map<String,Object> map = findStartAndEnd(rankDetail.getYear()+"-"+rankDetail.getMonth());
        LocalDate startDate = LocalDate.parse(map.get("startDate").toString());
        LocalDate endDate = LocalDate.parse(map.get("endDate").toString());

        return null;
    }

    /*
     * 功能描述:地块详情中的气象信息列表
     * @Param:
     * @Return: [rankDetail]
     * @version<1>  2020/3/25  wangli :Created
     */
    @Override
    public PageInfo<Map<String,Object>> rankDetailForMeteorologicalPhenomena(RankDetail rankDetail) {
        //根据地块id获取所属的区县id  气象数据只到level4
        RankInfo rankInfo = getRankInfo(rankDetail.getRankId());
        Long countyId = rankInfo.getCountyId();//所属区县id

        //根据年份和月份查询当月的所有气象信息  包括 降雨  地温  气温(监测时间，最高气温，最低气温，降雨量，地温)
        Map<String,Object> map = findStartAndEnd(rankDetail.getYear()+"-"+rankDetail.getMonth());
        ResultMessage weatherListMsg =  dsTtnService.findRankWeatherList(countyId,LocalDate.parse(map.get("startDate").toString()),LocalDate.parse(map.get("endDate").toString()));
        //如果查询结果不为空则返回分页信息
        PageHelper.startPage(rankDetail.getPage(),rankDetail.getRows());
        if(weatherListMsg.isFlag()){
            List<Map<String,Object>> weatherList = (List<Map<String,Object>>)weatherListMsg.getData();
            return new PageInfo<Map<String,Object>>(weatherList);
        }
        return null;
    }



    /*
     * 功能描述: 根据地块id拼接对应的地块表表名
     * @Param:
     * @Return:
     * @version<1>  2020/3/25  wangli :Created
     */
    public RankInfo getRankInfo(Long rankId){
        String rankIdStr = rankId.toString();
        String year = rankIdStr.substring(0,4);//年份
        String regionIdStr = rankIdStr.substring(4,13);//所属乡镇
        String id = rankIdStr.substring(13,rankIdStr.length());//自然数 地块编号
        Long regionId = Long.parseLong(regionIdStr);//所属乡镇id
        //--根据region_id查init_region表中所属的地级市

        //根据region_id查询所属的区县
        ResultMessage townMsg = regionService.findRegionById(regionId);
        //根据区县的id查询所属的地级市id
        Map<String,Object> town =(Map<String,Object> ) townMsg.getData();//所属乡镇  level=5

        Long countyId = Long.parseLong(town.get("parentId").toString());
        ResultMessage countyMsg = regionService.findRegionById(countyId);//所属区县 level=4
        Map<String,Object> county =(Map<String,Object> ) countyMsg.getData();

        Long cityId = Long.parseLong(county.get("parentId").toString());

        //根据所属的地级市id查询 地级市信息
        ResultMessage cityMsg = regionService.findRegionById(cityId);
        Map<String,Object> city =(Map<String,Object> ) cityMsg.getData();

        Long proviceId = Long.parseLong(city.get("parentId").toString());
        ResultMessage proviceMsg = regionService.findRegionById(cityId);
        Map<String,Object> provice =(Map<String,Object> ) proviceMsg.getData();
        //根据年份和获取到的地级市id 拼接成对应的地块表的表名
        String tableName = "init_rank_" + year + "_" + cityId;

        String proviceName = provice.get("chinaName").toString();
        String proviceCode = provice.get("regionCode").toString();
        String cityName = city.get("chinaName").toString();
        String cityCode = provice.get("regionCode").toString();
        String countyName = county.get("chinaName").toString();
        String countyCode = provice.get("regionCode").toString();
        String townName = town.get("chinaName").toString();
        String townCode = town.get("regionCode").toString();
        RankInfo rankInfo = new RankInfo();
        rankInfo.setProviceName(proviceName);
        rankInfo.setProviceCode(proviceCode);
        rankInfo.setProviceId(proviceId);
        rankInfo.setCityName(cityName);
        rankInfo.setCityCode(cityCode);
        rankInfo.setCityId(cityId);
        rankInfo.setCountyName(countyName);
        rankInfo.setCountyCode(countyCode);
        rankInfo.setCountyId(countyId);
        rankInfo.setTownName(townName);
        rankInfo.setTownCode(townCode);
        rankInfo.setTownId(regionId);
        rankInfo.setId(Integer.parseInt(id));
        rankInfo.setTableName(tableName);
        return rankInfo;
    }

    public String getLonAndLat(BigDecimal lonOrLat,int type){

        String lonOrLatStr = lonOrLat.toString();
        int point = lonOrLatStr.indexOf(".");
        //longitude  度  截取lon的小数点前的数字
        String du = lonOrLatStr.substring(0,point);
        String calcNum1 = "0"+ lonOrLatStr.substring(point,lonOrLatStr.length());
        //计算分
        BigDecimal fenDecimal = new BigDecimal(calcNum1).multiply(new BigDecimal(60));
        //分 截取lon小数点后的小数部分*60 得到的数字 fen 获取小数点前的部分为分
        int pointFen = fenDecimal.toString().indexOf(".");
        BigDecimal fen = fenDecimal.setScale(BigDecimal.ROUND_DOWN);
        //秒 截取fen 小数点后的数字 *60 得到的数字 miao 截取小数点前的部分为秒
        String clacNum2 = "0" + fen.toString().substring(pointFen,fen.toString().length());
        //计算秒
        BigDecimal miao = new BigDecimal(clacNum2).multiply(new BigDecimal(60)).setScale(BigDecimal.ROUND_DOWN);
        int lonF = lonOrLat.setScale(0,BigDecimal.ROUND_HALF_UP).signum();
        String lonOrLatReturn = "";
        switch (lonF){//判断正负数
            case -1://负数
                du = du.substring(1,du.length());//负数要把前面的负号去掉
                if(type ==0 ){//经度
                    lonOrLatReturn = "西经W" + du +"°" + fen + "′" + miao + "″";
                }else {//纬度
                    lonOrLatReturn = "南纬S" + du +"°" + fen + "′" + miao + "″";
                }
                break;
            case 0://0
                if(type ==0 ){//经度
                    lonOrLatReturn = "本初子午线";
                }else {//纬度
                    lonOrLatReturn = "赤道";
                }
                break;
            case 1://正数
                if(type ==0 ){//经度
                    lonOrLatReturn = "东经E" + du +"°" + fen + "′" + miao + "″";
                }else {//纬度
                    lonOrLatReturn = "北纬N" + du +"°" + fen + "′" + miao + "″";
                }
                break;
        }
        return lonOrLatReturn;
    }

    public Map<String,Object> findStartAndEnd(String yearAndMonth){
        LocalDate date = LocalDate.parse(yearAndMonth+"-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate first = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = date.with(TemporalAdjusters.lastDayOfMonth());
        long days = first.until(last, ChronoUnit.DAYS);
        LocalDate startDate = LocalDate.parse(first.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate endDate = LocalDate.parse(last.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        Map<String,Object> map = new HashMap<>();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        return map;
    }
}
