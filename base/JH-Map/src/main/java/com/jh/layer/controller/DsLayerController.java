package com.jh.layer.controller;

import com.github.pagehelper.PageInfo;
import com.jh.biz.controller.BaseController;
import com.jh.constant.PropertiesConstant;
import com.jh.layer.entity.Layer;
import com.jh.layer.model.LayerVO;
import com.jh.layer.model.MapDataParam;
import com.jh.layer.model.MapImageParam;
import com.jh.layer.service.IDsLayerService;
import com.jh.util.DateUtil;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * 1.图层数据信息Controller
 *
 * @version <1> 2018-05-16 14:52 zhangshen: Created.
 */
@RestController
@RequestMapping("/layer")
public class DsLayerController extends BaseController{

    @Autowired
    private IDsLayerService dsLayerService;

    private static Logger logger = Logger.getLogger(DsLayerController.class);



    /**
     *  @description: 叠加栅格影像
     *  1. 直接加载给定URL的图层信息
     *  2. 加载对URL过滤后的图层信息,如filter= "code like 'USA%' and level='2'"
     *  @param mapImageParam 图层影像参数对象
     *  @return  png : 返回图片流
     *  @version<1> 2018-01-16 cxj: Created.
     */
    @ApiOperation(value="图层影像查询",notes="通过参数查询图层影像")
    @ApiImplicitParam(name = "mapImageParam",value = "图层影像参数对象",required = true, dataType = "MapImageParam")
    @RequestMapping(value="/wms", method= RequestMethod.GET)
    public @ResponseBody void queryGeoPng(MapImageParam mapImageParam, HttpServletRequest req, HttpServletResponse res){
        if(org.apache.commons.lang3.StringUtils.isNotBlank(mapImageParam.getLayers()) && org.apache.commons.lang3.StringUtils.isNotBlank(mapImageParam.getFORMAT())) {
            String geoserver_Url = PropertyUtil.getPropertiesForConfig("GEOSERVER_URL", PropertiesConstant.GEOSERVER_CONFIG);

            StringBuffer fullPath = new StringBuffer(geoserver_Url + "/wms");
            fullPath.append("?SERVICE=" + mapImageParam.getSERVICE());
            fullPath.append("&VERSION=" + mapImageParam.getVERSION());
            fullPath.append("&REQUEST=" + mapImageParam.getREQUEST());
            fullPath.append("&FORMAT=" + mapImageParam.getFORMAT());
            fullPath.append("&TRANSPARENT=" + mapImageParam.getTRANSPARENT());
            fullPath.append("&layers=" + mapImageParam.getLayers());
            fullPath.append("&STYLES=" + mapImageParam.getSTYLES());
            fullPath.append("&WIDTH=" + mapImageParam.getWIDTH());
            fullPath.append("&HEIGHT=" + mapImageParam.getHEIGHT());
            fullPath.append("&BBOX=" + mapImageParam.getBBOX());
            fullPath.append("&crs=" + mapImageParam.getCRS());


            if (StringUtils.isNotBlank(mapImageParam.getCQL_FILTER())){
                fullPath.append("&cql_filter=" + mapImageParam.getCQL_FILTER());
            }

            String filter = mapImageParam.getFILTER();
            if(StringUtils.isNotBlank(filter)){
                filter = filter.replace("<","%3C");
                filter = filter.replace(">","%3E");
                filter = filter.replace(" ","%20");
                fullPath.append("&FILTER=" + filter);
            }

            System.out.println(fullPath.toString());

            dsLayerService.findGeoPng(req, res, fullPath.toString());
        }else{
            logger.error("LayerController queryGeoPng method，request parameter error");
        }
    }

    /**
     *  @description: 查询WMS图层数据(将WMS图层过滤，构建成新的Vector Layer.)
     *  @param mapDataParam 图层数据参数对象
     *  @return  json : 返回图层数据
     *  @version<1> 2018-01-16 cxj: Created.
     */
    @ApiOperation(value="图层数据查询",notes="通过参数查询图层数据")
    @ApiImplicitParam(name = "mapDataParam",value = "图层数据参数对象",required = true, dataType = "MapDataParam")
    @RequestMapping(value="/ows", method= RequestMethod.GET)
    public  Object queryGeoJson(MapDataParam mapDataParam, HttpServletRequest req, HttpServletResponse res) {
        if(org.apache.commons.lang3.StringUtils.isNotBlank(mapDataParam.getOutputFormat()) && org.apache.commons.lang3.StringUtils.isNotBlank(mapDataParam.getTypeName())){
            String geoserver_Url = PropertyUtil.getPropertiesForConfig("GEOSERVER_URL", PropertiesConstant.GEOSERVER_CONFIG);

            StringBuffer fullPath = new StringBuffer(geoserver_Url+"/ows");
            fullPath.append("?typeName=" + mapDataParam.getTypeName());
            fullPath.append("&service=" + mapDataParam.getService());
            fullPath.append("&request=" + mapDataParam.getRequest());
            fullPath.append("&outputFormat=" + mapDataParam.getOutputFormat());

            if(null != mapDataParam.getCQL_FILTER()) {
                fullPath.append("&CQL_FILTER=" + mapDataParam.getCQL_FILTER().replace(" ", "%20"));
            }
            if(null != mapDataParam.getFILTER()){
                String filter = mapDataParam.getFILTER();
                filter = filter.replace("<","%3C");
                filter = filter.replace(">","%3E");
                filter = filter.replace(" ","%20");
                fullPath.append("&FILTER=" + filter);
            }

            return dsLayerService.findGeoJson( fullPath.toString());
        }else{
            logger.error("LayerController queryGeoJson method，request parameter error");
            return null;
        }
    }


    /**
     * 根据图层ids批量发布图层
     * @param ids
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    @ApiOperation(value = "根据图层ids批量发布图层", notes = "根据图层ids批量发布图层")
    @ApiImplicitParam(name = "ids",value = "图层ids",required = true, dataType = "String")
    @PostMapping("/batchPublishTifByIds")
    public ResultMessage batchPublishTifByIds(@RequestBody String ids){
        Layer layer = new Layer();
        layer.setModifier(getCurrentAccountId());
        layer.setModifierName(getCurrentNickName());
        layer.setModifyTime(LocalDateTime.now());
        return dsLayerService.batchPublishTifByIds(ids,layer);
    }

    /**
     * 根据图层ids批量撤回图层
     * @param ids
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    @ApiOperation(value = "根据图层ids批量撤回图层", notes = "根据图层ids批量撤回图层")
    @ApiImplicitParam(name = "ids",value = "图层ids",required = true, dataType = "String")
    @PostMapping("/batchBackTifByIds")
    public ResultMessage batchBackTifByIds(@RequestBody String ids){
        Layer layer = new Layer();
        layer.setPublisher(getCurrentAccountId());
        layer.setPublisherName(getCurrentNickName());
        layer.setPublishTime(LocalDateTime.now());
        return dsLayerService.batchBackTifByIds(ids,layer);
    }


    /**
     * 根据任务id批量发布图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    @ApiOperation(value = "根据任务id批量发布图层", notes = "根据任务id批量发布图层")
    @ApiImplicitParam(name = "taskId",value = "任务id",required = true, dataType = "Integer")
    @PostMapping("/batchPublishTifByTaskId")
    public ResultMessage batchPublishTifByTaskId(@RequestBody Integer taskId){
        Layer layer = new Layer();
        layer.setPublisher(getCurrentAccountId());
        layer.setPublisherName(getCurrentNickName());
        layer.setPublishTime(LocalDateTime.now());
        return dsLayerService.batchPublishTifByTaskId(taskId,layer);
    }

    /**
     * 根据任务id批量撤回图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    @ApiOperation(value = "根据任务id批量撤回图层", notes = "根据任务id批量撤回图层")
    @ApiImplicitParam(name = "taskId",value = "任务id",required = true, dataType = "Integer")
    @PostMapping("/batchBackTifByTaskId")
    public ResultMessage batchBackTifByTaskId(@RequestBody Integer taskId){
        Layer layer = new Layer();
        layer.setModifier(getCurrentAccountId());
        layer.setModifierName(getCurrentNickName());
        layer.setModifyTime(LocalDateTime.now());
        return dsLayerService.batchBackTifByTaskId(taskId,layer);
    }

    /**
     * 根据任务id批量删除图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    @ApiOperation(value = "根据任务id批量删除图层", notes = "根据任务id批量删除图层")
    @ApiImplicitParam(name = "taskId",value = "任务id",required = true, dataType = "Integer")
    @PostMapping("/deleteLayerByTaskId")
    public ResultMessage deleteLayerByTaskId(@RequestBody Integer taskId){
        Layer layer = new Layer();
        layer.setModifier(getCurrentAccountId());
        layer.setModifierName(getCurrentNickName());
        layer.setModifyTime(LocalDateTime.now());
        return dsLayerService.deleteLayerByTaskId(taskId, layer);
    }

    /**
     * 根据区域id, 数据集id, 作物id, 数据时间 查询图层
     * @param
     * @return ResultMessage
     * @version <1> 2019/9/2 14:46 zhangshen:Created.
     */
    @ApiOperation(value = "根据条件查询图层信息", notes = "根据条件查询图层信息")
    @ApiImplicitParam(name = "layer",value = "图层条件",required = true, dataType = "Layer")
    @PostMapping("/findLayerByCondition")
    public ResultMessage findLayerByCondition(@RequestBody Map<String, String> map) {
        Layer layer = new Layer();
        layer.setRegionId(Long.parseLong(map.get("regionId")));
        layer.setDatasetId(Integer.parseInt(map.get("datasetId")));
        layer.setParentId(Long.parseLong(map.get("parentId")));
        if (map.containsKey("cropId") && StringUtils.isNotBlank(map.get("cropId"))){
            layer.setCropId(Integer.parseInt(map.get("cropId")));
        }
        String dataTime = map.get("dataTime");
        if (StringUtils.isNotBlank(dataTime) && dataTime.length() == 19) {
            layer.setDataTime(DateUtil.strToLocalDateTime(dataTime, "yyyy-MM-dd HH:mm:ss"));
        } else if (StringUtils.isNotBlank(dataTime) && dataTime.length() == 10) {
            layer.setDataTime(DateUtil.strToLocalDateTime(dataTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }

        return dsLayerService.findLayerByCondition(layer);
    }

    /**
     * 根据taskId查询图层列表
     * @param taskId
     * @return List<Layer>
     * @version <1> 2019/9/20 lzm:Created.
     */
    @ApiOperation(value = "根据taskId查询图层列表", notes = "根据taskId查询图层列表")
    @ApiImplicitParam(name = "taskId",value = "taskId",required = true, dataType = "Integer")
    @GetMapping("/findLayerListByTaskId")
    public ResultMessage findLayerListByTaskId(Integer taskId) {
        return dsLayerService.findLayerListByTaskId(taskId);
    }

    @ApiOperation(value = "根据图层编号查找图层",notes = "根据图层编号查找图层")
    @ApiImplicitParam(name = "layerId",value = "图层编号",required = true,dataType = "Integer")
    @GetMapping("/findLayerById")
    public ResultMessage findLayerById(@RequestParam  Integer layerId){
        return dsLayerService.findDsLayerByLayerId(layerId);
    }


    /**
     * 图层分页查询
     * @param layer 图层查询参数
     * @return 图层分页数据
     * @version <1> 2019-09-23 lzm： Created.
     */
    @ApiOperation(value = "图层分页查询", notes = "图层分页查询")
    @ApiImplicitParam(name = "layer",value = "layer", dataType = "Layer")
    @PostMapping("/findLayerPageInfo")
    public PageInfo<Layer> findLayerPageInfo(Layer layer) {

        PageInfo<Layer> pages = dsLayerService.findLayerPageInfo(layer);
        return pages;
    }

    /**
     * 图层明细发布或撤销
     * @param layerVO
     * @return ResultMessage
     * @version <1> 2019/9/23 lzm:Created.
     */
    @ApiOperation(value = "图层明细发布或撤销", notes = "图层明细发布或撤销")
    @ApiImplicitParam(name = "layerVO",value = "layerVO", dataType = "LayerVO")
    @PostMapping("/publish")
    public ResultMessage publish(HttpServletRequest request, @RequestBody LayerVO layerVO) {

        Layer layer = new Layer();
        Integer accountId = getCurrentAccountId();
        String accountName = getCurrentNickName();
        if(accountId!=null){
            layer.setPublisher(accountId);
        }

        if(accountName!=null){
            layer.setPublisherName(accountName);
        }

        List<Integer> list = layerVO.getIdList();
        if(layerVO.getPublishStatus()==2201){
            return dsLayerService.batchPublishTifByIds(list, layer);
        }else{
            return dsLayerService.batchBackTifByIds(list,layer);
        }

        //return dsLayerService.publish(layerVO);
    }

    /**
     * 图层删除
     * 1、从geoserver 中删除图层
     * 2、删除图层表记录
     * 3、修改再加工数据表 数据状态为未发布
     * @param layerId
     * @return
     */
    @ApiOperation(value = "根据图层编号删除图层",notes = "根据图层编号删除图层")
    @ApiImplicitParam(name = "layerId",value = "图层编号",required = true,dataType = "Integer")
    @PostMapping("/delete")
    public ResultMessage delete(@RequestParam  Integer layerId){
        return dsLayerService.deleteDsLayerByLayerId(layerId);
    }



}
