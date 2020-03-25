package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 调用系统微服务中接口
 * XZG 2019-07-29  13:09
 */
@FeignClient(value = "JH-SYS",fallback = SysServiceFallback.class)
public interface SysService {

    /**根据手机号获取AccountId，用于feign调用
     * @param accountName 手机号
    * XZG 2019-07-29  13:09
    * @return com.jh.vo.ResultMessage
    */
    @PostMapping(value = "/person/getAccountIdByPhone")
    ResultMessage getAccountIdByPhone(@RequestParam("accountName") String accountName);

    /**根据手机号获取AccountId，用于feign调用
     * @param request
     * XZG 2019-07-29  13:09
     * @return com.jh.vo.ResultMessage
     */
    @PostMapping(value = "/person/findCurrentPerson")
    ResultMessage findCurrentPerson(@RequestParam("request") HttpServletRequest request);


}
