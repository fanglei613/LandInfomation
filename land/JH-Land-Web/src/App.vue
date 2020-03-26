<template>
  <div id="app">
    <div class="layout">
      <!-- 页面头部 -->
      <j-header></j-header>
      <!-- 页面主体 -->
      <div class="main-content">
        <router-view></router-view>
      </div>
      <!-- 免费使用弹窗 -->
      <j-dialog
        :width="freeDialogObj.width"
        :height="freeDialogObj.height"
        :is-show="freeDialogObj.isShowFreeDialog"
        :is-show-footer="freeDialogObj.isShowFooter"
        :confirm-txt="freeDialogObj.confirmTxt"
        @handleConfirmClcik="openFreeLink"
        @closeDialog="closeDialog"
      >
        <div slot="header">免费试用</div>
        <div slot="main">
          <div class="free-con">
            <p>
              想要继续体验地查查，请申请
              <span>免费试用</span>哦!
            </p>
            <ul>
              <li>
                <i class="iconfont">&#xe637;</i>
                <span>
                  试用时间
                  <br />
                  <b>七天</b>
                </span>
              </li>
              <li>
                <i class="iconfont">&#xe63b;</i>
                <span>
                  查询次数：
                  <br />
                  <b>不限</b>
                </span>
              </li>
              <li>
                <i class="iconfont">&#xe636;</i>
                <span>
                  下载次数：
                  <br />
                  <b>不限</b>
                </span>
              </li>
            </ul>
          </div>
        </div>
      </j-dialog>
      <!-- 数据下载弹窗 -->
      <j-dialog
        :width="downloadDialogObj.width"
        :height="downloadDialogObj.height"
        :is-show="downloadDialogObj.isShowLoadDialog"
        :is-show-footer="downloadDialogObj.isShowFooter"
        :confirm-txt="downloadDialogObj.confirmTxt"
      >
        <div slot="header">数据下载</div>
        <div slot="main">
          <div class="download-con">
            <div class="field">
              <label>
                <i></i>
                <b>所在区域</b>
              </label>
              <div class="select"></div>
            </div>
            <div class="field">
              <label>
                <i></i>
                <b>数据类型</b>
              </label>
              <div class="select">
                <el-select v-model="dataVal" placeholder="请选择">
                  <el-option
                    v-for="item in dataOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </div>
            </div>
            <div class="field">
              <label>
                <i></i>
                <b>所在区域</b>
              </label>
              <div class="select">
                <el-date-picker
                  v-model="monthVal"
                  type="month"
                  placeholder="选择月"
                ></el-date-picker>
              </div>
            </div>
          </div>
        </div>
      </j-dialog>
      <!-- 用户登录 -->
      <j-dialog
        :width="loginDialogObj.width"
        :height="loginDialogObj.height"
        :is-show="loginDialogObj.isShowDialog"
        :is-show-close="loginDialogObj.isShowClose"
      >
        <div slot="header">用户登录</div>
        <div slot="main">
          <div class="modal-content">
            <div class="inputFiled">
              <span class="icon-user">
                <i class="iconfont">&#xe62d;</i>
              </span>
              <input
                type="text"
                placeholder="请输入手机号码"
                v-model.trim="loginForm.userAccount"
                ref="userAccount"
              />
            </div>
            <div class="inputFiled">
              <span class="icon-password">
                <i class="iconfont">&#xe628;</i>
              </span>
              <input
                type="password"
                placeholder="请输入密码"
                v-model.trim="loginForm.userPwd"
                ref="userPwd"
              />
            </div>
            <div class="inputFiled clearfix">
              <span class="icon-code">
                <i class="iconfont">&#xe629;</i>
              </span>
              <input
                type="text"
                placeholder="请输入验证码"
                v-model.trim="loginForm.userCode"
                class="code"
                ref="userCode"
              />
              <div class="vertifyCode" id="vertCode" @click="getVertifyImg">
                <img
                  :src="verifyCodeImg"
                  id="verifyCode"
                  width="90"
                  height="35"
                />
              </div>
            </div>
            <div class="inputFiled">
              <div id="warningInfo">{{ warningInfo }}</div>
              <div class="login-btn" id="loginBtn" @click="login">登录</div>
              <div class="forget">
                <label id="toForget" @click="toForgetClick">忘记密码</label>
              </div>
            </div>
            <div class="tip">
              还没有账号?
              <b id="toRegister" @click="toRegisterClick">去注册</b>
            </div>
          </div>
        </div>
      </j-dialog>
      <!-- 用户注册 -->
      <j-dialog
        :width="registerDialogObj.width"
        :height="registerDialogObj.height"
        :is-show="registerDialogObj.isShowDialog"
        :is-show-close="registerDialogObj.isShowClose"
      >
        <div slot="header">用户注册</div>
        <div slot="main">
          <div class="modal-content">
            <div class="inputFiled">
              <span class="icon-user">
                <i class="iconfont">&#xe62d;</i>
              </span>
              <input
                type="text"
                placeholder="请输入手机号码"
                id="registerAccount"
                ref="registerAccount"
                v-model.trim="RegisterForm.registerAccount"
              />
            </div>
            <div class="inputFiled">
              <span class="icon-password">
                <i class="iconfont">&#xe628;</i>
              </span>
              <input
                type="password"
                placeholder="请输入密码"
                id="registerPwd"
                ref="registerPwd"
                v-model.trim="RegisterForm.registerPwd"
              />
            </div>
            <div class="inputFiled clearfix">
              <span class="icon-code">
                <i class="iconfont">&#xe629;</i>
              </span>
              <input
                type="text"
                placeholder="请输入手机验证码"
                class="code"
                id="validCode"
                ref="validCode"
                v-model.trim="RegisterForm.validCode"
              />
              <input
                class="vertifyCode"
                id="vertifyCode"
                type="button"
                @click="getCodeClick"
                value="获取手机验证码"
              />
            </div>
            <div class="inputFiled">
              <div id="registerWarnInfo">{{ registerWarnInfo }}</div>
              <div class="register-btn" id="registerBtn" @click="register">
                注册
              </div>
              <div class="forget"></div>
            </div>
            <div class="tip">
              已有账号?
              <b id="toLogin" @click="toLoginClick">去登录</b>
            </div>
          </div>
        </div>
      </j-dialog>
      <!-- 忘记密码 -->
      <j-dialog
        :width="forgetDialogObj.width"
        :height="forgetDialogObj.height"
        :is-show="forgetDialogObj.isShowDialog"
        :is-show-close="forgetDialogObj.isShowClose"
      >
        <div slot="header">忘记密码</div>
        <div slot="main">
          <div class="modal-content">
            <div class="inputFiled">
              <span class="icon-user">
                <i class="iconfont">&#xe62d;</i>
              </span>
              <input
                type="text"
                placeholder="请输入手机号码"
                id="fgtAccount"
                ref="fgtAccount"
                v-model.trim="forgetForm.fgtAccount"
              />
            </div>
            <div class="inputFiled clearfix">
              <span class="icon-code">
                <i class="iconfont">&#xe629;</i>
              </span>
              <input
                type="text"
                placeholder="请输入验证码"
                class="code"
                id="fgtCode"
                ref="fgtCode"
                v-model.trim="forgetForm.fgtCode"
              />
              <input
                class="vertifyCode"
                id="vertifyFgtCode"
                ref="vertifyFgtCode"
                type="button"
                @click="getFgtCodeClick"
                value="获取手机验证码"
              />
            </div>
            <div class="inputFiled">
              <span class="icon-password">
                <i class="iconfont">&#xe628;</i>
              </span>
              <input
                type="password"
                placeholder="请输入新密码"
                id="fgtPwd"
                ref="fgtPwd"
                v-model.trim="forgetForm.fgtPwd"
              />
            </div>
            <div class="inputFiled">
              <span class="icon-password">
                <i class="iconfont">&#xe628;</i>
              </span>
              <input
                type="password"
                placeholder="请确认新密码"
                id="reFgtPwd"
                ref="reFgtPwd"
                v-model.trim="forgetForm.reFgtPwd"
              />
            </div>
            <div class="inputFiled">
              <div id="fgtWarnInfo">{{ fgtWarnInfo }}</div>
              <div class="forget-btn" id="fgtConfirm" @click="forgetPwd">
                确认
              </div>
              <div class="forget"></div>
            </div>
            <div class="tip">
              <b id="backLogin" @click="toLoginClick">返回登录</b>
            </div>
          </div>
        </div>
      </j-dialog>
    </div>
  </div>
</template>

<script>
import jHeader from "@/components/header";
import jDialog from "@/components/dialog";
import {
  LOGIN_CONFIG,
  COOKIE_CONFIG,
  REGISTER_CONFIG
} from "config/url_config";
import commons from "utils/commons";
import formVerfication from "utils/formVerfication";
import Base64 from "utils/base64";
export default {
  name: "Home",
  components: {
    jHeader,
    jDialog
  },
  data() {
    return {
      // 免费试用
      freeDialogObj: {
        width: 650,
        height: 480,
        isShowFreeDialog: false,
        isShowFooter: true,
        confirmTxt: "立即申请"
      },
      // 数据下载
      downloadDialogObj: {
        width: 650,
        height: 480,
        isShowLoadDialog: false,
        isShowFooter: true,
        confirmTxt: "立即下载"
      },
      /**
       * 登录
       */
      // 登录弹窗配置对象
      loginDialogObj: {
        width: 505,
        height: 548,
        isShowDialog: true,
        isShowClose: true
      },
      // 登录表单输入值
      loginForm: {
        // 用户手机号码
        userAccount: "",
        // 用户密码
        userPwd: "",
        // 用户验证码
        userCode: ""
      },
      // 用户是否登录
      isLogin: this.$store.state.isShowLogin,
      // 用户登录验证提示信息
      warningInfo: "",
      // 验证码图片
      verifyCodeImg: "",
      /**
       * 注册
       */
      // 注册弹窗配置对象
      registerDialogObj: {
        width: 505,
        height: 548,
        isShowDialog: false,
        isShowClose: true
      },
      // 注册表单输入值
      RegisterForm: {
        // 用户手机号码
        registerAccount: "",
        // 用户密码
        registerPwd: "",
        // 用户验证码
        validCode: ""
      },
      // 用户注册验证提示信息
      registerWarnInfo: "",
      // 注册常用变量
      registerObj: {
        isClick: false,
        timer: null
      },
      // 忘记密码
      forgetDialogObj: {
        width: 505,
        height: 548,
        isShowDialog: false,
        isShowClose: true
      },
      // 注册表单输入值
      forgetForm: {
        // 用户手机号码
        fgtAccount: "",
        // 用户验证码
        fgtCode: "",
        // 用户新密码
        fgtPwd: "",
        // 用户确认新密码
        reFgtPwd: ""
      },
      // 用户忘记密码验证提示信息
      fgtWarnInfo: "",
      // 忘记密码常用变量
      forgetObj: {
        isClick: false,
        timer: null
      },
      dataVal: "1",
      dataOptions: [
        { value: "1", label: "地块数据" },
        { value: "2", label: "长势数据" },
        { value: "3", label: "气象数据" }
      ],
      monthVal: ""
    };
  },
  computed: {},
  mounted() {
    this.$store.commit("userStatus", this.$data);
    // 初始化
    this.init();
    // 判断是否登录
    //this.initIsLogin();
  },
  methods: {
    /* 初始化 */
    init() {
      // 初始化token
      this.validToken = this.getValidToken();
      //初始化获取验证码图片
      this.getVertifyImg();
    },
    /**
     * 获取验证码token
     */
    getValidToken: function() {
      let str = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx";
      return str.replace(/[xy]/g, function(c) {
        let r = (Math.random() * 16) | 0,
          v = c == "x" ? r : (r & 0x3) | 0x8;
        return v.toString(16);
      });
    },
    /**
     * 获取验证码图片
     */
    getVertifyImg() {
      const date = new Date();
      this.verifyCodeImg =
        this.baseApiPath +
        LOGIN_CONFIG.verify_url +
        "?validToken=" +
        this.validToken +
        "&datetime=" +
        date.getTime();
    },
    /**
     * 用户登录
     */
    login() {
      let that = this;
      let isValid = this.checkLoginForm();
      if (!isValid) {
        that.getVertifyImg();
        return false;
      }
      let accountName = this.loginForm.userAccount;
      let accountPwd = this.loginForm.userPwd;
      let code = this.loginForm.userCode;
      let validToken = this.validToken;
      let permAccount = {
        accountName: accountName,
        accountPwd: Base64.encode(accountPwd)
      };

      let jsonData = {
        permAccount: permAccount,
        validToken: validToken,
        verifyCode: code
      };

      this.$axios
        .post(this.baseApiPath + LOGIN_CONFIG.login_url, jsonData)
        .then(result => {
          const res = result.data;
          if (res.flag) {
            //location.reload();
            const data = res.data;
            commons.setCookie(COOKIE_CONFIG.cookieName, data.AccessToken, 120);
            that.loginDialogObj.isShowDialog = false;
          } else {
            that.warningInfo = res.msg;
            that.loginForm.userCode = "";
            // 重新刷新二维码
            that.getVertifyImg();
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    /**
     * 验证登录表单
     */
    checkLoginForm() {
      let userAccount = this.loginForm.userAccount;
      let userPwd = this.loginForm.userPwd;
      let regExp = /^0?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9]]|19[0-9])[0-9]{8}$/;
      if (userAccount == "") {
        this.$refs.userAccount.focus();
        this.warningInfo = "请输入手机号码";
        this.loginForm.userAccount = "";
        return false;
      } else if (!regExp.test(userAccount)) {
        this.$refs.userAccount.focus();
        this.warningInfo = "手机格式不正确";
        this.loginForm.userAccount = "";
        return false;
      } else if (userPwd == "") {
        this.$refs.userPwd.focus();
        this.warningInfo = "请输入密码";
        this.loginForm.userPwd = "";
        return false;
      } else if (!formVerfication.isScopeLength(userPwd, 6, 20)) {
        this.$refs.userPwd.focus();
        this.warningInfo = "密码为6-20位字母/数字/符号组合";
        this.loginForm.userPwd = "";
        return false;
      } else if (this.loginForm.userCode == "") {
        this.$refs.userCode.focus();
        this.warningInfo = "请输入验证码";
        this.loginForm.userCode = "";
        return false;
      } else {
        this.warningInfo = "";
        return true;
      }
    },
    /**
     * 用户注册
     */
    register() {
      const that = this;
      // 点击注册
      let isValid = that.checkRegisterForm();
      if (!isValid) {
        return false;
      }
      let userValidCode = that.RegisterForm.validCode; //手机验证码
      let registerAccount = that.RegisterForm.registerAccount; // 手机号
      let param = {};
      param.phone = registerAccount;
      param.pwd = Base64.encode(that.RegisterForm.registerPwd);
      param.smsVerifCode = userValidCode;
      this.$axios
        .post(this.baseApiPath + REGISTER_CONFIG.register_URL, param)
        .then(result => {
          console.log(result);
          const res = result.data;
          console.log(res);
        })
        .catch(error => {
          console.log(error);
        });
    },
    /**
     * 验证注册表单
     *
     */
    checkRegisterForm: function() {
      let registerAccount = this.RegisterForm.registerAccount;
      let registerPwd = this.RegisterForm.registerPwd;
      let regExp = /^0?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9]]|19[0-9])[0-9]{8}$/;
      if (registerAccount == "") {
        this.$refs.registerAccount.focus();
        this.registerWarnInfo = "请输入手机号码";
        this.RegisterForm.registerAccount = "";
        return false;
      } else if (!regExp.test(registerAccount)) {
        this.$refs.registerAccount.focus();
        this.registerWarnInfo = "手机格式不正确";
        this.RegisterForm.registerAccount = "";
        return false;
      } else if (registerPwd == "") {
        this.$refs.registerPwd.focus();
        this.registerWarnInfo = "请输入密码";
        this.RegisterForm.registerPwd = "";
        return false;
      } else if (!formVerfication.isScopeLength(registerPwd, 6, 20)) {
        this.$refs.registerPwd.focus();
        this.registerWarnInfo = "密码为6-20位字母/数字/符号组合";
        this.RegisterForm.registerPwd = "";
        return false;
      } else if (this.RegisterForm.validCode == "") {
        this.$refs.validCode.focus();
        this.registerWarnInfo = "请输入手机验证码";
        this.RegisterForm.validCode = "";
        return false;
      } else {
        this.registerWarnInfo = "";
        return true;
      }
    },

    /**
     * 获取手机验证码
     */
    async getCodeClick() {
      let that = this;
      // 验证手机号是否存在
      // let valid = that.checkUserRegister();
      let registerAccount = this.RegisterForm.registerAccount;
      let regExp = /^0?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9]]|19[0-9])[0-9]{8}$/;
      if (this.RegisterForm.registerAccount == "") {
        this.$refs.registerAccount.focus();
        this.registerWarnInfo = "请输入手机号码";
        this.RegisterForm.registerAccount = "";
        return false;
      } else if (!regExp.test(registerAccount)) {
        this.$refs.registerAccount.focus();
        this.registerWarnInfo = "手机格式不正确";
        this.RegisterForm.registerAccount = "";
        return false;
      }
      let isFlag = true;
      // 检查手机号是否存在
      await this.$axios
        .get(this.baseApiPath + REGISTER_CONFIG.checkAccountExists, {
          params: {
            accountName: this.RegisterForm.registerAccount
          }
        })
        .then(result => {
          let res = result.data;
          if (res.data) {
            this.registerWarnInfo = res.msg;
            isFlag = false;
            // let target = document.querySelector("#vertifyCode");
            // target.classList.add("disabled");
            // target.setAttribute("disabled", "disabled");
          }
        })
        .catch(error => {
          console.log(error);
        });
      if (!isFlag) {
        return false;
      }
      // 触发定时器计时器
      let totalCount = 60;
      let target = document.querySelector("#vertifyCode");
      let triggerTime = function() {
        if (totalCount > 0) {
          let txt = totalCount + "秒后重发";
          target.classList.add("disabled");
          target.setAttribute("disabled", "disabled");
          target.value = txt;
          totalCount--;
        } else {
          totalCount = 60;
          target.value = "获取验证码";
          target.classList.remove("disabled");
          target.removeAttribute("disabled", "disabled");
          clearInterval(that.registerObj.timer);
        }
      };
      // 没有注册就发送手机验证码
      if (!that.registerObj.isClick) {
        that.registerObj.isClick = true;
        let registerAccount = this.RegisterForm.registerAccount;
        this.registerWarnInfo = "";
        this.$axios
          .get(this.baseApiPath + REGISTER_CONFIG.sendSmsCode_URL, {
            params: {
              mobile: registerAccount
            }
          })
          .then(result => {
            console.log(result);
            let res = result.data;
            if (res.flag) {
              that.registerObj.timer = setInterval(function() {
                triggerTime();
              }, 1000);
            } else {
              target.value = "获取手机验证码";
              target.classList.remove("disabled");
              target.removeAttribute("disabled", "disabled");
              that.registerWarnInfo = res.msg;
              that.registerObj.isClick = false; //进行标志，防止多次点击
            }
          })
          .catch(error => {
            console.log(error);
            target.classList.remove("disabled");
            target.removeAttribute("disabled", "disabled");
            that.registerObj.isClick = false;
          });
      }
      setTimeout(function() {
        that.registerObj.isClick = false;
      }, 5000);
    },

    /**
     * 忘记密码
     */
    forgetPwd() {
      const that = this;
      //点击确定修改密码
      const isValid = that.checkFgtForm();
      if (!isValid) {
        return false;
      }
      const accountName = that.forgetForm.fgtAccount;
      const accountPwd = that.forgetForm.fgtPwd;
      const code = that.forgetForm.fgtCode;
      const param = {};
      param.accountName = accountName;
      param.accountPwd = Base64.encode(accountPwd);
      param.smsVerifCode = code;
      this.$axios
        .post(this.baseApiPath + LOGIN_CONFIG.resetPwd_url, param)
        .then(result => {
          console.log(result);
          const res = result.data;
          if (res.flag) {
            console.log("修改密码成功");
            // 关闭后回调--打开登录框
            this.forgetDialogObj.isShowDialog = false;
            this.loginDialogObj.isShowDialog = true;
          } else {
            console.log("修改密码失败");
          }
        })
        .catch(error => {
          console.log(error);
        });
    },

    /**
     * 验证忘记密码表单
     *
     */
    checkFgtForm() {
      const fgtAccount = this.forgetForm.fgtAccount;
      const fgtCode = this.forgetForm.fgtCode;
      const fgtPwd = this.forgetForm.fgtPwd;
      const reFgtPwd = this.forgetForm.reFgtPwd;
      const regExp = /^0?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9]]|19[0-9])[0-9]{8}$/;
      if (fgtAccount == "") {
        this.$refs.fgtAccount.focus();
        this.fgtWarnInfo = "请输入手机号码";
        this.forgetForm.fgtAccount = "";
        return false;
      } else if (!regExp.test(fgtAccount)) {
        this.$refs.fgtAccount.focus();
        this.fgtWarnInfo = "手机格式不正确";
        this.forgetForm.fgtAccount = "";
        return false;
      } else if (fgtCode == "") {
        this.$refs.fgtCode.focus();
        this.fgtWarnInfo = "请输入手机验证码";
        this.forgetForm.fgtCode = "";
        return false;
      } else if (fgtPwd == "") {
        this.$refs.fgtPwd.focus();
        this.fgtWarnInfo = "请输入密码";
        this.forgetForm.fgtPwd = "";
        return false;
      } else if (!formVerfication.isScopeLength(fgtPwd, 6, 20)) {
        this.$refs.fgtPwd.focus();
        this.fgtWarnInfo = "密码为6-20位字母/数字/符号组合";
        this.forgetForm.fgtPwd = "";
        return false;
      } else if (reFgtPwd == "") {
        this.$refs.reFgtPwd.focus();
        this.fgtWarnInfo = "请输入确认密码";
        this.forgetForm.reFgtPwd = "";
        return false;
      } else if (!formVerfication.isScopeLength(reFgtPwd, 6, 20)) {
        this.$refs.reFgtPwd.focus();
        this.fgtWarnInfo = "密码为6-20位字母/数字/符号组合";
        this.forgetForm.reFgtPwd = "";
        return false;
      } else if (fgtPwd !== reFgtPwd) {
        this.fgtWarnInfo = "两次输入的密码不一致";
        return false;
      } else {
        this.fgtWarnInfo = "";
        return true;
      }
    },

    /**
     * 忘记密码
     * 获取手机验证码
     */
    async getFgtCodeClick() {
      const that = this;
      // 验证手机号是否存在
      var userAccount = this.forgetForm.fgtAccount;
      var regExp = /^0?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9]]|19[0-9])[0-9]{8}$/;
      if (userAccount == "") {
        this.$refs.fgtAccount.focus();
        this.fgtWarnInfo = "请输入手机号码";
        this.forgetForm.fgtAccount = "";
        return false;
      } else if (!regExp.test(userAccount)) {
        this.$refs.fgtAccount.focus();
        this.fgtWarnInfo = "手机格式不正确";
        this.forgetForm.fgtAccount = "";
        return false;
      } else {
        this.fgtWarnInfo = "";
      }
      let isFlag = true;
      await this.$axios
        .get(this.baseApiPath + REGISTER_CONFIG.checkAccountExists, {
          params: {
            accountName: userAccount
          }
        })
        .then(result => {
          const res = result.data;
          if (res.data) {
            this.fgtWarnInfo = "";
          } else {
            this.fgtWarnInfo = res.msg;
            isFlag = false;
          }
        })
        .catch(error => {
          console.log(error);
        });
      if (!isFlag) {
        return false;
      }
      // 触发定时器计时器
      let totalCount = 60;
      let target = document.querySelector("#vertifyFgtCode");
      const triggerTime = function() {
        if (totalCount > 0) {
          let txt = totalCount + "秒后重发";
          target.classList.add("disabled");
          target.setAttribute("disabled", "disabled");
          target.value = txt;
          totalCount--;
        } else {
          totalCount = 60;
          target.value = "获取验证码";
          target.classList.remove("disabled");
          target.removeAttribute("disabled", "disabled");
          clearInterval(that.forgetObj.timer);
        }
      };
      // 有注册过就发送手机验证码
      if (!that.forgetObj.isClick) {
        that.forgetObj.isClick = true;
        const fgtAccount = this.forgetForm.fgtAccount;
        this.registerWarnInfo = "";
        this.$axios
          .get(this.baseApiPath + LOGIN_CONFIG.sendsms_url, {
            params: {
              mobile: fgtAccount
            }
          })
          .then(result => {
            console.log(result);
            let res = result.data;
            if (res.flag) {
              that.forgetObj.timer = setInterval(function() {
                triggerTime();
              }, 1000);
            } else {
              target.value = "获取手机验证码";
              target.classList.remove("disabled");
              target.removeAttribute("disabled", "disabled");
              that.registerWarnInfo = res.msg;
              that.forgetObj.isClick = false; //进行标志，防止多次点击
            }
          })
          .catch(error => {
            console.log(error);
            target.classList.remove("disabled");
            target.removeAttribute("disabled", "disabled");
            that.forgetObj.isClick = false;
          });
      }
      setTimeout(function() {
        that.forgetObj.isClick = false;
      }, 5000);
    },

    /**
     * 清空输入框
     */
    clearUserInput() {
      this.loginForm.userAccount = "";
      this.loginForm.userPwd = "";
      this.loginForm.userCode = "";
      this.warningInfo = "";
      this.RegisterForm.registerAccount = "";
      this.RegisterForm.registerPwd = "";
      this.RegisterForm.validCode = "";
      this.registerWarnInfo = "";
      this.forgetForm.fgtAccount = "";
      this.forgetForm.fgtCode = "";
      this.forgetForm.fgtPwd = "";
      this.forgetForm.reFgtPwd = "";
      this.fgtWarnInfo = "";
    },
    /**
     * 跳转到注册
     */
    toRegisterClick() {
      this.clearUserInput();
      this.loginDialogObj.isShowDialog = false;
      this.registerDialogObj.isShowDialog = true;
      this.forgetDialogObj.isShowDialog = false;
    },
    /**
     * 跳转到忘记密码
     */
    toForgetClick() {
      this.clearUserInput();
      this.loginDialogObj.isShowDialog = false;
      this.registerDialogObj.isShowDialog = false;
      this.forgetDialogObj.isShowDialog = true;
    },
    /**
     * 跳转到登录
     */
    toLoginClick() {
      this.clearUserInput();
      this.loginDialogObj.isShowDialog = true;
      this.registerDialogObj.isShowDialog = false;
      this.forgetDialogObj.isShowDialog = false;
    },
    /**
     * 关闭免费使用弹窗
     */
    closeDialog(isShow) {
      this.freeDialogObj.isShowFreeDialog = !isShow;
    },
    /**
     * 跳转到免费试用页面
     */
    openFreeLink() {
      this.freeDialogObj.isShowFreeDialog = false;
      this.$router.push({ name: "free" });
    }
  }
};
</script>
<style lang="less">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-size: 14px;
  color: #333333;
  width: 100%;
  height: 100%;
  background: #ffffff;
  .layout {
    width: 100%;
    height: 100%;
    background: #f5f5f5;
    .main-content {
      position: relative;
      width: 100%;
      height: calc(100% - 56px);
    }
  }
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}

// 免费试用弹框
.free-con {
  p {
    margin-bottom: 65px;
    font-size: 16px;
    color: #707070;
    text-align: center;
    span {
      display: inline-block;
      vertical-align: middle;
      width: 85px;
      height: 30px;
      margin: 0 10px;
      line-height: 30px;
      font-size: 16px;
      color: #007dff;
      text-align: center;
      border: 1px solid #007dff;
    }
  }
  ul {
    margin-bottom: 114px;
    padding: 0 60px;
    display: flex;
    justify-content: space-around;
    li {
      i {
        display: inline-block;
        vertical-align: middle;
        margin-right: 12px;
        font-size: 36px;
        color: #007dff;
      }
      span {
        display: inline-block;
        vertical-align: middle;
        font-size: 16px;
        color: #707070;
        b {
          display: inline-block;
          margin-top: 4px;
          color: #007dff;
        }
      }
    }
  }
}
// 数据下载弹窗
.download-con {
  margin: 16px auto 40px;
  overflow: hidden;
  .field {
    margin-bottom: 30px;
    padding-left: 115px;
    label {
      display: inline-block;
      vertical-align: middle;
      margin-right: 20px;
      font-size: 16px;
      i {
        display: inline-block;
        vertical-align: middle;
        width: 2px;
        height: 16px;
        margin-right: 10px;
        background: #007dff;
      }
      b {
        font-weight: normal;
        display: inline-block;
        vertical-align: middle;
      }
    }
    .select {
      display: inline-block;
      vertical-align: middle;
      /deep/ .el-date-editor.el-input {
        width: 180px !important;
      }
      /deep/ .el-select {
        width: 180px !important;
      }
    }
  }
}
// 用户登录注册忘记密码
.modal-content {
  .inputFiled {
    position: relative;
    width: 315px;
    margin: 0 auto;
    margin-bottom: 10px;
    overflow: hidden;
    span {
      position: absolute;
      top: 0;
      left: 0;
      width: 40px;
      height: 40px;
      line-height: 40px;
      font-size: 15px;
      color: #707070;
      text-align: center;
    }
    input {
      width: 100%;
      height: 40px;
      line-height: 40px;
      padding-left: 40px;
      font-size: 14px;
      color: #333333;
      border: 1px solid #cccccc;
      &.code {
        float: left;
        width: 187px;
      }
    }
    .vertifyCode {
      float: right;
      width: 120px;
      height: 40px;
      line-height: 40px;
      padding-left: 0;
      font-size: 14px;
      color: #007dff;
      text-align: center;
      border: 1px solid #cccccc;
      img {
        width: 100%;
        height: 100%;
      }
    }
    #warningInfo,
    #registerWarnInfo,
    #fgtWarnInfo {
      margin-top: 10px;
      font-size: 14px;
      color: #ff0000;
      height: 30px;
      line-height: 30px;
    }
  }
  .login-btn,
  .register-btn,
  .forget-btn {
    // margin-top: 30px;
    height: 40px;
    line-height: 40px;
    background-color: #007dff;
    font-size: 14px;
    color: #ffffff;
    text-align: center;
    cursor: pointer;
  }
  .forget {
    margin-top: 10px;
    font-size: 14px;
    color: #999999;
    label {
      cursor: pointer;
    }
  }
  .tip {
    position: absolute;
    bottom: 50px;
    width: 100%;
    font-size: 14px;
    color: #999999;
    text-align: center;
    b {
      color: #007dff;
      margin-left: 10px;
      cursor: pointer;
    }
  }
}
</style>
