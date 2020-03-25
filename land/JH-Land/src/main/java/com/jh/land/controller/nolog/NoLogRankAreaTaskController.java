package com.jh.land.controller.nolog;

import com.github.pagehelper.PageInfo;
import com.jh.biz.controller.BaseController;
import com.jh.land.entity.DsRankTask;
import com.jh.land.model.LayerParam;
import com.jh.land.model.RankAreaTaskVO;
import com.jh.land.service.IRankAreaTaskService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Api(value = "地块数据入库接口", description = "地块数据入库接口")
@RestController
@RequestMapping("/nolog/rankAreaTask")
public class NoLogRankAreaTaskController extends BaseController {

    @Autowired
    private IRankAreaTaskService rankAreaTaskService;

    /*
     * 功能描述:获取地块数据入库信息（带分页）
     * @Param:
     * @Return:
     * @version<1>  2019/11/28  wangli :Created
     */
    @ApiOperation(value = "获取数据入库任务信息列表(带分页)", notes = "获取数据入库任务信息列表(带分页)")
    @ApiImplicitParam(name = "warehousingTaskVO", value = "数据入库任务参数", required = true, dataType = "WarehousingTaskVO")
    @PostMapping("/findRankAreaTaskPage")
    public PageInfo<RankAreaTaskVO> findRankAreaTaskPage(RankAreaTaskVO rankAreaTaskVO) {
        return rankAreaTaskService.findRankAreaTaskPage(rankAreaTaskVO);
    }

    /*
     * 功能描述: 根据任务id执行数据入库任务
     * @Param:
     * @Return:
     * @version<1>  2019/11/28  wangli :Created
     */
    @ApiOperation(value = "根据任务id执行数据入库任务", notes = "根据任务id执行数据入库任务")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer")
    @PostMapping("/executeRankAreaTaskById")
    public ResultMessage executeRankAreaTaskById(@RequestParam Integer taskId) {
        RankAreaTaskVO rankAreaTaskVO = new RankAreaTaskVO();
        rankAreaTaskVO.setTaskId(taskId);
        rankAreaTaskVO.setCreator(getCurrentAccountId());
        rankAreaTaskVO.setCreatorName(getCurrentNickName());
        rankAreaTaskVO.setCreateTime(LocalDateTime.now());
        return rankAreaTaskService.executeRankAreaTaskById(rankAreaTaskVO);
    }

    /*
     * 功能描述:删除地块数据任务
     * @Param:
     * @Return:
     * @version<1>  2019/11/29  wangli :Created
     */
    @ApiOperation(value = "根据任务id删除数据入库任务", notes = "根据任务id删除数据入库任务")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer")
    @PostMapping("/deleteRankAreaTaskById")
    public ResultMessage deleteRankAreaTaskById(@RequestParam Integer taskId) {
        return rankAreaTaskService.deleteRankAreaTaskById(taskId);
    }


    /*
     * 功能描述:根据传递过来的区域id  区域等级  开始时间和结束时间  返回图层名称
     * @Param:
     * @Return:
     * @version<1>  2019/12/10  wangli :Created
     */
    @ApiOperation(value = "根据区域和时间查询图层名称", notes = "根据区域和时间查询图层名称")
    @ApiImplicitParam(name = "layerParam", value = "任务id", required = true, dataType = "LayerParam")
    @PostMapping("/findLayerName")
    public ResultMessage findLayerName(@RequestBody LayerParam layerParam) {
        return rankAreaTaskService.findLayerName(layerParam);
    }

    /**
     * 批量发布数据和图层
     *
     * @param taskIds
     * @return ResultMessage
     * @version <1> 2019/8/30 16:20 zhangshen:Created.
     */
    @ApiOperation(value = "批量发布数据和图层", notes = "批量发布数据和图层")
    @ApiImplicitParam(name = "taskIds", value = "任务ids", required = true, dataType = "String")
    @PostMapping("/batchPublishTask")
    public ResultMessage batchPublishTask(@RequestBody String taskIds) {
        DsRankTask task = new DsRankTask();
        task.setPublisher(getCurrentAccountId());
        task.setPublisherName(getCurrentNickName());
        task.setPublishTime(LocalDateTime.now());
        return rankAreaTaskService.batchPublishTask(taskIds, task);
    }

    /*
     * 功能描述: 批量撤回数据
     * @Param:
     * @Return:
     * @version<1>  2019/12/13  wangli :Created
     */
    @ApiOperation(value = "批量撤回数据", notes = "批量发布数据和图层")
    @ApiImplicitParam(name = "taskIds", value = "任务ids", required = true, dataType = "String")
    @PostMapping("/batchBackTask")
    public ResultMessage batchBackTask(@RequestBody String taskIds) {
        DsRankTask task = new DsRankTask();
        task.setPublisher(getCurrentAccountId());
        task.setPublisherName(getCurrentNickName());
        task.setPublishTime(LocalDateTime.now());
        return rankAreaTaskService.batchPublishTask(taskIds, task);
    }
}
