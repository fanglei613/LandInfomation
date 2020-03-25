package com.jh.land.mapping;

import com.jh.land.entity.LandEntity;
import com.jh.land.model.LandParam;
import com.jh.land.model.LandVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ILandMapper {

    /**
     * 获取乡镇地块统计信息
     */
    List<LandVO> queryLandStatistics(LandParam landParam);

    /**
     * 分页查询地块列表
     */
    List<LandEntity> queryLandList(LandParam landParam);

    /**
     * 查询地块总数
     */
    Long queryLandListSum(LandParam landParam);

    /**
     * 根据经纬度查询单个地块
     */
    LandEntity queryLandInfoByLonLat(LandParam landParam);

    /**
     * 根据ID查询单个地块
     */
    LandEntity queryLandInfoById(LandParam landParam);

}
