package com.jh.system.service;

import com.jh.system.entity.OpLoginLogs;
import com.jh.vo.ResultMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface IOpLoginLogsService {

    /*
     * 功能描述:用户每次登录成功则进行一次记录
     * @Param:
     * @Return: [opLoginLogs]
     * @version<1>  2019/11/14  wangli :Created
     */
    ResultMessage recordLogin(OpLoginLogs opLoginLogs);


    /*
     * 功能描述:统计用户总共登录成功的记录
     * @Param:
     * @Return:
     * @version<1>  2019/11/14  wangli :Created
     */
    ResultMessage queryLoginNum(Integer accountId);
}
