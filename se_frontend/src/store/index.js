import { createStore } from "vuex";

// 用户相关的store
import userStore from "./modules/user";
// 布局相关的store
import layoutStore from "@/store/modules/layout";
// import courseStore from "@/store/modules/course";
import evalStore from "@/store/modules/eval_store";
import forumStore from "@/store/modules/forum_store";
export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    userStore,
    layoutStore,
    evalStore,
    forumStore,
  },
});
