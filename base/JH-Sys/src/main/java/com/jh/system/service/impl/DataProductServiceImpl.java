package com.jh.system.service.impl;

import com.jh.constant.CommonDefineEnum;
import com.jh.enums.DataStatusEnum;
import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.base.service.impl.BaseServiceImpl;
import com.jh.util.AccountTokenUtil;
import com.jh.util.DateUtil;
import com.jh.util.StringUtil;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.DataProduct;
import com.jh.system.mapping.IDataProductMapper;
import com.jh.system.service.IDataProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据权限
 * Created by XZG on 2018/6/6.
 */
@Service
@Transactional
public class DataProductServiceImpl extends BaseServiceImpl<DataProduct, DataProduct, Integer> implements IDataProductService{

    @Autowired
    private IDataProductMapper dataProductMapper;

    @Override
    protected IBaseMapper<DataProduct, DataProduct, Integer> getDao() {
        return dataProductMapper;
    }

    @Override
    public ResultMessage saveUserProduct(DataProduct product) {
        if (product == null || product.getAccountId() == null || product.getRegionId() == null || product.getDatasetId() == null
                || !StringUtil.isNotNull(product.getStartDate()) ||  !StringUtil.isNotNull(product.getEndDate())){
            return ResultMessage.fail().fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(),CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }

        product.setProductType(2);//自定义数据权限
        product.setProductStartDate(DateUtil.strToDate(product.getStartDate()));
        product.setProductEndDate(DateUtil.strToDate(product.getEndDate()));
        int num = dataProductMapper.selectByParams(product);
        if(num==0) {
            int insertCount = dataProductMapper.insertDataProduct(product);
            if (insertCount == 0) {
                return ResultMessage.fail(CommonDefineEnum.ADD_PRODUCT_FAIL.getValue(), CommonDefineEnum.ADD_PRODUCT_FAIL.getMesasge());
            }

            //如果设置当前是默认权限，进行更新
            if (product.getDefaultShow()) {
                updateDataProductDefaultShow(product);
            }

            return ResultMessage.success(CommonDefineEnum.ADD_PRODUCT_SUCCESS.getValue(), CommonDefineEnum.ADD_PRODUCT_SUCCESS.getMesasge(),product);
        }
        else{
            return ResultMessage.fail(CommonDefineEnum.ADD_PRODUCT_EXISTS.getMesasge());
        }
    }

    @Override
    public ResultMessage deleteUserProductByProductId(DataProduct product) {
        if (product == null || product.getProductId() == null || product.getAccountId() == null){
            return ResultMessage.fail().fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(),CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }
        DataProduct param = new DataProduct();
        param.setProductId(product.getProductId());
        //当前数据权限为默认权限不能删除
        DataProduct findProduct = dataProductMapper.selectByParam(param);
        if (findProduct != null && findProduct.getDefaultShow()){
            return ResultMessage.fail("默认权限，不能删除");
        }

        param.setAccountId(product.getAccountId());
        int delCount = dataProductMapper.deleteDataProduct(param);
//        if (delCount == 0){
//            return ResultMessage.fail().fail(CommonDefineEnum.DELETE_OBJECT_FAIL.getValue(),CommonDefineEnum.DELETE_OBJECT_FAIL.getMesasge());
//        }
        return ResultMessage.success(CommonDefineEnum.DELETE_OBJECT_SUCCESS.getValue(),CommonDefineEnum.DELETE_OBJECT_SUCCESS.getMesasge());
    }

    @Override
    public ResultMessage findProductByAccountId(DataProduct product) {
        if (product == null || product.getAccountId() == null){
            return ResultMessage.fail().fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(),CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }
        DataProduct param = new DataProduct();
        param.setAccountId(product.getAccountId());
        List<DataProduct> dataProductList =  dataProductMapper.selectDataProduct(param);
        return ResultMessage.success(dataProductList);
    }

    @Override
    public ResultMessage batchSaveProduct(List<Integer> accountIdList, DataProduct product) {
        if (product == null || product.getRegionId() == null || product.getDatasetId() == null || product.getAccuracyId() == null
                || !StringUtil.isNotNull(product.getStartDate()) ||  !StringUtil.isNotNull(product.getEndDate()) || accountIdList == null  || accountIdList.size() == 0){
            return ResultMessage.fail().fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(),CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }

        for (Integer accountId : accountIdList){
            product.setAccountId(accountId);
            dataProductMapper.insertDataProduct(product);
        }

        return ResultMessage.success(CommonDefineEnum.SAVE_OBJECT_SUCCESS.getValue(),CommonDefineEnum.SAVE_OBJECT_SUCCESS.getMesasge());
    }

    @Override
    public ResultMessage updateDataProductDefaultShow(DataProduct product) {
        if (product == null || product.getAccountId() == null || product.getProductId() == null){
            return ResultMessage.fail().fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(),CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }

        DataProduct p1 = new DataProduct();
        p1.setAccountId(product.getAccountId());
        p1.setDefaultShow(false);
        int updateCount = dataProductMapper.updateDataProduct(p1);
        if (updateCount == 0){
            return ResultMessage.fail().fail(CommonDefineEnum.RECORD_UPDATE_FAIL.getValue(),CommonDefineEnum.RECORD_UPDATE_FAIL.getMesasge());
        }

        p1.setAccountId(null);
        p1.setProductId(product.getProductId());
        p1.setDefaultShow(true);
        updateCount = dataProductMapper.updateDataProduct(p1);
        if (updateCount ==  0 ){
            return ResultMessage.fail().fail(CommonDefineEnum.RECORD_UPDATE_FAIL.getValue(),CommonDefineEnum.RECORD_UPDATE_FAIL.getMesasge());
        }
        return ResultMessage.success(CommonDefineEnum.RECORD_UPDATE_SUCCESS.getValue(),CommonDefineEnum.RECORD_UPDATE_SUCCESS.getMesasge());
    }
    @Override
    public ResultMessage queryHasProduct(DataProduct product,String accountToken) {
        if (product == null || product.getAccountId() == null){
            return ResultMessage.fail().fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(),CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }
        //如果是管理员则无限制
        boolean isAdmin = AccountTokenUtil.isSuperAdmin(accountToken);
        if (isAdmin){
            return ResultMessage.success(1);
        }
        if(product.getDatasetId() == 1807){
            product.setAccuracyId(null);
        }
        int num =  dataProductMapper.selectByParams(product);
        return ResultMessage.success(num);
    }
}
