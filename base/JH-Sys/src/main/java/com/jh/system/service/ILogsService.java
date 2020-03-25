package com.jh.system.service;

import com.github.pagehelper.PageInfo;
import com.jh.system.base.service.IBaseService;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.Logs;
import com.jh.system.model.LogParam;

import javax.servlet.http.HttpServletRequest;

/**
 * description:日志信息管理
 *
 * @version <1> 2018-01-25 lcw： Created.
 */
public interface ILogsService extends IBaseService<LogParam,Logs,Integer>{


    /**
     * 插入日志消息（将各业务的插入日志的方法统一成一个方法）
     * @param logs
     * @return
     * @version<1> 2018-04-12 lcw :Created.
     */
    public ResultMessage addLog(Logs logs);

    /**
     * 新增日志信息
     * @param request
     * @param logs
     */
    public ResultMessage addLog(HttpServletRequest request, Logs logs);


    /**
     * 查询日志信息
     * @param logParam
     * @return
     */
    public PageInfo<Logs> findByPage(LogParam logParam);


}
