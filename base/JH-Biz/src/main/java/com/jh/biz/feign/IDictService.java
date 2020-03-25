package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * description:
 *查询字典相关接口
 * @version <1> 2018-08-17 huxiaoqiang: Created.
 */
@FeignClient(name="jh-sys")
 public interface IDictService {
    /**
     *  查询数据字典所有数据
     * @version  <1> 2017-12-14 cxw : Created.:
     */
    @PostMapping(value="/nolog/dict/queryDictList")
     ResultMessage queryDictList();

    /**
     *  根据父ID查询子节点
     * @return DictTreeReturn
     * @version  <1> 2017-12-27 cxw : Created.:
     */
    @PostMapping(value="/nolog/dict/queryDictListByPid")
     ResultMessage queryDictListByPid(@RequestParam(name="parentId") Integer parentId);


    /**
     * 根据id查询数据字典
     * @param dictId
     * @return
     */
    @PostMapping(value="/nolog/dict/queryDictById")
     ResultMessage  queryDictById(@RequestParam(name="dictId") Integer dictId);


    /**
     * 根据编码查询出id，然后根据查询出的id匹配父ID查询出所有字典信息
     * @param dataCode
     * @return
     */
    @PostMapping(value="/nolog/dict/queryDictByCode")
     ResultMessage  queryDictByCode(@RequestParam(name="dataCode") Integer dataCode);

    /**
     * 查询子字典数据
     * @param parentId
     * @return
     */
    @PostMapping(value = "/nolog/dict/queryDictByParentId")
     ResultMessage queryDictByParentId(@RequestParam(name="parentId") Integer parentId);

    /**
     * 查询子字典数据
     * @param parentId
     * @return
     */
    @PostMapping(value = "/dict/queryDictChildrenByParentId")
    ResultMessage queryDictChildrenByParentId(@RequestParam(name="parentId") Integer parentId);

    /**
     * @description: 通过字典父主键集合，查询多个子字典数据
     * @param list 字典父主键集合
     * @version <1> 2018-01-18 cxj： Created.
     */
    @PostMapping(value = "/nolog/dict/queryDictByParentIdList")
     ResultMessage queryDictByParentIdList(@RequestBody List<Integer> list);

    /**
     * 查询下载配置中的所有数据分类
     *
     * @version <1> 2018-02-05 djh： Created.
     */
    @PostMapping(value = "/nolog/dict/queryDataType")
     ResultMessage queryDataType() ;

    /**
     * 查询指定父节点下的所有子字典数据
     * @param parentId
     * @return
     */
    @PostMapping(value = "/nolog/dict/querySubDictListByParentId")
     ResultMessage querySubDictListByParentId(@RequestParam(name="parentId") Integer parentId);

    /**
     * @description: 根据区域id查询农作物信息
     * @param regionId 区域id
     * @version <1> 2018-05-10 wl： Created.
     */
    @PostMapping(value = "/nolog/dict/findCropByRegionId")
     ResultMessage findCropByRegionId( @RequestParam(name="regionId") Integer regionId);

    /**
     * 根据 字典名 查询
     * @param dictNameStr  字典中文名，多个逗号分隔
     * @return
     */
    @GetMapping(value = "/nolog/dict/queryDictByName")
     ResultMessage queryDictByName(@RequestParam(name="dictNameStr") String dictNameStr);




}
