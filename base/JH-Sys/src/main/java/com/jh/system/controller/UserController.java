/**
* 用户及权限接口
* 1. 
* @version <1> 2018-04-24 15:30:59 Hayden : Created.
*/

package com.jh.system.controller;

import com.jh.system.entity.PermPerson;
import com.jh.system.model.LoginParam;
import com.jh.system.service.IBuzRedisService;
import com.jh.system.service.IUserService;
import com.jh.util.AccountTokenUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * description:用户操作服务:
 * 1.根据用户信息Id查询用户信息
 * 2.根据用户信息ID修改用户信息
 * 3.用户登录后修改用户密码
 * @version <1> 2018-04-27 cxw: Created.
 */
@Api(value = "user Service Interface",description="用户操作接口")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	@Autowired
	private IBuzRedisService redisService;
	/**
	 * 根据用户信息Id查询用户信息
	 * @param  personId: 用户信息Id
     * @return  ResultMessage
	 * @version <1> 2018-05-10 13:46:16 cxw : Created.
	 */
	@ApiOperation(value="queryUserById",notes = "根据ID查询用户信息")
	@GetMapping("/queryUserById")
	public ResultMessage queryUserById(Integer personId)  {
		return userService.findUserById(personId);
	}

	/**
	 * 根据用户信息ID修改用户信息
	 * @param  user: 用户信息
     * personId：用户信息ID
     * personName：姓名
     * nickName：昵称
     * sex：性别
     * personBorn ：出生日期
     * email：邮箱
     * qq：qq
     * address:地址
     * @return  ResultMessage
     * @version <1> 2018-04-27 13:46:16 Hayden : Created.
	 */
	@ApiOperation(value="updateUser",notes = "修改用户信息")
	@PostMapping("/updateUser")
	public ResultMessage updateUser(@RequestBody PermPerson user)  {
		return userService.updateUser(user);
	}

	/**
	 * 根据手机号判断serviceKey是否存在
	 * @param serviceKey  用户key
	 * @param phone        用户手机号
     * @return
	 * @version <1> 2018-05-10 xzg: Created.
     */
	@ApiOperation(value = "根据手机号判断serviceKey是否存在")
	@RequestMapping(value = "/testServiceKey",method = RequestMethod.GET)
	public ResultMessage testServiceKey(String serviceKey,String phone){
		return userService.findUserByKey(serviceKey,phone);
	}

    /**
     * 用户登录后修改用户密码
     * @param  loginEntity: 用户账号密码信息
     *  accountName:账号即手机号
     *  accountPwd：密码
     *  newPwd:新密码
     * @return  ResultMessage
     * @version <1> 2018-05-10 13:46:16 cxw : Created.
     */
    @ApiOperation(value="updateAccountPwd",notes = "修改用户密码")
    @PostMapping("/updateAccountPwd")
    public ResultMessage updateAccountPwd(@RequestBody LoginParam loginEntity)  {
        return userService.updateAccountPwd(loginEntity);
    }

	/**
	 * 获取用户权限区域数据集列表
	 * @param regionId
	 * @return
	 * @version <1> 2018-5-16 xzg : Created.
	 */
	@ApiOperation(value = "获取用户权限区域数据集列表" )
	@ApiImplicitParam(name = "regionId",value = "区域ID",required = true,paramType = "query")
	@GetMapping("/dsListForRegion")
	public ResultMessage dsListForRegion(HttpServletRequest request,Long regionId){
		String accountToken = AccountTokenUtil.getToken(request);
		return userService.findUserDsByRegion(accountToken,regionId);
	}

	/**
	 * 获取所有数据集列表
	 * @param regionId
	 * @return
	 * @version <1> 2018-5-16 xzg : Created.
	 */
	@ApiOperation(value = "获取用户权限区域数据集列表" )
	@ApiImplicitParam(name = "regionId",value = "区域ID",required = true,paramType = "query")
	@GetMapping("/dsListForAllRegion")
	public ResultMessage dsListForAllRegion(HttpServletRequest request,Long regionId){
		String accountToken = AccountTokenUtil.getToken(request);
		return userService.findUserDsByAllRegion(accountToken,regionId);
	}

	/**
	 *   用户选择区域中相关数据集作物列表、数据源即分辨率
	 *   1.管理员用户：显示所有分辨率
	 *   2.非管理员用户：通过regionId和dsId从当前用户缓存中读取权限
	 * @param dsId
	 * @return
	 * @version <1> 2017-12-25 lcw : Created.
	 */
	@ApiOperation(value = "用户选择区域中相关数据集作物列表、数据源即分辨率" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true,paramType = "query"),
			@ApiImplicitParam(name = "dsId",value = "数据集ID",required = true,paramType = "query")
	})
	@GetMapping("/cropDataTypeListForDsRegion")
	public ResultMessage cropDataTypeListForDsRegion(HttpServletRequest request,Long regionId, Integer dsId){
		String accountToken = AccountTokenUtil.getToken(request);
		return userService.findCropDataTypeByDsRegion(accountToken,regionId,dsId);
	}

	/**
	 *   用户选择区域中相关数据集作物列表、数据源即分辨率
	 *   1.管理员用户：显示所有分辨率
	 *   2.非管理员用户：通过regionId和dsId从当前用户缓存中读取权限
	 * @param dsId
	 * @return
	 * @version <1> 2017-12-25 lcw : Created.
	 */
	@ApiOperation(value = "用户选择区域中相关数据集作物列表、数据源即分辨率" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true,paramType = "query"),
			@ApiImplicitParam(name = "dsId",value = "数据集ID",required = true,paramType = "query")
	})
	@GetMapping("/allCropDataTypeListForDsRegion")
	public ResultMessage allCropDataTypeListForDsRegion(HttpServletRequest request,Long regionId, Integer dsId){
		String accountToken = AccountTokenUtil.getToken(request);
		return userService.findAllCropDataTypeByDsRegion(accountToken,regionId,dsId);
	}


	/**
	 * 根据区域，作物，数据集，分辨率获取用户权限时间
	 * @param regionId 区域主键
	 * @param dsId 数据集主键
	 * @param dataTypeId 分辨率主键
	 * @param cropId 作物主键
	 * @return
	 * @version <1> 2018-07-05 cxw : Created.
	 */
	@ApiOperation(value = "获取用户权限区域时间" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "regionId",value = "区域ID",required = true,paramType = "query"),
			@ApiImplicitParam(name = "dsId",value = "数据集ID",required = true,paramType = "query"),
			@ApiImplicitParam(name = "dataTypeId",value = "分辨率ID",required = true,paramType = "query"),
			@ApiImplicitParam(name = "cropId",value = "作物ID",required = true,paramType = "query")
	})
	@GetMapping("/dataTimeForPerm")
	public ResultMessage dataTimeForPerm(HttpServletRequest request,Long regionId, Integer dsId,Integer dataTypeId,Integer cropId ){
		String accountToken = AccountTokenUtil.getToken(request);
		return userService.dataTimeForPerm(accountToken,regionId,dsId,dataTypeId,cropId);
	}


    /**
     * 发送注册的手机短信验证码
     * @param  telphone: 注册用户手机号
     * @return  ResultMessage
     * @version <1> 2018-08-16 17:46:16 wl : Created.
     */
    @ApiOperation(value="sendSmsToPhone",notes = "发送注册手机验证码")
    @PostMapping("/sendSmsToPhone")
    public ResultMessage sendSmsToPhone(String telphone)  {
        return userService.sendWechatSmsValidCode(telphone);
    }

    /**
     * 根据phone查询用户信息(app移动端用到)
     * @param phone
     * @return ResultMessage
     * @version <1> 2019/4/10 15:23 zhangshen:Created.
     */
	@ApiOperation(value = "根据phone查询用户信息(app移动端用到)" )
	@ApiImplicitParam(name = "phone",value = "phone",required = true,paramType = "String")
	@PostMapping("/findUserInfoByPhone")
    public ResultMessage findUserInfoByPhone(@RequestParam("phone") String phone){
    	return userService.findUserInfoByPhone(phone);
	}

}