package com.jh.land.service.impl;

import com.jh.land.entity.DsAreaRank;
import com.jh.land.entity.RankAreaTimes;
import com.jh.land.enums.ProductTypeEnum;
import com.jh.land.mapping.*;
import com.jh.land.model.*;
import com.jh.land.service.IRankAreaService;
import com.jh.util.DateUtil;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class RankAreaServiceImpl implements IRankAreaService {

    @Autowired
    private IAreaMapper areaMapper;

    @Autowired
    private IYieldMapper yieldMapper;

    //地块分布信息表
    @Autowired
    private IDsAreaRankMapper areaRankMapper;

    //地块分布信息时间轴统计表
    @Autowired
    private IRankAreaTimesMapper rankAreaTimesMapper;

    //检查表是否存在
    @Autowired
    private IDsRankTaskMapper rankAreaTaskMapper;

    //查询所有的地块分布表表名
    @Autowired
    private IRankMapper iRankMapper;


    @Override
    public ResultMessage findAreaTimes(RankAreaVO rankAreaVO) {
        // List<String> list = areaMapper.findRankAreaTimes(areaVO);
        if(rankAreaVO.getEndYear() != null){
            //获取结束时间的年份  自动拼接12月31日
            LocalDate endDate = LocalDate.of(rankAreaVO.getEndYear(),12,31);
            rankAreaVO.setEndDate(endDate);
        }else{
            LocalDate endDate = LocalDate.of(LocalDate.now().getYear(),12,31);
            rankAreaVO.setEndDate(endDate);
        }
        if(rankAreaVO.getStartYear() != null){
            //获取开始时间的年份  自动拼接1月1日
            LocalDate startDate = LocalDate.of(rankAreaVO.getStartYear(),1,1);
            rankAreaVO.setStartDate(startDate);
        }else{
            LocalDate startDate = LocalDate.of(LocalDate.now().getYear(),1,1);
            rankAreaVO.setStartDate(startDate);
        }
        List<String> list = rankAreaTimesMapper.findRankAreaTimes(rankAreaVO);
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage findRankAndBeyondArea(AreaVO areaVO) {
        List<AreaVO> list = areaMapper.findRankAndBeyondArea(areaVO);
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage findThreeYearsArea(AreaVO areaVO) {
        //查询参数校验
        if (null == areaVO.getRegionId() || null == areaVO.getCropId()){
            return ResultMessage.fail("查询参数缺失");
        }


        int days = Integer.parseInt(PropertyUtil.getPropertiesForConfig("calculation_days"));
        int yearNum = 3;// 显示最近三年数据

        Map<String, Object> allMap = new HashMap<String, Object>();
        if(areaVO.getDataTime() != null){
            areaVO.setStartDate(areaVO.getDataTime());
            areaVO.setEndDate(areaVO.getDataTime());
            areaVO.setDataTime(null);
        }

        // 获取查询年份日期的数据
        for (int i = 0; i < yearNum; i++) {
            // 获取每一年的数据，并将年做为键，保存三年的数据
            List<AreaVO> dataList  = areaMapper.findRankAndBeyondArea(areaVO);
            int year = areaVO.getStartDate().getYear();
            String key = year + "";
            if (i < yearNum) {
                allMap.put(key, dataList);
            }
            // 用于循环每年数据
            areaVO.setStartDate(areaVO.getStartDate().minusYears(1).minusDays(days));
            areaVO.setEndDate(areaVO.getEndDate().minusYears(1).plusDays(days));
        }

        // 设置返回值
        return ResultMessage.success(allMap);
    }

    @Override
    public ResultMessage findRegionAndBeyondAreaAndYield(AreaVO areaVO) {
        //区分查询的地区的等级
        List<AreaVO> areaList = areaMapper.findRankAndBeyondArea(areaVO);
        int year = areaVO.getDataTime().getYear();
        LocalDate startDate = DateUtil.strToLocalDate(year + "-01-01");
        LocalDate endDate = DateUtil.strToLocalDate(year + "-12-30");
        YieldVO yieldParam = new YieldVO();
        yieldParam.setCropId(areaVO.getCropId());
        yieldParam.setRegionId(areaVO.getRegionId());
        yieldParam.setStartDate(startDate);
        yieldParam.setEndDate(endDate);
        List<YieldVO> yieldList = yieldMapper.findRegionAndBeyondYield(yieldParam);
        if (null != areaList && null != yieldList){
            for (int i=0;i<areaList.size();i++){
                for (int j=0;j<yieldList.size();j++){
                    if (areaList.get(i).getChinaName().equals(yieldList.get(j).getChinaName())){
                        areaList.get(i).setYield(yieldList.get(j).getYield());
                    }
                }
            }
        }

        return ResultMessage.success(areaList);
    }

    @Override
    public ResultMessage deleteRankArea(RankAreaTaskVO rankAreaTaskVO) {
        areaMapper.deleteRankArea(rankAreaTaskVO);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage totalRankAreaTimeStatistics() {
        //先清空统计表单信息
        rankAreaTimesMapper.deleteAllRankAreaTimes();
        String tableType = ProductTypeEnum.AREA.getKey();
        //查询所有地块分布表的表名 存储到list
        List<String> tableNameList = iRankMapper.findAllRankTables(tableType);
      /*  //查询所有地块表的表名 存储到list
        List<String> tableNameList = areaRankMapper.findAllRankAreaTables();*/
        //查询
        for(int i = 0; i< tableNameList.size(); i++){
            RankAreaParam rankAreaParam = new RankAreaParam();
            String areaTableName = tableNameList.get(i);
            rankAreaParam.setRankAreaTableName(areaTableName);
            //先检查两张表单是否存在 如果有一个不存在则不进行查询
            Map<String,Object> map = new HashMap<>();
            map.put("tableName",areaTableName);
            //对于不存在的表需要先创建
            int isAreaTable = rankAreaTaskMapper.checkTable(map);
            if(isAreaTable >0){
                //查询统计地块表信息插入到预处理的地块信息统计表中
                List<RankAreaTimes> rankAreaInfoList = areaRankMapper.findRankAreaTimes(rankAreaParam);
                if(rankAreaInfoList.size()>0){
                    rankAreaTimesMapper.addRankAreaTimes(rankAreaInfoList);
                }
            }

        }

        return ResultMessage.success();
    }

    @Override
    public ResultMessage publishData(RankAreaVO rankAreaVO) {
        areaRankMapper.publishData(rankAreaVO);
        return ResultMessage.success();
    }

    @Override
    public List<DsAreaRank> findAreaRankCropById(RankAreaVO rankAreaVO) {
        return areaRankMapper.findAreaRankCropById(rankAreaVO);
    }
}
