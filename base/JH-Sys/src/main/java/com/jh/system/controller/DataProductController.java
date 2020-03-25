package com.jh.system.controller;

import com.jh.system.base.controller.BaseController;
import com.jh.system.entity.PermAccount;
import com.jh.util.AccountTokenUtil;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.DataProduct;
import com.jh.system.service.impl.DataProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据权限接口
 * Created by XZG on 2018/6/6.
 * @version <1> 2018-06-06 xzg： Created.
 */
@Api(value = "数据权限接口")
@RestController
@RequestMapping("/dataProduct")
public class DataProductController extends BaseController {

    @Autowired
    private DataProductServiceImpl dataProductService;

    /**
     * 添加数据权限
     * @version  <1> 2018-06-06 xzg : Created.:
     */
    @ApiOperation(value="添加数据权限",notes="为用户添加数据权限" )
    @PostMapping(value="/saveProduct")
    public ResultMessage saveProduct(@RequestBody DataProduct product){
        return dataProductService.saveUserProduct(product);
    }

    /**
     * 删除数据权限
     * @version  <1> 2018-06-06 xzg : Created.:
     */
    @ApiOperation(value="删除数据权限",notes="删除数据权限" )
    @PostMapping(value="/delProduct")
    public ResultMessage delProduct(@RequestBody DataProduct product){
        return dataProductService.deleteUserProductByProductId(product);
    }

    /**
     * 根据用户查询数据权限
     * @version  <1> 2018-06-06 xzg : Created.:
     */
    @ApiOperation(value="根据用户查询数据权限",notes="根据用户查询数据权限" )
    @PostMapping(value="/queryProduct")
    public ResultMessage queryProduct(@RequestBody DataProduct product){
        return dataProductService.findProductByAccountId(product);
    }

    /**
     * 批量给多个用户添加相同的数据权限
     * @param accountIdList 用户主键ID
     * @param product 数据权限
     * @version  <1> 2018-06-06 xzg : Created.:
     */
    @ApiOperation(value="批量给多个用户添加相同的数据权限",notes="批量给多个用户添加相同的数据权限" )
    @PostMapping(value="/batchSaveProduct")
    public ResultMessage batchSaveProduct(List<Integer> accountIdList,@RequestBody DataProduct product){
        return dataProductService.batchSaveProduct(accountIdList,product);
    }


    /**
     * 重新设置默认数据权限
     * @version  <1> 2018-06-06 xzg : Created.:
     */
    @ApiOperation(value="重新设置默认数据权限",notes="重新设置默认数据权限" )
    @PostMapping(value="/settingDefaultShow")
    public ResultMessage settingDefaultShow(@RequestBody DataProduct product){
        return dataProductService.updateDataProductDefaultShow(product);
    }

    /**
     * 查询用户是否有数据权限
     * @version  <1> 2018-06-06 xzg : Created.:
     */
    @ApiOperation(value="查询用户是否有数据权限",notes="查询用户查询数据权限" )
    @PostMapping(value="/queryHasProduct")
    public ResultMessage queryHasProduct(@RequestBody DataProduct product){
        PermAccount permAccount = getCurrentPermAccount();
        if(permAccount != null){
            product.setAccountId(permAccount.getAccountId());
        }
        String accountToken = AccountTokenUtil.getToken(request);
        return dataProductService.queryHasProduct(product,accountToken);
    }

    /**
     * 查询用户是否有数据权限Feign调用
     * @version  <1> 2018-06-06 xzg : Created.:
     */
    @ApiOperation(value="查询用户是否有数据权限",notes="查询用户查询数据权限" )
    @PostMapping(value="/queryHasProductForFeign")
    public ResultMessage queryHasProductForFeign(@RequestParam Integer accountId,
                                                 @RequestParam Integer DatasetId,
                                                 @RequestParam Long regionId,
                                                 @RequestParam Integer cropId,
                                                 @RequestParam Integer resolution,
                                                 @RequestParam String startDate,
                                                 @RequestParam String endDate,
                                                 @RequestParam String accountToken){
        DataProduct product = new DataProduct();
        product.setDatasetId(DatasetId);
        product.setAccountId(accountId);
        product.setRegionId(regionId);
        product.setStartDate(startDate);
        product.setAccuracyId(resolution);
        product.setEndDate(endDate);
        return dataProductService.queryHasProduct(product,accountToken);
    }

}
