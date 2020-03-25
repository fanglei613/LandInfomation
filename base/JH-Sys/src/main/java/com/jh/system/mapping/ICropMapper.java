package com.jh.system.mapping;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 查询作物
 * @version <1> 2017-12-06 cxw : Created.
 */
@Component
@Mapper
public interface ICropMapper {

    /**
     * 根据区域查询关联的作物
     * @param regionId  区域ID
     * @return (cropId , cropCode , cropName)
     */
    List<Map<String,Object>> findCropList(Long regionId);
}
