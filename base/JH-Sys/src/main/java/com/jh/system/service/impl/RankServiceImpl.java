package com.jh.system.service.impl;

import com.jh.system.mapping.IRankMapper;
import com.jh.system.model.RankParam;
import com.jh.system.service.IRankService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RankServiceImpl implements IRankService {
    @Autowired
    private IRankMapper iRankMapper;

    @Override
    public ResultMessage queryRegionRankStatistics(Long parentId) {
        List<RankParam> list = iRankMapper.queryRegionRankStatistics(parentId);
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage findRankTimes(RankParam rankParam) {
        List<String> list = iRankMapper.findRankTimes(rankParam);
        return ResultMessage.success(list);
    }
}
