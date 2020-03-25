package com.jh.land.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.land.entity.Area;
import com.jh.land.model.AreaVO;
import com.jh.land.model.RankAreaTaskVO;
import com.jh.land.model.WarehousingTaskVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分布相关数据接口
 * 继承自IBaseMapper，已包含增删改查、列表查询数据接口
 * @version <1> 2019/8/19 mason:Created.
 */
@Mapper
@Repository
public interface IAreaMapper extends IBaseMapper<AreaVO, Area,Integer> {

    /**
     * 根据条件查询分布数据的时间点集合
     * @param areaVO
     * @return List<String> 时间点集合
     * @version <1> 2019/8/19 mason:Created.
     */
    List<String> findAreaTimes(AreaVO areaVO);

    /*
     * 功能描述:根据条件查询地块分布数据的时间点集合
     * @Param:
     * @Return:
     * @version<1>  2019/11/7  wangli :Created
     */
    List<String> findRankAreaTimes(AreaVO areaVO);

    /**
     * 分布数据分页查询
     * @param areaVO
     * @return List<AreaVO>
     * @version<1> 2019-08-19 lzm: Created.
     */
    List<AreaVO> findWithPage(AreaVO areaVO);

    /**
     * 修改发布状态
     * @param areaVO
     * @version <1> 2019-08-27 lzm:Created.
     */
    void publishStatus(AreaVO areaVO);

    /**
     * 根据主键id查询单条分布数据
     * @param id
     * @return Area
     * @version <1> 2019-08-21 lzm:Created.
     */
    Area findById(Integer id);

    /**
     * 查询选中区域及其下级区域某一期的分布数据
     * @param areaVO
     * @return List<Area> 分布实体集合
     * @version <1> 2019/8/20 mason:Created.
     */
    List<AreaVO> findRegionAndBeyondArea(AreaVO areaVO);

    /*
     * 功能描述: 查询选中区域及其下级区域某一期的分布数据
     * @Param:
     * @Return:
     * @version<1>  2019/11/7  wangli :Created
     */
    List<AreaVO> findRankAndBeyondArea(AreaVO areaVO);

    /**
     * 查询下级区域某一期的分布数据
     * @param areaVO
     * @return List<Area> 分布实体集合
     * @version <1> 2019/8/20 mason:Created.
     */
    List<AreaVO> selectBeyondArea(AreaVO areaVO);


    /**
     * 根据数据时间查询选中区域及其下级区域某一期的分布数据
     * @param areaVO
     * @return List<AreaVO> 分布实体集合
     * @version <1> 2019-08-29 lzm:Created.
     */
    List<AreaVO> findRegionAndAreaByDataTime(AreaVO areaVO);

    /**
     * 查询选中区域及其下级区域最新时间的分布数据
     * @param areaVO
     * @return List<AreaVO>
     * @version <1> 2019-08-29 lzm:Created.
     */
    List<AreaVO> findRegionAndLatestTimeArea(AreaVO areaVO);

    /**
     * 根据作物类别和区域查询最近三年的时间
     * @param areaVO
     * @return List<String> 时间点集合
     * @version <1> 2019-08-29 lzm:Created.
     */
    List<String> queryLatestThreeYearTime(AreaVO areaVO);

    /**
     * 根据主键id删除分布数据
     * @param id
     * @version <1> 2019-08-30 lzm:Created.
     */
    void delete(Integer id);

    /**
     * 根据ids批量删除数据
     * @param ids
     * @return ResultMessage
     * @version <1> 2019/8/30 10:57 zhangshen:Created.
     */
    void batchDeleteByIds(@Param("ids") List<Integer> ids);

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
     * 查询选中区域及其下级区域两年的分布数据
     * @param areaVO
     * @return List<Area> 分布实体集合
     * @version <1> 2019/8/20 mason:Created.
     */
    List<AreaVO> findRegionAndAreaWithTwoYear(AreaVO areaVO);

    /**
     * 查询选中区域及其下级区域三年的分布数据
     * @param areaVO
     * @return List<Area> 分布实体集合
     * @version <1> 2019/8/20 mason:Created.
     */
    List<AreaVO> findRegionAndAreaWithThreeYear(AreaVO areaVO);


    /*
     * 功能描述: 查询选中区域及其下级区域地块三年的分布数据
     * @Param:
     * @Return:
     * @version<1>  2019/11/8  wangli :Created
     */
    List<AreaVO> findRankAndAreaWithThreeYear(AreaVO areaVO);

    /**
     * 根据任务ID查询分布的数据集
     * @param warehousingTaskVO
     * @return List<AreaVO>
     * @version <1> 2019-09-18 lzm:Created.
     */
    List<AreaVO> findAreaDataSetListByTaskId(WarehousingTaskVO warehousingTaskVO);

    /**
     * 查询分布导出数据
     * @param
     * @return
     * @version <1> 2019/9/27 mason:Created.
     */
    List<AreaVO> findExportData(AreaVO areaVO);

    /*
     * 功能描述: 删除对应表中的信息
     * @Param:
     * @Return:
     * @version<1>  2019/12/5  wangli :Created
     */
    int deleteRankArea(RankAreaTaskVO rankAreaTaskVO);
}