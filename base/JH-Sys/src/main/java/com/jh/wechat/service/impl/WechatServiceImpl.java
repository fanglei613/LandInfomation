package com.jh.wechat.service.impl;

import com.jh.constant.SysConstant;
import com.jh.system.model.RegisterEntity;
import com.jh.util.PropertyUtil;
import com.jh.wechat.entity.WXRegionCropEntity;
import com.jh.wechat.entity.WXRelateAccountEntity;
import com.jh.wechat.entity.WXSentReporterEntity;
import com.jh.wechat.enums.WechatUserEnum;
import com.jh.wechat.mapping.IWXRegionCropMapper;
import com.jh.wechat.mapping.IWXRelateAccountMapper;
import com.jh.wechat.mapping.IWXSentReporterMapper;
import com.jh.system.service.IUserService;
import com.jh.wechat.service.IWechatService;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信接口
 * @version <1> 2018-05-08 xzg：Created
 * @version <2> 2018-05-16 lxy：Updated 添加了 保存已发送消息模板
 */
@Service
@Transactional
public class WechatServiceImpl implements IWechatService {

    @Autowired
    private IWXRelateAccountMapper relateAccountMapper;
    @Autowired
    private IWXRegionCropMapper regionCropMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private IWXSentReporterMapper sentReporterMapper;

    @Override
    public ResultMessage addWechatUser(WXRelateAccountEntity relateAccount) {
        /**
         *
         * 注册账号 ，并添加微信号 跟 手机号 关联记录
         */
        if (relateAccount == null || StringUtils.isEmpty(relateAccount.getTel())
                || StringUtils.isEmpty(relateAccount.getVerifCode()) || StringUtils.isEmpty(relateAccount.getWxId())){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }

        WXRelateAccountEntity saveAccount =  relateAccountMapper.findWxUserByWxid(relateAccount.getWxId());
        if (saveAccount != null){
            return ResultMessage.fail(SysConstant.Msg_Account_Registered);
        }

        RegisterEntity registerEntity = new RegisterEntity();
        registerEntity.setPhone(relateAccount.getTel());
        registerEntity.setPwd(PropertyUtil.getPropertiesForConfig("LOGIN_DEFAULT_PASSWORD"));//默认密码123456
        registerEntity.setSmsVerifCode(relateAccount.getVerifCode());
        ResultMessage returnRegister = userService.registerWechatUser(registerEntity);

        if (returnRegister.isFlag()){
            //用户账号添加成功
            Integer insertCount = relateAccountMapper.insertRelateAccount(relateAccount);
            if (insertCount != null && insertCount == 1){
                return ResultMessage.success(SysConstant.Msg_Account_Register_Success);
            } else {
                return ResultMessage.fail(SysConstant.Msg_Account_Register_Fail);
            }
        } else {
            //注册失败
            return  returnRegister;
        }
    }

    @Override
    public ResultMessage addSubscribeBriefing(WXRegionCropEntity regionCrop) {
        //1、保存用户订阅的简报 区域、作物
        if (regionCrop == null || StringUtils.isEmpty(regionCrop.getWxId()) || regionCrop.getRegionId() == null
                || regionCrop.getCropIdList() == null || regionCrop.getCropIdList().length == 0){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }

        WXRelateAccountEntity relateAccount = relateAccountMapper.findWxUserByWxid(regionCrop.getWxId());
        if (relateAccount == null){
            return ResultMessage.fail(WechatUserEnum.NOT_REGIST.getKey(),WechatUserEnum.NOT_REGIST.getMessage(),null);
        }

        regionCrop.setTel(relateAccount.getTel());
        regionCropMapper.deleteByTel(relateAccount.getTel());//在新增订阅信息之前就得删除原先订阅的简报信息
        Integer insertCount = regionCropMapper.insertRegionCrop(regionCrop);
        if (insertCount != null && insertCount > 0){
            return ResultMessage.success(WechatUserEnum.BRIEFING_SUCCESS.getKey(),WechatUserEnum.BRIEFING_SUCCESS.getMessage(),null);
        } else {
            return ResultMessage.fail(WechatUserEnum.BRIEFING_FAIL.getKey(),WechatUserEnum.BRIEFING_FAIL.getMessage(),null);
        }
    }

    /**
     * 保存微信用户已经推送的模板消息。
     * @param reporters 记录已发送的微信消息实体
     * @return 返回操作结果
     * @version <1> 2018-05-16 lxy：Created
     */
    @Override
    public ResultMessage saveWxSentReporter(List<WXSentReporterEntity> reporters) {
        try{
            sentReporterMapper.saveWXSentReporter(reporters);
            return ResultMessage.success();
        }catch(Exception e){
            e.printStackTrace();
            return ResultMessage.fail(e.getMessage());
        }

    }

    /**
     * 根据微信编号查询 微信用户已经推送的模板消息。
     * @param wxIds 多个微信编号
     * @return 返回微信用户已经推送的模板消息。
     * @version <1> 2018-05-16 lxy：Created
     */
    @Override
    public ResultMessage findWxSentReporterByWxIds(List<String> wxIds) {
        List<WXSentReporterEntity> reporterList = sentReporterMapper.findSentReporterByWxids(wxIds);
        if(reporterList.size() == 0){
            return ResultMessage.fail(SysConstant.Msg_Wechat_FindSentReporter_NotExist);
        }
        //按照wxId为键，WXSentReporterEntity为值来保存数据。
        Map<String,Object> resultMap = new HashMap<>();
        for(WXSentReporterEntity reporter:reporterList){
            String wxId = reporter.getWxId();//微信编号
            Long reporterId  = reporter.getReporterId();//简报编号
            resultMap.put(wxId+"-"+reporterId,reporter);//wxId+"-"+reporterId，组合成键，reporter做为值
        }
        return ResultMessage.success(resultMap);
    }

}
