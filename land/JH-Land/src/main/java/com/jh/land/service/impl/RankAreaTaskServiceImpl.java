package com.jh.land.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.biz.feign.IRegionService;
import com.jh.constant.PropertiesConstant;
import com.jh.land.entity.DsRankTask;
import com.jh.land.enums.ProductTypeEnum;
import com.jh.land.enums.PublishStatusEnum;
import com.jh.land.enums.TaskStatusEnum;
import com.jh.land.mapping.IDsRankTaskMapper;
import com.jh.land.model.LayerParam;
import com.jh.land.model.RankAreaTaskVO;
import com.jh.land.model.RankAreaVO;
import com.jh.land.service.IRankAreaService;
import com.jh.land.service.IRankAreaTaskService;
import com.jh.util.DateUtil;
import com.jh.util.PropertyUtil;
import com.jh.util.cache.IdTransformUtils;
import com.jh.util.ceph.CephUtils;
import com.jh.util.geoserver.GeoServerUtil;
import com.jh.vo.ResultMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class RankAreaTaskServiceImpl implements IRankAreaTaskService {

    private static Logger logger = Logger.getLogger(RankAreaTaskServiceImpl.class);

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private IDsRankTaskMapper rankAreaTaskMapper;

    @Autowired
    private IRegionService regionService;

    @Autowired
    private IRankAreaService rankAreaService;

    @Override
    public PageInfo<RankAreaTaskVO> findRankAreaTaskPage(RankAreaTaskVO rankAreaTaskVO) {
        PageHelper.startPage(rankAreaTaskVO.getPage(),rankAreaTaskVO.getRows());
        List<RankAreaTaskVO> list = rankAreaTaskMapper.findRankAreaTaskList(rankAreaTaskVO);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<RankAreaTaskVO>(list);
    }

    @Override
    public ResultMessage executeRankAreaTaskById(RankAreaTaskVO rankAreaTaskVO) {
        ResultMessage result = ResultMessage.fail();

        try {

            Integer taskId = rankAreaTaskVO.getTaskId();
            if (taskId == null) {
                return ResultMessage.fail("任务id为空");
            }

            DsRankTask dsRankTask = rankAreaTaskMapper.getById(taskId);
            if (dsRankTask == null) {
                return ResultMessage.fail("入库任务不存在");
            }

            Integer taskStatus = dsRankTask.getTaskStatus();
            if (taskStatus == null) {
                return ResultMessage.fail("任务状态为空");
            }

            //待处理的或处理中的 表示不能重新执行
            if (taskStatus.equals(TaskStatusEnum.TOBEEXECUTED.getValue()) || taskStatus.equals(TaskStatusEnum.EXECUTING.getValue())) {
                return ResultMessage.fail("待处理的或处理中的任务, 不能重新执行");
            }

            //更改任务状态由python程序自动执行
            int i = rankAreaTaskMapper.activationTask(taskId);
            if(i>0){
                result = ResultMessage.success();
            }


        } catch (Exception e) {
            logger.error(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
            result = ResultMessage.fail(e.toString());
        }


        return result;
    }

    @Override
    public ResultMessage deleteRankAreaTaskById(Integer taskId) {
        ResultMessage result = ResultMessage.fail();
       /* DsRankTask dsRankTask = new DsRankTask();
        dsRankTask.setTaskId(taskId);
        rankAreaTaskMapper.delete(dsRankTask);
        result = ResultMessage.success();*/
       try {

            //根据taskId查询任务信息
            DsRankTask dsRankTask = rankAreaTaskMapper.getById(taskId);
            if (dsRankTask != null) {
                //根据任务id删除数据入库任务
                rankAreaTaskMapper.delete(dsRankTask);
                result = ResultMessage.success();
               /* Integer publishStatus = dsRankTask.getPublishStatus();
                //非发布成功的数据
                if (publishStatus != null && !"2201".equals(publishStatus)) {
                    //根据任务id删除数据入库任务
                    rankAreaTaskMapper.delete(dsRankTask);
                    result = ResultMessage.success();
                } else {
                    result = ResultMessage.fail("删除数据入库任务失败, 已发布任务, 不能进行删除操作!");
                }*/
            } else {
                result = ResultMessage.fail("数据入库任务失败, 数据入库任务失败不存在!");
            }

        } catch (Exception e) {
            logger.error(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
            result = ResultMessage.fail(e.toString());
        }

        return result;
    }

    @Override
    public ResultMessage activationTask(Integer taskId) {
        rankAreaTaskMapper.activationTask(taskId);
        return ResultMessage.success();
    }


    @Override
    public ResultMessage findLayerName(LayerParam layerParam) {
        /*
         *先判断区域等级 如果区域等级为5 则根据传递的region_code查询其所属地级市的region_id
        如果不为5 则直接返回  JiaHeDC:ly_dev_dk 因为只在镇一级别才展示地块

        根据区域id和 传递过来的开始时间和结束时间进行匹配
        如果开始时间和结束时间的年份相同 则拼接图层名
        如果年份不同则先取年份大的数字拼接图层名 如果不存在则使用年份小的拼接
        拼接后判定是否存在该图层 如果存在返回拼接好的图层名
        如果不存在 返回ly_dev_dk（避免前端找不到图层报错）
         */
        String workSpace = PropertyUtil.getPropertiesForConfig("WORKSPACE_DATACENTER", PropertiesConstant.GEOSERVER_CONFIG);
        String defaultLayerName = "JiaHeDC:ly_dev_dk";//JiaHeDC:init_rank_2018_3103000146
        //先判断等级是否为5
        if(layerParam.getLevel() == 5){
            String layerName = "";
            String store_name = "";
            //查询所属地级市id 取region_code 前三个字符串
            String city_code = layerParam.getRegionCode().substring(0,10);
            Map<String,Object> cityRegionInfo = this.rankAreaTaskMapper.findRegionByCode(city_code);
            String cityRegionId = cityRegionInfo.get("regionId").toString();
            LocalDate date = layerParam.getDataTime();
            String year = null;
            if(null == date){
                return ResultMessage.success(defaultLayerName);
            }
            year = date.toString().substring(0,4);
            layerName = ProductTypeEnum.RANK.getKey() + "_" + year + "_" + cityRegionId;
            store_name = layerName.replaceFirst("init_rank", "ly_dev_dk");
            //检查图层是否存在 如果图层不存在
            GeoServerUtil geoUtil = new GeoServerUtil();
            Boolean hasLayer = geoUtil.checkLayer(store_name);
            if(hasLayer){//如果存在该图层则返回图层名称
                defaultLayerName = workSpace +":"+ layerName;
            }
        }
        return ResultMessage.success(defaultLayerName);
    }

    /**
     * 获取数据入库相对路径：data\project\rank\dataimport\2019-08-28\1566963159198\
     * @param
     * @return String
     * @version <1> 2019/8/28 11:35 zhangshen:Created.
     */
    private String getReDataInputPath(String timeStamp, Integer productType){
        //数据入库任务前缀路径
        String dataInputPrefix = CephUtils.getCephUrl("RANK_DATA_IMPORT").replace("\\", File.separator);
        String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");

        String reDataInputPath = dataInputPrefix  + File.separator + productType + date + File.separator + timeStamp;
        return reDataInputPath;
    }


    /**
     * 获取数据入库绝对路径：\\192.168.1.223\data\mnt\data\project\rank\dataimport\2019-08-28\1566963159198\
     * @param
     * @return String
     * @version <1> 2019/8/28 11:30 zhangshen:Created.
     */
    private String getAbDataInputPath(String timeStamp, Integer productType){
        //绝对路径
        String absolutePath = CephUtils.getAbsolutePath("");
        //数据入库任务前缀路径
        String dataInputPrefix = CephUtils.getCephUrl("RANK_DATA_IMPORT").replace("\\", File.separator);
        String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");

        String abDataInputPath = absolutePath + dataInputPrefix + File.separator + productType + File.separator + date + File.separator + timeStamp;
        return abDataInputPath;
    }

    /*
     * 功能描述: 发布图层
     * @Param:
     * @Return: [taskIds, task]
     * @version<1>  2019/12/12  wangli :Created
     */
    @Override
    public ResultMessage batchPublishTask(String taskIds, DsRankTask task) {
        ResultMessage result = ResultMessage.fail();
        //数据转换
        JSONObject json = JSONObject.fromObject(taskIds);
        JSONArray jsonArray = JSONArray.fromObject(json.getString("taskIds"));
        Object[] ids = jsonArray.toArray();
        try {
            boolean flag = true;
            for(Object taskId: ids){
                ResultMessage re = publishTaskById(Integer.parseInt(taskId.toString()), task);
                if (!re.isFlag()) {
                    result = ResultMessage.fail("发布数据和图层异常");
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result = ResultMessage.success();
            }
        } catch (Exception e) {
            logger.error(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
        }
        return result;
    }

    /**
     * 根据任务id发布数据和图层
     * @param taskId taskVO
     * @return ResultMessage
     * @version <1> 2019/8/30 16:20 zhangshen:Created.
     */

    public ResultMessage publishTaskById(Integer taskId, DsRankTask taskVO) {

        //传递过来taskIdList
        //根据taskId 查询任务详情
        ResultMessage result = ResultMessage.fail();
        DsRankTask task = rankAreaTaskMapper.getById(taskId);
        if (task == null) {
            return ResultMessage.fail("任务id为" + taskId + "的任务为空");
        }

        Integer taskStatus = task.getTaskStatus();
        if (!TaskStatusEnum.SUCCESS.getValue().equals(taskStatus)) {
            return ResultMessage.fail("任务id为" + taskId + "的任务状态不是处理成功");
        }

        //根据 任务类型进行判断  地块需要发布图层  其他需要发布数据
        int productType = task.getProductType();
        String dataTime = "";
        if(task.getDataTime()!=null){
            dataTime = task.getDataTime().toString();
        }else {
            dataTime = task.getShpTime().toString();
        }
        String year = dataTime.substring(0,4);
        Long regionId = task.getRegionId();
        //先查询所选区域的level 如果level<3  当level=1 全国 则需要校验全国的地市级表  如果level=2则需要校验全省的地市级表
        //如果level=3 则校验他本身
        //如果level >3  如果level=4 则需要查询到他上级的region_id  如果level=5 则需要查询到他上级的上级的region_id 及所属市的region_id
        int level = task.getLevel();
        ResultMessage regionIdListResult =regionService.findRegionIdListByRegionCode(task.getRegionCode());
        List<Long> regionIdList = new ArrayList<>();
        if(regionIdListResult.isFlag()){
            regionIdList = (List<Long>)regionIdListResult.getData();
        }
        List tableNameList = new ArrayList();
        String tableName = "";
        String tableType = "";
        //获取类型  分布/长势/地块
        switch (productType){
            case 2601:
                tableType = ProductTypeEnum.RANK.getKey();
                break;
            case 2602:
                tableType = ProductTypeEnum.AREA.getKey();
                break;
            case 2603:
                tableType = ProductTypeEnum.GROWTH.getKey();
                break;
        }
        switch (level){
            case 1:
            case 2:
                for(int i = 0;i<regionIdList.size();i++){
                    tableName = tableType + "_" + year + "_" + regionId;
                    tableNameList.add(tableName);
                }
                break;
            case 3:
                tableName = tableType + "_" + year + "_" + regionId;
                tableNameList.add(tableName);
                break;
            case 4:
            case 5:
                //截取region_code查询 地级市的region_id
                String city_code = task.getRegionCode().substring(0,10);
                ResultMessage cityCodeResult = regionService.findRegionByCode(city_code);
                if(cityCodeResult.isFlag()){
                    Map<String,Object> cityRegionInfo =(Map<String,Object>) cityCodeResult.getData();
                    String cityRegionId = cityRegionInfo.get("regionId").toString();
                    tableName = tableType + "_" + year + "_" + cityRegionId;
                    tableNameList.add(tableName);
                }
                break;
        }

        //获取类型  分布/长势/地块
        switch (productType){
            case 2601://地块
                for(int i = 0;i < tableNameList.size();i++){
                    String table = tableNameList.get(i).toString();
                    //地块只发布图层
                    GeoServerUtil geoServerUtil = new GeoServerUtil();
                    ResultMessage resultMessage = geoServerUtil.publishPostgis(table);
                }
                break;
            case 2602://分布
                for(int i = 0;i < tableNameList.size();i++){
                    String table = tableNameList.get(i).toString();
                    //分布只发布数据
                    RankAreaVO rankAreaVO = new RankAreaVO();
                    rankAreaVO.setAreaTableName(table);
                    rankAreaVO.setProductId(taskId);
                    rankAreaVO.setPublishStatus(PublishStatusEnum.PUBLISH_STATUS_FBCG.getValue());
                    rankAreaService.publishData(rankAreaVO);
                }
                break;
            case 2603://长势
                break;
        }

        //修改任务发布状态为发布成功
        taskVO.setTaskId(taskId);
        taskVO.setPublishStatus(PublishStatusEnum.PUBLISH_STATUS_FBCG.getValue());
        rankAreaTaskMapper.update(taskVO);
        result = ResultMessage.success();

        return result;
    }


}
