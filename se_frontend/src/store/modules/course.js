export default {
  state: {
    remainingPoints: 100, // 初始值
  },
  mutations: {
    setRemainingPoints(state, points) {
      state.remainingPoints = points;
    },
  },
  getters: {
    getRemainingPoints(state) {
      return state.remainingPoints;
    },
  },
};
