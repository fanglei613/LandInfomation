<template>
  <div class="timeline">
    <div
      class="slide-left"
      @click="slideLeft"
      :class="{ disabled: !this.canSlideLeft }"
    ></div>
    <div class="slide-container">
      <div class="slide-overflow">
        <div
          class="slide-inner-container"
          :class="{ center: sizeNum < 5 }"
          :style="{ marginRight: slideDis + 'px' }"
        >
          <div
            class="slide-item"
            v-for="(item, index) in timeData"
            :key="index"
            @click="handleTimelineClick(item, index)"
            :class="{ active: currentIndex == index }"
          >
            <div class="timeline-circle"></div>
            <div class="timeline-line"></div>
            <div class="timeline-date">{{ item }}</div>
          </div>
        </div>
      </div>
    </div>
    <div
      class="slide-right"
      @click="slideRight"
      :class="{ disabled: !this.canSlideRight }"
    ></div>
  </div>
</template>

<script>
export default {
  props: {
    timeData: {
      type: [Object, Array],
      dafault() {
        return [];
      }
    }
  },
  data() {
    return {
      currentIndex: 0,
      slideDis: 0, //滑动距离
      sizeNum: 5, //时间线可视区域日期数
      canSlideLeft: true, //左边可滑动
      canSlideRight: false //右边可滑动
    };
  },
  watch: {
    timeData(newVal, oldVal) {
      if (newVal) {
        const len = this.timeData.length;
        if (len < this.sizeNum) {
          this.slideLeft = false;
          this.slideRight = false;
        }
        this.currentIndex = this.timeData.length - 1;
      }
    }
  },
  mounted() {},
  methods: {
    handleTimelineClick(event, idx) {
      this.currentIndex = idx;
      this.$emit("childTimeClick", event);
    },
    slideLeft() {
      if (this.canSlideLeft) {
        this.canSlideRight = true;
        const totalDis = this.timeData.length * 120; // 所有日期数的总长度
        const domDis = this.sizeNum * 120; // 可视区域容器宽度
        const canSlideDis = totalDis - domDis; //可滑动总长度
        if (
          this.canSlideLeft &&
          (this.timeData.length > this.sizeNum ||
            this.timeData.length == this.sizeNum)
        ) {
          this.slideDis = this.slideDis - domDis;
        } else {
          return false;
        }
        // 如果滑动到最后一屏需要判断
        if (this.slideDis == -canSlideDis || this.slideDis < -canSlideDis) {
          const remainDis = canSlideDis + this.slideDis + domDis;
          this.slideDis = this.slideDis + domDis - remainDis;
          this.canSlideLeft = false;
        } else {
          this.canSlideLeft = true;
        }
      }
    },
    slideRight() {
      if (this.canSlideRight) {
        this.canSlideLeft = true;
        const domDis = this.sizeNum * 120; // 可视区域容器宽度
        if (
          this.canSlideRight &&
          (this.timeData.length > this.sizeNum ||
            this.timeData.length == this.sizeNum)
        ) {
          this.slideDis = this.slideDis + domDis;
        } else {
          return false;
        }
        if (this.slideDis == 0 || this.slideDis > 0) {
          this.slideDis = 0;
          this.canSlideRight = false;
        } else {
          this.canSlideRight = true;
        }
      }
    }
  }
};
</script>

<style lang="less" scoped>
/* 时间轴组件 */
.timeline {
  position: absolute;
  right: 10px;
  bottom: 14px;
  max-width: 800px;
  height: 35px;
  background: #ffffff;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.3);
  z-index: 100;
  .slide-left {
    position: absolute;
    top: 0;
    left: 0;
    width: 35px;
    height: 35px;
    background: url("../assets/images/ico_arrow_left.png") no-repeat center;
    background-size: 14px 20px;
    box-shadow: 0px 3px 6px rgba(0, 0, 0, 0.16);
    cursor: pointer;
    &.disabled {
      background: url("../assets/images/disabled-slide-left.png") no-repeat
        center;
      background-size: 14px 20px;
      cursor: text;
    }
  }
  .slide-right {
    position: absolute;
    top: 0;
    right: 0;
    width: 35px;
    height: 35px;
    background: url("../assets/images/ico_arrow_right.png") no-repeat center;
    background-size: 14px 20px;
    box-shadow: 0px 3px 6px rgba(0, 0, 0, 0.16);
    cursor: pointer;
    &.disabled {
      background: url("../assets/images/disabled-slide-right.png") no-repeat
        center;
      background-size: 14px 20px;
      cursor: text;
    }
  }
  .slide-container {
    padding: 0 100px;
    .slide-overflow {
      width: 100%;
      //padding: 0 32px 0 40px;
      overflow: hidden;
      .slide-inner-container {
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
        &.center {
          justify-content: center;
        }
        .slide-item {
          display: inline-flex;
          flex-direction: column;
          align-items: center;
          flex: 0 0 120px;
          width: 130px;
          height: 35px;
          cursor: pointer;
          &.active {
            .timeline-circle {
              background: #007dff;
            }
            .timeline-line {
              border-top: 1px solid #007dff;
            }
            .timeline-date {
              color: #007dff;
            }
          }
          .timeline-circle {
            position: relative;
            width: 8px;
            height: 8px;
            flex: 0 0 8px;
            background: #cccccc;
            border-radius: 50%;
            margin-top: 6px;
          }
          .timeline-line {
            width: 100%;
            height: 0;
            border-top: 1px dotted #cccccc;
            margin-top: -4px;
          }
          .timeline-date {
            font-size: 10px;
            color: #707070;
            margin-top: 8px;
          }
        }
      }
    }
  }
}
</style>
