/**
* 业务相关的部分数据会存放在Redis服务器中，如会话数据、临时数据等。
* 1. 用户登录系统后，将临时Token与用户信息保存在Redis中
* 2. 获取手机验证码时，将手机号与验证码保存在Redis中
* 
* @version <1> 2018-05-09 16:04:29 Hayden : Created.
*/

package com.jh.system.service;

import com.jh.enums.ImageValidateCodeEnum;
import com.jh.vo.ResultMessage;

import java.util.Map;

public interface IBuzRedisService {


    /**
     * 将该用户的账号信息、用户信息保存到redis 中
     * @param userPhone
     * @return  返回  mes = rediskey  ,data = userinfo
     * @version <1> 2018-05-11 xzg : Created.
     */
    ResultMessage setUserLoginInfo(String userPhone,boolean useProductFilterFlag);

    /**
     * 将用户登录的账号信息保存至redis中
     * @param userLoginInfo
     * @return
     */
    ResultMessage setUserLoginInfo(Map<String,Object> userLoginInfo);

    /**
     * 获取登录用户的账号信息、用户信息
     * @param accountToken
     * @return
     * @version <1> 2018-05-11 xzg : Created.
     */
    ResultMessage getUserLoginInfo(String accountToken);


    /**
     * 保存用户手机验证码 到 redis
     * @param userPhone  用户手机号
     * @param smsCode    手机验证码
     * @param smsType     短信验证码类型
     * @return
     * @version <1> 2018-05-15 xzg : Created.
     */
    ResultMessage setRedisSmsCode(String userPhone, String smsCode, ImageValidateCodeEnum smsType);

    /**
     * 从 redis 中取得 用户的短信验证码
     * @param userPhone    用户的手机号
     * @param smsType       短信验证码类型
     * @return
     * @version <1> 2018-05-15 xzg : Created.
     */
    ResultMessage getRedisSmsCode(String userPhone, ImageValidateCodeEnum smsType);


    /**
     *  保存图形验证码到redis
     * @param validToken    图形验证码唯一码
     * @param imageCode     图形验证码值
     * @param imageType     图形验证码类型
     * @return
     * @version <1> 2018-05-15 xzg : Created.
     */
    ResultMessage setRedisImageValidateCode(String validToken, String imageCode, ImageValidateCodeEnum imageType);

    /**
     * 从redis 中取得 图形验证码
     * @param validToken   图形验证码唯一码
     * @param imageType    图形验证码类型
     * @return
     * @version <1> 2018-05-15 xzg : Created.
     */
    ResultMessage getRedisImageValidateCode(String validToken, ImageValidateCodeEnum imageType);


}