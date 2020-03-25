<template>
  <div class="panel-container">
    <div class="panel-title">我的收藏</div>
    <div class="panel-content">
      <div class="no-data">
        <div class="no-data-img">
          <i class="iconfont">&#xe625;</i>
        </div>
        <div class="no-data-txt">暂无收藏</div>
      </div>
      <div class="panel-table">
        <el-table
          :data="collectTableData"
          :row-class-name="tableRowClassName"
          style="width: 100%"
        >
          <el-table-column
            type="index"
            label="序号"
            width="200px"
            min-width="200px"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="name" label="地块名称" align="center">
          </el-table-column>
          <el-table-column prop="date" label="收藏时间" align="center">
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                >收藏</el-button
              ></template
            >
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "myCollect",
  data() {
    return {
      collectTableData: [
        {
          date: "2016-05-02",
          name: "冬小麦",
          level: "很好"
        },
        {
          date: "2016-05-04",
          name: "冬小麦",
          level: "很好"
        },
        {
          date: "2016-05-01",
          name: "冬小麦",
          level: "很好"
        },
        {
          date: "2016-05-03",
          name: "冬小麦",
          level: "很好"
        }
      ]
    };
  },
  methods: {
    tableRowClassName({ rowIndex }) {
      if (rowIndex % 2 == 0) {
        return "highlight-row";
      }
    },
    handleEdit(index, row) {}
  }
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
    .no-data {
      display: none;
      text-align: center;
      .no-data-img {
        margin-top: 100px;
        i {
          font-size: 100px;
          color: #cccccc;
        }
      }
      .no-data-txt {
        margin-top: 30px;
        font-size: 14px;
        color: #cccccc;
      }
    }
  }
}

/* 覆盖element样式*/

.el-table /deep/ .highlight-row {
  background: #f3f9fd !important;
}
</style>
