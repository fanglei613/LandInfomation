<template>
  <div class="content">
    <div class="center-container">
      <!-- 地块简介 -->
      <div class="column">
        <div class="block-info clearfix">
          <img
            src="../assets/images/block.png"
            width="180"
            height="180"
            alt="地块图片"
          />
          <div class="block-dtl">
            <h4>湖北省襄阳市兴国县林山镇王家村001号</h4>
            <ul>
              <li>用地分类：{{ dataInfo.type }}</li>
              <li>地块面积：{{ dataInfo.area }}m亩</li>
              <li>地址：{{ dataInfo.address }}</li>
              <li>
                经纬度： 北纬{{ dataInfo.coordinate[0] }} 东经{{
                  dataInfo.coordinate[1]
                }}
              </li>
            </ul>
          </div>
          <div
            class="collect"
            :class="{ active: isCollect }"
            @click="handleCollectClick"
          >
            <i class="iconfont">&#xe626;</i>收藏
          </div>
        </div>
      </div>
      <!-- 地块概览 -->
      <div class="column">
        <div class="block-g">
          <div class="g-head">
            <div class="head-l">
              <span>地块概览</span>
              <p class="datePicker">
                <el-date-picker
                  v-model="yearValue"
                  type="year"
                  size="mini"
                  width="80"
                  placeholder="选择年"
                >
                </el-date-picker>
              </p>
            </div>
            <div class="download-btn">数据下载</div>
          </div>
          <div class="g-content">
            <el-row :gutter="20">
              <el-col :span="6"
                ><div class="grid-content bg-white">
                  <div class="grid-label">
                    <i class="iconfont">&#xe635;</i>
                    <span>土壤类型</span>
                  </div>
                  <div class="grid-name">
                    {{ blockInfo.type }}
                  </div>
                </div></el-col
              >
              <el-col :span="6"
                ><div class="grid-content bg-white">
                  <div class="grid-label">
                    <i class="iconfont">&#xe62b;</i>
                    <span>种植作物</span>
                  </div>
                  <div class="grid-name">
                    {{ blockInfo.crop }}
                  </div>
                </div></el-col
              >
              <el-col :span="6"
                ><div class="grid-content bg-white">
                  <div class="grid-label">
                    <i class="iconfont">&#xe633;</i>
                    <span>种植面积</span>
                  </div>
                  <div class="grid-name">
                    {{ blockInfo.area }}{{ landUnit }}
                  </div>
                </div></el-col
              >
              <el-col :span="6"
                ><div class="grid-content bg-white">
                  <div class="grid-label">
                    <i class="iconfont">&#xe634;</i>
                    <span>预估单量</span>
                  </div>
                  <div class="grid-name">
                    {{ blockInfo.singleAccount }}{{ landUnit }}
                  </div>
                </div></el-col
              >
              <el-col :span="6"
                ><div class="grid-content bg-white">
                  <div class="grid-label">
                    <i class="iconfont">&#xe62f;</i>
                    <span>预计产量</span>
                  </div>
                  <div class="grid-name">
                    {{ blockInfo.proAccount }}{{ wUnit }}
                  </div>
                </div></el-col
              >
              <el-col :span="6"
                ><div class="grid-content bg-white">
                  <div class="grid-label">
                    <i class="iconfont">&#xe63c;</i>
                    <span>日均温度</span>
                  </div>
                  <div class="grid-name">
                    {{ blockInfo.averageTmp }} {{ tmpUnit }}
                  </div>
                </div></el-col
              >
              <el-col :span="6"
                ><div class="grid-content bg-white">
                  <div class="grid-label">
                    <i class="iconfont">&#xe62e;</i>
                    <span>累计降水</span>
                  </div>
                  <div class="grid-name">
                    {{ blockInfo.totalRainFall }}{{ rainUnit }}
                  </div>
                </div></el-col
              >
              <el-col :span="6"
                ><div class="grid-content bg-white">
                  <div class="grid-label">
                    <i class="iconfont">&#xe638;</i>
                    <span>日均降水</span>
                  </div>
                  <div class="grid-name">
                    {{ blockInfo.dailyRainFall }} {{ rainUnit }}
                  </div>
                </div></el-col
              >
              <el-col :span="6"
                ><div class="grid-content bg-white">
                  <div class="grid-label">
                    <i class="iconfont">&#xe63a;</i>
                    <span>最高气温</span>
                  </div>
                  <div class="grid-name">
                    {{ blockInfo.hignTmp }}{{ tmpUnit }}
                  </div>
                </div></el-col
              >
              <el-col :span="6"
                ><div class="grid-content bg-white">
                  <div class="grid-label">
                    <i class="iconfont">&#xe632;</i>
                    <span>最低气温</span>
                  </div>
                  <div class="grid-name">
                    {{ blockInfo.lowTmp }}{{ tmpUnit }}
                  </div>
                </div></el-col
              >
              <el-col :span="6"
                ><div class="grid-content bg-white">
                  <div class="grid-label">
                    <i class="iconfont">&#xe630;</i>
                    <span>日均气温</span>
                  </div>
                  <div class="grid-name">
                    {{ blockInfo.averageAirTmp }}{{ tmpUnit }}
                  </div>
                </div></el-col
              >
            </el-row>
          </div>
        </div>
      </div>
      <!-- 历年长势&气象 -->
      <div class="column">
        <div class="block-t">
          <div class="t-head">
            <div class="head-l">
              <span
                :class="{ active: queryType == 'growth' }"
                @click="handleGrowthClick"
                >历年长势</span
              >
              <span
                :class="{ active: queryType == 'weather' }"
                @click="handleWeatherClick"
                >历年气象</span
              >
              <div class="picker">
                <el-date-picker
                  v-model="monthValue"
                  type="month"
                  size="mini"
                  placeholder="选择月"
                >
                </el-date-picker>
              </div>
            </div>
            <div class="download-btn">数据下载</div>
          </div>
          <div class="t-con" v-if="queryType == 'growth'">
            <el-table
              :data="growthTableData"
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
              <el-table-column prop="date" label="监测时间" align="center">
              </el-table-column>
              <el-table-column prop="crop" label="种植作物" align="center">
              </el-table-column>
              <el-table-column
                prop="level"
                label="长势等级"
                align="center"
              ></el-table-column>
            </el-table>
            <div class="page">
              <el-pagination layout="prev, pager, next" :total="1000">
              </el-pagination>
            </div>
          </div>
          <div class="t-con" v-else-if="queryType == 'weather'">
            <el-table
              :data="weatherTableData"
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
              <el-table-column prop="date" label="监测时间" align="center">
              </el-table-column>
              <el-table-column
                prop="highTmp"
                label="最高气温（℃）"
                align="center"
              >
              </el-table-column>
              <el-table-column
                prop="lowTmp"
                label="最低气温（℃）"
                align="center"
              >
              </el-table-column>
              <el-table-column
                prop="rainFall"
                label="降雨量（mm）"
                align="center"
              >
              </el-table-column>
              <el-table-column prop="landTmp" label="地温℃" align="center">
              </el-table-column>
            </el-table>
            <div class="page">
              <el-pagination layout="prev, pager, next" :total="1000">
              </el-pagination>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "detail",
  data() {
    return {
      isCollect: false,
      landUnit: "kg/亩",
      wUnit: "kg",
      tmpUnit: "℃",
      rainUnit: "mm",
      queryType: "growth",
      yearValue: "",
      monthValue: "",
      dataInfo: {
        type: "耕地",
        area: 123,
        address: "湖北省襄阳市兴国县林山镇王家村",
        coordinate: [110, 32]
      },
      blockInfo: {
        type: "黄褐土",
        crop: "冬小麦",
        area: 100,
        singleAccount: 100,
        proAccount: 1000,
        averageTmp: 8.5,
        totalRainFall: 244.233,
        dailyRainFall: 1.233,
        hignTmp: 42,
        lowTmp: 4,
        averageAirTmp: 25
      },
      growthTableData: [
        {
          date: "2016-05-02",
          crop: "冬小麦",
          level: "很好"
        },
        {
          date: "2016-05-04",
          crop: "冬小麦",
          level: "很好"
        },
        {
          date: "2016-05-01",
          crop: "冬小麦",
          level: "很好"
        },
        {
          date: "2016-05-03",
          crop: "冬小麦",
          level: "很好"
        }
      ],
      weatherTableData: [
        {
          date: "2016-05-03",
          highTmp: 18,
          lowTmp: 5,
          rainFall: 50,
          landTmp: 18.1
        },
        {
          date: "2016-05-03",
          highTmp: 18,
          lowTmp: 5,
          rainFall: 50,
          landTmp: 18.1
        },
        {
          date: "2016-05-03",
          highTmp: 18,
          lowTmp: 5,
          rainFall: 50,
          landTmp: 18.1
        },
        {
          date: "2016-05-03",
          highTmp: 18,
          lowTmp: 5,
          rainFall: 50,
          landTmp: 18.1
        },
        {
          date: "2016-05-03",
          highTmp: 18,
          lowTmp: 5,
          rainFall: 50,
          landTmp: 18.1
        },
        {
          date: "2016-05-03",
          highTmp: 18,
          lowTmp: 5,
          rainFall: 50,
          landTmp: 18.1
        }
      ]
    };
  },
  mounted() {},
  methods: {
    handleCollectClick() {},
    handleGrowthClick() {
      this.queryType = "growth";
    },
    handleWeatherClick() {
      this.queryType = "weather";
    },
    tableRowClassName({ rowIndex }) {
      if (rowIndex % 2 == 0) {
        return "highlight-row";
      }
    }
  }
};
</script>

<style lang="less" scoped>
.content {
  width: 100%;
  height: 100%;
  overflow: auto;
  .center-container {
    padding: 20px 30px;
    // margin: 0 auto;
    .column {
      margin-bottom: 20px;
      background: #ffffff;
      /* 地块详情 */
      .block-info {
        padding: 30px 32px 30px 28px;
        img {
          float: left;
          width: 180px;
          height: 180px;
        }
        .block-dtl {
          float: left;
          margin-left: 45px;
          overflow: hidden;
          h4 {
            margin-top: 35px;
            font-size: 18px;
          }
          ul {
            margin-top: 40px;
            width: 660px;
            overflow: hidden;
            li {
              float: left;
              width: 330px;
              margin-bottom: 20px;
            }
          }
        }
        .collect {
          float: right;
          width: 116px;
          height: 40px;
          line-height: 40px;
          font-size: 14px;
          color: #999999;
          text-align: center;
          border: 1px solid #999999;
          border-radius: 20px;
          cursor: pointer;
          &.active {
            color: #007dff;
            border: 1px solid #007dff;
          }
          i {
            display: inline-block;
            vertical-align: text-bottom;
            font-size: 20px;
            line-height: 1;
            margin-right: 10px;
          }
        }
      }
      /* 地块概览 */
      .block-g {
        .g-head {
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 0 28px;
          height: 70px;
          border-bottom: 1px solid #dadada;
          .head-l {
            display: flex;
            align-items: center;
            span {
              margin-right: 30px;
              font-size: 20px;
              line-height: 1;
            }
          }
          .download-btn {
            width: 84px;
            height: 32px;
            line-height: 32px;
            border: 1px solid #007dff;
            font-size: 14px;
            color: #007dff;
            text-align: center;
            cursor: pointer;
          }
        }
        .g-content {
          padding: 20px 30px 10px 30px;
          .grid-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding: 0 20px;
            height: 70px;
            border: 1px solid #dadada;
            transition: all 0.3s ease;
            &:hover {
              transform: translateY(-3px);
              box-shadow: 3px 3px 3px rgba(0, 0, 0, 0.1);
            }
            .grid-label {
              display: flex;
              align-items: center;
              i {
                font-size: 36px;
                color: #007dff;
              }
              span {
                font-size: 14px;
                color: #707070;
              }
            }
          }
        }
      }
      /* 历年长势&气象 */
      .block-t {
        .t-head {
          display: flex;
          align-items: center;
          justify-content: space-between;
          padding: 0 30px;
          height: 90px;
          border-bottom: 1px solid #dadada;
          .head-l {
            display: flex;
            span {
              display: inline-block;
              padding-bottom: 16px;
              margin-top: 25px;
              margin-right: 40px;
              border-bottom: 4px solid transparent;
              font-size: 20px;
              color: #cccccc;
              cursor: pointer;
              &.active {
                color: #333333;
                border-bottom: 4px solid #007dff;
              }
            }
            .picker {
              margin-top: 20px;
            }
          }
          .download-btn {
            width: 84px;
            height: 32px;
            line-height: 32px;
            border: 1px solid #007dff;
            font-size: 14px;
            color: #007dff;
            text-align: center;
            cursor: pointer;
          }
        }
        .t-con {
          width: 100%;
          overflow: hidden;
          .page {
            display: flex;
            justify-content: center;
            margin: 25px 0 20px;
          }
        }
      }
    }
  }
}

/* 覆盖element样式*/

.el-table /deep/ .highlight-row {
  background: #f3f9fd !important;
}
.datePicker /deep/ .el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 120px !important;
}
.picker /deep/ .el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 140px !important;
}
</style>
