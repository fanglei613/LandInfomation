package com.jh.system.service;

import com.jh.system.model.RankParam;
import com.jh.vo.ResultMessage;

public interface IRankService {
    /*
     * 功能描述:查询统计各镇地块总数量和面积
     * @Param:
     * @Return:
     * @version<1>  2019/10/29  wangli :Created
     */
    ResultMessage queryRegionRankStatistics(Long parentId);

    /**
     * 根据条件查询灾害的数据时间点
     * @param
     * @return
     * @version <1> 2019/11/6 wangli:Created.
     */
    ResultMessage findRankTimes(RankParam rankParam);
}
