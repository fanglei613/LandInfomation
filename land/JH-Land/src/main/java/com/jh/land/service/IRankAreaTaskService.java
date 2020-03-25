package com.jh.land.service;

import com.github.pagehelper.PageInfo;
import com.jh.land.entity.DsRankTask;
import com.jh.land.model.LayerParam;
import com.jh.land.model.RankAreaTaskVO;
import com.jh.vo.ResultMessage;

public interface IRankAreaTaskService {

    /*
     * 功能描述:查询地块数据入库任务（带分页）
     * @Param:
     * @Return:
     * @version<1>  2019/11/28  wangli :Created
     */
    PageInfo<RankAreaTaskVO> findRankAreaTaskPage(RankAreaTaskVO rankAreaTaskVO);

    /*
     * 功能描述:根据任务id执行数据入库
     * @Param:
     * @Return:
     * @version<1>  2019/11/28  wangli :Created
     */
    ResultMessage executeRankAreaTaskById(RankAreaTaskVO rankAreaTaskVO);

    /*
     * 功能描述: 根据任务id删除入库任务
     * @Param:
     * @Return:
     * @version<1>  2019/11/29  wangli :Created
     */
    ResultMessage deleteRankAreaTaskById(Integer taskId);

    /*
     * 功能描述: 激活数据处理任务
     * @Param:
     * @Return:
     * @version<1>  2019/11/29  wangli :Created
     */
    ResultMessage activationTask(Integer taskId);

    /*
     * 功能描述:根据区域id和时间查询图层名称
     * @Param:
     * @Return:
     * @version<1>  2019/12/10  wangli :Created
     */
    ResultMessage findLayerName(LayerParam layerParam);

    /*
     * 功能描述: 批量发布图层
     * @Param:
     * @Return:
     * @version<1>  2019/12/12  wangli :Created
     */
    ResultMessage batchPublishTask(String taskIds, DsRankTask task);


}
