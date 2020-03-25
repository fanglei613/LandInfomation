package com.jh.util.sms;


import com.jh.vo.ResultMessage;

/**
 * @description: 发送短消息
 * @version <1> 2018/1/22 djh： Created.
 */
public interface SMSUtil {

    /**
     * 发送验收短信
     *  @version <1>2016-12-13 zhaoyong
     *  @param moblie 接收短信手机号码
     * @return
     */
    public ResultMessage sendVerifyMessage(String moblie);

}