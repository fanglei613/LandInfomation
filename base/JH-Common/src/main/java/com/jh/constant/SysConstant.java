package com.jh.constant;

/**
* 用来定义系统常量
* @version <1> 2018-05-08 14:25:21 Hayden : Created.
*/
public class SysConstant{

	//微服务名
	public static String Module_DS_Dgy_Name = "jh-ds-dgy";//分布、长势
	public static String Module_DS_Ttn_Name = "jh-ds-ttn";//降水、地温、干旱
	public static String Module_Report_Name = "jh-report";//简报、报告
	public static String Module_System_name = "jh-sys";//系统

	public static Integer Value_Dict_t_id = 1804;//地温字典id
	public static Integer Value_Dict_trmm_id = 1805;//降水字典id
	public static Integer Value_Dict_temp_id = 1807;//气温字典id
	public static Integer Value_Dict_DataSet_id = 1800;//数据集id
	public static Integer Value_Dict_Accuracy_id = 4000;//数据源精度

	//==================================下载 or 显示==============================
	public static String Value_ContentType_Download = "application/octet-stream";
	public static String Value_ContentType_ShowPdf = "application/pdf";


	//定义保存token值的键
	public static String Key_Login_Token="AccessToken";
	public static String Key_Service_Token="serviceKey";
	public static String Key_User_Phone="phone";
	public static String Msg_Redis_AccountToken="无法为账号生成Token";

	//===============================redis 缓存提示信息==============================
	public static String Msg_Redis_UserInfo_Empty = "redis中没有找到用户信息";
	public static String Msg_Redis_SmsCode_Empty = "短信验证码已过期";
	public static String Msg_Redis_ImgCode_Empty = "图形验证码已过期";

	//登录后查询信息提示
	public static String Param_Login_Token = "用户登录Token为空";
	public static String Param_Region_Null = "区域信息为空";
	public static String Request_Param_Empty = "请确认非空在数都有值";

	//=============================用户注册默认权限==================
	public static Long Product_RegionId = 3102000006L;//区域ID
	public static Integer Product_DatasetId = 1801;//数据集ID  降水
	public static Integer Product_AccuracyId = 4010;//精度ID
//	public static Integer Product_CropId = null;//作物ID   （降水、地温不分作物）
	public static Integer[] Product_CropIds = {501,502}; //作物集合
	public static Integer Product_Type = 1;//产品类型：1 默认产品权限，2 自定义产品权限
	public static String Product_StartDate = "2015-01-01";//数据权限开始时间
	public static String Product_EndDate = "2028-12-31";//数据权限结束时间
	public static String Product_Default_Fail = "默认权限保存失败";//默认权限保存失败

	//=============================用户注册========================
	public static Integer AccountPwd_Min_Length = 6;//用户注册密码最小长度
	public static Integer AccountPwd_Max_Length = 16;//用户注册密码最大长度

	public static String Msg_Account_Register_Success = "用户注册成功";
	public static String Msg_Account_Save_Fail = "用户信息保存失败";
	public static String Msg_Account_Register_Fail = "用户注册失败";
	public static String Msg_Account_Registered = "用户已注册";
    public static String Msg_Account_Registered_Code = "600970";//用户已经注册Code
	public static String Msg_Account_Not_Registered = "用户未注册";
	public static String Msg_AccountName_Not_Exist = "账户名不存在";
	public static String Msg_AccountName_Exist = "账户名已存在";
	public static String Msg_AccountName_Format_Error = "账户名格式不正确";
	public static String Msg_AccountName_Is_Empty = "账户名为空";
	public static String Msg_Account_Is_Empty = "账户为空";
	public static String Msg_AccountPwd_Is_Empty = "账户密码为空";
	public static String Msg_AccountConfirmPwd_Is_Empty = "确认密码为空";
	public static String Msg_AccountTwicePwd_Not_Equal = "两次密码不一致";
	public static String Msg_LoginAccountPwd_Is_Error = "密码不正确";
	public static String Msg_NewPwd_Is_Empty = "账户新密码为空";
    public static String Msg_AccountPwd_Is_Error = "账户原密码错误";
	public static String Msg_AccountName_Length_Error = "账号密码长度不正确";
	public static String Msg_Phone_VerifCode_Empty = "短信验证码为空";
	public static String Msg_Phone_RedisVerifCode_Not_Exist = "手机验证码不存在";
	public static String Msg_Phone_VerifCode_Not_Right = "短信验证码不正确";
	public static String Msg_Phone_VerifCode_Right = "短信验证码正确";
	public static String Msg_Phone_VerifCode_Unchecked = "短信验证码未校验";
	public static String Msg_Company_Is_Empty = "公司名为空";
	public static String Msg_Img_VerifCode_Empty = "图形验证码为空";
	public static String Msg_Img_VerifCode_Error = "图形验证码不正确";
	public static String Msg_UserName_Is_Empty = "用户名为空";
	public static String Msg_AccountRole_Is_Fail = "账号角色关联失败";
	public static String Msg_Account_Query_Empty = "没有检索到用户";
	public static String Msg_UserInfo_Is_Fail = "用户信息不存在";
	public static String Msg_UpdateUserInfo_Is_Fail = "用户信息修改失败";
	public static String Msg_UpdateUserInfo_Is_Success = "用户信息修改成功";
    public static String Msg_UpdatePwd_Is_Success = "用户密码修改成功";
    public static String Msg_UpdatePwd_Is_Fail = "用户密码修改失败";
	public static String Msg_Account_Login_Success = "用户登录成功";
	public static String Msg_Account_Login_Fail = "用户登录失败，请联系管理员";


	//=================================================微信简报=================================
	public static String Msg_Wechat_WechatId_Enpty = "微信ID为空";
	public static String Msg_Wechat_User_Not_Relate = "微信用户未绑定";
	public static String Key_Wechat_User_Not_Relate = "notRegist";
	public static String Msg_Wechat_Not_Subscribe = "用户已注册，未订阅简报";
	public static String Key_Wechat_Not_Subscribe = "notBriefing";
	public static String Msg_Wechat_Keyword_Empty = "查询关键字为空";
	public static String Msg_Wechat_Not_BriefingOrKeyword = "用户未添加关键字或订阅简报";
	public static String Msg_Wechat_FindBriefing_Success = "简报查询成功";
    public static String Msg_Wechat_FindBriefing_Fail = "简报查询失败";
	public static String Msg_Wechat_Registered_Subscribed = "用户已注册，并订阅";
	public static String Msg_Wechat_CropName_NotExist = "作物:%s 不存在,请您重新输入！";
	public static String Msg_Wechat_ChinaName_Multiple = "%s有相似的区域：%s,请您输入确认的区域！";
	public static String Msg_Wechat_ChinaName_NotExist = "区域：%s不存在，请您重新输入！";
    public static String Msg_Wechat_FindSentReporter_NotExist = "查找已发送消息模板信息不存在！";


	//=========================================图形验证码提示信息==============================
	public static String Msg_Image_Verification_Not_Equal = "图形验证码不正确";


	//=======================================手机短信发送提示信息=============================
	public static String Msg_Sms_Verification_Send_Success = "短信发送成功";
	public static String Msg_Sms_Verification_Send_Fail = "短信发送失败";
	public static String Msg_Sms_Verification_Send_Upperlimit = "短信发送次数已达上限";
	public static String Msg_Sms_Verification_Empty = "短信验证码为空";
	public static String Msg_Sms_Verification_Not_Exists = "短信验证码不存在";
	public static String Msg_Sms_Verification_Not_Equal = "短信验证码不正确";
	public static String Msg_Sms_Verification_Init_Fail = "短信初始化失败";


	//==========================================报告权限======================
	public static String Msg_Report_User_No_Product = "用户没有此报告权限";
	public static String Msg_Report_Not_Exists = "报告不存在";


	//========================================图层代理接口提示信息========================
	public static String Msg_LayerProxy_Layer_Not_Exists = "没有查询到指定图层";
	public static String Msg_LayerProxy_Search_Param_Empty = "图层查询参数为空";



}
