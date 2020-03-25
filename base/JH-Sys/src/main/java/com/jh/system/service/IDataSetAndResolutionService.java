package com.jh.system.service;

import com.jh.system.entity.RelateDataSetResolution;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 * Description: 数据集和精度关联接口
 * 1.
 *
 * @version <1> 2018/8/30 15:54 zhangshen: Created.
 */
public interface IDataSetAndResolutionService {

    /**
     * 根据数据集id和精度id查询某数据集绑定的精度
     * @param dataSetId
     * @return 数据精度
     */
    ResultMessage queryResolutionListByDataSetId(Integer dataSetId);

    /**
     * 根据数据集id和精度id查询某数据集绑定的精度
     * @param relateDataSetResolution
     * @return 数据精度
     */
    ResultMessage queryResolutionListByDSId(RelateDataSetResolution relateDataSetResolution);

    /**
     * 新增或修改数据集与精度的绑定
     * @param relateDataSetResolution
     * @return
     */
     ResultMessage addDatasetAndResolutionRelateData(RelateDataSetResolution relateDataSetResolution);

    /**
     * Description: 查询数据集精度列表
     * @param
     * @return 数据精度列表
     * @version <1> 2019-03-13  cxw: Created.
     */
    ResultMessage findDsResolutionList();
}
