<template>
    <div class="header">
        <!-- 左侧logo以及名称 -->
        <router-link to="/" class="info" tag="div">
            <div class="logo">
                <img
                        src="../assets/images/index/logo.png"
                        width="90"
                        height="40"
                        alt="logo"
                />
            </div>
            <div class="line"></div>
            <div class="name">地地查</div>
            <j-region id="region" style="margin-left:50px;" :maxLevel="4"></j-region>
        </router-link>
        <!-- 右侧个人中心及跳转 -->
        <div class="head-right">
            <router-link to="/free" tag="div" class="link">免费试用</router-link>
            <router-link to="/about" tag="div" class="link">关于我们</router-link>
            <!-- <div class="link">关于我们</div> -->
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
</template>

<script>
    import jRegion from "@/components/region";

    export default {
        name: "jHeader",
        components: {
            jRegion
        },
        data() {
            return {};
        }
    };
</script>

<style lang="less" scoped>
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        position: relative;
        width: 100%;
        height: 56px;
        background: #ffffff;
        box-shadow: 0px 2px 3px rgba(0, 0, 0, 0.3);
        z-index: 1000;

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

                &:hover {
                    color: #007dff;
                }
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
</style>
