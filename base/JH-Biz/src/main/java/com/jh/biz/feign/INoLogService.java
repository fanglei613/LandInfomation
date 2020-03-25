package com.jh.biz.feign;
/**
* 用户登录接口
* @version Hayden 2018-08-07 16:11:16 : Created.
*/

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="jh-sys")
public interface INoLogService{ 
	/**
	* App账号登录
	* 用于App通过账号、密码、及设备MAC地址登录
	* @version Hayden 2018-08-07 16:18:10 : Created.
	*/
	@PostMapping(value = "/nolog/user/loginForApp")
	public ResultMessage loginForApp(
						@RequestParam(name="accountNo")String accountNo,
						@RequestParam(name="pwd")String pwd
						);


    /**
     * 珈和产品登录
     * @version wl 2018-08-17 : Created.
     */
    @PostMapping(value = "/nolog/user/loginForProduct")
    public ResultMessage loginForProduct(
            @RequestParam(name="accountNo")String accountNo,
            @RequestParam(name="pwd")String pwd,
            @RequestParam(name="regionId") Long regionId
    );

    /**
     * 珈和产品登录
     * @version wl 2018-08-17 : Created.
     */
    @PostMapping(value = "/nolog/user/loginForWx")
    public ResultMessage loginForWx(
            @RequestParam(name="accountNo")String accountNo,
            @RequestParam(name="openId")String openId,
            @RequestParam(name="regionId")Long regionId

    );
    /**
     * 珈和产品登录
     * @version wl 2018-08-20 : Created.
     */
    @PostMapping(value = "/nolog/user/registerProduct")
    public ResultMessage registerProduct(@RequestParam(name="phone")String phone,@RequestParam(name="pwd")String pwd,@RequestParam(name="companyName")String companyName);

    /**
     * 检查账号是否存在或者手机号是否已经注册
     * @version wl 2018-08-20 : Created.
     */
    @GetMapping(value ="/nolog/user/checkAccountExists")
    public ResultMessage checkAccountExists(@RequestParam(name="accountName")String accountName);

    /**
     * 发送注册的短信验证码
     * @version wangli [2018-08-16 17:47:09] : Created.
     */
    @GetMapping(value = "/nolog/user/findSmsValidCode")
    ResultMessage findSmsValidCode(@RequestParam(name="phone") String phone);


    /**
     * 发送找回密码的短信验证码
     * @version wangli [2018-08-16 17:47:09] : Created.
     */
    @GetMapping(value = "/nolog/user/findValidCodeForPwd")
    ResultMessage findValidCodeForPwd(@RequestParam(name="mobile") String mobile);

    /**
     * 修改密码时验证手机号 是否和验证码匹配
     * @version wangli [2018-08-16 17:47:09] : Created.
     */
    @GetMapping(value = "/nolog/user/checkPhoneCode")
    ResultMessage checkPhoneCode(@RequestParam(name="phone")String phone,@RequestParam(name="verifCode")String verifCode);


    /**
     * 注册或者绑定手机号时验证手机号是否正确
     * @version wangli 2018-08-28: Created.
     */
//    @GetMapping(value = "/nolog/user/checkPhoneCode")
//    ResultMessage checkPhoneCodeByProduct(@RequestParam(name="phone")String phone,@RequestParam(name="verifCode")String verifCode);


    /**
     * 珈和产品重置密码
     * @version wl 2018-08-24 : Created.
     */
    @PostMapping(value = "/nolog/user/resetAccountPwd")
    public ResultMessage resetAccountPwd(@RequestParam(name="accountName")String accountName,@RequestParam(name="accountPwd")String accountPwd,@RequestParam(name="checkCode")String checkCode);


    /**
     * 珈和产品设置微信id
     * @version wl 2018-08-28 : Created.
     */
    @PostMapping(value = "/nolog/user/setWechatId")
    public ResultMessage setWechatId(@RequestParam(name="mobile")String mobile,@RequestParam(name="wechatId")String wechatId);

    /**
     * 珈和产品注册或者绑定手机号
     * @version wl 2018-08-28 : Created.
     */
    @PostMapping(value = "/nolog/user/registerRelateWx")
    public ResultMessage registerRelateWx(@RequestParam(name="accountName")String accountName,@RequestParam(name="pwd")String pwd,@RequestParam(name="confirmPwd")String confirmPwd,@RequestParam(name="company")String company,@RequestParam(name="msgCode")String msgCode);

}