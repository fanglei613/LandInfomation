package com.jh.layer.service;


import com.github.pagehelper.PageInfo;
import com.jh.layer.entity.Layer;
import com.jh.layer.model.LayerVO;
import com.jh.vo.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Description:
 * 1.图层数据信息 接口
 *
 * @version <1> 2018-05-16 14:53 zhangshen: Created.
 */
public interface IDsLayerService{

    /**
     *  @description: 叠加栅格影像
     *  1. 直接加载给定URL的图层信息
     *  2. 加载对URL过滤后的图层信息,如filter= "code like 'USA%' and level='2'"
     *  @return  png  返回地图底图
     *  @version<1> 2018-01-16 cxj: Created.
     */
    void findGeoPng(HttpServletRequest request, HttpServletResponse response, String path);

    /**
     * @description: 查询WMS图层数据
     * @return  json : 返回图层数据
     * @version<1> 2018-01-16 cxj: Created.
     */
    Object findGeoJson(String path);

    /**
     * 根据图层ids批量发布图层
     * @param ids
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    ResultMessage batchPublishTifByIds(String ids, Layer layer);
    ResultMessage batchPublishTifByIds(List<Integer> ids, Layer layer);

    /**
     * 根据图层ids批量撤回图层
     * @param ids
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    ResultMessage batchBackTifByIds(String ids, Layer layer);
    ResultMessage batchBackTifByIds(List<Integer> ids, Layer layer);

    /**
     * 根据任务id批量发布图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    ResultMessage batchPublishTifByTaskId(Integer taskId, Layer layer);

    /**
     * 根据任务id批量撤回图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    ResultMessage batchBackTifByTaskId(Integer taskId, Layer layer);

    /**
     * 根据任务id批量删除图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    ResultMessage deleteLayerByTaskId(Integer taskId, Layer layer);

    /**
     * 根据区域id, 数据集id, 作物id, 数据时间 查询图层
     * @param layer
     * @return ResultMessage
     * @version <1> 2019/9/2 14:46 zhangshen:Created.
     */
    ResultMessage findLayerByCondition(Layer layer);

    /**
     * 根据taskId查询图层列表
     * @param taskId
     * @return List<Layer>
     * @version <1> 2019/9/20 lzm:Created.
     */
    ResultMessage findLayerListByTaskId(Integer taskId);

    /**
     * 图层分页查询
     * @param layer 图层查询参数
     * @return 图层分页数据
     * @version <1> 2019-09-23 lzm： Created.
     */
    PageInfo<Layer> findLayerPageInfo(Layer layer);

    /**
     * 图层明细发布或撤销
     * @param layerVO
     * @return ResultMessage
     * @version <1> 2019/9/23 lzm:Created.
     */
    ResultMessage publish(LayerVO layerVO);

    /**
     * 图层删除
     * 1、从geoserver 中删除图层
     * 2、删除图层表记录
     * 3、修改再加工数据表 数据状态为未发布
     * @param layerId 图层编号
     * @return 返回操作的记录数
     * @version<1> 2019-09-23 lzm: Created.
     */
    ResultMessage deleteDsLayerByLayerId(Integer layerId);

    /**
     * 根据图层编号查询对应的图层
     * @param layerId
     * @return 返回对应的图层（DsLayer）
     * @version<1> 2019-09-24 lzm: Created.
     */
    ResultMessage findDsLayerByLayerId(Integer layerId);


}
