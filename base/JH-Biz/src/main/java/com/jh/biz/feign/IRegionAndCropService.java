package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 区域与作物配置接口
 * 1.根据区域ID查询关联作物
 * @version <1> 2018-08-13 cxw: Created.
 */
@FeignClient(name="jh-sys")
public interface IRegionAndCropService {

    /**
     * 根据区域查询作物
     * regionId 区域ID
     * <1> 2018-8-13 cxw: Created.
     */
    @GetMapping( "/regionAndCrop/queryCropListByRegionId")
    public ResultMessage queryCropListByRegionId(@RequestParam(name="regionId")Long regionId);
}
