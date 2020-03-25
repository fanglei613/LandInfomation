package com.jh.system.controller;

import com.github.pagehelper.PageInfo;
import com.jh.system.base.controller.BaseController;
import com.jh.system.entity.PermAccount;
import com.jh.system.entity.PermPerson;
import com.jh.system.model.LoginParam;
import com.jh.system.model.PersonParam;
import com.jh.system.service.IPermAccountService;
import com.jh.system.service.IPermPersonService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *  @description: 用户管理
 *  @version <1> 2017-01-26 cxj： Created.
 */
@RestController
@RequestMapping("/person")
public class PermPersonController extends BaseController {
    @Autowired
    private IPermPersonService permPersonService;
    @Autowired
    private IPermAccountService permAccountService;

    /**
     *  @description: 用户信息分页查询
     *  @param request
     *  @param personParam 用户基本信息参数
     *  @return
     *  @version <1> 2018-01-24 cxj： Created.
     */
    @ApiOperation(value = "用户分页查询",notes = "分页查询系统用户信息")
    @ApiImplicitParam(name = "personParam",value = "系统用户查询参数",required = false,dataType = "PersonParam")
    @PostMapping("/findByPage")
    public PageInfo<PermPerson> findByPage(HttpServletRequest request, PersonParam personParam){
        return permPersonService.findByPage(personParam);
    }

    /**
     *  @description: 校验账号是否已经存在
     *  若为true，则表示表中已存在，不可重复添加
     *  若为false，则表示表中不存在，可以添加
     *  @param loginParam 登陆参数
     *  @return
     *  @version <1> 2018-01-24 cxj : created
     */
    @ApiOperation(value="校验用户账号是否已存在", notes="校验用户账号是否已存在,只需输入accountName即可")
    @ApiImplicitParam(name = "loginParam",value = "参数对象",required = true, dataType = "LoginParam")
    @PostMapping("/checkAccountName")
    public ResultMessage checkAccountName(@RequestBody LoginParam loginParam){
        return permAccountService.checkAccountName(loginParam);
    }

    /**
     *  @description: 用户新增
     *  @param request
     *  @param personParam 用户基本信息参数
     *  @return
     *  @version <1> 2018-01-25 cxj： Created.
     */
    @ApiOperation(value = "用户新增",notes = "新增系统用户")
    @ApiImplicitParam(name = "personParam",value = "系统用户实体",required = true,dataType = "PersonParam")
    @PostMapping("/add")
    public ResultMessage add(HttpServletRequest request, @RequestBody PersonParam personParam){
        PermAccount permAccount = getCurrentPermAccount();
        PermPerson permPerson = personParam.getPermPerson();
        if(permAccount != null){
            permPerson.setAccountId(permAccount.getAccountId());
            permPerson.setCreator(permAccount.getAccountId());
            permPerson.setCreatorName(permAccount.getAccountName());
        }

        return  permPersonService.savePersonAndAccount(personParam);
    }

    /**
     *  @description: 根据ID查询用户信息
     *  @param personId 用户基本信息主键
     *  @return
     *  @version <1> 2018-01-25 cxj： Created.
     */
    @ApiOperation(value = "用户查询",notes = "按用户主键查询用户")
    @ApiImplicitParam(name = "personId",value = "系统用户主键",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/getById")
    public ResultMessage getdById(@RequestParam Integer personId){
        return permPersonService.findPersonAndAccount(personId);
    }

    /**
     *  @description: 用户修改
     *  @param request
     *  @param personParam 用户基本信息参数
     *  @return
     *  @version <1> 2018-01-25 cxj： Created.
     */
    @ApiOperation(value = "用户修改",notes = "修改系统用户")
    @ApiImplicitParam(name = "personParam",value = "系统用户实体",required = true,dataType = "PersonParam")
    @PostMapping("/edit")
    public ResultMessage edit(HttpServletRequest request, @RequestBody PersonParam personParam){
        PermAccount permAccount = getCurrentPermAccount();
        PermPerson permPerson = personParam.getPermPerson();
        if(permAccount != null){
            permPerson.setAccountId(permAccount.getAccountId());
            permPerson.setCreator(permAccount.getAccountId());
            permPerson.setCreatorName(permAccount.getAccountName());
        }

        return permPersonService.save(personParam);
    }

    /**
     *  @description: 禁用/启用用户
     *  @param request
     *  @param personParam 用户基本信息参数
     *  @return
     *  @version <1> 2018-01-25 cxj： Created.
     */
    @ApiOperation(value = "禁用/启用用户",notes = "禁用/启用用户")
    @ApiImplicitParam(name = "personParam",value = "用户参数",required = false,dataType = "PersonParam")
    @PostMapping("/setPersonDataStatus")
    public ResultMessage setPersonDataStatus(HttpServletRequest request , @RequestBody PersonParam personParam){
        PermAccount permAccount = getCurrentPermAccount();
        PermPerson permPerson = personParam.getPermPerson();
        if(permAccount != null){
            permPerson.setModifier(permAccount.getAccountId());
            permPerson.setModifierName(permAccount.getAccountName());
        }
        return permPersonService.updatePersonDataStatus(permPerson);
    }

    /**
     * @description: 用户关联角色
     * @param request
     * @param personParam 用户基本信息参数
     * @return
     */
    @ApiOperation(value = "关联角色",notes = "关联角色")
    @ApiImplicitParam(name = "personParam",value = "用户参数",required = false,dataType = "PersonParam")
    @PostMapping("/relateRole")
    public ResultMessage relateRole(HttpServletRequest request,@RequestBody PersonParam personParam){
        return permPersonService.saveRelateRole(personParam);
    }

    @ApiOperation(value = "当前用户信息")
    @PostMapping("/findCurrentPerson")
    public ResultMessage findCurrentPerson(HttpServletRequest request){
        PermAccount permAccount = getCurrentPermAccount();
        return permPersonService.findPersonByAccountId(permAccount.getAccountId());
    }

    /**
     * @description: 查询子字典数据
     * @version <1> 2018-05-18 cxj： Created.
     */
    @ApiOperation(value="查询系统内部用户",notes="查询系统内部用户")
    @PostMapping(value = "queryPerson")
    public ResultMessage queryPerson(){
        return permPersonService.queryPerson();
    }

    /**
     *  @description: 用户新增 ，用于feign调用
     *  @param voMap 用户基本信息参数
     *  @return
     *  @version <1> 2018-01-25 cxj： Created.
     */
    @ApiOperation(value = "用户新增",notes = "新增系统用户")
    @ApiImplicitParam(name = "voMap",value = "系统用户实体",required = true,dataType = "Map")
    @PostMapping("/addUser")
    public ResultMessage addUser(@RequestBody Map<String,Object> voMap){
        PersonParam p = generateParam(voMap);
        return  permPersonService.savePersonAndAccount(p);
    }

    /**
     *  @description: 根据手机号获取AccountId，用于feign调用
     *  @param accountName 手机号
     *  @return
     *  @version <1> 2019-01-25 lijie： Created.
     */
    @ApiOperation(value = "根据手机号获取AccountId",notes = "修改系统用户")
    @ApiImplicitParam(name = "accountName",value = "手机号",required = true,dataType = "String")
    @PostMapping("/getAccountIdByPhone")
    public ResultMessage getAccountIdByPhone(String accountName){
        Integer accountId=null;
        ResultMessage result= permAccountService.queryPermAccountByAccountName(accountName);
        if(result.isFlag()){
            PermAccount account=(PermAccount)result.getData();
            if(account!=null){
                accountId=account.getAccountId();
            }
        }
        return ResultMessage.success(accountId);
    }

    /**
     *  @description: 用户修改 ，用于feign调用
     *  @param voMap 用户基本信息参数
     *  @return
     *  @version <1> 2019/3/26 mason:Created.
     */
    @ApiOperation(value = "用户修改",notes = "修改系统用户")
    @ApiImplicitParam(name = "voMap",value = "系统用户实体",required = true,dataType = "Map")
    @PostMapping("/editUser")
    public ResultMessage editUser(@RequestBody Map<String,Object> voMap){
        PersonParam p = generateParam(voMap);
        return  permPersonService.save(p);
    }

    /**
     * 构建新增或修改用户参数
     * @param
     * @return
     * @version <1> 2019/3/26 mason:Created.
     */
    private PersonParam generateParam(Map<String,Object> voMap){
        PersonParam p=new PersonParam();
        PermPerson per=new PermPerson();
        PermAccount acc=new PermAccount();
        try {
            BeanUtils.populate(p,voMap);
            BeanUtils.populate(per,voMap);
            BeanUtils.populate(acc,voMap);
        } catch (Exception e) {

        }
        p.setPermPerson(per);
        p.setPermAccount(acc);
        return p;
    }

    /**
     * 更换用户头像
     * @param request 用户信息
     * @return
     * @version <1> 2019/3/28 mason:Created.
     */
    @ApiOperation(value = "更换用户头像",notes = "更换用户头像")
    @ApiImplicitParam(name = "request",value = "用户实体",required = false,dataType = "HttpServletRequest")
    @PostMapping("/editPersonPhoto")
    public ResultMessage editPersonPhoto(HttpServletRequest request){
        PermPerson person = getPermPerson(request);
        return permPersonService.editPersonPhoto(request,person);
    }

    /**
     * 从请求信息中获取person信息
     * @param
     * @return
     * @version <1> 2019/3/28 mason:Created.
     */
    private PermPerson getPermPerson(HttpServletRequest request){
        PermPerson person = new PermPerson();
        if (StringUtils.isNotBlank(request.getParameter("accountId"))){
            person.setAccountId(Integer.parseInt(request.getParameter("accountId")));
        }
        return person;
    }


    /**
     *  @description: 根据用户id集合，查询用户信息列表
     *  用于查询我的关注用户列表信息
     *  @param accIds 用户id集合
     *  @return
     *  @version <1> 2019-04-19 zhangshen： Created.
     */
    @ApiOperation(value = "根据用户id集合，查询用户信息列表",notes = "根据用户id集合，查询用户信息列表")
    @ApiImplicitParam(name = "accIds",value = "用户id集合",required = true,dataType = "List")
    @PostMapping("/findPermPersonListByAccIds")
    public ResultMessage findPermPersonListByAccIds(@RequestBody List<Integer> accIds){
        return permPersonService.findPermPersonListByAccIds(accIds);
    }

    /**
     * 手机端更换用户头像
     * @param request 用户信息
     * @return
     * @version <1> 2019/4/23 mason:Created.
     */
    @RequestMapping(value="/editPersonPhotoApp", method = {RequestMethod.POST})
    public ResultMessage editPersonPhotoApp(@RequestParam String phone, HttpServletRequest request){
        PermPerson person = new PermPerson();
        person.setAccountId(getCurrentAccountIdApp(phone));
        return permPersonService.editPersonPhoto(request,person);
    }


    /**
     * 根据账号ID获取个人信息
     * @param accountId
     * @return
     */
    @GetMapping("/queryPersonByAccountId")
    public ResultMessage queryPersonByAccountId(Integer accountId){

        ResultMessage result = permPersonService.findPersonByAccountId(accountId);
        return result;

    }


    /**
     *  @description: app用户修改
     *  @param voMap 用户基本信息参数
     *  @return
     *  @version <1> 2019/5/13 mason:Created.
     */
    @ApiOperation(value = "用户修改",notes = "修改系统用户")
    @ApiImplicitParam(name = "voMap",value = "系统用户实体",required = true,dataType = "Map")
    @PostMapping("/appEditUser")
    public ResultMessage appEditUser(@RequestBody Map<String,Object> voMap,@RequestParam String phone){
        PersonParam p = generateParam(voMap);
        return  permPersonService.save(p);
    }

}
