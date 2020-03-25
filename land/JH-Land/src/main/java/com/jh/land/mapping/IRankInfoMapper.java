package com.jh.land.mapping;

import com.jh.land.entity.InitRank;
import com.jh.land.model.RankParam;
import org.mapstruct.Mapper;

@Mapper
public interface IRankInfoMapper {

    InitRank queryRankById(RankParam rankParam);
}
