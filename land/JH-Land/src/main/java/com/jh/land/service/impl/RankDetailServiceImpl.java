package com.jh.land.service.impl;

import com.jh.biz.feign.IRegionService;
import com.jh.land.entity.InitRank;
import com.jh.land.entity.RankDetail;
import com.jh.land.model.RankDetailForBase;
import com.jh.land.model.RankParam;
import com.jh.land.model.RankVO;
import com.jh.land.service.IRankDetailService;
import com.jh.land.service.IRankInfoService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

public class RankDetailServiceImpl implements IRankDetailService {

    @Autowired
    private IRegionService regionService;

    @Autowired
    private IRankInfoService rankInfoService;


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
        Map<String,Object> rankInfo = getRankInfo(rankId);
        String proviceName = rankInfo.get("proviceName").toString();
        String cityName = rankInfo.get("cityName").toString();
        String countyName = rankInfo.get("countyName").toString();
        String townName = rankInfo.get("townName").toString();
        String id = rankInfo.get("id").toString();
        //地块名称
        String rankName = proviceName + cityName + countyName + townName + id + "号";
        //用地分类  通过地块id  查询对应的地块表
        String tableName = rankInfo.get("tableName").toString();
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
        // 土壤类型
        //
        //种植作物 （多个）

        //种植面积

        //累计降水

        //日均降水

        //日均地温

        //最低气温

        //日均温度

        //最高气温


        return null;
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
        return null;
    }

    /*
     * 功能描述:地块详情中的气象信息列表
     * @Param:
     * @Return: [rankDetail]
     * @version<1>  2020/3/25  wangli :Created
     */
    @Override
    public ResultMessage rankDetailForMeteorologicalPhenomena(RankDetail rankDetail) {
        //根据年份和月份查询当月的所有气象信息  包括 降雨  地温  气温
        //监测时间
        //
        //
        //最高气温（℃）
        //
        //
        //最低气温（℃）
        //
        //
        //降雨量（mm）
        //
        //
        //地温（℃）
        return null;
    }



    /*
     * 功能描述: 根据地块id拼接对应的地块表表名
     * @Param:
     * @Return:
     * @version<1>  2020/3/25  wangli :Created
     */
    public Map<String,Object>  getRankInfo(Long rankId){
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
        Map<String,Object> rankInfo = new HashMap<>();
        rankInfo.put("proviceName",proviceName);
        rankInfo.put("proviceCode",proviceCode);
        rankInfo.put("cityName",cityName);
        rankInfo.put("cityCode",cityCode);
        rankInfo.put("countyName",countyName);
        rankInfo.put("countyCode",countyCode);
        rankInfo.put("townName",townName);
        rankInfo.put("townCode",townCode);
        rankInfo.put("id",id);
        rankInfo.put("tableName",tableName);


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
}
