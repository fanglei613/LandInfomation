package com.jh.land.service;

import com.jh.land.entity.DsAreaRank;
import com.jh.land.model.AreaVO;
import com.jh.land.model.RankAreaTaskVO;
import com.jh.land.model.RankAreaVO;
import com.jh.vo.ResultMessage;

import java.util.List;

public interface IRankAreaService {
    /*
     * 功能描述:查询时间轴信息
     * @Param:
     * @Return:
     * @version<1>  2019/11/21  wangli :Created
     */
    ResultMessage findAreaTimes(RankAreaVO rankAreaVO);

    /*
     * 功能描述:
     * @Param:
     * @Return:
     * @version<1>  2019/11/21  wangli :Created
     */
    ResultMessage findRankAndBeyondArea(AreaVO areaVO);

    /*
     * 功能描述:
     * @Param:
     * @Return:
     * @version<1>  2019/11/21  wangli :Created
     */
    ResultMessage findThreeYearsArea(AreaVO areaVO);

    /*
     * 功能描述:
     * @Param:
     * @Return:
     * @version<1>  2019/11/21  wangli :Created
     */
    ResultMessage findRegionAndBeyondAreaAndYield(AreaVO areaVO);

    /*
     * 功能描述:删除对应的分布信息
     * @Param:
     * @Return:
     * @version<1>  2019/12/5  wangli :Created
     */
    ResultMessage deleteRankArea(RankAreaTaskVO rankAreaTaskVO);


    /*
     * 功能描述: 地块分布时间轴统计预处理信息
     * @Param:
     * @Return:
     * @version<1>  2019/12/9  wangli :Created
     */
    ResultMessage totalRankAreaTimeStatistics();

    /*
     * 功能描述: 发布分布数据
     * @Param:
     * @Return:
     * @version<1>  2019/12/12  wangli :Created
     */
    ResultMessage publishData(RankAreaVO rankAreaVO);


    List<DsAreaRank> findAreaRankCropById(RankAreaVO rankAreaVO);
}
