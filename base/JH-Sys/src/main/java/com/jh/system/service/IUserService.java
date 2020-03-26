/**
* 定义用户及权限查询接口
* 1. 获取短信验证码
* 2. 获取图片验证码
* 3. 注册，即生成新的账号与用户信息
* 4. 检查账号是否存在
* 5. 用户登录
* 6. 找回密码：通过短信的方式发送新密码
* 7. 重置密码：通过新旧密码，更新为新密码
* 8. 修改用户信息
* @version <1> 2018-04-27 10:51:42 Hayden : Created.
*/
package com.jh.system.service;

import com.jh.system.entity.PermPerson;
import com.jh.system.model.LoginParam;
import com.jh.system.model.RegisterEntity;
import com.jh.system.model.UserParam;
import com.jh.vo.ResultMessage;

/**
 * 客户操作
 * @version <1> 2018-05-04 xzg： Created.
 */
public interface IUserService {
	/**
	* 微信注册模块，为手机号获取短信验证码
	* @param  phone : 接收验证码短信的手机号
    * @return  ResultMessage
	* @version <1> 2018-04-27 11:06:10 Hayden : Created.
	*/
	ResultMessage sendWechatSmsValidCode(String phone);

	/**
	 * 注册模块，短信发送
	 * @param mobile 用户参数
	 * @param imageVerifyCode 图形验证码
	 * @param validToken 图形验证码 唯一标识
     * @return  ResultMessage
	 * @version <1> 2018-05-09 cxw： Created.
	 */
    ResultMessage findValidCodeForRegister(String mobile, String imageVerifyCode, String validToken);

	/**
	 * 找回密码，短信发送
	 * @param mobile 用户参数
	 * @version <1> 2018-05-14 cxw： Created.
	 */
	public ResultMessage findValidCodeForPwd(String mobile);


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
	*/
	ResultMessage register(RegisterEntity registerEntity);

	/**
	* 检查账号是否存在
	* @param  accountName : 账号，即手机号
	*@version <1> 2018-04-27 11:20:10 Hayden : Created.
	*/
	ResultMessage checkAccountExists(String accountName);

	/**
     * 重置密码：通过新旧密码，更新为新密码
     * @param loginEntity 登录账号信息
     * accountName : 账号
     * accountPwd : 旧密码
     *  newPwd : 新密码
     * @return  ResultMessage
     *  @version <1> 2018-05-10 13:46:16 cxw : Created.
     */
    ResultMessage updateAccountPwd(LoginParam loginEntity) ;

    /**
    * 修改用户信息
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
    ResultMessage updateUser(PermPerson user);

	/**
	 * 根据用户信息Id查询用户信息
	 * @param  personId: 用户信息Id
     * @return  ResultMessage
	 * @version <1> 2018-05-10 13:46:16 cxw : Created.
	 */
	ResultMessage findUserById(Integer personId);

	/**
	 * 微信用户注册
	 * @param registerEntity
	 * @return  ResultMessage
	 * @version <1> 2018-05-04 xzg： Created.
	 */
	ResultMessage registerWechatUser(RegisterEntity registerEntity);

	/**
	 * 根据手机号判断serviceKey是否存在
	 * @param serviceKey
	 * @param phone
	 * @return
	 * @version <1> 2018-05-10 xzg: Created.
     */
	ResultMessage findUserByKey(String serviceKey, String phone);

	/**
	 * 根据短信修改账号密码(短信验证通过后才可修改密码)
	 * @param  loginEntity: 登录信息
     * accountName:账号即手机号
     * accountPwd：密码
     * @return  ResultMessage
	 * @version <1> 2018-05-10 13:46:16 cxw : Created.
	 */
	ResultMessage resetPwd(LoginParam loginEntity);

	/**
	 * 根据手机号查询用户信息
	 * @param userPhone
	 * @return
	 * @version <1> 2018-05-11 xzg : Created.
     */
	ResultMessage findUserInfoByPhone(String userPhone);

	/**
	 * 修改密码时验证手机号与验证码是否匹配
	 * @param phone
	 * @param verifCode
     * @return
	 * @version <1> 2018-05-15 xzg： Created.
     */
	ResultMessage checkPhoneCode(String phone, String verifCode);

//    /**
//     * 修改密码时验证手机号与验证码是否匹配
//     * @param phone
//     * @param verifCode
//     * @return
//     * @version <1> 2018-08-28 wl： Created.
//     */
//    ResultMessage checkPhoneCodeByProduct(String phone, String verifCode);

	/**
	 * 获取用户权限区域数据集列表
	 * @param accountToken
	 * @param regionId
	 * @return
	 * version <1> 2018-5-16 xzg : Created.
	 */
	ResultMessage findUserDsByRegion(String accountToken, Long regionId);

	/**
	 * 获取用户权限区域数据集列表
	 * @param accountToken
	 * @param regionId
	 * @return
	 * version <1> 2018-5-16 xzg : Created.
	 */
	ResultMessage findUserDsByAllRegion(String accountToken, Long regionId);

	/**
	 * 用户选择区域中相关数据集作物列表、数据源即分辨率
	 *   1.管理员用户：直接读取系统初始化的作物信息、分辨率信息
	 *   2.非管理员用户：通过regionId和dsId从当前用户缓存中读取权限
	 * @param accountToken
	 * @param regionId
	 * @param dsId
     * @return
     */
	ResultMessage findCropDataTypeByDsRegion(String accountToken, Long regionId, Integer dsId);

	/**
	 * 用户选择区域中相关数据集作物列表、数据源即分辨率
	 *   1.管理员用户：直接读取系统初始化的作物信息、分辨率信息
	 *   2.非管理员用户：通过regionId和dsId从当前用户缓存中读取权限
	 * @param accountToken
	 * @param regionId
	 * @param dsId
	 * @return
	 */
	ResultMessage findAllCropDataTypeByDsRegion(String accountToken, Long regionId, Integer dsId);


	/**
	 * 根据区域，作物，数据集，分辨率获取用户权限时间
	 * @param accountToken
	 * @param regionId 区域主键
	 * @param dsId 数据集主键
	 * @param dataTypeId 分辨率主键
	 * @param cropId 作物主键
	 * @return
	 * @version <1> 2018-07-05 cxw : Created.
	 */
	ResultMessage dataTimeForPerm(String accountToken, Long regionId, Integer dsId, Integer dataTypeId, Integer cropId);

	/**
	 * 根据账号密码验证码登录
	 * @param  userParam: 登录信息
	 *  accountName:账号即手机号
	 *  accountPwd：密码
	 *  verifyCode：图形验证码
	 *  validToken： 登录生成的token
	 * @return  ResultMessage
	 * @version <1> 2018-08-08 13:46:16 cxw : Created.(加注释)
	 */
    ResultMessage loginForShow(UserParam userParam);


	/**
	 * 私有云后台服务登录
	 * @param userParam
	 * @return
	 * @version<1> 2018-08-28 lcw: Created.</1>
	 */
	ResultMessage loginForWeb(UserParam userParam);

	/**
	 * 更新用户信息
	 * @param redisKey  accountName
	 * @return
	 * @version<1> 2018-08-28 lcw: Created.</1>
	 */
	ResultMessage updateUserInfo(String redisKey,String accountName);

	/**
	 * 根据账号密码登录
	 * @param  userParam: 登录信息
	 *  accountName:账号即手机号
     *  accountPwd：密码
     *  validToken： 登录生成的token
     *  isVerifyCode ：是否校验验证码（为true则校验，false则不校验）
	 * @return  ResultMessage
	 * @version <1> 2018-08-08 13:46:16 cxw : Created.
	 */
//	ResultMessage loginForNoVerifyCode(UserParam userParam,boolean isVerifyCode, boolean useProductFilterFlag);

	/**
	 * 获取用户信息
	 * @param accountName
	 * @return
	 * @version<1> 2018-08-28 lcw : 修改
	 * 将用户信息的获取方法提取出来单独作为方法使用
	 */
	ResultMessage getUserInfo(String accountName);

    /**
     * 根据账号密码登录
     * @param  accountNo: 登录信息
     *  accountName:账号即手机号
     *  accountPwd：密码
     *  validToken： 登录生成的token
     *  isVerifyCode ：是否校验验证码（为true则校验，false则不校验）
     * @return  ResultMessage
     * @version <1> 2018-08-08 13:46:16 cxw : Created.
     */
    ResultMessage loginForWx(String accountNo,String openId, Long regionId);

	ResultMessage saveDeafultDataPermisson(Integer accountId);

	/**
	 * app产品登录
	 * @param user
	 * @param isVerifyCode
	 * @return
	 * @version <1> 2018-08-08 13:46:16 cxw : Created.
	 */
    ResultMessage loginForApp(UserParam user, boolean isVerifyCode);

	/**
	 * 报告产品用户登录
	 * @param user
	 * @param isVerifyCode
	 * @return
	 * @version <1> 2018-08-30 lcw: Created.
	 */
	ResultMessage loginForProduct(UserParam user,Long regionId, boolean isVerifyCode);

	/**
	 * 通过AccountName修改wechatID
	 * @param permPerson
	 * @return
	 * @version <1> 2018-08-30 lcw: Created.
	 */
	ResultMessage updateWechatIdByAccountName(PermPerson permPerson);

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
	ResultMessage forumRegister(RegisterEntity registerEntity);

	/**
	 * 社区注册模块，短信发送
	 * @param mobile 用户参数
	 * @return  ResultMessage
	 * @version <1> 2018-05-09 cxw： Created.
	 */
	ResultMessage findForumValidCodeForRegister(String mobile);
}
