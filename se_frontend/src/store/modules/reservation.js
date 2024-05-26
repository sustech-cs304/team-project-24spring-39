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
        // 预处理数据，将 rooms 字段重命名为 children
        const processedLocations = res.data.map((location) => ({
          ...location,
          children: location.rooms,
          rooms: undefined, // 删除原来的 rooms 字段
        }));
        commit("setLocations", processedLocations);
      } catch (error) {
        ElMessage.error("查询失败，请稍后重试");
      }
    },
  },
};
