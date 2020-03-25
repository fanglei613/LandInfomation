import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import {
  Button,
  Select,
  Option,
  Icon,
  ButtonGroup,
  Table,
  TableColumn,
  Pagination,
  Row,
  Col,
  DatePicker,
  TimeSelect,
  TimePicker
} from "element-ui";
// 引入公用css
import "./assets/css/reset.css";
import "./assets/css/global.css";

//设置打包跨域设置
Vue.prototype.baseApiPath =
    process.env.NODE_ENV === "production" ? "http://113.57.163.74:8088/" : "/api";

// 按需引入element-ui
Vue.use(Button);
Vue.use(Select);
Vue.use(Option);
Vue.use(Icon);
Vue.use(ButtonGroup);
Vue.use(Table);
Vue.use(TableColumn);
Vue.use(Pagination);
Vue.use(Row);
Vue.use(Col);
Vue.use(DatePicker);
Vue.use(TimeSelect);
Vue.use(TimePicker);
Vue.config.productionTip = false;
// 引入leaflet
import L from "leaflet";
import omnivore from "leaflet-omnivore";
import utils from "./utils";
Vue.prototype.$utils = utils;
Vue.prototype.$L = L;
Vue.prototype.$omnivore = omnivore;
// 引入iconfont
import "./assets/iconfont/iconfont.css";
// 引入Axios
import axios from "axios";
Vue.prototype.$axios = axios;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
