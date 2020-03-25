package com.jh.system.mapping;

import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.entity.DataProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 数据权限
 * Created by XZG on 2018/6/6.
 */
@Mapper
public interface IDataProductMapper extends IBaseMapper<DataProduct, DataProduct ,Integer> {

    /**
     * 根据条件查询数据权限
     * @param product
     * @return
     */
    public List<DataProduct> selectDataProduct(DataProduct product);

    /**
     * 保存数据权限
     * @param product
     * @return
     */
    public int insertDataProduct(DataProduct product);


    /**
     * 删除用户
     * @param product
     * @return
     */
    public int deleteDataProduct(DataProduct product);


    /**
     * 更新数据权限
     * @param product
     * @return
     */
    public int updateDataProduct(DataProduct product);

    /**
     * 根据条件查询数据权限
     * @param product
     * @return
     */
    public DataProduct selectByParam(DataProduct product);

    /**
     * 根据条件查询数据权限是否存在
     * @param product
     * @return int
     */
    public int selectByParams(DataProduct product);


    /**********************************************农情简报部分*****************************************************************/
    /**
     * 添加用户数据权限
     * @version  <1> 2018-1-16 cxw : Created.:
     * @param dataProductEntity 数据产品对象
     */
    int addDataProduct(DataProduct dataProductEntity);

    /**
     * 根据用户ID获取用户的所有数据访问权限
     * @param  accountId
     * @return List<DataProduct> 用户的所有数据权限
     * @version <1> 2018-1-5 lcw : Created.:
     */
    List<Map<String,Object>> findProductsByAccountId(Integer accountId);


    /**
     * 根据区域，作物，数据集，分辨率获取用户权限时间
     * @param dataProductEntity 数据产品对象
     * regionId 区域主键
     * dsId 数据集主键
     * dataTypeId 分辨率主键
     * cropId 作物主键
     * @return
     * @version <1> 2018-07-05 cxw : Created.
     */
    Map<String,Object> dataTimeForPerm(DataProduct dataProductEntity);

    /**
     * 根据区域，作物，数据集，分辨率获取用户权限时间
     * @param dataProductEntity 数据产品对象
     * regionId 区域主键
     * dsId 数据集主键
     * dataTypeId 分辨率主键
     * cropId 作物主键(为空)
     * @return
     * @version <1> 2018-07-05 cxw : Created.
     */
    Map<String,Object> dataTimeForPermCrop(DataProduct dataProductEntity);

}
