package com.jh.system.controller;

import com.jh.util.sms.EmailHelper;
import com.jh.util.sms.EmailParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 邮箱接口
 * @version <1> 2018-06-07 cxw： Created.
 */
@Api(value = "邮箱接口")
@RestController
@RequestMapping("/nolog/email")
public class NoLogEmailController {


    /**
     *邮件发送
     * @param personName 姓名
     * @param mobile 电话号码
     * @param email 邮件参数
     * @param company 公司
     * @param remark 说明
     * @return ResultMessage
     * @version <1> 2018-06-07 cxw： Created.
     */
    @ApiOperation(value = "邮件发送",notes = "邮件发送")
    @ApiImplicitParam(name = "EmailParam",value = "邮件参数",required = false,dataType = "EmailParam")
    @GetMapping("/sendEmail")
    public String sendEmail(String callback, String personName,String mobile,String email,String company,String remark){
        String success = "{\"flag\":\"true\", \"msg\":\"邮件发送成功\"}";
        String fail = "{\"flag\":\"false\", \"msg\":\"邮件发送失败\"}";
        String retStr = callback + "(" + success + ")";  //jsonp跨区返回参数
        //判断参数是否为空
        if(StringUtils.isBlank(mobile)){
            retStr = callback + "(" + fail+ ")";
        }
        if(StringUtils.isBlank(remark)){
            retStr = callback + "(" + fail + ")";
        }
        EmailParam emailParam = new EmailParam();
        emailParam.setPersonName(personName);
        emailParam.setMobile(mobile);
        emailParam.setCompany(company);
        emailParam.setEamil(email);
        emailParam.setRemark(remark);
        Boolean res = EmailHelper.sendEmail(emailParam);
        if(res){
            retStr = callback + "(" + success + ")";
        }
        else{
            retStr = callback + "(" + fail + ")";
        }

        return retStr;
    }


}
