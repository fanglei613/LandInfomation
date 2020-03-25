package com.jh.land.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.land.entity.Yield;
import com.jh.land.entity.YieldCollect;
import com.jh.land.model.WarehousingTaskVO;
import com.jh.land.model.YieldVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * 估产相关数据接口
 * 继承自IBaseMapper，已包含增删改查、列表查询数据接口
 * @version <1> 2019/8/19 mason:Created.
 */
@Mapper
@Repository
public interface IYieldMapper extends IBaseMapper<YieldVO, Yield,Integer> {

    /**
     * 根据条件查询估产数据时间点
     * @param yieldVO
     * @return  List<String> 估产时间点集合
     * @version <1> 2019/8/19 mason:Created.
     */
    List<String> findYieldTimes(YieldVO yieldVO);

    /**
     * 根据主键id查询单条估产数据
     * @param id
     * @return Yield
     * @version <1> 2019-08-21 lzm:Created.
     */
    Yield findById(Integer id);

    /**
     * 修改发布状态
     * @param yieldVO
     * @version <1> 2019-08-27 lzm:Created.
     */
    void publishStatus(YieldVO yieldVO);

    /**
     * 根据作物及时间节点查询返回估产汇总数据
     * @param cropId
     * @param regionId
     * @return
     */
    List<YieldCollect> findYieldByCropAndTimes(@Param("cropId") Integer cropId, @Param("regionId") BigInteger regionId);

    /**
     * 根据作物查询返回最近3年估产汇总数据
     * @param cropId
     * @param regionId
     * @return
     */
    List<YieldCollect> findYieldByCropWith3Years(@Param("cropId") Integer cropId, @Param("regionId") BigInteger regionId);

    /**
     * 根据作物查询返回最近2年估产汇总数据
     * @param cropId
     * @param regionId
     * @return
     */
    List<YieldCollect> findYieldByCropWith2Years(@Param("cropId") Integer cropId, @Param("regionId") BigInteger regionId);

    /**
     * 根据ids批量删除数据
     * @param ids
     * @return ResultMessage
     * @version <1> 2019/8/30 10:57 zhangshen:Created.
     */
    void batchDeleteByIds(@Param("ids") List<Integer> ids);
    /**
     * 根据主键id删除估产数据
     * @param id
     * @version <1> 2019-08-30 lzm:Created.
     */
    void delete(Integer id);

    /**
     * 根据ids批量发布数据
     * @param ids
     * @return ResultMessage
     * @version <1> 2019/8/30 17:24 zhangshen:Created.
     */
    void batchPublishByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据ids批量撤回数据
     * @param ids
     * @return ResultMessage
     * @version <1> 2019/8/30 17:34 zhangshen:Created.
     */
    void batchRecallByIds(@Param("ids") List<Integer> ids);

    /**
     * 查询选中区域及其下级区域某一期的估产数据
     * @param yieldVO
     * @return
     * @version <1> 2019/9/16 mason:Created.
     */
    List<YieldVO> findRegionAndBeyondYield(YieldVO yieldVO);

    /**
     * 根据任务ID查询估产的数据集
     * @param warehousingTaskVO
     * @return List<YieldVO>
     * @version <1> 2019-09-19 lzm:Created.
     */
    List<YieldVO> findYieldDataSetListByTaskId(WarehousingTaskVO warehousingTaskVO);

    /**
     * 估产数据分页查询
     * @param yieldVO
     * @return List<YieldVO>
     * @version<1> 2019-08-19 lzm: Created.
     */
    List<YieldVO> findWithPage(YieldVO yieldVO);

}