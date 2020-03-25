package com.jh.land.mapping;


import com.jh.land.entity.RankAreaTimes;
import com.jh.land.model.RankAreaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IRankAreaTimesMapper {


    /*
     * 功能描述: 删除所有地块分布表的时间轴信息
     * @Param:
     * @Return:
     * @version<1>  2019/12/9  wangli :Created
     */
    int deleteAllRankAreaTimes();

    /*
     * 功能描述: 批量插入时间轴信息
     * @Param:
     * @Return:
     * @version<1>  2019/12/9  wangli :Created
     */
    int addRankAreaTimes(List<RankAreaTimes> rankAreaTimes);

    /*
     * 功能描述:根据区域id  作物信息 精度信息 查询时间轴
     * @Param:
     * @Return:
     * @version<1>  2019/12/9  wangli :Created
     */
    List<String> findRankAreaTimes(RankAreaVO rankAreaVO);
}