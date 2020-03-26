package com.jh.land.service;

import com.jh.land.entity.InitSoilTypeName;

public interface IRankSoilTypeNameService {

    InitSoilTypeName findRankSoilTypeNameByTypeId(Integer soilType);
}
