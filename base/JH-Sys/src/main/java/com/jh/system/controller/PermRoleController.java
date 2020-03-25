package com.jh.system.controller;

import com.github.pagehelper.PageInfo;
import com.jh.system.base.controller.BaseController;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.PermAccount;
import com.jh.system.entity.PermRole;
import com.jh.system.model.RoleParam;
import com.jh.system.service.IPermRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *  @description: 角色管理
 *  @version <1> 2018-01-25 cxj： Created.
 */
@Api(value = "角色管理")
@RestController
@RequestMapping("/role")
public class PermRoleController extends BaseController {
    @Autowired
    private IPermRoleService permRoleService;

    /**
     *  @description: 获取所有角色信息, 若参数中存在accountId , 则关联查询用户与角色关联表
     *  @param request
     *  @return
     *  @version <1> 2018-01-24 cxj： Created.
     */
    @ApiOperation(value = "获取所有角色",notes = "获取所有角色")
    @ApiImplicitParam(name = "accountId",value = "账号ID",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/findAll")
    public ResultMessage findAll(HttpServletRequest request, @RequestParam Integer accountId){
        List<PermRole> roles = getRoles(request); //当前登陆人角色

        return permRoleService.findAll(accountId);
    }

    /**
     * @description: 角色分页查询
     * @param request
     * @param roleParam 角色参数
     * @return
     * @version <1> 2018-01-26 cxj： Created.
     */
    @ApiOperation(value = "角色分页查询",notes = "分页查询系统角色信息")
    @ApiImplicitParam(name = "roleParam",value = "系统角色查询参数",required = false,dataType = "RoleParam")
    @GetMapping("/findByPage")
    public PageInfo<PermRole> findByPage(HttpServletRequest request, RoleParam roleParam){
        return permRoleService.findByPage(roleParam);
    }

    /**
     * @description: 角色新增
     * @param request
     * @param permRole
     * @return
     * @version <1> 2018-01-26 cxj： Created.
     */
    @ApiOperation(value = "角色新增",notes = "新增系统角色")
    @ApiImplicitParam(name = "permRole",value = "系统角色实体",required = true,dataType = "PermRole")
    @PostMapping("/add")
    public ResultMessage add(HttpServletRequest request, @RequestBody PermRole permRole){
        PermAccount permAccount = getCurrentPermAccount();
        if(permAccount != null){
            permRole.setCreator(permAccount.getAccountId());
            permRole.setCreatorName(permAccount.getAccountName());
        }
        return  permRoleService.save(permRole);
    }

    /**
     * @description: 检索角色编码是否存在
     * 1.检索成功，则表示角色编码已存在，返回true
     * 2.检索为空，则表示角色编码不存在，返回false
     * @param permRole
     * @return
     * @version <1> 2018-01-26 cxj : created.
     */
    @ApiOperation(value="校验角色编码是否已存在", notes="校验角色编码是否已存在，只需要传roleCode参数即可")
    @ApiImplicitParam(name = "permRole",value = "角色对象",required = true, paramType = "query", dataType = "PermRole")
    @PostMapping("/checkRoleCode")
    public ResultMessage checkRoleCode(@RequestBody PermRole permRole){
        return permRoleService.checkRoleCode(permRole.getRoleCode());
    }

    /**
     * @description: 根据角色ID查询角色实体信息
     * @param request
     * @param roleId 角色主键
     * @return
     * @version <1> 2018-01-29 cxj： Created.
     */
    @ApiOperation(value = "角色查询",notes = "按角色主键查询角色")
    @ApiImplicitParam(name = "roleId",value = "系统角色主键",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/getById")
    public ResultMessage getById(HttpServletRequest request, @RequestParam Integer roleId){
        return permRoleService.getById(roleId);
    }

    /**
     * @description: 角色修改
     * @param request
     * @param permRole 角色对象
     * @return
     * @version <1> 2018-01-29 cxj： Created.
     */
    @ApiOperation(value = "角色修改",notes = "修改系统角色")
    @ApiImplicitParam(name = "permRole",value = "系统角色实体",required = true,dataType = "PermRole")
    @PostMapping("/edit")
    public ResultMessage edit(HttpServletRequest request, @RequestBody PermRole permRole){
        PermAccount permAccount = getCurrentPermAccount();
        if(permAccount != null){
            permRole.setModifier(permAccount.getAccountId());
            permRole.setModifierName(permAccount.getAccountName());
        }

        return permRoleService.update(permRole);
    }

    /**
     * @description: 角色和资源关联
     * @param request
     * @param roleParam
     * @return
     * @version <1> 2018-02-05 cxj： Created.
     */
    @ApiOperation(value = "角色和资源关联",notes = "角色和资源关联")
    @ApiImplicitParam(name = "roleParam",value = "角色参数",required = true, dataType = "RoleParam")
    @PostMapping("/relateResource")
    public ResultMessage relateResource(HttpServletRequest request, @RequestBody RoleParam roleParam){
        ResultMessage result  = permRoleService.saveRelateResource(roleParam);
        return result;
    }


    @PostMapping("/getDefaultRoleId")
    public ResultMessage getDefaultRoleId(){

        Object defaultRoleId = (PropertyUtil.getPropertiesForConfig("DEFAULT_PERMISSION_ID") == null ? 3 : PropertyUtil.getPropertiesForConfig("DEFAULT_PERMISSION_ID"));
        return ResultMessage.success(defaultRoleId);
    }

    /**
     *  @description: 是否存在某角色
     *  @param accountId
     *  @return
     *  @version <1> 2018-01-24 cxj： Created.
     */
    @ApiOperation(value = "是否存在某角色",notes = "是否存在某角色")
    @ApiImplicitParam(name = "accountId",value = "账号ID",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/isExistRole")
    public ResultMessage isExistRole(@RequestParam Integer accountId,@RequestParam String roleCode){
        return permRoleService.isExistRole(accountId,roleCode);
    }

}
