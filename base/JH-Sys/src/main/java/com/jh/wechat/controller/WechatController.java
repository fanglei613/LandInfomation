package com.jh.wechat.controller;

import com.jh.wechat.entity.WXRegionCropEntity;
import com.jh.wechat.entity.WXRelateAccountEntity;
import com.jh.wechat.entity.WXSentReporterEntity;
import com.jh.vo.ResultMessage;
import com.jh.wechat.service.IWechatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 微信接口
 * @version <1> 2018-05-08 xzg：Created
 * @version <2> 2018-05-16 lxy：Updated 添加保存已发送的微信消息
 */
@Api(value = "对接微信相关接口")
@RestController
@RequestMapping("/nolog/wechat")
public class WechatController {

    @Autowired
    private IWechatService wechatService;

    /**
     * 微信用户注册
     * 默认密码123456
     * @param relateAccount  注册手机号  微信号
     * @return
     */
    @ApiOperation(value = "微信用户注册")
    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    public ResultMessage regionUserByWechat(@RequestBody WXRelateAccountEntity relateAccount){
        return wechatService.addWechatUser(relateAccount);
    }

    /**
     * 微信用户订阅简报
     * @param regionCrop   区域id  作物id   订阅手机号
     * @return
     */
    @ApiOperation(value = "微信用户订阅简报")
    @RequestMapping(value = "/subscribe",method = RequestMethod.POST)
    public ResultMessage subscribeBriefing(@RequestBody WXRegionCropEntity regionCrop){
        return wechatService.addSubscribeBriefing(regionCrop);
    }

    /**
     * 根据微信编号获取已发送的简报消息
     * @param wxIds 多个微信编号
     * @return 返回已发送的简报消息
     * @version <1> 2018-05-16 lxy：Created
     */
    @ApiOperation(value = "根据微信编号获取已发送的简报消息")
    @RequestMapping(value = "/findWxSentReporterByWxIds",method = RequestMethod.POST)
    public ResultMessage findWxSentReporterByWxIds(@RequestBody List<String> wxIds){
        return wechatService.findWxSentReporterByWxIds(wxIds);
    }

    /**
     * 保存已发送的微信消息
     * @param reporters 要保存的消息
     * @return 返回操作的结果
     * @version <1> 2018-05-16 lxy：Created
     */
    @ApiOperation(value = "保存已发送的微信消息")
    @RequestMapping(value = "/saveWxSentReporter",method = RequestMethod.POST)
    public ResultMessage saveWxSentReporter(@RequestBody List<WXSentReporterEntity> reporters){
        return wechatService.saveWxSentReporter(reporters);
    }
}
