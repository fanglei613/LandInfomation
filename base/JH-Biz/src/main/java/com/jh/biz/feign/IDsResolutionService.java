package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 数据集和精度关联接口
 * @version <1> 2019-03-13 cxw: Created.
 */
@FeignClient(name="jh-sys")
public interface IDsResolutionService {

    /**
     * 数据集和精度关联
     * <1> 2019-03-13 cxw: Created.
     */
    @GetMapping( "/dataSetAndResolution/queryDsResolutionList")
    public ResultMessage queryDsResolutionList();
}
