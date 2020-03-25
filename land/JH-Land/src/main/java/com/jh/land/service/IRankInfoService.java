package com.jh.land.service;

import com.jh.land.entity.InitRank;
import com.jh.land.model.RankParam;

public interface IRankInfoService {

    InitRank queryRankById(RankParam rankParam);
}
