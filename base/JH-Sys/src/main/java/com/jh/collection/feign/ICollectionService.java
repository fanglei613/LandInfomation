package com.jh.collection.feign;
import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 移动采集接口，通过feign方式
 *
 */
@FeignClient(name="jh-forum")
public interface ICollectionService {
    /**
     * 采集注册用户注册后默认创建模板和任务
     */
    @PostMapping(value = "/collection/taskInfo/registerToAllocation")
    ResultMessage registerToAllocation(@RequestParam(name = "phone") String phone);
}