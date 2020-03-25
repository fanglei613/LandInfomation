<template>
    <div class="content">
        <!-- 左侧列表 -->
        <j-table :cropMap="cropMap"></j-table>
        <!-- 作物下拉框选择-->
        <div class="crop-sel">
            <div class="current-crop">
                <label>{{ currentCrop }}</label>
                <span class="arrow"></span>
            </div>
            <div class="select">
                <div
                        class="option"
                        v-for="(value, key,index) in cropMap"
                        :key="index"
                        @click="handleOptionClick(value)"
                >
                    {{ value }}
                </div>
            </div>
        </div>
        <!-- 地图-->
        <l-map :cropMap="cropMap"></l-map>
        <!-- 图例 -->
        <j-legend :legendData="legendObj"></j-legend>
        <!-- 时间轴 -->
        <j-timeline :timeData="timeData"></j-timeline>
    </div>
</template>

<script>
    import jTable from "@/components/table";
    import lMap from "@/components/lmap";
    import jLegend from "@/components/legend";
    import jTimeline from "@/components/timeline";

    export default {
        name: "homeIndex",
        components: {
            jTable,
            lMap,
            jLegend,
            jTimeline
        },
        data() {
            return {
                currentCrop: "大豆",
                legendObj: {
                    title: "作物类型",
                    data: [
                        {color: "#FF3636", name: "玉米"},
                        {color: "#FFF700", name: "小麦"},
                        {color: "#00FFF6", name: "中稻"},
                        {color: "#00FF4C", name: "油菜"}
                    ]
                },
                cropMap: {
                    501: '大豆',
                    502: '玉米',
                    503: '春玉米',
                    504: '夏玉米',
                    505: '春小麦',
                    506: '冬小麦',
                    507: '水稻',
                    508: '早稻',
                    509: '中稻',
                    510: '晚稻',
                    511: '油菜',
                    512: '棉花',
                    513: '棕榈',
                    514: '小麦'
                },
                timeData: [2019]
            };
        },
        mounted() {
        },
        methods: {}
    };
</script>

<style lang="less" scoped>
    .content {
        width: 100%;
        height: 100%;
        overflow: hidden;
        position: relative;
        /* 作物下拉框 */

        .crop-sel {
            position: absolute;
            top: 20px;
            left: 420px;
            width: 80px;
            overflow: hidden;
            background: #ffffff;
            box-shadow: 0px 3px 6px rgba(0, 0, 0, 0.16);
            z-index: 101;

            &:hover {
                .select {
                    display: block;
                    transition: all 0.2s ease;
                }

                .current-crop {
                    color: #007dff;

                    span {
                        border-color: #007dff transparent transparent;
                    }
                }
            }

            .current-crop {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 34px;
                font-size: 12px;
                color: #707070;

                &.active {
                    color: #007dff;

                    span {
                        border-color: #007dff transparent transparent;
                    }
                }

                span {
                    display: inline-block;
                    width: 0;
                    height: 0;
                    margin-left: 7px;
                    border-width: 8px 6px 0;
                    border-style: solid;
                    border-color: #707070 transparent transparent;
                }

                .crop-select {
                    label {
                        color: #007dff;
                    }

                    span {
                        border-color: #007dff transparent transparent;
                    }
                }
            }

            .select {
                display: none;
                max-height: 360px;
                overflow-y: auto;

                .option {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 36px;
                    font-size: 12px;
                    color: #707070;

                    &:hover {
                        color: #007dff;
                    }
                }
            }
        }
    }
</style>
