package com.jh.system.controller;

import com.github.pagehelper.PageInfo;
import com.jh.system.base.controller.BaseController;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.PermAccount;
import com.jh.system.entity.PermResource;
import com.jh.system.model.ResourceParam;
import com.jh.system.service.IPermResourceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *  @description: 系统资源管理
 *  @version <1> 2017-01-29 cxj： Created.
 */
@RestController
@RequestMapping("/resource")
public class PermResourceController extends BaseController {
    @Autowired
    private IPermResourceService permResourceService;

    @ApiOperation(value = "获取所有菜单",notes = "获取所有菜单，若角色不为空，则标记已关联的菜单权限")
    @ApiImplicitParam(name = "roleId",value = "角色ID",required = false,paramType = "query", dataType = "Integer")
    @PostMapping("/findAll")
    public ResultMessage findAll(HttpServletRequest request, Integer roleId){
        return permResourceService.findAll(roleId);
    }

    /**
     * 查询所有的资源
     * @return
     * @version <1> 2018-04-03 xzg： Created.
     */
    @ApiOperation(value = "查询所有的资源",notes = "查询所有的资源")
    @PostMapping("/findAllResource")
    public ResultMessage findAllResource(){
        return permResourceService.findAllResource();
    }

    /**
     * 查询所有的资源
     * @return
     * @version <1> 2018-05-29 lxy： Created.
     */
    @ApiOperation(value = "根据父节点去查询所有的子节点",notes = "查询所有的资源")
    @PostMapping("/findResourceByPid")
    public ResultMessage findResourceByPid(@RequestBody Map<String,Object> param){
        Object resId = param.get("resId");
        if(resId != null){
            return permResourceService.findResourceByParentId(new Integer(resId.toString()));
        }else{
            return ResultMessage.fail();
        }

    }



    /**
     * @description: 资源分页查询
     * @param request
     * @param resourceParam
     * @return
     * @version <1> 2018-02-02 cxj： Created.
     */
    @ApiOperation(value = "资源分页查询",notes = "分页查询系统资源信息")
    @ApiImplicitParam(name = "resourceParam",value = "系统资源查询参数",required = false,dataType = "ResourceParam")
    @PostMapping("/findByPage")
    public PageInfo<PermResource> findByPage(HttpServletRequest request, ResourceParam resourceParam){
        return permResourceService.findByPage(resourceParam);
    }

    /**
     * @description: 检索资源编码是否存在
     * 1.检索成功，则表示角色编码已存在，返回true
     * 2.检索为空，则表示角色编码不存在，返回false
     * @param resourceParam
     * @return
     * @version <1> 2018-02-02 cxj : created.
     */
    @ApiOperation(value="校验资源编码是否已存在", notes="校验资源编码是否已存在")
    @ApiImplicitParam(name = "resourceParam",value = "资源编码",required = true, paramType = "query", dataType = "ResourceParam")
    @PostMapping("/checkResCode")
    public ResultMessage checkResCode(@RequestBody ResourceParam resourceParam){
        return permResourceService.checkResCode(resourceParam);
    }

    /**
     * @description: 资源新增
     * @param request
     * @param permResource
     * @return
     * @version <1> 2018-02-02 cxj : created.
     */
    @ApiOperation(value = "资源新增",notes = "新增系统资源")
    @ApiImplicitParam(name = "permResource",value = "系统资源实体",required = true,dataType = "PermResource")
    @PostMapping("/add")
    public ResultMessage add(HttpServletRequest request, @RequestBody PermResource permResource){
        PermAccount permAccount = getCurrentPermAccount();
        if(permAccount != null){
            permResource.setCreator(permAccount.getAccountId());
            permResource.setCreatorName(permAccount.getAccountName());
        }

        return  permResourceService.saveResource(permResource);
    }

    /**
     * @description: 根据资源ID查询资源实体信息
     * @param request
     * @param resId
     * @return
     * @version <1> 2018-02-02 cxj : created.
     */
    @ApiOperation(value = "资源查询",notes = "按资源主键查询角色")
    @ApiImplicitParam(name = "resId",value = "系统资源主键",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/getById")
    public ResultMessage getdById(HttpServletRequest request, @RequestParam Integer resId){
        return permResourceService.findById(resId);
    }

    /**
     * @description: 资源删除
     * @param request
     * @param resId
     * @return
     * @version <1> 2018-02-02 cxj : created.
     */
    @ApiOperation(value = "资源删除",notes = "删除系统资源")
    @ApiImplicitParam(name = "resId",value = "系统资源主键",required = true, paramType = "query", dataType = "Integer")
    @PostMapping("/delete")
    public ResultMessage delete(HttpServletRequest request, @RequestParam Integer resId){
        PermResource permResource = new PermResource();
        permResource.setResId(resId);
        return permResourceService.delete(permResource);
    }

    /**
     * 资源修改
     * @param request
     * @param permResource
     * @return
     * @version <1> 2018-02-07 cxj： Created.
     */
    @ApiOperation(value = "资源修改",notes = "修改资源角色")
    @ApiImplicitParam(name = "permResource",value = "系统资源实体",required = true,dataType = "PermResource")
    @PostMapping("/edit")
    public ResultMessage edit(HttpServletRequest request, @RequestBody PermResource permResource){
        PermAccount permAccount = getCurrentPermAccount();
        if(permAccount != null){
            permResource.setModifier(permAccount.getAccountId());
            permResource.setModifierName(permAccount.getAccountName());
        }
        return permResourceService.updateResource(permResource);
    }

    @ApiOperation(value = "获取菜单",notes = "获取当前用户拥有的权限菜单")
    @ApiImplicitParam(name = "accountId",value = "用户ID",required = false,paramType = "query", dataType = "Integer")
    @PostMapping("/findMenu")
    public ResultMessage findMenu(Integer roleType){
        PermAccount permAccount = getCurrentPermAccount(); //获取当前登录人信息

        return permResourceService.findMenu(permAccount.getAccountId(),roleType);
    }

    @ApiOperation(value = "按钮权限",notes = "查询当前用户是否拥有按钮权限")
    @ApiImplicitParam(name = "permResource",value = "系统资源实体",required = true,dataType = "PermResource")
    @PostMapping("/findButton")
    public ResultMessage findButton(HttpServletRequest request,@RequestBody PermResource permResource){
        PermAccount permAccount = getCurrentPermAccount(); //获取当前登录人信息
        return permResourceService.findButton(permAccount.getAccountId(),permResource.getResCode());
    }

    /**
     * @description: 检索该资源是否存在子节点
     * 1.检索成功，则表示存在子节点，返回true
     * 2.检索为空，则表示不存在子节点，返回false
     * @param resourceParam
     * @return
     * @version <1> 2018-04-08 wl : created.
     */
    @ApiOperation(value="校验该资源菜单是否存在子节点", notes="校验该资源菜单是否存在子节点")
    @ApiImplicitParam(name = "parentId",value = "资源Id",required = true, paramType = "query", dataType = "Integer")
    @PostMapping("/checkResleaf")
    public ResultMessage checkResleaf(@RequestBody ResourceParam resourceParam){
        return permResourceService.checkResleaf(resourceParam.getParentId());
    }

}
