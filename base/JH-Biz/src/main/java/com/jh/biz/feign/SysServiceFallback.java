package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * XZG 2019-07-29  13:18
 */
@Component
public class SysServiceFallback implements SysService{

    @Override
    public ResultMessage getAccountIdByPhone(String accountName) {
        return ResultMessage.fail("服务中断");
    }

    @Override
    public ResultMessage findCurrentPerson(HttpServletRequest request) {
        return ResultMessage.fail("服务中断");
    }

}
