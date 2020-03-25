<template>
  <div class="panel-container">
    <div class="panel-title">安全设置</div>
    <div class="panel-content">
      <div class="form">
        <div class="filed">
          <label><i>*</i>原密码</label>
          <input type="text" placeholder="输入原密码" />
        </div>
        <div class="filed">
          <label><i>*</i>新密码</label>
          <input type="text" placeholder="输入新密码" />
        </div>
        <div class="filed">
          <label><i>*</i>确认密码</label>
          <input type="text" placeholder="输入确认密码" />
        </div>
        <div class="save-btn">保存</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "mySafe"
};
</script>

<style lang="less" scoped>
.panel-container {
  .panel-title {
    height: 70px;
    line-height: 70px;
    padding-left: 25px;
    border-bottom: 1px solid #dadada;
    font-size: 20px;
  }
  .panel-content {
    padding-top: 25px;
    padding-left: 30px;
    .form {
      .filed {
        margin-bottom: 30px;
        &:last-child {
          margin-bottom: 0;
        }
        label {
          display: inline-block;
          vertical-align: middle;
          width: 80px;
          i {
            font-size: 14px;
            color: #ff0000;
          }
        }
        input {
          display: inline-block;
          vertical-align: middle;
          width: 230px;
          height: 40px;
          line-height: 40px;
          padding-left: 14px;
          color: #999999;
          border: 1px solid #cccccc;
        }
      }
      .save-btn {
        margin-top: 50px;
        width: 150px;
        height: 34px;
        line-height: 34px;
        font-size: 14px;
        color: #ffffff;
        text-align: center;
        background: #007dff;
        cursor: pointer;
      }
    }
  }
}
</style>
