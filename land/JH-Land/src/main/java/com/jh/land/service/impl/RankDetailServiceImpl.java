package com.jh.land.service.impl;

import com.jh.biz.feign.IRegionService;
import com.jh.land.entity.RankDetail;
import com.jh.land.service.IRankDetailService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.KeyStore;
import java.util.Map;

public class RankDetailServiceImpl implements IRankDetailService {

    @Autowired
    private IRegionService regionService;


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
        String cityName = city.get("chinaName").toString();
        String countyName = county.get("chinaName").toString();
        String townName = town.get("chinaName").toString();
        //地块名称
        String rankName = proviceName + cityName + countyName + townName + id + "号";
        //用地分类  通过地块id  查询对应的地块表
        String rankType = "";
        //地块面积

        //地块经纬度

        //地块地址
        String rankAddress = proviceName + cityName + countyName + townName;

        return null;
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
    public Long getRankTableName(Integer rankId){
        return null;
    }
}
