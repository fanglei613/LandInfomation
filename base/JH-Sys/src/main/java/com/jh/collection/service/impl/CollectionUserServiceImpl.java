package com.jh.collection.service.impl;

import com.cloopen.rest.sdk.utils.encoder.BASE64Encoder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.collection.controller.CollectionUserController;
import com.jh.collection.entity.CollectionUser;
import com.jh.collection.feign.ICollectionService;
import com.jh.collection.mapping.CollectionUserMapper;
import com.jh.collection.service.ICollectionUserService;
import com.jh.collection.utils.SsoLoginStore;
import com.jh.collection.utils.SsoSessionIdHelper;
import com.jh.collection.utils.SsoTokenLoginHelper;
import com.jh.constant.SysConstant;
import com.jh.enums.ImageValidateCodeEnum;
import com.jh.system.Enum.PersonTypeEnum;
import com.jh.system.entity.PermAccount;
import com.jh.system.mapping.IPermAccountMapper;
import com.jh.system.model.RegisterEntity;
import com.jh.system.service.IUserService;
import com.jh.util.JsonUtils;
import com.jh.util.MD5Util;
import com.jh.util.RedisUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.jh.constant.CommonDefineEnum.FIND_OBJECT_NOT_EXISTS;

/**
 * 用户采集任务Service
 * @version <1> 2018-11-23 xy: Created.
 */
@Service
public class CollectionUserServiceImpl implements ICollectionUserService {

    private static final Logger LOG = LoggerFactory.getLogger(CollectionUserController.class);

    @Autowired
    private CollectionUserMapper collectionUserMapper;

    @Autowired
    private ICollectionService collectionService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IPermAccountMapper permAccountMapper;

    @Override
    public PageInfo<CollectionUser> findByPage(CollectionUser UserQuery) {
        PageHelper.startPage(UserQuery.getPage(), UserQuery.getRows());
        List<CollectionUser> list = collectionUserMapper.findCollectionUserByPage(UserQuery);
        return new PageInfo<CollectionUser>(list);
    }

    @Transactional(readOnly = false)
    @Override
    public ResultMessage register(CollectionUser collectionUser) throws Exception {
        ResultMessage checkResult  = checkUserParam(collectionUser);
        if (!checkResult.isFlag()){
            return checkResult;
        }
        CollectionUser dbCollectionUser = collectionUserMapper.getCollectionUserByPhone(collectionUser.getPhone());
        if(dbCollectionUser !=null){
            return ResultMessage.fail("该手机号已注册");
        }
        //注册
        collectionUser.setCreateTime(new Date());
        collectionUser.setDelFlag(1);
        collectionUser.setUserStatus("01");//TODO 未实现枚举
        String pwd = collectionUser.getPwd();
        collectionUser.setPwd(MD5Util.digest(collectionUser.getPwd()));
        int count = collectionUserMapper.insert(collectionUser);

        //检查一下是否有私有云账号
        ResultMessage resultMessage = userService.checkAccountExists(collectionUser.getPhone());
        if(resultMessage !=null && StringUtils.equals(resultMessage.getMsg(), SysConstant.Msg_AccountName_Not_Exist)){
            //注册私有云账号
            RegisterEntity registerEntity = new RegisterEntity();
            registerEntity.setPhone(collectionUser.getPhone());
            BASE64Encoder base64 = new BASE64Encoder();
            registerEntity.setPwd(base64.encode(pwd.getBytes()));
            registerEntity.setConfirmPwd(base64.encode(pwd.getBytes()));
            registerEntity.setSmsVerifCode(collectionUser.getSmsCode());
            registerEntity.setPersonType(PersonTypeEnum.PERSON_TYPE_COLLECTION.getId()); //采集app注册
            userService.register(registerEntity);
        }
        //注册后给用户默认创建一个任务
        ResultMessage result = null;
        try {
            result = collectionService.registerToAllocation(collectionUser.getPhone());
        }catch (Exception e){
            throw new RuntimeException();
        }
        if(!result.isFlag()){
            throw new RuntimeException();
        }
        //注册成功后完成登录
        ResultMessage loginResult = this.login(collectionUser);
        return ResultMessage.success(loginResult.getData());
    }

    @Override
    public ResultMessage login(CollectionUser collectionUser) {
        LOG.info(">>>collectionUser:{}", JsonUtils.objectToJson(collectionUser));
        if(collectionUser == null || StringUtils.isEmpty(collectionUser.getPhone())
                || StringUtils.isEmpty(collectionUser.getPwd())){
            return ResultMessage.fail("账户或密码不能为空");
        }
        CollectionUser dbCollectionUser = collectionUserMapper.getCollectionUserByPhone(collectionUser.getPhone());
        if(dbCollectionUser == null ){
            return ResultMessage.fail("该手机号未注册");
        }
        if(!StringUtils.equals(MD5Util.digest(collectionUser.getPwd()),dbCollectionUser.getPwd())){
            return ResultMessage.fail("账户或密码不正确");
        }
        //设置采集APP登录信息
        String sessionId = getSessionId(dbCollectionUser);
        dbCollectionUser.setSessionId(sessionId);
        return ResultMessage.success(dbCollectionUser);
    }

    //检验采集App注册用户信息
    private ResultMessage checkUserParam(CollectionUser collectionUser){
        LOG.info(">>>collectionUser:{}", JsonUtils.objectToJson(collectionUser));
        ResultMessage res = ResultMessage.success();
        if(collectionUser == null || StringUtils.isEmpty(collectionUser.getPhone()) || StringUtils.isEmpty(collectionUser.getPwd())){
            return ResultMessage.fail("账户或密码不能为空");
        }
        //校验用户验证码
        //判断传递的短信验证码是或否和redis缓存的短信验证码一致
        if(StringUtils.isBlank(collectionUser.getSmsCode())){
            res = ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Empty);
            return res;
        }
        String redisKey = ImageValidateCodeEnum.Web_Image_code.getRedisCode() + collectionUser.getPhone();
        String smsCode = RedisUtil.get(redisKey);
        if (StringUtils.isEmpty(smsCode)){
            return ResultMessage.fail(SysConstant.Msg_Redis_SmsCode_Empty);
        }
        if(!collectionUser.getSmsCode().equalsIgnoreCase(smsCode)){
            res = ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Not_Right);
            return res;
        }
        return res;
    }
    @Override
    public ResultMessage loginOut(String sessionId) {
        LOG.info(">>>sessionId:{}",sessionId);
        try {
            if(StringUtils.isEmpty(sessionId)){
                return ResultMessage.fail("sessionId不能为空");
            }
            SsoTokenLoginHelper.logout(sessionId);
        }catch (Exception e){
            return ResultMessage.fail("系统异常"+e.getMessage());
        }
        return ResultMessage.success();
    }

    @Transactional(readOnly = false)
    @Override
    public ResultMessage resetPwd(CollectionUser collectionUser) {
        CollectionUser dbCollectionUser = collectionUserMapper.getCollectionUserByPhone(collectionUser.getPhone());
        if(dbCollectionUser == null){
            return ResultMessage.fail("此手机号未注册");
        }
        /*ResultMessage resultMessage = checkUserParam(collectionUser);
        if(!resultMessage.isFlag()){
            return resultMessage;
        }*/
        //修改密码
        dbCollectionUser.setPwd(MD5Util.digest(collectionUser.getPwd()));
        dbCollectionUser.setUpdateTime(new Date());
        collectionUserMapper.updateByPrimaryKey(dbCollectionUser);
        //更改私有云密码
        ResultMessage userInfoByPhone = userService.findUserInfoByPhone(collectionUser.getPhone());
        if(userInfoByPhone.isFlag()){
            Map<String,Object> userInfo = userInfo = (Map<String,Object>) userInfoByPhone.getData();
            Integer accountId = Integer.valueOf(userInfo.get("accountId").toString());
            PermAccount permAccount = new PermAccount();
            permAccount.setAccountId(accountId);
            permAccount.setAccountPwd(dbCollectionUser.getPwd());
            permAccountMapper.updatePermAccountPassword(permAccount);
        }

        return ResultMessage.success();
    }


    @Transactional(readOnly = false)
    @Override
    public ResultMessage modifyPwd(CollectionUser collectionUser) {
        if(StringUtils.isEmpty(collectionUser.getPhone()) || StringUtils.isEmpty(collectionUser.getPwd())
                || StringUtils.isEmpty(collectionUser.getNewPwd())){
            return ResultMessage.fail("手机号或密码为空");
        }
        CollectionUser dbCollectionUser = collectionUserMapper.getCollectionUserByPhone(collectionUser.getPhone());
        if(dbCollectionUser == null){
            LOG.info(">>>phone:{}未注册",collectionUser.getPhone());
            return ResultMessage.fail("该手机号未注册");
        }
        if(!StringUtils.equals(MD5Util.digest(collectionUser.getPwd()),dbCollectionUser.getPwd())){
            return ResultMessage.fail("原密码不正确");
        }
        dbCollectionUser.setPwd(MD5Util.digest(collectionUser.getNewPwd()));
        dbCollectionUser.setUpdateTime(new Date());
        collectionUserMapper.updateByPrimaryKey(dbCollectionUser);
        return ResultMessage.success();
    }

    @Transactional(readOnly = false)
    @Override
    public ResultMessage resetPhone(CollectionUser collectionUser) {
        CollectionUser dbCollectionUser = collectionUserMapper.getCollectionUserByPhone(collectionUser.getPhone());
        if(dbCollectionUser == null){
           return ResultMessage.fail("此手机号未注册");
        };
        //设置新密码
        dbCollectionUser.setPhone(collectionUser.getNewPhone());//新密码
        dbCollectionUser.setSmsCode(collectionUser.getSmsCode());//验证码
        ResultMessage checkResult  = checkUserParam(dbCollectionUser);
        if (!checkResult.isFlag()){
            return checkResult;
        }
        dbCollectionUser.setUpdateTime(new Date());
        collectionUserMapper.updateByPrimaryKey(dbCollectionUser);
        //更新私有云手机号
        ResultMessage userInfoByPhone = userService.findUserInfoByPhone(collectionUser.getPhone());
        if(userInfoByPhone.isFlag()){
            Map<String,Object> userInfo = userInfo = (Map<String,Object>) userInfoByPhone.getData();
            Integer accountId = Integer.valueOf(userInfo.get("accountId").toString());
            PermAccount permAccount = new PermAccount();
            permAccount.setAccountId(accountId);
            permAccount.setAccountName(collectionUser.getNewPhone());
            permAccountMapper.update(permAccount);
        }
        return ResultMessage.success();
    }

    @Override
    public ResultMessage findPwd(CollectionUser collectionUser) {
        if(collectionUser == null || StringUtils.isEmpty(collectionUser.getPhone())){
            return ResultMessage.fail("请填写手机号");
        }
        ResultMessage res  = ResultMessage.success();
        CollectionUser dbCollectionUser = collectionUserMapper.getCollectionUserByPhone(collectionUser.getPhone());
        if(dbCollectionUser == null){
            return ResultMessage.fail("该手机号未注册");
        }
        if(StringUtils.isBlank(collectionUser.getSmsCode())){
            res = ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Empty);
            return res;
        }
        String redisKey = ImageValidateCodeEnum.Web_Image_code.getRedisCode() + collectionUser.getPhone();
        String smsCode = RedisUtil.get(redisKey);
        if (StringUtils.isEmpty(smsCode)){
            return ResultMessage.fail(SysConstant.Msg_Redis_SmsCode_Empty);
        }
        if(!collectionUser.getSmsCode().equalsIgnoreCase(smsCode)){
            res = ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Not_Right);
            return res;
        }
        return res;
    }

    @Override
    public ResultMessage loginCheck(String sessionId) {
        LOG.info(">>>sessionId:{}",sessionId);
        if(StringUtils.isEmpty(sessionId)){
            return ResultMessage.fail("sessionId不能为空");
        }
        CollectionUser collectionUser = SsoTokenLoginHelper.loginCheck(sessionId);
        if(collectionUser == null){
            return ResultMessage.fail("no login");
        }
        return ResultMessage.success(collectionUser);
    }

    @Override
    public ResultMessage relateWx(CollectionUser collectionUser) {
        if(StringUtils.isEmpty(collectionUser.getWxId())){
            return ResultMessage.fail("关联微信Id不能为空");
        }
        CollectionUser dbCollectionUser = collectionUserMapper.getCollectionUserByPhone(collectionUser.getPhone());
        if(dbCollectionUser == null || !StringUtils.equals(MD5Util.digest(collectionUser.getPwd()),dbCollectionUser.getPwd())){
            return ResultMessage.fail("登录用户名密码错误");
        }
        dbCollectionUser.setWxId(collectionUser.getWxId());
        return ResultMessage.success();
    }


    @Override
    public ResultMessage wxLogin(String wxId) {
        if(StringUtils.isEmpty(wxId)){
            return ResultMessage.fail("微信Id不能为空");
        }
        CollectionUser dbCollectionUser = collectionUserMapper.getCollectionUserByWxId(wxId);
        if(dbCollectionUser == null){
            return ResultMessage.fail(FIND_OBJECT_NOT_EXISTS.getValue());
        }

        //设置登录sessionId
        String sessionId = getSessionId(dbCollectionUser);
        dbCollectionUser.setSessionId(sessionId);
        return ResultMessage.success(dbCollectionUser);
    }

    /**
     * 设置sessionId
     * @param collectionUser
     * @return
     */
    public String getSessionId(CollectionUser collectionUser){
        collectionUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        collectionUser.setExpireMinite(SsoLoginStore.getRedisExpireMinite());
        collectionUser.setExpireFreshTime(System.currentTimeMillis());

        String sessionId = SsoSessionIdHelper.makeSessionId(collectionUser);
        SsoTokenLoginHelper.login(sessionId, collectionUser);
        return sessionId;
    }
}
