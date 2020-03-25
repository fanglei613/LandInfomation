<template>
    <div class="layout">
        <!-- 头部 -->
        <div class="header">
            <!-- 左侧logo以及名称 -->
            <div class="info">
                <div class="logo">
                    <img
                            src="../assets/images/index/logo.png"
                            width="165"
                            height="40"
                            alt=""
                    />
                </div>
                <div class="line"></div>
                <div class="name">地地查</div>
                <j-region id="region" style="margin-left:50px;"></j-region>
            </div>
            <!-- 右侧个人中心及跳转 -->
            <div class="head-right">
                <div class="link">免费试用</div>
                <div class="link">关于我们</div>
                <div class="user">
                    <img
                            src="../assets/images/user/user-avtar.png"
                            width="32"
                            height="32"
                            alt=""
                    />
                    <span>小珈助手</span>
                </div>
            </div>
        </div>
        <!-- 页面主体 -->
        <div class="main-content">
            <!-- 左侧列表 -->
            <j-table @tableData="tableData"></j-table>
            <!-- 地图-->
            <l-map></l-map>
        </div>
    </div>
</template>

<script>
    import jRegion from "@/components/region";
    import jTable from "@/components/table";
    import lMap from "@/components/lmap";

    export default {
        name: "Home",
        components: {
            jTable,
            lMap,
            jRegion
        },
        data() {
            return {
                tableData: [
                    {name: "高新镇", number: 1200},
                    {name: "高新镇", number: 1200},
                    {name: "高新镇", number: 1200},
                    {name: "高新镇", number: 1200},
                    {name: "高新镇", number: 1200},
                    {name: "高新镇", number: 1200},
                    {name: "高新镇", number: 1200}
                ]
            };
        },
        mounted() {
        },
        methods: {}
    };
</script>

<style lang="less">
    .layout {
        width: 100%;
        height: 100%;

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: relative;
            width: 100%;
            height: 56px;
            background: #ffffff;
            box-shadow: 0px 2px 3px rgba(0, 0, 0, 0.3);
            z-index: 10;

            .info {
                margin-left: 16px;
                display: flex;
                align-items: center;

                .line {
                    width: 1px;
                    height: 12px;
                    background: #cccccc;
                    margin: 0 10px;
                }

                .name {
                    font-size: 18px;
                    font-weight: bold;
                    color: #333333;
                }
            }

            .head-right {
                display: flex;
                align-items: center;
                margin-right: 30px;

                .link {
                    font-size: 14px;
                    font-weight: bold;
                    margin-right: 60px;
                }

                .user {
                    display: inline-flex;
                    align-items: center;

                    span {
                        font-size: 12px;
                        margin-left: 10px;
                    }
                }
            }
        }

        .main-content {
            position: relative;
            width: 100%;
            height: calc(100% - 56px);
        }
    }
</style>
