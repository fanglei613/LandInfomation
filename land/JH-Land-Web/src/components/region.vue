<template>
    <div class="region-panel region-class">
        <div class="region-text-panel region-class" @click="showSelectRegion" :title="regionInfo.chinaName">
            <input type="text" readonly="true" class="region-class"
                   :value="regionInfo.chinaName"
                   :regionId="regionInfo.regionId"
                   :chinaName="regionInfo.chinaName"
                   :parentId="regionInfo.parentId"
                   :centroid="regionInfo.centroid"
                   :level="regionInfo.level"
            ></input><i class="region-class"></i>
        </div>
        <div id="regionArea" class="region-panel-area region-class" v-show="isRegionAreaShow">
            <div class="region-panel-area-header region-class">
                <span class="region-panel-clear-label region-class" @click="canelRegion">取消</span>
                <div id="regionHeadValue" class="region-panel-value region-class">
                    <a v-for="(showRegion,idx) in showRegionFamily"
                       :regionId="showRegion.regionId"
                       :regionCode="showRegion.regionCode"
                       :centroid="showRegion.centroid"
                       :level="showRegion.level"
                       :parentId="showRegion.parentId"
                       @click="checkRegionFamily(idx,showRegion)"
                       href="javascript:void(0);"
                       class="region-class">
                        {{showRegion.chinaName}}
                    </a>
                </div>
            </div>
            <ul class="region-panel-area-content region-class">
                <li v-for="(showRegion,idx) in showRegionList"
                    :regionId="showRegion.regionId"
                    :regionCode="showRegion.regionCode"
                    :centroid="showRegion.centroid"
                    :level="showRegion.level"
                    :parentId="showRegion.parentId"
                    @click="loadRegion(showRegion)"
                    class="region-class"
                >
                    {{showRegion.chinaName}}
                </li>

            </ul>
        </div>
    </div>
</template>

<script>
    export default {
        name: "jRegion",
        props: {
            maxLevel: { // 最大选中级别
                type: Number,
                default() {
                    return 5;
                }
            }
        },
        data() {
            return {
                // 行政区划信息地址
                queryRegionByIdUrl: this.baseApiPath + "/jh-land/nolog/region/queryRegionById",
                // 行政区划列表地址
                queryRegionListUrl: this.baseApiPath + "/jh-land/nolog/region/blockRegionList",

                // 默认行政区划信息
                defaultRegionInfo: {
                    chinaName: "中国",
                    regionId: 3100000000,
                    parendId: null,
                    level: 1,
                    centroid: "POINT(103.846520481217 36.4431310505716)",
                    regionCode: "CHN",
                },

                // 当前行政区划信息
                regionInfo: {},

                // 当前显示的行政区划
                currentRegionInfo: {},

                // 是否显示当前行政区划选择面板
                isRegionAreaShow: false,

                // 行政区划级联关系
                showRegionFamily: [],

                // 行政区划列表
                showRegionList: [],
            };
        },
        created() {
            // 绑定全局触发器
            this.bindTriggers();
        },
        mounted() {
            let that = this;
            that.initRegion();
            // 绑定全局点击事件
            document.onclick = function (evt) {
                let target = evt.target;
                try {
                    let isRegionClass = target.className.indexOf('region-class') !== -1;
                    if (!isRegionClass && that.isRegionAreaShow) { // 如果点击行政区划面板外并且面板是展开状态则选择行政区划
                        that.onSelectRegion();
                    }
                } catch (e) {
                    if (that.isRegionAreaShow) {
                        that.onSelectRegion();
                    }
                }
            };
        },
        methods: {
            /**
             * 初始化当前行政区划
             */
            initRegion() {
                let that = this;
                that.regionInfo = that.defaultRegionInfo;
                that.currentRegionInfo = that.regionInfo;
            },

            /**
             * 绑定全局触发器
             */
            bindTriggers() {
                let that = this;
                // 选择行政区划
                JHGEvent.on("Region/SelectRegion", regionId => {
                    if (!regionId) {
                        return;
                    }
                    that.selectRegion(regionId);
                });
            },

            /**
             *  选择行政区划
             */
            selectRegion(regionId) {
                let that = this;
                let url = that.queryRegionByIdUrl + "?regionId=" + regionId;
                that.$axios.post(url).then(response => {
                    let result = response.data;
                    let data = result.data;
                    that.regionInfo = data;
                    // 调用地图的事件
                    JHGEvent.trigger("Map/SelectRegion", that.regionInfo);
                });
            },

            /**
             * 选择行政区划
             */
            onSelectRegion() {
                let that = this;
                that.regionInfo = that.currentRegionInfo;
                // 触发行政区划选择事件
                JHGEvent.trigger("Map/SelectRegion", that.regionInfo);
                // 隐藏行政区划窗口
                that.isRegionAreaShow = false;
            },

            /**
             * 选择级联的行政区划
             */
            checkRegionFamily(idx, showRegion) {
                let that = this;
                let nShowRegionFamily = [];
                for (let i = 0; i < idx; i++) {
                    nShowRegionFamily.push(that.showRegionFamily[i]);
                }
                that.showRegionFamily = nShowRegionFamily;
                that.loadRegion(showRegion);
            },

            /**
             * 显示行政区划选择框
             */
            showSelectRegion() {
                let that = this;
                that.showRegionFamily = [];
                that.showRegionList = [];
                that.loadRegion();
            },

            /**
             * 加载行政区划
             */
            loadRegion(showRegion) {
                let that = this;
                if (!showRegion) {
                    showRegion = that.defaultRegionInfo;
                }
                that.currentRegionInfo = showRegion;
                // 添加到级联行政区划中
                that.showRegionFamily.push(showRegion);
                // 如果为最大选中级别则直接选择
                if (showRegion.level == that.maxLevel) {
                    that.onSelectRegion();
                    return;
                }
                // 显示行政区划面板
                that.isRegionAreaShow = true;
                // 加载行政区划列表
                let url = that.queryRegionListUrl;
                that.$axios.post(url, {
                    level: showRegion.level,
                    parentId: showRegion.regionId
                }).then(response => {
                    let result = response.data;
                    let data = result.data;
                    that.showRegionList = data;
                });
            },

            /**
             * 取消选择
             */
            canelRegion() {
                let that = this;
                that.isRegionAreaShow = false;
            }
        }
    }
</script>

<style lang="less" scoped>
    .region-panel {
        height: 32px;
        background: rgba(255, 255, 255, 1);
        border: 1px solid rgba(204, 204, 204, 1);
        position: relative;

        .region-text-panel {
            width: 100%;
            height: 100%;
            cursor: pointer;
            position: relative;

            input {
                padding-left: 30px;
                padding-right: 50px;
                text-align: center;
                cursor: pointer;
                color: #000;
                border: none;
                height: 100%;
                width: 120px;
            }

            i {
                position: absolute;
                top: 14px;
                float: left;
                right: 10px;
                width: 6px;
                height: 4px;
                background: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAYAAAAECAYAAACtBE5DAAAABHNCSVQICAgIfAhkiAAAAFNJREFUCJlVyKEBgCAUBND7OBWDkO0kZiH+pEtY3IABGIFiv/jPZPDFZ621A8COvzORrJLmN5ImyZrcnRFRJFESI6K4OzcAGGM8OedlZlfv/QaAF+Z8LUdbyTQZAAAAAElFTkSuQmCC");
            }
        }

        .region-panel-area {
            position: absolute;
            width: 400px;
            height: 260px;
            z-index: 9999;
            top: 33px;
            border: solid 1px #CCC;
            background-color: #FFF;
            text-align: center;
            box-shadow: 0px 3px 4px rgba(0, 0, 0, 0.16);

            .region-panel-area-header {
                overflow: hidden;
                padding: 10px;
                box-sizing: border-box;
                width: 100%;
                height: 45px;
                font-size: 14px;

                .region-panel-clear-label {
                    width: 60px;
                    border: 1px solid #007DFF;
                    padding: 5px;
                    float: left;
                    border-radius: 5px;
                    cursor: pointer;
                }

                .region-panel-value {
                    width: 305px;
                    margin-left: 5px;
                    height: 100%;
                    border-bottom: 1px dashed #007DFF;
                    padding: 5px;
                    float: left;

                    a {
                        cursor: pointer;
                        text-decoration: none;
                        color: #007DFF;
                    }

                    a:not(:first-child):before {
                        content: "  |  ";
                    }
                }
            }

            .region-panel-area-content {
                overflow-y: scroll;
                height: 212px;
                overflow-y: scroll;
                height: 212px;
                padding: 10px;
                box-sizing: border-box;
                width: 100%;

                li {
                    cursor: pointer;
                    width: 105px;
                    float: left;
                    padding: 10px 10px;
                }
            }
        }
    }
</style>