export default {
  state: {
    tableDataList: [],
  },
  mutations: {
    setTableDataList(state, data) {
      state.tableDataList = data;
      console.log("mutation setTableDataList called with", state.tableDataList);
    },
  },
  actions: {
    // 注意这里是 `actions` 而不是 `action`
    async getTableALLDataList({ commit }) {
      try {
        const res = await fetch("http://localhost:8080/api/course");
        const data = await res.json();
        console.log("获取表格数据成功：", data);
        commit("setTableDataList", data);
        return data;
      } catch (error) {
        console.log("获取表格数据失败：", error);
        return [];
      }
    },
  },
};
