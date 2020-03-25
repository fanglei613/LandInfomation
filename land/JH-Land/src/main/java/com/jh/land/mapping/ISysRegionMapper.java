package com.jh.land.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.land.entity.InitRegion;
import com.jh.land.model.RegionParam;
import com.jh.land.model.RegionTreeReturn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description: 区域数据访问接口
 * @version <1> 2018-01-18 cxj： Created.
 * @version <2> 2018/1/26 djh： update.
 *      新增接口
 */
@Mapper
@Repository
public interface ISysRegionMapper extends IBaseMapper<RegionParam, InitRegion, Long> {
    /**
     *  @description: 根据上一级ID获取区域列表
     *  @param parentId: 上一级区域ID
     *  @version <1> 2018-01-18 cxj:Created.
     */
    List<RegionParam> findRegionListByParentId(@Param(value = "parentId") Long parentId);

    /**
     *  @description: 根据上一级ID获取存在地块的区域列表
     *  @param parentId: 上一级区域ID
     */
    List<RegionParam> findBlockRegionListByParentId(@Param(value = "parentId") Long parentId,
                                                    @Param(value = "level") Integer level);

    /*
     * 功能描述:查询自己以及自己下级的region_id
     * @Param:
     * @Return:
     * @version<1>  2019/12/5  wangli :Created
     */
    List<Long> findRegionIdListByRegionCode(@Param(value = "regionCode") String regionCode);

    /**
     *  @description: 根据父类区域ID查询其省级区域列表
     *  @param parentId: 上一级区域ID
     *  @version <1> 2018-04-17 cxw:Created.
     */
    List<InitRegion> findRegionListByParentIdForReport(@Param(value = "parentId") Long parentId);

    /**
     * @description: 更新区域数据
     * @param initRegion 区域对象
     * @return
     * @version <1> 2018/1/26 djh： Created.
     */
    public void updateByPrimaryKeySelective(InitRegion initRegion);

    /**
     * 查询所有顶级区域数据
     *
     * @return
     * @version <1> 2018/1/31 djh： Created.
     */
    List<RegionTreeReturn> findFirstLevelInitRegionList();

    /**
     * 根据父主键id查询其下的所有子数据对象列表
     *
     * @param regionParentId 区域父主键id
     * @return
     * @version <1> 2018/1/31 djh： Created.
     */
    List<RegionTreeReturn> querySubInitRegionListByParentId(Long regionParentId);

    /**
     * 查询指定区域编码是否存在
     *
     * @param regionCode 区域编码
     * @version <1> 2018-02-10 djh： Created.
     */
    Integer checkRegionCodeIsExists(String regionCode);

    /**
     * 查询指定父类id下最大的子记录的主键id值
     *
     * @param parentId
     * @version <1> 2018-02-10 djh： Created.
     */
    Long findMaxSubInitRegionIdByParentId(Long parentId);

    /**
     *  @description: 查询所有有效的区域
     *  @version <1> 2018-05-10 wl:Created.
     */
    List<InitRegion> findAll();

    /**
     *  @description: 查询所有有效的父级区域
     *  @version <1> 2018-05-10 wl:Created.
     */
    List<Long> findAllParentId();

    /**
     * 根据parent_id获取相应数据服务的所有区域
     * 1. parent_id为空,则获取level=1的国家级区域列表
     * 2. parent_id非空,则获取下一级区域列表
     *
     * @param parentId: 账号ID
     * @return List<RegionEntity> : 区域列表
     * @version <1> 2017-12-20 Hayden:Created.
     **/
    List<InitRegion> findRegionsByParentId(@Param("parentId") Long parentId);

    /**
     * 根据账号获取相应数据服务的所有区域
     * 1.
     * @param accountId: 账号ID
     * @return List<RegionEntity> : 区域列表
     * @version <1> 2017-12-20 Hayden:Created.
     */
    List<InitRegion> findRegionListByAccount(@Param("accountId") Integer accountId);

    /**
     * 根据regionCode 查询区域
     * @param regionCode : 区域代码
     * @version <1> 2017-12-22 Hayden:Created.
     */
    InitRegion findRegionByCode(@Param("regionCode") String regionCode);

    /**
     * 根据regionId 查询区域
     * @param regionId : 区域id
     * @version <1> 2017-12-22 Hayden:Created.
     */
    InitRegion findRegionById(@Param("regionId") Long regionId);

    /**
     * 根据中文名查新区域
     * @param chinaName
     * @return
     */
    List<InitRegion> findRegionByChinaName(String chinaName);

    /**
     * 根据code查询所有子级
     * @param regionCode
     * @return
     */
    List<Long> findRegionsByCode(String regionCode);

    /**
     * 查询区域（parentId不为空查询子级区域  bbox不为空时查询交集区域 ）
     * @param  map
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    List<Map<String,Object>> findRegion(Map<String, Object> map);


    /**
     *根据等级查询region_code为空的list,刷新region_code使用
     * @return
     */
    List<InitRegion> findNullRegionCodeList(Integer level);

    /**
     * @description: 更新区域数据regioncode,regionname
     * @param initRegion 区域对象
     * @return
     */
    public void updateCodeById(InitRegion initRegion);

    /*
     *查询regionWorld信息
     */
    public InitRegion getByRegionId(Long regionId);

    /**
     * 查询regionWorld编码是否存在
     *
     * @param regionCode 区域编码
     */
    Integer checkCodeIsExists(String regionCode);

    /**
     * 根据所有的省级直管县或者市
     * @return
     */
    List<Long> findMunicipalityArea();


}
