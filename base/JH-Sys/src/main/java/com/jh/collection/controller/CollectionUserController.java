package com.jh.collection.controller;

import com.jh.collection.entity.CollectionUser;
import com.jh.collection.service.ICollectionUserService;
import com.jh.system.service.IUserService;
import com.jh.util.JsonUtils;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.jh.constant.CommonDefineEnum.REQUEST_FAIL;

/**
 * 移动采集用户接口
 * @version <1> 2018-11-23 xzg：Created
 */
@Api(value = "CollectionUser",description="对接采集用户相关接口")
@RestController
@RequestMapping("/nolog/collection")
public class CollectionUserController {

    @Autowired
    private IUserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(CollectionUserController.class);

    @Autowired
    private ICollectionUserService collectionUserServiceImpl;

    /**
     * 采集用户注册
     * @return
     */
    @ApiOperation(value = "采集用户注册")
    @ApiImplicitParam(name = "collectionUser",value = "采集用户注册参数",required = false,dataType = "CollectionUser")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResultMessage register(@RequestBody CollectionUser collectionUser){
        ResultMessage register = ResultMessage.success();
        try {
            register = collectionUserServiceImpl.register(collectionUser);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(">>>system exception e:{}",e.getMessage());
            register = ResultMessage.fail(REQUEST_FAIL.getMesasge());
            return register;
        }
        return register;
    }
    /**
     * 采集用户登录
     * @return
     */
    @ApiOperation(value = "采集用户登录")
    @ApiImplicitParam(name = "collectionUser",value = "采集用户注册参数",required = false,dataType = "CollectionUser")
    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public ResultMessage doLogin(@RequestBody CollectionUser collectionUser) {
        return collectionUserServiceImpl.login(collectionUser);
    }
    /**
     * 采集用户退出登录
     * @return
     */
    @ApiOperation(value = "采集用户退出登录")
    @RequestMapping(value = "/loginOut",method = RequestMethod.POST)
    public ResultMessage loginOut(@RequestBody String sessionId){
        return collectionUserServiceImpl.loginOut(sessionId);
    }

    /**
     * 重置密码
     * @param collectionUser
     * @return
     */
    @ApiOperation(value = "重置密码")
    @ApiImplicitParam(name = "collectionUser",value = "重置密码参数",required = false,dataType = "CollectionUser")
    @RequestMapping(value = "/resetPwd",method = RequestMethod.POST)
    public ResultMessage resetPwd(@RequestBody CollectionUser collectionUser){
        return collectionUserServiceImpl.resetPwd(collectionUser);
    }
    /**
     * 解绑手机(更换手机号码)
     * @param collectionUser
     * @return
     */
    @ApiOperation(value = "解绑手机(更换手机号码)")
    @ApiImplicitParam(name = "collectionUser",value = "重置密码参数",required = false,dataType = "CollectionUser")
    @RequestMapping(value = "/resetPhone",method = RequestMethod.POST)
    public ResultMessage resetPhone(@RequestBody CollectionUser collectionUser){
        return collectionUserServiceImpl.resetPhone(collectionUser);
    }
    /**
     * 采集用户找回密码
     * @param collectionUser
     * @return
     */
    @ApiOperation(value = "采集用户找回密码")
    @ApiImplicitParam(name = "collectionUser",value = "采集用户注册参数",required = false,dataType = "CollectionUser")
    @RequestMapping(value = "/findPwd",method = RequestMethod.POST)
    public ResultMessage findPwd(@RequestBody CollectionUser collectionUser){
        return collectionUserServiceImpl.findPwd(collectionUser);
    }

    /**
     * 登录检查
     * @param sessionId
     * @return
     */
    @ApiOperation(value = "登录检查")
    @RequestMapping(value = "/sessionId",method = RequestMethod.POST)
    public ResultMessage loginCheck(String sessionId){
        return collectionUserServiceImpl.loginCheck(sessionId);
    }

    /**
     * 关联微信号
     * @param collectionUser
     * @return
     */
    @ApiOperation(value = "")
    @ApiImplicitParam(name = "collectionUser",value = "采集用户注册参数",required = false,dataType = "CollectionUser")
    @RequestMapping(value = "/relateWx",method = RequestMethod.POST)
    public ResultMessage relateWx(@RequestBody @Valid CollectionUser collectionUser){
        return collectionUserServiceImpl.relateWx(collectionUser);
    }

    /**
     * 微信登录
     * @param collectionUser
     * @return
     */
    @ApiOperation(value = "微信登录")
    @ApiImplicitParam(name = "collectionUser",value = "采集用户注册参数",required = false,dataType = "CollectionUser")
    @RequestMapping(value = "/wxLogin",method = RequestMethod.POST)
    public ResultMessage wxLogin(@RequestBody @Valid CollectionUser collectionUser){
        return collectionUserServiceImpl.relateWx(collectionUser);
    }

    /**
     * 采集用户注册，短信发送
     * @version <1> 2018-11-26 xy： Created.
     */
    @ApiOperation(value="发短信接口",notes = "为手机号获取短信验证码")
    @GetMapping("/findSmsValidCode")
    public ResultMessage sendWechatSmsValidCode(String phone) {
        LOG.info(">>>>>request phone:{}",phone);
        ResultMessage resultMessage = userService.sendWechatSmsValidCode(phone);
        LOG.info(">>>>>response resultMessage:{}", JsonUtils.objectToJson(resultMessage));
        return resultMessage;
    }

    /**
     * 修改密码
     * @param collectionUser
     * @return
     */
    @ApiOperation(value = "修改密码")
    @ApiImplicitParam(name = "collectionUser",value = "修改密码参数",required = false,dataType = "CollectionUser")
    @RequestMapping(value = "/modifyPwd",method = RequestMethod.POST)
    public ResultMessage modifyPwd(@RequestBody CollectionUser collectionUser){
        return collectionUserServiceImpl.modifyPwd(collectionUser);
    }

}
