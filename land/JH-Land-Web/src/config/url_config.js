// 登录接口配置参数
const LOGIN_CONFIG = {
  //获取验证码
  verify_url: "/jh-sys/nolog/user/loginValidateCode",
  //登录
  login_url: "/jh-sys/nolog/user/loginForWeb",
  //判断用户是否登录
  check_user_login: "/jh-sys/nolog/user/checkUserLogin",
  // 用户退出登录
  logout_url: "/jh-sys/nolog/user/logout",
  // 验证用户账号是否存在
  checkAccountExists: "/jh-sys/nolog/user/checkAccountExists",
  // 更新用户密码
  updateUserPwd: "/jh-sys/permAccount/updatePwd",
  // 确认忘记密码
  resetPwd_url: "/jh-sys/nolog/user/resetPwd",
  // verify_url : "/index/verifyCode",
  sendsms_url: "/jh-sys/nolog/user/findValidCodeForPwd",
  checkPhoneValicode_url: "/jh-sys/nolog/user/checkPhoneCode",
  updateUserPwd_url: "/jh-sys/nolog/user/updateUserPwd",
  resetUserPwd_url: "/jh-sys/permAccount/resetUserPwd",
  checkAccountCodeExists_url: "/jh-sys/permAccount/checkAccountCodeExists"
};

const REGISTER_CONFIG = {
  verify_url: "/jh-sys/nolog/user/regionValidateCode",
  checkAccountExists: "/jh-sys/nolog/user/checkAccountExists",
  //注册时发送手机验证码
  sendSmsCode_URL: "/jh-sys/nolog/user/findForumValidCodeForRegister",
  register_URL: "/jh-sys/nolog/user/forumRegister"
};


// cookie配置参数
const COOKIE_CONFIG = {
  cookieName: "AccessTokenForForum"
};

export { LOGIN_CONFIG, COOKIE_CONFIG, REGISTER_CONFIG };
