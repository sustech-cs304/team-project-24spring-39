import request from "@/utils/request";

export function fetchDataByType(type) {
  return request({
    method: "get",
    url: `/course/get_course/${type}`, // 使用模板字符串插入type变量
  });
}

// 更新 Vuex state

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

export function submitCourse(params) {
  const queryParams = new URLSearchParams(params).toString();
  return request({
    method: "post",
    url: `/course/admin_add_course?${queryParams}`,
  });
}

export function queryCourse(data) {
  // 初始化请求参数对象
  let queryParams = {};
  if (data.time) {
    queryParams.time = data.time;
  }
  // 如果department存在且不为空，添加到queryParams
  if (data.department) {
    queryParams.department = data.department;
  }

  // 如果name存在且不为空，添加到queryParams
  if (data.name) {
    queryParams.name = data.name;
  }

  return request({
    method: "get",
    url: `/course/query`, // 根据你的实际API端点修改
    params: queryParams,
  });
}

export function endSelection() {
  return request({
    method: "post",
    url: `/course/end_course_selection`, // 根据你的实际API端点修改
  });
}
export function queryStopState() {
  return request({
    method: "get",
    url: `/course/get_course_selection`,
  });
}
export function QuerySelectedCourseFinal() {
  return request({
    method: "post",
    url: `/course/update_selection`,
  });
}
export function adminDeleteCourse(params) {
  return request({
    method: "delete",
    url: "/course/admin_delete_course",
    params,
  });
}
