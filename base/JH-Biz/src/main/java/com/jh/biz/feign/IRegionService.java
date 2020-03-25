package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @version <1> 2018-07-23  lcw : Created.
 */
@FeignClient(name="jh-sys")
public interface IRegionService {

	/**
	* 根据区域ID获取区域信息
	* @version Hayden [2018-08-03 11:47:09] : Created.
	*/
    @PostMapping(value = "/nolog/region/queryRegionById")
    ResultMessage findRegionById(@RequestParam(name="regionId") Long regionId);

    /**
    * 根据区域ID获取下一级所有区域列表
    *@version Hayden [2018-08-03 11:48:15] : Created.
    */
    @PostMapping(value = "/region/queryRegionListByParentId")
    ResultMessage queryRegionListByParentId(@RequestParam(name="parentId") Long parentId);

    /**
    * 根据区域id获取农作物列表
    *@version Hayden [2018-08-03 09:54:12] : Create.
    */
    @PostMapping(value="/nolog/crop/queryCropList")
    ResultMessage queryCropList(@RequestParam(name="regionId") Long regionId);

    /**
     * 根据父级id获取字典表中的子集列表
     *@version wangli 2018-08-08 : Create.
     */
    @PostMapping(value = "/dict/queryDictByParentId")
    ResultMessage queryDictByParentId(@RequestParam(name="parentId") Integer parentId);

    /**
     * 根据区域中文名 模糊查询
     * @param chinaName
     * @return
     */
    @GetMapping(value = "/nolog/region/queryRegionByChinaName")
    ResultMessage queryRegionByChinaName(@RequestParam(name="chinaName") String chinaName);

    /**
     * 根据区域id 或者bbox
     * @param map
     * @return
     */
    @PostMapping(value = "/nolog/region/findRegion")
    ResultMessage findRegion(@RequestBody  Map<String, Object> map);

    /**
     * 查询所有的省级直管县或者市
     *@version Mason [2018-11-08] : Created.
     */
    @PostMapping(value = "/region/queryMunicipalityArea")
    ResultMessage queryMunicipalityArea();

    /*
     * 功能描述:查询自身级下级所属区域id list
     * @Param:
     * @Return:
     * @version<1>  2019/12/5  wangli :Created
     */
    @PostMapping(value = "/region/findRegionIdListByRegionCode")
    ResultMessage findRegionIdListByRegionCode(@RequestParam(name="regionCode") String regionCode);

    /*
     * 功能描述:根据区域code查询区域信息
     * @Param:
     * @Return:
     * @version<1>  2019/12/6  wangli :Created
     */
    @PostMapping(value = "/region/findRegionByCode")
    ResultMessage findRegionByCode(@RequestParam(name="regionCode") String regionCode);
}
