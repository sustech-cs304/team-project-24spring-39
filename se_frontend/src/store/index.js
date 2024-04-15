import { createStore } from "vuex";

// 用户相关的store
import userStore from "./modules/user";
// 布局相关的store
import layoutStore from "@/store/modules/layout";

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    userStore,
    layoutStore,
  },
});
