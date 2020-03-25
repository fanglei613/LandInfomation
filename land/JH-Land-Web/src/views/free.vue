<template>
  <div class="container">
    <div class="content">
      <div class="banner">
        <h4>
          全国地块查询平台<br />
          湖北、安徽、河南、湖南已上线，山东、黑龙江、新疆正在更新中...
        </h4>
        <p>注册就能领取免费</p>
        <ul>
          <li><i></i><span>试用时间：7天</span></li>
          <li><i></i><span>查询次数：不限</span></li>
          <li><i></i><span>下载次数：不限</span></li>
        </ul>
        <p class="sub">
          直接开通VIP请联系客服<br />
          客服QQ:2357667118
        </p>
      </div>
      <div class="trial-sidebar">
        <div class="trail-head">
          <img
            src="../assets/images/index/logo.png"
            width="90"
            height="40"
            alt="logo"
          />
          <div class="line"></div>
          <span>地查查</span>
        </div>
        <div class="trial-content">
          <div class="inputFiled">
            <input type="text" placeholder="请输入手机号码" id="userAccount" />
          </div>
          <div class="inputFiled clearfix">
            <input
              type="text"
              placeholder="请输入验证码"
              class="code"
              id="userCode"
            />
            <input
              class="vertifyCode"
              id="vertifyCode"
              type="button"
              value="获取验证码"
            />
          </div>
          <div class="inputFiled">
            <input type="text" placeholder="请输入您的姓名" id="userName" />
          </div>
          <div class="inputFiled">
            <input
              type="text"
              placeholder="请输入您的公司名称"
              id="userCpyName"
            />
          </div>
          <div class="inputFiled">
            <div id="warningInfo"></div>
            <div class="free-btn" id="loginBtn">免费试用</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "free"
};
</script>

<style lang="less" scoped>
.container {
  width: 100%;
  height: 100%;
  background: linear-gradient(
    90deg,
    rgba(11, 66, 245, 1) 0%,
    rgba(52, 227, 255, 1) 100%
  );
  overflow: hidden;
  .content {
    position: relative;
    width: 100%;
    height: 100%;
    background: url("../assets/images/free/bg-img.png") no-repeat;
    background-size: 573px 428px;
    background-position: 280px 160px;
    overflow: hidden;
    .banner {
      margin-top: 65px;
      margin-left: 83px;
      color: #ffffff;
      > h4 {
        line-height: 34px;
        font-size: 20px;
        font-weight: bold;
      }
      ul {
        margin-top: 20px;
        li {
          margin-bottom: 20px;
          &:last-child {
            margin-bottom: 0;
          }
          i {
            display: inline-block;
            vertical-align: middle;
            width: 19px;
            height: 15px;
            margin-right: 8px;
            background: url("../assets/images/free/checked.png") no-repeat;
            background-size: 19px 15px;
          }
          span {
            display: inline-block;
            vertical-align: middle;
          }
        }
      }
      p {
        margin-top: 60px;
        line-height: 24px;
        &.sub {
          margin-top: 44px;
        }
      }
    }
    .trial-sidebar {
      position: absolute;
      top: 50%;
      right: 65px;
      width: 400px;
      height: 500px;
      padding-top: 40px;
      transform: translateY(-50%);
      background: #ffffff;
      box-shadow: 0px 3px 6px rgba(0, 0, 0, 0.16);
      .trail-head {
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 20px;
        font-weight: bold;
        .line {
          margin: 0 10px;
          width: 1px;
          height: 12px;
          background: #cccccc;
        }
      }
      .trial-content {
        margin-top: 45px;
        .inputFiled {
          width: 250px;
          margin: 0 auto;
          margin-bottom: 10px;
          overflow: hidden;
          input {
            width: 100%;
            height: 40px;
            line-height: 40px;
            padding-left: 10px;
            font-size: 14px;
            color: #333333;
            border: 1px solid #cccccc;
            &.code {
              float: left;
              width: 150px;
            }
          }
          .vertifyCode {
            float: right;
            width: 88px;
            height: 40px;
            line-height: 40px;
            padding-left: 0;
            font-size: 14px;
            color: #007dff;
            text-align: center;
            border: 1px solid #cccccc;
          }
        }
        .free-btn {
          margin-top: 40px;
          height: 40px;
          line-height: 40px;
          background-color: #007dff;
          font-size: 14px;
          color: #ffffff;
          text-align: center;
          cursor: pointer;
        }
      }
    }
  }
}
</style>
