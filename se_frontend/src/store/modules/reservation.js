import { ElMessage } from "element-plus";
import { fetchLocations } from "@/api/reservation";

export default {
  namespaced: true,
  state: {
    locations: [],
  },
  mutations: {
    setLocations(state, locations) {
      state.locations = locations;
    },
  },
  actions: {
    async loadLocations({ commit }) {
      try {
        const res = await fetchLocations();
        commit("setLocations", res.data.locationList);
      } catch (error) {
        ElMessage.error("查询失败，请稍后重试");
      }
    },
  },
};
