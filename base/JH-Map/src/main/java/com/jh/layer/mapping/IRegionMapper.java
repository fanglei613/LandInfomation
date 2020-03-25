package com.jh.layer.mapping;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description: 区域数据访问接口
 * @version <1> 2018-01-18 cxj： Created.
 * @version <2> 2018/1/26 djh： update.
 *      新增接口
 */
@Mapper
public interface IRegionMapper {


    /*
     *查询regionWorld信息
     */
    public Long getByRegionId(Long regionId);


}
