import Vue from "vue";
import VueRouter from "vue-router";
Vue.use(VueRouter);
// 解决路由跳转报错
const VueRouterPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(to) {
  return VueRouterPush.call(this, to).catch(err => err);
};

const routes = [
  /* 主页 */
  {
    path: "/",
    name: "homeIndex",
    component: () => import("../views/index.vue")
  },
  /* 地块详情 */
  {
    path: "/detail",
    name: "detail",
    component: () => import("../views/detail.vue")
  },
  /* 关于我们 */
  {
    path: "/about",
    name: "about",
    component: () => import("../views/aboutUs.vue")
  },
  /* 免费试用 */
  {
    path: "/free",
    name: "free",
    component: () => import("../views/free.vue")
  },
  /* 个人中心 */
  {
    path: "/userCenter",
    name: "userCenter",
    component: () => import("../views/userCenter.vue"),
    children: [
      /* 个人中心 -- 我的收藏 */
      {
        path: "myCollect",
        name: "myCollect",
        component: () => import("../views/myCollect.vue")
      },
      /* 个人中心 -- 安全设置 */
      {
        path: "mySafe",
        name: "mySafe",
        component: () => import("../views/mySafe.vue")
      }
    ],
    redirect: "/userCenter/myCollect"
  },
  /* 后台 -- 用户管理*/
  {
    path: "/userAdmin",
    name: "userAdmin",
    component: () => import("../views/admin/userAdmin.vue")
  }
];

const router = new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes
});

export default router;
