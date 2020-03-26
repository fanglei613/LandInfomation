package com.jh.land.service.impl;

import com.jh.land.mapping.InitSoilTypeMapper;
import com.jh.land.service.IRankSoilTypeService;
import org.springframework.beans.factory.annotation.Autowired;

public class RankSoilTypeServiceImpl implements IRankSoilTypeService {

    @Autowired
    private InitSoilTypeMapper soilTypeMapper;

    @Override
    public Integer findSoilTypeByTownCode(String townCode) {
        return soilTypeMapper.findSoilTypeByTownCode(townCode);
    }
}
