<template>
    <div class="leftColumn" :style="{ transform: isShowDtl ? 'translateX(-400px)' : 'translateX(0)' }">
        <!-- 乡镇列表 -->
        <div class="table">
            <div
                    class="tr"
                    v-for="(item, index) in tableData"
                    :key="index"
                    :class="{ active: currentIndex == index }"
                    @click="handleTrClick(item, index)"
            >
                <div class="td">{{ item.chinaName }}</div>
                <div class="td">
                    <span>{{ item.rankNum }}宗地块</span>
                    <i></i>
                </div>
            </div>
        </div>
        <!-- 地块列表 -->
        <div class="subPage">
            <div class="back" @click="handlePrevClick"><i></i>返回上一级</div>
            <div class="sub-table">
                <div class="table">
                    <div class="t-head">
                        <div class="th">序号</div>
                        <div class="th">地块名称</div>
                        <div class="th">种植作物</div>
                        <div class="th">面积(亩)</div>
                    </div>
                    <div class="t-body">
                        <div
                                class="tr"
                                v-for="(item, index) in landData"
                                :key="index"
                                :class="{ active: crtIndex == index }"
                                @click="handleSubTrClick(item, index)"
                        >
                            <div class="td">{{ index + 1 }}</div>
                            <div class="td">{{ item.name }}</div>
                            <div class="td">{{ item.crop }}</div>
                            <div class="td">{{ item.area }}</div>
                        </div>
                    </div>
                </div>
                <div class="page">
                    <el-pagination
                            background
                            layout="prev, pager, next"
                            :current-page="landDataParam.page"
                            :page-size="landDataParam.pageSize"
                            :total="landDataParam.total"
                            @current-change="landListPageChange"
                    ></el-pagination>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

    export default {
        name: "jTable",
        props: {
            dateYear: { // 默认年份
                type: Number,
                default() {
                    /*
                    let _date = new Date();
                    return _date.getFullYear();
                     */
                    return 2019;
                }
            },
            cropMap: { // 作物ID对应作物名称列表
                type: Object,
                default() {
                    return {};
                }
            },
        },
        data() {
            return {
                currentIndex: null,
                crtIndex: null,

                // 是否显示地块列表
                isShowDtl: false,

                // 查询乡镇统计列表地址
                findLandStatisticsUrl: this.baseApiPath + "/jh-land/nolog/land/findLandStatistics",
                // 查询地块列表地址
                findLandListUrl: this.baseApiPath + "/jh-land/nolog/land/findLandList",

                currentCityRegionId: null, // 当前地级市的行政区划编码
                currentTownRegionId: null, // 当前区县的行政区划编码

                tableData: [], // 乡镇列表数据
                landData: [], // 地块列表数据

                // 地块列表分页请求参数
                landDataParam: {
                    regionId: null,
                    page: 1,
                    pageSize: 5,
                    total: 0
                },
            };
        },
        created() {
            // 绑定触发器
            this.bindTriggers();
        },
        methods: {
            /**
             * 绑定触发器
             */
            bindTriggers() {
                let that = this;
                // 选择行政区划后加载乡镇列表
                JHGEvent.on("Table/SelectRegion", regionInfo => {
                    if (!regionInfo) {
                        return;
                    }
                    that.initSelectRegion(regionInfo);
                });
            },

            /**
             * 根据行政区划获取列表
             */
            initSelectRegion(regionInfo) {
                let that = this;
                that.isShowDtl = false;
                // 行政区划级别小于4级不显示左侧面板
                if (regionInfo.level < 4) {
                    that.tableData = [];
                    return;
                }
                // 显示左侧面板
                that.isShow = true;
                // 加载乡镇列表
                if (regionInfo.level == 4) {
                    that.loadTownList(regionInfo);
                    that.currentCityRegionId = regionInfo.parentId;
                    that.currentTownRegionId = regionInfo.regionId;
                    return;
                }
                // 加载地块列表
                if (regionInfo.level == 5) {
                    that.isShowDtl = true;
                    that.landDataParam.regionId = regionInfo.regionId;
                    that.landDataParam.total = 0;
                    that.loadLandList(that.landDataParam);
                }
            },

            /**
             * 加载乡镇列表
             */
            loadTownList(regionInfo) {
                let that = this;
                // 加载乡镇列表
                that.$axios.post(that.findLandStatisticsUrl, {
                    regionId: regionInfo.regionId,
                    startDate: that.dateYear + "-01-01",
                    endDate: that.dateYear + "-12-31"
                }).then(response => {
                    let result = response.data;
                    that.tableData = result.data;
                });
            },

            /**
             * 加载地块列表
             */
            loadLandList() {
                let that = this;
                let landTableName = "init_rank_" + that.dateYear + "_" + that.currentCityRegionId;
                that.$axios.post(that.findLandListUrl, {
                    regionId: that.landDataParam.regionId,
                    landTableName: landTableName,
                    page: that.landDataParam.page,
                    rows: that.landDataParam.pageSize,
                    // 用于查询总数的参数，查询一次获取总数后则不再查询
                    total: that.landDataParam.total,
                    startDate: that.dateYear + "-01-01",
                    endDate: that.dateYear + "-12-31",
                }).then(response => {
                    let result = response.data;
                    that.landDataParam.total = result.total;
                    let list = result.list;
                    list.forEach(item => {
                        item.name = item.rankName;
                        let cropName = that.cropMap[item.cropId];
                        if (!cropName) {
                            cropName = "一";
                        }
                        item.crop = cropName;
                    });
                    that.landData = list;
                });
            },

            /**
             *  分页请求地块列表
             */
            landListPageChange(page) {
                let that = this;
                that.landDataParam.page = page;
                that.loadLandList();
            },

            /**
             *  乡镇列表点击
             */
            handleTrClick(item, index) {
                this.currentIndex = index;
                JHGEvent.trigger("Region/SelectRegion", item.regionId);
            },

            /**
             * 地块列表点击
             */
            handleSubTrClick(item, idx) {
                this.crtIndex = idx;
                // 在地图中弹出POPUP
                JHGEvent.trigger("Map/ClickLand", item.rankId);
            },

            /**
             * 返回上一页
             */
            handlePrevClick() {
                let that = this;
                JHGEvent.trigger("Region/SelectRegion", that.currentTownRegionId);
            }
        }
    };
</script>

<style lang="less" scoped>
    .leftColumn {
        position: absolute;
        left: 0;
        top: 0;
        width: 800px;
        height: 100%;
        z-index: 99;
        background: #f5f5f5;
        transition: all 0.2s ease;

        > .table {
            float: left;
            width: 400px;
            height: 100%;
            padding: 13px;

            .tr {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 12px;
                padding: 0 16px;
                height: 50px;
                background: #ffffff;
                cursor: pointer;

                &:hover,
                &.active {
                    background: #eeeeee;
                }

                .td {
                    i {
                        display: inline-block;
                        width: 6px;
                        height: 9px;
                        margin-left: 12px;
                        background: url("../assets/images/index/arrow-right.png") no-repeat;
                        background-size: 6px 9px;
                    }
                }
            }
        }

        .subPage {
            float: left;
            width: 400px;
            height: 100%;

            .back {
                display: flex;
                align-items: center;
                padding-left: 20px;
                height: 42px;
                border-bottom: 1px solid #dadada;
                cursor: pointer;

                i {
                    display: inline-block;
                    width: 6px;
                    height: 9px;
                    margin-right: 6px;
                    background: url("../assets/images/index/back.png") no-repeat;
                    background-size: 6px 9px;
                }
            }

            .sub-table {
                height: calc(100% - 42px);

                .table {
                    .t-head {
                        display: flex;
                        border-bottom: 1px solid #dadada;
                        background-color: #ffffff;

                        .th {
                            display: inline-flex;
                            justify-content: center;
                            align-items: center;
                            height: 40px;
                            flex: 0 0 90px;
                            font-size: 12px;
                            font-weight: bold;

                            &:nth-child(2) {
                                flex: 0 0 130px;
                            }
                        }
                    }

                    .t-body {
                        .tr {
                            display: flex;
                            border-bottom: 1px solid #dadada;
                            background-color: #ffffff;

                            &:hover,
                            &.active {
                                background-color: #eeeeee;
                            }

                            .td {
                                display: inline-flex;
                                justify-content: center;
                                align-items: center;
                                height: 85px;
                                line-height: 16px;
                                flex: 0 0 90px;
                                font-size: 12px;

                                &:nth-child(2) {
                                    flex: 0 0 130px;
                                }
                            }
                        }
                    }
                }

                .page {
                    padding: 20px;
                }
            }
        }
    }
</style>
