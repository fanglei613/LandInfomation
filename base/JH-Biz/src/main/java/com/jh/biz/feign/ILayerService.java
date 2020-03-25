package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Description: 内部图层接口定义，用于调用私有图层数据服务
 * 1. 图层发布接口
 * 2. 查询作物图层接口
 *
 * @version <1> 2018-07-26 13:29 zhangshen: Created.
 */
@FeignClient(name="jh-map")
public interface ILayerService {

    /**
     * 根据任务id批量发布图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/31 10:41 zhangshen:Created.
     */
    @PostMapping("/layer/batchPublishTifByTaskId")
    ResultMessage batchPublishTifByTaskId(Integer taskId);

    /**
     * 根据任务id批量撤回图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/31 10:41 zhangshen:Created.
     */
    @PostMapping("/layer/batchBackTifByTaskId")
    ResultMessage batchBackTifByTaskId(Integer taskId);

    /**
     * 根据任务id批量删除图层
     * @param taskId
     * @return ResultMessage
     * @version <1> 2019/8/31 10:41 zhangshen:Created.
     */
    @PostMapping("/layer/deleteLayerByTaskId")
    ResultMessage deleteLayerByTaskId(Integer taskId);

}
