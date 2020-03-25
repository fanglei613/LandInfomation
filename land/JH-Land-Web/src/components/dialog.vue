<template>
  <div class="dialog">
    <!-- 外层遮罩 -->
    <div class="dialog-cover" v-if="isShow">
      <!-- 弹窗内容 -->
      <div
        class="dialog-container"
        :style="{ width: width + 'px', height: height + 'px' }"
      >
        <div class="dialog-close" @click="closeDialog" v-if="isShowClose">
          <i class="iconfont">&#xe63e;</i>
        </div>
        <div class="dialog-header">
          <slot name="header"></slot>
        </div>
        <div class="dialog-content">
          <slot name="main"></slot>
        </div>
        <div class="dialog-footer" v-if="isShowFooter">
          <div class="confirm-btn btn" @click="handleConfirmClcik">
            {{ confirmTxt }}
          </div>
          <div class="cancel-btn btn" @click="handleCancelClick">
            {{ cancelTxt }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "jDialog",
  props: {
    // 控制显示弹窗
    isShow: {
      type: Boolean,
      default() {
        return false;
      },
      required: true
    },
    // 是否显示右上角关闭按钮
    isShowClose: {
      type: Boolean,
      default() {
        return false;
      }
    },
    // 是否显示底部操作按钮
    isShowFooter: {
      type: Boolean,
      default() {
        return false;
      }
    },
    // 弹框内容宽度
    width: {
      type: [Number, String],
      default() {
        return 650;
      }
    },
    // 弹窗内容高度
    height: {
      type: [Number, String],
      default() {
        return 650;
      }
    },
    // 确定按钮文字
    confirmTxt: {
      type: String,
      default() {
        return "确认";
      }
    },
    // 取消按钮文字
    cancelTxt: {
      type: String,
      default() {
        return "取消";
      }
    }
  },
  methods: {
    closeDialog() {
      this.$emit("closeDialog", this.isShow);
    },
    handleConfirmClcik() {
      this.$emit("handleConfirmClcik");
    },
    handleCancelClick() {
      this.$emit("closeDialog", this.isShow);
    }
  }
};
</script>

<style lang="less" scoped>
.dialog {
  .dialog-cover {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    width: 100%;
    height: 100%;
    background: rgba(51, 51, 51, 0.5);
    z-index: 1099;
    .dialog-container {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      background: #ffffff;
      z-index: 1100;
      .dialog-close {
        position: absolute;
        top: 25px;
        right: 25px;
        i {
          font-size: 16px;
          color: #007dff;
        }
      }
      .dialog-header {
        padding: 55px 0 50px;
        font-size: 24px;
        font-weight: bold;
        color: #007dff;
        text-align: center;
      }
      .dialog-footer {
        padding: 0 0 70px;
        text-align: center;
        font-size: 0;
        .btn {
          display: inline-block;
          width: 125px;
          height: 36px;
          line-height: 36px;
          font-size: 14px;
          color: #007dff;
          text-align: center;
          border: 1px solid #007dff;
          &.confirm-btn {
            margin-right: 80px;
            color: #ffffff;
            background-color: #007dff;
          }
        }
      }
    }
  }
}
</style>
