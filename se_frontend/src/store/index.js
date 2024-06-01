import { createStore } from "vuex";

// 用户相关的store
import userStore from "./modules/user";
// 布局相关的store
import layoutStore from "@/store/modules/layout";
import courseStore from "@/store/modules/course";
// import courseStore from "@/store/modules/course";
// 预约相关的store
import reservationStore from "@/store/modules/reservation";

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    userStore,
    layoutStore,
    reservationStore,
    courseStore,
  },
});
