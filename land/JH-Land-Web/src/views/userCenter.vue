<template>
  <div class="main">
    <div class="content">
      <div class="left-column">
        <div class="user-info">
          <img src="../assets/images/block.png" alt="用户头像" />
          <p>小珈助手</p>
        </div>
        <div class="menu-sidebar">
          <ul>
            <router-link
              to="/userCenter/myCollect"
              active-class="active"
              tag="li"
              >我的收藏</router-link
            >
            <router-link to="/userCenter/mySafe" active-class="active" tag="li"
              >安全设置</router-link
            >
          </ul>
        </div>
      </div>
      <div class="right-content">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "userCenter"
};
</script>

<style lang="less" scoped>
.main {
  padding: 20px 80px;
  overflow: auto;
  .content {
    .left-column {
      float: left;
      width: 220px;
      .user-info {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin-bottom: 20px;
        height: 200px;
        background: #ffffff;
        img {
          width: 70px;
          height: 70px;
          border-radius: 50%;
        }
        p {
          margin-top: 20px;
        }
      }
      .menu-sidebar {
        background: #ffffff;
        ul {
          li {
            position: relative;
            height: 40px;
            line-height: 40px;
            text-align: center;
            &.active {
              &::before {
                content: "";
                display: inline-block;
                position: absolute;
                top: 0;
                left: 0;
                width: 6px;
                height: 40px;
                background: #007dff;
              }
            }
          }
        }
      }
    }
    .right-content {
      float: right;
      width: 860px;
      min-height: 500px;
      background: #ffffff;
    }
  }
}
</style>
