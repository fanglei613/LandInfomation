package com.jh.system.controller;

import com.jh.system.base.controller.BaseController;
import com.jh.system.entity.OpLoginLogs;
import com.jh.system.service.IOpLoginLogsService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 * 功能描述:
 * @Param:
 * @Return:
 * @version<1>  2019/11/14  wangli :Created
 */
@Api(value = "登录记录")
@RestController
@RequestMapping("/loginLogs")
public class OpLoginLogsController extends BaseController {

    @Autowired
    private IOpLoginLogsService opLoginLogsService;

    /*
     * 功能描述: 查询用户登录次数
     * @Param:
     * @Return:
     * @version<1>  2019/11/14  wangli :Created
     */
    @ApiOperation(value="查询用户当前登录次数", notes="查询用户当前登录次数")
    @ApiImplicitParam(name = "accountId",value = "当前登录人id",required = true, dataType = "Integer")
    @PostMapping("/queryLoginNum")
    public ResultMessage queryLoginNum(@RequestParam Integer accountId){
        OpLoginLogs opLoginLogs = new OpLoginLogs();
        opLoginLogs.setOperator(accountId);
        opLoginLogs.setOpContent("用户登录");
        opLoginLogsService.recordLogin(opLoginLogs);
        return opLoginLogsService.queryLoginNum(accountId);
    }
}
