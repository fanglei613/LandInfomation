package com.jh.land.service;

import com.jh.land.entity.RankDetail;
import com.jh.vo.ResultMessage;

public interface IRankDetailService {
    /*
     * 功能描述: 根据rankId查询基本信息
     * @Param:
     * @Return: [rankId]
     * @version<1>  2020/3/24  wangli :Created
     */
    ResultMessage rankDetailForBase(Long rankId);

    /*
     * 功能描述: 根据地块id和年份查询地块概况
     * @Param:
     * @Return: [rankDetail]
     * @version<1>  2020/3/24  wangli :Created
     */
    ResultMessage rankDetailForBasicFacts(RankDetail rankDetail);

    /*
     * 功能描述: 根据地块id和年月查询地块的历年长势数据
     * @Param:
     * @Return:
     * @version<1>  2020/3/24  wangli :Created
     */
    ResultMessage rankDetailForGrowth(RankDetail rankDetail);

    /*
     * 功能描述: 根据地块id和年月查询历年的的气象数据
     * @Param:
     * @Return:
     * @version<1>  2020/3/24  wangli :Created
     */
    ResultMessage rankDetailForMeteorologicalPhenomena(RankDetail rankDetail);
}
