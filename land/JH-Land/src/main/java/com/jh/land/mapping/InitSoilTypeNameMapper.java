package com.jh.land.mapping;


import com.jh.land.entity.InitSoilTypeName;

import java.util.List;

public interface InitSoilTypeNameMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table init_soil_type_name
     *
     * @mbggenerated
     */
    int insertSelective(InitSoilTypeName record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table init_soil_type_name
     *
     * @mbggenerated
     */
    InitSoilTypeName findRankSoilTypeNameByTypeId(Integer soilType);
}