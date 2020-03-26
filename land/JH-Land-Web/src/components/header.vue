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
                        alt
                />
                <span>小珈助手</span>
                <ul class="userMenu" v-show="showUserCenter">
                    <li class="isCms">
                        <a href=" " target="_blank">
                        <i class="iconfont">&#xe640;</i>后台管理
                    </a>
                    </li>
                    <li>
                        <a href class="active" id>
                            <i class="iconfont">&#xe641;</i>我的主页
                        </a>
                    </li>
                    <li>
                        <a href="#" id="quitBtn"> <i class="iconfont">&#xe63f;</i>退出
                    </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
    import jRegion from "@/components/region";
    import {LOGIN_CONFIG} from "../config/url_config";

    export default {
        name: "jHeader",
        components: {
            jRegion
        },
        data() {
            return {
                showUserCenter: false
            };
        },
        mounted() {
            // 初始化
            this.init();
        },
        methods: {
            init() {
                // 判断用户是否登录
                this.isLogin();
            },
            isLogin() {
                this.$axios
                    .get(this.baseApiPath + LOGIN_CONFIG.check_user_login)
                    .then(result => {
                        console.log(result);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
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
                position: relative;
                display: inline-flex;
                align-items: center;

                span {
                    font-size: 12px;
                    margin-left: 10px;
                }

                .userMenu {
                    position: absolute;
                    top: 45px;
                    left: -33px;
                    width: 135px;
                    height: 135px;
                    padding-left: 17px;
                    padding-top: 20px;
                    background: #ffffff;
                    box-shadow: 0px 3px 6px rgba(0, 0, 0, 0.16);
                    z-index: 10000;

                    li {
                        margin-bottom: 20px;

                        a {
                            display: block;
                            font-size: 13px;
                            color: #707070;

                            &:hover {
                                color: #007dff;

                                i {
                                    color: #007dff;
                                }
                            }

                            i {
                                display: inline-block;
                                vertical-align: middle;
                                margin-right: 20px;
                                font-size: 20px;
                                color: #5f5e5e;
                            }
                        }
                    }
                }
            }
        }
    }
</style>