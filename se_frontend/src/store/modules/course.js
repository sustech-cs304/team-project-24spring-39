import { getAllCourse } from "@/api/course";
export default {
  state: {
    tableDataList: [],
  },
  mutations: {},
  actions: {
    // 注意这里是 `actions` 而不是 `action`
    // eslint-disable-next-line no-empty-pattern
    async getTableALLDataList({}) {
      try {
        const res = await getAllCourse();
        console.log("res：", res);
        const data = res.data;
        console.log("data：", data);
        return data;
      } catch (error) {
        console.log("获取表格数据失败：", error);
        return [];
      }
    },
  },
};
