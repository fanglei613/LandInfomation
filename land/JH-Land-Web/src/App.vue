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
            <p>想要继续体验地查查，请申请<span>免费试用</span>哦!</p>
            <ul>
              <li>
                <i class="iconfont">&#xe637;</i>
                <span>试用时间<br /><b>七天</b></span>
              </li>
              <li>
                <i class="iconfont">&#xe63b;</i>
                <span>查询次数：<br /><b>不限</b></span>
              </li>
              <li>
                <i class="iconfont">&#xe636;</i>
                <span>下载次数：<br /><b>不限</b></span>
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
              <label><i></i><b>所在区域</b></label>
              <div class="select"></div>
            </div>
            <div class="field">
              <label><i></i><b>数据类型</b></label>
              <div class="select">
                <el-select v-model="dataVal" placeholder="请选择">
                  <el-option
                    v-for="item in dataOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </div>
            </div>
            <div class="field">
              <label><i></i><b>所在区域</b></label>
              <div class="select">
                <el-date-picker
                  v-model="monthVal"
                  type="month"
                  placeholder="选择月"
                >
                </el-date-picker>
              </div>
            </div>
          </div>
        </div>
      </j-dialog>
      <!-- 用户登录 -->
      <j-dialog
        :width="loginDialogObj.width"
        :height="loginDialogObj.height"
        :is-show="loginDialogObj.isShowLoginDialog"
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
                id="userAccount"
              />
            </div>
            <div class="inputFiled">
              <span class="icon-password">
                <i class="iconfont">&#xe628;</i>
              </span>
              <input type="password" placeholder="请输入密码" id="userPwd" />
            </div>
            <div class="inputFiled clearfix">
              <span class="icon-code">
                <i class="iconfont">&#xe629;</i>
              </span>
              <input
                type="text"
                placeholder="请输入验证码"
                class="code"
                id="userCode"
              />
              <div class="vertifyCode" id="vertCode">
                <img src="" id="verifyCode" width="90" height="35" />
              </div>
            </div>
            <div class="inputFiled">
              <div id="warningInfo"></div>
              <div class="login-btn" id="loginBtn">登录</div>
              <div class="forget"><label id="toForget">忘记密码</label></div>
            </div>
            <div class="tip">还没有账号?<b id="toRegister">去注册</b></div>
          </div>
        </div>
      </j-dialog>
      <!-- 用户注册 -->
      <j-dialog
        :width="registerDialogObj.width"
        :height="registerDialogObj.height"
        :is-show="registerDialogObj.isShowLoginDialog"
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
              />
              <input
                class="vertifyCode"
                id="vertifyCode"
                type="button"
                value="获取手机验证码"
              />
            </div>
            <div class="inputFiled">
              <div id="registerWarnInfo"></div>
              <div class="register-btn" id="registerBtn">注册</div>
              <div class="forget"></div>
            </div>
            <div class="tip">已有账号?<b id="toLogin">去登录</b></div>
          </div>
        </div>
      </j-dialog>
      <!-- 忘记密码 -->
      <j-dialog
        :width="forgetDialogObj.width"
        :height="forgetDialogObj.height"
        :is-show="forgetDialogObj.isShowLoginDialog"
        :is-show-close="forgetDialogObj.isShowClose"
      >
        <div slot="header">忘记密码</div>
        <div slot="main">
          <div class="modal-content">
            <div class="inputFiled">
              <span class="icon-user">
                <i class="iconfont">&#xe62d;</i>
              </span>
              <input type="text" placeholder="请输入手机号码" id="fgtAccount" />
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
              />
              <input
                class="vertifyCode"
                id="vertifyFgtCode"
                type="button"
                value="获取手机验证码"
              />
            </div>
            <div class="inputFiled">
              <span class="icon-password">
                <i class="iconfont">&#xe628;</i>
              </span>
              <input type="password" placeholder="请输入新密码" id="fgtPwd" />
            </div>
            <div class="inputFiled">
              <span class="icon-password">
                <i class="iconfont">&#xe628;</i>
              </span>
              <input type="password" placeholder="请确认新密码" id="reFgtPwd" />
            </div>
            <div class="inputFiled">
              <div id="fgtWarnInfo"></div>
              <div class="forget-btn" id="fgtConfirm">确认</div>
              <div class="forget"></div>
            </div>
            <div class="tip"><b id="backLogin">返回登录</b></div>
          </div>
        </div>
      </j-dialog>
    </div>
  </div>
</template>

<script>
import jHeader from "@/components/header";
import jDialog from "@/components/dialog";
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
      // 用户登录
      isLogin: this.$store.state.isShowLogin,
      loginDialogObj: {
        width: 505,
        height: 548,
        isShowLoginDialog: false,
        isShowClose: true
      },
      // 用户注册
      registerDialogObj: {
        width: 505,
        height: 548,
        isShowLoginDialog: false,
        isShowClose: true
      },
      // 忘记密码
      forgetDialogObj: {
        width: 505,
        height: 548,
        isShowLoginDialog: false,
        isShowClose: true
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
    console.log(this.$data);
    this.$store.commit("userStatus", this.$data);
    console.log(this.$store.state);
    // this.loginDialogObj.isShowLoginDialog = this.$store.state.isShowLogin;
  },
  methods: {
    // 关闭免费使用弹窗
    closeDialog(isShow) {
      console.log(isShow);
      this.freeDialogObj.isShowFreeDialog = !isShow;
    },
    // 跳转到免费试用页面
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
  }
  .login-btn,
  .register-btn,
  .forget-btn {
    margin-top: 30px;
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
