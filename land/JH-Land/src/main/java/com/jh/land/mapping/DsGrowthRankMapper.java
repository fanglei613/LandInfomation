package com.jh.land.mapping;


import com.jh.land.entity.DsGrowthRank;
import com.jh.land.model.RankVO;

import java.util.List;

public interface DsGrowthRankMapper {

    List<DsGrowthRank> queryGrowthLevel(RankVO rankVO);
}