/**
* 此类用于定义用户无需登录的方法
* 注册、登录、找回密码、短信验证码、图片验证码、检查账号是否存在
* @version <1> 2018-04-27 14:47:19 Hayden : Created.
*/

package com.jh.system.controller;

import com.github.pagehelper.PageInfo;
import com.jh.constant.CommonDefineEnum;
import com.jh.enums.ImageValidateCodeEnum;
import com.jh.system.Enum.UserEnum;
import com.jh.system.base.controller.BaseController;
import com.jh.system.entity.PermAccount;
import com.jh.system.entity.PermPerson;
import com.jh.system.model.LoginParam;
import com.jh.system.model.PersonParam;
import com.jh.system.model.RegisterEntity;
import com.jh.system.model.UserParam;
import com.jh.system.service.IBuzRedisService;
import com.jh.system.service.IPermAccountService;
import com.jh.system.service.IPermPersonService;
import com.jh.system.service.IUserService;
import com.jh.util.AccountTokenUtil;
import com.jh.util.CaptchaUtil;
import com.jh.util.PropertyUtil;
import com.jh.util.RedisUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * description:用户操作服务:
 * 1.注册模块，网页短信发送
 * 2.获取图形验证码
 * 3.用户注册，生成账号信息与用户信息
 * 4.用户登录
 * 5.根据短信修改账号密码(短信验证通过后才可修改密码)
 * @version <1> 2018-05-10 cxw: Created.
 */
@Api(value = "Operation for no logged user ",description="无需登录的用户操作")
@RestController
@RequestMapping("/nolog/user")
public class NoLogController extends BaseController{

	@Autowired
	private IUserService userService;


	@Autowired
	private IPermAccountService permAccountService;


	@Autowired
	private IBuzRedisService buzRedisService;

	@Autowired
	private IPermPersonService permPersonService;


	/**
	 * 微信注册模块，短信发送
	 * @param phone 用户参数
	 * @version <1> 2018-04-27 xzg： Created.
	 * @version <2> 2018-05-10 cxw： update:将实现方法熊controller层移动至service层
	 */
	@ApiOperation(value="findSmsValidCode",notes = "为手机号获取短信验证码")
	@GetMapping("/findSmsValidCode")
	public ResultMessage sendWechatSmsValidCode(String phone) {
		return userService.sendWechatSmsValidCode(phone);
	}

	/**
	 * 注册模块，网页短信发送
	 * @param mobile 用户账号即手机号码
	 * @param imageVerifyCode 图形验证码
	 * @param validToken 图形验证码 唯一标识
     * @return  ResultMessage
	 * @version <1> 2018-05-09 cxw： Created.
	 */
	@ApiOperation(value="findValidCodeForRegister",notes ="网页短信发送(注册模块)")
	@GetMapping("/findValidCodeForRegister")
	public ResultMessage findValidCodeForRegister(String mobile,String imageVerifyCode,String validToken) {
		return  userService.findValidCodeForRegister(mobile,imageVerifyCode,validToken);
	}

    /**
     * 找回密码，网页短信发送
     * @param mobile 用户账号即手机号码
     * @return  ResultMessage
     * @version <1> 2018-05-09 cxw： Created.
     */
    @ApiOperation(value="findValidCodeForPwd",notes ="网页短信发送(找回密码模块)")
    @GetMapping("/findValidCodeForPwd")
    public ResultMessage findValidCodeForPwd(String mobile) {
        return  userService.findValidCodeForPwd(mobile);
    }

    /**
	 * 修改密码时验证手机号与验证码是否匹配
	 *
	 * 修改密码是执行下一步，判断验证码是否正确。验证码正确，更新redis 值为true
	 *
	 * @param phone   手机号
	 * @param verifCode  手机验证码
	 * @return
	 * @version <1> 2018-05-15 xzg： Created.
     */
    @ApiOperation(value = "修改密码时验证手机号与验证码是否匹配")
	@GetMapping("/checkPhoneCode")
	public ResultMessage checkPhoneCode(String phone,String verifCode){
		return userService.checkPhoneCode(phone,verifCode);
	}


//    /**
//     * 用户注册或者绑定手机号时验证码是否正确
//     *
//     * @param phone   手机号
//     * @param verifCode  手机验证码
//     * @return
//     * @version <1> 2018-05-15 xzg： Created.
//     */
//    @ApiOperation(value = "注册绑定手机号时短信验证码是否正确")
//    @GetMapping("/checkPhoneCodeByProduct")
//    public ResultMessage checkPhoneCodeByProduct(String phone,String verifCode){
//        return userService.checkPhoneCodeByProduct(phone,verifCode);
//    }

	/**
	 * web 用户注册 获取图形验证码
	 * @param validToken 唯一码
	 * @param response 请求响应
     * @return  ResultMessage
	 * @version <1> 2018-05-09 cxw： Created.
	 */
	@ApiOperation(value="regionValidateCode",notes = "用户注册 获取图形验证码")
	@GetMapping("/regionValidateCode")
	public void regionValidateCode(HttpServletResponse response,String validToken)  {
		//生成随机字串
		String verifyCode = "";
		if (!StringUtils.isEmpty(validToken)){
			verifyCode = CaptchaUtil.generateVerifyCode(4);
			//将图像验证码缓存到redis
			buzRedisService.setRedisImageValidateCode(validToken,verifyCode, ImageValidateCodeEnum.Web_Image_code);
		}
		//request.getSession().setAttribute(ConstantUtil.VERIFY_CODE, verifyCode);
		int w = 76, h = 30;
		try {
			CaptchaUtil.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * web 用户登录 获取图形验证码
	 * @param validToken 唯一码
	 * @param response 请求响应
	 * @return  ResultMessage
	 * @version <1> 2018-05-09 cxw： Created.
	 */
	@ApiOperation(value="loginValidateCode",notes = "用户登录 获取图形验证码")
	@GetMapping("/loginValidateCode")
	public void loginValidateCode(HttpServletResponse response,String validToken)  {
		//生成随机字串
		String verifyCode = "";
		if (!StringUtils.isEmpty(validToken)){
			verifyCode = CaptchaUtil.generateVerifyCode(4);
			//将图像验证码缓存到redis
			buzRedisService.setRedisImageValidateCode(validToken,verifyCode, ImageValidateCodeEnum.Web_Image_code);
		}
		//request.getSession().setAttribute(ConstantUtil.VERIFY_CODE, verifyCode);
		int w = 76, h = 30;
		try {
			CaptchaUtil.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查账号(手机号)是否已注册
	 * @param accountName
	 * @return
	 * @version <1> 2018-05-14 xzg： Created.
     */
	@ApiOperation(value="checkAccountExists",notes = "检查账号是否存在")
	@GetMapping("/checkAccountExists")
	public ResultMessage checkAccountExists(String accountName)  {
		return userService.checkAccountExists(accountName);
	}

	/**
	 * 用户注册，生成账号信息与用户信息
	 * 1. 检查注册信息的必填项，手机号、密码
	 * 2. 为用户生成访问的KEY，构造账号信息并写入
	 * 3. 写入用户信息
	 * @param registerEntity : 注册信息
     *   phone:手机号
     *   personName：姓名
     *   companyName：公司名称
     *   pwd：密码
     *   smsVerifCode：短信验证码
     *   imgVerifyCode：图形验证码
     * @return  ResultMessage
	 * @version <1> 2018-04-27 11:16:16 Hayden : Created.
	 * @version <2> 2018-05-09 11:16:16 cxw : upate  UserVo替换RegisterVo参数.
	 */
	@ApiOperation(value="register",notes = "用户注册，生成账号信息与用户信息")
	@PostMapping("/register")
	public ResultMessage register(@RequestBody RegisterEntity registerEntity)  {
		return userService.register(registerEntity);
	}

    /**
     * 用户注册，生成账号信息与用户信息（用于珈和产品的注册或者手机号的绑定）
     * @param accountName
     *   accountName:手机号
     *   companyName：公司名称
     *   pwd：密码
     * @return  ResultMessage
     * @version <1> 2018-08-28 wl : Created.
     */
    @ApiOperation(value="registerRelateWx",notes = "珈和产品用户注册，生成账号信息与用户信息")
    @PostMapping("/registerRelateWx")
    public ResultMessage registerRelateWx(String accountName,String pwd,String confirmPwd,String company,String msgCode)  {
        RegisterEntity registerEntity = new RegisterEntity();
        registerEntity.setPhone(accountName);
        registerEntity.setPwd(pwd);
        registerEntity.setConfirmPwd(confirmPwd);
        registerEntity.setCompanyName(company);
        registerEntity.setSmsVerifCode(msgCode);
        return userService.register(registerEntity);
    }


	/**
	 * 根据短信修改账号密码(短信验证通过后才可修改密码)
	 * @param  loginEntity: 登录信息
     *  accountName:账号即手机号
     *  accountPwd：密码
     *  smsVerifCode:短信验证码
     * @return  ResultMessage
	 * @version <1> 2018-05-10 13:46:16 cxw : Created.
	 */
	@ApiOperation(value="resetPwd",notes = "找回密码：通过短信的方式发送验证码后修改新密码")
	@PostMapping("/resetPwd")
	public ResultMessage resetPwd(@RequestBody LoginParam loginEntity)  {
		return userService.resetPwd(loginEntity);
	}



	/***********后台管理部分***************/
	/**
	 * 私有云用户登录功能:
	 *
	 * 根据账号密码验证码登录
	 * @param  userParam: 登录信息
	 *  accountName:账号即手机号
	 *  accountPwd：密码（前端base64加密）
	 *  verifyCode：图形验证码
	 *   validToken： 登录生成的token
	 * @return  ResultMessage
	 * @version <1> 2018-08-08 13:46:16 cxw : Created.(加注释)
	 * @version <2> 2018-08-28 lcw : 修改
	 */
	@ApiOperation(value="用户登录",notes="用户登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userParam",value = "用户账户参数对象",required = true, dataType = "UserParam")
	})
	@PostMapping("/loginForWeb")
	public ResultMessage loginForWeb(@RequestBody UserParam userParam) {

		ResultMessage result = userService.loginForWeb(userParam);

		return result;
	}


	/**
	 * 更新用户信息功能:
	 *
	 * 根据账号密码验证码登录
	 * @param  userParam: 登录信息
	 *  accountName:账号即手机号
	 *  accountPwd：密码（前端base64加密）
	 *  verifyCode：图形验证码
	 *   validToken： 登录生成的token
	 * @return  ResultMessage
	 * @version <1> 2018-08-08 13:46:16 cxw : Created.(加注释)
	 * @version <2> 2018-08-28 lcw : 修改
	 */
	@ApiOperation(value="更新用户信息",notes="更新用户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userParam",value = "用户账户参数对象",required = true, dataType = "UserParam")
	})
	@PostMapping("/updateUserInfo")
	public ResultMessage updateUserInfo(@RequestParam String redisKey,@RequestParam String accountName) {

		ResultMessage result = userService.updateUserInfo(redisKey,accountName);

		return result;
	}

	/**
	 * 珈和农情遥感用户登录功能
	 *
	 * 根据账号密码验证码登录
	 * @param  userParam: 登录信息
	 *  accountName:账号即手机号
	 *  accountPwd：密码（前端base64加密）
	 *  verifyCode：图形验证码
	 *   validToken： 登录生成的token
	 * @return  ResultMessage
	 * @version <1> 2018-08-08 13:46:16 cxw : Created.(加注释)
	 * @version <2> 2018-08-28 lcw : 修改
	 */
	@ApiOperation(value="用户登录",notes="用户登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userParam",value = "用户账户参数对象",required = true, dataType = "UserParam")
	})
	@PostMapping("/loginForShow")
	public ResultMessage loginForShow(@RequestBody UserParam userParam){
		return userService.loginForShow(userParam);
	}


	/**
	 * 根据账号密码登录
	 *  accountName:账号即手机号
	 *  accountPwd：密码（前端base64加密）
	 *   validToken： 登录生成的token
	 * @return  ResultMessage
	 * @version <1> 2018-08-08 13:46:16 cxw : Created.
	 */
	@ApiOperation(value="用户登录",notes="用户登录(无需验证码)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userParam",value = "用户账户参数对象",required = true, dataType = "UserParam")
	})
	@PostMapping("/loginForApp")
	public ResultMessage loginForApp(String accountNo,String pwd) {
		UserParam user = new UserParam();
		PermAccount account = new PermAccount();
		account.setAccountName(accountNo);
		account.setAccountPwd(pwd);
		user.setPermAccount(account);
		ResultMessage result = userService.loginForApp(user,false);
		return result;
	}


	@PostMapping("/loginForMui")
	public ResultMessage loginForMui(String accountNo,String pwd, String appMenu) {
		UserParam user = new UserParam();
		user.setAppMenu(appMenu);
		PermAccount account = new PermAccount();
		account.setAccountName(accountNo);
		account.setAccountPwd(pwd);
		user.setPermAccount(account);
		ResultMessage result = userService.loginForApp(user,false);
		return result;
	}


    /**
	 * 报告产品展示平台用户登录（无验证码）
     * 根据账号密码登录
     * @param
     *  accountNo:账号即手机号
     *  pwd：密码（前端base64加密）
     * @return  ResultMessage
     * @version <1> 2018-08-17 wl: Created.
     */
    @ApiOperation(value="用户登录",notes="用户登录(无需验证码)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userParam",value = "用户账户参数对象",required = true, dataType = "UserParam")
    })
    @PostMapping("/loginForProduct")
    public ResultMessage loginForProduct(String accountNo,String pwd, Long regionId) {
        UserParam user = new UserParam();
        PermAccount account = new PermAccount();
        account.setAccountName(accountNo);
        account.setAccountPwd(pwd);
        user.setPermAccount(account);
        ResultMessage result = userService.loginForProduct(user,regionId,false);
        return result;
    }


    /**
	 *
     * 根据账号密码登录
     * @param
     *  accountNo:账号即手机号
     *  pwd：密码（前端base64加密）
     * @return  ResultMessage
     * @version <1> 2018-08-17 wl: Created.
     */
    @ApiOperation(value="用户登录",notes="用户登录(无需验证码)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userParam",value = "用户账户参数对象",required = true, dataType = "UserParam")
    })
    @PostMapping("/loginForWx")
    public ResultMessage loginForWx(String accountNo,String openId, Long regionId) {
        ResultMessage result = userService.loginForWx(accountNo, openId,regionId);
        return result;
    }

	/**
	 * 判断用户是否登录
	 * @return
	 * @version<1> 2018-2-2 lcw : created.
	 */
	@GetMapping("checkUserLogin")
	public ResultMessage checkUserLogin(HttpServletRequest request){
		ResultMessage result = ResultMessage.fail(UserEnum.USER_NOT_LOGIN.getValue(),UserEnum.USER_NOT_LOGIN.getMesasge());

		String token = AccountTokenUtil.getToken(request);

		result = buzRedisService.getUserLoginInfo(token);

		if(result.isFlag()){
			Map<String,Object> dataInfo = (Map<String,Object>) result.getData();
			if(dataInfo != null && dataInfo.size() > 0 ){
				String userInfo = JSONObject.fromObject(dataInfo.get("userInfo")).toString();

				//更新缓存
				Integer expireSecond = Integer.valueOf(PropertyUtil.getPropertiesForConfig("User_Login_Expire"));
				RedisUtil.setJsonStr( token,dataInfo,expireSecond);

				result = ResultMessage.success(null,userInfo);
			}else{
				result = ResultMessage.fail(CommonDefineEnum.NO_LOGIN_FAIL.getMesasge());
			}
		}


		return result;
	}



	/**
	 * 为指定账号更新其密码
	 *
	 * @param request   请求对象
	 * @param userParam 用户参数
	 * @version <1> 2018-01-23 djh： Created.
	 */
	@ApiOperation("更新账号密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "request",value = "request请求对象",required = false, dataType = "HttpServletRequest"),
			@ApiImplicitParam(name = "userParam",value = "用户账户参数对象",required = true, dataType = "UserParam")
	})
	@PostMapping("updateUserPwd")
	public ResultMessage updateUserPwd(HttpServletRequest request, @RequestBody UserParam userParam) {
		return permAccountService.updatePermAccount(userParam.getPermAccount());
	}



	/**
	 * 用户退出
	 * @return
	 * @version<1> 2018-2-2 lcw : created.
	 * @version<2> 2018-8-24 zhangshen : update. accessToken添加不为空判断
	 */
	@ApiOperation(value="退出登录",notes="退出登录")
	@PostMapping("logout")
	public ResultMessage logout(){
		String accessToken = getToken();
		if(StringUtils.isNotBlank(accessToken) && RedisUtil.testKeyExists(accessToken)){
			RedisUtil.del(accessToken);
		}
		return ResultMessage.success();
	}

    @ApiOperation(value="registerProduct",notes = "珈和产品用户注册，生成账号信息与用户信息")
    @PostMapping("/registerProduct")
    public ResultMessage registerProduct(String phone,String pwd,String companyName)  {
        RegisterEntity registerEntity = new RegisterEntity();
        registerEntity.setPhone(phone);
        registerEntity.setPwd(pwd);
        registerEntity.setCompanyName(companyName);
        return userService.register(registerEntity);
    }


    /**
     * 根据短信修改账号密码(短信验证通过后才可修改密码)  珈和产品调用
     * @param
     *  accountName:账号即手机号
     *  accountPwd：密码
     * @return  ResultMessage
     * @version <1> 2018-08-24 wl: Created.
     */
    @ApiOperation(value="resetAccountPwd",notes = "找回密码：通过短信的方式发送验证码后修改新密码")
    @PostMapping("/resetAccountPwd")
    public ResultMessage resetAccountPwd(String accountName,String accountPwd,String checkCode)  {
        LoginParam loginEntity = new LoginParam();
        loginEntity.setAccountName(accountName);
        loginEntity.setAccountPwd(accountPwd);
		loginEntity.setSmsVerifCode(checkCode);
        return userService.resetPwd(loginEntity);
    }

    /**
     * 根据短信修改账号密码(短信验证通过后才可修改密码)  珈和产品调用
     * @param
     *  mobile:账号即手机号
     *  wechatId：密码
     * @return  ResultMessage
     * @version <1> 2018-08-28 wl: Created.
     */
    @ApiOperation(value="setWechatId",notes = "珈和产品已经注册过的用户绑定微信id")
    @PostMapping("/setWechatId")
    public ResultMessage setWechatId(String mobile,String wechatId)  {
        PermPerson permPerson = new PermPerson();
        permPerson.setMobile(mobile);
        permPerson.setWechatId(wechatId);


        return userService.updateWechatIdByAccountName(permPerson);


//        return userService.updateUser(permPerson);
    }


	/**
	 * 按用户accountId查询用户
	 * @param accountId
	 * @return
	 * @version <1> 2019/3/11 mason:Created.
	 */
	@ApiOperation(value = "无需登录用户查询",notes = "按用户accountId查询用户")
	@ApiImplicitParam(name = "accountId",value = "accountId",required = true,paramType = "query", dataType = "Integer")
	@GetMapping("/getByAccountId")
	public ResultMessage getdById(@RequestParam Integer accountId){
		return permPersonService.findPersonByAccountId(accountId);
	}

	/**
	 * 社区用户注册，生成账号信息与用户信息
	 * 1. 检查注册信息的必填项，手机号、密码
	 * 2. 为用户生成访问的KEY，构造账号信息并写入
	 * 3. 写入用户信息
	 * @param registerEntity : 注册信息
	 *   phone:手机号
	 *   pwd：密码
	 *   smsVerifCode：短信验证码
	 * @return  ResultMessage
	 * @version <1> 2019-03-13 11:16:16 lijie : Created.
	 */
	@ApiOperation(value="forumRegister",notes = "社区用户注册，生成账号信息与用户信息")
	@PostMapping("/forumRegister")
	public ResultMessage forumRegister(@RequestBody RegisterEntity registerEntity)  {
		return userService.forumRegister(registerEntity);
	}

	/**
	 * 社区注册模块，网页短信发送
	 * @param mobile 用户账号即手机号码
	 * @return  ResultMessage
	 * @version <1> 2018-05-09 cxw： Created.
	 */
	@ApiOperation(value="findForumValidCodeForRegister",notes ="社区网页短信发送(注册模块)")
	@GetMapping("/findForumValidCodeForRegister")
	public ResultMessage findForumValidCodeForRegister(String mobile) {
		return  userService.findForumValidCodeForRegister(mobile);
	}

	/**
	 * 根据account_id数组查询person表信息
	 * @param ids
	 * @return
	 * @version <1> 2019/4/19 mason:Created.
	 */
	@ApiOperation(value = "根据account_id数组查询person表信息",notes = "根据account_id数组查询person表信息")
	@ApiImplicitParam(name = "ids[]",value = "ids[]",required = true,paramType = "query",dataType = "int[]")
	@GetMapping("/batchFindPersonByAccountIdArr")
	public ResultMessage batchFindPersonByAccountIdArr(@RequestParam("ids[]")int[] ids){
		return permPersonService.batchFindPersonByAccountIdArr(ids);
	}

	/**
	 * 根据关键词，查找用户名或者备注匹配的用户
	 * @param personParam
	 * @return 用户列表
	 * @version <1> 2019/4/23 mason:Created.
	 */
	@ApiOperation(value = "根据关键词，查找用户名或者备注匹配的用户",notes = "根据关键词，查找用户名或者备注匹配的用户")
	@ApiImplicitParam(name = "personParam",value = "系统用户查询参数",required = false,dataType = "PersonParam")
	@PostMapping("/findPersonByKeyword")
	public PageInfo<PermPerson> findPersonByKeyword(@RequestBody PersonParam personParam){
		return permPersonService.findPersonByKeyword(personParam);
	}

	/**
	 * 根据账号ID获取个人信息
	 * @param accountId
	 * @return
	 */
	@GetMapping("/queryPersonByAccountId")
	public ResultMessage queryPersonByAccountId(Integer accountId){
		ResultMessage result = permPersonService.findPersonByAccountId(accountId);
		return result;

	}



}