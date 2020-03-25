package com.jh.system.service;

import com.jh.vo.ResultMessage;
import com.jh.system.base.service.IBaseService;
import com.jh.system.entity.DataProduct;

import java.util.List;

/**
 * 数据权限
 * Created by XZG on 2018/6/6.
 */
public interface IDataProductService extends IBaseService<DataProduct,DataProduct,Integer> {


    /**
     * 添加用户权限
     * @param product
     * @return
     */
    public ResultMessage saveUserProduct(DataProduct product);

    /**
     * 根据权限主键删除权限
     * @param product
     * @return
     */
    public ResultMessage deleteUserProductByProductId(DataProduct product);

    /**
     * 查询用户的数据权限
     * @param product
     * @return
     */
    public ResultMessage findProductByAccountId(DataProduct product);

    /**
     * 批量给多个用户添加相同的数据权限
     * @param accountIdList
     * @param product
     * @return
     */
    public ResultMessage batchSaveProduct(List<Integer> accountIdList, DataProduct product);

    /**
     * 更新默认显示的数据权限
     * @param product
     * @return
     */
    public ResultMessage updateDataProductDefaultShow(DataProduct product);

    /**
     * 查看用户是否有数据权限
     * @param product
     * @return
     */
    ResultMessage queryHasProduct(DataProduct product,String accountToken);

}
