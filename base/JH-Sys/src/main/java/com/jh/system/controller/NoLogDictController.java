package com.jh.system.controller;

import com.jh.system.entity.Dict;
import com.jh.system.model.DictParam;
import com.jh.system.model.DictTreeReturn;
import com.jh.system.service.IDictService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 字典操作接口
 * @version <1> 2018-01-17 cxj： Created.
 */
@Api(value = "字典操作接口")
@RestController
@RequestMapping("/nolog/dict")
public class NoLogDictController {
    @Autowired
    private IDictService dictService;


    /**
     *  查询数据字典所有数据
     * @version  <1> 2017-12-14 cxw : Created.:
     */
    @ApiIgnore
    @ApiOperation(value="查询数据字典所有数据",notes="查询数据字典所有数据" )
    @PostMapping(value="/queryDictList")
    public ResultMessage queryDictList(){
        return dictService.findDictList();
    }

    /**
     *  根据父ID查询子节点
     * @return DictTreeReturn
     * @version  <1> 2017-12-27 cxw : Created.:
     */
    @ApiOperation(value="根据父ID查询子节点",notes="根据父ID查询子节点" )
    @ApiImplicitParam(name = "dtr", value = "字典节点返回对象", required = true, dataType = "DictTreeReturn")
    @PostMapping(value="/queryDictListByPid")
    public ResultMessage queryDictListByPid(@RequestBody DictTreeReturn dtr){
        return  dictService.findDictListByPid(dtr);
    }

    /**
     *  根据id查询数据字典
     * @param dict 字典对象
     * @version  <1> 2017-12-14 cxw : Created.:
     */
    @ApiOperation(value="查询数据字典",notes="根据id查询数据字典详情" )
    @ApiImplicitParam(name = "dictId",value = "主键",required = true, dataType = "Integer")
    @PostMapping(value="/queryDictById")
    public ResultMessage  queryDictById(@RequestBody DictParam dict){
        return dictService.findDictById(dict);
    }


    /**
     *  根据编码查询出id，然后根据查询出的id匹配父ID查询出所有字典信息
     * @param dict 字典对象
     * @return dict  字典对象集合
     * @version  <1> 2018-03-06 cxw : Created.:
     */
    @ApiOperation(value="查询数据字典",notes="根据编码查询出id，然后根据查询出的id匹配父ID查询出所有字典信息" )
    @ApiImplicitParam(name = "dataCode",value = "主键",required = true, dataType = "String")
    @PostMapping(value="/queryDictByCode")
    public ResultMessage  queryDictByCode(@RequestBody DictParam dict){
        return dictService.findDictByCode(dict);
    }

    /**
     * @description: 查询子字典数据
     * @param dict 字典对象
     * @version <1> 2018-01-17 cxj： Created.
     */
    @ApiOperation(value="查询子字典数据",notes="通过父主键,查询子字典数据")
    @ApiImplicitParam(name = "dict",value = "父主键",required = true, dataType = "Dict")
    @PostMapping(value = "queryDictByParentId")
    public ResultMessage queryDictByParentId(@RequestBody Dict dict){
        return dictService.queryDictByParentId(dict);
    }

    /**
     * @description: 通过字典父主键集合，查询多个子字典数据
     * @param list 字典父主键集合
     * @version <1> 2018-01-18 cxj： Created.
     */
    @ApiOperation(value="查询多个子字典数据",notes="通过字典父主键集合，查询多个子字典数据")
    @ApiImplicitParam(name = "list",value = "父主键集合",required = true, dataType = "List<Integer>")
    @PostMapping(value = "queryDictByParentIdList")
    public ResultMessage queryDictByParentIdList(@RequestBody List<Integer> list){
        return dictService.queryDictByParentIdList(list);
    }

    /**
     * @description: 添加数据字典
     * @param dict
     * @version  <1> 2017-12-14 cxw : Created.:
     */
    @ApiOperation(value="添加数据字典",notes="添加数据字典" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest"),
            @ApiImplicitParam(name = "dictParam", value = "字典参数", required = true, dataType = "DictParam")
    })
    @PostMapping(value = "saveDict")
    public ResultMessage saveDict(@RequestBody DictParam dict, HttpServletRequest request){
        return dictService.addDict(dict,request);
    }

    /**
     * @description: 修改数据字典
     * @param dict
     * @version  <1> 2017-12-14 cxw : Created.:
     */
    @ApiOperation(value="修改数据字典",notes="修改数据字典" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest"),
            @ApiImplicitParam(name = "dictParam", value = "字典参数", required = true, dataType = "DictParam")
    })
    @PostMapping(value = "editDict")
    public ResultMessage editDict(@RequestBody DictParam dict, HttpServletRequest request){
        return dictService.updateDict(dict,request);
    }

    /**
     *  删除数据字典
     * @param dict
     * @version  <1> 2017-12-14 cxw : Created.:
     */
    @ApiIgnore
    @ApiOperation(value="删除数据字典",notes="根据ID删除数据字典" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dict", value = "字典", required = true, dataType = "DictParam"),
            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest")
    })
    @PostMapping(value = "delDict")
    public ResultMessage delDict(@RequestBody DictParam dict, HttpServletRequest request){
        return dictService.delDictById(dict,request);
    }

    /**
     * 查询下载配置中的所有数据分类
     *
     * @version <1> 2018-02-05 djh： Created.
     */
    @ApiOperation(value = "查询下载配置中的所有数据分类")
    @PostMapping(value = "queryDataType")
    public ResultMessage queryDataType() {
        return dictService.queryDataType();
    }

    /**
     * 查询指定父节点下的所有子字典数据
     *
     * @param dictParam 字典参数
     * @version <1> 2018/1/30 djh： Created.
     */
    @ApiOperation(value = "查询指定父节点下的所有子字典数据")
    @ApiImplicitParam(name = "dictParam", value = "字典参数", required = true, dataType = "DictParam")
    @PostMapping(value = "querySubDictListByParentId")
    public ResultMessage querySubDictListByParentId(@RequestBody DictParam dictParam) {
        if (null == dictParam || dictParam.getDictId() == null) {
            return ResultMessage.fail();
        }
        return dictService.querySubDictListByParentId(dictParam.getDictId());
    }

    /**
     *查询所有的下载状态
     * @return
     * @version <1> 2018-03-13 xzg： Created.
     */
//    @ApiOperation(value = "查询所有的下载状态")
//    @PostMapping(value = "queryDownloadStatus")
//    public ResultMessage queryDownloadStatus(){
//        Dict dict = new Dict();
//        dict.setParentId(DownloadStatusEnum.DOWNLOAD_STATUS.getValue());
//        return dictService.queryDictByParentId(dict);
//    }

    /**
     * @description: 校验数据字典Id是否已存在
     * 1.检索成功，则表示已存在，返回true
     * 2.检索为空，则表示不存在，返回false
     * @param dictParam
     * @return
     * @version <1> 2018-04-16 wl : created.
     */
    @ApiOperation(value="校验数据字典Id是否已存在", notes="校验数据字典Id是否已存在")
    @ApiImplicitParam(name = "dictparam",value = "资源编码",required = true, paramType = "query", dataType = "DictParam")
    @PostMapping("/checkDictCode")
    public ResultMessage checkDictCode(@RequestBody DictParam dictParam){
        return dictService.checkDictCode(dictParam);
    }

/**
     * 查询指定字典主键是否存在
     *
     * @param dictParam 字典参数
     * @version <1> 2018-02-11 djh： Created.
     *//*
    @ApiOperation(value = "查询指定字典主键是否存在")
    @ApiImplicitParam(name = "dictParam", value = "字典参数", required = true, dataType = "DictParam")
    @PostMapping(value = "checkDictIdIsExists")
    public ResultMessage checkDictIdIsExists(@RequestBody DictParam dictParam) {
        if (dictParam == null || dictParam.getDict() == null || dictParam.getDict().getDictId() == null) {
            return ResultMessage.fail("1", "系统故障");
        }
        return dictService.checkDictIdIsExists(dictParam.getDict().getDictId());
    }
/*
    *//**
     * 查询指定字典编码是否存在
     *
     * @param dictParam 字典参数
     * @version <1> 2018-02-11 djh： Created.
     *//*
    @ApiOperation(value = "查询指定字典编码是否存在")
    @ApiImplicitParam(name = "dictParam", value = "字典参数", required = true, dataType = "DictParam")
    @PostMapping(value = "checkDictCodeIsExists")
    public ResultMessage checkDictCodeIsExists(@RequestBody DictParam dictParam) {
        if (dictParam == null || dictParam.getDict() == null || dictParam.getDict().getDataCode() == null) {
            return ResultMessage.fail("1", "系统故障");
        }
        return dictService.checkDictCodeIsExists(dictParam.getDict().getDataCode());
    }*/

    /**
     * @description: 根据区域id查询农作物信息
     * @param regionId 区域id
     * @version <1> 2018-05-10 wl： Created.
     */
    @ApiOperation(value="查询子字典数据",notes="通过父主键,查询子字典数据")
    @ApiImplicitParam(name = "dict",value = "父主键",required = true, dataType = "Dict")
    @PostMapping(value = "findCropByRegionId")
    public ResultMessage findCropByRegionId( @RequestParam Integer regionId){
        return dictService.findCropByRegionId(regionId);
    }

    /**
     * 根据 字典名 查询
     * @param dictNameStr  字典中文名，多个逗号分隔
     * @return
     */
    @RequestMapping(value = "/queryDictByName",method = RequestMethod.GET)
    public ResultMessage queryDictByName(String dictNameStr){
        return dictService.findDictByName(dictNameStr);
    }
}
