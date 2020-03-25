package com.jh.land.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.land.entity.DsRankTask;
import com.jh.land.model.RankAreaTaskVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface IDsRankTaskMapper extends IBaseMapper<RankAreaTaskVO, DsRankTask, Integer> {

    /*
     * 功能描述:创建地块入库任务
     * @Param:
     * @Return:
     * @version<1>  2019/11/28  wangli :Created
     */
    void createRankAreaTask(RankAreaTaskVO rankAreaTaskVO);

    /*
     * 功能描述:查询地块数据入库分页(带分页)
     * @Param:
     * @Return:
     * @version<1>  2019/11/28  wangli :Created
     */
    List<RankAreaTaskVO> findRankAreaTaskList(RankAreaTaskVO rankAreaTaskVO);

    /*
     * 功能描述: 激活数据处理任务
     * @Param:
     * @Return:
     * @version<1>  2019/11/29  wangli :Created
     */
    int activationTask(Integer taskId);

    /*
     * 功能描述: 判断是否有相同类型 相同区域 相同作物  相同 数据时间的任务
     * @Param:
     * @Return:
     * @version<1>  2019/12/4  wangli :Created
     */
    List<Integer> checkTask(RankAreaTaskVO rankAreaTaskVO);

    /*
     * 功能描述:删除重复性任务
     * @Param:
     * @Return:
     * @version<1>  2019/12/5  wangli :Created
     */
    int deleteRankTask(RankAreaTaskVO rankAreaTaskVO);

    /*
     * 功能描述:检查表单是否存在
     * @Param:
     * @Return:
     * @version<1>  2019/12/6  wangli :Created
     */
    int checkTable(Map<String, Object> map);

    /*
     * 功能描述: 创建任务所需的对应的地块表
     * @Param:
     * @Return:
     * @version<1>  2019/12/6  wangli :Created
     */
    int createInitRankTable(Map<String, Object> map);

    /*
     * 功能描述:创建任务所需的对应的地块分布表
     * @Param:
     * @Return:
     * @version<1>  2019/12/11  wangli :Created
     */
    int createRankAreaTable(Map<String, Object> map);

    /**
     * 获取点击的地块信息
     */
    LinkedHashMap<String, Object> findClickBlock(RankAreaTaskVO rankAreaTaskVO);

    /**
     * 获取点击的地块信息(无关联空间分布属性)
     */
    LinkedHashMap<String, Object> findClickBlockWithNoProperties(RankAreaTaskVO rankAreaTaskVO);

    /**
     * 判断地块表关联的空间分布属性表是否存在
     */
    String isBlockPropertiesTable(RankAreaTaskVO rankAreaTaskVO);

    /**
     * 根据行政区划编码获取信息
     */
    LinkedHashMap<String, Object> findRegionByCode(@Param(value = "regionCode") String regionCode);
}
