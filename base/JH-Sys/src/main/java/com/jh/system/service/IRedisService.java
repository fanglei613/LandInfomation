package com.jh.system.service;

import com.jh.vo.ResultMessage;

import java.util.Map;

/**
 * redis操作接口
 * @version <1> 2018-05-11 cxw： Created.
 */
public interface IRedisService {

    /**
     * 缓存短信验证码
     * @param key 验证码标识
     * @param value 验证码
     * @param   second 缓存时间(秒)
     * @version <1> 2018-05-11 cxw： Created.
     */
    public ResultMessage saveSmsValidCode(String key, String value, int second);

    /**
     * 根据key获取短信验证码
     * @param key 验证码标识
     * @version <1> 2018-05-11 cxw： Created.
     */
    public ResultMessage getSmsValidCode(String key);

    /**
     * 缓存登录账号信息
     * @param key 账号标识
     * @param value 账号信息
     * @param   second 缓存时间(秒)
     * @version <1> 2018-05-11 cxw： Created.
     */
    public ResultMessage saveUserAccoutInfo(String key, Map<String, String> value, int second);

    /**
     * 根据key获取登录账号信息
     * @param key 账号标识
     * @version <1> 2018-05-11 cxw： Created.
     */
    public ResultMessage getUserAccoutInfo(String key);
}
