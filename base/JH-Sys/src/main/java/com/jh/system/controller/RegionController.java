package com.jh.system.controller;

import com.jh.constant.CommonDefineEnum;
import com.jh.system.entity.InitRegion;
import com.jh.util.AccountTokenUtil;
import com.jh.vo.ResultMessage;
import com.jh.system.model.DictParam;
import com.jh.system.model.RegionParam;
import com.jh.system.model.RegionTreeReturn;
import com.jh.system.service.IRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *  @description: 用于构造区域信息查询服务
 *  @version <1> 2018-01-18 cxj:Created.
 */
@Api(value = "区域操作接口")
@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private IRegionService regionService;

    /**
     *  @description: 根据父类区域ID查询其下一级区域列表
     *  @version <1> 2018-01-18 cxj:Created.
     */
    @ApiOperation(value="Find Region List for Parent Region",notes="根据父类区域ID查询其下一级区域列表")
    @ApiImplicitParam(name = "parentId",value = "父级区域ID",required = false,paramType="form",dataType = "Long")
    @RequestMapping(value="/regionList",method= RequestMethod.POST)
    public ResultMessage queryRegionList(@RequestBody(required=false)DictParam dictParam){


        return regionService.findRegionListByParentId(dictParam.getParentId());
    }

    /**
     *  @description: 根据父类区域ID查询其下一级包含地块的区域列表
     */
    @ApiOperation(value="Find Region List for Parent Region By Block",notes="根据父类区域ID查询其下一级包含地块的区域列表")
    @ApiImplicitParam(name = "parentId",value = "父级区域ID",required = false,paramType="form",dataType = "Long")
    @RequestMapping(value="/blockRegionList",method= RequestMethod.POST)
    public ResultMessage queryBlockRegionList(@RequestBody(required=false)DictParam dictParam){
        return regionService.findBlockRegionListByParentId(dictParam.getParentId(),dictParam.getLevel());
    }

    /**
     *  @description: 根据父类区域ID查询其省级区域列表
     *  @version <1> 2018-04-17 cxw:Created.
     */
    @ApiOperation(value="Find Region List for Parent Region",notes="根据父类区域ID查询其下一级区域列表")
    @ApiImplicitParam(name = "parentId",value = "父级区域ID",required = false,paramType="form",dataType = "Long")
    @RequestMapping(value="/regionListForReport",method= RequestMethod.POST)
    public ResultMessage queryRegionListForReport(@RequestBody(required=false)DictParam dictParam){


        return regionService.findRegionListByParentIdForReport(dictParam.getParentId());
    }


    /**
     * @description: 根据区域主键值，查询对应的区域信息
     * @param regionId 区域参数
     * @version <1> 2018/1/26 djh： Created.
     */
    @ApiOperation("查询区域数据")
    @ApiImplicitParam(name = "regionId", value = "区域参数", required = true, dataType = "Long")
    @PostMapping(value = "queryRegionById")
    public ResultMessage queryInitRegionByInitRegionId(@RequestParam Long regionId) {
        return regionService.queryInitRegionByInitRegionId(regionId);
    }

    /**
     * @description:根据区域主键值，更新对应的区域信息
     * @param request   request请求对象
     * @param regionParam 区域参数
     * @version <1> 2018/1/26 djh： Created.
     */
    @ApiOperation("更新区域数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest"),
            @ApiImplicitParam(name = "regionParam", value = "区域参数", required = true, dataType = "RegionParam")
    })
    @PostMapping(value = "updateInitRegionByInitRegionId")
    public ResultMessage updateInitRegionByInitRegionId(HttpServletRequest request, @RequestBody RegionParam regionParam) {
        if (regionParam == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }
        return regionService.updateInitRegionByInitRegionId(request, regionParam.getInitRegion());
    }

    /**
     * @description: 保存区域对象
     * @param request   request请求对象
     * @param regionParam 区域参数
     * @version <1> 2018/1/26 djh： Created.
     */
    @ApiOperation("保存区域数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest"),
            @ApiImplicitParam(name = "regionParam", value = "区域参数", required = true, dataType = "RegionParam")
    })
    @PostMapping(value = "saveInitRegion")
    public ResultMessage saveInitRegion(HttpServletRequest request, @RequestBody RegionParam regionParam) {
        if (regionParam == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }
        return regionService.saveInitRegion(request, regionParam.getInitRegion());
    }

    /**
     * @description: 根据区域主键值，删除对应的区域信息
     * @param regionParam 区域参数
     * @version <1> 2018/1/26 djh： Created.
     */
    @ApiOperation("删除区域数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "request请求对象", required = true, dataType = "HttpServletRequest"),
            @ApiImplicitParam(name = "regionParam", value = "区域参数", required = true, dataType = "RegionParam")
    })
    @PostMapping(value = "deleteInitRegionByInitRegionId")
    public ResultMessage deleteInitRegionByInitRegionId(HttpServletRequest request, @RequestBody RegionParam regionParam) {
        if (regionParam == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }
        return regionService.deleteInitRegionByInitRegionId(request, regionParam.getInitRegion());
    }

    /**
     * 查询所有顶级区域数据
     *
     * @version <1> 2018/1/31 djh： Created.
     */
    @ApiOperation("查询所有顶级区域数据")
    @PostMapping(value = "queryFirstLevelInitRegionList")
    public ResultMessage queryFirstLevelInitRegionList(){
        return regionService.queryFirstLevelInitRegionList();
    }

    /**
     * 查询指定父节点下的所有子区域数据
     *
     * @param regionTreeReturn 区域树映射实体参数
     * @version <1> 2018/1/31 djh： Created.
     * @version <1> 2018/1/31 djh： Created.
     */
    @ApiOperation(value = "查询指定父节点下的所有子区域数据")
    @ApiImplicitParam(name = "regionTreeReturn", value = "区域参数", required = true, dataType = "RegionTreeReturn")
    @PostMapping(value = "querySubRegionListByParentId")
    public ResultMessage querySubRegionListByParentId(@RequestBody RegionTreeReturn regionTreeReturn) {
        if (null == regionTreeReturn || regionTreeReturn.getId() == null) {
            return ResultMessage.fail();
        }
        return regionService.querySubRegionListByParentId(regionTreeReturn.getId());
    }

    public RegionController() {
        super();
    }

    /**
     * 查询指定区域编码是否存在
     *
     * @param regionParam 区域参数
     * @version <1> 2018-02-10 djh： Created.
     */
    @ApiOperation(value = "查询指定区域编码是否存在")
    @ApiImplicitParam(name = "regionParam", value = "区域参数", required = true, dataType = "RegionParam")
    @PostMapping(value = "checkRegionCodeIsExists")
    public ResultMessage checkRegionCodeIsExists(@RequestBody RegionParam regionParam) {
        if (regionParam == null || regionParam.getRegionCode() == null) {
            return ResultMessage.fail("1", "系统故障");
        }
        return regionService.checkRegionCodeIsExists(regionParam.getRegionCode());
    }

    /**
     * 查询所有有效区域数据
     *
     * @version <1> 2018-05-10 wl： Created.
     */
    @ApiOperation("查询所有有效区域数据")
    @PostMapping(value = "/findAll")
    public ResultMessage findAll(){
        return regionService.findAll();
    }
    /**
     * 根据账号获取区域权限信息
     * 1. 获取账号购买的数据服务，从而得到区域信息
     * 2. 过滤重复区域
     * 3. 获取每个区域的上一级区域，直到区域没有上一级为止。
     * 4. 将账号的权限区域及相关的所有上一级区域进行返回
     * @param parentId: 上级ID
     * @return ResultMessage : 区域列表，如果返回true,包含List<RegionParam>
     * @version <1> 2017-12-20 Hayden:Created.
     */
    @ApiOperation(value="Find Region List for User",notes="根据账号获取区域权限信息")
    @ApiImplicitParam(name = "parentId",value = "父级区域ID",required = false,paramType="form",dataType = "Long")
    @RequestMapping(value="/userRegionList",method=RequestMethod.POST)
    public ResultMessage queryRegionListByAccount(HttpServletRequest request, @RequestParam(required=false) Long parentId){
        String accountToken = AccountTokenUtil.getToken(request);
        return regionService.findRegionByToken(accountToken,parentId);
    }

    /**
     * 根据账号获取区域权限信息
     * 1. 获取账号购买的数据服务，从而得到区域信息
     * 2. 过滤重复区域
     * 3. 获取每个区域的上一级区域，直到区域没有上一级为止。
     * 4. 将账号的权限区域及相关的所有上一级区域进行返回
     * @param parentId: 上级ID
     * @return ResultMessage : 区域列表，如果返回true,包含List<RegionParam>
     * @version <1> 2017-12-20 Hayden:Created.
     */
    @ApiOperation(value="Find Region List for User",notes="根据账号获取区域权限信息")
    @ApiImplicitParam(name = "parentId",value = "父级区域ID",required = false,paramType="form",dataType = "Long")
    @RequestMapping(value="/userAllRegionList",method=RequestMethod.POST)
    public ResultMessage queryAllRegionListByAccount(HttpServletRequest request, @RequestParam(required=false) Long parentId){
        String accountToken = AccountTokenUtil.getToken(request);
        return regionService.findAllRegionByToken(accountToken,parentId);
    }

    /**
     *  @description: 根据父类区域ID查询其下一级区域列表
     *  @version <1> 2018-01-18 cxj:Created.
     */
    @ApiOperation(value="Find Region List for Parent Region",notes="根据父类区域ID查询其下一级区域列表")
    @ApiImplicitParam(name = "parentId",value = "父级区域ID",required = true,dataType = "Long")
    @PostMapping(value="queryRegionListByParentId")
    public ResultMessage queryRegionListByParentId(@RequestParam Long parentId){
        return regionService.findRegionListByParentId(parentId);
    }


    /**
     * 根据regionId查询该regionId的家族祖先信息
     * @param regionId
     * @return
     * @version<1> 2018-10-14 lcw :Created.
     */
    @ApiImplicitParam(name = "regionId",value = "区域ID",required = true,dataType = "Long")
    @PostMapping("queryRegionFamily")
    public ResultMessage queryRegionFamily(@RequestParam Long regionId){

        return regionService.findRegionFamily(regionId);
    }

    /**
     * 根据父级页面查询所有页面，若为空则查询level=1的区域
     * @return
     */
/*    @PostMapping("/queryRegionsByParentId")
    public ResultMessage queryRegionsByParentId( @RequestParam(required=false) Long parentId){
        ResultMessage re = ResultMessage.success();
        re.setData(regionService.findRegionsByParentId(parentId));
        return re;
    }*/

    /**
     * 根据父类区域ID查询其下一级区域列表
     * @version <1> 2017-11-07 Hayden:Created.
     */
  /*  @ApiOperation(value="Find Region List for Parent Region",notes="根据父类区域ID查询其下一级区域列表")
    @ApiImplicitParam(name = "parentId",value = "父级区域ID",required = false,paramType="form",dataType = "Long")
    @RequestMapping(value="/regionList",method=RequestMethod.GET)
    public ResultMessage queryRegionList(Long parentId){
        List<InitRegion> list =  regionService.findRegionsByParentId(parentId);
        return ResultMessage.success("success","1",list);
    }
*/

    @ApiOperation(value="Find Region List for Parent Region",notes="根据父类区域ID查询自身以及其下一级区域列表")
    @ApiImplicitParam(name = "parentId",value = "父级区域ID",required = false,paramType="form",dataType = "Long")
    @RequestMapping(value="/findRegionIdListByRegionCode",method= RequestMethod.POST)
    public ResultMessage findRegionIdListByRegionCode(@RequestParam String regionCode){
        return regionService.findRegionIdListByRegionCode(regionCode);
    }

    @ApiOperation(value="Find Region List for Parent Region",notes="根据父类区域ID查询自身以及其下一级区域列表")
    @ApiImplicitParam(name = "parentId",value = "父级区域ID",required = false,paramType="form",dataType = "Long")
    @RequestMapping(value="/findRegionByCode",method= RequestMethod.POST)
    public ResultMessage findRegionByCode(@RequestParam String regionCode){
        return regionService.findRegionByCode(regionCode);
    }

}
