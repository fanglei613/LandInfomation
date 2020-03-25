package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ProjectName: JH-Cloud
 * @Package: com.jh.biz.feign
 * @ClassName: ISmsService
 * @Description: 通用调用发送短信验证码
 * @Author: wangli
 * @CreateDate: 2018/8/16 18:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/16 18:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@FeignClient(name="jh-sys")
public interface ISmsService {

    /**
     * 发送注册的短信验证码
     * @version wangli [2018-08-16 17:47:09] : Created.
     */
    @PostMapping(value = "/user/sendSmsToPhone")
    ResultMessage sendSmsToPhone(@RequestParam(name="telphone") String telphone);

}
