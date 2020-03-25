package com.jh.system.controller;

import com.jh.system.base.controller.BaseController;
import com.jh.vo.ResultMessage;
import com.jh.util.Base64Util;
import com.jh.constant.ConstantUtil;
import com.jh.enums.IsEnum;
import com.jh.util.MD5Util;
import com.jh.system.Enum.UserEnum;
import com.jh.system.entity.PermAccount;
import com.jh.system.model.UserParam;
import com.jh.system.model.UserPwdParam;
import com.jh.system.service.IPermAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @description: 用户账户操作接口
 * @version <1> 2018-01-11 cxj： Created.
 */
@Api(value = "用户账户操作接口")
@RestController
@RequestMapping("/permAccount")
public class PermAccountController extends BaseController {
    @Autowired
    private IPermAccountService permAccountService;

    /**
     * @description: 分页查询用户账户数据
     * @param userParam 用户参数对象
     * @version <1> 2018-01-11 cxj： Created.
     */
//    @ApiOperation(value="分页查询用户账户数据",notes="分页查询用户账户数据")
//    @ApiImplicitParam(name = "userParam",value = "用户账户参数对象",required = true, dataType = "UserParam")
//    @PostMapping(value = "/queryPermAccountList")
//    public ResultMessage queryPermAccountList(@RequestBody UserParam userParam){
//        return permAccountService.queryPermAccountList(userParam);
//    }



    /**
     * 密码修改
     * @param request   请求对象
     * @version <1> 2018/1/23 djh： Created.
     */
    @ApiOperation(value="密码修改",notes="密码修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request",value = "request请求对象",required = true, dataType = "HttpServletRequest"),
    })
    @PostMapping("/updatePwd")
    public
    @ResponseBody
    ResultMessage updatePwd(HttpServletRequest request, @RequestBody UserPwdParam pwdParam,Model model) {
        Map<String,Object> map = model.asMap();
        Integer accountId = pwdParam.getAccountId();//账户编号
        String oldPass = pwdParam.getOldPass();//旧密码
        String newPass = pwdParam.getNewPass();//新密码
        String truePass = pwdParam.getTruePass();//确认新密码
        ResultMessage result = new ResultMessage();
        if (oldPass == null || StringUtils.isEmpty(oldPass)) {
            result.setCode(IsEnum.Error.getValue());
            result.setMsg("请输入原密码");
            return result;
        }

        //判断原密码是否正确
        PermAccount ua = permAccountService.queryPermAccountByAccountId(new Integer(accountId));
        oldPass = new String(Base64Util.decode(oldPass));
        oldPass = MD5Util.digest(oldPass);
        String backOldPwd = ua.getAccountPwd();
        if(!oldPass.equals(backOldPwd)){
            result.setCode(IsEnum.Error.getValue());
            result.setMsg("输入的原密码不正确");
            return result;
        }
        if (newPass == null || StringUtils.isEmpty(newPass)) {
            result.setCode(IsEnum.Error.getValue());
            result.setMsg("请输入新密码");
            return result;
        }

        if (truePass == null || StringUtils.isEmpty(truePass)) {
            result.setCode(IsEnum.Error.getValue());
            result.setMsg("请输入确认新密码");
            return result;
        }
        if (!newPass.equals(truePass)) {
            result.setCode(IsEnum.Error.getValue());
            result.setMsg("两次输入密码不正确");
            return result;
        }

        //设置为新密码
        ua.setAccountPwd(truePass);
        result = permAccountService.updatePermAccount(ua);
        return result;
    }

    /**
     * Description: 根据accountId重置密码
     * @param pwdParam
     * @return
     * @version <1> 2018/8/3 16:42 zhangshen: Created.
     */
    @PostMapping("/resetUserPwd")
    public ResultMessage resetUserPwd(@RequestBody UserPwdParam pwdParam) {
        return permAccountService.resetUserPwd(pwdParam);
    }

    /**
     * 检查账号名(accountCode)是否已注册
     * @param accountId
     * @param accountName
     * @return
     * @version <1> 2018-08-06 zhangshen： Created.
     */
    @ApiOperation(value="checkAccountCodeExists",notes = "检查accountCode是否存在")
    @GetMapping("/checkAccountCodeExists")
    public ResultMessage checkAccountCodeExists(Integer accountId, String accountName)  {
        return permAccountService.checkAccountCodeExists(accountId, accountName);
    }
}