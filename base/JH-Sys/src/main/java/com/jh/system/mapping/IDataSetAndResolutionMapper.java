package com.jh.system.mapping;

import com.jh.system.entity.RelateDataSetResolution;
import com.jh.system.model.RegionCropParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description: 数据集和精度关联Mapper
 * 1.
 *
 * @version <1> 2018/8/30 16:08 zhangshen: Created.
 */
@Mapper
public interface IDataSetAndResolutionMapper {

    /**
     * 根据数据集id 查询精度列表
     * @param dataSetId
     * @return 精度列表
     */
    List<RelateDataSetResolution> queryResolutionListByDataSetId(Integer dataSetId);

    /*
     *批量新增数据集与精度关联
    * data_set_id 数据集ID
    * resolution_id 精度ID
    * <1> 2018-08-31 huxiaoqiang: Created.
     */
    int addDatasetAndResolutionRelateData(List<RelateDataSetResolution> relateDataSetResolutionList);

    /**
     *批量删除数据集与精度关联
     * data_set_id 数据集ID
     * resolution_id 精度ID
     * <1> 2018-08-31 huxiaoqiang: Created.
     */
    int delDatasetAndResolutionRelateData(Integer dataSetId);

    /**
     * Description: 查询数据集精度列表
     * @param
     * @return 数据精度列表
     * @version <1> 2019-03-13  cxw: Created.
     */
    List<RelateDataSetResolution> findDsResolutionList();

}
