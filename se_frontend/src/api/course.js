import request from "@/utils/request";

export function fetchDataByType(type) {
  return request({
    method: "get",
    url: `/course/get_course/${type}`, // 使用模板字符串插入type变量
  });
}

// 更新 Vuex state
export function fetchCourse() {
  return request({
    method: "get",
    url: "/course/get_course_test",
  });
}

export function ReturnSelectedCourse() {
  return request({
    method: "get",
    url: `/course/show_selected_course`,
    params: { SID: "12345678" },
  });
}
export function fetchDataByCourseId(id) {
  return request({
    method: "get",
    url: `/course/show_selected_students`,
    params: { CID: id },
  });
}
export function submitSelectedCourse(courseCid, score) {
  return request({
    method: "post",
    url: `/course/add_course`,
    params: { course_id: courseCid, student_id: 12345678, score: score },
  });
}
export function DeleteSelectedCourseByCourseId(courseCid) {
  return request({
    method: "delete",
    url: `/course/delete_selected_course`, // 根据你的实际API端点修改
    params: { course_id: courseCid, student_id: 12345678 },
  });
}

export function searchTeacherBySid(Id) {
  return request({
    method: "get",
    url: "/course/search-teacher", // 根据你的实际API端点修改
    params: { sid: Id }, // 假设后端API使用'sid'作为查询学号的参数
  });
}

export function submitCourse(params) {
  const queryParams = new URLSearchParams(params).toString();
  return request({
    method: "post",
    url: `/course/admin_add_course?${queryParams}`,
  });
}

export function queryCourse(data) {
  return request({
    method: "get",
    url: `/course/query`, // 根据你的实际API端点修改
    params: {
      department: data.department,
      time: data.time,
      name: data.name,
    },
  });
}

export function endSelection() {
  return request({
    method: "get",
    url: "/course/end-course-selection",
  });
}

export function adminDeleteCourse(params) {
  return request({
    method: "delete",
    url: "/course/admin_delete_course",
    params,
  });
}
