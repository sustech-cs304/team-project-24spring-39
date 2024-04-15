export default {
  state: {
    // 是否展开侧边栏
    isExpand: true,
  },
  mutations: {
    // 设置侧边栏展开状态
    setIsExpand(state) {
      state.isExpand = !state.isExpand;
    },
  },
};
