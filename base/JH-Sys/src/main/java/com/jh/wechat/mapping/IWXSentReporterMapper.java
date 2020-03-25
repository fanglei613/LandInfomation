package com.jh.wechat.mapping;

import com.jh.wechat.entity.WXSentReporterEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 记录微信用户已经推送的模板消息
 * @version <1> 2018-05-16 lxy： Created.
 */
@Mapper
public interface IWXSentReporterMapper {


    /**
     * 批量保存微信用户已经推送的模板消息
     * @param sentReporters 微信用户已经推送的模板消息
     * @return 返回结果
     * @version <1> 2018-05-16 lxy： Created.
     */
    Integer saveWXSentReporter(@Param("sentReporters") List<WXSentReporterEntity> sentReporters);

    /**
     * 根据多个微信ID ，获取 已发送简报消息实体
     * @param wxIds 多个微信编号
     * @return 返回查找到的已发送的简报消息实体。
     */
    List<WXSentReporterEntity> findSentReporterByWxids(@Param("wxIds") List<String> wxIds);

}
