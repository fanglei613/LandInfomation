package com.jh.system.controller;

import com.github.pagehelper.PageInfo;
import com.jh.system.base.controller.BaseController;
import com.jh.system.entity.Logs;
import com.jh.system.model.LogParam;
import com.jh.system.service.ILogsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志管理
 * @version <1> 2017-03-12 xzg： Created.
 */
@RestController
@RequestMapping("/logs")
public class LogController extends BaseController{

    @Autowired
    private ILogsService logsService;

    /**
     * 日志分页查询
     * @param logParam
     * @return
     * @version <1> 2018-03-12 xzg： Created.
     */
    @ApiOperation(value = "日志分页查询",notes = "分页查询日志信息")
    @ApiImplicitParam(name = "logParam",value = "日志查询条件",required = true,dataType = "LogParam")
    @PostMapping("/findByPage")
    public PageInfo<Logs> findByPage(LogParam logParam){
        return logsService.findByPage(logParam);
    }

}
