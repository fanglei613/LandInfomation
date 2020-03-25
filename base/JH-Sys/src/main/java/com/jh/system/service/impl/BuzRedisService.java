package com.jh.system.service.impl;

import com.jh.constant.PropertiesConstant;
import com.jh.constant.SysConstant;
import com.jh.enums.ImageValidateCodeEnum;
import com.jh.system.mapping.IDataProductMapper;
import com.jh.system.service.IBuzRedisService;
import com.jh.system.service.IRelateAccountRoleService;
import com.jh.system.service.IUserService;
import com.jh.util.PropertyUtil;
import com.jh.util.RedisUtil;
import com.jh.util.UUIDUtil;
import com.jh.vo.ResultMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @version <1> 2018-05-11 xzg : Created.
 */
@Service
public class BuzRedisService implements IBuzRedisService{

	
	private static Logger logger = LoggerFactory.getLogger(IBuzRedisService.class);
	
    @Autowired
    private IUserService userService;
    @Autowired
    private IDataProductMapper dataProductMapper;
    @Autowired
    private IRelateAccountRoleService relateAccountRoleService;






    /**
     * 缓存用户信息
     * @param userPhone
     * @param useProductFilterFlag
     * @return
     *
     */
    @Override
    public ResultMessage setUserLoginInfo(String userPhone, boolean useProductFilterFlag) {
        if (StringUtils.isEmpty(userPhone)){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }

        Map<String,Object> redisValue = new HashMap<String,Object>();

        //缓存用户信息
        ResultMessage userInfoMes = userService.findUserInfoByPhone(userPhone);
        Integer accountId = null;
        Map<String,Object> userInfo = new HashMap<String, Object>();

        if (userInfoMes.isFlag()){
            userInfo = (Map<String,Object>) userInfoMes.getData();
            accountId = Integer.valueOf(userInfo.get("accountId").toString());
            for (Map.Entry<String,Object> p : userInfo.entrySet()){
                //时间类型 转 json 字符串时 异常，解决方法 日期类型转成字符串
                if (p.getValue() instanceof Date){
                    userInfo.put(p.getKey(),p.getValue().toString());
                }
            }
        }
        redisValue.put("userInfo",userInfo);

        //缓存用户数据权限
//        List<Map<String,Object>> productList =  dataProductMapper.findProductsByAccountId(accountId);
//        for (Map<String,Object> product : productList){
//            for (Map.Entry<String,Object> p : product.entrySet()){
//                //时间类型 转 json 字符串时 异常，解决方法 日期类型转成字符串
//                if (p.getValue() instanceof Date){
//                    product.put(p.getKey(),p.getValue().toString());
//                }
//            }
//        }
//        redisValue.put("productList",productList);

//        //缓存账号角色
//        ResultMessage resultRoleMsg = relateAccountRoleService.findRolesByAccountId(accountId);
//        List<Map<String,Object>> roleList = null;
//        if (resultRoleMsg.isFlag()){
//            roleList = (List<Map<String,Object>>)resultRoleMsg.getData();
//        }
//        redisValue.put("roleList",roleList);
//        for(int i=0;i<roleList.size();i++){
//            if((roleList.get(i).get("roleCode"))!=null)
//            {
//                String rcode = roleList.get(i).get("roleCode").toString();
//                if(!rcode.equals("Role_Admin")){
//                    useProductFilterFlag = true;
//                    break;
//                }
//            }
//        }
//        redisValue.put(ConstantUtil.USEPRODUCTFILTER_KEY, useProductFilterFlag);

        String redisKey = UUIDUtil.generateUUID();

        redisValue.put(SysConstant.Key_Login_Token, redisKey); //token
//        过期时间
        Integer expireSecond = Integer.valueOf(PropertyUtil.getPropertiesForConfig("User_Login_Expire"));
        RedisUtil.setJsonStr( redisKey,redisValue,expireSecond);

        return ResultMessage.success(redisKey,redisValue);

    }


    @Override
    public ResultMessage setUserLoginInfo(Map<String, Object> userLoginInfo) {

        if (userLoginInfo == null || userLoginInfo.size() == 0 ){
            return ResultMessage.fail("用户登录失败");
        }
        String redisKey = userLoginInfo.get(SysConstant.Key_Login_Token).toString();
        Integer expireSecond = Integer.valueOf(PropertyUtil.getPropertiesForConfig("User_Login_Expire"));
        RedisUtil.setJsonStr( redisKey,userLoginInfo,expireSecond);

        return ResultMessage.success(userLoginInfo);
    }

    @Override
    public ResultMessage getUserLoginInfo(String accountToken) {
        if (StringUtils.isEmpty(accountToken)){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        Map<String, Object> userData = RedisUtil.getJsonToMap(accountToken);
        if (userData != null){
            return ResultMessage.success(userData);
        } else {
            return ResultMessage.fail(SysConstant.Msg_Redis_UserInfo_Empty);
        }
    }

    @Override
    public ResultMessage setRedisSmsCode(String userPhone, String smsCode, ImageValidateCodeEnum smsType) {
        if (StringUtils.isEmpty(userPhone) || StringUtils.isEmpty(smsCode) || smsType == null){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        //短信过期时间
        Integer expireSecond = Integer.valueOf(PropertyUtil.getPropertiesForConfig("RLY_VERIFYCODE_TIME", PropertiesConstant.SMS_CONFIG)) * 60;
        String redisKey = smsType.getRedisCode() + userPhone;
        RedisUtil.set(redisKey,smsCode,expireSecond);
        return ResultMessage.success(redisKey,smsCode);
    }

    @Override
    public ResultMessage getRedisSmsCode(String userPhone, ImageValidateCodeEnum smsType) {
        if (StringUtils.isEmpty(userPhone) || smsType == null){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        String redisKey = smsType.getRedisCode() + userPhone;
        String smsCode = RedisUtil.get(redisKey);
        if (StringUtils.isEmpty(smsCode)){
            return ResultMessage.fail(SysConstant.Msg_Redis_SmsCode_Empty);
        }
        return ResultMessage.success(smsCode,smsCode);
    }

    @Override
    public ResultMessage setRedisImageValidateCode(String validToken, String imageCode, ImageValidateCodeEnum imageType) {
        if (StringUtils.isEmpty(validToken) || StringUtils.isEmpty(imageCode) || imageType == null){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        // 图形验证码过期时间
        Integer expireSecond = Integer.valueOf(PropertyUtil.getPropertiesForConfig("ImgCode_Expire"));
        String redisKey = imageType.getRedisCode() + validToken;
        RedisUtil.set(redisKey,imageCode,expireSecond);
        return ResultMessage.success(redisKey,redisKey);
    }

    @Override
    public ResultMessage getRedisImageValidateCode(String validToken, ImageValidateCodeEnum imageType) {
        if (StringUtils.isEmpty(validToken) || imageType == null){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        String redisKey = imageType.getRedisCode() + validToken;
        String imageCode =  RedisUtil.get(redisKey);
        
        
        if (StringUtils.isEmpty(imageCode)){
        	
        	logger.info("redisKey：        "+redisKey+"    imageCode    "+imageCode);
        	
            return ResultMessage.fail(SysConstant.Msg_Redis_ImgCode_Empty);
        }
        return ResultMessage.success(imageCode,imageCode);
    }
}
