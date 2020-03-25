<template>
  <div class="legend">
    <div class="legend-title">{{ legendData.title }}</div>
    <div
      class="legend-item"
      v-for="(legend, index) in legendData.data"
      :key="index"
    >
      <div class="legend-color" :style="{ background: legend.color }"></div>
      <div class="legend-name">{{ legend.name }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    legendData: [Object, Array]
  }
};
</script>

<style lang="less" scoped>
.legend {
  position: absolute;
  bottom: 60px;
  right: 10px;
  width: 100px;
  padding-bottom: 11px;
  background-color: #ffffff;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.3);
  overflow: hidden;
  z-index: 100;
  .legend-title {
    margin-top: 15px;
    margin-bottom: 11px;
    font-size: 12px;
    color: #333333;
    text-align: center;
  }
  .legend-item {
    display: flex;
    align-items: center;
    margin-bottom: 8px;
    .legend-color {
      margin: 0 5px 0 26px;
      width: 15px;
      height: 14px;
    }
    .legend-name {
      font-size: 11px;
    }
  }
}
</style>
