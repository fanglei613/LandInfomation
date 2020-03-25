package com.jh.layer.mapping;

import com.jh.layer.entity.Layer;
import com.jh.layer.model.LayerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDsLayerMapper {

    /**
     * 根据layerId查询图层信息
     * @param layerId
     * @return Layer
     * @version <1> 2019/8/31 12:41 zhangshen:Created.
     */
    Layer findLayerListByLayerId(@Param("layerId")Integer layerId);

    /**
     * 根据图层编号返回对应的图层
     * @param layerId 图层编号
     * @return 返回图层
     * @version <1> 2019-09-23 lzm: Created.
     */
    Layer findDsLayerByLayerId(Integer layerId);

    /**
     * 根据图层编号删除图层
     * @param layerId 图层编号
     * @return 返回删除记录数
     * @version <1> 2019-09-23 lzm: Created.
     */
    int deleteDsLayerByLayerId(@Param("layerId") Integer layerId);

    /**
     * 根据taskId查询图层列表
     * @param taskId
     * @return List<Layer>
     * @version <1> 2019/8/31 12:41 zhangshen:Created.
     */
    List<Layer> findLayerListByTaskId(@Param("taskId") Integer taskId);

    /**
     * 根据任务id批量删除图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/30 18:04 zhangshen:Created.
     */
    void deleteLayerByTaskId(@Param("taskId") Integer taskId);

    /**
     * 修改layer信息
     * @param layer
     * @return void
     * @version <1> 2019/8/31 14:52 zhangshen:Created.
     */
    void updateDsLayer(Layer layer);

    /**
     * 根据区域id, 数据集id, 作物id, 数据时间 查询图层
     * @param layer
     * @return ResultMessage
     * @version <1> 2019/9/2 14:46 zhangshen:Created.
     */
    List<Layer> findLayerByCondition(Layer layer);

    /**
     * 图层分页查询
     * @param layer 图层查询参数
     * @return 图层分页数据
     * @version <1> 2019-09-23 lzm： Created.
     */
    List<Layer> findLayerPageInfo(Layer layer);

    /**
     * 发布和撤回图层
     * @param layerVO 发布人名称  发布人id  发布状态（发布、撤销）
     * @return 修改操作记录数据
     * @version <1> 2019-09-23 lzm： Created.
     */
    int publish(LayerVO layerVO);
}
