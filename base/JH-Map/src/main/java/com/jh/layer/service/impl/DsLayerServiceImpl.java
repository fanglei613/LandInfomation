package com.jh.layer.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.constant.CommonDefineEnum;
import com.jh.layer.entity.Layer;
import com.jh.layer.mapping.IDsLayerMapper;
import com.jh.layer.mapping.IRegionMapper;
import com.jh.layer.model.LayerVO;
import com.jh.layer.service.IDsLayerService;
import com.jh.util.JsonByHttp;
import com.jh.util.cache.IdTransformUtils;
import com.jh.util.ceph.CephUtils;
import com.jh.util.geoserver.GeoServerUtil;
import com.jh.vo.ResultMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * 1.图层数据信息 实现类
 *
 * @version <1> 2018-05-16 14:54 zhangshen: Created.
 */
@Service
@Transactional
public class DsLayerServiceImpl implements IDsLayerService {

    @Autowired
    private IDsLayerMapper dsLayerMapper;

    @Autowired
    private IRegionMapper regionMapper;

    private static Logger logger = Logger.getLogger(DsLayerServiceImpl.class);

    private static String STYLE = "style_lyg_";

    private static String LY = "ly_";

    private static String STORE = "store_";

    @Override
    public void findGeoPng(HttpServletRequest req, HttpServletResponse res, String path) {
        OutputStream out = null;
        BufferedInputStream bis = null;
        try {
            req.setCharacterEncoding("UTF-8");
            out = res.getOutputStream();
            URL url = new URL(path);
            HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            res.setHeader("Content-Type", "image/png");
            StreamUtils.copy(bis,out);
        } catch (Exception e) {
            logger.error("LayerService findGeoPng method :" + e.getMessage());
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
                if(bis != null){
                    bis.close();
                }
            }catch (Exception e){
                logger.error("LayerService findGeoPng method，data flow is closed exception:" + e.getMessage());
            }
        }
    }

    @Override
    public Object findGeoJson(String path) {
        JsonByHttp jsonByHttp = new JsonByHttp();
        String result= jsonByHttp.sendPost(path.replace(" ", ""),"");
//        JSONObject string_to_json = null;
//        if(!"".equals(result)&&result!=null&&result.indexOf("{")!=-1) {
//            result = result.replaceAll("null","\"null\"");
//             string_to_json = JSONObject.fromObject(result);
//             int num =  Integer.parseInt(string_to_json.getString("totalFeatures"));
//             if(num>0){
//                 return string_to_json;
//             }else {
//                 return null;
//             }
//        }else{
//            return null;
//         }
        return result;
    }

    /**
     * 根据图层ids批量发布图层
     * @param ids
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    @Override
    public ResultMessage batchPublishTifByIds(String ids, Layer layer) {
        //数据转换
        JSONObject json = JSONObject.fromObject(ids);
        JSONArray jsonArray = JSONArray.fromObject(json.getString("ids"));
        Object[] arr = jsonArray.toArray();
        List<Integer> list = new ArrayList<Integer>();
        for (Object o : arr) {
            list.add(Integer.parseInt(o.toString()));
        }
        return batchPublishTifByIds(list, layer);
    }

    /**
     * 根据图层ids批量撤回图层
     * @param ids
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    @Override
    public ResultMessage batchBackTifByIds(String ids, Layer layer) {
        //数据转换
        JSONObject json = JSONObject.fromObject(ids);
        JSONArray jsonArray = JSONArray.fromObject(json.getString("ids"));
        Object[] arr = jsonArray.toArray();
        List<Integer> list = new ArrayList<Integer>();
        for (Object o : arr) {
            list.add(Integer.parseInt(o.toString()));
        }
        return batchBackTifByIds(list, layer);
    }

    @Override
    public ResultMessage batchPublishTifByIds(List<Integer> ids, Layer layer) {
        ResultMessage result = ResultMessage.fail();
        boolean flag = true;
        for (Integer layerId : ids) {
            Layer layer1 = dsLayerMapper.findLayerListByLayerId(layerId);
            LayerVO layerVO = new LayerVO();
            BeanUtils.copyProperties(layer1,layerVO);
            IdTransformUtils.idTransForObj(layerVO);
            LocalDateTime dataTime = layerVO.getDataTime();

            //String regionCode = layerVO.getRegionCode();
            String datasetCode = layerVO.getDatasetCode().toLowerCase();
            String cropCode = layerVO.getCropCode();
            //String dt = DateUtil.dateToString(dataTime,"yyyy-MM-dd");

            String name = "";
            String geoStyle = "";

            String layerN = layerVO.getLayerName();

            if (StringUtils.isNotBlank(layerN)) {
                String a = layerN.split(":")[1];
                name = a.substring(a.indexOf("_") + 1, a.length());
            } else{
                if (StringUtils.isNotEmpty(cropCode)) {
                    cropCode = cropCode.toLowerCase();
                    //name = regionCode + "_" + cropCode + "_" + dt + "_" + datasetCode;
                } else {
                    //name = regionCode + "_" + dt + "_" + datasetCode;
                }
            }


            if (StringUtils.isNotEmpty(cropCode)) {
                cropCode = cropCode.toLowerCase();
                if (layerVO.getDatasetId() == 1803 || layerVO.getDatasetId() == 1820 || layerVO.getDatasetId() == 1824) {
                    geoStyle = STYLE + datasetCode;
                } else {
                    geoStyle = STYLE + cropCode + "_" + datasetCode;
                }
            } else {
                geoStyle = STYLE + datasetCode;
            }


            String layerName = LY + name;
            String storeName = STORE + name;
            logger.info("发布图层：" + layerName + "-----------------------" + storeName);
            logger.info("发布图层的样式：" + geoStyle);

            File layerFile = new File(CephUtils.getAbsolutePath(layer1.getFilePath()));


            GeoServerUtil geoUtil = new GeoServerUtil();
            ResultMessage r = geoUtil.publishTiff(layerName, layerFile, geoStyle);

            if (r.isFlag()) {
                layer.setLayerId(layerId);
                layer.setLayerName(r.getData().toString());
                layer.setPublishTime(LocalDateTime.now());
                layer.setPublishStatus(2201);
                dsLayerMapper.updateDsLayer(layer);
            } else {
                flag = false;
                break;
            }


        }

        if (flag) {
            result = ResultMessage.success();
        }

        return result;
    }

    @Override
    public ResultMessage batchBackTifByIds(List<Integer> ids, Layer layer) {
        ResultMessage result = ResultMessage.fail();
        boolean flag = true;
        for (Integer layerId : ids) {
            Layer layer1 = dsLayerMapper.findLayerListByLayerId(layerId);
            //LayerVO layerVO = new LayerVO();
            //BeanUtils.copyProperties(layer1,layerVO);
            //IdTransformUtils.idTransForObj(layerVO);
            //Date dataTime = layerVO.getDataTime();
            //String name = layerVO.getRegionCode() + "_" + layerVO.getCropCode() + "_" + DateUtil.dateToString(dataTime,"yyyy-MM-dd");
            //String layerName = LY + name + DISTRIBUTION;
            //String storeName = STORE + name + DISTRIBUTION;
            //logger.info("撤回图层：" + layerName + "-----------------------" + storeName);

            GeoServerUtil geoUtil = new GeoServerUtil();
            String layerName= layer1.getLayerName();
            ResultMessage r = geoUtil.removeStore(layerName.substring(layerName.indexOf(":")+1).replace("ly_","store_"));

            if (r.isFlag()) {
                layer.setLayerId(layerId);
                layer.setPublishStatus(2202);
                dsLayerMapper.updateDsLayer(layer);
            } else {
                flag = false;
                break;
            }
        }

        if (flag) {
            result = ResultMessage.success();
        }

        return result;
    }

    /**
     * 根据任务id批量发布图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    @Override
    public ResultMessage batchPublishTifByTaskId(Integer taskId, Layer layer) {
        List<Layer> layerList = dsLayerMapper.findLayerListByTaskId(taskId);
        List<Integer> ids = new ArrayList<Integer>();
        for(Layer l : layerList) {
            ids.add(l.getLayerId());
        }
        return batchPublishTifByIds(ids, layer);
    }

    /**
     * 根据任务id批量撤回图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    @Override
    public ResultMessage batchBackTifByTaskId(Integer taskId, Layer layer) {
        List<Layer> layerList = dsLayerMapper.findLayerListByTaskId(taskId);
        List<Integer> ids = new ArrayList<Integer>();
        for(Layer l : layerList) {
            ids.add(l.getLayerId());
        }
        return batchBackTifByIds(ids, layer);
    }

    /**
     * 根据任务id批量删除图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    @Override
    public ResultMessage deleteLayerByTaskId(Integer taskId, Layer layer) {
        //撤回
        batchBackTifByTaskId(taskId,layer);
        //删除
        dsLayerMapper.deleteLayerByTaskId(taskId);
        return null;
    }

    /**
     * 根据区域id, 数据集id, 作物id, 数据时间 查询图层
     * @param layer
     * @return ResultMessage
     * @version <1> 2019/9/2 14:46 zhangshen:Created.
     */
    @Override
    public ResultMessage findLayerByCondition(Layer layer) {
        List<Layer> list = dsLayerMapper.findLayerByCondition(layer);
        while (list.size()==0 && layer.getRegionId() != null){
            if(layer.getParentId()!=null){
                Long parentId =  regionMapper.getByRegionId(layer.getRegionId());
                if(parentId == null){
                    break;
                }
                layer.setRegionId(parentId);
                list = dsLayerMapper.findLayerByCondition(layer);
                if(list.size()>0){
                    break;
                }

            }
        }
        return ResultMessage.success(list);
    }

    /**
     * 根据taskId查询图层列表
     *
     * @param taskId
     * @return List<Layer>
     * @version <1> 2019/9/20 lzm:Created.
     */
    @Override
    public ResultMessage findLayerListByTaskId(Integer taskId) {
        List<Layer> list = dsLayerMapper.findLayerListByTaskId(taskId);
        return ResultMessage.success(list);
    }

    /**
     * 图层分页查询
     * @param layer 图层查询参数
     * @return 图层分页数据
     * @version <1> 2019-09-23 lzm： Created.
     */
    @Override
    public PageInfo<Layer> findLayerPageInfo(Layer layer) {
        PageHelper.startPage(layer.getPage(), layer.getRows());
        List<Layer> list = dsLayerMapper.findLayerPageInfo(layer);
        IdTransformUtils.idTransForList(list);

        return new PageInfo<Layer>(list);
    }

    /**
     * 图层明细发布或撤销
     * @param layerVO
     * @return ResultMessage
     * @version <1> 2019/9/23 lzm:Created.
     */
    @Override
    public ResultMessage publish(LayerVO layerVO) {
        int num = dsLayerMapper.publish(layerVO);
        if(num > 0){
            return ResultMessage.success();
        }else{
            return ResultMessage.success();
        }
    }

    /**
     * 根据图层编号删除图层
     * @param layerId 图层编号
     * @return 返回操作的记录数
     * @version<1> 2018-06-08 lxy: Created.
     */
    @Override
    public ResultMessage deleteDsLayerByLayerId(Integer layerId) {
        ResultMessage result = new ResultMessage();
        Layer dsLayer = dsLayerMapper.findDsLayerByLayerId(layerId);
        if (dsLayer == null){
            result.setCode(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getValue());
            result.setMsg(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getMesasge());
            return result;
        }

        //删除geoserver 中图层
        GeoServerUtil geoUtil = new GeoServerUtil();

        String layerName = dsLayer.getLayerName();

        geoUtil.removeStore(layerName.substring(layerName.indexOf(":")+1).replace("ly_","store_"));

        //删除图层表中的数据
        int num = dsLayerMapper.deleteDsLayerByLayerId(layerId);

        //更新再加工数据状态为待发布
//
//        DataReprocess dataReprocess = new DataReprocess();
//        dataReprocess.setPublishStatus(ReprocessPubStaEnum.DATA_REPROCESS_PUB_STA_WFB.getId());
//        dataReprocess.setModifierName(dsLayer.getModifierName());
//        dataReprocess.setModifier(dsLayer.getModifier());
//        List<Integer> rIds = new ArrayList<Integer>();
//        rIds.add(dsLayer.getReprocessId());
//        dataReprocessService.updateReprocessDataStatus(rIds,dataReprocess);


        if(num > 0){
            return ResultMessage.success();
        }else{
            return ResultMessage.fail();
        }
    }

    /**
     * 根据图层编号查询对应的图层
     * @param layerId
     * @return 返回对应的图层（DsLayer）
     * @version<1> 2019-09-24 lzm: Created.
     */
    @Override
    public ResultMessage findDsLayerByLayerId(Integer layerId) {
        Layer dsLayer = dsLayerMapper.findDsLayerByLayerId(layerId);
        if(dsLayer != null){
            return ResultMessage.success(dsLayer);
        }else{
            return ResultMessage.fail();
        }

    }
}
