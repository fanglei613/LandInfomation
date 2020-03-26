package com.jh.system.service.impl;

import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;
import com.jh.cache.service.ICacheService;
import com.jh.constant.ConstantUtil;
import com.jh.constant.SysConstant;
import com.jh.enums.DataStatusEnum;
import com.jh.enums.DelStatusEnum;
import com.jh.enums.ImageValidateCodeEnum;
import com.jh.system.Enum.AccountTypeEnum;
import com.jh.system.Enum.PersonTypeEnum;
import com.jh.system.Enum.UserEnum;
import com.jh.system.entity.DataProduct;
import com.jh.system.entity.PermAccount;
import com.jh.system.entity.PermPerson;
import com.jh.system.entity.PermResource;
import com.jh.system.mapping.IDataProductMapper;
import com.jh.system.mapping.IPermAccountMapper;
import com.jh.system.mapping.IPermPersonMapper;
import com.jh.system.model.LoginParam;
import com.jh.system.model.RegisterEntity;
import com.jh.system.model.UserParam;
import com.jh.system.service.*;
import com.jh.util.*;
import com.jh.util.sms.RLYSMSUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 客户操作接口
 * @version <1> 2018-05-04 xzg： Created.
 */
@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private IPermAccountMapper permAccountMapper;
    @Autowired
    private IPermPersonMapper permPersonMapper;
    @Autowired
    private IDataProductMapper dataProductMapper;
    @Autowired
    private IBuzRedisService buzRedisService;
    @Autowired
    private CropService  cropService;
    @Autowired
    private IDictService dictService;
    @Autowired
    private ICacheService cacheService;

    @Autowired
    private IPermAccountService permAccountService;

    @Autowired
    private IRelateAccountRoleService relateAccountRole;

    @Autowired
    private IPermResourceService permResourceService;

    @Autowired
    private IRelateAccountRoleService relateAccountRoleService;


    /**
     * 微信注册模块，短信发送
     * @param phone 用户参数
     * @version <1> 2018-04-27 xzg： Created.
     * @version <2> 2018-05-10 cxw： update:将实现方法熊controller层移动至service层,将发送短信wx端与web端通用方法提取出来
     */
    @Override
    public ResultMessage sendWechatSmsValidCode(String phone) {
        ResultMessage result = ResultMessage.success(SysConstant.Msg_Sms_Verification_Send_Success);
        //短信发送
        ResultMessage sendResultMap = new RLYSMSUtil().sendVerifyMessage(phone);
        if (sendResultMap.isFlag()){
            result = buzRedisService.setRedisSmsCode(phone,sendResultMap.getData().toString(), ImageValidateCodeEnum.Web_Image_code);
        }else {
           return sendResultMap;
        }
        return result;
    }

    /**
     * 注册模块，短信发送
     * @param mobile 用户参数
     *  @param imageVerifyCode 图形验证码
     * @version <1> 2018-05-09 cxw： Created.
     * @version <2>2018-05-11 cxw:update:将发送短信wx端与web端通用方法提取出来
     */
    @Override
    public ResultMessage findValidCodeForRegister(String mobile,String imageVerifyCode,String validToken) {
            ResultMessage result = ResultMessage.success(SysConstant.Msg_Sms_Verification_Send_Success);
            //获取redis缓存图形验证码
            result = buzRedisService.getRedisImageValidateCode(validToken,ImageValidateCodeEnum.Web_Image_code);
            if(result.isFlag()){
                String imgVerifyCodeRedis = result.getData().toString();
                //判断输入图形验证码是否与redis缓存图形验证码一致
                if (imgVerifyCodeRedis.equalsIgnoreCase(imageVerifyCode)) {
                    // 判断该账号是否已经注册
                    Map<String,Object> account = findAccountByAccountName(mobile);  //查询账号信息
                    if (account != null) {
                        result = ResultMessage.fail(SysConstant.Msg_Account_Registered);
                        return result;
                    }
                    //用户未注册  ，短信发送
                    ResultMessage sendResultMap = new RLYSMSUtil().sendVerifyMessage(mobile);
                    if (sendResultMap.isFlag()){
                        result = buzRedisService.setRedisSmsCode(mobile,sendResultMap.getData().toString(),ImageValidateCodeEnum.Web_Image_code);
                    } else {
                        result = sendResultMap;
                    }
                } else {
                    result = ResultMessage.fail(SysConstant.Msg_Image_Verification_Not_Equal);
                }
            }
            return result;
    }

    /**
     * 社区注册模块，短信发送
     * @param mobile 用户参数
     * @version <1> 2019-05-09 lijie： Created.
     */
    @Override
    public ResultMessage findForumValidCodeForRegister(String mobile) {
        ResultMessage result = ResultMessage.success(SysConstant.Msg_Sms_Verification_Send_Success);
        // 判断该账号是否已经注册
        Map<String,Object> account = findAccountByAccountName(mobile);  //查询账号信息
        if (account != null) {
            result = ResultMessage.fail(SysConstant.Msg_Account_Registered);
            return result;
        }
        //用户未注册  ，短信发送
        ResultMessage sendResultMap = new RLYSMSUtil().sendVerifyMessage(mobile);
        if (sendResultMap.isFlag()){
            result = buzRedisService.setRedisSmsCode(mobile,sendResultMap.getData().toString(),ImageValidateCodeEnum.Web_Image_code);
        } else {
            result = sendResultMap;
        }
        return result;
    }


    /**
     * 找回密码，短信发送
     * @param mobile 用户参数
     * @version <1> 2018-05-14 cxw： Created.
     */
    @Override
    public ResultMessage findValidCodeForPwd(String mobile) {
        ResultMessage result = ResultMessage.success(SysConstant.Msg_Sms_Verification_Send_Success);
        // 判断该账号是否已经注册,注册账号才可发送短信
        Map<String,Object> account = findAccountByAccountName(mobile);  //查询账号信息
        if (account == null) {
            result = ResultMessage.fail(SysConstant.Msg_AccountName_Not_Exist);
            return result;
        }

        //短信发送
        result = new RLYSMSUtil().sendVerifyMessage(mobile);
        if (result.isFlag()){
            //短信发送成功，缓存手机验证码
            result = buzRedisService.setRedisSmsCode(mobile,result.getData().toString(),ImageValidateCodeEnum.Web_Image_code);
        }
        return result;
    }

    /**
     * 检查账号是否存在
     * @param  accountName : 账号，即手机号
     *@version <1> 2018-04-27 11:20:10 Hayden : Created.
     */
    @Override
    public ResultMessage checkAccountExists(String accountName) {
        if (StringUtils.isBlank(accountName)){
            return ResultMessage.fail(SysConstant.Msg_AccountName_Is_Empty);
        }
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("phone",accountName);
        Map<String,Object> account = permAccountMapper.findAccountOne(params);
        if (account == null){
            return ResultMessage.success(SysConstant.Msg_AccountName_Not_Exist,false);
        } else {
            return ResultMessage.success(SysConstant.Msg_AccountName_Exist,true);
        }
    }

    /**
     * 私有云后台服务： 根据账号密码验证码登录
     * @param  userParam: 登录信息
     *  accountName:账号即手机号
     *  accountPwd：密码
     *   verifyCode：图形验证码
     *   validToken： 登录生成的token
     * @return  ResultMessage
     * @version <1> 2018-08-08 13:46:16 cxw : Created.(加注释)
     */
    @Override
    public ResultMessage loginForWeb(UserParam userParam) {

        Map<String,Object> voMap = new HashMap<String,Object>();

        ResultMessage result = loginForNoVerifyCode(userParam,true);
        if(result.isFlag()){

            voMap = (Map<String,Object>)result.getData();
            Map<String,Object> userInfo = (Map<String,Object>)voMap.get("userInfo");

            Integer accountId = Integer.parseInt(userInfo.get("accountId").toString());


            //查询角色信息

            //缓存账号角色
            ResultMessage resultRoleMsg = relateAccountRoleService.findRolesByAccountId(accountId);
            List<Map<String,Object>> roleList = null;
            if (resultRoleMsg.isFlag()){
                roleList = (List<Map<String,Object>>)resultRoleMsg.getData();
            }
            voMap.put("roleList",roleList);

            //默认子系统首页,将排序后最小值的res_url给defaultMenu，作为首页访问
                ResultMessage menuResult = permResourceService.findSubSystemByAccountId(accountId);
                if(menuResult.isFlag()){
                    List<PermResource> resources = (List<PermResource>)menuResult.getData();
                    if(resources != null && resources.size() > 0){
                        Short resNo = resources.get(0).getResNo() == null ? 10000 : resources.get(0).getResNo(); //第一个排序最小值，如果resNo为空，则赋予10000给他，尽量不在首页显示
                        String defaultMenu = resources.get(0).getResUrl();
                        for(PermResource resource : resources){
                            if(resource.getResNo() != null && StringUtils.isNotBlank(resource.getResUrl())){
                                if(resource.getResNo() < resNo ){
                                    resNo = resource.getResNo();
                                    defaultMenu = resource.getResUrl();
                                }
                            }
                        }
                        voMap.put("defaultMenu", defaultMenu);
                    }
                }
            return buzRedisService.setUserLoginInfo(voMap);
        }
        return result;

    }


    /**
     * 私有云后台服务： 更新个人昵称后重新获取个人的账号信息并刷新redis记录
     * @param   redisKey, accountName
     *  accountName:账号即手机号
     *  accountPwd：密码
     *   verifyCode：图形验证码
     *   validToken： 登录生成的token
     * @return  ResultMessage
     * @version <1> 2018-08-08 13:46:16 cxw : Created.(加注释)
     */
    @Override
    public ResultMessage updateUserInfo(String redisKey,String accountName) {

        Map<String,Object> voMap = new HashMap<String,Object>();

        ResultMessage result = findUserInfo(redisKey,accountName,true);
        if(result.isFlag()){

            voMap = (Map<String,Object>)result.getData();
            Map<String,Object> userInfo = (Map<String,Object>)voMap.get("userInfo");

            Integer accountId = Integer.parseInt(userInfo.get("accountId").toString());


            //查询角色信息

            //缓存账号角色
            ResultMessage resultRoleMsg = relateAccountRoleService.findRolesByAccountId(accountId);
            List<Map<String,Object>> roleList = null;
            if (resultRoleMsg.isFlag()){
                roleList = (List<Map<String,Object>>)resultRoleMsg.getData();
            }
            voMap.put("roleList",roleList);

            //默认子系统首页,将排序后最小值的res_url给defaultMenu，作为首页访问
            ResultMessage menuResult = permResourceService.findSubSystemByAccountId(accountId);
            if(menuResult.isFlag()){
                List<PermResource> resources = (List<PermResource>)menuResult.getData();
                if(resources != null && resources.size() > 0){
                    Short resNo = resources.get(0).getResNo() == null ? 10000 : resources.get(0).getResNo(); //第一个排序最小值，如果resNo为空，则赋予10000给他，尽量不在首页显示
                    String defaultMenu = resources.get(0).getResUrl();
                    for(PermResource resource : resources){
                        if(resource.getResNo() != null && StringUtils.isNotBlank(resource.getResUrl())){
                            if(resource.getResNo() < resNo ){
                                resNo = resource.getResNo();
                                defaultMenu = resource.getResUrl();
                            }
                        }
                    }
                    voMap.put("defaultMenu", defaultMenu);
                }
            }
            return buzRedisService.setUserLoginInfo(voMap);
        }
        return result;

    }



    /**
     * 根据账号密码验证码登录
     * @param  userParam: 登录信息
     *  accountName:账号即手机号
     *  accountPwd：密码
     *   verifyCode：图形验证码
     *   validToken： 登录生成的token
     *   useProductFilterFlag：是否需要经过productFilter拦截器状态
     * @return  ResultMessage
     * @version <1> 2018-08-08 13:46:16 cxw : Created.(加注释)
     *
     */
    @Override
    public ResultMessage loginForShow(UserParam userParam) {


        Map<String,Object> voMap = new HashMap<String,Object>();

        ResultMessage result = loginForNoVerifyCode(userParam,true);
        if(result.isFlag()){

            voMap = (Map<String,Object>)result.getData();
            Map<String,Object> userInfo = (Map<String,Object>)voMap.get("userInfo");

            Integer accountId = Integer.parseInt(userInfo.get("accountId").toString());
            //用户昵称为空，显示账号名
            voMap.put("nickName",((userInfo.get("nickName") != null && StringUtils.isNotBlank(userInfo.get("nickName").toString().trim())) ? userInfo.get("nickName").toString().trim() : (userInfo.get("accountName") != null && StringUtils.isNotBlank(userInfo.get("accountName").toString().trim())) ? userInfo.get("accountName").toString() : userInfo.get("accountCode").toString()));


            //缓存账号角色
            ResultMessage resultRoleMsg = relateAccountRoleService.findRolesByAccountId(accountId);
            List<Map<String,Object>> roleList = null;
            if (resultRoleMsg.isFlag()){
                roleList = (List<Map<String,Object>>)resultRoleMsg.getData();
            }
//            voMap.put("roleList",roleList);
            if(roleList != null && roleList.size() > 0){

                boolean useProductFilterFlag = true;
                String roleCode = "normal";
                for(int i=0;i<roleList.size();i++){
                    if(roleList.get(i).get("roleCode").equals("Role_Admin")){
                        roleCode = roleList.get(i).get("roleCode").toString();
                        useProductFilterFlag = false;
                    }
                }
                voMap.put("roleCode",roleCode);
                voMap.put(ConstantUtil.USEPRODUCTFILTER_KEY, useProductFilterFlag);
            }


            List<Map<String,Object>> productList =  dataProductMapper.findProductsByAccountId(accountId);
            for (Map<String,Object> product : productList){
                for (Map.Entry<String,Object> p : product.entrySet()){
                    //时间类型 转 json 字符串时 异常，解决方法 日期类型转成字符串
                    if (p.getValue() instanceof Date){
                        product.put(p.getKey(),p.getValue().toString());
                    }
                }
            }
            voMap.put("productList", productList);
            /**
             //                 * 获取用户默认权限
             //                 * 1.如果用户已设置默认权限：defaultShow = true， 则使用默认权限给用户
             //                 * 2.如果未设置默认权限，则读取productType == 1 , 表示系统默认初始权限
             //                 */
            Map<String,Object> defaultProduct = null;
            if(productList != null && productList.size() > 0){
                for(Map<String,Object> product : productList){
                    if((boolean)(product.get("defaultShow"))){
                        defaultProduct = product;
                        break;
                    }
                }
                if(defaultProduct == null){ //如果用户没有设置默认权限，则使用系统默认权限
                    for(Map<String,Object> product : productList){
                        if(Integer.valueOf(product.get("productType").toString()) == 1 ){  //是否是默认权限
                            defaultProduct = product;
                            break;
                        }
                    }
                }
            }
            voMap.put("defaultProduct",defaultProduct);
            return buzRedisService.setUserLoginInfo(voMap);
        }


        return result;


    }


    @Override
    public ResultMessage loginForApp(UserParam userParam, boolean isVerifyCode){

        boolean useProductFilterFlag = false;
        ResultMessage result = loginForNoVerifyCode(userParam, isVerifyCode);
        if(result.isFlag()){
            Map<String,Object> userInfo = (Map<String,Object>)result.getData();

            if(StringUtils.isNotBlank(userParam.getAppMenu())){
                Map<String,Object> user = (Map<String,Object>)userInfo.get("userInfo");
                Integer accountId = Integer.parseInt(user.get("accountId").toString());
                Map<String,Object> voMap = new HashMap<String,Object>();
                voMap.put("accountId", accountId);
                voMap.put("appMenus", userParam.getAppMenu().split(","));
                ResultMessage menuResult = permResourceService.queryAppMenus(voMap);

                userInfo.put("appFlag", menuResult.isFlag()); //查询菜单标记
                if(menuResult.isFlag()){
                    String appMenu = "";
                    List<Map<String,Object>> menuList = (List<Map<String,Object>>)menuResult.getData();
                    if(menuList != null && menuList.size() > 0){
                        for(Map<String,Object> menu : menuList){
                            appMenu += menu.get("rescode");
                        }
                    }

                    userInfo.put("appMenu", appMenu);
                }
            }


            return buzRedisService.setUserLoginInfo(userInfo);
        }
        return result;
    }


    @Override
    public ResultMessage loginForProduct(UserParam userParam,Long regionId, boolean isVerifyCode){

        ResultMessage result = loginForNoVerifyCode(userParam, isVerifyCode);

        if(result.isFlag()){

            Map<String,Object> resultData = (Map<String,Object>)result.getData();

//            resultData.put("region_id", regionId);
            Map<String,Object> userInfo = (Map<String,Object>)resultData.get("userInfo");
            userInfo.put("region_id", regionId);

            return buzRedisService.setUserLoginInfo(resultData);
        }
        return result;
    }


    /**
     * 用户登录：多态的运用
     * 默认是均不需要经过productFilter拦截器，若需要经历该拦截器，可直接调用loginForNoVerifyCode(UserParam userParam,boolean isVerifyCode, boolean useProductFilterFlag)方法
     * @param userParam
     * @param isVerifyCode
     * @return
     */
    public ResultMessage loginForNoVerifyCode(UserParam userParam,boolean isVerifyCode) {
        return loginForNoVerifyCode(userParam, isVerifyCode, false);
    }


    /**
     * 根据账号密码登录
     * @param  userParam: 登录信息
     *  accountName:账号即手机号
     *  accountPwd：密码
     *  validToken： 登录生成的token
     *  isVerifyCode ：是否校验验证码（为true则校验，false则不校验）
     * @return  ResultMessage
     * @version <1> 2018-08-08 13:46:16 cxw : Created.
     *
     *
     * @version<2> 2018-08-29 lcw : modify.
     *  1.验证用户名密码是否正确
     *  2.将用户信息和角色信息缓存至redis中
     *  3.各业务系统可能需要的信息分别进入对应的登陆方法进行增加,此处只是公共部分
     *
     */
    public ResultMessage loginForNoVerifyCode(UserParam userParam,boolean isVerifyCode, boolean useProductFilterFlag) {
        Map<String,Object> voMap = new HashMap<String, Object>();

        try {
            PermAccount permAccount = userParam.getPermAccount();
            if (StringUtils.isBlank(permAccount.getAccountName())) {
                return ResultMessage.fail(UserEnum.ACCOUNT_NAME_EMPTY.getValue(), UserEnum.ACCOUNT_NAME_EMPTY.getMesasge());
            } else if (StringUtils.isBlank(permAccount.getAccountPwd())) {
                return ResultMessage.fail(UserEnum.ACCOUNT_PWD_EMPTY.getValue(), UserEnum.ACCOUNT_PWD_EMPTY.getMesasge());
            }
            //如果isVerifyCode为true则需校验图形证码
            if(isVerifyCode) {
                if (StringUtils.isEmpty(userParam.getVerifyCode())){
                    return ResultMessage.fail(SysConstant.Msg_Img_VerifCode_Empty);
                }

                //判断图形验证码
                ResultMessage res = buzRedisService.getRedisImageValidateCode(userParam.getValidToken(),ImageValidateCodeEnum.Web_Image_code);
                if (!res.isFlag()){
                    return res;
                }
                if (!userParam.getVerifyCode().equalsIgnoreCase(res.getData().toString())){
                    return ResultMessage.fail(SysConstant.Msg_Img_VerifCode_Error);
                }
            }

            //判断账号是否存在
            PermAccount account = permAccountMapper.queryPermAccountByAccountName(permAccount.getAccountName());
            if(account== null){
                return ResultMessage.fail(UserEnum.ACCOUNT_NAME_NOT_EXIST.getValue(), UserEnum.ACCOUNT_NAME_NOT_EXIST.getMesasge());
            }

            BASE64Decoder base64 = new BASE64Decoder();
            String pwd = new String(base64.decodeBuffer(permAccount.getAccountPwd()));

            //判断登录密码是否正确
            if (!account.getAccountPwd().equals(MD5Util.digest(pwd))){
                return ResultMessage.fail(SysConstant.Msg_LoginAccountPwd_Is_Error);
            }

            //
//            ResultMessage userInfoResult =  buzRedisService.setUserLoginInfo(permAccount.getAccountName(),false); //不需要经过productFilter过滤器

//          redis缓存应该是在各自登录所需要的数据组装完毕后设置，而不是直接在此处通用设置



            ResultMessage userInfo = getUserInfo(permAccount.getAccountName());
            if(userInfo.isFlag()){
                voMap.put("userInfo",(Map<String,Object>)userInfo.getData());
            }



            voMap.put(ConstantUtil.USEPRODUCTFILTER_KEY, useProductFilterFlag);

            String redisKey = UUIDUtil.generateUUID();
            //AccessToken

            voMap.put(SysConstant.Key_Login_Token, redisKey); //token

            return ResultMessage.success(voMap);
//
//            if(userInfoResult.isFlag()){
//
//                String accountToken = userInfoResult.getMsg();
//                voMap.put(SysConstant.Key_Login_Token,accountToken);
//
//
//                Map<String,Object> userData = (Map<String,Object>)userInfoResult.getData();
//                Map<String,Object> userInfo = (Map<String, Object>) userData.get("userInfo");
//                voMap.put("accountId", userInfo.get("accountId"));
//
//                //默认子系统首页,将排序后最小值的res_url给defaultMenu，作为首页访问
//                ResultMessage menuResult = permResourceService.findSubSystemByAccountId(Integer.parseInt(userInfo.get("accountId").toString()));
//                if(menuResult.isFlag()){
//                    List<PermResource> resources = (List<PermResource>)menuResult.getData();
//                    if(resources != null && resources.size() > 0){
//                        Short resNo = resources.get(0).getResNo() == null ? 10000 : resources.get(0).getResNo(); //第一个排序最小值，如果resNo为空，则赋予10000给他，尽量不在首页显示
//                        String defaultMenu = resources.get(0).getResUrl();
//                        for(PermResource resource : resources){
//                            if(resource.getResNo() != null && StringUtils.isNotBlank(resource.getResUrl())){
//                                if(resource.getResNo() < resNo ){
//                                    resNo = resource.getResNo();
//                                    defaultMenu = resource.getResUrl();
//                                }
//                            }
//                        }
//                        voMap.put("defaultMenu", defaultMenu);
//                    }
//                }
//
//
//                List<Map<String,Object>> productList = (List<Map<String,Object>>)userData.get("productList");
//                List<Map<String,Object>> roleListInfo = (List<Map<String,Object>>)userData.get("roleList");
//                String roleCode = "normal";
//                for(int i=0;i<roleListInfo.size();i++){
//                    if(roleListInfo.get(i).get("roleCode").equals("Role_Admin")){
//                        roleCode = roleListInfo.get(i).get("roleCode").toString();
//                    }
//                }
//                voMap.put("roleCode",roleCode);
//
//                //用户昵称为空，显示账号名
//                voMap.put("nickName",((userInfo.get("nickName") != null && StringUtils.isNotBlank(userInfo.get("nickName").toString().trim())) ? userInfo.get("nickName").toString().trim() : (userInfo.get("accountName") != null && StringUtils.isNotBlank(userInfo.get("accountName").toString().trim())) ? userInfo.get("accountName").toString() : userInfo.get("accountCode").toString()));
//
//                /**
//                 * 获取用户默认权限
//                 * 1.如果用户已设置默认权限：defaultShow = true， 则使用默认权限给用户
//                 * 2.如果未设置默认权限，则读取productType == 1 , 表示系统默认初始权限
//                 */
//                Map<String,Object> defaultProduct = null;
//                if(productList != null && productList.size() > 0){
//                    for(Map<String,Object> product : productList){
//                        if((boolean)(product.get("defaultShow"))){
//                            defaultProduct = product;
//                            break;
//                        }
//                    }
//                    if(defaultProduct == null){ //如果用户没有设置默认权限，则使用系统默认权限
//                        for(Map<String,Object> product : productList){
//                            if(Integer.valueOf(product.get("productType").toString()) == 1 ){  //是否是默认权限
//                                defaultProduct = product;
//                                break;
//                            }
//                        }
//                    }
//                }
//                voMap.put("defaultProduct",defaultProduct);
//
//
//                return ResultMessage.success(voMap);
//            }
//
//            return ResultMessage.fail(UserEnum.ACCOUNT_OR_PASSWORD_ERROR.getValue(), UserEnum.ACCOUNT_OR_PASSWORD_ERROR.getMesasge());
        }catch (Exception e){
            return ResultMessage.fail("用户登录失败");
        }
    }


    /**
     * 更新用户信息：多态的运用
     * 默认是均不需要经过productFilter拦截器，若需要经历该拦截器，可直接调用loginForNoVerifyCode(UserParam userParam,boolean isVerifyCode, boolean useProductFilterFlag)方法
     * @param redisKey
     * @param accountName
     * @return
     */
    public ResultMessage findUserInfo(String redisKey,String accountName) {
        return findUserInfo(redisKey,accountName, false);
    }


    /**
     * 根据账号密码登录
     * @param  userParam: 登录信息
     *  accountName:账号即手机号
     *  accountPwd：密码
     *  validToken： 登录生成的token
     *  isVerifyCode ：是否校验验证码（为true则校验，false则不校验）
     * @return  ResultMessage
     * @version <1> 2018-08-08 13:46:16 cxw : Created.
     *
     *
     * @version<2> 2018-08-29 lcw : modify.
     *  1.验证用户名密码是否正确
     *  2.将用户信息和角色信息缓存至redis中
     *  3.各业务系统可能需要的信息分别进入对应的登陆方法进行增加,此处只是公共部分
     *
     */
    public ResultMessage findUserInfo(String redisKey,String accountName, boolean useProductFilterFlag) {
        Map<String,Object> voMap = new HashMap<String, Object>();

        if (StringUtils.isBlank(accountName)) {
            return ResultMessage.fail(UserEnum.ACCOUNT_NAME_EMPTY.getValue(), UserEnum.ACCOUNT_NAME_EMPTY.getMesasge());
        }




        ResultMessage userInfo = getUserInfo(accountName);
        if(userInfo.isFlag()){
            voMap.put("userInfo",(Map<String,Object>)userInfo.getData());
        }



        voMap.put(ConstantUtil.USEPRODUCTFILTER_KEY, useProductFilterFlag);

        //AccessToken

        voMap.put(SysConstant.Key_Login_Token, redisKey); //token

        return ResultMessage.success(voMap);
    }



    @Override
    public ResultMessage getUserInfo(String accountName){
        if(StringUtils.isBlank(accountName)){
            return ResultMessage.fail("用户名不能为空");
        }
        //查询用户信息
        ResultMessage userInfoMes = this.findUserInfoByPhone(accountName);
        Integer accountId = null;
        Map<String,Object> userInfo = new HashMap<String, Object>();

        if (userInfoMes.isFlag()){
            userInfo = (Map<String,Object>) userInfoMes.getData();
            for (Map.Entry<String,Object> p : userInfo.entrySet()){
                //时间类型 转 json 字符串时 异常，解决方法 日期类型转成字符串
                if (p.getValue() instanceof Date){
                    userInfo.put(p.getKey(),p.getValue().toString());
                }
            }
            return ResultMessage.success(userInfo);
        }
        return userInfoMes;
    }

    @Override
    public ResultMessage loginForWx(String phone,String openId, Long regionId) {

        ResultMessage result = getUserInfo(phone);
        if(result.isFlag()){
            boolean useProductFilterFlag = false;

            Map<String,Object> voMap = new HashMap<String,Object>();

            Map<String,Object> userInfo = (Map<String,Object>)result.getData();
            userInfo.put("region_id", regionId);  //这地方这样写，我表示也很无赖。。 。

            voMap.put(ConstantUtil.USEPRODUCTFILTER_KEY, useProductFilterFlag);
            voMap.put("openId", openId);
            voMap.put(SysConstant.Key_Login_Token,  UUIDUtil.generateUUID());
            voMap.put("userInfo", userInfo);


            return buzRedisService.setUserLoginInfo(voMap);
        }
        return result;


//        Map<String,Object> voMap = new HashMap<String, Object>();
//        try {
//            //
//            ResultMessage userInfoResult =  buzRedisService.setUserLoginInfo(phone,false); //不需要经过productFilter过滤器
//            if(userInfoResult.isFlag()){
//
//                String accountToken = userInfoResult.getMsg();
//                Map<String,Object> userData = (Map<String,Object>)userInfoResult.getData();
//                Map<String,Object> userInfo = (Map<String, Object>) userData.get("userInfo");
//
//                //默认子系统首页,将排序后最小值的res_url给defaultMenu，作为首页访问
//                ResultMessage menuResult = permResourceService.findSubSystemByAccountId(Integer.parseInt(userInfo.get("accountId").toString()));
//                if(menuResult.isFlag()){
//                    List<PermResource> resources = (List<PermResource>)menuResult.getData();
//                    if(resources != null && resources.size() > 0){
//                        Short resNo = resources.get(0).getResNo() == null ? 10000 : resources.get(0).getResNo(); //第一个排序最小值，如果resNo为空，则赋予10000给他，尽量不在首页显示
//                        String defaultMenu = resources.get(0).getResUrl();
//                        for(PermResource resource : resources){
//                            if(resource.getResNo() != null && StringUtils.isNotBlank(resource.getResUrl())){
//                                if(resource.getResNo() < resNo ){
//                                    resNo = resource.getResNo();
//                                    defaultMenu = resource.getResUrl();
//                                }
//                            }
//                        }
//                        voMap.put("defaultMenu", defaultMenu);
//                    }
//                }
//
//
//                List<Map<String,Object>> productList = (List<Map<String,Object>>)userData.get("productList");
//                List<Map<String,Object>> roleListInfo = (List<Map<String,Object>>)userData.get("roleList");
//                String roleCode = "normal";
//                for(int i=0;i<roleListInfo.size();i++){
//                    if(roleListInfo.get(i).get("roleCode").equals("Role_Admin")){
//                        roleCode = roleListInfo.get(i).get("roleCode").toString();
//                    }
//                }
//                voMap.put(SysConstant.Key_Login_Token,accountToken);
//                voMap.put("roleCode",roleCode);
//
//                //用户昵称为空，显示账号名
//                voMap.put("nickName",((userInfo.get("nickName") != null && StringUtils.isNotBlank(userInfo.get("nickName").toString().trim())) ? userInfo.get("nickName").toString().trim() : (userInfo.get("accountName") != null && StringUtils.isNotBlank(userInfo.get("accountName").toString().trim())) ? userInfo.get("accountName").toString() : userInfo.get("accountCode").toString()));
//
//                /**
//                 * 获取用户默认权限
//                 * 1.如果用户已设置默认权限：defaultShow = true， 则使用默认权限给用户
//                 * 2.如果未设置默认权限，则读取productType == 1 , 表示系统默认初始权限
//                 */
//                Map<String,Object> defaultProduct = null;
//                if(productList != null && productList.size() > 0){
//                    for(Map<String,Object> product : productList){
//                        if((boolean)(product.get("defaultShow"))){
//                            defaultProduct = product;
//                            break;
//                        }
//                    }
//                    if(defaultProduct == null){ //如果用户没有设置默认权限，则使用系统默认权限
//                        for(Map<String,Object> product : productList){
//                            if(Integer.valueOf(product.get("productType").toString()) == 1 ){  //是否是默认权限
//                                defaultProduct = product;
//                                break;
//                            }
//                        }
//                    }
//                }
//                voMap.put("defaultProduct",defaultProduct);
//
//
//                return ResultMessage.success(voMap);
//            }
//
//            return ResultMessage.fail(UserEnum.ACCOUNT_OR_PASSWORD_ERROR.getValue(), UserEnum.ACCOUNT_OR_PASSWORD_ERROR.getMesasge());
//        }catch (Exception e){
//            return ResultMessage.fail("用户登录失败");
//        }
    }










    /**
     * 重置密码：通过新旧密码，更新为新密码
     * @param loginEntity 登录账号信息
     * accountName : 账号
     * accountPwd : 旧密码
     *  newPwd : 新密码
     *  @return    ResultMessage
     *  @version <1> 2018-05-10 13:46:16 cxw : Created.
     */
    @Override
    @Transactional
    public ResultMessage updateAccountPwd(LoginParam loginEntity) {
        ResultMessage result = ResultMessage.success();
        if(loginEntity!=null){
                if(StringUtils.isBlank(loginEntity.getAccountName())) {
                    result = ResultMessage.fail(SysConstant.Msg_AccountName_Is_Empty);
                    return  result;
                }
                if(StringUtils.isBlank(loginEntity.getAccountPwd())){
                    result = ResultMessage.fail(SysConstant.Msg_AccountPwd_Is_Empty);
                    return  result;
                }
                if(StringUtils.isBlank(loginEntity.getNewPwd())){
                    result = ResultMessage.fail(SysConstant.Msg_NewPwd_Is_Empty);
                    return  result;
                }
                //查询用户账号信息
                result = checkAccountExists(loginEntity.getAccountName());

                if (!result.isFlag() || result.getData() == null){
                    //账号不存在
                    return result;
                }

                Map<String,Object> account = findAccountByAccountName(loginEntity.getAccountName());//判断账号是否存在

                if(account != null){
                    //判断原密码是否正确
                    if(account.get("accountpwd").toString().equals(loginEntity.getAccountPwd())) {
                        int num = permAccountMapper.updatePwdBySms(loginEntity);
                        if(num>0){
                            result.setMsg(SysConstant.Msg_UpdatePwd_Is_Success);
                        }else{
                            result = ResultMessage.fail(SysConstant.Msg_UpdatePwd_Is_Fail);
                        }
                    } else {
                        result = ResultMessage.fail(SysConstant.Msg_AccountPwd_Is_Error);
                    }
                }else {
                    result = ResultMessage.fail(SysConstant.Msg_AccountName_Not_Exist);
                }
        }else {
            result = ResultMessage.fail(SysConstant.Msg_Account_Is_Empty);
        }
        return result;
    }

    /**
     * 根据用户信息Id查询用户信息
     * @param  personId: 用户信息Id
     *  @return    ResultMessage
     * @version <1> 2018-05-10 13:46:16 cxw : Created.
     */
    @Override
    public ResultMessage findUserById(Integer personId) {
        ResultMessage result = ResultMessage.success();
        if(personId!=null)
        {
            PermPerson user = permPersonMapper.findUserById(personId);
            if(user!=null){
                result.setData(user);
            }
        }
        else{
            result = ResultMessage.fail(SysConstant.Msg_UserInfo_Is_Fail);
        }

        return result;
    }

    /**
     * 修改用户信息
     * @param  user: 用户信息
     * personId：用户信息ID
     * personName：姓名
     * nickName：昵称
     * sex：性别
     * personBorn ：出生日期
     * email：邮箱
     * qq：qq
     * address:地址
     * @return  ResultMessage
     * @return    ResultMessage
     * @version <1> 2018-04-27 13:46:16 Hayden : Created.
     * @version <2> 2018-05-10 13:46:16 cxw : update:方法实现
     */
    @Override
    @Transactional
    public ResultMessage updateUser(PermPerson user) {
        ResultMessage result = ResultMessage.success();
        int num = permPersonMapper.updateUser(user);
        if(num>0){
            result.setMsg(SysConstant.Msg_UpdateUserInfo_Is_Success);
        }
        else{
            result = ResultMessage.fail(SysConstant.Msg_UpdateUserInfo_Is_Fail);
        }
        return result;
    }


    @Override
    public ResultMessage updateWechatIdByAccountName(PermPerson user){

        if(StringUtils.isBlank(user.getWechatId())){
            return ResultMessage.fail("wechatId不能为空");
        }
        permPersonMapper.updateWechatIdByAccountName(user);
        return ResultMessage.success("用户微信关联成功");

    }


    /**
     * 用户注册，生成账号信息与用户信息
     * 1. 检查注册信息的必填项，手机号、密码
     * 2. 为用户生成访问的KEY，构造账号信息并写入
     * 3. 写入用户信息
     * @param registerEntity : 注册信息
     *   phone:手机号
     *   personName：姓名
     *   companyName：公司名称
     *   pwd：密码
     *   smsVerifCode：短信验证码
     *   imgVerifyCode：图形验证码
     * @return  ResultMessage
     * @version <1> 2018-04-27 11:16:16 Hayden : Created.
     * @version <2> 2018-05-09 11:16:16 cxw : update  方法实现，提取wx端与web通用的短信验证码校验方法
     */
    @Override
    public ResultMessage register(RegisterEntity registerEntity) {
        //校验参数
        ResultMessage res = checkRegisterParam(registerEntity);
        if(res.isFlag()){
            //校验手机验证码
            //checkWebSmsVaildCode(registerEntity);
            //添加账号信息
            PermAccount permAccountEntity = new PermAccount();
            permAccountEntity.setAccountName(registerEntity.getPhone());
            permAccountEntity.setAccountPwd(MD5Util.digest(registerEntity.getPwd()));
            permAccountEntity.setCreateTime(LocalDateTime.now());
            permAccountEntity.setDataStatus(DataStatusEnum.Valid.getCode());
//            permAccountEntity.setDelStatus(DelStatusEnum.Normal.getCode());
            permAccountEntity.setServiceToken(UUIDUtil.generateUUID());
            permAccountEntity.setAccountType(AccountTypeEnum.ACCOUNT_TYPE_WEB.getCode());
            permAccountEntity.setAccountCode(registerEntity.getAccountCode());//账号名
            //int num = permAccountMapper.saveAccount(permAccountEntity);
            int num = permAccountService.saveAccount(permAccountEntity);

            //用户注册时保存用户信息，关联默认数据访问权限
            if(num > 0 && permAccountEntity.getAccountId() != null) {

                registerEntity.setPersonType(PersonTypeEnum.PERSON_TYPE_OUTER.getId()); //外部用户

                //保存用户信息
                ResultMessage resUser = saveUserInfo(registerEntity,permAccountEntity.getAccountId());

                //保存用户默认权限
                relateAccountRole.setDefaultPermission(permAccountEntity.getAccountId());

                //保存默认数据访问权限
                ResultMessage resData = saveDeafultDataPermisson(permAccountEntity.getAccountId());
                 if(resUser.isFlag() && resData.isFlag()){
                     res =  ResultMessage.success(SysConstant.Msg_Account_Register_Success);
                 } else {
                     res =  ResultMessage.fail(SysConstant.Msg_Account_Register_Fail);
                 }
            }
            else{
                res =  ResultMessage.fail(SysConstant.Msg_Account_Register_Fail);
            }

        }
        return res;
    }

    /**
     * 用户注册，生成账号信息与用户信息
     * 1. 检查注册信息的必填项，手机号、密码
     * 2. 为用户生成访问的KEY，构造账号信息并写入
     * 3. 写入用户信息
     * @param registerEntity : 注册信息
     *   phone:手机号
     *   personName：姓名
     *   companyName：公司名称
     *   pwd：密码
     *   smsVerifCode：短信验证码
     *   imgVerifyCode：图形验证码
     * @return  ResultMessage
     * @version <1> 2018-04-27 11:16:16 Hayden : Created.
     * @version <2> 2018-05-09 11:16:16 cxw : update  方法实现，提取wx端与web通用的短信验证码校验方法
     */
    @Override
    public ResultMessage forumRegister(RegisterEntity registerEntity) {
        //校验参数
        ResultMessage res = checkFormumRegisterParam(registerEntity);
        if(res.isFlag()){
            //添加账号信息
            PermAccount permAccountEntity = new PermAccount();
            permAccountEntity.setAccountName(registerEntity.getPhone());
            permAccountEntity.setAccountPwd(MD5Util.digest(registerEntity.getPwd()));
            permAccountEntity.setCreateTime(LocalDateTime.now());
            permAccountEntity.setDataStatus(DataStatusEnum.Valid.getCode());
            permAccountEntity.setServiceToken(UUIDUtil.generateUUID());
            permAccountEntity.setAccountType(AccountTypeEnum.ACCOUNT_TYPE_WEB.getCode());
            permAccountEntity.setAccountCode(registerEntity.getAccountCode());//账号名
            permAccountEntity.setNickName(registerEntity.getNickName());
            //int num = permAccountMapper.saveAccount(permAccountEntity);
            int num = permAccountService.saveAccount(permAccountEntity);
            //用户注册时保存用户信息，关联默认数据访问权限
            if(num > 0 && permAccountEntity.getAccountId() != null) {

                registerEntity.setPersonType(PersonTypeEnum.PERSON_TYPE_OUTER.getId()); //外部用户

                //将论坛昵称存入用户名
                registerEntity.setPersonName(permAccountEntity.getNickName());
                //保存用户信息
                ResultMessage resUser = saveUserInfo(registerEntity,permAccountEntity.getAccountId());

                //保存用户默认权限
                relateAccountRole.setDefaultPermission(permAccountEntity.getAccountId());

                //保存默认数据访问权限
                ResultMessage resData = saveDeafultDataPermisson(permAccountEntity.getAccountId());
                if(resUser.isFlag() && resData.isFlag()){
                    res =  ResultMessage.success(SysConstant.Msg_Account_Register_Success);
                } else {
                    res =  ResultMessage.fail(SysConstant.Msg_Account_Register_Fail);
                }
                //刷新用户缓存
                cacheService.updateCacheUser(permAccountEntity.getAccountId());
            }
            else{
                res =  ResultMessage.fail(SysConstant.Msg_Account_Register_Fail);
            }

        }
        return res;
    }

    /**
     * 微信用户注册
     * @param registerEntity
     * @return  ResultMessage
     * @version <1> 2018-05-04 xzg： Created.
     * @version <2> 2018-05-11 13:46:16 cxw : update:提取wx端与web通用的短信验证码校验方法
     * @version <3> 2018-05-20 lxy : update: 修改将checkWxRegisterParam()验证的后的结果要往上面抛出去，这个调用它的方法，才可以判断验证的结果
     */
    @Override
    @Transactional
    public ResultMessage registerWechatUser(RegisterEntity registerEntity) {
        //校验参数
        ResultMessage resultMessage =  checkWxRegisterParam(registerEntity);
        //这里还要判断 SysConstant.Msg_Account_Registered_Code，用户已经在私有云平台已经注册的。但注册微信公众号用户，可以放行，要抛出去
        if(!resultMessage.isFlag() || SysConstant.Msg_Account_Registered_Code.equals(resultMessage.getCode())){
            return resultMessage;
        }

        //校验手机验证码
        resultMessage = buzRedisService.getRedisSmsCode(registerEntity.getPhone(), ImageValidateCodeEnum.Web_Image_code);
        if(!resultMessage.isFlag()){
            return resultMessage;
        }

        //判断验证码是否相等
        String redisCode  = resultMessage.getData().toString();
        if(!registerEntity.getSmsVerifCode().equals(redisCode)){
            return ResultMessage.fail(SysConstant.Msg_Sms_Verification_Not_Equal);
        }
        PermAccount permAccountEntity = new PermAccount();
        permAccountEntity.setAccountName(registerEntity.getPhone());
        permAccountEntity.setAccountPwd(MD5Util.digest(registerEntity.getPwd()));
        permAccountEntity.setCreateTime(LocalDateTime.now());
        permAccountEntity.setDataStatus(DataStatusEnum.Valid.getCode());
//        permAccountEntity.setDelStatus(DelStatusEnum.Normal.getCode());
        permAccountEntity.setAccountType(AccountTypeEnum.ACCOUNT_TYPE_WX.getCode());
        //int num = permAccountMapper.saveAccount(permAccountEntity);
        int num = permAccountService.saveAccount(permAccountEntity);
        //用户注册时保存用户信息，关联默认数据访问权限
        if(num>0&&permAccountEntity.getAccountId()!=null) {
            //保存用户信息
            ResultMessage resUser = saveUserInfo(registerEntity,permAccountEntity.getAccountId());
            //保存默认数据访问权限
            ResultMessage resData = saveDeafultDataPermisson(permAccountEntity.getAccountId());
            if(resUser.isFlag()&&resData.isFlag()){
                return  ResultMessage.success(SysConstant.Msg_Account_Register_Success);
            }
            else{
                return ResultMessage.fail(SysConstant.Msg_Account_Register_Fail);
            }
        }
        return ResultMessage.success(SysConstant.Msg_Account_Register_Success);
    }

    /**
     * 根据手机号判断serviceKey是否存在
     * @param serviceKey
     * @param phone
     * @return
     * @version <1> 2018-05-10 xzg: Created.
     */
    @Override
    public ResultMessage findUserByKey(String serviceKey, String phone) {
        if (StringUtils.isEmpty(serviceKey) || StringUtils.isEmpty(phone)){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("serviceKey",serviceKey);
        params.put("phone",phone);
        Map<String,Object> accountOne = permAccountMapper.findAccountOne(params);
        if (accountOne == null){
            return ResultMessage.fail(SysConstant.Msg_Account_Query_Empty);
        } else {
            return ResultMessage.success(accountOne);
        }
    }

    /**
     * 根据短信修改账号密码(短信验证通过后才可修改密码)
     * @param  loginEntity: 登录信息
     * accountName:账号即手机号
     * accountPwd：密码
     * smsVerifCode:验证码
     * @return  ResultMessage
     * @version <1> 2018-05-10 13:46:16 cxw : Created.
     */
    @Override
    public ResultMessage resetPwd(LoginParam loginEntity) {
        ResultMessage res = ResultMessage.success();
        if(loginEntity!=null){
            if(StringUtils.isBlank(loginEntity.getAccountName()))
            {
                res = ResultMessage.fail(SysConstant.Msg_AccountName_Is_Empty);
                return  res;
            }
            if(StringUtils.isBlank(loginEntity.getAccountPwd())){
                res = ResultMessage.fail(SysConstant.Msg_AccountPwd_Is_Empty);
                return  res;
            } else {
                //base 64  解密
                try {
                    BASE64Decoder base64 = new BASE64Decoder();
                    loginEntity.setAccountPwd(new String(base64.decodeBuffer(loginEntity.getAccountPwd())));
                } catch (IOException e) {
                    return ResultMessage.fail(SysConstant.Msg_LoginAccountPwd_Is_Error);
                }
            }
            //判断手机验证码是否已校验成功
            res = buzRedisService.getRedisSmsCode(loginEntity.getAccountName(),ImageValidateCodeEnum.Web_Image_code);
            if(!res.isFlag()){
                //从redis 中获取验证码为空，可能验证码已过期，或未发送验证码
                return res;
            }


            if(!loginEntity.getSmsVerifCode().equalsIgnoreCase(res.getData().toString())){
                return ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Not_Right);
            }


            //判断手机验证码是否已校验
//            if (!"true".equalsIgnoreCase(res.getData().toString())){
//                res = ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Unchecked);
//                return res;
//            }

            //密码加密
            loginEntity.setAccountPwd(MD5Util.digest(loginEntity.getAccountPwd()));
            int num = permAccountMapper.updatePwdBySms(loginEntity);
            if(num>0){
                res.setMsg(SysConstant.Msg_UpdatePwd_Is_Success);
            }
            else{
                res = ResultMessage.fail(SysConstant.Msg_UpdatePwd_Is_Fail);
            }
        }
        else {
            res = ResultMessage.fail(SysConstant.Msg_Account_Is_Empty);
        }
        return res;
    }

    /**
     * 根据手机号查询用户信息
     * @param userPhone
     * @return
     * @version <1> 2018-05-11 xzg : Created.
     * 若用户信息中的service_token 为空，则先更新service_token
     */
    @Override
    public ResultMessage findUserInfoByPhone(String userPhone) {
        if (StringUtils.isEmpty(userPhone)){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("phone",userPhone);
        Map<String,Object> userInfo = permAccountMapper.findAccountOne(params);

        if(userInfo != null && userInfo.size() > 0){
            if(userInfo.get("serviceKey") == null){ //servicekey 如果为null ，则设置它
                String serviceKey = UUIDUtil.generateUUID();
                PermAccount account= new PermAccount();
                account.setAccountId((Integer)userInfo.get("accountId"));
                account.setServiceToken(serviceKey);
                ResultMessage result = permAccountService.updateServiceKeyByAccountId(account);
                if(result.isFlag()){
                    userInfo.put("serviceKey", serviceKey);
                }else{
                    return ResultMessage.fail("用户信息不完整，无法登录");
                }
            }

            return ResultMessage.success(userInfo);
        }else{
            return ResultMessage.fail("无此用户信息");
        }
    }

    /**
     * 修改密码时验证手机号与验证码是否匹配
     * @param phone
     * @param verifCode
     * @return
     * @version <1> 2018-05-15 xzg： Created.
     */
    public ResultMessage checkPhoneCode(String phone, String verifCode) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(verifCode)){
        return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }

        //获取修改密码的 redis 验证码
        ResultMessage res = buzRedisService.getRedisSmsCode(phone,ImageValidateCodeEnum.Web_Image_code);
        if (!res.isFlag()){
        return res;
        }
        //判断验证码是否正确
        if (!verifCode.equalsIgnoreCase(res.getData().toString())){
        res = ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Not_Right);
        return res;
        }

        //验证码正确，更新reeis值为校验为 true ,表示已校验通过
//        buzRedisService.setRedisSmsCode(phone,"true",ImageValidateCodeEnum.Web_Image_code);

        return ResultMessage.success(SysConstant.Msg_Phone_VerifCode_Right);
    }


//    /**
//     *用户注册时验证手机号验证码是否匹配
//     * @param phone
//     * @param verifCode
//     * @return
//     * @version <1> 2018-05-15 xzg： Created.
//     */
//    public ResultMessage checkPhoneCodeByProduct(String phone, String verifCode) {
//        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(verifCode)){
//            return ResultMessage.fail(SysConstant.Request_Param_Empty);
//        }
//
//        //获取修改密码的 redis 验证码
//        ResultMessage res = buzRedisService.getRedisSmsCode(phone,ImageValidateCodeEnum.Web_Image_code);
//        if (!res.isFlag()){
//            return res;
//        }
//        //判断验证码是否正确
//        if (!verifCode.equalsIgnoreCase(res.getData().toString())){
//            res = ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Not_Right);
//            return res;
//        }
//
//        return ResultMessage.success(SysConstant.Msg_Phone_VerifCode_Right);
//    }

    @Override
    public ResultMessage findUserDsByRegion(String accountToken, Long regionId) {
        if (StringUtils.isBlank(accountToken)){
            return ResultMessage.fail(SysConstant.Param_Login_Token);
        }

        if (regionId == null){
             return ResultMessage.fail(SysConstant.Param_Region_Null);
        }

        Set<Map<String,Object>> regionDs = new LinkedHashSet<Map<String,Object>>();
        boolean isAdmin = AccountTokenUtil.isSuperAdmin(accountToken);
        if (isAdmin){
            //如果是超级管理员角色
            //查询所有数据集
            ResultMessage dictResult = dictService.findDictByParamId(SysConstant.Value_Dict_DataSet_id);
            if (dictResult.isFlag() && dictResult.getData() != null){
                List<Map<String,Object>> dictList = (List<Map<String,Object>>)dictResult.getData();
                for (Map<String,Object> dict : dictList){
                    regionDs.add(generatorDataSetProduct(dict.get("dictId"),dict.get("dataCode"),dict.get("dataName")));
                }
            }
        } else {
            List<Map<String,Object>> productList = AccountTokenUtil.getUserProductsFromRedis(accountToken);//获取用户的数据权限
            if (productList != null){
                for (Map<String,Object> product : productList){
                    String strRegion = product.get("regionId").toString();
                    //if (regionId.longValue() ==  (long)(product.get("regionId"))){
                    if (regionId.longValue() ==  Long.parseLong(strRegion)){
                        //获取指定区域下的数据集
                        regionDs.add(generatorDataSetProduct(product.get("datasetId"),product.get("datasetCode"),product.get("datasetName")));
                    }
                }
            }
        }
        return ResultMessage.success(regionDs);
    }

    @Override
    public ResultMessage findUserDsByAllRegion(String accountToken, Long regionId) {
        if (StringUtils.isBlank(accountToken)){
            return ResultMessage.fail(SysConstant.Param_Login_Token);
        }

        if (regionId == null){
            return ResultMessage.fail(SysConstant.Param_Region_Null);
        }
        Set<Map<String,Object>> regionDs = new LinkedHashSet<Map<String,Object>>();
        //查询所有数据集
        ResultMessage dictResult = dictService.findDictByParamId(SysConstant.Value_Dict_DataSet_id);
        if (dictResult.isFlag() && dictResult.getData() != null){
            List<Map<String,Object>> dictList = (List<Map<String,Object>>)dictResult.getData();
            for (Map<String,Object> dict : dictList){
                regionDs.add(generatorDataSetProduct(dict.get("dictId"),dict.get("dataCode"),dict.get("dataName")));
            }
        }
        return ResultMessage.success(regionDs);
    }

    private Map<String,Object> generatorDataSetProduct(Object datasetId,Object datasetCode,Object datasetName){
        Map<String,Object> ds = new HashMap<String,Object>();
        ds.put("datasetId",datasetId);
        ds.put("datasetCode",datasetCode);
        ds.put("datasetName",datasetName);
        return ds;
    }

    @Override
    public ResultMessage findCropDataTypeByDsRegion(String accountToken, Long regionId, Integer dsId) {
        if (StringUtils.isBlank(accountToken) ||  regionId == null || dsId == null){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        boolean isAdmin = AccountTokenUtil.isSuperAdmin(accountToken);

        Set<Map<String,Object>> cropList = new LinkedHashSet<Map<String,Object>>();
        Set<Map<String,Object>> dataTypeList = new LinkedHashSet<Map<String,Object>>();
        if (isAdmin){
            //管理员，直接读取所有数据
            //查询作物，（降水、地温、气温没有作物）
            if (!dsId.equals(SysConstant.Value_Dict_t_id) && !dsId.equals(SysConstant.Value_Dict_trmm_id)
                    && !dsId.equals(SysConstant.Value_Dict_temp_id)){
                ResultMessage cropResult  = cropService.findCropList(regionId);
                if (cropResult.isFlag() && cropResult.getData() != null){
                    List<Map<String,Object>> crops = (List<Map<String,Object>>) cropResult.getData();
                    for (Map<String,Object> crop : crops){
                        cropList.add(generatorCropProduct(crop.get("cropId"),crop.get("cropName"),crop.get("cropCode")));
                    }
                }
            }

            //查询所有数据源精度
            ResultMessage dsResult = dictService.findDictByParamId(SysConstant.Value_Dict_Accuracy_id);
            if (dsResult.isFlag() && dsResult.getData() != null){
                List<Map<String,Object>> dsList = (List<Map<String,Object>>)dsResult.getData();
                for (Map<String,Object> ds : dsList){
                    dataTypeList.add(generatorAccuracyProduct(ds.get("dictId"),ds.get("dataCode"),ds.get("dataName"),ds.get("dataValue")));
                }
            }

        } else {
            //非管理员，获取对应的权限数据
            List<Map<String,Object>> productList = AccountTokenUtil.getUserProductsFromRedis(accountToken);
            if(productList != null && productList.size() > 0){
                for(Map<String,Object> product : productList){
                    Long redisReginId = product.get("regionId").equals("null") ? null : Long.parseLong(product.get("regionId").toString());
                    Integer redisDatasetId = product.get("datasetId").equals("null") ? null : Integer.parseInt(product.get("datasetId").toString());
                    if(redisReginId != null && redisDatasetId != null && redisReginId.longValue() == regionId.longValue() && dsId.intValue() == redisDatasetId.intValue()){ //获取区域下的数据集

                        Integer cropId = product.get("cropId").equals("null") ? null : Integer.valueOf(product.get("cropId").toString());
                        if(cropId != null){ //作物
                            cropList.add(generatorCropProduct(cropId,product.get("cropName"),product.get("cropCode")));
                        }

                        Integer redisAccuracyId = product.get("accuracyId").equals("null") ? null : Integer.parseInt(product.get("accuracyId").toString());
                        if(redisAccuracyId != null){ //数据源
                            dataTypeList.add(generatorAccuracyProduct(redisAccuracyId,product.get("accuracyCode"),product.get("accuracyName"),product.get("accuracyValue")));
                        }
                    }
                }
            }
        }
        Map<String,Object> cropAndDs = new HashMap<String,Object>();
        cropAndDs.put("cropList",cropList);
        cropAndDs.put("dataTypeList",dataTypeList);
        return ResultMessage.success(cropAndDs);
    }

    @Override
    public ResultMessage findAllCropDataTypeByDsRegion(String accountToken, Long regionId, Integer dsId) {
        if (StringUtils.isBlank(accountToken) ||  regionId == null || dsId == null){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        Set<Map<String,Object>> cropList = new LinkedHashSet<Map<String,Object>>();
        Set<Map<String,Object>> dataTypeList = new LinkedHashSet<Map<String,Object>>();
        //直接读取所有数据
        //查询作物，（降水、地温、气温没有作物）
        if (!dsId.equals(SysConstant.Value_Dict_t_id) && !dsId.equals(SysConstant.Value_Dict_trmm_id)
                && !dsId.equals(SysConstant.Value_Dict_temp_id)){
            ResultMessage cropResult  = cropService.findCropList(regionId);
            if (cropResult.isFlag() && cropResult.getData() != null){
                List<Map<String,Object>> crops = (List<Map<String,Object>>) cropResult.getData();
                for (Map<String,Object> crop : crops){
                    cropList.add(generatorCropProduct(crop.get("cropId"),crop.get("cropName"),crop.get("cropCode")));
                }
            }
        }
        //查询所有数据源精度
        ResultMessage dsResult = dictService.findDictByParamId(SysConstant.Value_Dict_Accuracy_id);
        if (dsResult.isFlag() && dsResult.getData() != null){
            List<Map<String,Object>> dsList = (List<Map<String,Object>>)dsResult.getData();
            for (Map<String,Object> ds : dsList){
                dataTypeList.add(generatorAccuracyProduct(ds.get("dictId"),ds.get("dataCode"),ds.get("dataName"),ds.get("dataValue")));
            }
        }
        Map<String,Object> cropAndDs = new HashMap<String,Object>();
        cropAndDs.put("cropList",cropList);
        cropAndDs.put("dataTypeList",dataTypeList);
        return ResultMessage.success(cropAndDs);
    }


    /**
     * 根据区域，作物，数据集，分辨率获取用户权限时间
     * @param accountToken
     * @param regionId 区域主键
     * @param dsId 数据集主键
     * @param dataTypeId 分辨率主键
     * @param cropId 作物主键
     * @return
     * @version <1> 2018-07-05 cxw : Created.
     */
    @Override
    public ResultMessage dataTimeForPerm(String accountToken, Long regionId, Integer dsId, Integer dataTypeId, Integer cropId) {
        if (StringUtils.isBlank(accountToken) ||  regionId == null || dsId == null || dataTypeId ==null ){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        Map<String,Object> cropProduct = new HashMap<String,Object>();
        DataProduct dataProductEntity = new DataProduct();
        dataProductEntity.setRegionId(regionId);
        dataProductEntity.setCropId(cropId);
        dataProductEntity.setDatasetId(dsId);
        dataProductEntity.setAccuracyId(dataTypeId);
        ResultMessage res = buzRedisService.getUserLoginInfo(accountToken);
        if(res.getData()!=null) {
            Map<String,Object> userData = (Map<String,Object>)res.getData();
            Map<String,Object> userInfo = (Map<String, Object>) userData.get("userInfo");
            if(userInfo!=null)
            {
                if(userInfo.get("accountId")!=null)
                {
                    Integer accountId = Integer.parseInt(userInfo.get("accountId").toString());
                    dataProductEntity.setAccountId(accountId);
                }
            }
        }
        else{
            return ResultMessage.fail(SysConstant.Msg_AccountName_Is_Empty);
        }
        if(cropId == null)
        {
            cropProduct = dataProductMapper.dataTimeForPermCrop(dataProductEntity);
        }
        else{
            cropProduct = dataProductMapper.dataTimeForPerm(dataProductEntity);
        }
        return ResultMessage.success(cropProduct);
    }


    /**
     * 构造作物权限返回字段
     * @param cropId
     * @param cropName
     * @param cropCode
     * @return
     */
    private Map<String,Object> generatorCropProduct(Object cropId,Object cropName,Object cropCode){
        Map<String,Object> cropProduct = new HashMap<String,Object>();
        cropProduct.put("cropId",cropId);
        cropProduct.put("cropCode",cropCode);
        cropProduct.put("cropName",cropName);
        return cropProduct;
    }

    private Map<String,Object> generatorAccuracyProduct(Object accuracyId,Object accuracyCode,Object accuracyName,Object accuracyValue){
        Map<String,Object> dataTypeProduct = new HashMap<String,Object>();
        dataTypeProduct.put("accuracyId",accuracyId);
        dataTypeProduct.put("accuracyCode",accuracyCode);
        dataTypeProduct.put("accuracyName",accuracyName);
        dataTypeProduct.put("accuracyValue",accuracyValue);
        return dataTypeProduct;
    }

    /**
     * 检测web端注册信息参数
     * @param registerEntity : 注册信息
     * @return ResultMessage
     * @version <1> 2018-05-09 11:16:16 cxw : create
     */
    private ResultMessage checkRegisterParam(RegisterEntity registerEntity){
        ResultMessage res = ResultMessage.success();
        if(StringUtils.isBlank(registerEntity.getPhone())){
            res = ResultMessage.fail(SysConstant.Msg_AccountName_Is_Empty);
            return res;
        }
        if(StringUtils.isBlank(registerEntity.getPwd())){
            res = ResultMessage.fail(SysConstant.Msg_AccountPwd_Is_Empty);
            return res;
        }

        if (StringUtils.isBlank(registerEntity.getConfirmPwd())){
            res = ResultMessage.fail(SysConstant.Msg_AccountConfirmPwd_Is_Empty);
            return res;
        }

        if (!registerEntity.getPwd().equals(registerEntity.getConfirmPwd())){
            res = ResultMessage.fail(SysConstant.Msg_AccountTwicePwd_Not_Equal);
            return res;
        } else {
            try {
                //密码 base64 解密
                BASE64Decoder base64 = new BASE64Decoder();
                registerEntity.setPwd(new String(base64.decodeBuffer(registerEntity.getPwd())));
            } catch (IOException e) {
                return ResultMessage.fail(SysConstant.Msg_LoginAccountPwd_Is_Error);
            }
        }

//        if(StringUtils.isBlank(registerEntity.getPersonName())){
//            res = ResultMessage.fail(SysConstant.Msg_UserName_Is_Empty);
//            return res;
//        }
//        if(StringUtils.isBlank(registerEntity.getCompanyName())){
//            res = ResultMessage.fail(SysConstant.Msg_Company_Is_Empty);
//            return res;
//        }
      /*  if(StringUtils.isBlank(registerEntity.getImgVerifyCode())){
            res = ResultMessage.fail(SysConstant.Msg_Img_VerifCode_Empty);
            return res;
        }*/

        //判断传递的短信验证码是或否和redis缓存的短信验证码一致
        if(StringUtils.isBlank(registerEntity.getSmsVerifCode())){
            res = ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Empty);
            return res;
        }

        res = buzRedisService.getRedisSmsCode(registerEntity.getPhone(),ImageValidateCodeEnum.Web_Image_code);
        if(res.isFlag() && !registerEntity.getSmsVerifCode().equalsIgnoreCase(res.getData().toString())){
            res = ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Not_Right);
            return res;
        }
        return res;
    }

    /**
     * 检测wx端注册信息参数
     * @param registerEntity : 注册信息
     * @return ResultMessage
     * @version <1> 2018-05-11 11:16:16 cxw : create
     * @version <3> 2018-05-20 lxy : update: 修改 已经在私有云平台的用户，返回ResultMessage，新增了SysConstant.Msg_Account_Registered_Code这个状态码
     */
    private ResultMessage checkWxRegisterParam(RegisterEntity registerEntity){
        ResultMessage res = ResultMessage.success();
        String phone = registerEntity.getPhone();
        if(phone == null || phone.trim().isEmpty()){
            return ResultMessage.fail(SysConstant.Msg_AccountName_Is_Empty);
        }else if(!FormUtils.isMobile(phone)){
            return ResultMessage.fail(SysConstant.Msg_AccountName_Format_Error);
        }

        //用户检查,判断是否已注册
        Map<String,Object> account = findAccountByAccountName(phone);
        if(account != null){
            //用户已注册
            return ResultMessage.success(SysConstant.Msg_Account_Registered_Code,SysConstant.Msg_Account_Registered,null);
        }

        String pwd = registerEntity.getPwd();
        if(pwd == null || pwd.trim().isEmpty()){
            return ResultMessage.fail(SysConstant.Msg_AccountPwd_Is_Empty);
        }else if(pwd.length() < SysConstant.AccountPwd_Min_Length || pwd.length() > SysConstant.AccountPwd_Max_Length){
            return ResultMessage.fail(SysConstant.Msg_AccountName_Length_Error);
        }
        String verifCode = registerEntity.getSmsVerifCode();
        if(verifCode == null || verifCode.trim().isEmpty()){
            return ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Empty);
        }
        return res;
    }


    /**
     * 查询正常、有效的指定手机号账号
     * @param accountName
     * @return
     * @version <1> 2018-05-14 xzg : Created.
     */
    private Map<String,Object> findAccountByAccountName(String accountName){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("phone",accountName);
        params.put("delFlag",DelStatusEnum.Normal.getCode());
        params.put("dataStatus",DataStatusEnum.Valid.getCode());
        Map<String,Object> account = permAccountMapper.findAccountOne(params);
        return account;
    }



    /**
     * 注册账号时保存用户信息
     * @param registerEntity 注册信息
     * @param accountId 账号ID
     * @return ResultMessage
     * @version <1> 2018-05-15 cxw : Created.
     */
    private ResultMessage saveUserInfo(RegisterEntity registerEntity,Integer accountId){
        ResultMessage res = ResultMessage.success();

        PermPerson permPerson = new PermPerson();
        permPerson.setPersonType(registerEntity.getPersonType());
        permPerson.setCompany(registerEntity.getCompanyName());
        permPerson.setAccountId(accountId);
        permPerson.setPersonName(registerEntity.getPersonName());
        permPerson.setMobile(registerEntity.getPhone());
        permPersonMapper.save(permPerson);

        return res;
    }

    /**
     * 注册账号时保存默认数据权限
     * @param accountId 账号ID
     * @return ResultMessage
     * @version <1> 2018-05-15 cxw : Created.
     */
    @Override
    public ResultMessage saveDeafultDataPermisson(Integer accountId){
        ResultMessage res = ResultMessage.success();

        Integer[] cropIds = SysConstant.Product_CropIds;
        if(cropIds != null && cropIds.length > 0){
            for(Integer cropId : cropIds){
                //封装注册用户默认数据权限
                DataProduct dp = new DataProduct();
                dp.setAccountId(accountId);
                dp.setCreator(accountId);
                dp.setRegionId(SysConstant.Product_RegionId);
                dp.setDatasetId(SysConstant.Product_DatasetId);
                dp.setAccuracyId(SysConstant.Product_AccuracyId);
                dp.setCropId(cropId);
                dp.setDefaultShow(true);
                dp.setDataStatus(DataStatusEnum.Valid.getCode());
                dp.setProductType(SysConstant.Product_Type);
                dp.setProductStartDate(DateUtil.StringToDateYMD(SysConstant.Product_StartDate));
                dp.setProductEndDate(DateUtil.StringToDateYMD(SysConstant.Product_EndDate));
                int num  = dataProductMapper.addDataProduct(dp);//保存数据权限
                if(num==0){
                    return ResultMessage.fail(SysConstant.Product_Default_Fail);
                }
            }
        }
        return res;
    }

    /**
     * 检测web端注册信息参数
     * @param registerEntity : 注册信息
     * @return ResultMessage
     * @version <1> 2018-05-09 11:16:16 cxw : create
     */
    private ResultMessage checkFormumRegisterParam(RegisterEntity registerEntity){
        ResultMessage res = ResultMessage.success();
        if(StringUtils.isBlank(registerEntity.getPhone())){
            res = ResultMessage.fail(SysConstant.Msg_AccountName_Is_Empty);
            return res;
        }
        if(StringUtils.isBlank(registerEntity.getPwd())){
            res = ResultMessage.fail(SysConstant.Msg_AccountPwd_Is_Empty);
            return res;
        }

        try {
            //密码 base64 解密
            BASE64Decoder base64 = new BASE64Decoder();
            registerEntity.setPwd(new String(base64.decodeBuffer(registerEntity.getPwd())));
        } catch (IOException e) {
            return ResultMessage.fail(SysConstant.Msg_LoginAccountPwd_Is_Error);
        }

        //判断传递的短信验证码是或否和redis缓存的短信验证码一致
        if(StringUtils.isBlank(registerEntity.getSmsVerifCode())){
            res = ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Empty);
            return res;
        }

        res = buzRedisService.getRedisSmsCode(registerEntity.getPhone(),ImageValidateCodeEnum.Web_Image_code);
        if(res.isFlag() && !registerEntity.getSmsVerifCode().equalsIgnoreCase(res.getData().toString())){
            res = ResultMessage.fail(SysConstant.Msg_Phone_VerifCode_Not_Right);
            return res;
        }
        return res;
    }

}
