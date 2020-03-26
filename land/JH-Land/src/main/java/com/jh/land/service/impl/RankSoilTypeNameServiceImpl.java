package com.jh.land.service.impl;

import com.jh.land.entity.InitSoilTypeName;
import com.jh.land.mapping.InitSoilTypeNameMapper;
import com.jh.land.service.IRankSoilTypeNameService;
import org.springframework.beans.factory.annotation.Autowired;

public class RankSoilTypeNameServiceImpl implements IRankSoilTypeNameService {

    @Autowired
    private InitSoilTypeNameMapper soilTypeNameMapper;

    @Override
    public InitSoilTypeName findRankSoilTypeNameByTypeId(Integer soilType) {
        return soilTypeNameMapper.findRankSoilTypeNameByTypeId(soilType);
    }
}
