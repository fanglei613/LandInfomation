<template>
  <div class="admin-page">
    <div class="content">
      <div class="search">
        <div class="search-column">
          <label for="userPhone">手机号：</label>
          <input
            type="text"
            id="userPhone"
            class="search-input"
            v-model="userPhone"
          />
        </div>
        <div class="search-column">
          <label for="userCompany">公司名称：</label>
          <input
            type="text"
            id="userCompany"
            class="search-input"
            v-model="userCompany"
          />
        </div>
        <input type="button" value="搜索" id="search-btn" />
      </div>
      <div class="admin-table">
        <el-table
          :data="tableData"
          :row-class-name="tableRowClassName"
          header-row-class-name="table-head"
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

          <el-table-column prop="account" label="账号" align="center">
          </el-table-column>
          <el-table-column prop="telphone" label="手机号" align="center">
          </el-table-column>
          <el-table-column prop="name" label="姓名" align="center">
          </el-table-column>
          <el-table-column prop="companyName" label="公司名" align="center">
          </el-table-column>
          <el-table-column prop="isApply" label="是否申请试用" align="center">
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleCheck(scope.$index, scope.row)"
                >审核</el-button
              >
              <el-button
                size="mini"
                @click="handleDecline(scope.$index, scope.row)"
                >拒绝</el-button
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
  name: "userAdmin",
  data() {
    return {
      userPhone: "",
      userCompany: "",
      tableData: [
        {
          account: "jack239596840",
          telphone: "13154879647",
          name: "五谷丰登",
          companyName: "珈和科技有限公司",
          isApply: "是"
        },
        {
          account: "jack239596840",
          telphone: "13154879647",
          name: "五谷丰登",
          companyName: "珈和科技有限公司",
          isApply: "是"
        },
        {
          account: "jack239596840",
          telphone: "13154879647",
          name: "五谷丰登",
          companyName: "珈和科技有限公司",
          isApply: "是"
        },
        {
          account: "jack239596840",
          telphone: "13154879647",
          name: "五谷丰登",
          companyName: "珈和科技有限公司",
          isApply: "是"
        },
        {
          account: "jack239596840",
          telphone: "13154879647",
          name: "五谷丰登",
          companyName: "珈和科技有限公司",
          isApply: "是"
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
    handleCheck() {},
    handleDecline() {}
  }
};
</script>

<style lang="less" scoped>
.admin-page {
  padding: 20px 30px;
  .content {
    background-color: #ffffff;
    overflow: hidden;
    .search {
      padding: 14px 0;
      .search-column {
        float: left;
        margin-left: 14px;
        label {
          display: inline-block;
          vertical-align: middle;
          margin-right: 10px;
        }
        input {
          display: inline-block;
          vertical-align: middle;
          width: 151px;
          height: 40px;
          line-height: 40px;
          padding-left: 10px;
          background-color: #eeeeee;
          border: 1px solid #dadada;
        }
      }
      #search-btn {
        display: inline-block;
        vertical-align: middle;
        margin-left: 30px;
        width: 105px;
        height: 40px;
        line-height: 40px;
        color: #ffffff;
        text-align: center;
        background: #007dff;
        border: none;
      }
    }
  }
}

/* 覆盖element样式*/

.el-table /deep/ .highlight-row {
  background: #f3f9fd !important;
}
.el-table /deep/ .table-head th {
  color: #ffffff !important;
  background: #007dff !important;
}
</style>
