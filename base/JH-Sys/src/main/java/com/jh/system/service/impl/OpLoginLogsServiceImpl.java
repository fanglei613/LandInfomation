package com.jh.system.service.impl;

import com.jh.system.entity.OpLoginLogs;
import com.jh.system.mapping.IOpLoginLogsMapper;
import com.jh.system.mapping.IPermPersonMapper;
import com.jh.system.service.IOpLoginLogsService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OpLoginLogsServiceImpl implements IOpLoginLogsService {

    @Autowired
    private IOpLoginLogsMapper opLoginLogsMapper;

    @Autowired
    private IPermPersonMapper permPersonMapper;

    @Override
    public ResultMessage recordLogin(OpLoginLogs opLoginLogs) {
        opLoginLogsMapper.insertSelective(opLoginLogs);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage queryLoginNum(Integer accountId) {
        //查询当前登陆人是否是付费用户，如果为付费用户则不需要在第一次弹出提示
        int loginNum = opLoginLogsMapper.queryLoginNum(accountId);
        return ResultMessage.success(loginNum);
    }
}
