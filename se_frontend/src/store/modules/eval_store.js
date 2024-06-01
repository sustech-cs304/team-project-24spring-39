import { fetchCoursesInside } from "@/api/eval_api";
export default {
  state: {
    courses: [
      {
        id: 1,
        name: "课程1",
        intro: "intro_1",
      },
      { id: 2, name: "课程2", intro: "intro_2" },
      { id: 3, name: "课程3", intro: "intro_3" },
      { id: 4, name: "课程4", intro: "intro_4" },
      { id: 5, name: "课程5", intro: "intro_5" },
      { id: 6, name: "课程6", intro: "intro_6" },
    ],

    selectedCourse: {
      id: -1,
      name: "课程名称",
      intro:
        "《原神》是由上海米哈游网络科技股份有限公司制作发行的一款开放世界冒险游戏，于2017年1月底立项 [27]，原初测试于2019年6月21日开启 [1]，再临测试于2020年3月19日开启，启程测试于2020年6月11日开启 [2]，PC版技术性开放测试于9月15日开启，公测于2020年9月28日开启 [3]。在数据方面，同在官方服务器的情况下，iOS、PC、Android平台之间的账号数据互通，玩家可以在同一账号下切换设备。游戏发生在一个被称作“提瓦特大陆”的幻想世界，在这里，被神选中的人将被授予“神之眼”，导引元素之力。玩家将扮演一位名为“旅行者”的神秘角色，在自由的旅行中邂逅性格各异、能力独特的同伴们，和他们一起击败强敌，找回失散的亲人——同时，逐步发掘“原神”的真相",
    },
  },

  mutations: {
    setCourses(state, newCourses) {
      state.courses = newCourses;
    },
    setSelectedCourse(state, course) {
      state.selectedCourse = course;
    },
    deleteCourse(state, course_id) {
      state.courses = state.courses.filter((item) => item.id !== course_id);
      if (state.courses.length !== 0) {
        state.selectedCourse = state.courses[0];
      } else {
        state.selectedCourse = {
          id: -1,
          name: "课程名称",
          intro:
            "《原神》是由上海米哈游网络科技股份有限公司制作发行的一款开放世界冒险游戏，于2017年1月底立项 [27]，原初测试于2019年6月21日开启 [1]，再临测试于2020年3月19日开启，启程测试于2020年6月11日开启 [2]，PC版技术性开放测试于9月15日开启，公测于2020年9月28日开启 [3]。在数据方面，同在官方服务器的情况下，iOS、PC、Android平台之间的账号数据互通，玩家可以在同一账号下切换设备。游戏发生在一个被称作“提瓦特大陆”的幻想世界，在这里，被神选中的人将被授予“神之眼”，导引元素之力。玩家将扮演一位名为“旅行者”的神秘角色，在自由的旅行中邂逅性格各异、能力独特的同伴们，和他们一起击败强敌，找回失散的亲人——同时，逐步发掘“原神”的真相",
        };
      }
      // console.log(state.courses);
    },
  },

  actions: {
    async fetchCourses({ commit }, studentNumber) {
      try {
        const response = await fetchCoursesInside(studentNumber);
        // console.log("response:", response.data.courses);
        // const data = await response.json();
        // console.log("data:", data);
        commit("setCourses", response.data.courses);
        commit("setSelectedCourse", response.data.courses[0]);
      } catch (error) {
        console.error("Error:", error);
      }
    },

    selectCourse({ commit }, course) {
      commit("setSelectedCourse", course); // 调用mutation更新状态
    },
  },
};
