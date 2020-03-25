package com.jh.land.service.impl;

import com.jh.land.entity.InitRank;
import com.jh.land.mapping.IRankInfoMapper;
import com.jh.land.model.RankParam;
import com.jh.land.service.IRankInfoService;
import org.springframework.beans.factory.annotation.Autowired;

public class RankInfoServiceImpl implements IRankInfoService {

    @Autowired
    private IRankInfoMapper rankInfoMapper;

    @Override
    public InitRank queryRankById(RankParam rankParam) {
        return rankInfoMapper.queryRankById(rankParam);
    }
}
