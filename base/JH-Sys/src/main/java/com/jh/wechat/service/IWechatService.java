package com.jh.wechat.service;


import com.jh.vo.ResultMessage;
import com.jh.wechat.entity.WXRegionCropEntity;
import com.jh.wechat.entity.WXRelateAccountEntity;
import com.jh.wechat.entity.WXSentReporterEntity;

import java.util.List;

/**
 * 微信简报
 * @version <1> 2018-05-08 xzg： Created.
 * @version <2> 2018-05-16 lxy：Updated 保存微信用户已经推送的模板消息。
 */
public interface IWechatService {

    /**
     * 通过微信注册
     * @return
     * @version <1> 2018-05-08 xzg： Created.
     */
    ResultMessage addWechatUser(WXRelateAccountEntity relateAccount);

    /**
     * 保存微信用户订阅的区域、作物
     * @return 返回操作结果
     * @version <1> 2018-05-08 xzg： Created.
     */
    ResultMessage addSubscribeBriefing(WXRegionCropEntity regionCrop);

    /**
     * 保存微信用户已经推送的模板消息。
     * @param reporters 记录已发送的微信消息实体
     * @return 返回操作结果
     * @version <1> 2018-05-16 lxy：Created
     */
    ResultMessage saveWxSentReporter(List<WXSentReporterEntity> reporters);

    /**
     * 根据微信编号查询 微信用户已经推送的模板消息。
     * @param wxIds 多个微信编号
     * @return 返回微信用户已经推送的模板消息。
     * @version <1> 2018-05-16 lxy：Created
     */
    ResultMessage findWxSentReporterByWxIds(List<String> wxIds);

}
