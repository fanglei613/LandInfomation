package com.jh.cache.controller;

import com.jh.cache.service.ICacheService;
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
 * @description: 数据缓存接口
 * @version <1> 2019-02-12 lijie： Created.
 */
@Api(value = "数据缓存接口")
@RestController
@RequestMapping("/nolog/cache")
public class CacheController {
    @Autowired
    private ICacheService cacheService;


    /**
     * @description: 根据id查询数据字典名称
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="根据id查询数据字典名称",notes="根据id查询数据字典名称" )
    @PostMapping(value="/queryNameByDictId")
    public ResultMessage queryNameByDictId(Integer dictId){
        return cacheService.queryNameByDictId(dictId);
    }

    /**
     * @description: 根据id查询数据字典编码
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="根据id查询数据字典编码",notes="根据id查询数据字典编码" )
    @PostMapping(value="/queryCodeByDictId")
    public ResultMessage queryCodeByDictId(Integer dictId){
        return cacheService.queryCodeByDictId(dictId);
    }

    /**
     * @description: 根据pid查询数据字典列表
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="根据pid查询数据字典列表",notes="根据pid查询数据字典列表" )
    @PostMapping(value="/findSubListByDictId")
    public ResultMessage findSubListByDictId(Integer parentId){
        return cacheService.findSubListByDictId(parentId);
    }


    @PostMapping(value="/queryAllLeaf")
    public ResultMessage queryAllLeaf(Integer parentId){
        return cacheService.queryAllLeaf(parentId);

    }

    /**
     * @description: 根据id查询区域名称
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="根据id查询区域名称",notes="根据id查询区域名称" )
    @PostMapping(value="/queryNameByRegionId")
    public ResultMessage queryNameByRegionId(Long regionId){
        return cacheService.queryNameByRegionId(regionId);
    }

    /**
     * @description: 根据id查询区域编码
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="根据id查询区域编码",notes="根据id查询区域编码" )
    @PostMapping(value="/queryCodeByRegionId")
    public ResultMessage queryCodeByRegionId(Long regionId){
        return cacheService.queryCodeByRegionId(regionId);
    }

    /**
     * @description: 根据pid查询下级区域列表
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="根据pid查询下级区域列表",notes="根据pid查询下级区域列表" )
    @PostMapping(value="/findSubListByRegionId")
    public ResultMessage findSubListByRegionId(Long parentId){
        return cacheService.findSubListByRegionId(parentId);
    }


    /**
     * @description: 根据id查询用户名称
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="根据id查询用户名称",notes="根据id查询用户名称" )
    @PostMapping(value="/queryNameByAccountId")
    public ResultMessage queryNameByAccountId(Integer accountId){
        return cacheService.queryNameByAccountId(accountId);
    }

    /**
     * @description: 根据pid查询用户列表
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="根据pid查询用户列表",notes="根据pid查询用户列表" )
    @PostMapping(value="/findUserByAccountId")
    public ResultMessage findUserByAccountId(Integer parentId){
        return cacheService.findUserByAccountId(parentId);
    }

    /**
     * @description: 初始化数据字典缓存
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="初始化数据字典缓存",notes="初始化数据字典缓存" )
    @PostMapping(value="/initDictCache")
    public void initDictCache(){
        cacheService.initDictCache();
    }

    /**
     * @description: 初始化区域缓存
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="初始化区域缓存",notes="初始化区域缓存" )
    @PostMapping(value="/initRegionCache")
    public void initRegionCache(){
        cacheService.initRegionCache();
    }

    /**
     * @description: 初始化用户缓存
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="初始化用户缓存",notes="初始化用户缓存" )
    @PostMapping(value="/initUserCache")
    public void initUserCache(){
        cacheService.initUserCache();
    }

    /**
     * @description: 初始化所有数据缓存
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="初始化所有数据缓存",notes="初始化所有数据缓存" )
    @PostMapping(value="/initAllDataCache")
    public void initAllDataCache(){
        cacheService.initAllDataCache();
    }

    /**
     * @description: 获取缓存最后更新时间
     * @version <1> 2019-02-12 lijie： Created.
     */
    @ApiIgnore
    @ApiOperation(value="获取缓存最后更新时间",notes="获取缓存最后更新时间" )
    @PostMapping(value="/getCacheLastTime")
    public ResultMessage getCacheLastTime(){
        return cacheService.getCacheLastTime();
    }


}
