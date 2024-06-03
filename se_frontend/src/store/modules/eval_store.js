import { fetchCoursesInside } from "@/api/eval_api";
function transformCourseData(data) {
  return data.map((course) => ({
    id: course.id,
    name: course.name,
    intro: course.description,
  }));
}
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
      intro: "",
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
          intro: "",
        };
      }
      // console.log(state.courses);
    },
  },

  actions: {
    async fetchCourses({ commit }) {
      try {
        const response = await fetchCoursesInside();
        // console.log("response:", response.data.courses);
        // const data = await response.json();
        // console.log("data:", data);
        const transformedData = transformCourseData(response.data);
        commit("setCourses", transformedData);
        commit("setSelectedCourse", transformedData[0]);
      } catch (error) {
        console.error("Error:", error);
      }
    },

    selectCourse({ commit }, course) {
      commit("setSelectedCourse", course); // 调用mutation更新状态
    },
  },
};
