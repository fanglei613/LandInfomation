package com.jh.land.service;

import com.jh.base.service.IBaseService;
import com.jh.land.entity.InitRegion;
import com.jh.land.model.RegionParam;
import com.jh.vo.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @description: 区域业务逻辑接口
 * @version <1> 2018-01-18 cxj： Created.
 * @version <2> 2018/1/26 djh： update.
 *      新增接口
 */
public interface ISysRegionService extends IBaseService<RegionParam, InitRegion, Long> {
    /**
     *  @description: 根据上一级ID获取区域列表
     *  @param parentId: 上一级区域ID
     *  @version <1> 2018-01-18 cxj:Created.
     */
    ResultMessage findRegionListByParentId(Long parentId);

    /**
     *  @description: 根据上一级ID获取存在地块的区域列表
     *  @param parentId: 上一级区域ID
     */
    ResultMessage findBlockRegionListByParentId(Long parentId, Integer level);

    /**
     *  @description: 根据父类区域ID查询其省级区域列表
     *  @param parentId: 上一级区域ID
     *  @version <1> 2018-04-17 cxw:Created.
     */
    ResultMessage findRegionListByParentIdForReport(Long parentId);

    /**
     * @description: 查询区域数据
     * @param initRegion 区域对象
     * @return
     * @version <1> 2018/1/26 djh： Created.
     */
    ResultMessage queryInitRegionByInitRegionId(Long initRegion);

    /**
     * 查询所有区域数据
     *
     * @version <1> 2018/1/31 djh： Created.
     */
    ResultMessage queryFirstLevelInitRegionList();

    /**
     * 查询指定父节点下的所有子区域数据
     *
     * @param regionParentId 父区域的主键id
     * @return
     * @version <1> 2018/1/31 djh： Created.
     * @version<2>  2018-02-06 lcw : updated.
     *  修改该方法的返回类型为ResultMessage
     */
    ResultMessage querySubRegionListByParentId(Long regionParentId);

    /**
     * 查询指定区域编码是否存在
     *
     * @param regionCode 区域编码
     * @version <1> 2018-02-10 djh： Created.
     */
    ResultMessage checkRegionCodeIsExists(String regionCode);

    /**
     * 查询所有有效区域数据
     *
     * @version <1> 2018-05-10 wl： Created.
     */
    ResultMessage findAll();

    /**
     * 查询所有有效区域数据
     *
     * @version <1> 2018-05-10 wl： Created.
     */
    ResultMessage findAllParentId();


    /**
     * 根据区域中文名 模糊 查询
     * @param chinaName
     * @return
     */
    ResultMessage findRegionByChinaName(String chinaName);

    /**
     * 根据上一级ID获取区域列表
     * @param parentId: 上一级区域ID
     * @return ResultMessage :
     * @version <1> 2017-11-07 Hayden:Created.
     */
    List<InitRegion> findRegionsByParentId(Long parentId);

    /**
     * 根据账号获取区域权限信息
     * 1. 获取账号购买的数据服务，从而得到区域信息
     * 2. 过滤重复区域
     * 3. 获取每个区域的上一级区域，直到区域没有上一级为止。
     * 4. 将账号的权限区域及相关的所有上一级区域进行返回
     * @param accountId: 账号ID
     * @return ResultMessage : 区域列表，如果返回true,包含List<RegionParam>
     * @version <1> 2017-12-20 Hayden:Created.
     */
    ResultMessage findRegionListByAccount(Integer accountId, Long parentId);

    /**
     * 根据账号获取区域权限信息
     * @param accountToken
     * @param parentId
     * @return
     * @version <1> 2018-05-17 xzg:Created.
     */
    ResultMessage findRegionByToken(String accountToken, Long parentId);

    /**
     * 根据账号获取区域权限信息(过滤权限)
     * @param accountToken
     * @param parentId
     * @return
     * @version <1> 2018-05-17 xzg:Created.
     */
    ResultMessage findAllRegionByToken(String accountToken, Long parentId);

    /**
     * 根据父级ID查询子级城市或者区域
     * @param  map
     * @return List<Map<String,Object>>
     * @version <1> 2018-06-24 wl:Created.
     */
    List<Map<String,Object>> findRegionByParentId(Map<String, Object> map);


    /*
     * 功能描述:查询自己以及下级region_id
     * @Param:
     * @Return:
     * @version<1>  2019/12/5  wangli :Created
     */
    ResultMessage findRegionIdListByRegionCode(String regionCode);

    /**
     * region_code编码：
     * 规则：1.默认首汉字拼音前三位
     *      2.若重复或少于3位，则随机生成一个
     */
    ResultMessage encodeRegionCode(Integer level);

    /**
     * regin_name编码
     * 规则：1、首汉字拼音+第二个汉字拼音，首字母大写
     */
    ResultMessage encodeRegionName(Integer level);


    public ResultMessage findRegion(Map<String, Object> map);

    /**
     * 根据regionId查询该区域的家族关系信息
     * @param regionId
     * @return
     * @version<1> 2018-10-14 lcw :created.
     */
    ResultMessage findRegionFamily(Long regionId);

    /**
     * 查询所有的省级直管县或者市
     * @return
     */
    ResultMessage findMunicipalityArea();

    /**
     * 根据区域id查询此区域下所有子孙元素,app用
     * @param
     * @return
     * @version <1> 2019/4/13 15:46 zhangshen:Created.
     */
    ResultMessage queryRegionAppReturn(Long regionId);

    /*
     * 功能描述: 根据regioncode 查询 区域信息
     * @Param:
     * @Return:
     * @version<1>  2019/12/6  wangli :Created
     */
    ResultMessage findRegionByCode(String regionCode);

}
