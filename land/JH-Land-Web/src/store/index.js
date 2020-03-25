import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isShowLogin: false
  },
  mutations: {
    userStatus(state, user) {
      if (!user.isLogin) {
        state.isShowLogin = true;
      }
    }
  },
  actions: {},
  modules: {}
});
